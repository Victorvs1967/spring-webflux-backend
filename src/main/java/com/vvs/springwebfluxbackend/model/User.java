package com.vvs.springwebfluxbackend.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document("users")
public class User implements UserDetails {
  
  @Id
  private String id;

  @Indexed(background = true, unique = true)
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String avatar;
  private Instant onCreated;
  private Instant onUpdated;
  private UserRole role = UserRole.USER;
  private boolean isActive = true;

  public User() {
    onCreated = Instant.now();
    onUpdated = onCreated;
  }

  public void setOnUpdated() {
    onUpdated = Instant.now();
  }

  public String getFullName() {
    return firstName != null ? firstName.concat(" ").concat(lastName) : "";
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }

}
