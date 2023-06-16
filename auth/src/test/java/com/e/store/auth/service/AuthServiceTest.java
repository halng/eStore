package com.e.store.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.auth.constant.Const;
import com.e.store.auth.entity.VerifyAccount;
import com.e.store.auth.exception.InternalErrorException;
import com.e.store.auth.repositories.IVerifyAccountRepository;
import com.e.store.auth.services.AuthService;
import com.e.store.auth.services.IMessageProducer;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.e.store.auth.services.IRefreshTokenService;
import com.e.store.auth.services.impl.AuthServiceImpl;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import jakarta.persistence.EntityNotFoundException;

public class AuthServiceTest {

    AuthService authService;
    IRoleRepository roleRepository;
    IAuthRepository authRepository;
    Account account;
    Role role;
    PasswordEncoder passwordEncoder;
    IRefreshTokenService refreshTokenService;
    AuthenticationManager authenticationManager;
    JwtUtilities jwtUtilities;
    Authentication authentication;
    IMessageProducer iMessageProducer;
    IVerifyAccountRepository iVerifyAccountRepository;

    @BeforeEach
    void setUp() {
        roleRepository = mock(IRoleRepository.class);
        authRepository = mock(IAuthRepository.class);
        account = mock(Account.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtilities = mock(JwtUtilities.class);
        authenticationManager = mock(AuthenticationManager.class);
        refreshTokenService = mock(IRefreshTokenService.class);
        authentication = mock(Authentication.class);
        iMessageProducer = mock(IMessageProducer.class);
        iVerifyAccountRepository = mock(IVerifyAccountRepository.class);
        authService = new AuthServiceImpl(authRepository, roleRepository, passwordEncoder, authenticationManager,
            jwtUtilities, refreshTokenService, iMessageProducer, iVerifyAccountRepository
        );

        role = Role.builder().roleName(AccountRole.ADMIN).build();
        account = Account.builder().id("111-222").status(AccountStatus.ACTIVE).email("admin@estore.com")
            .username("admin").password(passwordEncoder.encode("admin")).role(role).build();

    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenInvalidRole() {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 0L, "test@gmail.com");
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals("Role incorrect", badRequestException.getMessage());
    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenPasswordNotIdentical() {
        SignUpVm signUpVm = new SignUpVm("test", "test", "test_pass", 1L, "test@gmail.com");
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals("Password are not identical!", badRequestException.getMessage());
    }

    @Test
    void signUp_ShouldReturnErrorMessage_WhenEmailExist() {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
        when(authRepository.existsByEmail(anyString())).thenReturn(true);
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            authService.signUp(signUpVm);
        });
        assertEquals(badRequestException.getMessage(), "Email %s already exist!".formatted(signUpVm.email()));
    }

    @Test
    void signUp_ShouldReturnCreated_WhenDataValid() {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
        VerifyAccount verifyAccount = VerifyAccount.builder().token("token").account(account).expiryDate(Instant.now().plusSeconds(
            Const.DEFAULT_TIME_EXPIRY_CONFIRM_ACCOUNT)).build();
        role.setId(1L);
        role.setRoleName(AccountRole.BUYER);

        when(authRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encrypt_pass");
        when(authRepository.save(any())).thenReturn(account);
        when(iVerifyAccountRepository.save((any()))).thenReturn(verifyAccount);

        ResponseEntity<HttpStatus> result = authService.signUp(signUpVm);

        assertEquals(result.getStatusCode().toString(), HttpStatus.CREATED.toString());
    }

    @Test
    void signUp_ShouldReturnError_WhenAccountInvalid() {
        SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
        role.setId(1L);
        role.setRoleName(AccountRole.BUYER);

        when(authRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encrypt_pass");
        when(authRepository.save(any())).thenReturn(account);
        account.setId(null);

        InternalErrorException exception = Assertions.assertThrows(InternalErrorException.class, () -> {
            authService.signUp(signUpVm);

        });

        assertEquals(exception.getMessage(),"Server can't create account correctly. Try again!");
    }

    @Test
    void getAccountByUsername_ShouldReturnError_whenUsernameNotFound() {
        SignInVm signInVm = new SignInVm("hello", "NoPass");
        UsernameNotFoundException usernameNotFoundException = Assertions.assertThrows(UsernameNotFoundException.class,
            () -> {
                authService.loadAccountByUsername("any");
            });

        assertEquals("Username not found", usernameNotFoundException.getMessage());
    }

    @Test
    void getAccountByUsername_ShouldReturnError_whenDataValid() {
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
        assertEquals("admin", account.getUsername());
        assertEquals("admin@estore.com", account.getEmail());
    }
}
