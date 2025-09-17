package org.project.createlearnbe.config.exception.types;

public class UserNameDuplicateException extends RuntimeException {
    public UserNameDuplicateException() {
        super("User name is existed, try new one");
    }
}
