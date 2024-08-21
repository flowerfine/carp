create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

DROP TABLE IF EXISTS `schedule_job_group`;
CREATE TABLE `schedule_job_group`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `namespace`   varchar(64) NOT NULL COMMENT '命名空间',
    `name`        varchar(64) NOT NULL COMMENT '分组名称',
    `remark`      varchar(256) COMMENT 'remark',
    `creator`     varchar(32)          DEFAULT NULL COMMENT 'creator',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`      varchar(32)          DEFAULT NULL COMMENT 'editor',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_name` (`namespace`,`name`)
) ENGINE=InnoDB COMMENT='schedule job group';

DROP TABLE IF EXISTS `schedule_job_config`;
CREATE TABLE `schedule_job_config`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_group_id` bigint(20) NOT NULL COMMENT '任务分组 id',
    `type`         varchar(8)  NOT NULL COMMENT '任务类型',
    `name`         varchar(64) NOT NULL COMMENT '任务名称',
    `handler`      varchar(64) COMMENT '任务处理器',
    `remark`       varchar(255) COMMENT 'remark',
    `creator`      varchar(32)          DEFAULT NULL COMMENT 'creator',
    `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`       varchar(32)          DEFAULT NULL COMMENT 'editor',
    `update_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_group_name` (`job_group_id`,`name`)
) ENGINE=InnoDB COMMENT='schedule job config';

DROP TABLE IF EXISTS `schedule_job_instance`;
CREATE TABLE `schedule_job_instance`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_config_id` bigint(20) NOT NULL COMMENT '任务配置id',
    `name`          varchar(255) NOT NULL COMMENT '实例名称',
    `cron`          varchar(128) COMMENT 'CRON表达式',
    `params`        varchar(255) COMMENT '参数',
    `timeout`       bigint(20) COMMENT '超时时间（毫秒）',
    `status`        varchar(4)   NOT NULL COMMENT '状态',
    `remark`        varchar(255) COMMENT 'remark',
    `creator`       varchar(32)           DEFAULT NULL COMMENT 'creator',
    `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `editor`        varchar(32)           DEFAULT NULL COMMENT 'editor',
    `update_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='schedule job instance';

