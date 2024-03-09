package com.e.store.auth.viewmodel.res;

import com.e.store.auth.entity.Account;

public record AuthResVm(String accessToken, String refreshToken, String accountId, String email, String username,
		String role) {

	public static AuthResVm fromAccount(String accessToken, String refreshToken, Account account) {
		return new AuthResVm(accessToken, refreshToken, account.getId(), account.getEmail(), account.getUsername(),
				account.getRole().getRoleName().toString());
	}
}
