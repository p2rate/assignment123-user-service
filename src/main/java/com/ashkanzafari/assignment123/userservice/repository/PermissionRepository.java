package com.ashkanzafari.assignment123.userservice.repository;


import com.ashkanzafari.assignment123.userservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PermissionRepository.
 *
 * <p></p>
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

  Optional<Permission> findByPermissionAndDeletedIsFalse(String permission);

  Optional<Permission> findByIdAndDeletedIsFalse(String id);
}
