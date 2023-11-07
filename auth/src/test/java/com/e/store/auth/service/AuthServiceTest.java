package com.e.store.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.auth.config.jwt.JwtUtilities;
import com.e.store.auth.constant.AccountRole;
import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.constant.Const;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.entity.VerifyAccount;
import com.e.store.auth.exception.BadRequestException;
import com.e.store.auth.exception.ForbiddenException;
import com.e.store.auth.exception.InternalErrorException;
import com.e.store.auth.exception.NotFoundException;
import com.e.store.auth.exception.TokenException;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.repositories.IRoleRepository;
import com.e.store.auth.repositories.IVerifyAccountRepository;
import com.e.store.auth.services.IAuthService;
import com.e.store.auth.services.IMessageProducer;
import com.e.store.auth.services.IRefreshTokenService;
import com.e.store.auth.services.impl.AuthServiceImpl;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import com.e.store.auth.viewmodel.res.ValidateAuthVm;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

class AuthServiceTest {

  IAuthService iAuthService;
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
    iAuthService =
        new AuthServiceImpl(
            authRepository,
            roleRepository,
            passwordEncoder,
            authenticationManager,
            jwtUtilities,
            refreshTokenService,
            iMessageProducer,
            iVerifyAccountRepository);

    role = Role.builder().roleName(AccountRole.ADMIN).build();
    account =
        Account.builder()
            .id("111-222")
            .status(AccountStatus.ACTIVE)
            .email("admin@estore.com")
            .username("admin")
            .password(passwordEncoder.encode("admin"))
            .role(role)
            .build();

