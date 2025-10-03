package cn.sliew.carp.module.sql.console.model.jdbc;

import cn.sliew.carp.module.sql.console.model.ExecuteEngine;
import cn.sliew.carp.module.sql.console.model.ExecuteEngineFactory;

import java.util.Map;

public class JdbcExecuteEngineFactory implements ExecuteEngineFactory {

    @Override
    public void initialize(Map<String, String> properties) {

    }

    @Override
    public ExecuteEngine create(Map<String, String> configs) {
        return new JdbcExecuteEngine();
    }
}
