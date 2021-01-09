create schema if not exists `violet_cloud` collate utf8mb4_unicode_ci;
use violet_cloud;

-- 用户表
create table user
(
    id              bigint auto_increment comment '用户ID'
        primary key,
    username        varchar(50)             not null comment '用户名',
    password        varchar(128)            not null comment '密码',
    nickname        varchar(100) default '' null comment '昵称',
    group_id        bigint                  null comment '用户组',
    email           varchar(128)            null comment '邮箱',
    mobile          varchar(20)             null comment '联系电话',
    gender          int                     null comment '性别 0男 1女 2保密',
    is_tab          int                     null comment '是否开启tab，0关闭 1开启',
    theme           varchar(10)             null comment '主题',
    avatar          varchar(100)            null comment '头像',
    description     varchar(150)            null comment '描述',
    last_login_time datetime                null comment '最近访问时间',
    status          int                     not null comment '状态 0锁定 1有效',
    deleted         int          default 0  null comment '删除状态，0正常，1已删除',
    create_time     datetime                not null comment '创建时间',
    modify_time     datetime                null comment '修改时间'
) comment '用户表' charset = utf8;

create index user_username_idx
    on user (username);

-- 用户表数据
INSERT INTO user (id, username, password, nickname, group_id, email, mobile, gender, is_tab, theme, avatar, description, last_login_time, status, deleted, create_time, modify_time) VALUES (1, 'guqing', '$2a$10$x5ukrWFGEz1Wu4TzqJFh8espSkNXcYVpvsko44zJHSJeoJC2A/zvm', '聽見下雨的聲音', 1, '14845636141@qq.com', '12345678901', 0, 0, null, 'https://violet-cloud.oss-cn-hangzhou.aliyuncs.com/8e80f601-4001-4d6f-846f-ca0938c76bf8', '毕生所求无它，爱与自由而已', '2020-10-31 22:31:43', 0, 0, '2020-05-25 18:06:52', '2020-05-25 18:06:52');

-- 用户社交关系账号表
create table user_connection
(
    user_id            bigint       not null comment 'Violet系统用户名',
    provider_name      varchar(20)  not null comment '第三方平台名称',
    provider_user_id   varchar(50)  not null comment '第三方平台账户ID',
    provider_user_name varchar(50)  null comment '第三方平台用户名',
    nick_name          varchar(50)  null comment '第三方平台昵称',
    avatar             varchar(512) null comment '第三方平台头像',
    location           varchar(255) null comment '地址',
    remark             varchar(255) null comment '备注',
    constraint UserConnectionRank
        unique (user_id, provider_name, provider_user_id)
) comment '系统用户社交账户关联表' charset = utf8;

alter table user_connection
    add primary key (user_id, provider_name, provider_user_id);

-- 用户组表

create table user_group (
                            id          bigint auto_increment comment '用户组id'
                                primary key,
                            parent_id   bigint           not null comment '上级分组id',
                            group_name  varchar(100)     not null comment '分组名称',
                            sort_index  bigint default 0 null comment '排序',
                            create_time datetime         null comment '创建时间',
                            modify_time datetime         null comment '修改时间'
) comment '用户分组表' charset = utf8;

create index group_name_idx
    on user_group (group_name);

create index parent_id_idx
    on user_group (parent_id);
-- 用户组表数据
INSERT INTO user_group (id, parent_id, group_name, sort_index, create_time, modify_time) VALUES (1, 0, '默认分组', 1, '2020-05-28 16:22:01', '2020-05-28 16:21:58');


-- 菜单表
create table menu
(
    id          bigint auto_increment comment '菜单/按钮ID'
        primary key,
    parent_id   bigint           not null comment '上级菜单ID',
    title       varchar(100)     not null comment '菜单或按钮的标题',
    name        varchar(50)      null comment '组件名称',
    path        varchar(255)     null comment '对应路由path',
    redirect    varchar(150)     null comment '重定向到路径',
    component   varchar(255)     null comment '对应路由组件component',
    icon        varchar(50)      null comment '图标',
    keep_alive  int              null,
    hidden      int    default 0 null comment '控制路由和子路由是否显示在 sidebar',
    perms       varchar(50)      null comment '权限标识',
    type        char(2)          not null comment '类型 0菜单 1按钮',
    sort_index  bigint default 0 null comment '排序',
    create_time datetime         not null comment '创建时间',
    modify_time datetime         null comment '修改时间'
) comment '菜单表' charset = utf8;

