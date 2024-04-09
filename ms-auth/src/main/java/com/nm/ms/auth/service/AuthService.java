package com.nm.ms.auth.service;

import com.nm.ms.auth.model.request.LoginRequest;
import com.nm.ms.auth.model.request.RefreshTokenRequest;
import com.nm.ms.auth.model.response.TokenPair;

public interface AuthService {

    TokenPair login(LoginRequest request);

    TokenPair refresh(RefreshTokenRequest request);

    void verifyEmail(String email, String token);

}
