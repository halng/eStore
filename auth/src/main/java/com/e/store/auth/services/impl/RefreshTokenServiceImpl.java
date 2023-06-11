package com.e.store.auth.services.impl;

import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.RefreshToken;
import com.e.store.auth.exception.TokenException;
import com.e.store.auth.repositories.RefreshTokenRepository;
import com.e.store.auth.services.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
//    @Value("${e.store.jwt.refresh-token.expire}")
    private final Long refreshTokenDuration  = 86400000L;
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public String getRefreshToken(Account account) {
        //TODO: need to check is user already login or session login still valid
        RefreshToken refreshToken = new RefreshToken();
        String generatedToken = UUID.randomUUID().toString();
        refreshToken.setAccount(account);
        refreshToken.setToken(generatedToken);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshTokenRepository.save(refreshToken);

        return generatedToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = this.refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new EntityNotFoundException("Token: %s not found!".formatted(token)));

        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.refreshTokenRepository.delete(refreshToken);
            throw new TokenException("Token %s expired".formatted(token));
        }

        return refreshToken;
    }
}
