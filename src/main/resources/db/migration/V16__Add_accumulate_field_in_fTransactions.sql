alter table if exists fuel_transaction
    add accumulate bool;

update fuel_transaction  set accumulate  =  true where bonus>=0;
update fuel_transaction set accumulate = false where bonus <0;