
ALTER TABLE if exists users
    ADD CONSTRAINT email_unique UNIQUE (email),
    ADD CONSTRAINT username_unique UNIQUE (username);