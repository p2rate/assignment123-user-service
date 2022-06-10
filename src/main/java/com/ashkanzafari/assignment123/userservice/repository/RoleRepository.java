package com.ashkanzafari.assignment123.userservice.repository;

import com.ashkanzafari.assignment123.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository.
 *
 * <p></p>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Optional<Role> findByRoleAndDeletedIsFalse(String role);

  Optional<Role> findByIdAndDeletedIsFalse(String id);
}
