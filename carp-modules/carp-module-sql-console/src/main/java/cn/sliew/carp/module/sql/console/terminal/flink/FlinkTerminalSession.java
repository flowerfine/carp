package cn.sliew.carp.module.sql.console.terminal.flink;

import cn.sliew.carp.module.sql.console.terminal.JDBCResultSet;
import cn.sliew.carp.module.sql.console.terminal.SimpleResultSet;
import cn.sliew.carp.module.sql.console.terminal.TerminalSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.*;
import org.apache.flink.table.gateway.api.SqlGatewayService;
import org.apache.flink.table.gateway.api.session.SessionEnvironment;
import org.apache.flink.table.gateway.api.session.SessionHandle;
import org.apache.flink.table.gateway.rest.util.SqlGatewayRestAPIVersion;
import org.apache.flink.table.gateway.service.SqlGatewayServiceImpl;
import org.apache.flink.table.gateway.service.context.DefaultContext;
import org.apache.flink.table.gateway.service.session.SessionManager;
import org.apache.flink.table.gateway.service.session.SessionManagerImpl;
import org.apache.flink.table.jdbc.FlinkDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class FlinkTerminalSession implements TerminalSession {

    private final String sessionId;

    private final DataSource flinkDataSource;
    private final Connection connection;

    private final SessionManager sessionManager;
    private final SqlGatewayService sqlGatewayService;
    private final SessionHandle sessionHandle;

    public FlinkTerminalSession(String sessionId) {
        this.sessionId = sessionId;

        Configuration configuration = GlobalConfiguration.loadConfiguration();
        configuration.set(JobManagerOptions.ADDRESS, "localhost");
        configuration.set(RestOptions.ADDRESS, "localhost");
        configuration.set(RestOptions.PORT, 8081);
        configuration.set(DeploymentOptions.TARGET, "remote");
        configuration.setString("s3.endpoint", "http://localhost:9000");
        configuration.setString("s3.access-key", "admin");
        configuration.setString("s3.secret-key", "password");
        configuration.setString("s3.path.style.access", "true");
        DefaultContext defaultContext = new DefaultContext(configuration, Collections.emptyList());
        this.sessionManager = new SessionManagerImpl(defaultContext);
        this.sessionManager.start();
        this.sqlGatewayService = new SqlGatewayServiceImpl(this.sessionManager);
        this.sessionHandle = this.sqlGatewayService.openSession(SessionEnvironment.newBuilder()
                .setSessionEndpointVersion(SqlGatewayRestAPIVersion.V2)
                .setSessionName(sessionId)
                .build());


        try {
            this.flinkDataSource = new FlinkDataSource("jdbc:flink://localhost:8083", new Properties());
            this.connection = flinkDataSource.getConnection();
            try (Statement st = connection.createStatement()) {
                st.execute("""
                        CREATE TABLE T (
                            word STRING
                        ) WITH (
                            'connector' = 'datagen',
                            'fields.word.length' = '1'
                        );
                        """);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> configs() {
        return Map.of();
    }

    @Override
    public ResultSet executeStatement(String catalog, String statement) {
        try {
            Statement st = connection.createStatement();
            boolean executed = st.execute(statement);
            if (executed) {
                java.sql.ResultSet rs = st.getResultSet();
                return new JDBCResultSet(rs, st);
            } else {
                int updateCount = st.getUpdateCount();
                return new SimpleResultSet(Collections.emptyList(), Collections.emptyList());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> logs() {
        return List.of();
    }

    @Override
    public boolean active() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
//        try {
//            sqlGatewayService.getSessionConfig(sessionHandle);
//            return true;
//        } catch (SqlGatewayException e) {
//            return false;
//        }
    }

    @Override
    public void release() {
        sessionManager.closeSession(sessionHandle);
        sessionManager.stop();
    }
}
