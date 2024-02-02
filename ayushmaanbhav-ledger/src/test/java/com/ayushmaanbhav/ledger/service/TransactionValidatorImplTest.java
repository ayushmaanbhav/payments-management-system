package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.Transaction;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import com.ayushmaanbhav.ledger.exception.TransactionAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.TransactionValidationException;
import com.ayushmaanbhav.ledger.exception.UnbalancedTransactionLineItemsException;
import com.ayushmaanbhav.ledger.testsetup.account.AccountSetup;
import com.ayushmaanbhav.ledger.testsetup.transaction.TransactionDtoDataSetup;
import com.ayushmaanbhav.ledger.testsetup.transaction.TransactionSetup;
import com.ayushmaanbhav.ledger.validator.TransactionValidatorImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ayushmaanbhav.ledger.util.TestValues.*;
import static com.ayushmaanbhav.ledger.util.TestValues.ACCOUNT_EXTERNAL_ID_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionValidatorImplTest extends AbstractServiceTest {

    @Autowired
    TransactionValidatorImpl transactionValidator;
    @Autowired
    TransactionSetup transactionSetup;
    @Autowired
    AccountSetup accountSetup;

    @Test
    public void testValidateTransaction() throws TransactionValidationException {
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        transactionValidator.validateTransaction(correctDto);
        TransactionDto blankRefIdDto = TransactionDtoDataSetup.getFaultyBlankRefIdTransactionDto();
        try {
            transactionValidator.validateTransaction(blankRefIdDto);
            fail("Should have thrown exception");
        } catch (TransactionValidationException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        TransactionDto singleLineItemDto = TransactionDtoDataSetup.getFaultySingleLineItemTransactionDto();
        try {
            transactionValidator.validateTransaction(singleLineItemDto);
            fail("Should have thrown exception");
        } catch (TransactionValidationException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        TransactionDto faultyAmountDto = TransactionDtoDataSetup.getFaultyAmountTransactionDto();
        try {
            transactionValidator.validateTransaction(faultyAmountDto);
            fail("Should have thrown exception");
        } catch (TransactionValidationException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    public void testTransactionExists() throws TransactionAlreadyExistsException {
        TransactionDto transactionDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        transactionValidator.transactionExists(transactionDto.getTransactionRefId());
        Transaction transaction = transactionSetup.setupTransaction();
        try {
            transactionValidator.transactionExists(transaction.getTransactionRefId());
            fail("Should have thrown exception");
        } catch (TransactionAlreadyExistsException e) {
            assertEquals(ErrorCode.CONFLICT, e.getErrorCode());
        }
    }

    @Test
    public void testIsTransactionBalanced() throws UnbalancedTransactionLineItemsException {
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        transactionValidator.isTransactionBalanced(correctDto.getTransactionLineItems());
        TransactionDto unbalancedDto = TransactionDtoDataSetup.getFaultyUnbalancedTransactionDto();
        try {
            transactionValidator.isTransactionBalanced(unbalancedDto.getTransactionLineItems());
            fail("Should have thrown exception");
        } catch (UnbalancedTransactionLineItemsException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

    // todo: test for currency mismatch
    @Test
    public void testCurrenciesMatch() throws TransactionValidationException, AccountNotFoundException {
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        try {
            transactionValidator.currenciesMatch(correctDto.getTransactionLineItems());
            fail("Should have thrown exception");
        } catch (AccountNotFoundException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
        Account account1 = accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        Account account2 = accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        transactionValidator.currenciesMatch(correctDto.getTransactionLineItems());
    }

}
