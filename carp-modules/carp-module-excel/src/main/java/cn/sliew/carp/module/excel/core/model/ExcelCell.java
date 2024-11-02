package cn.sliew.carp.module.excel.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelCell {

    private Integer rowIndex;
    private Integer columnIndex;
    private String headerName;
    private String fieldName;
    private Object value;
}
