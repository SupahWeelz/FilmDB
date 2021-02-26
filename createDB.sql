DROP DATABASE filmdb;
CREATE DATABASE filmdb;
USE filmdb;

CREATE TABLE users (
	id INT AUTO_INCREMENT,
	email VARCHAR(50),
	password VARCHAR(50),
	username VARCHAR(50),
	PRIMARY KEY (id)
);

INSERT INTO users (email, password, username)
	VALUES 	("a@a","password","John"),
			("b@b","password","Jill"),
			("c@c","password","Jack"),
			("d@d","password","Jacqueline")
;

CREATE TABLE movies (
	id INT AUTO_INCREMENT,
	name VARCHAR(50),
	director VARCHAR(50),
	rating FLOAT,
	showing BOOL,
	summary TEXT,
	PRIMARY KEY (id)
);

INSERT INTO movies (name, director, rating, showing, summary)
	VALUES 	("greatmovie2","someguy2",9.1,TRUE,"Lorem ipsum dolor sit amet."),
			("greatmovie3","someguy3",9.3,TRUE,"Lorem ipsum dolor sit amet."),
			("greatmovie4","someguy4",8.8,FALSE,"Lorem ipsum dolor sit amet."),
			("greatmovie5","someguy5",9.0,TRUE,"Lorem ipsum dolor sit amet."),
			("greatmovie6","someguy6",8.5,FALSE,"Lorem ipsum dolor sit amet.")
;

CREATE TABLE actors (
	id INT AUTO_INCREMENT,
	name VARCHAR(50),
	PRIMARY KEY (id)
);

INSERT INTO actors (name)
	VALUES	("otherguy2"),
			("otherguy3"),
			("otherguy4"),
			("otherguy5")
;

CREATE TABLE userFavorites (
	userId INT,
	movieId INT,
	PRIMARY KEY (userId, movieId),
	FOREIGN KEY (userId) REFERENCES users(id),
	FOREIGN KEY (movieId) REFERENCES movies(id)
);

INSERT INTO userFavorites (userId,movieId)
	VALUES	(2,3),
			(2,4),
			(2,5),
			(3,5),
			(4,6),
			(4,1),
			(5,3),
			(5,1),
			(5,5)
;

CREATE TABLE movieActors (
	movieId INT,
	actorId INT,
	PRIMARY KEY (movieId, actorId),
	FOREIGN KEY (actorId) REFERENCES actors(id),
	FOREIGN KEY (movieId) REFERENCES movies(id)
);

INSERT INTO movieActors (movieId,actorId)
	VALUES 	(2,2),
			(2,4),
			(3,2),
			(3,1),
			(4,5),
			(1,3),
			(1,4),
			(5,5),
			(4,3),
			(5,1)
	;