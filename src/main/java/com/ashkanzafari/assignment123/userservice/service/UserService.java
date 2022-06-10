package com.ashkanzafari.assignment123.userservice.service;

import com.ashkanzafari.assignment123.userservice.config.RegistrationConfig;
import com.ashkanzafari.assignment123.userservice.dto.request.UserEditRequestDto;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateEmailCreationException;
import com.ashkanzafari.assignment123.userservice.exception.DuplicateUserCreationException;
import com.ashkanzafari.assignment123.userservice.exception.OperationNotAllowedException;
import com.ashkanzafari.assignment123.userservice.exception.RoleNotFoundException;
import com.ashkanzafari.assignment123.userservice.exception.UserNotFoundException;
import com.ashkanzafari.assignment123.userservice.model.Role;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.repository.UserRepository;
import com.ashkanzafari.assignment123.userservice.util.MyTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * UserService.
 *
 * <p>The CRUD service methods for Users</p>
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final RoleService roleService;
  private final RegistrationConfig registrationConfig;

  /**
   * Service layer method to add a new user to DB
   *
   * @param user
   * @return
   * @throws DuplicateEmailCreationException
   * @throws DuplicateUserCreationException
   * @throws RoleNotFoundException
   */
  public User add(User user)
          throws DuplicateEmailCreationException, DuplicateUserCreationException, RoleNotFoundException {
    Optional<User> userByUsername = userRepository.findByUsernameAndDeletedIsFalse(user.getUsername());
    Optional<User> userByUsernameDeleted = userRepository.findByUsername(user.getUsername());
    Optional<User> userByEmail = userRepository.findByEmailAndDeletedIsFalse(user.getEmail());
    Optional<User> userByEmailDeleted = userRepository.findByEmail(user.getEmail());

    if (userByUsername.isPresent())
      throw new DuplicateUserCreationException();
    if (userByEmail.isPresent())
      throw new DuplicateEmailCreationException();
    if (userByEmailDeleted.isPresent())
      userRepository.delete(userByEmailDeleted.get());
    if (userByUsernameDeleted.isPresent())
      userRepository.delete(userByUsernameDeleted.get());

    Timestamp now = new Timestamp(MyTimeUtil.calculateNow());
    user.setActive(true);
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole(roleService.getByRole("USER"));
    user.setPasswordExpire(new Timestamp(MyTimeUtil.calculateExpiryDateInDays(
            registrationConfig.getPasswordExpiryInDays())));
    user = userRepository.save(user);
    return user;
  }

  /**
   * Service layer method to edit an existing user. cannot update the following using this method: password,username
   *
   * @param newUser
   * @return
   * @throws UserNotFoundException
   */
  public User edit(UserEditRequestDto newUser)
          throws UserNotFoundException, RoleNotFoundException {

    Role newRole = roleService.getByRole(newUser.getRole());
    User dbUser = userRepository.findByUsernameAndDeletedIsFalse(newUser.getUsername())
            .orElseThrow(() -> new UserNotFoundException());
    dbUser.setEmail(newUser.getEmail());
    dbUser.setFirstName(newUser.getFirstName());
    dbUser.setLastName(newUser.getLastName());
    dbUser.setRole(newRole);
    return userRepository.save(dbUser);
  }

  /**
   * Service layer method to edit the password of a user
   *
   * @param username
   * @param newPassword
   * @return
   * @throws UserNotFoundException
   */
  public User editPassword(String username, String newPassword) throws UserNotFoundException {
    User dbUser = userRepository.findByUsernameAndDeletedIsFalse(username)
            .orElseThrow(() -> new UserNotFoundException());
    dbUser.setPassword(encoder.encode(newPassword));
    return userRepository.save(dbUser);
  }

  /**
   * Service layer method to mark a user as deleted
   *
   * @param username
   * @return
   * @throws UserNotFoundException
   * @throws OperationNotAllowedException
   */
  public User delete(String username)
          throws UserNotFoundException, OperationNotAllowedException {

    User dbUser = userRepository.findByUsernameAndDeletedIsFalse(username)
            .orElseThrow(() -> new UserNotFoundException());
    dbUser.setDeleted(true);
    return userRepository.save(dbUser);
  }

  /**
   * Service layer method to get all users. if user is an admin will return all users otherwise it will return
   * only the users with user role
   * @param executor
   * @return
   */
  public List<User> getAll(UsernamePasswordAuthenticationToken executor) {
    if (executor.getAuthorities().stream().filter(auth -> auth.getAuthority().equals(
            "ROLE_ADMINISTRATOR")).findAny().isPresent())
      return userRepository.findAllNonDeleted();
    else
      return userRepository.findAllNonAdmin();
  }

  /**
   * Service layer method to get users by a role
   * @param role
   * @return
   * @throws RoleNotFoundException
   */
  public List<User> getByRole(String role) throws RoleNotFoundException {
    Role dbRole = roleService.getByRole(role);
    return userRepository.findByRole(dbRole);
  }

  /**
   * Service layer method to get a user by id
   *
   * @param id
   * @return
   * @throws UserNotFoundException
   */
  public User getById(String id) throws UserNotFoundException {

    User dbUser = userRepository.findByIdAndDeletedIsFalse(id)
            .orElse(null);
    return dbUser;
  }

  /**
   * Service layer method to get a user by username
   *
   * @param username
   * @return
   * @throws UserNotFoundException
   */
  public User getByUsername(String username) throws UserNotFoundException {
    return userRepository.findByUsernameAndDeletedIsFalse(username)
            .orElseThrow(() -> new UserNotFoundException());
  }

  /**
   * Service layer method to get a user by email
   *
   * @param email
   * @return
   * @throws UserNotFoundException
   */
  public User getByEmail(String email) throws UserNotFoundException {
    User user = null;
    User dbUser = userRepository.findByEmailAndDeletedIsFalse(email)
            .orElseThrow(() -> new UserNotFoundException());
    return dbUser;
  }

  public boolean blockUser(String username) throws UserNotFoundException {

    User dbUser = getByUsername(username);
    dbUser.setActive(false);
    dbUser = userRepository.save(dbUser);
    return dbUser.getActive();
  }

  /**
   * Service layer method to disable user
   * @param username
   * @return
   * @throws UserNotFoundException
   */
  public boolean disableUser(String username) throws UserNotFoundException {

    User dbUser = getByUsername(username);
    dbUser.setActive(true);
    dbUser = userRepository.save(dbUser);
    return dbUser.getActive();
  }
}
