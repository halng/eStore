package com.e.store.auth.repositories;

import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, String> {

	Optional<RefreshToken> findByAccount(Account account);

}
