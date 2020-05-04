INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Emma', '1980-01-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Liam', '1980-02-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Johnson', 'Olivia', '1980-03-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Johnson', 'Noah', '1980-04-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Ava', '1980-05-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'William', '1980-06-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Isabella', '1980-07-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'James', '1980-08-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Sophia', '1980-09-01');
INSERT INTO person (firstname, lastname, birthday) VALUES ('Smith', 'Oliver', '1980-10-01');

INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user0@login.com', 'pwd0', '06*********', '2020-01-01', 0);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user1@login.com', 'pwd1', '06*********', '2020-01-01', 0);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user2@login.com', 'pwd2', '06*********', '2020-01-01', 1);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user3@login.com', 'pwd3', '06*********', '2020-01-01', 1);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user4@login.com', 'pwd4', '06*********', '2020-01-01', 2);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user5@login.com', 'pwd5', '06*********', '2020-01-01', 2);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user6@login.com', 'pwd6', '06*********', '2020-01-01', 3);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user7@login.com', 'pwd7', '06*********', '2020-01-01', 3);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user8@login.com', 'pwd9', '06*********', '2020-01-01', 4);
INSERT INTO users (profile, login, password, phone, registration, state) VALUES ('010010001110001', 'user9@login.com', 'pwd9', '06*********', '2020-01-01', 4);

INSERT INTO category (label) VALUES ('book');
INSERT INTO category (label) VALUES ('dvd');
INSERT INTO category (label) VALUES ('cd');

INSERT INTO theme (label) VALUES ('fantasy');
INSERT INTO theme (label) VALUES ('sci-fi');
INSERT INTO theme (label) VALUES ('romance');

INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('10000000', 'book1', 'sometext', '010010001110001', 1, 1, 3, 1);
INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('20000000', 'dvd1', 'sometext', '010010001110001', 2, 2, 4, 2);
INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('30000000', 'cd1', 'sometext', '010010001110001', 3, 3, 20, 5);
INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('10000001', 'book2', 'sometext', '010010001110001', 1, 1, 7, 2);
INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('20000001', 'dvd2', 'sometext', '010010001110001', 2, 2, 6, 2);
INSERT INTO media (ref, title, description, image, category, theme, score, nb_rate) VALUES ('30000001', 'cd2', 'sometext', '010010001110001', 3, 3, 20, 5);

INSERT INTO book (media_ref, author, nb_pages) VALUES ('10000000', 8, 300);
INSERT INTO book (media_ref, author, nb_pages) VALUES ('10000001', 8, 300);

INSERT INTO dvd (media_ref, director, duration, minAge) VALUES ('20000000', 7, 124, 12);
INSERT INTO dvd (media_ref, director, duration, minAge) VALUES ('20000001', 7, 124, 12);

INSERT INTO cd (media_ref, composer, track) VALUES ('30000000', 9, 9);
INSERT INTO cd (media_ref, composer, track) VALUES ('30000001', 9, 9);