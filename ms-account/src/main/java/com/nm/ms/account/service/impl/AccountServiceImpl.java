package com.nm.ms.account.service.impl;

import com.nm.ms.account.mapper.AccountMapper;
import com.nm.ms.account.model.request.CreateAccountRequest;
import com.nm.ms.account.model.response.AccountResponse;
import com.nm.ms.account.repository.AccountRepository;
import com.nm.ms.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountResponse getAccount(Long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountResponse createAccount(CreateAccountRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deposit(Long id, BigDecimal amount) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long id, BigDecimal amount) {

    }

}
