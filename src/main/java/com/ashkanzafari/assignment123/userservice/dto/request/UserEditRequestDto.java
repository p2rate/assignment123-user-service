package com.ashkanzafari.assignment123.userservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserEditRequestDto.
 *
 * <p></p>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequestDto {

  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
}
