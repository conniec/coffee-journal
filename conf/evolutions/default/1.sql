# --- !Ups

create sequence coffee_seq start with 1000;
CREATE TABLE coffee (
    id bigint NOT NULL,
    name varchar(255) NOT NULL,
    producer varchar(255) NOT NULL,
    brew_date timestamp,
    rating int,
    
);
 
# --- !Downs
 
drop sequence if exists coffee_seq;
DROP TABLE coffee;
```