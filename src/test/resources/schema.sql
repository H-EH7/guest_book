drop table if exists post CASCADE;

create table post
(
    id              bigint generated by default as identity,
    author          varchar(10) not null,
    password        varchar(10),
    side            varchar(5),
    relationship    varchar(10),
    content         varchar(255) not null,
    primary key (id)
);