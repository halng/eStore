package com.e.store.auth.services;

import com.e.store.auth.viewmodel.req.AccountInfoVm;
import com.e.store.auth.viewmodel.req.RefreshTokenReqVm;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.RefreshTokenResVm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AuthService {
    ResponseEntity<HttpStatus> signUp(SignUpVm signUpData);

    ResponseEntity<?> signIn(SignInVm signInData);

    ResponseEntity<HttpStatus> deActiveAccount(String accountId);

    ResponseEntity<HttpStatus> activeAccount(String accountId);

    ResponseEntity<RefreshTokenResVm> refreshToken(RefreshTokenReqVm refreshTokenReqVm);

    ResponseEntity<HttpStatus> updateAccount(String accountId, AccountInfoVm data, MultipartFile photo);

}
