package com.nm.ms.auth.service;

import com.nm.ms.auth.model.request.RegisterRequest;
import com.nm.ms.auth.model.response.UserResponse;

public interface UserService {

    UserResponse registerUser(RegisterRequest request);

    UserResponse getUser(Long id);

}
