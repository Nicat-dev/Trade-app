package com.nm.ms.auth.controller;

import com.nm.ms.auth.model.request.LoginRequest;
import com.nm.ms.auth.model.request.RefreshTokenRequest;
import com.nm.ms.auth.model.response.TokenPair;
import com.nm.ms.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenPair> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refresh(request));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String email,
                                            @RequestParam String token) {
        service.verifyEmail(email,token);
        return ResponseEntity.noContent().build();
    }

}
