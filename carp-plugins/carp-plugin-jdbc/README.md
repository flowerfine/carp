# Carp JDBC Plugin

## 元数据

JDBC 数据源作为一种流行协议，是获取数据库元数据的最兼容的方式，通过 JDBC 获取元数据有 2 种方式：

* 使用 JDBC 元数据。
* 查询数据库实现的底层元数据。

一般实现有 2 种方式：

* 默认使用 JDBC 元数据
* 提供插件机制，供数据库插件实现自行查询数据库底层元数据，获取更多的信息

经典实现如 [Chat2DB](https://github.com/OtterMind/Chat2DB)：

* 默认 JDBC 元数据。[DefaultMetaService](https://github.com/OtterMind/Chat2DB/blob/main/chat2db-community-server/chat2db-community-spi/src/main/java/ai/chat2db/spi/DefaultMetaService.java)
* 数据库插件实现。[MysqlMetaData](https://github.com/OtterMind/Chat2DB/blob/main/chat2db-community-server/chat2db-community-plugins/chat2db-community-mysql/src/main/java/ai/chat2db/plugin/mysql/MysqlMetaData.java)

还有 [dms](https://github.com/wuchunfu/dms)：

* 默认 JDBC 元数据。[JdbcCatalogHandler](https://github.com/wuchunfu/dms/blob/main/dms-plugins/dms-plugins-datasource/src/main/java/com/basedt/dms/plugins/datasource/impl/jdbc/JdbcCatalogHandler.java)
* 数据库插件实现。[DorisCatalogHandler](https://github.com/wuchunfu/dms/blob/main/dms-plugins/dms-plugins-datasource/src/main/java/com/basedt/dms/plugins/datasource/impl/doris/DorisCatalogHandler.java)

### DatabaseMetaData

可以直接通过 JDBC 的 `DatabaseMetaData` 获取元数据，兼容性最好

```java
DatabaseMetaData metaData = connection.getMetaData();
ResultSet catalogs = metaData.getCatalogs();
ResultSet schemas = metaData.getSchemas();
ResultSet schemas = metaData.getSchemas(catalog, null);
ResultSet schemas = metaData.getSchemas(catalog, schema);
ResultSet tables = metaData.getTables(catalog, schema, null, new String[]{TableType.TABLE.getTypeName(), TableType.SYSTEM_TABLE.getTypeName(), TableType.VIEW.getTypeName()});
ResultSet columns = metaData.getColumns(catalog, schema, table, null);
ResultSet indexInfo = metaData.getIndexInfo(catalog, schema, table, false, false);
ResultSet functions = metaData.getFunctions(catalog, schema, null);
ResultSet procedures = metaData.getProcedures(catalog, schema, null);
```

实现比较好的如 [Chat2DB](https://github.com/OtterMind/Chat2DB) 的 [DefaultMetaService](https://github.com/OtterMind/Chat2DB/blob/main/chat2db-community-server/chat2db-community-spi/src/main/java/ai/chat2db/spi/DefaultMetaService.java)、[carp](https://github.com/flowerfine/carp) 的 [JooqSqlMeta](https://github.com/flowerfine/carp/blob/dev/carp-plugins/carp-plugin-jdbc/carp-plugin-jdbc-driver-base/src/main/java/cn/sliew/carp/plugin/jdbc/driver/jooq/JooqSqlMeta.java)。

### 数据库元数据表

如 MySQL 查询 `INFORMATION_SCHEMA` 数据库获取元信息：

```sql
SELECT TABLE_SCHEMA,
         TABLE_NAME,
         `ENGINE`,
         `VERSION`,
         TABLE_ROWS,
         DATA_LENGTH,
         `AUTO_INCREMENT`,
         CREATE_TIME,
         UPDATE_TIME,
         TABLE_COLLATION,
         TABLE_COMMENT
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE IN ('BASE TABLE','SYSTEM VIEW')
        AND TABLE_SCHEMA = '%s'
```

实现比较好的同样是 [Chat2DB](https://github.com/OtterMind/Chat2DB) 的 [MysqlMetaData](https://github.com/OtterMind/Chat2DB/blob/main/chat2db-community-server/chat2db-community-plugins/chat2db-community-mysql/src/main/java/ai/chat2db/plugin/mysql/MysqlMetaData.java)

## ResultSet 转换

将 JDBC `ResultSet` 转成 BEAN 对象的方式有多样：

* 硬编码。采用 `rs.getString("table_name")`
* Proxy。对 `ResultSet` 构建一个代理
