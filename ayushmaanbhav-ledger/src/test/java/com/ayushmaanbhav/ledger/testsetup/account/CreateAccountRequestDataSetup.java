package com.ayushmaanbhav.ledger.testsetup.account;

import com.ayushmaanbhav.commons.contstants.AccountType;
import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountRequestDataSetup {
    public static CreateAccountRequest getCreateAccountRequest(String requestId) {
        return CreateAccountRequest.builder()
                .requestId(requestId)
                .currency(Currency.INR)
                .type(AccountType.STORE)
                .build();
    }
}
