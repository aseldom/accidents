CREATE TABLE users (
  username VARCHAR(50) NOT NULL primary key,
  password VARCHAR(100) NOT NULL,
  enabled boolean default true
);