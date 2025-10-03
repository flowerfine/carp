package cn.sliew.carp.module.sql.console.model;

import java.util.List;
import java.util.Map;

public interface ExecuteEngine {

    List getCatalogs();

    Map<String, String> getConfigs();

    IResultSet execute(String script);
}
