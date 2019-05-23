insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (1, 'Surname1', 'FirstName1', 'SecondName1');

insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (2, 'Surname2', 'FirstName2', 'SecondName2');

insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (3, 'Surname3', 'FirstName3', 'SecondName3');

--------------------------

insert into GENRE (ID, NAME) values (1, 'Genre1');
insert into GENRE (ID, NAME) values (2, 'Genre2');
insert into GENRE (ID, NAME) values (3, 'Genre3');

--------------------------

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (1, sysdate, 'book_1', 1, 1, 'comment');

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (2, sysdate, 'book_2', 1, 1, 'comment');

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, COMMENT)
    values (3, sysdate, 'book_3', 1, 1, 'comment');

--------------------------
