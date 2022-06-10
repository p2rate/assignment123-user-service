package com.ashkanzafari.assignment123.userservice.service;

import com.ashkanzafari.assignment123.userservice.exception.DuplicateRoleCreationException;
import com.ashkanzafari.assignment123.userservice.exception.RoleNotFoundException;
import com.ashkanzafari.assignment123.userservice.model.Role;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RoleService.
 *
 * <p>The CRUD service methods for Roles</p>
 */
@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  /**
   * The service layer method to add a new Role to DB
   *
   * @param t
   * @return
   * @throws DuplicateRoleCreationException
   */
  public Role add(Role t) throws DuplicateRoleCreationException {
    Optional<Role> odbRole = roleRepository.findByRoleAndDeletedIsFalse(t.getRole());
    if (odbRole.isPresent()) {
      throw new DuplicateRoleCreationException();
    }
    t.setDeleted(false);
    t = roleRepository.save(t);
    return t;
  }

  /**
   * Service layer method to edit an existing role entry
   *
   * @param t
   * @return
   * @throws RoleNotFoundException
   */
  public Role edit(Role t) throws RoleNotFoundException {
    Role dbRole = roleRepository.findByIdAndDeletedIsFalse(t.getId())
            .orElseThrow(() -> new RoleNotFoundException());
    t = roleRepository.save(t);
    return t;

  }

  /**
   * Service layer method to mark a Role entry as deleted
   *
   * @param id
   * @return
   * @throws RoleNotFoundException
   */
  public Role delete(String id) throws RoleNotFoundException {
    Role dbRole = roleRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RoleNotFoundException());
    dbRole.setDeleted(true);
    return roleRepository.save(dbRole);

  }

  /**
   * Service layer method to get a Role by id
   *
   * @param id
   * @return
   * @throws RoleNotFoundException
   */
  public Role getById(String id) throws RoleNotFoundException {
    Role dbRole = roleRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RoleNotFoundException());
    return dbRole;
  }

  /**
   * Service layer method to get a Role by role name
   *
   * @param role
   * @return
   * @throws RoleNotFoundException
   */
  public Role getByRole(String role) throws RoleNotFoundException {
    Role dbRole = roleRepository.findByRoleAndDeletedIsFalse(role).orElseThrow(() -> new RoleNotFoundException());
    return dbRole;
  }

  /**
   * Get all roles in DB
   *
   * @return
   */
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

}
