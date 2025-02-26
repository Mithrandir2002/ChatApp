package org.peppermint.ChatApp.exception;

public class UserExistedException extends RuntimeException{
    public UserExistedException(String message) {
        super(message);
    }
}
