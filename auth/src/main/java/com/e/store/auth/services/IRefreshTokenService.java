package com.e.store.auth.services;

import com.e.store.auth.entity.Account;

public interface IRefreshTokenService {

	String generateRefreshToken(Account account);

}
