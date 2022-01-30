package com.svyazda.utils.exceptions;

public class UserDoesNotExistException extends Exception {
    
    public UserDoesNotExistException(Integer id) {
        super("Invalid user: " + id);
    }
}
