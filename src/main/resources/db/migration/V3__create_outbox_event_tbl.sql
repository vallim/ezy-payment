CREATE TABLE outbox_event (
  id bigserial PRIMARY KEY,
  type varchar(100),
  payload json,
  status varchar(100),
  created_at timestamp,
  updated_at timestamp
);