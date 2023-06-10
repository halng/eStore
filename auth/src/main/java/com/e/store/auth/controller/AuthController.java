package com.e.store.auth.controller;

import com.e.store.auth.services.AuthService;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody SignUpVm signUpData) {
        return authService.signUp(signUpData);
    }
//
//    @PostMapping("/sign-in")
//    public ResponseEntity<?> signIn(@RequestBody SignInVm signInData) {
//        return authService.signIn(signInData);
//    }
//
//    @PutMapping("/{accountId}/deactive")
//    public ResponseEntity<?> deActiveAccount(@PathVariable String accountId) {
//        return authService.deActiveAccount(accountId);
//    }
//
//    @PutMapping("/{accountId}/active")
//    public ResponseEntity<?> activeAccount(@PathVariable(name = "accountId") String accountId) {
//        return authService.activeAccount(accountId);
//    }
//
//    @PutMapping("/{accountId}/update")
//    public ResponseEntity<?> updateAccount(@PathVariable(name = "accountId") String accountId) {
//        return authService.updateAccount();
//    }
//
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(){
//        return authService.refreshToken();
//    }

}
