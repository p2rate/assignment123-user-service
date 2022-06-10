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
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Role.
 *
 * <p>Class to define the role of the User. each
 * Role has a set of corresponding Permissions.
 * access to endpoints are defined by the Permissions </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "role_index", columnList = "role", unique = true)
})
public class Role extends Auditable implements Serializable {

  @JsonIgnore
  @Builder.Default
  private static final long serialVersionUID = 1;

  @NotNull
  @NotEmpty
  private String role;

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "role")
  private Set<User> users = new HashSet<>();


  @Builder.Default
  @ManyToMany(fetch = FetchType.EAGER)
  Set<Permission> permissions = new HashSet<>();
}
