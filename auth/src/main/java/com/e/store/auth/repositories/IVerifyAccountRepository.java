package com.e.store.auth.repositories;

import com.e.store.auth.entity.VerifyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerifyAccountRepository extends JpaRepository<VerifyAccount, Long> {

}
