package com.ayushmaanbhav.ledger.testsetup.account;

import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountSetup {
    AccountRepository accountRepository;

    public Account setupAccount(String requestId, String accountId) {
        Account account = AccountDataSetup.getAccount(requestId, accountId);
        accountRepository.save(account);
        return account;
    }
}
