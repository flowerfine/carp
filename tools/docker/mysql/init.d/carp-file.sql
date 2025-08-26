create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_file_folder;
create table carp_file_folder
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `namespace`        varchar(64) not null,
    `pid`             bigint not null,
    `name`             varchar(64) not null comment '名称',
    `remark`           text,
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key                idx_name (`namespace`, `name`)
) engine = innodb comment = 'file 文件夹';

drop table if exists carp_file;
create table carp_file
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `namespace`        varchar(64) not null,
    `folder_id` bigint(20),
    `name`             varchar(64) not null comment '名称',
    `type`             varchar(64) not null comment '类型',
    `path`             varchar(64) comment '存储路径',
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key                idx_name (`namespace`, `name`)
) engine = innodb comment = 'file';

drop table if exists carp_file_extra;
create table carp_file_extra
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `namespace`        varchar(64) not null,
    `file_id` bigint(20) not null,
    `owner` varchar(64) not null,
    `business_value` longtext COMMENT '业务价值',
    `remark`           text,
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key                idx_name (`namespace`, `name`)
) engine = innodb comment = 'file extra';

drop table if exists carp_file_version;
create table carp_file_version
(
    `id`               bigint      not null auto_increment comment '自增主键',
    `namespace`        varchar(64) not null,
    `file_id` bigint(20) not null,
    `version` varchar(64) not null,
    `path` varchar(64) not null,
    `remark`           text,
    `creator`          varchar(32) comment '创建人',
    `create_time`      datetime    not null default current_timestamp comment '创建时间',
    `editor`           varchar(32) comment '修改人',
    `update_time`      datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    key                idx_name (`namespace`, `name`)
) engine = innodb comment = 'file version';