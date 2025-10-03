package cn.sliew.carp.module.sql.console.terminal.flink;

import cn.sliew.carp.module.sql.console.terminal.TerminalSession;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.gateway.api.SqlGatewayService;
import org.apache.flink.table.gateway.api.operation.OperationHandle;
import org.apache.flink.table.gateway.api.results.FetchOrientation;
import org.apache.flink.table.gateway.api.results.ResultSet;
import org.apache.flink.table.gateway.api.session.SessionHandle;

import java.util.List;

public class FlinkResultSet implements TerminalSession.ResultSet {

    private final SqlGatewayService sqlGatewayService;
    private final SessionHandle sessionHandle;
    private final OperationHandle operationHandle;

    public FlinkResultSet(SqlGatewayService sqlGatewayService, SessionHandle sessionHandle, OperationHandle operationHandle) {
        this.sqlGatewayService = sqlGatewayService;
        this.sessionHandle = sessionHandle;
        this.operationHandle = operationHandle;

        final ResultSet resultSet = sqlGatewayService.fetchResults(sessionHandle, operationHandle, FetchOrientation.FETCH_NEXT, 1);
        final ResolvedSchema resultSchema = resultSet.getResultSchema();
    }

    @Override
    public List<String> columns() {
        return List.of();
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public Object[] rowData() {
        return new Object[0];
    }

    @Override
    public void close() {

    }
}
