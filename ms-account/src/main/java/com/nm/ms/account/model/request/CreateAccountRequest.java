package com.nm.ms.account.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nm.ms.account.model.enums.AccountType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record CreateAccountRequest(
        @NotNull
        Long userId,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        AccountType type,
        @NotNull
        @Digits(integer = 10, fraction = 2)
        BigDecimal balance
) {
}
