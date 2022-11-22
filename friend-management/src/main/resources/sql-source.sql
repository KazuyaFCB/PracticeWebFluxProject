CREATE SCHEMA IF NOT EXISTS friend_management;

CREATE TABLE IF NOT EXISTS friend_management.friend (
  id SERIAL PRIMARY KEY,
  email1 VARCHAR(100),
  email2 VARCHAR(100)
);

CREATE UNIQUE INDEX email1_email2_unique_idx ON friend_management.friend (email1, email2);