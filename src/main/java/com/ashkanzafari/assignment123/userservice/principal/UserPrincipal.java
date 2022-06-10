package com.ashkanzafari.assignment123.userservice.principal;

import com.ashkanzafari.assignment123.userservice.model.User;
import com.ashkanzafari.assignment123.userservice.util.MyTimeUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * UserPrincipal.
 *
 * <p></p>
 */
@RequiredArgsConstructor
@Getter
public class UserPrincipal implements UserDetails {

  private final User user;
  private Set<GrantedAuthority> authorities = null;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities == null) {
      authorities = new HashSet<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
      user.getRole().getPermissions().stream().forEach(permission -> {
        authorities.add(
                new SimpleGrantedAuthority(permission.getPermission()));
      });
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return !user.isDeleted();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getActive();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    if (user.getPasswordExpire() != null
            && user.getPasswordExpire().before(new Timestamp(MyTimeUtil.calculateNow())))
      return false;
    else
      return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getActive();
  }

}
