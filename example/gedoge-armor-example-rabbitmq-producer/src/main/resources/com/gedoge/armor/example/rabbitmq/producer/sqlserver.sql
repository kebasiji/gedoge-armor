create table [user]
(
    id         bigint      not null
    constraint user_pk
    primary key,
    name       varchar(50) not null,
    created_at datetime    not null,
    updated_at datetime
    )
    go

