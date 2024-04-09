package com.nm.ms.auth.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record RegisterRequest(@NotBlank
                              String username,
                              @Email
                              @NotBlank
                              String email,
                              @NotBlank
                              @Size(min = 6, message = "{validation.password.size}")
                              @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validation.password.pattern}")
                              String password) {
}
