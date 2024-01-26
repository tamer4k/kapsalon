INSERT INTO diensten (category, title, description, duration, price) VALUES ('Men', 'Herenservice', 'Onderhoud van mannelijke kippen', 15, 20.0);
INSERT INTO diensten (category, title, description, duration, price) VALUES ('Women', 'Dameservice', 'Verzorging van vrouwelijke kippen', 15, 25.0);
INSERT INTO diensten (category, title, description, duration, price) VALUES ('Children', 'Kinderdienst', 'Leuke activiteiten voor kuikens', 15, 15.0);

INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '10:00 - 16:00', 'Closed');
INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', 'Closed', 'Closed');


INSERT INTO kapsalon (name, owner, location, postal_code, availability, opening_hours_id)VALUES('StarkStyle', 'Eddard',  'Main Street10','1811DS', true,1);
INSERT INTO kapsalon (name, owner,location, postal_code,availability, opening_hours_id)VALUES('LannisterStyle', 'Tywin' ,  'Main Street4','3356KA', true,2);


INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Sansa', true, 'License123',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Arya', true, 'License456',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Cersei', true, 'License789',2);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Baelish', true, 'License780',2);



INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,1);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (3,2);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (3,3);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (2,2);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,3);


INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('Jon', ' Snow', 'Jon@example.com', '+1112233445');
INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('Joffrey', 'Baratheon', 'Joffrey@example.com', '+1234567890');
INSERT INTO customers (first_name, second_name, email, phone_number) VALUES('Tyrion', 'Lannister', 'Tyrion@example.com', '+9876543210');


INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('ING', 'John Doe', '12345678', '1111222233334444', '12/24');
INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('ABN', 'Alice Smith', '98765432', '5555666677778888', '08/23');
INSERT INTO financial_details (bank_name, account_name, account_number, card_number, valid) VALUES('Rabobank', 'Bob Johnson', '11223344', '9999888877776666', '03/25');



INSERT INTO appointment (kapsalon_id,dienst_id , barber_id,appointment_date, appointment_time ,customer_id,is_paid) VALUES( 1,1,1, '2023-01-15', '10:30',1, true);
INSERT INTO appointment (kapsalon_id,dienst_id , barber_id,appointment_date, appointment_time ,customer_id,is_paid) VALUES( 2,3,3, '2023-01-20', '10:30',2, false);



INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');