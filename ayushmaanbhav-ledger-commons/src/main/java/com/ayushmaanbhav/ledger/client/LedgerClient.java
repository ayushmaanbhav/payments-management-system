package com.ayushmaanbhav.ledger.client;

import com.ayushmaanbhav.client.AbstractClient;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;
import org.springframework.http.HttpMethod;

public class LedgerClient extends AbstractClient {

    public LedgerClient(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }

    public GetAccountResponse createLedgerAccount(CreateAccountRequest request) throws ApiException {
        Object response = makeRequest(HttpMethod.POST, "/account", null, request, GenericResponse.class).getData();
        return mapper.convertValue(response, GetAccountResponse.class);
    }

    public void recordTransaction(TransactionDto request) throws ApiException {
        makeRequest(HttpMethod.POST, "/transaction", null, request, Void.class);
    }

}
