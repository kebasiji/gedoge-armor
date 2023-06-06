create table outbound_event
(
    event_id    varchar(50)   not null
        constraint outbound_event_pk
            primary key,
    occurred_at datetime      not null,
    type        varchar(50)   not null,
    source_id   varchar(50)   not null,
    source      varchar(1000) not null,
    payload     varchar(1000),
    destination varchar(50)   not null,
    tag         varchar(50),
    status      varchar(50)   not null,
    sent_at     datetime
)
go

exec sp_addextendedproperty 'MS_Description', N'事件id', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'event_id'
go

exec sp_addextendedproperty 'MS_Description', N'事件发生时间', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'occurred_at'
go

exec sp_addextendedproperty 'MS_Description', N'事件类型', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN', 'type'
go

exec sp_addextendedproperty 'MS_Description', N'源数据id', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'source_id'
go

exec sp_addextendedproperty 'MS_Description', N'源数据内容', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'source'
go

exec sp_addextendedproperty 'MS_Description', N'负载', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'payload'
go

exec sp_addextendedproperty 'MS_Description', N'路由地址', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'destination'
go

exec sp_addextendedproperty 'MS_Description', N'标签', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN', 'tag'
go

exec sp_addextendedproperty 'MS_Description', N'状态：WAITING SENT', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event',
     'COLUMN',
     'status'
go

exec sp_addextendedproperty 'MS_Description', N'事件发送时间', 'SCHEMA', 'dbo', 'TABLE', 'outbound_event', 'COLUMN',
     'sent_at'
go

create table inbound_event
(
    event_id    varchar(50)   not null
        constraint inbound_event_pk
            primary key,
    occurred_at datetime      not null,
    type        varchar(50)   not null,
    source_id   varchar(50)   not null,
    source      varchar(1000) not null,
    payload     varchar(1000),
    arrived_at  datetime      not null
)
go

exec sp_addextendedproperty 'MS_Description', N'事件id', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'event_id'
go

exec sp_addextendedproperty 'MS_Description', N'事件发生时间', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'occurred_at'
go

exec sp_addextendedproperty 'MS_Description', N'事件类型', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN', 'type'
go

exec sp_addextendedproperty 'MS_Description', N'源数据id', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'source_id'
go

exec sp_addextendedproperty 'MS_Description', N'源数据内容', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'source'
go

exec sp_addextendedproperty 'MS_Description', N'负载', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'payload'
go

exec sp_addextendedproperty 'MS_Description', N'收到事件时间', 'SCHEMA', 'dbo', 'TABLE', 'inbound_event', 'COLUMN',
     'arrived_at'
go

