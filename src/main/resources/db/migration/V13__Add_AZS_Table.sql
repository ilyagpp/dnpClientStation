create sequence azs_sequence start 1 increment 1;
create table azs(
    azs_id int8 PRIMARY KEY,
    name varchar(50) not null unique,
    properties varchar(255) not null,
    elevated_bonus bool,
    bonus float4,
    user_id int8
);

alter table if exists azs 
    add constraint azs_user_fk
        foreign key (user_id) references users;

alter table if exists users
    add azs_id int8 unique,
    add azs_name varchar(50),
    add constraint users_azs_fk
        foreign key (azs_id) references azs;


