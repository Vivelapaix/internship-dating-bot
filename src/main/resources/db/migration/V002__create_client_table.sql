CREATE TABLE client(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(1024) NOT NULL,
  type SMALLINT NOT NULL,
  state SMALLINT NOT NULL
);