/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info   */
/******************************************/
use violet_nacos;

CREATE TABLE `config_info`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255)          DEFAULT NULL,
    `content`      longtext     NOT NULL COMMENT 'content',
    `md5`          varchar(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       varchar(20)           DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128)          DEFAULT NULL,
    `tenant_id`    varchar(128)          DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256)          DEFAULT NULL,
    `c_use`        varchar(64)           DEFAULT NULL,
    `effect`       varchar(64)           DEFAULT NULL,
    `type`         varchar(64)           DEFAULT NULL,
    `c_schema`     text,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info';
INSERT INTO violet_nacos.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (1, 'violet-gateway', 'DEFAULT_GROUP', 'server:
  port: 8301
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          ''[/**]'':
            allowedOrigins: "*"
            allowedMethods: "*"
            allowedHeaders: "*"
            allowCredentials: true
      routes:
        - id: Violet-Auth-Social
          uri: lb://Violet-Auth
          predicates:
            - Path=/auth/social/**
          filters:
            - name: Hystrix
              args:
                name: socialfallback
                fallbackUri: forward:/fallback/Violet-Auth
        - id: Violet-Auth
          uri: lb://Violet-Auth
          predicates:
            - Path=/auth/**
          filters:
            - name: Hystrix
              args:
                name: authfallback
                fallbackUri: forward:/fallback/Violet-Auth
        - id: Violet-App-Admin
          uri: lb://Violet-App-Admin
          predicates:
            - Path=/admin/**
          filters:
            - name: Hystrix
              args:
                name: adminfallback
                fallbackUri: forward:/fallback/Violet-App-Admin
        - id: Violet-App-Test
          uri: lb://Violet-App-Test
          predicates:
            - Path=/test/**
          filters:
            - name: Hystrix
              args:
                name: testfallback
                fallbackUri: forward:/fallback/Violet-App-Test
      loadbalancer:
        use404: true
      default-filters:
        - StripPrefix=1
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration

# 网关增强配置
  data:
    mongodb:
      host: 192.168.1.12
      port: 27017
      database: violet_cloud_route
  redis:
    database: 3
    host: ${REDIS_URL}
    port: 6379
    lettuce:
      pool:
        min-idle: 8
        max-idle: 500
        max-active: 2000
        max-wait: 10000
    timeout: 5000

# 网关配置
violet:
  gateway:
    # 是否启用网关增强默认true
    # 使用网关增强时注释spring.autoconfigure.exclude并配置mongodb
    enhance: false
    jwt:
      secret: 1234567
      expiration: 36000

hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 10000
    socialfallback:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000

ribbon:
  eager-load:
    enabled: true

management:
  endpoint:
    health:
      show-details: ALWAYS
  endpoints:
    web:
      exposure:
        include: health,info,gateway
logging:
  level:
    xyz.guqing.violet.gateway: debug

', '1a68c652131cea6ceafa0c6981ee6380', '2020-10-31 15:52:43', '2021-01-10 08:22:31', null, '172.20.0.1', '', '', '', '', '', 'yaml', '');
INSERT INTO violet_nacos.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (2, 'violet-auth', 'DEFAULT_GROUP', 'server:
  port: 8101
spring:
  datasource:
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${MYSQL_URL}/violet_cloud?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8
    hikari:
      connection-timeout: 30000
      max-lifetime: 1800000
      max-pool-size: 15
      min-idle: 5
      connection-test-query: select 1
      pool-name: VioletHikariCP
  redis:
    database: 0
    host: ${REDIS_URL}
    port: 6379
    lettuce:
      pool:
        min-idle: 8
        max-idle: 500
        max-active: 2000
        max-wait: 10000
    timeout: 5000
logging:
  level:
    xyz.guqing.violet.auth: debug
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  boot:
    admin:
      client:
        url: http://127.0.0.1:8401
        username: violet
        password: 123456
        instance:
          prefer-ip: true
info:
  app:
    name: ${spring.application.name}
    description: "@project.description@"
    version: "@project.version@"

management:
  endpoints:
    web:
      exposure:
        include: ''*''
  endpoint:
    health:
      show-details: ALWAYS

justauth:
  enabled: true
  type:
    github:
      client-id:
      client-secret:
      redirect-uri:
    gitee:
      client-id:
      client-secret:
      redirect-uri:
    tencent_cloud:
      client-id:
      client-secret:
      redirect-uri:
    dingtalk:
      client-id:
      client-secret:
      redirect-uri:
    qq:
      client-id:
      client-secret:
      redirect-uri:
    microsoft:
      client-id:
      client-secret:
      redirect-uri:
  cache:
    type: redis
    prefix: ''Violet::CLOUD::SOCIAL::STATE::''
    timeout: 1h

violet:
  auth:
    redirectUrl: ''http://127.0.0.1:8000''
    socialLoginClientId: violet
  cloud:
    security:
      enable: true
      auth-uri: /**
      anon-uris: /actuator/**,/captcha,/social/**,/v2/api-docs,/v2/api-docs-ext,/login,/resource/**
security:
  oauth2:
    client:
      client-id: violet
      client-secret: violet-secret-123456
      user-authorization-uri:  http://127.0.0.1:8301/auth/oauth/authorize
      access-token-uri:  http://127.0.0.1:8301/auth/oauth/token
    resource:
      jwt:
        key-uri:  http://127.0.0.1:8301/auth/oauth/token_key
        key-value: violet', '370822274b1002cbcf6cef19e7b13c22', '2020-10-31 15:52:43', '2020-10-31 15:58:34', null, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO violet_nacos.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (3, 'violet-app-admin', 'DEFAULT_GROUP', 'server:
  port: 8201
spring:
  aop:
    proxy-target-class: true
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  hikari:
    connection-timeout: 30000
    max-lifetime: 1800000
    max-pool-size: 15
    min-idle: 5
    connection-test-query: select 1
    pool-name: VioletHikariCP
  datasource:
    username: root
    password: 123456
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver #com.mysql.cj.jdbc.Driver
    url: jdbc:p6spy:mysql://${MYSQL_URL}/violet_cloud?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    lettuce:
      pool:
        min-idle: 8
        max-idle: 500
        max-active: 2000
        max-wait: 10000
    timeout: 5000
security:
  oauth2:
    client:
      client-id: violet
      client-secret: violet-secret-123456
      user-authorization-uri:  http://127.0.0.1:8301/auth/oauth/authorize
      access-token-uri:  http://127.0.0.1:8301/auth/oauth/token
    resource:
      jwt:
        key-uri:  http://127.0.0.1:8301/auth/oauth/token_key
        key-value: violet
info:
  app:
    name: ${spring.application.name}
    description: "@project.description@"
    version: "@project.version@"

management:
  endpoints:
    web:
      exposure:
        include: ''*''
  endpoint:
    health:
      show-details: ALWAYS
violet:
  cloud:
    security:
      enable: true
      anon-uris: /test/**,/user/check/**,/mail/captcha
oss:
  # 是否开启oss对象存储
  enable: true
  # 开启end point接口
  info: true
  #使用云OSS需要将此设置为false
  path-style-access: false
  endpoint: oss-cn-hangzhou.aliyuncs.com
  bucket-name:
  access-key:
  secret-key:
mybatis:
  mapper-locations: classpath*:/mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: deleted  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
logging:
  level:
    xyz.guqing.violet.app.admin: debug', '8223ca023d218e65ed12496ca6fd0ad8', '2020-10-31 15:52:43', '2020-10-31 16:01:28', null, '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '');
INSERT INTO violet_nacos.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (4, 'violet-app-test', 'DEFAULT_GROUP', 'server:
  port: 8202
violet:
  cloud:
    security:
      enable: true
      anonUris: /hello
      authUri: /abc/**
security:
  oauth2:
    client:
      client-id: violet
      client-secret: violet-secret-123456
      user-authorization-uri:  http://47.103.195.246:8301/auth/oauth/authorize
      access-token-uri:  http://47.103.195.246:8301/auth/oauth/token
    resource:
      jwt:
        key-uri:  http://47.103.195.246:8301/auth/oauth/token_key
        key-value: violet', 'f398169f9a1ad63cf8c0bcd7a865f071', '2021-01-10 08:19:54', '2021-01-10 08:20:10', null, '172.20.0.1', '', '', '', '', '', 'yaml', '');
INSERT INTO violet_nacos.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema) VALUES (5, 'violet-apm-admin', 'DEFAULT_GROUP', 'server:
  port: 8401

spring:
  security:
    user:
      name: violet
      password: 123456
  boot:
    admin:
      ui:
        title: ${spring.application.name}', '071ef5f4adf9993faef503551cfe195a', '2020-11-01 08:29:45', '2020-11-01 08:29:45', null, '113.116.131.116', '', '', 'spring boot admin监控配置', null, null, 'yaml', null);


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_aggr   */
/******************************************/
CREATE TABLE `config_info_aggr`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) NOT NULL COMMENT 'datum_id',
    `content`      longtext     NOT NULL COMMENT '内容',
    `gmt_modified` datetime     NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) DEFAULT NULL,
    `tenant_id`    varchar(128) DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='增加租户字段';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_beta   */
