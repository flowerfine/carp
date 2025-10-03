package cn.sliew.carp.module.sql.console.model;

import java.util.Map;

public interface ExecuteEngineFactory {

    void initialize(Map<String, String> properties);

    ExecuteEngine create(Map<String, String> configs);
}
