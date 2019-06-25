insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (sq_author.nextval, 'Surname1', 'FirstName1', 'SecondName1');

insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (sq_author.nextval, 'Surname2', 'FirstName2', 'SecondName2');

insert into AUTHOR (ID, SURNAME, FIRST_NAME, SECOND_NAME)
values (sq_author.nextval, 'Surname3', 'FirstName3', 'SecondName3');

--------------------------

insert into GENRE (ID, NAME) values (sq_genre.nextval, 'Genre1');
insert into GENRE (ID, NAME) values (sq_genre.nextval, 'Genre2');
insert into GENRE (ID, NAME) values (sq_genre.nextval, 'Genre3');

--------------------------

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, DESCRIPTION)
values (sq_book.nextval, to_date('2019-01-01','YYYY-MM-DD'), 'book_1', 1, 1, 'description');

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, DESCRIPTION)
values (sq_book.nextval, to_date('2019-01-01','YYYY-MM-DD'), 'book_2', 1, 1, 'description');

insert into BOOK (ID, ADD_RECORD_DATE, CAPTION, AUTHOR_ID, GENRE_ID, DESCRIPTION)
values (sq_book.nextval, to_date('2019-01-01','YYYY-MM-DD'), 'book_3', 1, 1, 'description');

--------------------------

insert into COMMENT (ID, BOOK_ID, ADD_RECORD_DATE, USER_NAME, TEXT)
values (sq_comment.nextval, 1, to_date('2019-01-01 10:01:01','YYYY-MM-DD HH24-MI-SS'), 'user1', 'text1');

insert into COMMENT (ID, BOOK_ID, ADD_RECORD_DATE, USER_NAME, TEXT)
values (sq_comment.nextval, 1, to_date('2019-01-01 10:01:02','YYYY-MM-DD HH24-MI-SS'), 'user2', 'text2');

insert into COMMENT (ID, BOOK_ID, ADD_RECORD_DATE, USER_NAME, TEXT)
values (sq_comment.nextval, 2, to_date('2019-01-01 10:01:03','YYYY-MM-DD HH24-MI-SS'), 'user3', 'text3');

insert into COMMENT (ID, BOOK_ID, ADD_RECORD_DATE, USER_NAME, TEXT)
values (sq_comment.nextval, 2, to_date('2019-01-01 10:01:04','YYYY-MM-DD HH24-MI-SS'), 'user4', 'text4');