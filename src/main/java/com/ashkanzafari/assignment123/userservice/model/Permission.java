package com.ashkanzafari.assignment123.userservice.model;

import com.ashkanzafari.assignment123.userservice.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Permission.
 *
 * <p>Class to persist what permissions each Role has</p>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "permission_index", columnList = "permission", unique = true)
})
public class Permission extends Auditable implements Serializable {

  @Builder.Default
  private static final long serialVersionUID = 1L;

  @NotNull
  @NotEmpty
  String permission;

  @JsonIgnore
  @Builder.Default
  @ManyToMany(mappedBy = "permissions")
  Set<Role> roles = new HashSet<>();

  public Permission(String permission) {
    super();
    this.permission = permission;
  }
}
