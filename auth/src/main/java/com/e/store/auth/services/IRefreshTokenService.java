package com.e.store.auth.services;

import org.springframework.stereotype.Service;

import com.e.store.auth.entity.Account;

@Service
public interface IRefreshTokenService {
    String generateRefreshToken (Account account);
}
