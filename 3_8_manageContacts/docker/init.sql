create schema if not exists contacts_schema;

create table if not exists contacts_schema.contact
(
    id bigint PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(254) NOT NULL,
    phone VARCHAR(19) NOT NULL
)