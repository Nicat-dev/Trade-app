package com.nm.ms.account.repository;

import com.nm.ms.account.model.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}