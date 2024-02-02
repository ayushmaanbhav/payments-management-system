package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.exception.*;

public interface TransactionService {
    void recordTransaction(TransactionDto request) throws InsufficientFundsException, TransactionValidationException, UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException;
}
