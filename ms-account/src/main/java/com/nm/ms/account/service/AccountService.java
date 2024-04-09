package com.nm.ms.account.service;

import com.nm.ms.account.model.request.CreateAccountRequest;
import com.nm.ms.account.model.response.AccountResponse;

import java.math.BigDecimal;

public interface AccountService {

    AccountResponse getAccount(Long id);
    AccountResponse createAccount(CreateAccountRequest request);

    void deposit(Long id, BigDecimal amount);

    void withdraw(Long id, BigDecimal amount);

}
