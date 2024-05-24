use m295;

create table genres (
                        id_genre int primary key auto_increment,
                        name varchar(45) not null

);

create table books (
                       id_book int primary key auto_increment,
                       title varchar(45) not null,
                       bookdescription varchar(45) not null,
                       price decimal(10,2),
                       written date not null,
                       published boolean not null,
                       id_genre int not null,
                       FOREIGN KEY (id_genre)
                           REFERENCES genres(id_genre)

);

INSERT INTO genres (name) VALUES
('Fiction'),
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Thriller');

-- Füge Daten in die `books` Tabelle ein
INSERT INTO books (title, bookdescription, price, written, published, id_genre) VALUES
('To Kill a Mockingbird', 'Bla Bla Bla.', 12.99, '1960-07-11', true, 1);

