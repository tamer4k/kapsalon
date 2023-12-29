INSERT INTO dienst (category, title, description, duration, price) VALUES ('Men', 'Herenservice', 'Onderhoud van mannelijke kippen', 15, 20.0);
INSERT INTO dienst (category, title, description, duration, price) VALUES ('Women', 'Dameservice', 'Verzorging van vrouwelijke kippen', 15, 25.0);
INSERT INTO dienst (category, title, description, duration, price) VALUES ('Children', 'Kinderdienst', 'Leuke activiteiten voor kuikens', 15, 15.0);

INSERT INTO openings_tijden (monday, tuesday, wednesday, thursday, friday, saturday, sunday)
VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '10:00 - 16:00', 'Closed');


INSERT INTO kapsalon (name, location, availability, openings_tijden_id)VALUES('HairStyle Masters', '123 Main Street', true,1);

INSERT INTO kappers (name, available, license,kapsalon_id) VALUES ('Tamer', true, 'License123',1);
INSERT INTO kappers (name, available, license,kapsalon_id) VALUES ('Ashraf', true, 'License456',1);

INSERT INTO dienst_kapper(dienst_id,kapper_id)VALUES (1,1);
INSERT INTO dienst_kapper(dienst_id,kapper_id)VALUES (3,1);
INSERT INTO dienst_kapper(dienst_id,kapper_id)VALUES (2,2);


INSERT INTO klanten (first_name, second_name, email, phone_number) VALUES('John', 'Doe', 'john.doe@example.com', '+1234567890');

INSERT INTO klanten (first_name, second_name, email, phone_number) VALUES('Alice', 'Smith', 'alice.smith@example.com', '+9876543210');

INSERT INTO klanten (first_name, second_name, email, phone_number) VALUES('Bob', 'Johnson', 'bob.johnson@example.com', '+1112233445');