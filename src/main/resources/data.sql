
INSERT INTO diensten (category, title, description, duration, price)VALUES ('Men', 'Herenservice Deluxe', 'Een uitgebreide onderhoudsservice voor mannen', 30, 30.0);
INSERT INTO diensten (category, title, description, duration, price)VALUES ('Women', 'Damesknippen & Styling', 'Knippen en stylen voor vrouwen', 45, 40.0);
INSERT INTO diensten (category, title, description, duration, price)VALUES ('Children', 'Kinderfeestje Kapsel', 'Leuke knipbeurt en styling voor kinderen', 20, 15.0);

INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '10:00 - 16:00', 'Closed');
INSERT INTO opening_hours (monday, tuesday, wednesday, thursday, friday, saturday, sunday)VALUES('09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', '09:00 - 18:00', 'Closed', 'Closed');


INSERT INTO kapsalon (name, owner, location, postal_code, availability, opening_hours_id)VALUES('StarkStyle', 'Eddard',  'Main Street10','1811DS', true,1);
INSERT INTO kapsalon (name, owner,location, postal_code,availability, opening_hours_id)VALUES('LannisterStyle', 'Tywin' ,  'Main Street4','3356KA', true,2);


INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Sansa', false, 'License123',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Arya', true, 'License456',1);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Cersei', false, 'License789',2);
INSERT INTO barbers (name, available, license,kapsalon_id) VALUES ('Baelish', true, 'License780',2);



INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,1);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (1,3);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (2,2);

INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (3,1);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (3,3);
INSERT INTO barber_dienst(barber_id,dienst_id)VALUES (4,2);




--  3 Customers
INSERT INTO users (username, password, email) VALUES ('Jon', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','jon@test.nl');
INSERT INTO users (username, password, email) VALUES ('Joffrey', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'joffrey@test.nl');
INSERT INTO users (username, password, email) VALUES ('Tyrion', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','tyrion@test.nl');

INSERT INTO authorities (username, authority) VALUES ('Jon', 'ROLE_CUSTOMER');
INSERT INTO authorities (username, authority) VALUES ('Joffrey', 'ROLE_CUSTOMER');
INSERT INTO authorities (username, authority) VALUES ('Tyrion', 'ROLE_CUSTOMER');


INSERT INTO appointment (kapsalon_id,dienst_id , barber_id,appointment_date, appointment_time ,customer_id, status ,is_paid) VALUES( 1,1,1, '2024-01-01', '10:30','Jon','APPROVED', true);
INSERT INTO appointment (kapsalon_id,dienst_id , barber_id,appointment_date, appointment_time ,customer_id, status ,is_paid) VALUES( 1,1,1, '2024-12-12', '10:30','Jon','PENDING', false);
INSERT INTO appointment (kapsalon_id,dienst_id , barber_id,appointment_date, appointment_time ,customer_id, status ,is_paid) VALUES( 2,3,3, '2024-01-20', '10:30','Joffrey','APPROVED', true);


INSERT INTO users (username, password, email) VALUES ('User', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl');
INSERT INTO users (username, password, email) VALUES ('Admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl');
INSERT INTO users (username, password, email) VALUES ('Eddard', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','eddard@test.nl');
INSERT INTO users (username, password, email) VALUES ('Tywin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'tywin@test.nl');


INSERT INTO authorities (username, authority) VALUES ('Admin', 'ROLE_CUSTOMER');
INSERT INTO authorities (username, authority) VALUES ('Admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Eddard', 'ROLE_CUSTOMER');
INSERT INTO authorities (username, authority) VALUES ('Eddard', 'ROLE_OWNER');
INSERT INTO authorities (username, authority) VALUES ('Tywin', 'ROLE_CUSTOMER');
INSERT INTO authorities (username, authority) VALUES ('Tywin', 'ROLE_OWNER');


