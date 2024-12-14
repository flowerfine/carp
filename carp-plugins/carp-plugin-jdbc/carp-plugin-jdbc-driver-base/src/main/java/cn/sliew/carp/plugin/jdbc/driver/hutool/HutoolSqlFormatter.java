package cn.sliew.carp.plugin.jdbc.driver.hutool;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.plguin.jdbc.api.SqlFormatter;

public class HutoolSqlFormatter implements SqlFormatter {

    @Override
    public String format(String sql, DataSourceType dataSourceType) {
        return cn.hutool.db.sql.SqlFormatter.format(sql);
    }
}
