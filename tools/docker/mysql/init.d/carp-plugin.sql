create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_plugin_info;
create table carp_plugin_info
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `type`        varchar(16) not null comment 'type',
    `provider`    varchar(64) comment '提供者',
    `name`        varchar(64) comment '名称',
    `clazz`       varchar(255) comment 'plugin class',
    `license`     varchar(32) comment '证书',
    `dependency`  varchar(255) comment '依赖',
    `remark`      varchar(255) comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (`name`),
    key           idx_name (`name`)
) engine = innodb comment = 'plugin info';
INSERT INTO `carp_plugin_info` (`id`, `type`, `provider`, `name`, `clazz`, `license`, `dependency`, `remark`, `creator`,
                                `editor`)
VALUES (1, 'internal', 'carp', 'hello-plugin', 'cn.sliew.carp.plugin.test.HelloPlugin', 'Apache-2.0', NULL,
        'hello demo plugin', 'sys', 'sys');
INSERT INTO `carp_plugin_info` (`id`, `type`, `provider`, `name`, `clazz`, `license`, `dependency`, `remark`, `creator`,
                                `editor`)
VALUES (2, 'internal', 'carp', 'welcome-plugin', 'cn.sliew.carp.plugin.test.WelcomePlugin', 'Apache-2.0', NULL,
        'welcome demo plugin', 'sys', 'sys');

drop table if exists carp_plugin_release;
create table carp_plugin_release
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `plugin_id`   bigint      not null comment '插件ID',
    `uuid`        varchar(32) not null comment 'uuid',
    `version`     varchar(64) comment '版本',
    `url`         varchar(512) comment '链接',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (`plugin_id`, `version`)
) engine = innodb comment = 'plugin release';

INSERT INTO `carp_plugin_release` (`id`, `plugin_id`, `uuid`, `version`, `url`, `creator`, `editor`)
VALUES (1, 1, 'a83c70ad7de344a4a06c7c2d46d4265a', '0.0.23',
        'https://repo1.maven.org/maven2/cn/sliew/carp-plugin-test-1/0.0.23/carp-plugin-test-1-0.0.23.jar', 'sys',
        'sys');
INSERT INTO `carp_plugin_release` (`id`, `plugin_id`, `uuid`, `version`, `url`, `creator`, `editor`)
VALUES (2, 2, '0763a517723f4550a1f3afa665feee1e', '0.0.23',
        'https://repo1.maven.org/maven2/cn/sliew/carp-plugin-test-2/0.0.23/carp-plugin-test-2-0.0.23.jar', 'sys',
        'sys');

drop table if exists carp_plugin_status;
create table carp_plugin_status
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `plugin_uuid` varchar(32) not null comment 'uuid',
    `status`      varchar(16) comment '状态',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (`plugin_uuid`)
) engine = innodb comment = 'plugin status';
INSERT INTO `carp_plugin_status` (`id`, `plugin_uuid`, `status`, `creator`, `editor`)
VALUES (1, 'a83c70ad7de344a4a06c7c2d46d4265a', 'STARTED', 'sys', 'sys');
INSERT INTO `carp_plugin_status` (`id`, `plugin_uuid`, `status`, `creator`, `editor`)
VALUES (2, '0763a517723f4550a1f3afa665feee1e', 'STARTED', 'sys', 'sys');