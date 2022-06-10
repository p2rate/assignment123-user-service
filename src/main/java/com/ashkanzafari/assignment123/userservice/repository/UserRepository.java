package com.ashkanzafari.assignment123.userservice.repository;

import com.ashkanzafari.assignment123.userservice.model.Role;
import com.ashkanzafari.assignment123.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository.
 *
 * <p></p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
  /* ********************************************************************************************
   * PUBLIC METHODS
   *********************************************************************************************/
  Optional<User> findByUsernameAndDeletedIsFalse(String username);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmailAndDeletedIsFalse(String email);

  Optional<User> findByEmail(String email);

  Optional<User> findByIdAndDeletedIsFalse(String id);

  List<User> findByRole(Role role);

  @Query("SELECT u FROM User u INNER JOIN u.role r WHERE u.deleted = false and r.role!='administrator'")
  List<User> findAllNonAdmin();

  @Query("SELECT u FROM User u WHERE u.deleted = false")
  List<User> findAllNonDeleted();

  @Query("SELECT u FROM User u INNER JOIN u.role r WHERE u.deleted = false and r.role!='administrator' and u.username=?1")
  Optional<User> findNonAdmin(String username);
}
