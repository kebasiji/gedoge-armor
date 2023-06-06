create table outbound_event
(
    event_id    varchar(50)   not null comment '事件id',
    occurred_at datetime      not null comment '事件发生时间',
    type        varchar(50)   not null comment '事件类型',
    source_id   varchar(50)   not null comment '源数据id',
    source      varchar(1000) not null comment '源数据内容',
    payload     varchar(1000) null comment '负载',
    destination varchar(50)   not null comment '路由地址',
    tag         varchar(50)   null comment '标签',
    status      varchar(50)   not null comment '状态：WAITING SENT',
    sent_at     datetime      null comment '事件发送时间',
    constraint event_pk
        primary key (event_id)
);

create table inbound_event
(
    event_id    varchar(50)   not null comment '事件id',
    occurred_at datetime      not null comment '事件发生时间',
    type        varchar(50)   not null comment '事件类型',
    source_id   varchar(50)   not null comment '源数据id',
    source      varchar(1000) not null comment '源数据内容',
    payload     varchar(1000) null comment '负载',
    arrived_at  datetime      not null comment '收到事件时间',
    constraint event_pk
        primary key (event_id)
)