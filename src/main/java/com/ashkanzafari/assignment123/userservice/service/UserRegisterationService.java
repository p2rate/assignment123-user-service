package com.ashkanzafari.assignment123.userservice.service;


import com.ashkanzafari.assignment123.userservice.dto.request.UserRegisterationRequestDto;
import com.ashkanzafari.assignment123.userservice.dto.response.ApiResponse;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateEmailCreationException;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateUserCreationException;
import com.ashkanzafari.assignment123.userservice.exception.RoleNotFoundException;
import com.ashkanzafari.assignment123.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * UserRegisterationService.
 *
 * <p>The Service layer methods for user registration</p>
 */
@Service
@RequiredArgsConstructor
public class UserRegisterationService {

  private final UserService userService;

  /**
   *
   *
   * @param req
   * @return
   * @throws DuplicateEmailCreationException
   * @throws DuplicateUserCreationException
   * @throws RoleNotFoundException
   */
  public ApiResponse<User> processRegisteration(UserRegisterationRequestDto req)
          throws DuplicateEmailCreationException, DuplicateUserCreationException, RoleNotFoundException {

    User user = User.builder()
            .username(req.getUsername())
            .password(req.getPassword())
            .firstName(req.getFirstName())
            .lastName(req.getLastName())
            .email(req.getEmail())
            .build();

    User newUser = userService.add(user);

    return new ApiResponse<>(newUser, HttpStatus.OK, "OK");
  }

}
