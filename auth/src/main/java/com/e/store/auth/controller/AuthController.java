package com.e.store.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.store.auth.services.AuthService;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody SignUpVm signUpData) {
        return authService.signUp(signUpData);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResVm> login(@RequestBody SignInVm signInData) {
        return authService.signIn(signInData);
    }

    @GetMapping("grant")
    public String hello() {
        return "hello world";
    }

    @GetMapping("hello")
    public String helloOnly() {
        return "hello";
    }

}
