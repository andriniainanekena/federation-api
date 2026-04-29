INSERT INTO collectivity (id, number, name, location, specialty, date_creation) VALUES
        ('col-1', 1, 'Mpanorina',       'Ambatondrazaka', 'Riziculture', '2020-01-01'),
        ('col-2', 2, 'Dobo voalohany',  'Ambatondrazaka', 'Pisciculture','2020-01-01'),
        ('col-3', 3, 'Tantely mamy',    'Brickaville',    'Apiculture',  '2020-01-01');

-- MEMBRES (collectivites 1 et 2 partagent les memes personnes C1-M1..C1-M8)
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, adhesion_date, collectivity_id) VALUES
        ('C1-M1','Prénom membre 1', 'Nom membre 1',  '1980-02-01','MALE',  'Lot II V M Ambato.', 'Riziculteur','0341234567','member.1@fed-agri.mg', 'PRESIDENT',     '2020-01-01','col-1'),
        ('C1-M2','Prénom membre 2', 'Nom membre 2',  '1982-03-05','MALE',  'Lot II F Ambato.',   'Agriculteur','0321234567','member.2@fed-agri.mg', 'VICE_PRESIDENT','2020-01-01','col-1'),
        ('C1-M3','Prénom membre 3', 'Nom membre 3',  '1992-03-10','MALE',  'Lot II J Ambato.',   'Collecteur', '0331234567','member.3@fed-agrimg',  'SECRETARY',     '2020-01-01','col-1'),
        ('C1-M4','Prénom membre 4', 'Nom membre 4',  '1988-05-22','FEMALE','Lot A K 50 Ambato.', 'Distributeur','0381234567','member.4@fed-agri.mg','TREASURER',     '2020-01-01','col-1'),
        ('C1-M5','Prénom membre 5', 'Nom membre 5',  '1999-08-21','MALE',  'Lot UV 80 Ambato.',  'Riziculteur','0373434567','member.5@fed-agri.mg', 'SENIOR',        '2020-01-01','col-1'),
        ('C1-M6','Prénom membre 6', 'Nom membre 6',  '1998-08-22','FEMALE','Lot UV 6 Ambato.',   'Riziculteur','0372234567','member.6@fed-agri.mg', 'SENIOR',        '2020-01-01','col-1'),
        ('C1-M7','Prénom membre 7', 'Nom membre 7',  '1998-01-31','MALE',  'Lot UV 7 Ambato.',   'Riziculteur','0374234567','member.7@fed-agri.mg', 'SENIOR',        '2020-01-01','col-1'),
        ('C1-M8','Prénom membre 6', 'Nom membre 8',  '1975-08-20','MALE',  'Lot UV 8 Ambato.',   'Riziculteur','0370234567','member.8@fed-agri.mg', 'SENIOR',        '2020-01-01','col-1');


INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, adhesion_date, collectivity_id) VALUES
        ('C3-M1','Prénom membre 9',  'Nom membre 9',  '1988-01-02','MALE',  'Lot 33 J Antsirabe', 'Apiculteur',  '034034567', 'member.9@fed-agri.mg', 'PRESIDENT',     '2020-01-01','col-3'),
        ('C3-M2','Prénom membre 10', 'Nom membre 10', '1982-03-05','MALE',  'Lot 2 J Antsirabe',  'Agriculteur', '0338634567','member.10@fed-agri.mg','VICE_PRESIDENT','2020-01-01','col-3'),
        ('C3-M3','Prénom membre 11', 'Nom membre 11', '1992-03-12','MALE',  'Lot 8 KM Antsirabe', 'Collecteur',  '0338234567','member.11@fed-agrimg', 'SECRETARY',     '2020-01-01','col-3'),
        ('C3-M4','Prénom membre 12', 'Nom membre 12', '1988-05-10','FEMALE','Lot A K 50 Antsirabe','Distributeur','0382334567','member.12@fed-agri.mg','TREASURER',    '2020-01-01','col-3'),
        ('C3-M5','Prénom membre 13', 'Nom membre 13', '1999-08-11','MALE',  'Lot UV 80 Antsirabe.','Apiculteur', '0373365567','member.13@fed-agri.mg','SENIOR',        '2020-01-01','col-3'),
        ('C3-M6','Prénom membre 14', 'Nom membre 14', '1998-08-09','FEMALE','Lot UV 6 Antsirabe.', 'Apiculteur', '0378234567','member.14@fed-agri.mg','SENIOR',        '2020-01-01','col-3'),
        ('C3-M7','Prénom membre 15', 'Nom membre 15', '1998-01-13','MALE',  'Lot UV 7 Antsirabe',  'Apiculteur', '0374914567','member.15@fed-agri.mg','SENIOR',        '2020-01-01','col-3'),
        ('C3-M8','Prénom membre 16', 'Nom membre 16', '1975-08-02','MALE',  'Lot UV 8 Antsirabe',  'Apiculteur', '0370634567','member.16@fed-agri.mg','SENIOR',        '2020-01-01','col-3');

