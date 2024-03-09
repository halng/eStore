package com.e.store.auth.controller;

import com.e.store.auth.services.IAuthService;
import com.e.store.auth.viewmodel.req.SignInVm;
import com.e.store.auth.viewmodel.req.SignUpVm;
import com.e.store.auth.viewmodel.res.AuthResVm;
import com.e.store.auth.viewmodel.res.ValidateAuthVm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final IAuthService iAuthService;

	@Autowired
	public AuthController(IAuthService iAuthService) {
		this.iAuthService = iAuthService;
	}

	@PostMapping("register")
	public ResponseEntity<HttpStatus> register(@Valid @RequestBody SignUpVm signUpData) {
		return iAuthService.signUp(signUpData);
	}

	@PostMapping("login")
	public ResponseEntity<AuthResVm> login(@RequestBody SignInVm signInData) {
		return iAuthService.signIn(signInData);
	}

	@PostMapping("active-account/{token}/{email}")
	public ResponseEntity<HttpStatus> activeAccount(@PathVariable String token, @PathVariable String email) {
		return iAuthService.activeAccount(token, email);
	}

	@GetMapping("validate")
	public ResponseEntity<ValidateAuthVm> validationAccount() {
		return this.iAuthService.validateAuth();
	}

}
