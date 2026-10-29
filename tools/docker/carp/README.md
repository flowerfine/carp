# 使用注意

## gravitino

#### 启动

在 0.6.0 之后，只支持 jdbc 存储。jdbc 类型包括 `h2` 和 `mysql`，默认是 `h2`，如果要启用 `mysql`，需要添加驱动，初始化 `mysql` 表结构，更改配置启用 `mysql`。

> gravitino 不包含 mysql 驱动。mysql 驱动开源协议和 Apache 协议不兼容，因此 Apache 社区的开源项目都是不带 mysql 驱动的，如 dolphinscheduler、flink-cdc 都是不包含，其余项目都可以进行相应验证。

#### catalogs

如果是 `mysql`，需要添加对应的驱动实现类，位置位于 `${gravitino_home}/catalogs/jdbc-mysql/libs`。`postgresql` 或 `doris` 同理：

* mysql。`/path/to/mysql.jar:/root/gravitino/catalogs/jdbc-mysql/libs/mysql.jar`。
* doris。`/path/to/mysql.jar:/root/gravitino/catalogs/jdbc-doris/libs/mysql.jar`。使用 mysql 驱动
* postgresql。`/path/to/postgresql.jar:/root/gravitino/catalogs/jdbc-postgresql/libs/postgresql.jar`。