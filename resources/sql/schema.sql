CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TYPE occupation_enum AS ENUM (
    'JUNIOR',
    'SENIOR',
    'SECRETARY',
    'TREASURER',
    'VICE_PRESIDENT',
    'PRESIDENT'
    );

CREATE TABLE collectivity (
    id            VARCHAR      PRIMARY KEY,
    location      VARCHAR(255) NOT NULL,
    date_creation DATE         NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE member (
    id              VARCHAR         PRIMARY KEY,
    first_name      VARCHAR(100)    NOT NULL,
    last_name       VARCHAR(100)    NOT NULL,
    birth_date      DATE            NOT NULL,
    gender          gender_enum     NOT NULL,
    address         TEXT,
    profession      VARCHAR(100),
    phone_number    VARCHAR(20),
    email           VARCHAR(255)    UNIQUE NOT NULL,
    occupation      occupation_enum NOT NULL,
    adhesion_date   DATE            NOT NULL,
    collectivity_id VARCHAR         REFERENCES collectivity(id) ON DELETE SET NULL
);

CREATE TABLE collectivity_structure (
    collectivity_id   VARCHAR PRIMARY KEY REFERENCES collectivity(id) ON DELETE CASCADE,
    president_id      VARCHAR NOT NULL    REFERENCES member(id),
    vice_president_id VARCHAR NOT NULL    REFERENCES member(id),
    treasurer_id      VARCHAR NOT NULL    REFERENCES member(id),
    secretary_id      VARCHAR NOT NULL    REFERENCES member(id)
);

CREATE TABLE member_referees (
    member_id  VARCHAR REFERENCES member(id) ON DELETE CASCADE,
    referee_id VARCHAR REFERENCES member(id) ON DELETE CASCADE,
    PRIMARY KEY (member_id, referee_id),
    CHECK (member_id <> referee_id)
);