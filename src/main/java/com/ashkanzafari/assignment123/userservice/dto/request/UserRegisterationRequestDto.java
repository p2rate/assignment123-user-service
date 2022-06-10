package com.ashkanzafari.assignment123.userservice.dto.request;

import com.ashkanzafari.assignment123.userservice.validation.PasswordMatches;
import com.ashkanzafari.assignment123.userservice.validation.ValidEmail;
import com.ashkanzafari.assignment123.userservice.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * UserRegisterationRequestDto.
 *
 * <p>The DTO for accepting user registration requests</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatches(message = "validation.password.notmatches")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterationRequestDto {

  @NotNull(message = "validation.username.notnull")
  @NotEmpty(message = "validation.username.notempty")
  @JsonProperty(value = "username")
  private String username;

  @NotNull(message = "validation.password.notnull")
  @NotEmpty(message = "validation.password.notempty")
  @ValidPassword(message = "validation.password.notvalid")
  @JsonProperty(value = "password")
  private String password;

  @NotNull(message = "validation.confirmPassword.notnull")
  @NotEmpty(message = "validation.confirmPassword.notempty")
  @JsonProperty(value = "passwordConfirm")
  private String passwordConfirm;

  @NotNull(message = "validation.firstName.notnull")
  @NotEmpty(message = "validation.firstName.notempty")
  @JsonProperty(value = "firstName")
  private String firstName;

  @NotNull(message = "validation.lastName.notnull")
  @NotEmpty(message = "validation.lastName.notempty")
  @JsonProperty(value = "lastName")
  private String lastName;

  @ValidEmail(message = "validation.email.notvalid")
  @JsonProperty(value = "email")
  private String email;
}
