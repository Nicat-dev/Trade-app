package com.nm.ms.account.mapper;

import com.nm.ms.account.model.domain.Account;
import com.nm.ms.account.model.request.CreateAccountRequest;
import com.nm.ms.account.model.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toAccountResponse(Account account);

    Account toAccount(CreateAccountRequest request);

}
