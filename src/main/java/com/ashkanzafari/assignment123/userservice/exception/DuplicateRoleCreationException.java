package com.ashkanzafari.assignment123.userservice.exception;

/**
 * DuplicateRoleCreationException.
 *
 * <p></p>
 */
public class DuplicateRoleCreationException extends RuntimeException {

  public DuplicateRoleCreationException() {
  }

  public DuplicateRoleCreationException(String message) {
    super(message);
  }

  public DuplicateRoleCreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateRoleCreationException(Throwable cause) {
    super(cause);
  }

  public DuplicateRoleCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
