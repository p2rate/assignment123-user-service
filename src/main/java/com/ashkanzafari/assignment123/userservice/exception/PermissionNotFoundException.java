package com.ashkanzafari.assignment123.userservice.exception;

/**
 * PermissionNotFoundException.
 *
 * <p></p>
 */
public class PermissionNotFoundException extends RuntimeException {

  public PermissionNotFoundException() {
  }

  public PermissionNotFoundException(String message) {
    super(message);
  }

  public PermissionNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PermissionNotFoundException(Throwable cause) {
    super(cause);
  }

  public PermissionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
