package cn.sliew.carp.module.excel.core.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExcelSheet {

    private Integer sheetNo;
    private String sheetName;
    private Map<Integer, List<String>> columnHeadNames;
    private Table<Integer, Integer, ExcelCell> sheetTable = HashBasedTable.create();

    public void addCell(ExcelCell cell) {
        sheetTable.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
    }
}
