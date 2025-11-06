-- Active: 1762361951000@@127.0.0.1@5432@mysampledb
CREATE DATABASE mySampleDb;

-- don't forget to switch to the new database!
CREATE SCHEMA mySampleSchema;

CREATE TABLE mySampleSchema.myNewTable (
    --id INTEGER UNIQUE NOT NULL,
    id INTEGER PRIMARY KEY,
    email VARCHAR(100),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    salary FLOAT CHECK (salary > 0),
    -- secondSalary DOUBLE,
    thirdSalary DECIMAL,
    fourthSalary NUMERIC (10, 2) -- precision (total number of digits),
    -- scale (maximum number or digits to the right of the decimal)
);

CREATE TABLE mySampleSchema.AnotherSimpleTable (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100)

    --CONSTRAINT .....
);

ALTER TABLE mySampleSchema.AnotherSimpleTable 
ADD COLUMN newTable_id INTEGER;

ALTER TABLE mySampleSchema.AnotherSimpleTable 
ADD CONSTRAINT fk_newTable_id
FOREIGN KEY (newTable_id) REFERENCES mySampleSchema.myNewTable (id);

DROP TABLE mySampleSchema.myNewTable;

DROP TABLE mySampleSchema.AnotherSimpleTable;

DROP CONSTRAINT fk_newTable_id;