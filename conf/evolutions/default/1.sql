# --- !Ups

create sequence coffee_seq start with 1000;
CREATE TABLE coffee (
    id bigint NOT NULL,
    name varchar(255) NOT NULL,
    roaster varchar(255),
    roastDate timestamp,
    producer varchar(255) NOT NULL,
    brewDate timestamp,
    price int,
    rating int,
    flavors varchar(255),
    
);
 
# --- !Downs
 
drop sequence if exists coffee_seq;
DROP TABLE coffee;
```