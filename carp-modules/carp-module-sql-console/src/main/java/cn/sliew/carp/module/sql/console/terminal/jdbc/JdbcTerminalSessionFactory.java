package cn.sliew.carp.module.sql.console.terminal.jdbc;

import cn.sliew.carp.module.sql.console.option.Configurations;
import cn.sliew.carp.module.sql.console.terminal.TerminalSession;
import cn.sliew.carp.module.sql.console.terminal.TerminalSessionFactory;

import java.util.Map;

public class JdbcTerminalSessionFactory implements TerminalSessionFactory {

    @Override
    public void initialize(Configurations properties) {

    }

    @Override
    public TerminalSession create(Configurations configuration) {
        Map<String, String> configs = Map.of(
                "driverClassName", "com.mysql.cj.jdbc.Driver",
                "jdbcUrl", "jdbc:mysql://42.194.234.138:3306/",
                "username", "root",
                "password", "123456"
        );
        return new JdbcTerminalSession(configs);
    }
}
