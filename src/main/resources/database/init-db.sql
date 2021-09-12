DROP TABLE IF EXISTS tz_candidates;
DROP TABLE IF EXISTS tz_posts;
DROP TABLE IF EXISTS tz_users;
DROP TABLE IF EXISTS tz_cities;
DROP SEQUENCE IF EXISTS tz_cities_id_seq;
DROP SEQUENCE IF EXISTS tz_candidates_id_seq;
DROP SEQUENCE IF EXISTS tz_posts_id_seq;
DROP SEQUENCE IF EXISTS tz_users_id_seq;

CREATE TABLE tz_cities (
    id SERIAL PRIMARY KEY,
    ctName VARCHAR(120) NOT NULL
);

CREATE TABLE tz_candidates (
    id SERIAL PRIMARY KEY,
    id_city INTEGER REFERENCES tz_cities (id) ON DELETE SET NULL,
    cName VARCHAR(120) NOT NULL,
    cPosition VARCHAR(250),
    cCreated DATE DEFAULT current_date
);

CREATE TABLE tz_posts (
    id SERIAL PRIMARY KEY,
    pName VARCHAR(250) NOT NULL,
    pDescription TEXT,
    pCreated DATE DEFAULT current_date
);

CREATE TABLE tz_users (
    id SERIAL PRIMARY KEY,
    uName VARCHAR(120) NOT NULL,
    uMail VARCHAR(90) NOT NULL UNIQUE,
    uPassword VARCHAR(40) NOT NULL
);