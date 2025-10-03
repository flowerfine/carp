package cn.sliew.carp.module.sql.console.model.jdbc;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.SqlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.sliew.carp.module.sql.console.model.ColumnVO;
import cn.sliew.carp.module.sql.console.model.IResultSet;
import cn.sliew.carp.module.sql.console.utils.JdbcUtil;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.ResultSetDynaClass;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class JdbcResultSet implements IResultSet {

    private final ResultSet rs;
    private final ResultSetDynaClass dynaClass;
    private final List<ColumnVO> columns;
    private final Iterator<DynaBean> rows;

    public JdbcResultSet(ResultSet rs) {
        this.rs = rs;
        try {
            this.dynaClass = new ResultSetDynaClass(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.columns = JdbcUtil.getColumns(dynaClass);
        this.rows = this.dynaClass.iterator();
    }

    @Override
    public List<ColumnVO> columns() {
        return columns;
    }

    @Override
    public boolean next() {
        return rows.hasNext();
    }

    @Override
    public JSONObject rowData() {
        DynaBean bean = rows.next();
        DynaProperty[] cols = bean.getDynaClass().getDynaProperties();
        JSONObject node = JSONUtil.createObj();
        for (DynaProperty col : cols) {
            String colName = col.getName();
            if (col.getType().getName().equals(Timestamp.class.getName())) {
                Timestamp value = (Timestamp) bean.get(col.getName());
                if (Objects.isNull(value)) {
                    node.set(colName, null);
                } else {
                    node.set(colName, DateUtil.format(value, DatePattern.NORM_DATETIME_MS_PATTERN));
                }
            } else if (col.getType().getName().equals(Date.class.getName())) {
                Date value = (Date) bean.get(col.getName());
                if (Objects.isNull(value)) {
                    node.set(colName, null);
                } else {
                    node.set(colName, DateUtil.format(value, DatePattern.NORM_DATETIME_PATTERN));
                }
            } else if (col.getType().getName().equals(java.sql.Array.class.getName())) {
                Object value = bean.get(col.getName());
                if (Objects.isNull(value)) {
                    node.set(colName, null);
                } else {
                    node.set(colName, String.valueOf(value));
                }
            } else if (col.getType().getName().equals("oracle.jdbc.OracleClob")) {
                Clob value = (Clob) bean.get(col.getName());
                if (Objects.isNull(value)) {
                    node.set(colName, null);
                } else {
                    node.set(colName, SqlUtil.clobToStr(value));
                }
            } else {
                Object value = bean.get(col.getName());
                node.set(colName, value);
            }
        }
        return node;
    }

    @Override
    public void close() throws Exception {
        // ResultSetDynaClass
    }
}
