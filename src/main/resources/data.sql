DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_name VARCHAR(250) PRIMARY KEY,
  password VARCHAR(250) NOT NULL,
  user_type VARCHAR(250) NOT NULL CHECK ( user_type in ('payer', 'seller') ),
  access_token VARCHAR(250),
  face_id VARCHAR(250)
);

INSERT INTO users (user_name, password, user_type) VALUES
  ('payer_test', '123', 'payer'),
  ('seller_test', '123', 'seller');