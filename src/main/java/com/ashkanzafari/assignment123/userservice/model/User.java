package com.ashkanzafari.assignment123.userservice.model;

import com.ashkanzafari.assignment123.userservice.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User.
 *
 * <p>Class to persist User info on DB</p>
 */
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable implements Serializable {

  @JsonIgnore
  private static final long serialVersionUID = 1;

  @NotNull
  @NotEmpty
  private String username;

  @JsonIgnore
  @NotNull
  @NotEmpty
  private String password;

  @NotNull
  @NotEmpty
  private String firstName;

  @NotNull
  @NotEmpty
  private String lastName;


  private String email;

  @JsonIgnore
  private Boolean active;

  @JsonIgnore
  private Timestamp passwordExpire;

  @ManyToOne(fetch = FetchType.EAGER)
  private Role role;

}
