package com.nm.ms.auth.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record LoginRequest(@NotBlank String username, @NotBlank String password) {
}
