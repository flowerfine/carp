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
    `common_labels`      varchar(512) comment '通用labels',
    `common_annotations` text comment '通用annotations',
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
    key                  idx_fingerprint (fingerprint),
    key                  idx_starts_at (starts_at)
) engine = innodb comment = 'alert log';

drop table if exists carp_alert_message;
create table carp_alert_message
(
    `id`            bigint      not null auto_increment comment '自增主键',
    `namespace`     varchar(64) not null comment '命名空间',
    `rule_id`       varchar(64) not null comment '规则id',
    `resource_type` varchar(64) not null comment '资源类型',
    `resource_id`   varchar(64) not null comment '资源id',
    `fingerprint`   varchar(64) comment '告警消息fingerprint',
    `status`        varchar(8) comment '告警消息状态',
    `start_time`    datetime comment '告警时间',
    `end_time`      datetime comment '恢复时间',
    `count`         bigint comment '告警次数',
    `summary`       varchar(512),
    `description`   text,
    `source`        varchar(32),
    `creator`       varchar(32) comment '创建人',
    `create_time`   datetime    not null default current_timestamp comment '创建时间',
    `editor`        varchar(32) comment '修改人',
    `update_time`   datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_fingerprint (`namespace`, `rule_id`, `fingerprint`),
    key             idx_resource (`namespace`, `resource_type`, `resource_id`),
    key             idx_rule_resource (`namespace`, `rule_id`, `resource_type`, `resource_id`)
) engine = innodb comment = 'alert message';

drop table if exists carp_alert_rule;
create table carp_alert_rule
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `name`        varchar(64) not null comment '名称',
    `uuid`        varchar(64) not null,
    `is_enabled`  varchar(4),
    `level`       varchar(64) comment '级别',
    `promql`      varchar(512),
    `wait_for`    varchar(8),
    `summary`     varchar(512),
    `description` text,
    `remark`      text,
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key           idx_name (`namespace`, `name`)
) engine = innodb comment = 'alert rule';

INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (1, 'default', 'WatchDog', '98b450b8-2a6f-12ed-d9b4-a0458316f86e', '1', 'none', 'vector(1)', '1m',
        'Alert heartbeat',
        'This is an alert meant to ensure that the entire alerting pipeline is functional.\n            This alert is always firing, therefore it should always be firing in Alertmanager\n            and always fire against a receiver. There are integrations with various notification\n            mechanisms that send a notification when this alert is not firing. For example the\n            \"DeadMansSnitch\" integration in PagerDuty.',
        'Watch Dog', 'sys', 'sys');
INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (2, 'default', 'NodesOffline', 'f06c2876-6542-f4e4-a148-f6d697ef302a', '0', 'warn',
        'avg_over_time(minio_cluster_nodes_offline_total{job=\"minio-job\"}[5m]) > 0', '10m',
        'Node down in MinIO deployment', 'Node(s) in cluster {{ $labels.instance }} offline for more than 10 minutes',
        'Minio Rule', 'sys', 'sys');
INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (3, 'default', 'Minio Down', 'e3d5244e-c1ac-cf61-6fb4-91069fc6fc6d', '0', 'critical',
        'up{job=~\"minio-job\"} == 0', '1m', 'MinIO cluster is down',
        'All MinIO nodes are unreachable for more than 5 minutes. This is a critical issue affecting storage availability.',
        'Minio Rule', 'sys', 'sys');
INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (4, 'default', 'Job Restart', 'd2493ea2-e1fb-ddb8-5f72-7c2400b525bf', '0', 'critical',
        'delta(flink_jobmanager_job_numRestarts{deploymentId="0bcf6ab3-124a-fe45-28a2-618fde98a993"}[1m]) >= 1.0', '1m',
        '1min 内重启次数 > 1', '{{ $value | printf \"%.2f\" }}', 'Flink Rule', 'sys', 'sys');
INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (5, 'default', 'Job Lag', 'e6357866-45ce-2452-8f0e-8eaca36ee120', '0', 'critical',
        'max(flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deploymentId="373b231a-a030-26c1-8328-0db98c0abbd2"}/1000) >= 600.0',
        '1m', '延迟大于 > 600s', '{{ $value | printf \"%.2f\" }}', 'Flink Rule', 'sys', 'sys');
INSERT INTO `carp_alert_rule` (`id`, `namespace`, `name`, `uuid`, `is_enabled`, `level`, `promql`, `wait_for`,
                               `summary`, `description`, `remark`, `creator`, `editor`)
VALUES (6, 'default', 'Waiting Thread', '6e7cf69f-4704-df78-3c8c-3ef9ce292bda', '0', 'warn',
        'jvm_threads_states_threads{application=\"data-service\", state=\"waiting\"} >  500.0', '1m',
        'Waiting Thread 数量 > 500', '{{ $value | printf \"%.2f\" }}', 'SpringBoot Rule', 'sys', 'sys');