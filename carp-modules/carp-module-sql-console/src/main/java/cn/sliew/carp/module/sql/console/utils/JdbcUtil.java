package cn.sliew.carp.module.sql.console.utils;

import cn.sliew.carp.module.sql.console.model.ColumnVO;
import cn.sliew.carp.module.sql.console.model.DataType;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.ResultSetDynaClass;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public enum JdbcUtil {
    ;

    public static List<ColumnVO> getColumns(ResultSetDynaClass resultSetDynaClass) {
        DynaProperty[] cols = resultSetDynaClass.getDynaProperties();
        List<ColumnVO> columns = Lists.newArrayListWithExpectedSize(cols.length);
        for (DynaProperty col : cols) {
            ColumnVO columnVO = new ColumnVO();
            String name = col.getName();
            columnVO.setKey(name);
            columnVO.setTitle(name);
            columnVO.setDataIndex(name);
            columnVO.setDataType(convertDataType(col.getType()));
            columns.add(columnVO);
        }
        return columns;
    }

    private static DataType convertDataType(Class<?> type) {
        if (Byte.class.getName().equals(type.getName())
                || Short.class.getName().equals(type.getName())
                || Integer.class.getName().equals(type.getName())
                || Long.class.getName().equals(type.getName())
                || Float.class.getName().equals(type.getName())
                || Double.class.getName().equals(type.getName())
        ) {
            return DataType.NUMBER;
        } else if (Boolean.class.getName().equals(type.getName())) {
            return DataType.TEXT;
        } else if (Timestamp.class.getName().equals(type.getName())) {
            return DataType.TIMESTAMP;
        } else if (Date.class.getName().equals(type.getName())) {
            return DataType.DATESTRING;
        }
        return DataType.TEXT;
    }
}
