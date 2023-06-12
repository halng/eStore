package com.e.store.auth.services.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.RefreshToken;
import com.e.store.auth.repositories.IRefreshTokenRepository;
import com.e.store.auth.services.IRefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final IRefreshTokenRepository iRefreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Autowired
    public RefreshTokenServiceImpl (IRefreshTokenRepository iRefreshTokenRepository) {
        this.iRefreshTokenRepository = iRefreshTokenRepository;
    }

    @Override
    public String generateRefreshToken (Account account) {
        RefreshToken refreshToken      = RefreshToken.builder().account(account).expiryDate(
            Instant.now().plusMillis(refreshTokenExpiration)).build();
        RefreshToken savedRefreshToken = iRefreshTokenRepository.save(refreshToken);
        return savedRefreshToken.getId();
    }
}
