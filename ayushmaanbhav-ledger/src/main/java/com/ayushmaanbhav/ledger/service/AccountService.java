package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.ledger.exception.AccountAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;

public interface AccountService {
    GetAccountResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExistsException;

    GetAccountResponse getAccount(String accountId) throws AccountNotFoundException;
}
