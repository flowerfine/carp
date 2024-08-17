create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_k8s_cluster;
create table carp_k8s_cluster
(
    `id`             bigint      not null auto_increment comment '自增主键',
    `type`           varchar(4)  not null comment 'type',
    `uuid`           varchar(64) not null comment 'uuid',
    `name`           varchar(64) not null comment '名称',
    `spec`           text comment 'spec',
    `status`         text comment 'status',
    `cluster_status` varchar(4) comment '0: disabled, 1: enabled',
    `remark`         varchar(255) comment '备注',
    `creator`        varchar(32) comment '创建人',
    `create_time`    datetime    not null default current_timestamp comment '创建时间',
    `editor`         varchar(32) comment '修改人',
    `update_time`    datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (uuid),
    key              idx_name (`name`)
) engine = innodb comment = 'k8s cluster';