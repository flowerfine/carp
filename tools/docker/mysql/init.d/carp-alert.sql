create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_alert_log;
create table carp_alert_log
(
    `id`                 bigint      not null auto_increment comment '自增主键',
    `version`            varchar(4)  not null comment '消息版本号',
    `group_key`          varchar(64) not null comment '分组key',
    `group_labels`       varchar(64) comment '分组labels',
    `receiver`           varchar(64) comment '接收者',
    `common_labels`      varchar(64) comment '通用labels',
    `common_annotations` varchar(64) comment '通用annotations',
    `fingerprint`        varchar(64) comment '告警消息fingerprint',
    `status`             varchar(8) comment '告警消息状态',
    `labels`             varchar(255) comment '告警消息labels',
    `annotations`        varchar(255) comment '告警消息annotations',
    `starts_at`          bigint comment '告警时间',
    `ends_at`            bigint comment '恢复时间',
    `creator`            varchar(32) comment '创建人',
    `create_time`        datetime    not null default current_timestamp comment '创建时间',
    `editor`             varchar(32) comment '修改人',
    `update_time`        datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (fingerprint, starts_at)
) engine = innodb comment = 'alert log';

drop table if exists carp_alert_message;
create table carp_alert_message
(
    `id`          bigint     not null auto_increment comment '自增主键',
    `alertname`   varchar(4) not null comment '告警名称',
    `fingerprint` varchar(64) comment '告警消息fingerprint',
    `status`      varchar(8) comment '告警消息状态',
    `labels`      varchar(8) comment '告警消息labels',
    `annotations` varchar(8) comment '告警消息annotations',
    `starts_at`   bigint comment '告警时间',
    `ends_at`     bigint comment '恢复时间',
    `count`       bigint comment '告警次数',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime   not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime   not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (fingerprint),
    key           idx_name (`alertname`)
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
    key           idx_name (`name`)
) engine = innodb comment = 'alert rule';