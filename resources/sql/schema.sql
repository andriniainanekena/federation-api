CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_enum AS ENUM (
    'JUNIOR',
    'SENIOR',
    'SECRETARY',
    'TREASURER',
    'VICE_PRESIDENT',
    'PRESIDENT'
    );

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

ALTER TABLE member
    ADD COLUMN occupation occupation_enum NOT NULL;

ALTER TABLE member
    ADD COLUMN collectivity_id INT;

ALTER TABLE member
    ADD CONSTRAINT fk_member_collectivity
        FOREIGN KEY (collectivity_id)
            REFERENCES collectivity(id)
            ON DELETE SET NULL;

ALTER TABLE collectivity
    DROP COLUMN federation_approval;