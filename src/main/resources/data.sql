DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INTEGER (20)  PRIMARY KEY,
  user_name VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  user_type VARCHAR(250) NOT NULL CHECK ( user_type in ('payer', 'seller') ),
  access_token VARCHAR(250),
  face_id VARCHAR(250),
  user_id INTEGER(20) NOT NULL
);


INSERT INTO users (id,user_name, password, user_type,user_id) VALUES
  (1,'payer_test', '123', 'payer',1),
  (2,'seller_test', '123', 'seller',480928194);

DROP TABLE IF EXISTS sessions;

CREATE TABLE sessions (
  session_id BIGINT PRIMARY KEY,
  user_name VARCHAR(250) NOT NULL,
  logged VARCHAR(250) NOT NULL DEFAULT true,
  dt_logged_in DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dt_logged_out DATETIME,
  FOREIGN KEY (user_name) REFERENCES users(user_name)
);