    ReflectionTestUtils.setField(jwtUtilities, "jwtExpiration", 36000L);
    ReflectionTestUtils.setField(jwtUtilities, "secret", "hello_world");
  }

  @Test
  void signUp_ShouldReturnErrorMessage_WhenInvalidRole() {
    SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 0L, "test@gmail.com");
    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              iAuthService.signUp(signUpVm);
            });
    assertEquals("Role incorrect", badRequestException.getMessage());
  }

  @Test
  void signUp_ShouldReturnErrorMessage_WhenPasswordNotIdentical() {
    SignUpVm signUpVm = new SignUpVm("test", "test", "test_pass", 1L, "test@gmail.com");
    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              iAuthService.signUp(signUpVm);
            });
    assertEquals("Password are not identical!", badRequestException.getMessage());
  }

  @Test
  void signUp_ShouldReturnErrorMessage_WhenEmailExist() {
    SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
    when(authRepository.existsByEmail(anyString())).thenReturn(true);
    BadRequestException badRequestException =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              iAuthService.signUp(signUpVm);
            });
    assertEquals(
        badRequestException.getMessage(), "Email %s already exist!".formatted(signUpVm.email()));
  }

  @Test
  void signUp_ShouldReturnCreated_WhenDataValid() {
    SignUpVm signUpVm = new SignUpVm("test", "test_pass", "test_pass", 1L, "test@gmail.com");
    VerifyAccount verifyAccount =
        VerifyAccount.builder()
            .token("token")
            .account(account)
            .expiryDate(
                Instant.now()
                    .plusSeconds(Const.DEFAULT_TIME_EXPIRY_CONFIRM_ACCOUNT)
                    .getEpochSecond())
            .build();
    role.setId(1L);
    role.setRoleName(AccountRole.BUYER);

    when(authRepository.existsByEmail(anyString())).thenReturn(false);
    when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
    when(passwordEncoder.encode(anyString())).thenReturn("encrypt_pass");
    when(authRepository.save(any())).thenReturn(account);
    when(iVerifyAccountRepository.save((any()))).thenReturn(verifyAccount);

    ResponseEntity<HttpStatus> result = iAuthService.signUp(signUpVm);

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

    InternalErrorException exception =
        Assertions.assertThrows(
            InternalErrorException.class,
            () -> {
              iAuthService.signUp(signUpVm);
            });

    assertEquals("Server can't create account correctly. Try again!", exception.getMessage());
  }

  @Test
  void getAccountByUsername_ShouldReturnError_whenUsernameNotFound() {
    UsernameNotFoundException usernameNotFoundException =
        Assertions.assertThrows(
            UsernameNotFoundException.class,
            () -> {
              iAuthService.loadAccountByUsername("any");
            });

    assertEquals("Username any not found", usernameNotFoundException.getMessage());
  }

  @Test
  void getAccountByUsername_ShouldReturnError_whenDataValid() {
    when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(account));

    Account expected = iAuthService.loadAccountByUsername("username");

    assertEquals(expected.getUsername(), account.getUsername());
    assertEquals(expected.getEmail(), account.getEmail());
  }

  @Test
  void loadAccountByUsername_ShouldReturnError_whenUsernameNotFound() {
    UsernameNotFoundException usernameNotFoundException =
        Assertions.assertThrows(
            UsernameNotFoundException.class,
            () -> {
              iAuthService.loadAccountByUsername("any");
            });

    assertEquals("Username any not found", usernameNotFoundException.getMessage());
  }

  @Test
  void signIn_shouldReturnForbiddenException_whenAccountNotActive() {
    SignInVm signInVm = new SignInVm("hello", "NoPass");
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(signInVm.username(), signInVm.password());
    Authentication authentication1 =
        new Authentication() {
          @Override
          public boolean equals(Object another) {
            return false;
          }

          @Override
          public String toString() {
            return null;
          }

          @Override
          public int hashCode() {
            return 0;
          }

          @Override
          public String getName() {
            return null;
          }

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
            return true;
          }

          @Override
          public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
        };

    account.setStatus(AccountStatus.INACTIVE);
    when(authenticationManager.authenticate(token)).thenReturn(authentication1);
    when(authRepository.findByUsername(any())).thenReturn(Optional.of(account));

    ForbiddenException forbiddenException =
        Assertions.assertThrows(
            ForbiddenException.class,
            () -> {
              iAuthService.signIn(signInVm);
            });

    assertEquals(forbiddenException.getMessage(), "Account Not Active");
  }

  @Test
  void signIn_shouldReturn200_whenDataValid() {
    SignInVm signInVm = new SignInVm("hello", "NoPass");
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(signInVm.username(), signInVm.password());
    Authentication authentication1 =
        new Authentication() {
          @Override
          public boolean equals(Object another) {
            return false;
          }

          @Override
          public String toString() {
            return null;
          }

          @Override
          public int hashCode() {
            return 0;
          }

          @Override
          public String getName() {
            return null;
          }

          @Override
          public Collection<? extends GrantedAuthority> getAuthorities() {
            return account.getAuthorities();
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
            return true;
          }

          @Override
          public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
        };

    when(authenticationManager.authenticate(token)).thenReturn(authentication1);
    when(authRepository.findByUsername(any())).thenReturn(Optional.of(account));
    when(jwtUtilities.generateAccessToken(any(), any())).thenReturn("abc-xyz");
    when(refreshTokenService.generateRefreshToken(any())).thenReturn("123");

    ResponseEntity<AuthResVm> authResExpected = iAuthService.signIn(signInVm);

    Authentication expectedAuthenticate = SecurityContextHolder.getContext().getAuthentication();
    assertTrue(expectedAuthenticate.isAuthenticated());
    assertEquals(expectedAuthenticate.getAuthorities().toString(), "[ADMIN]");

    assertEquals(authResExpected.getStatusCode().toString(), "200 OK");
    AuthResVm resBody = authResExpected.getBody();

    assertNotNull(resBody);
    assertEquals(resBody.accessToken(), "abc-xyz");
    assertEquals(resBody.refreshToken(), "123");
    assertEquals(resBody.accountId(), account.getId());
    assertEquals(resBody.email(), account.getEmail());
    assertEquals(resBody.role(), account.getRole().getRoleName().toString());
  }

  @Test
  void activeAccount_shouldThrowNotFoundException_whenEmailNotExits() {
    when(authRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    NotFoundException notFoundException =
        Assertions.assertThrows(
            NotFoundException.class,
            () -> {
              iAuthService.activeAccount("token", "email@gmail");
            });

    assertEquals(
        notFoundException.getMessage(),
        "Account with email: %s not found".formatted("email@gmail"));
  }

  @Test
  void activeAccount_shouldThrowBadRequestException_whenAccountAlreadyActive() {
    when(authRepository.findByEmail(anyString())).thenReturn(Optional.of(account));

    BadRequestException exception =
        Assertions.assertThrows(
            BadRequestException.class,
            () -> {
              iAuthService.activeAccount("token", "email@gmail");
            });

    assertEquals("Account already active", exception.getMessage());
  }

  @Test
  void activeAccount_shouldThrowNotFoundException_whenTokenNotValid() {
    account.setStatus(AccountStatus.INACTIVE);
    when(authRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
    when(iVerifyAccountRepository.findByToken("123-323")).thenReturn(Optional.empty());

    NotFoundException notFoundException =
        Assertions.assertThrows(
            NotFoundException.class,
            () -> {
              iAuthService.activeAccount("123-323", "email@gmail");
            });

    assertEquals(notFoundException.getMessage(), "Token: %s not found".formatted("123-323"));
  }

  @Test
  void activeAccount_shouldThrowTokenException_whenTokenExpired() {
    account.setStatus(AccountStatus.INACTIVE);
    VerifyAccount verifyAccount =
        VerifyAccount.builder().expiryDate(Instant.now().getEpochSecond() - 1000).build();
    when(authRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
    when(iVerifyAccountRepository.findByToken("123-323")).thenReturn(Optional.of(verifyAccount));

    TokenException exception =
        Assertions.assertThrows(
            TokenException.class,
            () -> {
              iAuthService.activeAccount("123-323", "email@gmail");
            });

    assertEquals("Token expired", exception.getMessage());
  }

  @Test
  void activeAccount_shouldThrowTokenException_whenDataValid() {
    account.setStatus(AccountStatus.INACTIVE);
    VerifyAccount verifyAccount =
        VerifyAccount.builder().expiryDate(Instant.now().getEpochSecond() + 1000).build();
    when(authRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
    when(iVerifyAccountRepository.findByToken("123-323")).thenReturn(Optional.of(verifyAccount));
    when(authRepository.save(account)).thenReturn(account);

    ResponseEntity<HttpStatus> result = iAuthService.activeAccount("123-323", "email@gmail");

    assertEquals("200 OK", result.getStatusCode().toString());
  }

  @Test
  void validateAuthTest() {
    Authentication authentication1 =
        new Authentication() {
          @Override
          public Collection<? extends GrantedAuthority> getAuthorities() {
            return account.getAuthorities();
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
            return account.getUsername();
          }
        };
    SecurityContextHolder.getContext().setAuthentication(authentication1);

    when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(account));

    ResponseEntity<ValidateAuthVm> actualResult = iAuthService.validateAuth();
    assertEquals("200 OK", actualResult.getStatusCode().toString());

    ValidateAuthVm rawResult = actualResult.getBody();
    assertNotNull(rawResult);
    assertEquals(account.getUsername(), rawResult.username());
    assertEquals("ADMIN", rawResult.authority());
  }
}
