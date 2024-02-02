package com.ayushmaanbhav.ledger.validator;

import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import com.ayushmaanbhav.ledger.exception.*;

public interface TransactionValidator {
    void validateTransaction(TransactionDto request) throws TransactionValidationException;

    void transactionExists(String transactionRefId) throws TransactionAlreadyExistsException;

    void isTransactionBalanced(Iterable<TransactionLineItemDto> transactionLineItems)
            throws UnbalancedTransactionLineItemsException;

    void currenciesMatch(Iterable<TransactionLineItemDto> transactionLineItems)
            throws TransactionValidationException, AccountNotFoundException;

    void validBalance(Iterable<TransactionLineItemDto> transactionLineItems)
            throws InsufficientFundsException;
}
