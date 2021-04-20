CREATE TABLE project(
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(1024) NOT NULL,
  presentation VARCHAR(1024),
  description VARCHAR(1024) NOT NULL,
  test_task VARCHAR(1024),
  user_type SMALLINT NOT NULL,
  user_id BIGINT NOT NULL,
  state SMALLINT NOT NULL,
  mod_state SMALLINT NOT NULL
);