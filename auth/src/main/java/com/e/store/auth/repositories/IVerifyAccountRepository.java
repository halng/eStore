package com.e.store.auth.repositories;

import com.e.store.auth.entity.VerifyAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerifyAccountRepository extends JpaRepository<VerifyAccount, Long> {

	Optional<VerifyAccount> findByToken(String token);

}
