/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.carp.module.sql.console.terminal;

import cn.sliew.carp.module.sql.console.option.Configurations;
import cn.sliew.carp.module.sql.console.service.model.LatestSessionInfo;
import cn.sliew.carp.module.sql.console.service.model.LogInfo;
import cn.sliew.carp.module.sql.console.service.model.SqlResult;
import cn.sliew.carp.module.sql.console.terminal.flink.FlinkTerminalSessionFactory;
import cn.sliew.carp.module.sql.console.terminal.jdbc.JdbcTerminalSessionFactory;
import cn.sliew.carp.module.sql.console.terminal.local.LocalSessionFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TerminalManager {

    private static final int SESSION_TIMEOUT_CHECK_INTERVAL = 5 * 60 * 1000; // 5min

    private final AtomicLong threadPoolCount = new AtomicLong();
    private final TerminalSessionFactory sessionFactory;
    private final int resultLimits = 1000;
    private final boolean stopOnError = false;

    private final int sessionTimeout = 30;

    private final Object sessionMapLock = new Object();
    private final Map<String, TerminalSessionContext> sessionMap = Maps.newHashMap();
    private final Thread gcThread;
    private volatile boolean running = true;

    private final ThreadPoolExecutor executionPool =
            new ThreadPoolExecutor(
                    1,
                    50,
                    30,
                    TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(),
                    r -> new Thread(null, r, "terminal-execute-" + threadPoolCount.incrementAndGet()));

    public TerminalManager() {
        this.sessionFactory = loadTerminalSessionFactory("flink");
        gcThread = new Thread(new SessionCleanTask());
        gcThread.setName("terminal-session-gc");
        gcThread.start();
    }

    /**
     * execute script, return terminal sessionId
     *
     * @param terminalId - id to mark different terminal windows
     * @param catalog    - current catalog to execute script
     * @param script     - sql script to be executed
     * @return - sessionId, session refer to a sql execution context
     */
    public String executeScript(String terminalId, String catalog, String script) {
        String sessionId = getSessionId(terminalId, catalog);
        Configurations configuration = new Configurations();
        configuration.setInteger(TerminalSessionFactory.SessionConfigOptions.FETCH_SIZE, resultLimits);
        configuration.set(
                TerminalSessionFactory.SessionConfigOptions.CATALOGS, Lists.newArrayList(catalog));

        synchronized (sessionMapLock) {
            sessionMap.compute(
                    sessionId,
                    (id, ctx) -> {
                        if (ctx == null) {
                            return new TerminalSessionContext(
                                    id, executionPool, sessionFactory, configuration);
                        } else {
                            // need to re-create session context if configuration is changed
                            return ctx.sessionConfiguration().equals(configuration)
                                    ? ctx
                                    : new TerminalSessionContext(
                                    id, executionPool, sessionFactory, configuration);
                        }
                    });
        }

        TerminalSessionContext context = sessionMap.get(sessionId);
        if (!context.isReadyToExecute()) {
            throw new IllegalStateException(
                    "current session is not ready to execute script. status:" + context.getStatus());
        }
        context.submit(catalog, script, resultLimits, stopOnError);
        return sessionId;
    }

    /**
     * Get execution status and logs
     */
    public LogInfo getExecutionLog(String sessionId) {
        if (sessionId == null) {
            return new LogInfo(ExecutionStatus.Expired.name(), Lists.newArrayList());
        }
        TerminalSessionContext sessionContext;
        synchronized (sessionMapLock) {
            sessionContext = sessionMap.get(sessionId);
        }
        if (sessionContext == null) {
            return new LogInfo(ExecutionStatus.Expired.name(), Lists.newArrayList());
        }
        return new LogInfo(sessionContext.getStatus().name(), sessionContext.getLogs());
    }

    /**
     * Get execution result.
     */
    public List<SqlResult> getExecutionResults(String sessionId) {
        if (sessionId == null) {
            return Lists.newArrayList();
        }
        TerminalSessionContext context;
        synchronized (sessionMapLock) {
            context = sessionMap.get(sessionId);
        }
        if (context == null) {
            return Lists.newArrayList();
        }
        return context.getStatementResults().stream()
                .map(
                        statement -> {
                            SqlResult sql = new SqlResult();
                            sql.setId("line:" + statement.getLineNumber() + " - " + statement.getStatement());
                            sql.setColumns(statement.getColumns());
                            sql.setRowData(statement.getDataAsStringList());
                            sql.setStatus(
                                    statement.isSuccess()
                                            ? ExecutionStatus.Finished.name()
                                            : ExecutionStatus.Failed.name());
                            return sql;
                        })
                .collect(Collectors.toList());
    }

    /**
     * cancel execution
     */
    public void cancelExecution(String sessionId) {
        if (sessionId == null) {
            return;
        }
        TerminalSessionContext context;
        synchronized (sessionMapLock) {
            context = sessionMap.get(sessionId);
        }
        if (context != null) {
            context.cancel();
        }
    }

    /**
     * Get last execution info
     *
     * @param terminalId - id of terminal window
     * @return last session info
     */
    public LatestSessionInfo getLastSessionInfo(String terminalId) {
        String prefix = terminalId + "-";
        long lastExecutionTime = -1;
        String sessionId = "";
        String script = "";
        synchronized (sessionMapLock) {
            for (String sid : sessionMap.keySet()) {
                if (sid.startsWith(prefix)) {
                    TerminalSessionContext context = sessionMap.get(sid);
                    if (context == null) {
                        continue;
                    }
                    if (lastExecutionTime < context.lastExecutionTime()) {
                        lastExecutionTime = context.lastExecutionTime();
                        sessionId = sid;
                        script = context.lastScript();
                    }
                }
            }
        }
        return new LatestSessionInfo(sessionId, script);
    }

    public void dispose() {
        if (!running) {
            return;
        }
        running = false;
        if (gcThread != null) {
            gcThread.interrupt();
        }
        executionPool.shutdown();
    }

    // ========================== private method =========================

    private String getSessionId(String loginId, String catalog) {
        String sessionId = loginId + "-" + catalog;
        sessionId = sessionId.replace("/", "_");
        return sessionId;
    }

    private TerminalSessionFactory loadTerminalSessionFactory(String backend) {
        String backendImplement;
        switch (backend.toLowerCase()) {
            case "local":
                backendImplement = LocalSessionFactory.class.getName();
                break;
            case "jdbc":
                backendImplement = JdbcTerminalSessionFactory.class.getName();
                break;
            case "flink":
                backendImplement = FlinkTerminalSessionFactory.class.getName();
                break;
            default:
                throw new IllegalArgumentException(
                        "illegal terminal implement: " + backend + ", local, kyuubi, " + "custom is available");
        }
        TerminalSessionFactory factory;
        try {
            factory = (TerminalSessionFactory) Class.forName(backendImplement).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException("failed to init session factory", e);
        }

        Configurations configuration = new Configurations();
        configuration.set(TerminalSessionFactory.FETCH_SIZE, this.resultLimits);
        factory.initialize(configuration);
        return factory;
    }

    private class SessionCleanTask implements Runnable {
        private static final long MINUTE_IN_MILLIS = 60 * 1000;

        @Override
        public void run() {
            log.info("Terminal Session Clean Task started");
            log.info(
                    "Terminal Session Clean Task, check interval: " + SESSION_TIMEOUT_CHECK_INTERVAL + " ms");
            log.info("Terminal Session Timeout: {} minutes", sessionTimeout);
            while (running) {
                try {
                    List<TerminalSessionContext> sessionToRelease = checkIdleSession();
                    sessionToRelease.forEach(this::releaseSession);
                    if (!sessionToRelease.isEmpty()) {
                        log.info("Terminal Session release count: {}", sessionToRelease.size());
                    }
                } catch (Throwable t) {
                    log.error("error when check and release session", t);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(SESSION_TIMEOUT_CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    log.error("Interrupted when sleep", e);
                }
            }
        }

        private List<TerminalSessionContext> checkIdleSession() {
            final long timeoutInMillis = sessionTimeout * MINUTE_IN_MILLIS;
            synchronized (sessionMapLock) {
                List<TerminalSessionContext> sessionToRelease = Lists.newArrayList();
                for (String sessionId : sessionMap.keySet()) {
                    TerminalSessionContext sessionContext = sessionMap.get(sessionId);
                    if (sessionContext.isIdleStatus()) {
                        long idleTime = System.currentTimeMillis() - sessionContext.lastExecutionTime();
                        if (idleTime > timeoutInMillis) {
                            sessionToRelease.add(sessionContext);
                        }
                    }
                }

                sessionToRelease.forEach(s -> sessionMap.remove(s.getSessionId()));
                return sessionToRelease;
            }
        }

        private void releaseSession(TerminalSessionContext sessionContext) {
            try {
                sessionContext.release();
            } catch (Throwable t) {
                log.error("error when release session: {}", sessionContext.getSessionId(), t);
            }
        }
    }
}
