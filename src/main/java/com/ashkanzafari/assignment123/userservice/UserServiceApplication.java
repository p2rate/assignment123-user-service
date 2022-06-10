package com.ashkanzafari.assignment123.userservice;

import com.ashkanzafari.assignment123.userservice.dto.request.UserEditRequestDto;
import com.ashkanzafari.assignment123.userservice.model.Permission;
import com.ashkanzafari.assignment123.userservice.model.Role;
import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.service.PermissionService;
import com.ashkanzafari.assignment123.userservice.service.RoleService;
import com.ashkanzafari.assignment123.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@Log4j2
public class UserServiceApplication implements CommandLineRunner {

  @Autowired
  RoleService roleService;
  @Autowired
  PermissionService permissionService;
  @Autowired
  UserService userService;

  static final String ADMIN_USERNAME = "admin";
  static final String ADMIN_PASSWORD = "password!!!";
  static final String ADMIN_EMAIL = "admin@sth.com";

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    Permission readLev1 = new Permission("ReadLev1");
    addPermission(readLev1);

    Permission readLev2 = new Permission("ReadLev2");
    addPermission(readLev2);

    Permission readLev3 = new Permission("ReadLev3");
    addPermission(readLev3);

    Permission writeLev1 = new Permission("WriteLev1");
    addPermission(writeLev1);

    Permission writeLev2 = new Permission("WriteLev2");
    addPermission(writeLev2);

    Permission writeLev3 = new Permission("WriteLev3");
    addPermission(writeLev3);

    permissionService.flush();

    Role adminRole = Role.builder().role("ADMINISTRATOR")
            .permissions(new HashSet<>(Arrays.asList(readLev1, readLev2, readLev3, writeLev1, writeLev2, writeLev3)))
            .build();
    addRole(adminRole);

    Role userRole = Role.builder().role("USER")
            .permissions(new HashSet<>(Arrays.asList(readLev1, writeLev1))).build();
    addRole(userRole);

    User admin = User.builder().username(ADMIN_USERNAME)
            .firstName("adminFirstName").lastName("adminLastName")
            .password(ADMIN_PASSWORD).email(ADMIN_EMAIL)
				.role(adminRole)
            .build();
    addAdmin(admin);

    User user1 = User.builder().username("user1")
            .firstName("user1FirstName").lastName("user1LastName")
            .password("password1").email("user@sth.com")
            .role(userRole)
            .build();
    addUser(user1);
  }

  private void addPermission(Permission permission){
    try{
      permissionService.add(permission);
    }
    catch (Exception e){
      // if permission already exists do nothing
    }
  }

  private void addRole(Role role){
    try{
      roleService.add(role);
    }
    catch (Exception e){
      //if role already exists do nothing
    }
  }

  private void addUser(User user){
    try{
      User dbUser = userService.add(user);
    }
    catch (Exception e){
      // if user exists do nothing
    }
  }

  private void addAdmin(User user){
    try{
      User dbUser = userService.add(user);
      UserEditRequestDto administrator = new UserEditRequestDto(dbUser.getUsername(), dbUser.getFirstName(), dbUser.getLastName(), dbUser.getEmail(),
              "ADMINISTRATOR");
      userService.edit(administrator);
    }
    catch (Exception e){
      // if user exists do nothing
      log.info("error");
    }
  }

}