create index menu_id
    on menu (id);

create index menu_parent_id
    on menu (parent_id);

-- 菜单表数据
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (2, 0, '仪表盘', 'dashboard', '/dashboard', '/dashboard/workplace', 'RouteView', 'dashboard', 1, 0, null, '0', 2, '2020-05-28 20:44:36', '2020-05-28 20:44:41');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (3, 2, '分析页', 'analysis', '/dashboard/analysis', null, 'dashboard/Analysis', '', 1, 0, 'personnel:user', '0', 4, '2020-05-28 20:47:05', '2020-07-21 22:31:38');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (4, 2, '工作台', 'workplace', '/dashboard/workplace', null, 'dashboard/Workplace', '', 1, 0, 'personnel:groups', '0', 3, '2020-05-28 20:49:03', '2020-07-21 22:31:46');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (5, 0, '表单页', 'form', '/form', '/form/base-form', 'RouteView', 'form', 0, 0, null, '0', 5, '2020-05-28 20:50:32', '2020-05-28 20:50:30');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (6, 5, '基础表单', 'BaseForm', '/form/base-form', '', 'form/basicForm', '', 1, 0, '', '0', 6, '2020-05-28 20:52:22', '2020-05-28 20:52:26');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (7, 5, '分步表单', 'StepForm', '/form/step-form', null, 'form/stepForm/StepForm', '', 1, 0, null, '0', 7, '2020-05-30 14:40:24', '2020-05-30 14:40:27');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (8, 5, '高级表单', 'AdvanceForm', '/form/advanced-form', null, 'form/advancedForm/AdvancedForm', null, 1, 0, null, '0', 8, '2020-05-30 14:41:56', '2020-05-30 14:41:58');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (9, 0, '系统管理', 'ram', '/ram', '/ram/user', 'RouteView', 'safety-certificate', 1, 0, '', '0', 9, '2020-05-31 12:00:02', '2020-05-31 12:00:04');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (10, 9, '用户管理', 'user', '/ram/user', null, 'user/UserList', null, 1, 0, '', '0', 10, '2020-05-31 11:57:50', '2020-05-31 11:57:52');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (11, 9, '角色管理', 'role', '/ram/role', null, 'role/RoleList', null, 1, 0, 'role:view', '0', 11, '2020-06-04 14:47:58', '2020-06-04 14:48:01');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (12, 9, '菜单管理', 'menu', '/ram/menu', null, 'menu/MenuList', null, 1, 0, 'menu:view', '0', 12, '2020-06-05 16:26:55', '2020-06-05 16:26:58');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (13, 9, '用户组管理', 'group', '/ram/user/group', null, 'group/UserGroupList', null, 1, 0, 'group:view', '0', 13, '2020-06-05 18:35:27', '2020-06-05 18:35:31');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (14, 13, '保存', 'userGroupSave', '', null, null, null, 0, 0, 'group:save', '1', 14, '2020-06-06 13:09:17', '2020-06-06 13:09:21');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (15, 12, '保存', 'menuSave', '', null, '', null, 0, 0, 'menu:save', '1', 15, '2020-06-06 14:30:12', '2020-06-06 14:30:14');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (16, 11, '保存', 'roleSave', '', null, null, null, 0, 0, 'role:save', '1', 16, '2020-06-06 17:41:13', '2020-06-06 17:41:13');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (18, 12, '删除', 'menuDelete', null, null, null, null, 0, 0, 'menu:delete', '1', 17, '2020-06-16 11:46:12', '2020-06-16 11:46:15');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (19, 11, '删除', 'roleDelete', null, null, null, null, 0, 0, 'role:delete', '1', 18, '2020-06-16 11:47:26', '2020-06-16 11:47:29');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (20, 13, '删除', 'userGroupDelete', null, null, null, null, 0, 0, 'group:delete', '1', 19, '2020-06-16 11:48:37', '2020-06-16 11:48:40');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (21, 10, '新增', 'userAdd', null, null, null, null, 0, 0, 'user:add', '1', 20, '2020-06-29 11:53:35', '2020-06-29 11:53:41');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (22, 10, '重置密码', 'userResetPassword', null, null, null, null, 0, 0, 'user:reset', '1', 21, '2020-06-29 20:20:01', '2020-06-29 20:20:03');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (23, 10, '更新', 'userUpdate', null, null, null, null, 0, 0, 'user:update', '1', 22, '2020-06-30 17:53:02', '2020-06-30 17:53:07');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (24, 10, '删除', 'userDelete', null, null, null, null, 0, 0, 'user:delete', '1', 23, '2020-06-30 17:53:45', '2020-06-30 17:53:48');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (25, 0, '监控日志', 'monitor', '/monitor', '/monitor/log/action', 'RouteView', 'fund', 1, 0, '', '0', 24, '2020-07-16 09:54:09', '2020-07-16 09:54:12');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (26, 25, '系统日志', 'actionLog', '/monitor/log/action', null, 'monitor/ActionLog', null, 1, 0, null, '0', 25, '2020-07-16 09:54:27', '2020-07-16 09:54:25');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (27, 25, '登录日志', 'loginLog', '/monitor/log/login', null, 'monitor/LoginLog', null, 1, 0, null, '0', 26, '2020-07-16 21:14:02', '2020-07-16 21:14:04');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (28, 0, '个人页', 'account', '/account', '/account/center', 'RouteView', 'user', 1, 0, null, '0', 27, '2020-07-17 15:54:26', '2020-07-17 15:54:29');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (29, 28, '个人中心', 'accountCenter', '/account/center', null, 'account/center/Index', null, 1, 0, '', '0', 28, '2020-07-17 11:37:09', '2020-07-17 11:37:12');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (30, 0, '网关管理', 'gateway', '/gateway', '/gateway/route/user', 'RouteView', 'gateway', 1, 0, null, '0', 29, '2020-07-18 10:00:46', '2020-07-18 10:00:50');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (31, 30, '网关用户', 'routeUser', '/gateway/route/user', null, 'gateway/user', null, 1, 0, null, '0', 30, '2020-07-18 10:02:52', '2020-07-18 10:02:54');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (32, 30, '网关日志', 'routeLog', '/gateway/route/log', null, 'gateway/routelog', null, 1, 0, null, '0', 31, '2020-07-18 10:04:15', '2020-07-18 10:04:19');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (33, 30, '限流规则', 'rateLimitRule', '/gateway/ratelimit/rule', null, 'gateway/rate-limit-rule', null, 1, 0, null, '0', 32, '2020-07-18 10:07:39', '2020-07-18 10:07:42');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (34, 30, '限流日志', 'rateLimitLog', '/gateway/ratelimit/log', null, 'gateway/rate-limit-log', null, 1, 0, null, '0', 33, '2020-07-18 10:08:33', '2020-07-18 10:08:36');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (35, 30, '黑名单管理', 'blackList', '/gateway/black', null, 'gateway/black', null, 1, 0, null, '0', 34, '2020-07-18 10:10:36', '2020-07-18 10:10:39');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (36, 30, '拦截日志', 'blockList', '/gateway/blocklog', null, 'gateway/blocklog', null, 1, 0, null, '0', 35, '2020-07-18 10:11:53', '2020-07-18 10:11:56');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (37, 10, '查看', 'userView', '', null, null, null, 0, 1, 'user:view', '1', 36, '2020-07-23 18:20:59', '2020-07-23 18:21:04');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (38, 11, '查看', 'roleView', null, null, null, null, 0, 1, 'role:view', '1', 37, '2020-07-23 18:24:03', '2020-07-23 18:24:06');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (39, 12, '查看', 'menuView', null, null, null, null, 0, 1, 'menu:view', '1', 38, '2020-07-23 18:24:45', '2020-07-23 18:24:49');
INSERT INTO menu (id, parent_id, title, name, path, redirect, component, icon, keep_alive, hidden, perms, type, sort_index, create_time, modify_time) VALUES (40, 13, '查看', 'groupView', null, null, null, null, 0, 1, 'group:view', '1', 39, '2020-07-23 18:25:01', '2020-07-23 18:24:58');

