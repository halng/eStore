package com.e.store.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.store.auth.entity.RefreshToken;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
