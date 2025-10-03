package cn.sliew.carp.module.sql.console.model.jdbc;

import cn.sliew.carp.module.sql.console.model.EmptyResultSet;
import cn.sliew.carp.module.sql.console.model.ExecuteEngine;
import cn.sliew.carp.module.sql.console.model.IResultSet;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class JdbcExecuteEngine implements ExecuteEngine {

    private final Map<String, String> configs;
    private final DataSource dataSource;

    public JdbcExecuteEngine() {
        this.configs = Map.of(
                "", ""
        );
        try {
            DruidDataSource instance = new DruidDataSource();
            instance.setDriverClassName("com.mysql.cj.jdbc.Driver");
            instance.setUrl("jdbc:mysql://42.194.234.138:3306/");
            instance.setUsername("root");
            instance.setPassword("123456");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getCatalogs() {
        return List.of();
    }

    @Override
    public Map<String, String> getConfigs() {
        return configs;
    }

    @Override
    public IResultSet execute(String script) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(script)) {
            boolean executed = ps.execute();
            if (executed) {
                ResultSet rs = ps.getResultSet();
                return new JdbcResultSet(rs);
            } else {
                int updateCount = ps.getUpdateCount();
                return new EmptyResultSet();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