-- JUNCTION TABLE : membres par collectivite avec leur role dans chaque collectivite
-- Collectivite 1
INSERT INTO collectivity_member (collectivity_id, member_id, occupation) VALUES
        ('col-1','C1-M1','PRESIDENT'),
        ('col-1','C1-M2','VICE_PRESIDENT'),
        ('col-1','C1-M3','SECRETARY'),
        ('col-1','C1-M4','TREASURER'),
        ('col-1','C1-M5','SENIOR'),
        ('col-1','C1-M6','SENIOR'),
        ('col-1','C1-M7','SENIOR'),
        ('col-1','C1-M8','SENIOR');

-- Collectivite 2 (memes personnes, roles differents)
INSERT INTO collectivity_member (collectivity_id, member_id, occupation) VALUES
        ('col-2','C1-M1','SENIOR'),
        ('col-2','C1-M2','SENIOR'),
        ('col-2','C1-M3','SENIOR'),
        ('col-2','C1-M4','SENIOR'),
        ('col-2','C1-M5','PRESIDENT'),
        ('col-2','C1-M6','VICE_PRESIDENT'),
        ('col-2','C1-M7','SECRETARY'),
        ('col-2','C1-M8','TREASURER');

-- Collectivite 3
INSERT INTO collectivity_member (collectivity_id, member_id, occupation) VALUES
        ('col-3','C3-M1','PRESIDENT'),
        ('col-3','C3-M2','VICE_PRESIDENT'),
        ('col-3','C3-M3','SECRETARY'),
        ('col-3','C3-M4','TREASURER'),
        ('col-3','C3-M5','SENIOR'),
        ('col-3','C3-M6','SENIOR'),
        ('col-3','C3-M7','SENIOR'),
        ('col-3','C3-M8','SENIOR');

-- BUREAUX (collectivity_structure)
INSERT INTO collectivity_structure (collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id) VALUES
        ('col-1','C1-M1','C1-M2','C1-M4','C1-M3'),
        ('col-2','C1-M5','C1-M6','C1-M8','C1-M7'),
        ('col-3','C3-M1','C3-M2','C3-M4','C3-M3');

-- REFERENTS (parrains)
-- Collectivite 1 members (C1-M1 et C1-M2 n ont pas de parrains)
INSERT INTO member_referees (member_id, referee_id) VALUES
        ('C1-M3','C1-M1'),('C1-M3','C1-M2'),
        ('C1-M4','C1-M1'),('C1-M4','C1-M2'),
        ('C1-M5','C1-M1'),('C1-M5','C1-M2'),
        ('C1-M6','C1-M1'),('C1-M6','C1-M2'),
        ('C1-M7','C1-M1'),('C1-M7','C1-M2'),
        ('C1-M8','C1-M6'),('C1-M8','C1-M7');

-- Collectivite 3 members
INSERT INTO member_referees (member_id, referee_id) VALUES
        ('C3-M1','C1-M1'),('C3-M1','C1-M2'),
        ('C3-M2','C1-M1'),('C3-M2','C1-M2'),
        ('C3-M3','C3-M1'),('C3-M3','C3-M2'),
        ('C3-M4','C3-M1'),('C3-M4','C3-M2'),
        ('C3-M5','C3-M1'),('C3-M5','C3-M2'),
        ('C3-M6','C3-M1'),('C3-M6','C3-M2'),
        ('C3-M7','C3-M1'),('C3-M7','C3-M2'),
        ('C3-M8','C3-M1'),('C3-M8','C3-M2');

-- COTISATIONS (membership_fees)
INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES
        ('cot-1','col-1','2026-01-01','ANNUALLY',100000,'Cotisation annuelle','ACTIVE'),
        ('cot-2','col-2','2026-01-01','ANNUALLY',100000,'Cotisation annuelle','ACTIVE'),
        ('cot-3','col-3','2026-01-01','ANNUALLY', 50000,'Cotisation annuelle','ACTIVE');

