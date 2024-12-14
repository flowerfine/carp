package cn.sliew.carp.plguin.jdbc.api;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.AbstractDataSourceProperties;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcClient {

    void start() throws SQLException;

    void close();

    DataSourceType getType();

    Connection getConnection() throws SQLException;

    SqlMeta getSqlMeta();

    SqlExecutor getSqlExecutor();

    SqlFormatter getSqlFormatter();

    SqlTranslater getSqlTranslator();
}
