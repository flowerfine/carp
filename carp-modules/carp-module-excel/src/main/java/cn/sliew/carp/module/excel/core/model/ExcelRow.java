package cn.sliew.carp.module.excel.core.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ExcelRow {

    private List<ExcelCell> cells;

    //    private Table<Integer, String, Object> table = HashBasedTable.create();
    private Map<Integer, ExcelCell> columnIndex;
    private Map<String, ExcelCell> fieldNameIndex;

    public ExcelRow(List<ExcelCell> cells) {
        this.cells = cells;
        this.columnIndex = new HashMap<>(cells.size() * 2);
        this.fieldNameIndex = new HashMap<>(cells.size() * 2);
    }

    private void index() {
        cells.forEach(cell -> {
            columnIndex.put(cell.getColumnIndex(), cell);
            fieldNameIndex.put(cell.getFieldName(), cell);
        });
    }

    public Object getValueByColumn(Integer column) {
        return columnIndex.get(column);
    }

    public Object getValueByFieldName(String fieldName) {
        return fieldNameIndex.get(fieldName);
    }
}
