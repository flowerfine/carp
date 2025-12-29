create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_cep_rule;
create table carp_cep_rule
(
    `id`            bigint      not null auto_increment comment '自增主键',
    `namespace`     varchar(64) not null,
    `name`          varchar(64) not null comment '名称',
    `uuid`          varchar(64) not null,
    `type`          varchar(32) not null,
    `nodes`         text,
    `edges`         text,
    `skip_strategy` varchar(32) not null,
    `window`        varchar(128),
    `function`      varchar(128),
    `remark`        text,
    `creator`       varchar(32) comment '创建人',
    `create_time`   datetime    not null default current_timestamp comment '创建时间',
    `editor`        varchar(32) comment '修改人',
    `update_time`   datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key             idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep rule';

drop table if exists carp_cep_function;
create table carp_cep_function
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `type`        varchar(32) not null comment '类型',
    `name`        varchar(64) not null comment '名称',
    `short_name`  varchar(64) comment '短的描述',
    `uuid`        varchar(64) not null,
    `remark`      text comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key           idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep function';

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (1, 'default', '常用函数', 'add', 'add', '1',
        '用于精确数值加法的函数，使用BigDecimal进行计算以避免浮点数精度问题。', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (2, 'default', '常用函数', 'abs', 'abs', '2',
        '求绝对值', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (3, 'default', '常用函数', 'result_compare', 'result_compare', '3',
        '历史结果对比', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (4, 'default', '常用函数', 'his_result_compare', 'his_result_compare', '4',
        null, 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (5, 'default', '常用函数', 'isMobileNumber', 'isMobileNumber', '5',
        '15000530813', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (6, 'default', '常用函数', 'createJsonOBJ', 'createJsonOBJ', '6',
        '传入动态数据 拼接为一个对象数据，单数为键 双数为值，例如：craeteJsonOBJ("a",1,"b",2) 执行后为 {"a":1,"b":2}，若值为单数，则最后一个键对应的值为null',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (7, 'default', '常用函数', 'LIMS_Result_Compare', 'LIMS_Result_Compare', '7',
        null, 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (8, 'default', '常用函数', 'stringToNumberList', 'stringToNumberList', '8',
        '将以逗号分隔的字符串拆分为List<Number>', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (9, 'default', '常用函数', 'sumList', 'sumList', '9',
        '传入list数组求和，null返回0', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (10, 'default', '常用函数', 'Calculate', 'Calculate', '10',
        '', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (11, 'default', '常用函数', 'GET_TIM_BY_ID', 'GET_TIM_BY_ID', '11',
        '某两个表都有同一个字段，通过第一张表的该字段作为入参，查询另一张表中的其他需要的字段数据', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (12, 'default', '常用函数', 'MINUTEOFFSET', 'MINUTEOFFSET', '12',
        '对输入的时间字符串增加或减少指定分钟数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (13, 'default', '常用函数', 'TO_NEGATIVE', 'TO_NEGATIVE', '13',
        '将数字转换为负数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (14, 'default', '常用函数', 'TO_NEGATIVE_STR', 'TO_NEGATIVE_STR', '14',
        '将字符串转换为负数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (15, 'default', '常用函数', 'daysBetween', 'daysBetween', '15',
        '计算两个日期相差天数，日期格式为yyyy-MM-dd', 'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (16, 'default', '加解密函数', 'Base64Tools', 'Base64Tools', '16',
        'Base64加解密 Base64Tools(加解密字符串, 是否为加密)', 'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (17, 'default', '脱敏函数', 'MASKING_NAME', 'MASKING_NAME', '17',
        '姓名脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_NAME("苏东坡"); 返回 苏**', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (18, 'default', '脱敏函数', 'MASKING_IDCARD', null, '18',
        '身份证脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_IDCARD("51239876432789002X"); 返回 5123************2X',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (19, 'default', '脱敏函数', 'MASKING_BANKCARD', null, '19',
        '银行卡号脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_BANKCARD("9659980368535971298"); 返回 9659 **** **** *** 1298',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (20, 'default', '脱敏函数', 'MASKING_EMAIL', null, '20',
        '邮箱脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_EMAIL("1348888166@qq.com"); 返回 1*********@qq.com',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (21, 'default', '脱敏函数', 'MASKING_PHONE', null, '21',
        '手机号脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_PHONE("13488888166");返回 134****8166',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (22, 'default', '脱敏函数', 'MASKING_ADDRESS', null, '22',
        '地址脱敏工具，第一位参数为需要脱敏的数据，第二位为敏感信息长度，返回脱敏后的数据例：MASKING_ADDRESS("重庆市渝北区龙睛路",3); 返回 重庆市渝北区***',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (23, 'default', '脱敏函数', 'MASKING_CENTER', null, '23',
        '脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_CENTER("苏东坡"); 返回 苏*坡', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (24, 'default', '脱敏函数', 'MASKING_ENTRY_CENTER', null, '24',
        '脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_CENTER("测试企业名称"); 返回 测试*业名称',
        'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (25, 'default', '对象函数', 'SETVALUE', null, '25',
        '修改对象数据中指定属性的值；第二个参数为要修改的属性名，第三个参数为修改的值。例1：SETVALUE(obj,"id","20222156")注意：若属性不存在则，在数据中新增属性及值。',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (26, 'default', '对象函数', 'COPYPROPER', null, '26',
        '拷贝第一个对象的属性值到第二个对象。（仅拷贝属性名相同的值）', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (27, 'default', '对象函数', 'GETVALUE', null, '27',
        '获取对象数据中指定属性的值。例1：GETVALUE(date1,''danWei'')";', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (28, 'default', '对象函数', 'TOJSONOBJ', null, '28',
        '将json字符串转换为对象。例1：TOJSONOBJ("{"danWei":"444","zongJia":555}")', 'sys', 'sys');

drop table if exists carp_cep_function_detail;
create table carp_cep_function_detail
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `function_id` bigint      not null comment '函数 ID',
    `body`        text comment '函数体',
    `result_type` varchar(32) not null comment '结果类型',
    `arg_infos`   text comment '入参列表',
    `remark`      text comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_function (`namespace`, `function_id`)
) engine = innodb comment = 'cep function detail';