-- 角色表
create table role
(
    id          bigint auto_increment comment '角色ID'
        primary key,
    role_name   varchar(10)   not null comment '角色名称',
    remark      varchar(100)  null comment '角色描述',
    create_time datetime      not null comment '创建时间',
    modify_time datetime      null comment '修改时间'
) comment '角色表' charset = utf8;

-- 角色表数据
INSERT INTO role (id, role_name, remark, create_time, modify_time) VALUES (1, '普通用户', '用户注册默认角色', '2020-05-28 16:25:39', '2020-07-23 18:27:45');
INSERT INTO role (id, role_name, remark, create_time, modify_time) VALUES (2, '管理员', '管理员', '2020-06-04 10:23:29', '2020-07-23 18:28:00');

-- 角色菜单关联表
create table role_menu
(
    role_id bigint not null,
    menu_id bigint not null,
    primary key (role_id, menu_id)
) comment '角色菜单关联表' charset = utf8;

create index menu_id_idx
    on role_menu (menu_id);

create index role_id_idx
    on role_menu (role_id);

-- 角色菜单关联表数据
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 2);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 3);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 4);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 5);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 6);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 7);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 8);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 9);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 10);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 11);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 12);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 13);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 25);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 26);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 27);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 28);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 29);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 30);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 31);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 32);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 33);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 34);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 35);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 36);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 37);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 38);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 39);
INSERT INTO role_menu (role_id, menu_id) VALUES (1, 40);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 2);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 3);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 4);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 5);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 6);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 7);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 8);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 9);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 10);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 11);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 12);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 13);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 14);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 15);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 16);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 18);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 19);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 20);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 21);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 22);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 23);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 24);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 25);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 26);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 27);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 28);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 29);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 30);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 31);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 32);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 33);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 34);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 35);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 36);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 37);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 38);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 39);
INSERT INTO role_menu (role_id, menu_id) VALUES (2, 40);

