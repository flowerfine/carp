package cn.sliew.carp.module.excel.core.model;

import lombok.Data;

import java.util.List;

@Data
public class ExcelData {

    private List<ExcelSheet> sheets;
}
