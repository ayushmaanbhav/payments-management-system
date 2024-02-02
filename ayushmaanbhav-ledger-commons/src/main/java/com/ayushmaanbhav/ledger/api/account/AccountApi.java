package com.ayushmaanbhav.ledger.api.account;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("account")
public interface AccountApi {

    @PostMapping
    @NonNull GenericResponse<GetAccountResponse> createAccount(@NonNull @RequestBody CreateAccountRequest request)
            throws ApiException;

    @GetMapping
    @NonNull GenericResponse<GetAccountResponse> getAccount(@NonNull @RequestParam("accountId") String accountId)
            throws ApiException;
}
