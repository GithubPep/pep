-- auto-generated definition
create table app_account
(
    id         varchar(64) not null
        primary key comment '主键',
    brandCode  varchar(64) not null comment '品牌code',
    appKey     varchar(64) not null comment 'appKey',
    appSecret  varchar(64) not null comment 'appSecret',
    createUser varchar(64) null comment '创建人id',
    updateUser varchar(64) null comment '最后修改人id',
    createTime timestamp   not null comment '创建时间',
    updateTime timestamp   not null comment '最后修改时间',
    isDeleted  tinyint(1)  null comment '逻辑删除',
    projectId  varchar(64) null comment '空间id'
)
    comment '品牌表';

-- auto-generated definition
create table emq_client_auth
(
    id             varchar(64) not null
        primary key comment '主键',
    deviceCode     varchar(64) not null comment 'deviceCode',
    clientId       varchar(64) not null comment 'emq客户端id',
    serviceAddress varchar(64) not null comment 'mqtt消息地址',
    servicePort    varchar(64) not null comment 'mqtt消息端口',
    username       varchar(64) not null comment 'username认证需要',
    password       varchar(64) not null comment 'password认证需要',
    createUser     varchar(64) null comment '创建人id',
    updateUser     varchar(64) null comment '最后修改人id',
    createTime     timestamp   not null comment '创建时间',
    updateTime     timestamp   not null comment '最后修改时间',
    isDeleted      tinyint(1)  null comment '逻辑删除',
    projectId      varchar(64) null comment '空间id'
)
    comment 'emq客户端认证信息表';

-- auto-generated definition
create table pass_rule
(
    id            varchar(64) not null
        primary key comment '主键',
    ruleName      varchar(64) not null comment '规则名称',
    ruleDesc      varchar(64) not null comment '规则描述',
    ruleType      int         not null comment '规则类型:0 按照人员先发 1 按照组下发',
    recognizeType varchar(8)  not null comment '识别方式: 0 人脸 1 门禁 2 二维码',
    status        int         not null comment '状态 0.未下发 1已下发 2 下发中 3 下发失败',
    userCount     int         not null comment '人员数量',
    publishTime   timestamp   not null comment '发布时间',
    createUser    varchar(64) null comment '创建人id',
    updateUser    varchar(64) null comment '最后修改人id',
    createTime    timestamp   not null comment '创建时间',
    updateTime    timestamp   not null comment '最后修改时间',
    isDeleted     tinyint(1)  null comment '逻辑删除',
    projectId     varchar(64) null comment '空间id'
)
    comment '通行规则主表';

-- auto-generated definition
create table rule_device_summary
(
    id         varchar(64) not null
        primary key comment '主键',
    ruleId     varchar(64) not null comment '规则id',
    deviceId   varchar(64) not null comment '设备id',
    deviceCode varchar(64) not null comment '设备code',
    flowNo     varchar(64) not null comment '下发流水号,也就是一个设备和一个规则形成的id',
    createUser varchar(64) null comment '创建人id',
    updateUser varchar(64) null comment '最后修改人id',
    createTime timestamp   not null comment '创建时间',
    updateTime timestamp   not null comment '最后修改时间',
    isDeleted  tinyint(1)  null comment '逻辑删除',
    projectId  varchar(64) null comment '空间id'
)
    comment '规则设备表';

-- auto-generated definition
create table rule_person_summary
(
    id         varchar(64) not null
        primary key comment '主键',
    ruleId     varchar(64) not null comment '规则id',
    personId   varchar(64) not null comment '人员id',
    createUser varchar(64) null comment '创建人id',
    updateUser varchar(64) null comment '最后修改人id',
    createTime timestamp   not null comment '创建时间',
    updateTime timestamp   not null comment '最后修改时间',
    isDeleted  tinyint(1)  null comment '逻辑删除',
    projectId  varchar(64) null comment '空间id'
)
    comment '规则人员表';

-- auto-generated definition
create table rule_publish_snapshot
(
    id          varchar(64) not null
        primary key comment '主键',
    ruleId      varchar(64) not null comment '规则id',
    deviceCodes varchar(64) not null comment '规则中设备code的集合,用逗号分隔',
    persons     varchar(64) not null comment '规则中人员的集合,用逗号分隔',
    flowNos     varchar(64) not null comment '规则中下发参数的集合,用逗号分隔',
    createUser  varchar(64) null comment '创建人id',
    updateUser  varchar(64) null comment '最后修改人id',
    createTime  timestamp   not null comment '创建时间',
    updateTime  timestamp   not null comment '最后修改时间',
    isDeleted   tinyint(1)  null comment '逻辑删除',
    projectId   varchar(64) null comment '空间id'
)
    comment '下发快照表';
-- auto-generated definition
create table rule_time_summary
(
    id                 varchar(64) not null
        primary key comment '主键id',
    timeRuleName       varchar(64) not null comment '时间规则名称',
    ruleId             varchar(64) not null comment '规则id',
    effectiveWeek      varchar(64) not null comment '生效星期,用逗号分隔',
    timeRuleType       int         not null comment '规则生效类型: 0 长期 1 自定义 ',
    dayBeginTime       varchar(64) comment '自定义开始时间',
    dayEndTime         varchar(64) comment '自定义结束时间',
    effectiveBeginTime varchar(64) not null comment '生效时间段',
    effectiveEndTime   varchar(64) not null comment '结束时间段',
    permitType         int         not null comment '通行许可 0 不允许 1 允许',
    createUser         varchar(64) null comment '创建人id',
    updateUser         varchar(64) null comment '最后修改人id',
    createTime         timestamp   not null comment '创建时间',
    updateTime         timestamp   not null comment '最后修改时间',
    isDeleted          tinyint(1)  null comment '逻辑删除',
    projectId          varchar(64) null comment '空间id'
)
    comment '规则时间表';

