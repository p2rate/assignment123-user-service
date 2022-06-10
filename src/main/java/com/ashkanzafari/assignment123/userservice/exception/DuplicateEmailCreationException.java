package com.ashkanzafari.assignment123.userservice.exception;

/**
 * DuplicateEmailCreationException.
 *
 * <p></p>
 */
public class DuplicateEmailCreationException extends RuntimeException {

  public DuplicateEmailCreationException() {
  }

  public DuplicateEmailCreationException(String message) {
    super(message);
  }

  public DuplicateEmailCreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateEmailCreationException(Throwable cause) {
    super(cause);
  }

  public DuplicateEmailCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
