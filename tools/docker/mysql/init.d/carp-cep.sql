create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_cep_rule;
create table carp_cep_rule (
  `id` bigint not null auto_increment comment '自增主键',
  `namespace` varchar(64) not null,
  `name` varchar(64) not null comment '名称',
  `uuid` varchar(64) not null,
  `type` varchar(32) not null,
  `nodes` text,
  `edges` text,
  `skip_strategy` varchar(32) not null,
  `window` varchar(128),
  `function` varchar(128),
  `remark` text,
  `creator` varchar(32) comment '创建人',
  `create_time` datetime not null default current_timestamp comment '创建时间',
  `editor` varchar(32) comment '修改人',
  `update_time` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
  primary key (id),
  unique key uniq_uuid (`namespace`, `uuid`),
  key idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep rule';