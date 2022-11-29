CREATE SCHEMA IF NOT EXISTS friend_management;

CREATE TABLE IF NOT EXISTS friend_management.friend (
  id SERIAL PRIMARY KEY,
  email1 VARCHAR(100),
  email2 VARCHAR(100)
);

CREATE UNIQUE INDEX email1_email2_unique_idx ON friend_management.friend (email1, email2);
(1,2), (1,2)

CREATE TABLE IF NOT EXISTS friend_management.subscriber (
  id SERIAL PRIMARY KEY,
  requestor VARCHAR(100),
  target VARCHAR(100)
);

CREATE UNIQUE INDEX subscriber_requestor_target_unique_idx ON friend_management.subscriber (requestor, target);

CREATE TABLE IF NOT EXISTS friend_management.blocker (
  id SERIAL PRIMARY KEY,
  requestor VARCHAR(100),
  target VARCHAR(100)
);

CREATE UNIQUE INDEX blocker_requestor_target_unique_idx ON friend_management.blocker (requestor, target);