create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_file_import;
create table `carp_file_import`
(
    `id`           bigint       not null auto_increment comment '自增主键',
    `namespace`    varchar(64)  not null default 'default' comment '命名空间',
    `biz_code`     varchar(64)  not null comment '业务编码',
    `sub_biz_code` varchar(64) comment '子业务编码',
    `type`         varchar(64)  not null comment '导入类型。excel, csv, json 等',
    `execute_type` varchar(64)  not null comment '执行类型',
    `priority`     int          not null default 10 comment '优先级',
    `file_url`     varchar(256) not null comment '文件url',
    `file_name`    varchar(256) comment '文件名',
    `status`       varchar(64) comment '状态',
    `message`      varchar(128) comment '信息',
    `retry_times`  int comment '重试次数',
    `remark`       varchar(256),
    `creator`      varchar(32) comment '创建人',
    `create_time`  datetime     not null default current_timestamp comment '创建时间',
    `editor`       varchar(32) comment '修改人',
    `update_time`  datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`),
     key `idx_file_name` (`namespace`, `file_name`)
) engine = innodb comment='文件上传';