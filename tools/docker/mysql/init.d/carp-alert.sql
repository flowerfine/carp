create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_alert_message;
create table carp_alert_message
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `name`        varchar(64) not null comment '名称',
    `group_name`  varchar(64) comment '分组',
    `labels`      varchar(64) comment '标签',
    `level`       varchar(64) comment '级别',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (uuid),
    key           idx_name (`name`)
) engine = innodb comment = 'alert message';

drop table if exists carp_alert_quota;
create table carp_alert_quota
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `name`        varchar(64) not null comment '名称',
    `level`       varchar(64) comment '级别',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (uuid),
    key           idx_name (`name`)
) engine = innodb comment = 'alert quota';

drop table if exists carp_alert_rule;
create table carp_alert_rule
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `name`        varchar(64) not null comment '名称',
    `level`       varchar(64) comment '级别',
    `promql`      varchar(64),
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (uuid),
    key           idx_name (`name`)
) engine = innodb comment = 'alert rule';