DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;

CREATE TABLE AUTHOR(
    ID NUMBER(22,0) PRIMARY KEY not null,
    SURNAME VARCHAR(100) not null,
    FIRST_NAME VARCHAR(100) not null,
    SECOND_NAME VARCHAR(100)
);

CREATE TABLE GENRE(
    ID NUMBER(22,0) PRIMARY KEY not null,
    NAME VARCHAR(100) not null
);

CREATE TABLE BOOK(
    ID NUMBER(22,0) PRIMARY KEY not null,
    ADD_RECORD_DATE DATE default sysdate,
    CAPTION VARCHAR(255) not null,
    AUTHOR_ID NUMBER(22,0) not null,
    GENRE_ID NUMBER(22,0) not null,
    COMMENT VARCHAR(255),

    foreign key (AUTHOR_ID) references AUTHOR(ID),
    foreign key (GENRE_ID) references GENRE(ID)
);

CREATE SEQUENCE sq_author minvalue 1 start with 1 increment by 1;
CREATE SEQUENCE sq_genre minvalue 1 start with 1 increment by 1;
CREATE SEQUENCE sq_book minvalue 1 start with 1 increment by 1;
