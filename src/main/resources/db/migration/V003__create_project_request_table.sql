CREATE TABLE project_request (
  id BIGSERIAL PRIMARY KEY,
  tg_uid VARCHAR(1024) NOT NULL,
  project_id BIGINT NOT NULL,
  state SMALLINT NOT NULL
);