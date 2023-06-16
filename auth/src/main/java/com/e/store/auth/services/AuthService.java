package com.e.store.auth.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.e.store.auth.entity.Account;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;

@Service
public interface AuthService {

    ResponseEntity<HttpStatus> signUp(SignUpVm signUpData);

    Account loadAccountByUsername(String username);

    ResponseEntity<AuthResVm> signIn(SignInVm signInData);
}
