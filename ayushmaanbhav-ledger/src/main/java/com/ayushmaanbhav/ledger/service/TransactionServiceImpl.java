package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.Transaction;
import com.ayushmaanbhav.ledger.entity.TransactionLineItem;
import com.ayushmaanbhav.ledger.entity.repository.AccountRepository;
import com.ayushmaanbhav.ledger.entity.repository.TransactionRepository;
import com.ayushmaanbhav.ledger.exception.*;
import com.ayushmaanbhav.ledger.validator.TransactionValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionValidator validator;
    AccountRepository accountDao;
    TransactionRepository transactionDao;

    @Transactional(rollbackFor = Exception.class)
    public void recordTransaction(TransactionDto request) throws TransactionValidationException, UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        validateRequest(request);
        //validator.validBalance(request.getTransactionLineItems());
        //for (var lineItem : request.getTransactionLineItems()) {
            //accountDao.updateBalance(leg);
        //}
        storeTransaction(request);
    }

    private void validateRequest(TransactionDto request) throws TransactionValidationException, UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        validator.validateTransaction(request);
        validator.isTransactionBalanced(request.getTransactionLineItems());
        validator.transactionExists(request.getTransactionRefId());
        validator.currenciesMatch(request.getTransactionLineItems());
    }

    private void storeTransaction(TransactionDto request) {
        Transaction transaction = Transaction.builder()
                .transactionRefId(request.getTransactionRefId())
                .transactionDate(request.getTransactionDate())
                .transactionLineItems(new HashSet<>())
                .build();
        transaction.setTransactionLineItems(lineItem(request.getTransactionLineItems(), transaction));
        transactionDao.save(transaction);
    }

    private Set<TransactionLineItem> lineItem(List<TransactionLineItemDto> lineItems, Transaction transaction) {
        Set<TransactionLineItem> lineItemsSet = new HashSet<>();
        for (var lineItem : lineItems) {
            Account account = accountDao.selectByExternalId(lineItem.getAccountId());
            lineItemsSet.add(TransactionLineItem.builder()
                    .account(account)
                    .operationType(lineItem.getOperationType())
                    .transferEntityType(lineItem.getTransferEntityType())
                    .normalisedAmount(lineItem.getNormalisedAmount())
                    .currency(lineItem.getCurrency())
                    .transaction(transaction)
                    .build());
        }
        return lineItemsSet;
    }
}