/******************************************/
CREATE TABLE `config_info_beta`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext     NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024)         DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       varchar(20)           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128)          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_beta';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_tag   */
/******************************************/
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128)          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext     NOT NULL COMMENT 'content',
    `md5`          varchar(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       varchar(20)           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_tag';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_tags_relation   */
/******************************************/
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)   NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64)  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)   NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`),
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_tag_relation';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = group_capacity   */
/******************************************/
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128)        NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='集群、各Group容量信息表';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = his_config_info   */
/******************************************/
CREATE TABLE `his_config_info`
(
    `id`           bigint(64) unsigned NOT NULL,
    `nid`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255)        NOT NULL,
    `group_id`     varchar(128)        NOT NULL,
    `app_name`     varchar(128)                 DEFAULT NULL COMMENT 'app_name',
    `content`      longtext            NOT NULL,
    `md5`          varchar(32)                  DEFAULT NULL,
    `gmt_create`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `src_user`     text,
    `src_ip`       varchar(20)                  DEFAULT NULL,
    `op_type`      char(10)                     DEFAULT NULL,
    `tenant_id`    varchar(128)                 DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`),
    KEY `idx_gmt_create` (`gmt_create`),
    KEY `idx_gmt_modified` (`gmt_modified`),
    KEY `idx_did` (`data_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='多租户改造';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = tenant_capacity   */
/******************************************/
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128)        NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='租户容量信息表';


CREATE TABLE `tenant_info`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) default '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) default '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32)  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)   NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)   NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='tenant_info';

CREATE TABLE `users`
(
    `username` varchar(50)  NOT NULL PRIMARY KEY,
    `password` varchar(500) NOT NULL,
    `enabled`  boolean      NOT NULL
);

CREATE TABLE `roles`
(
    `username` varchar(50) NOT NULL,
    `role`     varchar(50) NOT NULL,
    UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions`
(
    `role`     varchar(50)  NOT NULL,
    `resource` varchar(255) NOT NULL,
    `action`   varchar(8)   NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
);

INSERT INTO users (username, password, enabled)
VALUES ('violet', '$2a$10$x5ukrWFGEz1Wu4TzqJFh8espSkNXcYVpvsko44zJHSJeoJC2A/zvm', TRUE);

INSERT INTO roles (username, role)
VALUES ('violet', 'ROLE_ADMIN');
