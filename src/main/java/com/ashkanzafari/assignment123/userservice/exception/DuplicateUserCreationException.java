package com.ashkanzafari.assignment123.userservice.exception;

/**
 * DuplicateUserCreationException.
 *
 * <p></p>
 */
public class DuplicateUserCreationException extends RuntimeException {

  public DuplicateUserCreationException() {
  }

  public DuplicateUserCreationException(String message) {
    super(message);
  }

  public DuplicateUserCreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateUserCreationException(Throwable cause) {
    super(cause);
  }

  public DuplicateUserCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
