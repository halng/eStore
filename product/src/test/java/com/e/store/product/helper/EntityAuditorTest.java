package com.e.store.product.helper;

import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityAuditorTest {
  EntityAuditorAware auditorAware;

  @BeforeEach
  void setup() {
    auditorAware = new EntityAuditorAware();
  }

  @Test
  void getCurrentAuditorTest() {
    Authentication authentication =
        new Authentication() {
          @Override
          public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
          }

          @Override
          public Object getCredentials() {
            return null;
          }

          @Override
          public Object getDetails() {
            return null;
          }

          @Override
          public Object getPrincipal() {
            return null;
          }

          @Override
          public boolean isAuthenticated() {
            return false;
          }

          @Override
          public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

          @Override
          public String getName() {
            return "admin";
          }
        };
    SecurityContextHolder.getContext().setAuthentication(authentication);
    Optional<String> user = auditorAware.getCurrentAuditor();
    Assertions.assertEquals("admin", user.get());
  }

  @Test
  void getCurrentAuditorTest_whenChangedBySystem() {
    SecurityContextHolder.getContext().setAuthentication(null);
    Optional<String> user = auditorAware.getCurrentAuditor();
    Assertions.assertEquals("SYSTEM", user.get());
  }
}
