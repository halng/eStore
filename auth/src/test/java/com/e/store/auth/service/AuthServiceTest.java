package com.e.store.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.e.store.auth.config.jwt.JwtUtilities;
import com.e.store.auth.constant.AccountRole;
import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.exception.BadRequestException;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.repositories.IRoleRepository;
import com.e.store.auth.services.AuthService;
import com.e.store.auth.services.IRefreshTokenService;
import com.e.store.auth.services.impl.AuthServiceImpl;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import jakarta.persistence.EntityNotFoundException;

public class AuthServiceTest {
    AuthServiceImpl       authService;
    IRoleRepository       roleRepository;
    IAuthRepository       authRepository;
    Account               account;
    Role                  role;
    PasswordEncoder       passwordEncoder;
    IRefreshTokenService  refreshTokenService;
    AuthenticationManager authenticationManager;
    JwtUtilities          jwtUtilities;
    Authentication authentication;

    @BeforeEach
    void setUp () {
        roleRepository = mock(IRoleRepository.class);
        authRepository = mock(IAuthRepository.class);
        account = mock(Account.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtilities = mock(JwtUtilities.class);
        authenticationManager = mock(AuthenticationManager.class);
        refreshTokenService = mock(IRefreshTokenService.class);
        authentication = mock(Authentication.class);
        authService = new AuthServiceImpl(authRepository, roleRepository, passwordEncoder, authenticationManager,
                                          jwtUtilities,
                                          refreshTokenService
        );

        role = Role.builder().roleName(AccountRole.ADMIN).build();
        account = Account.builder().id("111-222").status(AccountStatus.ACTIVE).email("admin@estore.com").username(
            "admin").password(passwordEncoder.encode("admin")).role(role).build();

    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenInvalidRole () {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 0L, "test@gmail.com");
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals("Role incorrect", badRequestException.getMessage());
    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenRoleNotFound () {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 5L, "test@gmail.com");
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals("Role with id not found", exception.getMessage());
    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenPasswordNotIdentical () {
        SignUpVm signUpVm = new SignUpVm("test", "test", "test_pass", 1L, "test@gmail.com");
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals("Password are not identical!", badRequestException.getMessage());
    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenEmailExist () {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
        when(authRepository.existsByEmail(anyString())).thenReturn(true);
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals(badRequestException.getMessage(), "Email %s already exist!".formatted(signUpVm.email()));
    }

    @Test
    void signUp_ShouldReturnCreated_WhenDataValid () {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
        role.setId(1L);
        role.setRoleName(AccountRole.BUYER);

        when(authRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encrypt_pass");

        ResponseEntity<HttpStatus> result = authService.signUp(signUpVm);

        assertEquals(result.getStatusCode().toString(), HttpStatus.CREATED.toString());
    }

    @Test
    void getAccountByUsername_ShouldReturnError_whenUsernameNotFound() {
        SignInVm signInVm = new SignInVm("hello", "NoPass");
        UsernameNotFoundException usernameNotFoundException = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            authService.getAccountByUsername("any");
        });

        assertEquals("Username not found", usernameNotFoundException.getMessage());
    }

    @Test
    void getAccountByUsername_ShouldReturnError_whenDataValid() {
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(account));

        assertEquals("admin",account.getUsername());
        assertEquals("admin@estore.com",account.getEmail());
    }
}
