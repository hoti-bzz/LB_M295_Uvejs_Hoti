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

)