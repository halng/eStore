package com.e.store.auth.services.impl;

import com.e.store.auth.config.jwt.JwtUtilities;
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
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthMessageVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import com.e.store.auth.viewmodel.res.ValidateAuthVm;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final IAuthRepository authRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private final IRefreshTokenService refreshTokenService;
    private final IMessageProducer iMessageProducer;
    private final IVerifyAccountRepository iVerifyAccountRepository;

    public ResponseEntity<HttpStatus> sendMessageActiveAccount(Account account) {
        if (account.getId() == null) {
            logger.error("Server can't create account correctly for user");
            throw new InternalErrorException("Server can't create account correctly. Try again!");
        }

        String token = UUID.randomUUID().toString();
        Long expiryDate = Instant.now().plusSeconds(Const.DEFAULT_TIME_EXPIRY_CONFIRM_ACCOUNT).getEpochSecond();
        VerifyAccount verifyAccount = VerifyAccount.builder().token(token).account(account).expiryDate(expiryDate)
            .build();

        VerifyAccount createdVerifyAccount = iVerifyAccountRepository.save(verifyAccount);

        AuthMessageVm authMessageVm = new AuthMessageVm(account.getEmail(), account.getUsername(),
            createdVerifyAccount.getToken(), createdVerifyAccount.getExpiryDate());

        logger.info("Send message for IMessageProducer");
        iMessageProducer.sendMessage(authMessageVm);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> signUp(SignUpVm signUpData) {

        // already on front-end - but check again to make sure user don't call api directly
        if (!signUpData.password().equals(signUpData.rePassword())) {
            throw new BadRequestException("Password are not identical!");
        }

        if (!(signUpData.role() == 1 || signUpData.role() == 2)) {
            throw new BadRequestException("Role incorrect");
        }

        if (authRepository.existsByEmail(signUpData.email())) {
            throw new BadRequestException("Email %s already exist!".formatted(signUpData.email()));
        }

        Role role = this.roleRepository.findById(signUpData.role())
            .orElseThrow(() -> new EntityNotFoundException("Role with id not found"));

        Account newAccount = Account.builder().username(signUpData.username()).role(role).status(AccountStatus.INACTIVE)
            .password(passwordEncoder.encode(signUpData.password())).email(signUpData.email()).build();

        newAccount.setCreateBy("USER");
        newAccount.setUpdateBy("USER");
        Account createedAccount = authRepository.save(newAccount);

        return sendMessageActiveAccount(createedAccount);
    }

    public Account getAccountByUsername(String username) {
        return authRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Account loadAccountByUsername(String username) {
        return getAccountByUsername(username);
    }

    @Override
    public ResponseEntity<AuthResVm> signIn(SignInVm signInData) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInData.username(), signInData.password()));

        Account account = getAccountByUsername(authentication.getName());

        if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new ForbiddenException("Account Not Active");
        }

        String accessToken = jwtUtilities.generateAccessToken(account.getUsername(),
            account.getRole().getRoleName().toString());
        String refreshToken = refreshTokenService.generateRefreshToken(account);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(200).body(AuthResVm.fromAccount(accessToken, refreshToken, account));
    }

    @Override
    public ResponseEntity<HttpStatus> activeAccount(String token, String email) {
        logger.info("Receive request to active account from {}", email);

        Account account = this.authRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Account with email: %s not found".formatted(email)));
        if (!account.getStatus().equals(AccountStatus.INACTIVE)) {
            throw new BadRequestException("Account already active");
        }

        VerifyAccount verifyAccount = iVerifyAccountRepository.findByToken(token)
            .orElseThrow(() -> new NotFoundException("Token: %s not found".formatted(token)));
        if (Instant.now().isAfter(Instant.ofEpochSecond(verifyAccount.getExpiryDate()))) {
            throw new TokenException("Token expired");
        }

        account.setStatus(AccountStatus.ACTIVE);
        authRepository.save(account);
        iVerifyAccountRepository.delete(verifyAccount);

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<ValidateAuthVm> validateAuth () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = getAccountByUsername(authentication.getName());
        ValidateAuthVm result = new ValidateAuthVm(account.getUsername(),
                                                   account.getAuthorities().stream().map(e -> e.getAuthority().toString()).reduce("", String::concat));
        return ResponseEntity.ok(result);
    }
}
