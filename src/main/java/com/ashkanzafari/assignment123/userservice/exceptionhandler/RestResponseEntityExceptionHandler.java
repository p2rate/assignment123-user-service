package com.ashkanzafari.assignment123.userservice.exceptionhandler;

import com.ashkanzafari.assignment123.userservice.dto.response.ApiResponse;
import com.ashkanzafari.assignment123.userservice.dto.response.ErrorResponse;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateEmailCreationException;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateUserCreationException;
import com.ashkanzafari.assignment123.userservice.exception.OperationNotAllowedException;
import com.ashkanzafari.assignment123.userservice.exception.UserNotFoundException;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.lang.model.type.ErrorType;
import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RestResponseEntityExceptionHandler.
 *
 * <p>Class that handles application exceptions.</p>
 */
@Log4j2
@ToString
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle method argument not valid
     *
     * @param ex current exception
     * @param headers that we received
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final List<String> errors = new ArrayList<String>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        final ErrorResponse apiErrorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiErrorResponse, headers, apiErrorResponse.getStatus(), request);
    }

    /**
     * Missing servlet request parameter exception
     *
     * @param ex current exception
     * @param headers current header
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        final MissingServletRequestParameterException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final String error = ex.getParameterName() + " parameter is missing";
        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getLocalizedMessage(),
            Collections.singletonList(error)
        );

        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * Constraint Validation exception
     *
     * @param ex current exception
     * @param request current request
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
        final ConstraintViolationException ex,
        final WebRequest request
    ) {

        final List<String> errors = new ArrayList<String>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ErrorResponse apiErrorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * Method argument type mismatch exception
     *
     * @param ex current exception
     * @param request current request
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        final MethodArgumentTypeMismatchException ex,
        final WebRequest request
    ) {

        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();


        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getLocalizedMessage(),
            Collections.singletonList(error)
        );
        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * No handler found exception
     *
     * @param ex current exception
     * @param headers current header
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        final NoHandlerFoundException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();


        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.getLocalizedMessage(),
            Collections.singletonList(error)
        );

        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * Method not supported exception
     *
     * @param ex current exception
     * @param headers current headers
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        final HttpRequestMethodNotSupportedException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final StringBuilder builder = new StringBuilder();

        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.METHOD_NOT_ALLOWED,
            ex.getLocalizedMessage(),
            Collections.singletonList(builder.toString())
        );

        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * Media Type not supported exception
     *
     * @param ex current exception
     * @param headers current headers
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        final HttpMediaTypeNotSupportedException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final StringBuilder builder = new StringBuilder();

        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            ex.getLocalizedMessage(),
            Collections.singletonList(builder.substring(0, builder.length() - 2))
        );
        return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    /**
     * Bad request exception
     *
     * @param ex current exception
     * @param request current request
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(
        final DataIntegrityViolationException ex,
        final WebRequest request
    ) {

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getLocalizedMessage(),
            Collections.singletonList("error occurred")
        );

        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus(), request);
    }

    /**
     * Message Not Readable exception
     *
     * @param ex current exception
     * @param headers current5 headers
     * @param status current status
     * @param request current request
     *
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        final HttpMessageNotReadableException ex,
        @NonNull final HttpHeaders headers,
        @NonNull final HttpStatus status,
        @NonNull final WebRequest request
    ) {

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getLocalizedMessage(),
            Collections.singletonList("error occurred")
        );

        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        return handleExceptionInternal(ex, apiErrorResponse, headers, apiErrorResponse.getStatus(), request);
    }

    /**
     * Method that is used in order to ensure handling of not found exception ( 404 )
     *
     * @param ex current caught exception
     * @param request request that is received
     *
     * @return ResponseEntity
     */
    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(
        final RuntimeException ex,
        final WebRequest request
    ) {

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.getLocalizedMessage(),
            Collections.singletonList("Not Found!")
        );

        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus(), request);
    }


    /**
     * Invalid data access exception
     *
     * @param ex current exception
     * @param request current request
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(
        final RuntimeException ex,
        final WebRequest request
    ) {

        final String bodyOfResponse = "This should be application specific";
        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.CONFLICT,
            ex.getLocalizedMessage(),
            Collections.singletonList("Not Found!")
        );

        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus(), request);
    }

    // 412

    // 500

    /**
     * Multiple Exceptions
     *
     * @param ex current exception
     * @param request current request
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(
        final RuntimeException ex,
        final WebRequest request
    ) {

        final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getLocalizedMessage(),
            Collections.singletonList("Internal Server Error")
        );

        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus(), request);
    }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DuplicateEmailCreationException.class)
  public ErrorResponse handleDuplicateEmailCreationException(DuplicateEmailCreationException ex) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "duplicate email", Arrays.asList(
            "email already exists"));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DuplicateUserCreationException.class)
  public ErrorResponse handleDuplicateUserCreationException(
          DuplicateUserCreationException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "duplicate user", Arrays.asList(
            "username already exists"));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RoleNotFoundException.class)
  public ErrorResponse handleRoleNotFoundException(
          RoleNotFoundException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "role not found", Arrays.asList(
            "role not found"));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserNotFoundException.class)
  public ErrorResponse handleUserNotFoundException(
          UserNotFoundException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "user not found", Arrays.asList(
            "user not found"));
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(OperationNotAllowedException.class)
  public ErrorResponse handleOperationNotAllowedException(
          OperationNotAllowedException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "not allowed", Arrays.asList(
            "operation is not allowed"));
  }


  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AccessDeniedException.class)
  public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.UNAUTHORIZED, "unauthorized", Arrays.asList(
            "you do not have authorization to this operation"));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UnexpectedTypeException.class)
  public ErrorResponse handleUnexpectedTypeException(UnexpectedTypeException ex, Locale locale) {

    return new ErrorResponse(HttpStatus.BAD_REQUEST, "unexpected type", Arrays.asList(
            "unexpected type"));
  }

  /**
   * Method to handle unhandled exceptions
   *
   * @param ex current exception
   * @param request current request
   *
   * @return ResponseEntity
   */
  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(
          final Exception ex,
          final WebRequest request
  ) {

    final ErrorResponse apiErrorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getLocalizedMessage(),
            Collections.singletonList("error occurred")
    );

    return new ResponseEntity<Object>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
  }

}
