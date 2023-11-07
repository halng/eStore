package com.e.store.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.auth.constant.AccountRole;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.services.impl.UserDetailsServiceImpl;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceTest {

  UserDetailsServiceImpl userDetailsService;
  IAuthRepository authRepository;

  @BeforeEach
  void setUp() {
    authRepository = mock(IAuthRepository.class);
    userDetailsService = new UserDetailsServiceImpl(authRepository);
  }

  @Test
  void loadUserByUsername_shouldThrowException_whenDataInvalid() {
    UsernameNotFoundException usernameNotFoundException =
        Assert.assertThrows(
            UsernameNotFoundException.class,
            () -> {
              userDetailsService.loadUserByUsername("admin");
            });

    assertEquals("Username admin not found", usernameNotFoundException.getMessage());
  }

  @Test
  void loadUserByUsername_shouldReturnUser_whenDataValid() {
    Role role = Role.builder().id(1L).roleName(AccountRole.BUYER).build();
    Account account =
        Account.builder().username("username").password("password").role(role).build();

    when(authRepository.findByUsername("username")).thenReturn(Optional.of(account));

    UserDetails userDetailsExpected = userDetailsService.loadUserByUsername("username");

    assertNotNull(userDetailsExpected);
    assertEquals(userDetailsExpected.getUsername(), account.getUsername());
    assertEquals(
        userDetailsExpected.getAuthorities().toString(), account.getAuthorities().toString());
    assertEquals(userDetailsExpected.getPassword(), account.getPassword());
  }
}
