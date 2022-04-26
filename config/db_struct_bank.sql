create schema if not exists bank;

create sequence transaction_pk_seq as integer;

alter sequence transaction_pk_seq owner to postgres;

create table account
(
    id serial not null constraint table_name_pk primary key,
    customer varchar(10),
    country     varchar(2),
    status      varchar(20),
    balance_eur integer,
    balance_sek integer,
    balance_gbp integer,
    balance_usd integer
);

alter table account owner to postgres;

create table transaction
(
    id serial  not null constraint transaction_pk primary key,
    amount     integer,
    direction  varchar(3),
    currency   varchar(3),
    account_id integer not null constraint transaction_account_id_fk references account
);

alter table transaction
    owner to postgres;

