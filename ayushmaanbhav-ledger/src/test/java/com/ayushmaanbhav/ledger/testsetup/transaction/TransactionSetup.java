package com.ayushmaanbhav.ledger.testsetup.transaction;

import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.Transaction;
import com.ayushmaanbhav.ledger.entity.TransactionLineItem;
import com.ayushmaanbhav.ledger.entity.repository.TransactionRepository;
import com.ayushmaanbhav.ledger.testsetup.account.AccountSetup;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ayushmaanbhav.ledger.util.TestValues.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransactionSetup {
    @Autowired
    AccountSetup accountSetup;
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction setupTransaction() {
        Account account1 = accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        Account account2 = accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto transactionDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        TransactionLineItemDto lineItemDto1 = transactionDto.getTransactionLineItems().get(0);
        TransactionLineItemDto lineItemDto2 = transactionDto.getTransactionLineItems().get(1);

        Transaction transaction = Transaction.builder()
                .transactionRefId(transactionDto.getTransactionRefId())
                .transactionDate(transactionDto.getTransactionDate())
                .transactionLineItems(new HashSet<>())
                .build();

        Set<TransactionLineItem> lineItemsSet = new HashSet<>();

        lineItemsSet.add(TransactionLineItem.builder()
                .account(account1)
                .operationType(lineItemDto1.getOperationType())
                .transferEntityType(lineItemDto1.getTransferEntityType())
                .normalisedAmount(lineItemDto1.getNormalisedAmount())
                .currency(lineItemDto1.getCurrency())
                .transaction(transaction)
                .build());

        lineItemsSet.add(TransactionLineItem.builder()
                .account(account2)
                .operationType(lineItemDto2.getOperationType())
                .transferEntityType(lineItemDto2.getTransferEntityType())
                .normalisedAmount(lineItemDto2.getNormalisedAmount())
                .currency(lineItemDto2.getCurrency())
                .transaction(transaction)
                .build());

        transaction.setTransactionLineItems(lineItemsSet);
        transactionRepository.save(transaction);

        return transaction;
    }

}
