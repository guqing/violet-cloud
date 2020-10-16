create schema if not exists `violet-cloud` collate utf8mb4_unicode_ci;

create table if not exists menu
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
    hidden      int    default 1 null comment '控制路由和子路由是否显示在 sidebar',
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

create table if not exists oauth_client_details
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

create table if not exists role
(
    id          bigint auto_increment comment '角色ID'
        primary key,
    role_name   varchar(10)   not null comment '角色名称',
    remark      varchar(100)  null comment '角色描述',
    is_default  int default 0 not null comment '是否是默认角色',
    deleted     int default 0 null comment '删除状态',
    create_time datetime      not null comment '创建时间',
    modify_time datetime      null comment '修改时间'
) comment '角色表' charset = utf8;

create table if not exists role_menu
(
  role_id bigint not null,
  menu_id bigint not null,
  primary key (role_id, menu_id)
)
  comment '角色菜单关联表' charset = utf8;

create index role_menu_menu_id
  on role_menu (menu_id);

create index role_menu_role_id
  on role_menu (role_id);

create table if not exists user
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
    modify_time     datetime                null comment '修改时间',
    constraint rms_user_mobile
        unique (mobile),
    constraint user_email_uindex
        unique (email)
) comment '用户表' charset = utf8;

create index user_mobile
  on user (mobile);

create index user_username
  on user (username);

create table if not exists user_connection
(
  user_name          varchar(50)  not null comment 'Violet系统用户名',
  provider_name      varchar(20)  not null comment '第三方平台名称',
  provider_user_id   varchar(50)  not null comment '第三方平台账户ID',
  provider_user_name varchar(50)  null comment '第三方平台用户名',
  nick_name          varchar(50)  null comment '第三方平台昵称',
  avatar             varchar(512) null comment '第三方平台头像',
  location           varchar(255) null comment '地址',
  remark             varchar(255) null comment '备注',
  constraint UserConnectionRank
    unique (user_name, provider_name, provider_user_id)
)
  comment '系统用户社交账户关联表' charset = utf8;

alter table user_connection
  add primary key (user_name, provider_name, provider_user_id);

create table if not exists user_group
(
  id          bigint auto_increment comment '用户组id'
    primary key,
  parent_id   bigint       not null comment '上级分组id',
  group_name  varchar(100) not null comment '分组名称',
  sort_index  bigint       null comment '排序',
  create_time datetime     null comment '创建时间',
  modify_time datetime     null comment '修改时间'
)
  comment '用户分组表' charset = utf8;

create index user_group_group_name
  on user_group (group_name);

create index user_group_parent_id
  on user_group (parent_id);

create table if not exists user_login_log
(
  id         bigint auto_increment comment 'id'
    primary key,
  username   varchar(50) not null comment '用户名',
  login_time datetime    not null comment '登录时间',
  location   varchar(50) null comment '登录地点',
  ip         varchar(50) null comment 'IP地址',
  `system`   varchar(50) null comment '操作系统',
  browser    varchar(50) null comment '浏览器'
)
  comment '登录日志表' engine = MyISAM
                  charset = utf8;

create index user_login_log_login_time
  on user_login_log (login_time);

create table if not exists user_role
(
  user_id bigint not null comment '用户ID',
  role_id bigint not null comment '角色ID',
  primary key (user_id, role_id)
)
  comment '用户角色关联表' charset = utf8;

create table if not exists violet_action_log
(
  id          bigint auto_increment comment '日志ID'
    primary key,
  username    varchar(50) null comment '操作用户',
  operation   text        null comment '操作内容',
  time        decimal(11) null comment '耗时',
  method      text        null comment '操作方法',
  params      text        null comment '方法参数',
  ip          varchar(64) null comment '操作者IP',
  location    varchar(50) null comment '操作地点',
  create_time datetime    null comment '创建时间'
)
  comment '用户操作日志表' engine = MyISAM
                    charset = utf8;

create index violet_action_log_create_time
  on violet_action_log (create_time);

create table setting_option
(
    id           bigint auto_increment
        primary key,
    option_key   varchar(100) not null comment 'key名称',
    option_value varchar(100) not null comment '值',
    create_time  datetime     not null comment '创建时间',
    modify_time  datetime     not null comment '修改时间',
    constraint setting_option_option_key_uindex
        unique (option_key)
);