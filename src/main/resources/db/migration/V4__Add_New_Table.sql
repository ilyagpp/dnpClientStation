create sequence card_sequence start 1;
create sequence transact_sequence start 1;

create table client_card (
    id int8 not null,
    bonus float4,
    card_number varchar(255),
    client_id int8,
    primary key (id));

create table fuel_transaction (
    id int8 not null default nextval('transact_sequence'),
    create_date_time timestamp not null,
    update_date_time timestamp DEFAULT now(),
    fuel varchar(15),
    price float4 not null,
    total float4 not null,
    volume float4 not null,
    client_card varchar(13),
    bonus float4,
    creator_id int8,
    primary key (id));


alter table if exists client_card
    add constraint client_card_uniq
        unique (card_number);

alter table if exists client_card
    add constraint client_card_clients_fk
        foreign key (client_id) references clients;

alter table if exists fuel_transaction
    add constraint fuel_transaction_users_fc
        foreign key (creator_id) references users;
