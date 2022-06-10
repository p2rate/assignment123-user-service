package com.ashkanzafari.assignment123.userservice.service;

import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailsSerivceCustom.
 *
 * <p>The Class which is used by Spring Security to retrieve  user info</p>
 */
@Component
@RequiredArgsConstructor
public class UserDetailsSerivceCustom implements UserDetailsService {

  private final UserService userService;

  /**
   * The interface method to be implemented in order to retrieve a user by it's username
   *
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = null;
    try {
      user = userService.getByUsername(username);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (user != null) {
      return new UserPrincipal(user);
    } else {
      throw new UsernameNotFoundException("User : " + username + " not found.");
    }
  }
}
