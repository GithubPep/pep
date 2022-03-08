-- auto-generated definition
create table rule_device_summary
(
    id            varchar(64) not null
        primary key,
    ruleId      varchar(64) not null,
    deviceId      varchar(64) not null,
    deviceCode      varchar(64) not null,
    flowNo      varchar(64) not null,
    createUser    varchar(64) null,
    updateUser    varchar(64) null,
    createTime    timestamp   null,
    updateTime    timestamp   null,
    isDeleted     tinyint(1)  not null,
    projectId     varchar(64) null
)
    comment '规则设备表';



-- auto-generated definition
create table rule_person_summary
(
    id            varchar(64) not null
        primary key,
    ruleId      varchar(64) not null,
    personId      varchar(64) not null,
    createUser    varchar(64) null,
    updateUser    varchar(64) null,
    createTime    timestamp   null,
    updateTime    timestamp   null,
    isDeleted     tinyint(1)  not null,
    projectId     varchar(64) null
)
    comment '规则人员表';



-- auto-generated definition
create table rule_time_summary
(
    id            varchar(64) not null
        primary key,
    timeRuleName      varchar(64) not null,
    ruleId      varchar(64) not null,
    effectiveWeek      varchar(64) not null,
    timeRuleType     int not null,
    dayBeginTime      varchar(64) not null,
    dayEndTime      varchar(64) not null,
    effectiveBeginTime      varchar(64) not null,
    effectiveEndTime      varchar(64) not null,
    permitType      int not null,
    createUser    varchar(64) null,
    updateUser    varchar(64) null,
    createTime    timestamp   null,
    updateTime    timestamp   null,
    isDeleted     tinyint(1)  not null,
    projectId     varchar(64) null
)
    comment '规则时间表';



-- auto-generated definition
create table emq_client_auth
(
    id            varchar(64) not null
        primary key,
    deviceCode      varchar(64) not null,
    clientId      varchar(64) not null,
    serviceAddress      varchar(64) not null,
    servicePort      varchar(64) not null,
    username      varchar(64) not null,
    password      varchar(64) not null,
    createUser    varchar(64) null,
    updateUser    varchar(64) null,
    createTime    timestamp   null,
    updateTime    timestamp   null,
    isDeleted     tinyint(1)  not null,
    projectId     varchar(64) null
)
    comment 'emq客户端认证信息表';

use `pep-iot`;
-- auto-generated definition
create table rule_publish_snapshot
(
    id            varchar(64) not null
        primary key,
    ruleId      varchar(64) not null,
    deviceCodes      varchar(64) not null,
    persons      varchar(64) not null,
    flows      varchar(64) not null,
    createUser    varchar(64) null,
    updateUser    varchar(64) null,
    createTime    timestamp   null,
    updateTime    timestamp   null,
    isDeleted     tinyint(1)  not null,
    projectId     varchar(64) null
)
    comment '下发快照表';