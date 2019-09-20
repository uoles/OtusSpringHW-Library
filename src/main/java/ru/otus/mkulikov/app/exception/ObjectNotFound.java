package ru.otus.mkulikov.app.exception;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 03.09.2019
 * Time: 1:58
 */

public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound(String obj, String id) {
        super("Not found object - " + obj + ", id - " + id);
    }
}
