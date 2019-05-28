insert into AUTHOR (SURNAME, FIRST_NAME, SECOND_NAME)
values ('Surname1', 'FirstName1', 'SecondName1');

insert into AUTHOR (SURNAME, FIRST_NAME, SECOND_NAME)
values ('Surname2', 'FirstName2', 'SecondName2');

insert into AUTHOR (SURNAME, FIRST_NAME, SECOND_NAME)
values ('Surname3', 'FirstName3', 'SecondName3');

--------------------------

insert into GENRE (NAME) values ('Genre1');
insert into GENRE (NAME) values ('Genre2');
insert into GENRE (NAME) values ('Genre3');

--------------------------

insert into BOOK (ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (sysdate, 'book_1', 1, 1, 'comment');

insert into BOOK (ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (sysdate, 'book_2', 1, 1, 'comment');

insert into BOOK (ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (sysdate, 'book_3', 1, 1, 'comment');

--------------------------
