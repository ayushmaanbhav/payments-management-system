package com.ayushmaanbhav.ledger.testsetup.account;

import com.ayushmaanbhav.commons.contstants.AccountType;
import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.ledger.entity.Account;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.ledger.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDataSetup {

    public static Account getAccount(String requestId, String accountId) {
        return Account.builder()
                .requestId(requestId)
                .externalId(accountId)
                .currency(Currency.INR)
                .type(AccountType.STORE)
                .build();
    }
}
