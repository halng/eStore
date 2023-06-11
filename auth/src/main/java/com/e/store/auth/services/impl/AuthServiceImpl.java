package com.e.store.auth.services.impl;

import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.AccountInfo;
import com.e.store.auth.entity.RefreshToken;
import com.e.store.auth.entity.Role;
import com.e.store.auth.exception.BadRequestException;
import com.e.store.auth.repositories.AccountInfoRepository;
import com.e.store.auth.repositories.AuthRepository;
import com.e.store.auth.repositories.RoleRepository;
import com.e.store.auth.security.JwtUtils;
import com.e.store.auth.services.AuthService;
import com.e.store.auth.services.RefreshTokenService;
import com.e.store.auth.viewmodel.req.AccountInfoVm;
import com.e.store.auth.viewmodel.req.RefreshTokenReqVm;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthVm;
import com.e.store.auth.viewmodel.res.RefreshTokenResVm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthServiceImpl implements AuthService {

    final private AuthRepository authRepository;
    final private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountInfoRepository accountInfoRepository;
    private final RefreshTokenService refreshTokenService;
    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder, AccountInfoRepository accountInfoRepository,
        RefreshTokenService refreshTokenService) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountInfoRepository = accountInfoRepository;
        this.refreshTokenService = refreshTokenService;
    }

    private Account getAccountById(String accountId) {
        return authRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(
            "Account with id: %s does not exits".formatted(accountId)));
    }

    @Override
    public ResponseEntity<HttpStatus> signUp(SignUpVm signUpData) {

        // already on front-end - but check again to make sure user don't call api directly
        if (!signUpData.password().equals(signUpData.rePassword())) {
            throw new BadRequestException("Password are not identical!");
        }

        if (signUpData.role() == 4 || signUpData.role() == 0) {
            throw new BadRequestException("Role incorrect");
        }

        if (authRepository.existsByEmail(signUpData.email())) {
            throw new BadRequestException("Email %s already exist!".formatted(signUpData.email()));
        }

        Role role = this.roleRepository.findById(signUpData.role())
            .orElseThrow(() -> new EntityNotFoundException("Role with id not found"));

        Account newAccount = Account.builder().username(signUpData.username()).role(role)
            .status(AccountStatus.INACTIVE).password(passwordEncoder.encode(signUpData.password()))
            .email(signUpData.email()).build();

        authRepository.save(newAccount);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @Override
    public ResponseEntity<?> signIn(SignInVm signInData) {
        // TODO: change to use AuthenticationManager
        Account account = authRepository.findByUsername(signInData.username())
            .orElseThrow(() -> new EntityNotFoundException("User with username doesn't exist!")
            );
        if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new BadRequestException("Account deleted or inactive");
        }
        // TODO: need to check and change approach of this condition
//        if (!account.getPassword().equals(passwordEncoder.encode(signInData.password()))) {
//            throw new BadRequestException("Password incorrect! + decode input password: %s".formatted(
//                passwordEncoder.encode(signInData.password())));
//        }

        String token = new JwtUtils().generateAccessToken(account.getId(), account.getRole().getRole().toString());
        String refreshToken = this.refreshTokenService.getRefreshToken(account);

        return ResponseEntity.ok(AuthVm.fromAccount(token, refreshToken, account));
    }

    @Override
    public ResponseEntity<HttpStatus> deActiveAccount(String accountId) {
        Account account = getAccountById(accountId);
        account.setStatus(AccountStatus.DELETED);
        authRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    public ResponseEntity<HttpStatus> activeAccount(String accountId) {
        Account account = getAccountById(accountId);
        if (!account.getStatus().toString().equals(AccountStatus.INACTIVE.toString())) {
            throw new BadRequestException(
                "Account with id: %s already active or account deleted".formatted(accountId));
        }

        account.setStatus(AccountStatus.ACTIVE);
        authRepository.save(account);

        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @Override
    public ResponseEntity<RefreshTokenResVm> refreshToken(RefreshTokenReqVm refreshTokenReqVm) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenReqVm.refreshToken());
        String accessToken = new JwtUtils().generateAccessToken(refreshToken.getAccount().getId(),
            refreshToken.getAccount().getRole().getRole().toString());

        return ResponseEntity.ok(new RefreshTokenResVm(accessToken, refreshToken.getToken()));
    }

    @Override
    public ResponseEntity<HttpStatus> updateAccount(String accountId, AccountInfoVm updateData, MultipartFile photo) {
        Account account = getAccountById(accountId);
        if (!account.getStatus().toString().equals(AccountStatus.ACTIVE.toString())) {
            throw new BadRequestException("Account disabled or deleted!!!");
        }

        //TODO: handle upload photo to media service and retrieve photo url.
        AccountInfo accountInfo = AccountInfo.builder().account(account)
            .address(updateData.address()).name(updateData.name()).phoneNumber(
                updateData.phoneNumber()).photoUrl("").build();

        account.setAccountInfo(accountInfo);

        accountInfoRepository.save(accountInfo);
        authRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
