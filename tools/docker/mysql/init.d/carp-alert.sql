create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_alert_prometheus;
create table carp_alert_prometheus
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `namespace`        varchar(64) not null,
    `name`             varchar(64) not null comment '名称',
    `uuid`             varchar(64) not null,
    `type`             varchar(32) not null,
    `url`              varchar(128),
    `config_file_path` varchar(256),
    `alert_rule_path`  varchar(256),
    `is_auth_enabled`  varchar(4),
    `username`         varchar(32),
    `password`         varchar(32),
    `remark`           text,
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key                idx_name (`namespace`, `name`)
) engine = innodb comment = 'alert Prometheus';

INSERT INTO `carp_alert_prometheus` (`id`, `namespace`, `name`, `uuid`, `type`, `url`, `config_file_path`,
                                     `alert_rule_path`, `is_auth_enabled`, `username`, `password`, `remark`, `creator`,
                                     `editor`)
VALUES (1, 'default', 'docker', '6d931285-4703-0178-0ec1-0d54eb3567fd', 'docker', 'http://localhost:9090',
        '/etc/prometheus/prometheus.yml', '/etc/prometheus/rules.yml', '0', NULL, NULL, NULL, 'sys', 'sys');

drop table if exists carp_alert_alertmanager;
create table carp_alert_alertmanager
(
    `id`              bigint      not null auto_increment comment '自增主键',
    `namespace`       varchar(64) not null,
    `name`            varchar(64) not null,
    `uuid`            varchar(64) not null,
    `type`            varchar(32) not null,
    `url`             varchar(128),
    `is_auth_enabled` varchar(4),
    `username`        varchar(32),
    `password`        varchar(32),
    `remark`          text,
    `creator`         varchar(32) comment '创建人',
    `create_time`     datetime    not null default current_timestamp comment '创建时间',
    `editor`          varchar(32) comment '修改人',
    `update_time`     datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key               idx_name (`namespace`, `name`)
) engine = innodb comment = 'alert AlertManager';

INSERT INTO `carp_alert_alertmanager` (`id`, `namespace`, `name`, `uuid`, `type`, `url`, `is_auth_enabled`, `username`,
                                       `password`, `remark`, `creator`, `editor`)
VALUES (1, 'default', 'docker', '768ce00e-13dd-d812-1086-8342ba2621de', 'docker', 'http:localhost:9093', '0', NULL,
        NULL, NULL, 'sys', 'sys');

-- 在大部分基于 prometheus + alertmanager 的告警体系中，往往会从 label 或 instance 中提取关联的实例信息
-- 如从 instance 提取实例ip，从 label 提取实例 uuid，确保能关联告警和实例
drop table if exists carp_alert_log;
create table carp_alert_log
(
    `id`                 bigint      not null auto_increment comment '自增主键',
    `version`            varchar(4)  not null comment '消息版本号',
    `group_key`          varchar(64) not null comment '分组key',
    `group_labels`       varchar(255) comment '分组labels',
    `receiver`           varchar(64) comment '接收者',
    `common_labels`      varchar(255) comment '通用labels',
    `common_annotations` varchar(255) comment '通用annotations',
    `fingerprint`        varchar(64) comment '告警消息fingerprint',
    `status`             varchar(8) comment '告警消息状态',
    `labels`             text comment '告警消息labels',
    `annotations`        text comment '告警消息annotations',
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