-- Types
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_enum AS ENUM ('JUNIOR','SENIOR','SECRETARY','TREASURER','VICE_PRESIDENT','PRESIDENT');
CREATE TYPE frequency_enum AS ENUM ('WEEKLY','MONTHLY','ANNUALLY','PUNCTUALLY');
CREATE TYPE activity_status_enum AS ENUM ('ACTIVE','INACTIVE');
CREATE TYPE payment_mode_enum AS ENUM ('CASH','MOBILE_BANKING','BANK_TRANSFER');
CREATE TYPE account_type_enum AS ENUM ('CASH','MOBILE_BANKING','BANK');
CREATE TYPE mobile_banking_service_enum AS ENUM ('AIRTEL_MONEY','MVOLA','ORANGE_MONEY');
CREATE TYPE bank_enum AS ENUM ('BRED','MCB','BMOI','BOA','BGFI','AFG','ACCES_BANQUE','BAOBAB','SIPEM');

-- Table collectivity
CREATE TABLE collectivity (
    id            VARCHAR PRIMARY KEY,
    name          VARCHAR(255) UNIQUE,
    number        INTEGER UNIQUE,
    location      VARCHAR(255) NOT NULL,
    specialty     VARCHAR(255),
    date_creation DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Table member
CREATE TABLE member (
    id              VARCHAR PRIMARY KEY,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    birth_date      DATE NOT NULL,
    gender          gender_enum NOT NULL,
    address         TEXT,
    profession      VARCHAR(100),
    phone_number    VARCHAR(20),
    email           VARCHAR(255) UNIQUE NOT NULL,
    occupation      occupation_enum NOT NULL,
    adhesion_date   DATE NOT NULL,
    collectivity_id VARCHAR REFERENCES collectivity(id)
);

-- Table junction collectivity_member (membre peut etre dans plusieurs collectivites)
CREATE TABLE collectivity_member (
    collectivity_id VARCHAR NOT NULL REFERENCES collectivity(id) ON DELETE CASCADE,
    member_id       VARCHAR NOT NULL REFERENCES member(id) ON DELETE CASCADE,
    occupation      occupation_enum NOT NULL,
    PRIMARY KEY (collectivity_id, member_id)
);

-- Table collectivity_structure (bureau)
CREATE TABLE collectivity_structure (
    collectivity_id   VARCHAR PRIMARY KEY REFERENCES collectivity(id) ON DELETE CASCADE,
    president_id      VARCHAR NOT NULL REFERENCES member(id),
    vice_president_id VARCHAR NOT NULL REFERENCES member(id),
    treasurer_id      VARCHAR NOT NULL REFERENCES member(id),
    secretary_id      VARCHAR NOT NULL REFERENCES member(id)
);

-- Table member_referees
CREATE TABLE member_referees (
    member_id  VARCHAR REFERENCES member(id) ON DELETE CASCADE,
    referee_id VARCHAR REFERENCES member(id) ON DELETE CASCADE,
    PRIMARY KEY (member_id, referee_id),
    CHECK (member_id <> referee_id)
);

-- Table membership_fee
CREATE TABLE membership_fee (
    id              VARCHAR PRIMARY KEY,
    collectivity_id VARCHAR NOT NULL REFERENCES collectivity(id),
    eligible_from   DATE NOT NULL,
    frequency       frequency_enum NOT NULL,
    amount          DECIMAL(15,2) NOT NULL CHECK (amount >= 0),
    label           VARCHAR(255),
    status          activity_status_enum NOT NULL DEFAULT 'ACTIVE'
);

-- Table financial_account
CREATE TABLE financial_account (
    id                     VARCHAR PRIMARY KEY,
    collectivity_id        VARCHAR NOT NULL REFERENCES collectivity(id),
    account_type           account_type_enum NOT NULL,
    amount                 DECIMAL(15,2) NOT NULL DEFAULT 0,
    holder_name            VARCHAR(255),
    bank_name              bank_enum,
    bank_code              INTEGER,
    bank_branch_code       INTEGER,
    bank_account_number    INTEGER,
    bank_account_key       INTEGER,
    mobile_banking_service mobile_banking_service_enum,
    mobile_number          VARCHAR(20)
);

-- Table member_payment
CREATE TABLE member_payment (
    id                  VARCHAR PRIMARY KEY,
    member_id           VARCHAR NOT NULL REFERENCES member(id),
    amount              DECIMAL(15,2) NOT NULL,
    payment_mode        payment_mode_enum NOT NULL,
    account_credited_id VARCHAR NOT NULL REFERENCES financial_account(id),
    membership_fee_id   VARCHAR NOT NULL REFERENCES membership_fee(id),
    creation_date       DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Table collectivity_transaction
CREATE TABLE collectivity_transaction (
    id                  VARCHAR PRIMARY KEY,
    collectivity_id     VARCHAR NOT NULL REFERENCES collectivity(id),
    creation_date       DATE NOT NULL DEFAULT CURRENT_DATE,
    amount              DECIMAL(15,2) NOT NULL,
    payment_mode        payment_mode_enum NOT NULL,
    account_credited_id VARCHAR NOT NULL REFERENCES financial_account(id),
    member_debited_id   VARCHAR NOT NULL REFERENCES member(id)
);
