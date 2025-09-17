package org.project.createlearnbe.config.exception.types;

public class PhoneDuplicateException extends RuntimeException {
    public PhoneDuplicateException() {
        super("Phone number is existed, try new one");
    }
}
