CREATE TABLE IF NOT EXISTS cl0ud.users (
    id bigint NOT NULL,
    full_name character varying(255),
    email character varying(255) unique,
    password character varying(255),
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean,
    role character varying(255)
);