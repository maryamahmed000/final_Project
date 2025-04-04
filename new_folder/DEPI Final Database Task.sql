create table TestStudent(ID int PRIMARY KEY, FirstName CHAR(50), SecondName CHAR(50),
Course CHAR(20), City Char(20)); 

INSERT INTO TestStudent VALUES(10, 'Salma', 'Ayman', 'Software Testing', 'Giza');
INSERT INTO TestStudent VALUES(11, 'Eman' , 'Shawky', 'Software Testing', 'Cairo');

INSERT INTO TestStudent VALUES(12, 'Maryam', 'Ahmed', 'Software Testing', 'Giza'),
(13, 'Heba', 'Salem', 'Software Testing', 'Alex');

SELECT * FROM TestStudent WHERE City= 'Giza';

UPDATE TestStudent SET Course = 'English' WHERE FirstName='Heba';

DELETE FROM TestStudent WHERE ID=11;

SELECT * FROM TestStudent;

--Validating Database Schema
EXEC sp_help TestStudent;



