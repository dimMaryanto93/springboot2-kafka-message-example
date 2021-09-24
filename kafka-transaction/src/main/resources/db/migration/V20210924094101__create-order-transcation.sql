create table order_parts
(
    id           character varying(64) not null primary key,
    part_number  varchar(10)           not null,
    qty          integer               not null default 0,
    created_time timestamp             not null default now(),
    created_by   varchar(100)          not null
);
