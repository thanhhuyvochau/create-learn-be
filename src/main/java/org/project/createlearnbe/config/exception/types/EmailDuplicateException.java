package org.project.createlearnbe.config.exception.types;

public class EmailDuplicateException extends RuntimeException {
  public EmailDuplicateException() {
    super("Email already exists, please try a new one.");
  }
}
