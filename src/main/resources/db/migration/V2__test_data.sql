INSERT INTO account (balance, initial_deposit)
VALUES (1000.00, 500.00);
INSERT INTO account (balance, initial_deposit)
VALUES (2000.00, 1000.00);
INSERT INTO account (balance, initial_deposit)
VALUES (3000.00, 1500.00);
INSERT INTO account (balance, initial_deposit)
VALUES (4000.00, 2000.00);
INSERT INTO account (balance, initial_deposit)
VALUES (5000.00, 2500.00);
INSERT INTO account (balance, initial_deposit)
VALUES (6000.00, 3000.00);
INSERT INTO account (balance, initial_deposit)
VALUES (7000.00, 3500.00);
INSERT INTO account (balance, initial_deposit)
VALUES (8000.00, 4000.00);
INSERT INTO account (balance, initial_deposit)
VALUES (9000.00, 4500.00);
INSERT INTO account (balance, initial_deposit)
VALUES (10000.00, 5000.00);

-- Insert test data into users table
INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'John Doe Smith', '1990-01-01', 'johndoe', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'johndoe');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Jane Roe Black', '1985-05-05', 'janeroe', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'janeroe');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Alice Johnson White', '1995-09-09', 'alicej', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'alicej');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Bob Brown Blue', '2000-12-12', 'bobbrown', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 4
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'bobbrown');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Charlie Green Yellow',
       '1992-02-02',
       'charliegreen',
       '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy',
       5
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'charliegreen');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Diana Prince', '1988-03-03', 'dianaprince', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 6
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'dianaprince');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Eve Adams Gray', '1993-04-04', 'eveadams', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 7
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'eveadams');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Frank Harris White',
       '1987-07-07',
       'frankharris',
       '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy',
       8
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'frankharris');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Grace Kelly', '1991-06-06', 'gracekelly', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 9
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'gracekelly');

INSERT INTO users (name, birth_date, login, password, account_id)
SELECT 'Hank Lewis', '1994-08-08', 'hanklewis', '$2a$10$31U1XX7lVVlaA3byv1QVCesYDqlM67IZbbKcozLm.aZuGB8sUl4jy', 10
WHERE NOT EXISTS (SELECT 1 FROM users WHERE login = 'hanklewis');

-- Insert test data into user_emails table
INSERT INTO user_emails (user_id, email)
VALUES (1, 'john.doe@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (1, 'doe.john@sample.com');
INSERT INTO user_emails (user_id, email)
VALUES (2, 'jane.roe@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (3, 'alice.johnson@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (4, 'bob.brown@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (5, 'charlie.green@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (6, 'diana.prince@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (7, 'eve.adams@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (8, 'frank.harris@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (9, 'grace.kelly@example.com');
INSERT INTO user_emails (user_id, email)
VALUES (10, 'hank.lewis@example.com');

-- Insert test data into user_phones table
INSERT INTO user_phones (user_id, phone)
VALUES (1, '+1234567890');
INSERT INTO user_phones (user_id, phone)
VALUES (2, '+1987654321');
INSERT INTO user_phones (user_id, phone)
VALUES (3, '+1122334455');
INSERT INTO user_phones (user_id, phone)
VALUES (4, '+1223344556');
INSERT INTO user_phones (user_id, phone)
VALUES (5, '+1445566778');
INSERT INTO user_phones (user_id, phone)
VALUES (6, '+1556677889');
INSERT INTO user_phones (user_id, phone)
VALUES (7, '+1667788990');
INSERT INTO user_phones (user_id, phone)
VALUES (8, '+1778899001');
INSERT INTO user_phones (user_id, phone)
VALUES (9, '+1889900112');
INSERT INTO user_phones (user_id, phone)
VALUES (10, '+1990011223');