-- COMPTES FINANCIERS
-- Collectivite 1
INSERT INTO financial_account (id, collectivity_id, account_type, amount) VALUES
    ('C1-A-CASH','col-1','CASH',750000);

INSERT INTO financial_account (id, collectivity_id, account_type, amount, holder_name, mobile_banking_service, mobile_number) VALUES
    ('C1-A-MOBILE-1','col-1','MOBILE_BANKING',0,'Mpanorina','ORANGE_MONEY','0370489612');

-- Collectivite 2
INSERT INTO financial_account (id, collectivity_id, account_type, amount) VALUES
    ('C2-A-CASH','col-2','CASH',550000);

INSERT INTO financial_account (id, collectivity_id, account_type, amount, holder_name, mobile_banking_service, mobile_number) VALUES
    ('C2-A-MOBILE-1','col-2','MOBILE_BANKING',100000,'Dobo voalohany','ORANGE_MONEY','0320489612');

-- Collectivite 3 (aucun paiement donc solde = 0)
INSERT INTO financial_account (id, collectivity_id, account_type, amount) VALUES
    ('C3-A-CASH','col-3','CASH',0);

-- PAIEMENTS - Collectivite 1
INSERT INTO member_payment (id, member_id, amount, payment_mode, account_credited_id, membership_fee_id, creation_date) VALUES
    ('pay-c1-1','C1-M1',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-2','C1-M2',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-3','C1-M3',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-4','C1-M4',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-5','C1-M5',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-6','C1-M6',100000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-7','C1-M7', 60000,'CASH','C1-A-CASH','cot-1','2026-01-01'),
    ('pay-c1-8','C1-M8', 90000,'CASH','C1-A-CASH','cot-1','2026-01-01');

-- PAIEMENTS - Collectivite 2
INSERT INTO member_payment (id, member_id, amount, payment_mode, account_credited_id, membership_fee_id, creation_date) VALUES
    ('pay-c2-1','C1-M1', 60000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-2','C1-M2', 90000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-3','C1-M3',100000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-4','C1-M4',100000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-5','C1-M5',100000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-6','C1-M6',100000,'CASH',          'C2-A-CASH',    'cot-2','2026-01-01'),
    ('pay-c2-7','C1-M7', 40000,'MOBILE_BANKING','C2-A-MOBILE-1','cot-2','2026-01-01'),
    ('pay-c2-8','C1-M8', 60000,'MOBILE_BANKING','C2-A-MOBILE-1','cot-2','2026-01-01');

-- TRANSACTIONS - Collectivite 1
INSERT INTO collectivity_transaction (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id) VALUES
    ('tx-c1-1','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M1'),
    ('tx-c1-2','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M2'),
    ('tx-c1-3','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M3'),
    ('tx-c1-4','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M4'),
    ('tx-c1-5','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M5'),
    ('tx-c1-6','col-1','2026-01-01',100000,'CASH','C1-A-CASH','C1-M6'),
    ('tx-c1-7','col-1','2026-01-01', 60000,'CASH','C1-A-CASH','C1-M7'),
    ('tx-c1-8','col-1','2026-01-01', 90000,'CASH','C1-A-CASH','C1-M8');

-- TRANSACTIONS - Collectivite 2
INSERT INTO collectivity_transaction (id, collectivity_id, creation_date, amount, payment_mode, account_credited_id, member_debited_id) VALUES
    ('tx-c2-1','col-2','2026-01-01', 60000,'CASH',          'C2-A-CASH',    'C1-M1'),
    ('tx-c2-2','col-2','2026-01-01', 90000,'CASH',          'C2-A-CASH',    'C1-M2'),
    ('tx-c2-3','col-2','2026-01-01',100000,'CASH',          'C2-A-CASH',    'C1-M3'),
    ('tx-c2-4','col-2','2026-01-01',100000,'CASH',          'C2-A-CASH',    'C1-M4'),
    ('tx-c2-5','col-2','2026-01-01',100000,'CASH',          'C2-A-CASH',    'C1-M5'),
    ('tx-c2-6','col-2','2026-01-01',100000,'CASH',          'C2-A-CASH',    'C1-M6'),
    ('tx-c2-7','col-2','2026-01-01', 40000,'MOBILE_BANKING','C2-A-MOBILE-1','C1-M7'),
    ('tx-c2-8','col-2','2026-01-01', 60000,'MOBILE_BANKING','C2-A-MOBILE-1','C1-M8');

