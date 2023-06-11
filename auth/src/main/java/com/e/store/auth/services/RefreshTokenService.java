package com.e.store.auth.services;

import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    String getRefreshToken(Account account);

    RefreshToken verifyRefreshToken(String token);
}
