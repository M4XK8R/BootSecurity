package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  GUEST,
  USER,
  ADMIN,
  MODERATOR;

  private static final String PREFIX = "ROLE_";

  @Override
  public String getAuthority() {
    return PREFIX + name();
  }
}
