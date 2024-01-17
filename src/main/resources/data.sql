INSERT INTO diensten (category, title, description, duration, price) VALUES ('Men', 'Herenservice', 'Onderhoud van mannelijke kippen', 15, 20.0);
INSERT INTO diensten (category, title, description, duration, price) VALUES ('Women', 'Dameservice', 'Verzorging van vrouwelijke kippen', 15, 25.0);
INSERT INTO diensten (category, title, description, duration, price) VALUES ('Children', 'Kinderdienst', 'Leuke activiteiten voor kuikens', 15, 15.0);

INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '10:00 - 16:00', 'Closed');
INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', 'Closed', 'Closed');


INSERT INTO kapsalon (name, location, postal_code, availability, opening_hours_id)VALUES('HairStyle Masters', 'Main Street10','1811DS', true,1);
INSERT INTO kapsalon (name, location, postal_code,availability, opening_hours_id)VALUES('Kapper Knipper', 'Main Street4','3356KA', true,2);


INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Tamer', true, 'License123',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Ashraf', true, 'License456',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Joeseef', true, 'License789',2);


INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,1);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (3,2);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (2,2);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,3);



INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('John', 'Doe', 'john.doe@example.com', '+1234567890');
INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('Alice', 'Smith', 'alice.smith@example.com', '+9876543210');
INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('Bob', 'Johnson', 'bob.johnson@example.com', '+1112233445');


INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('ING', 'John Doe', '12345678', '1111222233334444', '12/24');
INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('ABN', 'Alice Smith', '98765432', '5555666677778888', '08/23');
INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('Rabobank', 'Bob Johnson', '11223344', '9999888877776666', '03/25');



INSERT INTO appointment (appointment_date, appointment_time, barber_id,dienst_id ,customer_id,kapsalon_id) VALUES('2023-01-15', '10:30', 1,1,1,1);

-- INSERT INTO roles(rolename) VALUES ('ADMIN');
-- INSERT INTO roles(rolename) VALUES ('USER');
--
--
-- INSERT INTO users (username, password) VALUES ('Jasper','$2a$10$5vMSZ.1K6fpsCnOPt/majeei.JUSC8XYka8IlDZhmLc1Srv6j67ye');
-- INSERT INTO users (username, password) VALUES ('Alexia','$2a$10$5vMSZ.1K6fpsCnOPt/majeei.JUSC8XYka8IlDZhmLc1Srv6j67ye');
-- INSERT INTO users (username, password) VALUES ('Luna','$2a$10$5vMSZ.1K6fpsCnOPt/majeei.JUSC8XYka8IlDZhmLc1Srv6j67ye');
--
--
-- INSERT INTO users_roles(user_id, role_id) values (3,1);
-- INSERT INTO users_roles(user_id, role_id) values (3,2);

INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');