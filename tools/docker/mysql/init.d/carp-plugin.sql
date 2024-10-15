create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_plugin;
create table carp_plugin
(
    `id`          bigint     not null auto_increment comment '自增主键',
    `type`        varchar(4) not null comment 'type',
    `name`        varchar(64) comment '名称',
    `url`         varchar(512) comment '链接',
    `status`      varchar(8) comment '状态',
    `plugin_id`   varchar(32) comment 'pf4j pluginId',
    `remark`      varchar(255) comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime   not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime   not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (`name`),
    key           idx_name (`name`)
) engine = innodb comment = 'plugin';
INSERT INTO `carp_plugin`(`id`, `type`, `name`, `url`, `status`, `plugin_id`, `remark`, `creator`, `editor`)
VALUES (1, '0', 'carp-plugin-test-1',
        'https://repo1.maven.org/maven2/cn/sliew/carp-plugin-test-1/0.0.9/carp-plugin-test-1-0.0.9.jar', NULL, NULL,
        NULL, 'sys', 'sys');
INSERT INTO `carp_plugin`(`id`, `type`, `name`, `url`, `status`, `plugin_id`, `remark`, `creator`, `editor`)
VALUES (2, '0', 'carp-plugin-test-2',
        'https://repo1.maven.org/maven2/cn/sliew/carp-plugin-test-2/0.0.9/carp-plugin-test-2-0.0.9.jar', NULL, NULL,
        NULL, 'sys', 'sys');