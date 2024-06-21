create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists sec_user;
create table sec_user
(
    id          bigint      not null auto_increment comment '自增主键',
    type        varchar(4)  not null comment '用户类型。系统角色，用户定义',
    user_name   varchar(32) not null comment '用户名',
    nick_name   varchar(50) comment '昵称',
    avatar      varchar(255) comment '头像',
    email       varchar(128) comment '邮箱',
    phone       varchar(16) comment '手机',
    `password`  varchar(64) not null comment '密码',
    gender      varchar(4)  not null default '0' comment '性别',
    address     text comment '地址',
    summary     text comment '用户简介',
    `order`     int         not null default 0 comment '排序',
    `status`    varchar(4)  not null comment '用户状态。启用，禁用',
    remark      varchar(255) comment '备注',
    creator     varchar(32) comment '创建人',
    create_time datetime    not null default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (user_name),
    key (update_time)
) engine = innodb comment = 'security user';

insert into sec_user (id, type, user_name, nick_name, avatar, email, phone, password,
                      gender, address, summary, `order`, `status`, remark, creator, editor)
values (1, '0', 'sys_admin', '超级管理员', null, 'test@admin.com', null,
        '$2a$10$QX2DBrOBGLuhEmboliW66ulvQ5Hiy9GCdhsqqs1HgJVgslYhZEC6q',
        '0', null, null, 0, '0', null, 'sys', 'sys');

/* 角色表 */
drop table if exists sec_role;
create table sec_role
(
    id          bigint      not null auto_increment comment '自增主键',
    type        varchar(4)  not null comment '角色类型。系统角色，用户定义',
    `code`      varchar(32) not null comment '角色编码',
    `name`      varchar(64) not null comment '角色名称',
    `order`     int         not null default 0 comment '排序',
    `status`    varchar(4)  not null comment '角色状态',
    remark      varchar(255) comment '备注',
    creator     varchar(32) comment '创建人',
    create_time datetime    not null default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (code),
    key (update_time)
) engine = innodb comment = '角色表';

insert into sec_role (id, type, `code`, `name`, `order`, `status`, `creator`, `editor`)
values (1, '01', 'sys_super_admin', '超级系统管理员', 0, '1', 'sys', 'sys');
insert into sec_role (id, type, `code`, `name`, `order`, `status`, `creator`, `editor`)
values (2, '01', 'sys_admin', '系统管理员', 1, '1', 'sys', 'sys');
insert into sec_role (id, type, `code`, `name`, `order`, `status`, `creator`, `editor`)
values (3, '01', 'sys_normal', '普通用户', 2, '1', 'sys', 'sys');

drop table if exists sec_resource_web;
create table sec_resource_web
(
    id          bigint       not null auto_increment comment '自增主键',
    type        varchar(128) not null comment '资源类型。导航，菜单，页面，按钮',
    pid         bigint       not null default '0' comment '上级资源id',
    name        varchar(128) comment '名称',
    menu_name   varchar(128) comment '前端目录',
    path        varchar(128) comment '前端路由路径',
    redirect    varchar(128) comment '前端重定向路径',
    layout      tinyint comment '前端全局布局显示。只在一级生效',
    icon        varchar(128) comment '前端 icon',
    component   varchar(255) comment '前端组件',
    remark      varchar(256) comment '备注',
    creator     varchar(32) comment '创建人',
    create_time datetime     not null default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (type, pid, path)
) engine = innodb comment = '资源-web';

INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (1, '2', 0, '登陆', 'login', '/login', NULL, 0, NULL, './User/Login', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (2, '2', 0, '注册', 'register', '/user/register', NULL, 0, NULL, './User/Register', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (3, '0', 0, '工作台', 'studio', '/studio', NULL, 1, 'CodeSandboxOutlined', '', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (4, '2', 3, '数据看板', 'databoard', '/studio/databoard', NULL, 1, 'DashboardOutlined', './Studio/DataBoard', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (5, '0', 0, '项目', 'project', '/project', NULL, 1, 'ProjectOutlined', '', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (6, '2', 5, '项目列表', NULL, '/project', NULL, 1, NULL, './Project', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (7, '0', 0, '资源', 'resource', '/resource', NULL, 1, 'FileTextOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (8, '2', 7, '公共Jar', 'jar', '/resource/jar', NULL, 1, NULL, './Resource/Jar', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (9, '2', 7, 'Flink Release', 'flinkRelease', '/resource/flink-release', NULL, 1, NULL, './Resource/FlinkRelease', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (10, '2', 7, 'SeaTunnel Release', 'seatunnelRelease', '/resource/seatunnel-release', NULL, 1, NULL, './Resource/SeaTunnelRelease', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (11, '2', 10, 'SeaTunnel Connector', NULL, '/resource/seatunnel-release/connectors', NULL, 1, NULL, './Resource/SeaTunnelConnector', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (12, '2', 7, 'Kerberos', 'kerberos', '/resource/kerberos', NULL, 1, NULL, './Resource/Kerberos', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (13, '2', 7, 'Cluster Credential', 'clusterCredential', '/resource/cluster-credential', NULL, 1, NULL, './Resource/ClusterCredential', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (14, '0', 0, '元数据', 'metadata', '/metadata', NULL, 1, 'CompassOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (15, '0', 0, '数据标准', 'stdata', '/stdata', NULL, 1, 'DatabaseOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (16, '2', 15, '业务系统', 'system', '/stdata/system', NULL, 1, 'GroupOutlined', './Stdata/System', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (17, '2', 15, '数据元', 'dataElement', '/stdata/dataElement', NULL, 1, 'HddOutlined', './Stdata/DataElement', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (18, '2', 15, '参考数据', 'refdata', '/stdata/refdata', NULL, 1, 'ProfileOutlined', './Stdata/RefData', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (19, '2', 15, '数据映射', 'refdataMap', '/stdata/refdataMap', NULL, 1, 'OneToOneOutlined', './Stdata/RefDataMap', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (20, '2', 18, '参考数据编码', NULL, '/stdata/refdata/value', NULL, 1, NULL, './Stdata/RefData/Value', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (21, '0', 0, '系统管理', 'admin', '/admin', NULL, 1, 'SettingOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (22, '2', 21, '部门管理', 'dept', '/admin/dept', NULL, 1, 'ApartmentOutlined', './Admin/Dept', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (23, '2', 21, '角色管理', 'role', '/admin/role', NULL, 1, 'SafetyOutlined', './Admin/Role', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (24, '2', 21, '用户管理', 'user', '/admin/user', NULL, 1, 'UserOutlined', './Admin/User', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (25, '2', 21, 'Web资源', 'resource.web', '/admin/resource/web', NULL, 1, 'TeamOutlined', './Admin/Resource/Web', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (26, '2', 21, '系统任务', 'quartz', '/admin/workflow/quartz', NULL, 1, 'FieldTimeOutlined', './Workflow/Definition/Quartz', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (27, '2', 26, '子任务', NULL, '/admin/workflow/quartz/task', NULL, 1, NULL, './Workflow/Definition/Quartz/Task', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (28, '2', 26, '调度', NULL, '/admin/workflow/schedule', NULL, 1, NULL, './Workflow/Schedule', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (29, '2', 21, '数据字典', 'dict', '/admin/dict', NULL, 1, 'TableOutlined', './Admin/Dict', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (30, '2', 21, '系统设置', 'setting', '/admin/setting', NULL, 1, 'SettingOutlined', './Admin/Setting', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (31, '1', 14, '数据源', 'data-source', '/metadata/data-source', NULL, 1, 'FundViewOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (32, '1', 14, 'Gravitino', 'gravitino', '/metadata/gravitino', NULL, 1, 'GatewayOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (33, '2', 31, '数据源信息', 'info', '/metadata/data-source/info', NULL, 1, 'HddOutlined', './Metadata/DataSource/Info', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (34, '2', 31, '数据源创建', NULL, '/metadata/data-source/info/stepForms', NULL, 0, NULL, './Metadata/DataSource/Info/StepForms', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (35, '2', 32, 'Catalog', 'catalog', '/metadata/gravitino/catelog', NULL, 1, 'MergeCellsOutlined', './Metadata/Gravitino/Catalog', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (36, '1', 42, '引擎管理', 'project.engine', '/workspace/engine', NULL, 1, 'OneToOneOutlined', '', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (37, '1', 42, '数据集成', 'project.data-integration', '/workspace/data-integration', NULL, 1, 'ThunderboltOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (38, '1', 42, '数据开发', 'project.data-develop', '/workspace/data-develop', NULL, 1, 'ConsoleSqlOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (39, '1', 42, 'DAG 调度', 'project.dag-scheduler', '/workspace/dag-scheduler', NULL, 1, 'ScheduleOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (40, '1', 42, '数据服务', 'project.data-service', '/workspace/data-service', NULL, 1, 'ReadOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (41, '1', 42, '运维中心', 'project.operation', '/workspace/operation', NULL, 1, 'SolutionOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (42, '1', 0, '工作空间', NULL, '/workspace', NULL, 1, NULL, NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (43, '1', 36, '数据湖', 'lake', '/workspace/engine/lake', NULL, 1, 'RestOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (44, '1', 36, 'OLAP引擎', 'olap', '/workspace/engine/olap', NULL, 1, 'RocketOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (45, '1', 36, '计算引擎', 'compute', '/workspace/engine/compute', NULL, 1, 'SisternodeOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (46, '2', 43, 'Iceberg', 'iceberg', '/workspace/engine/lake/iceberg', NULL, 1, 'RobotOutlined', './Project/Workspace/Engine/Lake/Iceberg', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (47, '2', 43, 'Paimon', 'paimon', '/workspace/engine/lake/paimon', NULL, 1, 'RotateLeftOutlined', './Project/Workspace/Engine/Lake/Paimon', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (48, '1', 44, 'Doris', 'doris', '/workspace/engine/olap/doris', NULL, 1, 'ApartmentOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (49, '2', 44, 'StarRocks', 'starrocks', '/workspace/engine/olap/starrocks', NULL, 1, 'ApartmentOutlined', './Project/Workspace/Engine/OLAP/StarRocks', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (50, '2', 48, '部署模板', 'template', '/workspace/engine/olap/doris/template', NULL, 1, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorTemplate', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (51, '2', 48, '部署模板创建', NULL, '/workspace/engine/olap/doris/template/steps', NULL, 0, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorTemplate/Steps', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (52, '2', 48, '部署模板详情', NULL, '/workspace/engine/olap/doris/template/detail', NULL, 0, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorTemplate/Detail', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (53, '2', 48, '部署实例', 'instance', '/workspace/engine/olap/doris/instance', NULL, 1, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorInstance', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (54, '2', 48, '部署实例创建', NULL, '/workspace/engine/olap/doris/instance/steps', NULL, 0, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorInstance/Steps', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (55, '2', 48, '部署实例详情', NULL, '/workspace/engine/olap/doris/instance/detail', NULL, 0, NULL, './Project/Workspace/Engine/OLAP/Doris/OperatorInstance/Detail', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (56, '1', 45, 'Flink', 'flink', '/workspace/engine/compute/flink', NULL, 1, 'ApartmentOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (57, '2', 56, '部署模板', 'template', '/workspace/engine/compute/flink/template', NULL, 1, NULL, './Project/Workspace/Engine/Compute/Flink/Template', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (58, '2', 56, '部署模板-创建', NULL, '/workspace/engine/compute/flink/template/steps/new', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Template/Steps/New', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (59, '2', 56, '部署模板-更新', NULL, '/workspace/engine/compute/flink/template/steps/update', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Template/Steps/Update', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (60, '2', 56, 'Session 集群', 'session-cluster', '/workspace/engine/compute/flink/session-cluster', NULL, 1, NULL, './Project/Workspace/Engine/Compute/Flink/SessionCluster', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (61, '2', 56, 'Session 集群-创建', NULL, '/workspace/engine/compute/flink/session-cluster/steps/new', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/SessionCluster/Steps/New', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (62, '2', 56, 'Session 集群-更新', NULL, '/workspace/engine/compute/flink/session-cluster/steps/update', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/SessionCluster/Steps/Update', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (63, '2', 56, 'Session 集群-详情', NULL, '/workspace/engine/compute/flink/session-cluster/detail', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/SessionCluster/Detail', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (64, '2', 56, 'Deployment', 'deployment', '/workspace/engine/compute/flink/deployment', NULL, 1, NULL, './Project/Workspace/Engine/Compute/Flink/Deployment', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (65, '2', 56, 'Deployment-创建', NULL, '/workspace/engine/compute/flink/deployment/steps/new', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Deployment/Steps/New', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (66, '2', 56, 'Deployment-更新', '', '/workspace/engine/compute/flink/deployment/steps/update', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Deployment/Steps/Update', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (67, '2', 56, 'Deployment-详情', NULL, '/workspace/engine/compute/flink/deployment/detail', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Deployment/Detail', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (68, '2', 37, 'SeaTunnel', 'seatunnel', '/workspace/data-integration/seatunnel', NULL, 1, 'SafetyOutlined', './Project/Workspace/DataIntegration/SeaTunnel', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (69, '2', 37, 'SeaTunnel-DAG', NULL, '/workspace/data-integration/seatunnel/dag', NULL, 0, NULL, './Project/Workspace/DataIntegration/SeaTunnel/Dag', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (70, '2', 37, 'Flink CDC', 'flink-cdc', '/workspace/data-integration/flink-cdc', NULL, 1, 'FieldNumberOutlined', './Project/Workspace/DataIntegration/FlinkCDC', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (71, '2', 37, 'Flink CDC-新建', NULL, '/workspace/data-integration/flink-cdc/steps/new', NULL, 0, NULL, './Project/Workspace/DataIntegration/FlinkCDC/Steps/New', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (72, '2', 38, 'Flink Jar', 'flink-jar', '/workspace/data-develop/flink/jar', NULL, 1, 'SendOutlined', './Project/Workspace/DataDevelop/Flink/Jar', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (73, '2', 38, 'Flink Jar-历史', NULL, '/workspace/data-develop/flink/jar/history', NULL, 0, NULL, './Project/Workspace/DataDevelop/Flink/Jar/History', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (74, '2', 38, 'Flink SQL', 'flink-sql', '/workspace/data-develop/flink/sql', NULL, 1, 'TransactionOutlined', './Project/Workspace/DataDevelop/Flink/SQL', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (75, '2', 38, 'Flink SQL-编辑器', NULL, '/workspace/data-develop/flink/sql/editor', NULL, 0, NULL, './Project/Workspace/DataDevelop/Flink/SQL/CodeEditor', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (76, '2', 40, '接口配置', 'config', '/workspace/data-service/config', NULL, 1, NULL, './Project/Workspace/DataService/Config', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (77, '2', 40, '接口配置-创建', NULL, '/workspace/data-service/config/steps', NULL, 0, NULL, './Project/Workspace/DataService/Config/Steps', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (78, '2', 41, 'Flink任务', 'flink', '/workspace/operation/compute/flink/job', NULL, 1, NULL, './Project/Workspace/Engine/Compute/Flink/Job', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (79, '2', 41, 'Flink任务-详情', NULL, '/workspace/operation/compute/flink/job/detail', NULL, 0, NULL, './Project/Workspace/Engine/Compute/Flink/Job/Detail', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (80, '0', 0, 'OAM', 'oam', '/oam', NULL, 1, 'WalletOutlined', NULL, NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (81, '2', 80, 'XDefinition', 'definition', '/oam/definitin', NULL, 1, 'SignatureOutlined', './OAM/Definition', NULL, 'sys', 'sys');
INSERT INTO `sec_resource_web`(`id`, `type`, `pid`, `name`, `menu_name`, `path`, `redirect`, `layout`, `icon`, `component`, `remark`, `creator`, `editor`) VALUES (82, '2', 37, 'Flink CDC-更新', NULL, '/workspace/data-integration/flink-cdc/steps/update', NULL, 0, NULL, './Project/Workspace/DataIntegration/FlinkCDC/Steps/Update', NULL, 'sys', 'sys');

drop table if exists sec_resource_web_role;
create table sec_resource_web_role
(
    id              bigint   not null auto_increment comment '自增主键',
    resource_web_id bigint   not null,
    role_id         bigint   not null,
    creator         varchar(32) comment '创建人',
    create_time     datetime not null default current_timestamp comment '创建时间',
    editor          varchar(32) comment '修改人',
    update_time     datetime not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (resource_web_id, role_id)
) engine = innodb comment = '资源-web与角色关联表';

insert into sec_resource_web_role (resource_web_id, role_id, creator, editor)
select t1.id as resource_web_id,
       t2.id as role_id,
       'sys' as creator,
       'sys' as editor
from sec_resource_web t1,
     sec_role t2
where t2.`code` in ('sys_admin', 'sys_super_admin');

/* 用户角色关联表 */
drop table if exists sec_user_role;
create table sec_user_role
(
    id          bigint   not null auto_increment comment '自增主键',
    user_id     bigint   not null comment '用户id',
    role_id     bigint   not null comment '角色id',
    creator     varchar(32) comment '创建人',
    create_time datetime not null default current_timestamp comment '创建时间',
    editor      varchar(32) comment '修改人',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (user_id, role_id),
    key (update_time)
) engine = innodb comment = 'security user-role relation';

insert into sec_user_role (id, user_id, role_id, creator, editor)
values (1, 1, 1, 'sys', 'sys');