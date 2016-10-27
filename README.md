# PhoneBook
Для створення структури БД виконати запит:
 ```
 create database PhoneBook CHARACTER SET utf8;
use PhoneBook;
CREATE TABLE users (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     login CHAR(30) NOT NULL,
     fullName CHAR(100) NOT NULL,
     password CHAR(100) NOT NULL,
     PRIMARY KEY (id)
);
CREATE TABLE records (    id MEDIUMINT NOT NULL AUTO_INCREMENT, user_id  MEDIUMINT NOT NULL,     fName CHAR(50) NOT NULL,     
      sName CHAR(50) NOT NULL,          mName CHAR(50) NOT NULL,          mPhone CHAR(20) NOT NULL,     
      hPhone CHAR(20) NULL,          adress CHAR(100) NULL,          mail CHAR(30) NULL,     
        PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES users(id)   
   );
```




При старті вказувати шлях до конфіг-файлу, приклад запуску:

```
java -Dlardi.conf=d:\JAVA\PhoneBook\src\main\resources\application.properties -jar PhoneBook-0.0.1.jar
```
