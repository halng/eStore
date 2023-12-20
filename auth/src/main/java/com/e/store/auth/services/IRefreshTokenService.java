package com.e.store.auth.services;

import com.e.store.auth.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface IRefreshTokenService {

    String generateRefreshToken(Account account);
}
