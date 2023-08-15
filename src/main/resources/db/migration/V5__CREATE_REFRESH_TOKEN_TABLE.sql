CREATE TABLE IF NOT EXISTS cl0ud.users (
    id bigint NOT NULL,
    token character varying(255),
    expiry_date character varying(255) unique
);