package com.ashkanzafari.assignment123.userservice.service;


import com.ashkanzafari.assignment123.userservice.exception.DuplicatePermissionCreationException;
import com.ashkanzafari.assignment123.userservice.exception.PermissionNotFoundException;
import com.ashkanzafari.assignment123.userservice.model.Permission;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * PermissionService.
 *
 * <p>The CRUD service methods for permissions</p>
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PermissionService {

  private final PermissionRepository permissionRepository;

  /**
   * Service layer method to add a new permission to DB
   *
   * @param t
   * @return
   * @throws DuplicatePermissionCreationException
   */
  public Permission add(Permission t) throws DuplicatePermissionCreationException {

    Optional<Permission> odbPermission = permissionRepository.findByPermissionAndDeletedIsFalse(t.getPermission());
    if (odbPermission.isPresent()) {
      throw new DuplicatePermissionCreationException();
    }
    t = permissionRepository.save(t);
    return t;
  }

  /**
   * Service layer method to edit an existing permission
   *
   * @param t
   * @return
   * @throws PermissionNotFoundException
   */
  public Permission edit(Permission t) throws PermissionNotFoundException {
    Permission dbPermission = permissionRepository.findByIdAndDeletedIsFalse(t.getId())
            .orElseThrow(() -> new PermissionNotFoundException());
    t = permissionRepository.save(t);
    return t;
  }

  /**
   *The service method to mark a permission entry as deleted
   *
   * @param id
   * @return
   * @throws PermissionNotFoundException
   */
  public Permission delete(String id) throws PermissionNotFoundException {

    Permission dbPermission = permissionRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> new PermissionNotFoundException());
    dbPermission.setDeleted(true);
    return permissionRepository.save(dbPermission);
  }

  /**
   * Service method to get a permission by id
   *
   * @param id
   * @return
   * @throws PermissionNotFoundException
   */
  public Permission getById(String id) throws PermissionNotFoundException {

    Permission dbPermission = permissionRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> new PermissionNotFoundException());
    return dbPermission;
  }

  /**
   * Service method to get a permission by permission name
   *
   * @param permission
   * @return
   * @throws PermissionNotFoundException
   */
  public Permission getByPermission(String permission) throws PermissionNotFoundException {

    Permission dbPermission = permissionRepository.findByPermissionAndDeletedIsFalse(permission)
            .orElseThrow(() -> new PermissionNotFoundException());
    return dbPermission;
  }


  public void flush() {
    permissionRepository.flush();
  }
}
