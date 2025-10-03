package cn.sliew.carp.module.sql.console.model;

import cn.hutool.json.JSONObject;

import java.util.List;

public interface IResultSet extends AutoCloseable {

    List<ColumnVO> columns();

    boolean next();

    JSONObject rowData();
}
