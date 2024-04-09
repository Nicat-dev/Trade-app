package com.nm.ms.auth.mapper;

import com.nm.ms.auth.model.domain.User;
import com.nm.ms.auth.model.request.RegisterRequest;
import com.nm.ms.auth.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActivated", expression = "java(false)")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User toUser(RegisterRequest registerRequest);

}
