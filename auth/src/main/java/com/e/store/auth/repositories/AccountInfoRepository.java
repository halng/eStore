package com.e.store.auth.repositories;

import com.e.store.auth.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

}
