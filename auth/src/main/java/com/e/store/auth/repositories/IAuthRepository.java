package com.e.store.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.store.auth.entity.Account;

@Repository
public interface IAuthRepository extends JpaRepository<Account, String> {
    boolean existsByEmail (String email);

    Optional<Account> findByUsername (String username);

}
