-- CREATE TABLE Person (
--     id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--     sername varchar(50) NOT NULL,
--     name varchar(50) NOT NULL,
--     patronymic varchar(50),
--     gender varchar(7) NOT NULL,
--     birthday varchar(10) NOT NULL,
--     email varchar(50) NOT NULL UNIQUE,
--     password varchar NOT NULL,
--     role varchar(100) NOT NULL
-- );

-- INSERT INTO Person(sername, name, patronymic, gender, birthday, email, "password", "role") 
--     VALUES ('Пуха', 'Анастасия', 'Николаевна', 'женщина', '2002-09-23', 'pukha_an@mail.ru', 'pass', 'ROLE_ADMIN');

-- INSERT INTO Person(sername, name, patronymic, gender, birthday, email, "password", "role") 
--     VALUES ('Терещенко', 'Василиса', 'Геннадьевич', 'женщина', '1991-03-11', 'vasilii1991@gmail.com', 'pass', 'ROLE_USER');

-- INSERT INTO Person(sername, name, patronymic, gender, birthday, email, "password", "role") 
--      VALUES ('Потринкин', 'Кирилл', 'Иванович', 'мужчина', '1987-04-30', 'kirill@gmail.com', 'pass', 'ROLE_USER');

select * from Person;

-- DROP TABLE Person;
-- DELETE FROM Person WHERE id=5;
