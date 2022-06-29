create sequence price_sequence start 1;

create table price(
    id int8 primary key not null default nextval('price_sequence'),
    fuel varchar(15) NOT NULL,
    price float4 not null,
    creator_id int8,
        UNIQUE (fuel, creator_id)
);

alter  table if exists price
    add constraint price_user_fk
        foreign key (creator_id) references users;

