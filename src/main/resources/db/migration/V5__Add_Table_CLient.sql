create sequence client_sequence start 1;

create table clients (
    id int8 not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    patronymic varchar(50),
    sex varchar(15),
    birthday date,
    added timestamp not null,
    active bool not null,
    email varchar(50),
    phone_number varchar(50),
    client_card_id int8,
    user_id int8,
    primary key (id)
);

alter table if exists clients
    add constraint clients_uniq
        unique (email, phone_number, user_id);

alter table if exists client_card
    add constraint client_card_clients_fk
        foreign key (client_id) references clients;

alter table if exists fuel_transaction
    add constraint fuel_transaction_users_fc
        foreign key (creator_id) references users;

alter table if exists clients
    add constraint clients_users_fk
        foreign key (user_id) references users;

alter table if exists clients
    add constraint clients_client_card_fk
        foreign key (client_card_id) references client_card;