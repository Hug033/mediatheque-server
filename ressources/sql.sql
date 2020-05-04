\c postgres
drop database bd_mediatheque;
create database bd_mediatheque;
\c bd_mediatheque

create table person (
    id serial not null,
    firstname varchar not null,
    lastname varchar not null,
    birthday varchar not null,

    primary key(id)
);

create table users (
    person_id serial not null,
    profile bytea not null,
    login varchar not null,
    password varchar not null,
    phone varchar not null,
    registration varchar not null,
    state int not null,

    foreign key(person_id) REFERENCES person(id)
);

create table category (
    id serial not null,
    label varchar not null,
    primary key(id)
);

create table theme (
    id serial not null,
    label varchar not null,
    primary key(id)
);

create table media (
    ref varchar not null,
    title varchar not null,
    description varchar not null,
    image bytea not null,
    category int not null,
    theme int not null,
    score int,
    nb_rate int,

    foreign key(category) REFERENCES category(id),
    foreign key(theme) REFERENCES theme(id),
    primary key(ref)
);


create table borrowing  (
    id serial not null,
    startDate date not null,
    endDate date not null,
    condition varchar not null,
    borrower_id int not null,
    media_ref varchar not null,

	foreign key(media_ref) REFERENCES media(ref),
    primary key(id)
);

create table book (
    media_ref varchar not null,
    author int not null,
    nb_pages int not null,

    foreign key(media_ref) REFERENCES media(ref)
);

create table dvd (
    media_ref varchar not null,
    director int not null,
    duration float not null,
    minAge int not null,

    foreign key(media_ref) REFERENCES media(ref)
);

create table cd (
    media_ref varchar not null,
    composer int not null,
    track int not null,

    foreign key(media_ref) REFERENCES media(ref)
);

create table token (
    id_user int not null,
    token varchar not null,
    validate varchar not null,

    foreign key(id_user) REFERENCES person(id)
);

GRANT ALL PRIVILEGES ON person TO user_media;
GRANT ALL PRIVILEGES ON person_id_seq TO user_media;
GRANT ALL PRIVILEGES ON users TO user_media;
GRANT ALL PRIVILEGES ON media TO user_media;
GRANT ALL PRIVILEGES ON category TO user_media;
GRANT ALL PRIVILEGES ON category_id_seq TO user_media;
GRANT ALL PRIVILEGES ON theme TO user_media;
GRANT ALL PRIVILEGES ON theme_id_seq TO user_media;
GRANT ALL PRIVILEGES ON borrowing TO user_media;
GRANT ALL PRIVILEGES ON borrowing_id_seq TO user_media;
GRANT ALL PRIVILEGES ON book TO user_media;
GRANT ALL PRIVILEGES ON dvd TO user_media;
GRANT ALL PRIVILEGES ON cd TO user_media;
GRANT ALL PRIVILEGES ON token TO user_media;
