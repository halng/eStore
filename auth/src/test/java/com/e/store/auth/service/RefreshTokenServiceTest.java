package com.e.store.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.RefreshToken;
import com.e.store.auth.repositories.IRefreshTokenRepository;
import com.e.store.auth.services.impl.RefreshTokenServiceImpl;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class RefreshTokenServiceTest {

    RefreshTokenServiceImpl refreshTokenService;
    IRefreshTokenRepository iRefreshTokenRepository;
    Account account;
    Long refreshTokenExpiration = 864000L;

    @BeforeEach
    void setUp() {
        iRefreshTokenRepository = mock(IRefreshTokenRepository.class);
        account = mock(Account.class);
        refreshTokenService = new RefreshTokenServiceImpl(iRefreshTokenRepository);
        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenExpiration", 86400000L);
    }

    @Test
    void generateRefreshTokenTest() {
        RefreshToken refreshToken = RefreshToken.builder().id("abc-xyz").account(account)
            .expiryDate(Instant.now().plusMillis(refreshTokenExpiration)).build();

        when(iRefreshTokenRepository.findByAccount(any())).thenReturn(Optional.empty());
        when(iRefreshTokenRepository.save(any())).thenReturn(refreshToken);

        String token = refreshTokenService.generateRefreshToken(account);
        assertEquals("abc-xyz", token);
    }

    @Test
    void generateRefreshToken_whenAccountHaveExistToken() {
        RefreshToken refreshToken = RefreshToken.builder().id("abc-xyz").account(account)
                                                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration)).build();
        RefreshToken oldRefreshToken = RefreshToken.builder().id("nnn-mmm").account(account).build();

        when(iRefreshTokenRepository.findByAccount(any())).thenReturn(Optional.of(oldRefreshToken));
        when(iRefreshTokenRepository.save(any())).thenReturn(refreshToken);

        String token = refreshTokenService.generateRefreshToken(account);
        assertEquals("abc-xyz", token);
    }
}
