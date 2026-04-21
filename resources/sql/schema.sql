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
    id VARCHAR PRIMARY KEY,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE member (
    id VARCHAR PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender gender_enum NOT NULL,
    address TEXT,
    profession VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(255) UNIQUE NOT NULL,
    occupation occupation_enum NOT NULL,
    adhesion_date DATE NOT NULL,
    collectivity_id VARCHAR,
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id)
);

CREATE TABLE collectivity_structure (
    collectivity_id VARCHAR PRIMARY KEY,
    president_id VARCHAR NOT NULL,
    vice_president_id VARCHAR NOT NULL,
    treasurer_id VARCHAR NOT NULL,
    secretary_id VARCHAR NOT NULL,

    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id),
    FOREIGN KEY (president_id) REFERENCES member(id),
    FOREIGN KEY (vice_president_id) REFERENCES member(id),
    FOREIGN KEY (treasurer_id) REFERENCES member(id),
    FOREIGN KEY (secretary_id) REFERENCES member(id)
);

CREATE TABLE member_referees (
    member_id VARCHAR,
    referee_id VARCHAR,
    PRIMARY KEY (member_id, referee_id),

    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (referee_id) REFERENCES member(id)
);
