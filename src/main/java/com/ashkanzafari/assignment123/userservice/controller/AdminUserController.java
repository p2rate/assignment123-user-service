package com.ashkanzafari.assignment123.userservice.controller;

import com.ashkanzafari.assignment123.userservice.dto.request.UserEditRequestDto;
import com.ashkanzafari.assignment123.userservice.dto.request.UserRegisterationRequestDto;
import com.ashkanzafari.assignment123.userservice.dto.response.ApiResponse;
import com.ashkanzafari.assignment123.userservice.exception.OperationNotAllowedException;
import com.ashkanzafari.assignment123.userservice.exception.UserNotFoundException;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.service.UserRegisterationService;
import com.ashkanzafari.assignment123.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * UserController.
 *
 * <p>Controller class to expose CRUD endpoints for user. the enpoints require admin level access</p>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/v1/user")
public class AdminUserController {

    private final UserRegisterationService registerationService;
    private final UserService userService;

  /**
   * The endpoint to return list of all users.
   *
   * @param principal
   * @return
   */
  @PreAuthorize("hasAuthority('ReadLev3')")
  @GetMapping
    public ApiResponse<List<User>> getAllUsers(Principal principal){

        List<User> users = userService.getAll((UsernamePasswordAuthenticationToken) principal);
        return new ApiResponse<>(users, HttpStatus.OK, "OK");
    }

  /**
   * The endpoint to get a user by username. requires ReadLev3
   * @param username
   * @param principal
   * @return
   */
    @PreAuthorize("hasAuthority('ReadLev3')")
    @GetMapping("/{username}")
    public ApiResponse<User> getUserByUsername(@PathVariable String username, Principal principal){
        User user = userService.getByUsername(username);
        return new ApiResponse<>(user, HttpStatus.OK, "OK");
    }

  @PreAuthorize("hasAuthority('ReadLev3')")
  @GetMapping("/id/{id}")
  public ApiResponse<User> getUserById(@PathVariable String id){
    User user = userService.getById(id);
    return new ApiResponse<>(user, HttpStatus.OK, "OK");
  }

  /**
   * The endpoint to get users by role. requires ReadLev3
   * @param role
   * @param principal
   * @return
   */
    @PreAuthorize("hasAuthority('ReadLev3')")
    @GetMapping("/getbyrole/{role}")
    public ApiResponse<List<User>> getUserByRole(@PathVariable String role, Principal principal){

        List<User> users = userService.getByRole(role);

        return new ApiResponse<>(users, HttpStatus.OK, "OK");
    }

  /**
   * The endpoint to create a new user. requires WriteLev3
   *
   * @param registerationRequest
   * @param principal
   * @return
   * @throws RoleNotFoundException
   */
    @PreAuthorize("hasAuthority('WriteLev3')")
    @PostMapping
    public ApiResponse<User> createUser(
            @RequestBody @Valid UserRegisterationRequestDto registerationRequest, Principal principal)
        throws RoleNotFoundException {

        return registerationService.processRegisteration(registerationRequest);
    }

  /**
   * The endpoint to edit an existing user. requires WriteLev3
   *
   * @param userEditRequestDtoRequest
   * @param principal
   * @return
   * @throws UserNotFoundException
   * @throws RoleNotFoundException
   */
    @PreAuthorize("hasAuthority('WriteLev3')")
    @PutMapping
    public ApiResponse<User> editUser(
            @RequestBody @Valid UserEditRequestDto userEditRequestDtoRequest, Principal principal)
        throws UserNotFoundException, RoleNotFoundException {

        User editedUser = userService.edit(userEditRequestDtoRequest);
        return new ApiResponse<>(editedUser, HttpStatus.OK, "OK");
    }

  /**
   * The endpoint to mark a user as deleted. requires WriteLev3
   * @param id
   * @param principal
   * @return
   * @throws UserNotFoundException
   * @throws OperationNotAllowedException
   */
    @PreAuthorize("hasAuthority('WriteLev3')")
    @DeleteMapping("/{id}")
    public ApiResponse<User> deleteUser(@PathVariable String id, Principal principal) throws
            UserNotFoundException, OperationNotAllowedException {

        User editedUser = userService.delete(id);
        return new ApiResponse<>(editedUser, HttpStatus.OK, "OK");
    }

}
