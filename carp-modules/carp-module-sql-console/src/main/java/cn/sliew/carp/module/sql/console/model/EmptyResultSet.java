package cn.sliew.carp.module.sql.console.model;

import cn.hutool.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class EmptyResultSet implements IResultSet {

    @Override
    public List<ColumnVO> columns() {
        return Collections.emptyList();
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public JSONObject rowData() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
