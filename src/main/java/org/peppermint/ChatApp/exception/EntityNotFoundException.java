package org.peppermint.ChatApp.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id " + id + " doesn't exist in our record");
    }
}
