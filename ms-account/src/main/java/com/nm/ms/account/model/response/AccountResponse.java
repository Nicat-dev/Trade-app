package com.nm.ms.account.model.response;

import java.math.BigDecimal;


public record AccountResponse(Long id,
                              Long userId,
                              String description,
                              BigDecimal balance,
                              String name,
                              Boolean active) {
}