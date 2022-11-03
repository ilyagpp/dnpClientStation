alter table if exists clients
    add constraint unique_email
    UNIQUE (email);

alter table if exists clients
    add constraint phone
        UNIQUE (phone_number);