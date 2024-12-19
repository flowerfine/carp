create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_data_service_group;
create table carp_data_service_group
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null comment '命名空间',
    `name`        varchar(64) not null comment '名称',
    `code`        varchar(64) not null comment '编码',
    `remark`      text comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) engine = innodb comment = 'data service group';

INSERT INTO `carp_data_service_group` (`id`, `namespace`, `name`, `code`, `remark`, `creator`, `editor`)
VALUES (1, 'default', 'default', 'default', NULL, 'sys', 'sys');

drop table if exists carp_data_service_config;
create table carp_data_service_config
(
    `id`                 bigint      not null auto_increment comment '自增主键',
    `group_id`           bigint      not null comment '分组id',
    `type`               varchar(32) not null comment '类型',
    `name`               varchar(64) not null comment 'name',
    `http_path`          varchar(64) not null comment 'uri path',
    `http_method`        varchar(16) comment 'http method',
    `http_content_type`  varchar(64) comment 'http content type',
    `ds_id`              bigint      not null comment 'data source id',
    `query_type`         varchar(8) comment 'query type',
    `query_script`       text comment 'query script',
    `query_page_enabled` varchar(4) comment 'query page enabled',
    `query_result_type`  varchar(32) comment 'object、array',
    `status`             varchar(4)  not null comment 'status, disabled or enabled',
    `remark`             text comment '备注',
    `creator`            varchar(32) comment '创建人',
    `create_time`        datetime    not null default current_timestamp comment '创建时间',
    `editor`             varchar(32) comment '修改人',
    `update_time`        datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id)
) engine = innodb comment = 'data service config';

INSERT INTO `carp_data_service_config` (`id`, `group_id`, `type`, `name`, `http_path`, `http_method`,
                                        `http_content_type`, `ds_id`, `query_type`, `query_script`,
                                        `query_page_enabled`, `query_result_type`, `status`, `remark`, `creator`,
                                        `editor`)
VALUES (1, 1, 'sql', 'demo', 'demo', 'GET', 'application/json', 2, 'SELECT',
        'select *\nfrom sample_data_e_commerce as a\n<where>\n    <if test=\"invoiceNo != null and invoiceNo != \'\'\">\n        AND invoice_no = #{invoiceNo}\n    </if>\n</where>',
        '0', 'array', '0', NULL, 'sys', 'sys');
