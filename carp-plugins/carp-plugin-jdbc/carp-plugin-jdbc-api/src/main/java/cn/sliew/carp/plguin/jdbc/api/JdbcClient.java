package cn.sliew.carp.plguin.jdbc.api;

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcClient {

    void start() throws SQLException;

    void close();

    CarpDataSourceType getType();

    Connection getConnection() throws SQLException;

    SqlMeta getSqlMeta();

    SqlExecutor getSqlExecutor();

    SqlFormatter getSqlFormatter();

    SqlTranslater getSqlTranslator();
}
