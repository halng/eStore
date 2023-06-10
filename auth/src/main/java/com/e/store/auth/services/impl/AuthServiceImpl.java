package com.e.store.auth.services.impl;

import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.exception.BadRequestException;
import com.e.store.auth.repositories.AuthRepository;
import com.e.store.auth.repositories.RoleRepository;
import com.e.store.auth.services.AuthService;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    final private AuthRepository authRepository;
    final private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

//    @Override
//    public ResponseEntity<HttpStatus> signIn(SignInVm signInData) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<HttpStatus> deActiveAccount(String accountId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<HttpStatus> activeAccount(String accountId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<HttpStatus> refreshToken() {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<HttpStatus> updateAccount() {
//        return null;
//    }
}
