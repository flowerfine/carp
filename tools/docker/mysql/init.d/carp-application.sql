create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_oam_app;
create table carp_oam_app
(
    `id`          bigint      not null auto_increment comment 'id',
    `type`        varchar(4)  not null comment 'type',
    `uuid`        varchar(64) not null comment 'uuid',
    `name`        varchar(64) not null comment 'name',
    `status`      varchar(4) comment 'status',
    `remark`      varchar(255) comment 'remark',
    `creator`     varchar(32) comment 'creator',
    `create_time` datetime    not null default current_timestamp comment 'create time',
    `editor`      varchar(32) comment 'editor',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (id),
    unique key (uuid),
    key           idx_name (`name`)
) engine = innodb comment = 'oam app';

drop table if exists carp_oam_app_deploy_config;
create table carp_oam_app_deploy_config
(
    `id`          bigint      not null auto_increment comment 'id',
    `app_id`       bigint  not null comment 'app id',
    `creator`     varchar(32) comment 'creator',
    `create_time` datetime    not null default current_timestamp comment 'create time',
    `editor`      varchar(32) comment 'editor',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (id),
    unique key (app_id)
) engine = innodb comment = 'oam app deploy config';