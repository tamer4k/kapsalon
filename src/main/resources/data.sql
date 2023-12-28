INSERT INTO dienst (category, title, description, duration, price) VALUES ('Men', 'Herenservice', 'Onderhoud van mannelijke kippen', 15, 20.0);
INSERT INTO dienst (category, title, description, duration, price) VALUES ('Women', 'Dameservice', 'Verzorging van vrouwelijke kippen', 15, 25.0);
INSERT INTO dienst (category, title, description, duration, price) VALUES ('Children', 'Kinderdienst', 'Leuke activiteiten voor kuikens', 15, 15.0);

INSERT INTO kappers (name, available, license) VALUES ('Tamer', true, 'License123');
INSERT INTO kappers (name, available, license) VALUES ('Ashraf', true, 'License456');

INSERT INTO openings_tijden (monday, tuesday, wednesday, thursday, friday, saturday, sunday)
VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '10:00 - 16:00', 'Closed');


INSERT INTO kapsalon (name, location, availability, openings_tijden_id)VALUES('HairStyle Masters', '123 Main Street', true,1);


INSERT INTO kapsalon_kapper(kapsalon_id,kapper_id)VALUES (1,1)
