package cn.sliew.carp.module.odps.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.managed.ManagedTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public enum MybatisUtil {
    ;

    public static <T> Cursor<T> getCursor(SqlSessionFactory sqlSessionFactory, String mappedStatementName, Map<String, Object> params, CompletableFuture callback) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        Configuration configuration = sqlSession.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement(mappedStatementName);
        BoundSql boundSql = mappedStatement.getBoundSql(params);

        Connection connection = sqlSession.getConnection();
        PreparedStatement statement = connection.prepareStatement(boundSql.getSql());

        ParameterHandler parameterHandler = configuration.newParameterHandler(mappedStatement, params, boundSql);
        parameterHandler.setParameters(statement);

        callback.whenComplete((unused, throwable) -> {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (sqlSession != null) {
                    sqlSession.close();
                }
            } catch (SQLException e) {
                log.error("手动关闭 PreparedStatement 异常!", e);
            }
        });

        OdpsTaskExecutor executor = new OdpsTaskExecutor(configuration, new ManagedTransaction(connection, false));
        return executor.queryCursor(mappedStatement, params, RowBounds.DEFAULT);
    }
}