-- 用户和角色关联表
create table user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
) comment '用户角色关联表' charset = utf8;

-- 用户和角色关联表数据
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

-- oauth2客户端表
create table oauth_client_details
(
    client_id               varchar(255)  not null
        primary key,
    resource_ids            varchar(255)  null,
    client_secret           varchar(255)  null,
    scope                   varchar(255)  null,
    authorized_grant_types  varchar(255)  null,
    web_server_redirect_uri varchar(255)  null,
    authorities             varchar(255)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(255)  null
);
-- oauth2客户端表数据
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('violet', null, '$2a$10$aBM4ONm2kghXsJ60KiiWCuIWgU.710LW4ZDnW86ME1f7XwZI.Uiu6', 'all', 'authorization_code,refresh_token,password', null, null, 7200, 36000, null, '1');

-- 用户登录日志表
create table user_login_log
(
    id         bigint auto_increment comment 'id'
        primary key,
    username   varchar(50) not null comment '用户名',
    login_time datetime    not null comment '登录时间',
    location   varchar(50) null comment '登录地点',
    ip         varchar(50) null comment 'IP地址',
    `system`   varchar(50) null comment '操作系统',
    browser    varchar(50) null comment '浏览器'
) comment '登录日志表' engine = MyISAM
                    charset = utf8;

create index login_time_idx
    on user_login_log (login_time);


-- 系统操作日志表
create table violet_action_log
(
    id             bigint auto_increment comment '日志ID'
        primary key,
    username       varchar(50) null comment '操作用户',
    operation      text        null comment '操作内容',
    execution_time bigint      null comment '执行时间，单位毫秒',
    method         text        null comment '操作方法',
    params         text        null comment '方法参数',
    ip             varchar(64) null comment '操作者IP',
    location       varchar(50) null comment '操作地点',
    create_time    datetime    null comment '创建时间'
) comment '用户操作日志表' engine = MyISAM
                      charset = utf8;

create index create_time_idx
    on violet_action_log (create_time);


-- 系统配置选项表
create table setting_option
(
    id           bigint auto_increment
        primary key,
    option_key   varchar(100) not null comment 'key名称',
    option_value varchar(100) not null comment '值',
    create_time  datetime     not null comment '创建时间',
    modify_time  datetime     not null comment '修改时间'
) comment '系统配置选项表';

-- 系统配置选项表数据
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (1, 'email_host', 'smtp.qq.com', '2020-07-14 17:07:35', '2020-07-14 17:07:37');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (2, 'email_protocol', 'smtp', '2020-07-14 17:07:39', '2020-07-14 17:07:46');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (3, 'email_ssl_port', '587', '2020-07-14 17:07:42', '2020-07-14 17:07:48');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (4, 'email_username', 'admin@admin.com', '2020-07-14 17:07:44', '2020-07-14 17:07:51');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (5, 'email_password', '123456', '2020-07-14 17:08:03', '2020-07-14 17:08:05');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (6, 'email_enabled', 'true', '2020-07-14 17:08:37', '2020-07-14 17:08:39');
INSERT INTO setting_option (id, option_key, option_value, create_time, modify_time) VALUES (7, 'email_from_name', 'guqing', '2020-07-14 19:57:47', '2020-07-14 19:57:50');