package com.nm.ms.account.controller;

import com.nm.ms.account.model.request.CreateAccountRequest;
import com.nm.ms.account.model.response.AccountResponse;
import com.nm.ms.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid CreateAccountRequest request) {
        return ResponseEntity.ok(service.createAccount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }



}
