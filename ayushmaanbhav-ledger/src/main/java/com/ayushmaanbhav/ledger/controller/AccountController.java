package com.ayushmaanbhav.ledger.controller;

import com.ayushmaanbhav.ledger.api.account.AccountApi;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.ledger.exception.AccountAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import com.ayushmaanbhav.ledger.service.AccountServiceImpl;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController implements AccountApi {
    AccountServiceImpl accountService;

    @Override
    public @NonNull GenericResponse<GetAccountResponse> createAccount(@NonNull CreateAccountRequest request)
            throws AccountAlreadyExistsException {
        return GenericResponse.fromData(accountService.createAccount(request));
    }

    @Override
    public @NonNull GenericResponse<GetAccountResponse> getAccount(@NonNull String accountId)
            throws AccountNotFoundException {
        return GenericResponse.fromData(accountService.getAccount(accountId));
    }
}
