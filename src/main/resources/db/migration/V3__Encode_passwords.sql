create EXTENSION if not exists pgcrypto;

update users set password = crypt(password, gen_salt('bf', 8));