CREATE TABLE user (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(32) NOT NULL,
  last_name VARCHAR(32) NOT NULL,
  password VARCHAR(32) NOT NULL,
  email VARCHAR(32) NOT NULL UNIQUE,
  account_non_expired BOOLEAN NOT NULL,
  account_non_locked BOOLEAN NOT NULL,
  credentials_non_expired BOOLEAN NOT NULL,
  enabled BOOLEAN NOT NULL
);