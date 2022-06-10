package com.ashkanzafari.assignment123.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * ApiErrorResponse.
 *
 * <p>Model that is used to represent general response on API errors.</p>
 */
@Getter
@Setter
@Log4j2
@ToString
public class ErrorResponse extends ApiResponse {

    private final List<String> errors;

    /**
     * Main constructor method
     *
     * @param httpStatus of response
     * @param message of response
     * @param errors that we need to display
     */
    public ErrorResponse(final HttpStatus httpStatus, final String message, final List<String> errors) {
        super(null, httpStatus, message);
        this.errors = errors;
    }
}
