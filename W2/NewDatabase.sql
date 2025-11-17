-- Active: 1762363179651@@127.0.0.1@5432@mysampledb
CREATE DATABASE mySampleDb;
CREATE DATABASE expensesdb;

-- switch to mySampleDb BEFORE running this
CREATE SCHEMA mySampleSchema;

CREATE TABLE mySampleSchema.myNewTable (
    id INTEGER PRIMARY KEY,
    email VARCHAR(100), -- we tell system the max amount of characters in this value for mem management
    createdAT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- if we don't define this, the current time is added as default
    salary FLOAT CHECK (salary > 0),
    -- secondSalary DOUBLE,
    thirdSalary DECIMAL,
    fourthSalary NUMERIC(10, 2) -- NUMERIC - precision (num of digits), scale (num of digits after decimal)
);

CREATE TABLE mySampleSchema.AnotherSimpleTable (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100)
    -- CONSTRAINT foreign key can also be added here
);

ALTER TABLE mySampleSchema.AnotherSimpleTable
ADD COLUMN newTable_id INTEGER;

ALTER TABLE mysampleschema.anothersimpletable
ADD CONSTRAINT fk_newTable_id
FOREIGN KEY (newTable_id) REFERENCES mysampleschema.myNewTable (id);

/*

DML: Data Manipulation Language
    - INSERT
    - UPDATE
    - DELETE
- Table records are being manipulated, table structure is NOT being changed

*/

insert into mysampleschema.mynewtable 
(id, email, salary, thirdSalary, fourthSalary) 
values 
(1, 'a@b.com', 1000, 20.00, 8.00),
(2, 'c@b.com', 1000, 20.00, 8.00),
(3, 'd@b.com', 1000, 20.00, 8.00),
(4, 'e@b.com', 1000, 20.00, 8.00);

insert into mysampleschema.anothersimpletable
(id, name, newTable_id) 
values 
(1, 'Abe', 1),
(2, 'bob', 2),
(5, 'Simon', 3),
(4, 'Eddie', 4);

insert into mysampleschema.anothersimpletable (id, name, newtable_id)
values (6, 'Frank', 7);

select * from mysampleschema.anothersimpletable as simple left join mysampleschema.mynewtable as mnt
on simple.newtable_id = mnt.id;

ALTER TABLE mysampleschema.anothersimpletable DROP CONSTRAINT fk_newTable_id;
DROP TABLE mysampleschema.anothersimpletable;
DROP TABLE mysampleschema.mynewtable;

/******************************************************************************
    How to Present our Database
******************************************************************************/
/*

- Entity Relationship Diagram
    - Entities are tables, shows relationships between the tables
    - Relationships: 1:1, 1:N, N:N
        - Crow's Foot

*/