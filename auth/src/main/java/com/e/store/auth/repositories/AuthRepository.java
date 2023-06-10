package com.e.store.auth.repositories;

import com.e.store.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String email);

}
