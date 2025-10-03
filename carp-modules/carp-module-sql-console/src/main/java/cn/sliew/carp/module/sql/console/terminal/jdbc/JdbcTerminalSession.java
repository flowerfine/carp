package cn.sliew.carp.module.sql.console.terminal.jdbc;

import cn.sliew.carp.module.sql.console.terminal.JDBCResultSet;
import cn.sliew.carp.module.sql.console.terminal.SimpleResultSet;
import cn.sliew.carp.module.sql.console.terminal.TerminalSession;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JdbcTerminalSession implements TerminalSession {

    private final List<String> logs = Lists.newArrayList();

    private final Map<String, String> configs;
    private final DruidDataSource dataSource;
    private final Connection connection;

    public JdbcTerminalSession(Map<String, String> configs) {
        this.configs = configs;
        try {
            DruidDataSource instance = new DruidDataSource();
            instance.setDriverClassName(configs.get("driverClassName"));
            instance.setUrl(configs.get("jdbcUrl"));
            instance.setUsername(configs.get("username"));
            instance.setPassword(configs.get("password"));
//            instance.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            instance.setUrl("jdbc:mysql://42.194.234.138:3306/");
//            instance.setUsername("root");
//            instance.setPassword("123456");
            instance.setMinIdle(1);
            instance.setInitialSize(1);
            instance.setMaxActive(10);
            instance.setAsyncInit(true);
            instance.setMaxWait(Duration.ofSeconds(6L).toMillis());
            instance.setKeepAlive(true);
            instance.setPoolPreparedStatements(true);
            instance.setMaxOpenPreparedStatements(20);
            instance.setValidationQuery("SELECT 1");
            instance.setTestOnBorrow(false);
            instance.setTestOnReturn(false);
            instance.setTestWhileIdle(true);
            // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            instance.setTimeBetweenEvictionRunsMillis(Duration.ofMinutes(1L).toMillis());
            // 配置一个连接在池中最小生存的时间，单位是毫秒
            instance.setMinEvictableIdleTimeMillis(Duration.ofMinutes(5L).toMillis());
            instance.setMaxEvictableIdleTimeMillis(Duration.ofHours(1L).toMillis());

            instance.init();

            this.dataSource = instance;
            this.connection = this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> configs() {
        return configs;
    }

    @Override
    public ResultSet executeStatement(String catalog, String statement) {
        try {
            PreparedStatement ps = connection.prepareStatement(statement);
            boolean executed = ps.execute();
            if (executed) {
                java.sql.ResultSet rs = ps.getResultSet();
                return new JDBCResultSet(rs, ps);
            } else {
                int updateCount = ps.getUpdateCount();
                return new SimpleResultSet(Collections.emptyList(), Collections.emptyList());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> logs() {
        List<String> logs = Lists.newArrayList(this.logs);
        this.logs.clear();
        return logs;
    }

    @Override
    public boolean active() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void release() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
