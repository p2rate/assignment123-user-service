package com.ashkanzafari.assignment123.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserAuthenticationDto.
 *
 * <p></p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationDto {

  private String username;
  private String password;
}
