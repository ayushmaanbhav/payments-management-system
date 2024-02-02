package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.repository.AccountRepository;
import com.ayushmaanbhav.ledger.exception.AccountAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountDao;

    @Transactional(rollbackFor = Exception.class)
    public GetAccountResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExistsException {
        if (accountDao.existsByRequestId(request.getRequestId())) {
            throw new AccountAlreadyExistsException("Account already exists: " + request.getRequestId());
        }
        Account account = Account.builder()
                .currency(request.getCurrency())
                .type(request.getType())
                .externalId(LUUID.generateUuid())
                .requestId(request.getRequestId())
                .build();
        accountDao.save(account);
        return GetAccountResponse.builder()
                .accountId(account.getExternalId())
                .type(account.getType())
                .currency(account.getCurrency())
                .build();
    }

    public GetAccountResponse getAccount(String externalId) throws AccountNotFoundException {
        Account account = accountDao.selectByExternalId(externalId);
        if (account == null) {
            throw new AccountNotFoundException(externalId);
        }
        return GetAccountResponse.builder()
                .accountId(account.getExternalId())
                .type(account.getType())
                .currency(account.getCurrency())
                .build();
    }
}
