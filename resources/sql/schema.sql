CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    birt_date DATE NOT NULL,
    gender gender_enum NOT NULL,
    address TEXT,
    profession varchar(100),
    phone_number varchar(20),
    email varchar(255) UNIQUE NOT NULL,
    date_adhesion_federation DATE NOT NULL
);

CREATE TABLE collectivity (
    id SERIAL PRIMARY KEY,
    location varchar(255) NOT NULL,
    date_creation DATE NOT NULL,
    federation_approval BOOLEAN NOT NULL
);