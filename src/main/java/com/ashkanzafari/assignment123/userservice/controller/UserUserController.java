package com.ashkanzafari.assignment123.userservice.controller;

import com.ashkanzafari.assignment123.userservice.dto.response.ApiResponse;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The endpoints to manage users to be invoked by normal users
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserUserController {

  private final UserService userService;

  /**
   * Service layer method to get authenticated user info
   *
   * @param principal
   * @return
   */
  @PreAuthorize("hasAuthority('ReadLev1')")
  @GetMapping
  public ApiResponse<User> getUserInfo(Principal principal) {
    User user = userService.getById(principal.getName());
    return new ApiResponse<>(user, HttpStatus.OK, "OK");
  }




}
