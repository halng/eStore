package com.e.store.auth.repositories;

import java.util.Optional;

import com.e.store.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String email);

    Optional<Account> findByUsername(String username);

}
