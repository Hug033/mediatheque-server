-- recherche personne par nom--
SELECT * FROM person WHERE person.firstName LIKE ? OR person.lastName LIKE?;

-- recherche utilisateur par status--
SELECT * FROM person WHERE person.state =?;

-- recherche de tous les CD --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN cd ON media.ref = cd.media_ref
INNER JOIN person ON cd.composer = person.id;

-- recherche des CD par theme --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN cd ON media.ref = cd.media_ref
INNER JOIN person ON cd.composer = person.id
WHERE theme.label = ?;

-- recherche des CD par titre --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN cd ON media.ref = cd.media_ref
INNER JOIN person ON cd.composer = person.id
WHERE media.title LIKE ?;

-- recherche de tous les DVD --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN dvd ON media.ref = dvd.media_ref
INNER JOIN person ON dvd.director = person.id;

-- recherche des DVD par theme --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN dvd ON media.ref = dvd.media_ref
INNER JOIN person ON dvd.director = person.id
WHERE theme.label = ?;

-- recherche des DVD par titre --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN dvd ON media.ref = dvd.media_ref
INNER JOIN person ON dvd.director = person.id
WHERE media.title LIKE ?;

-- recherche de tous les livres --

SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN book ON media.ref = book.media_ref
INNER JOIN person ON book.author = person.id;

-- recherche des livres par theme --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN book ON media.ref = book.media_ref
INNER JOIN person ON book.author = person.id
WHERE theme.label = ?;

-- recherche des livres par titre --
SELECT * FROM media
INNER JOIN theme ON media.theme = theme.id
INNER JOIN book ON media.ref = book.media_ref
INNER JOIN person ON book.author = person.id
WHERE media.title LIKE ?;
