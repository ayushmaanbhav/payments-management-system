package com.ayushmaanbhav.ledger.controller;

import com.ayushmaanbhav.ledger.api.transaction.TransactionApi;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import com.ayushmaanbhav.ledger.exception.TransactionAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.TransactionValidationException;
import com.ayushmaanbhav.ledger.exception.UnbalancedTransactionLineItemsException;
import com.ayushmaanbhav.ledger.service.TransactionServiceImpl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController implements TransactionApi {
    TransactionServiceImpl transactionService;

    @Override
    public void recordTransaction(@NonNull TransactionDto request)
            throws UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException,
            TransactionValidationException, AccountNotFoundException {
        transactionService.recordTransaction(request);
    }
}
