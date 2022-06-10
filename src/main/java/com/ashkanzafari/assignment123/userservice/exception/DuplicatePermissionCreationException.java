package com.ashkanzafari.assignment123.userservice.exception;

/**
 * DuplicatePermissionCreationException.
 *
 * <p></p>
 */
public class DuplicatePermissionCreationException extends RuntimeException {

  public DuplicatePermissionCreationException() {
  }

  public DuplicatePermissionCreationException(String message) {
    super(message);
  }

  public DuplicatePermissionCreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicatePermissionCreationException(Throwable cause) {
    super(cause);
  }

  public DuplicatePermissionCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
