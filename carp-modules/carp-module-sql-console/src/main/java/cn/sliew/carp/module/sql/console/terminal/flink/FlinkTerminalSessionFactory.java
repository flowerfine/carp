package cn.sliew.carp.module.sql.console.terminal.flink;

import cn.sliew.carp.module.sql.console.option.Configurations;
import cn.sliew.carp.module.sql.console.terminal.TerminalSession;
import cn.sliew.carp.module.sql.console.terminal.TerminalSessionFactory;

public class FlinkTerminalSessionFactory implements TerminalSessionFactory {

    @Override
    public void initialize(Configurations properties) {

    }

    @Override
    public TerminalSession create(Configurations configuration) {
        return new FlinkTerminalSession(null);
    }
}
