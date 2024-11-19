create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_job_sync_offset;
create table `carp_job_sync_offset`
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `group`            varchar(64) not null comment '任务分组',
    `job`              varchar(64) not null comment '任务',
    `sub_job`          varchar(64) not null comment '子任务',
    `account`          varchar(128) comment '账号',
    `sub_account`      varchar(128) comment '子账号',
    `last_sync_offset` varchar(255) comment '上一次位点',
    `sync_offset`      varchar(255) comment '位点',
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`),
    unique key `uniq_job_account` (`group`,`job`,`sub_job`,`account`,`sub_account`)
) engine = innodb comment='任务 同步位点';