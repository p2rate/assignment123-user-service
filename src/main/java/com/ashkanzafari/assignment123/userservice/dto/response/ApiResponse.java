package com.ashkanzafari.assignment123.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

/**
 * BaseApiResponse.
 *
 * <p>Class that is used to manage base for responses.</p>
 */
@Getter
@Setter
@Log4j2
@ToString
public class ApiResponse<T> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final T payload;
  private final HttpStatus status;
  private final Timestamp timestamp;
  private final String message;

  public ApiResponse(final T payload, final HttpStatus status, final String message) {

    this.payload = payload;
    this.status = status;
    this.timestamp = new Timestamp(System.currentTimeMillis());
    this.message = message;
  }

}
