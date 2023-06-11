package com.e.store.auth.viewmodel.res;

import com.e.store.auth.entity.Account;

public record AuthVm(String token, String refreshToken, String accountId, String username, String role, String email) {

    public static AuthVm fromAccount(String token, String refreshToken, Account account) {
        return new AuthVm(token, refreshToken, account.getId(), account.getUsername(),
            account.getRole().getRole().toString(),
            account.getEmail());
    }
}
