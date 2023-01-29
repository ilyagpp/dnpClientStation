alter table azs
    drop constraint azs_user_fk;
alter table if exists azs
    DROP COLUMN "user_id";

