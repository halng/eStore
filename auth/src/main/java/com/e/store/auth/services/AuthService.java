package com.e.store.auth.services;

import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<HttpStatus> signUp(SignUpVm signUpData);

//    ResponseEntity<HttpStatus> signIn(SignInVm signInData);
//
//    ResponseEntity<HttpStatus> deActiveAccount(String accountId);
//
//    ResponseEntity<HttpStatus> activeAccount(String accountId);
//
//    ResponseEntity<HttpStatus> refreshToken();
//
//    ResponseEntity<HttpStatus> updateAccount();

}
