create table if not exists pep_emq_client_auth
(
    id            integer not null
        auto_increment primary key,
    deviceCode     varchar(64) not null,
    clientId       varchar(64) not null,
    serviceAddress varchar(64) not null,
    servicePort    varchar(64) not null,
    username       varchar(64) not null,
    password       varchar(64) not null,
    constraint pep_emq_client_auth_device_index
        unique (deviceCode)
);