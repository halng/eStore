package com.e.store.auth.services.impl;

import com.e.store.auth.constant.Const;
import com.e.store.auth.entity.VerifyAccount;
import com.e.store.auth.exception.InternalErrorException;
import com.e.store.auth.repositories.IVerifyAccountRepository;
import com.e.store.auth.services.IMessageProducer;
import com.e.store.auth.viewmodel.res.AuthMessageVm;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e.store.auth.config.jwt.JwtUtilities;
import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.exception.BadRequestException;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.repositories.IRoleRepository;
import com.e.store.auth.services.AuthService;
import com.e.store.auth.services.IRefreshTokenService;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final private IAuthRepository authRepository;
    final private IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private final IRefreshTokenService refreshTokenService;
    private final IMessageProducer iMessageProducer;
    private final IVerifyAccountRepository iVerifyAccountRepository;

    public ResponseEntity<HttpStatus> sendMessageActiveAccount(Account account) {
        if (account.getId() == null) {
            throw new InternalErrorException("Server can't create account correctly. Try again!");
        }

        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plusSeconds(Const.DEFAULT_TIME_EXPIRY_CONFIRM_ACCOUNT);
        VerifyAccount verifyAccount = VerifyAccount.builder().token(token).account(account).expiryDate(expiryDate)
            .build();

        VerifyAccount createdVerifyAccount = iVerifyAccountRepository.save(verifyAccount);

        AuthMessageVm authMessageVm = new AuthMessageVm(account.getEmail(), account.getUsername(),
            createdVerifyAccount.getToken(), createdVerifyAccount.getExpiryDate());

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

        String accessToken = jwtUtilities.generateAccessToken(account.getUsername(),
            account.getRole().getRoleName().toString());
        String refreshToken = refreshTokenService.generateRefreshToken(account);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(200).body(AuthResVm.fromAccount(accessToken, refreshToken, account));
    }
}
