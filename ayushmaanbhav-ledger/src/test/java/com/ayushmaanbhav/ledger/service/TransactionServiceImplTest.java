package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.Transaction;
import com.ayushmaanbhav.ledger.entity.repository.TransactionRepository;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import com.ayushmaanbhav.ledger.exception.TransactionAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.TransactionValidationException;
import com.ayushmaanbhav.ledger.exception.UnbalancedTransactionLineItemsException;
import com.ayushmaanbhav.ledger.testsetup.account.AccountSetup;
import com.ayushmaanbhav.ledger.testsetup.transaction.TransactionDtoDataSetup;
import com.ayushmaanbhav.ledger.testsetup.transaction.TransactionSetup;
import com.ayushmaanbhav.ledger.validator.TransactionValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static com.ayushmaanbhav.ledger.util.TestValues.*;
import static com.ayushmaanbhav.ledger.util.TestValues.ACCOUNT_EXTERNAL_ID_2;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionServiceImplTest extends AbstractServiceTest {
    @Mock
    TransactionValidator validator;
    @Autowired
    TransactionServiceImpl transactionService;
    @Autowired
    TransactionSetup transactionSetup;
    @Autowired
    AccountSetup accountSetup;
    @Autowired
    TransactionRepository transactionDao;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(transactionService, "validator", validator);
    }

    @Test
    public void testRecordTransactionHappy() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        doNothing().when(validator).validateTransaction(correctDto);
        doNothing().when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doNothing().when(validator).transactionExists(correctDto.getTransactionRefId());
        doNothing().when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        transactionService.recordTransaction(correctDto);
        Transaction transaction = transactionDao.selectByTransactionRefId(correctDto.getTransactionRefId());
        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionLineItems());
        assertEquals(correctDto.getTransactionLineItems().size(), transaction.getTransactionLineItems().size());
        assertEquals(correctDto.getTransactionDate(), transaction.getTransactionDate());
    }

    @Test
    public void testRecordTransactionSadInvalidRequest() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        doThrow(new TransactionValidationException("Incomplete transaction request, amounts should be > 0"))
                .when(validator).validateTransaction(correctDto);
        doNothing().when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doNothing().when(validator).transactionExists(correctDto.getTransactionRefId());
        doNothing().when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        try {
            transactionService.recordTransaction(correctDto);
            fail("Should have thrown exception");
        } catch (TransactionValidationException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }

    }

    @Test
    public void testRecordTransactionSadUnbalancedTransaction() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        doNothing().when(validator).validateTransaction(correctDto);
        doThrow(new UnbalancedTransactionLineItemsException("Transaction lineItems not balanced"))
                .when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doNothing().when(validator).transactionExists(correctDto.getTransactionRefId());
        doNothing().when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        try {
            transactionService.recordTransaction(correctDto);
            fail("Should have thrown exception");
        } catch (UnbalancedTransactionLineItemsException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    public void testRecordTransactionSadTransactionAlreadyExists() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        doNothing().when(validator).validateTransaction(correctDto);
        doNothing().when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doThrow(new TransactionAlreadyExistsException(correctDto.getTransactionRefId())).when(validator).transactionExists(correctDto.getTransactionRefId());
        doNothing().when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        try {
            transactionService.recordTransaction(correctDto);
            fail("Should have thrown exception");
        } catch (TransactionAlreadyExistsException e) {
            assertEquals(ErrorCode.CONFLICT, e.getErrorCode());
        }
    }

    @Test
    public void testRecordTransactionSadAccountNotFound() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        TransactionLineItemDto lineItemDto = correctDto.getTransactionLineItems().get(0);
        doNothing().when(validator).validateTransaction(correctDto);
        doNothing().when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doNothing().when(validator).transactionExists(correctDto.getTransactionRefId());
        doThrow(new AccountNotFoundException(lineItemDto.getAccountId())).when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        try {
            transactionService.recordTransaction(correctDto);
            fail("Should have thrown exception");
        } catch (AccountNotFoundException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
    }

    @Test
    public void testRecordTransactionSadCurrencyMisMatch() throws TransactionValidationException,
            UnbalancedTransactionLineItemsException, TransactionAlreadyExistsException, AccountNotFoundException {
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        accountSetup.setupAccount(ACCOUNT_REQUEST_ID_2, ACCOUNT_EXTERNAL_ID_2);
        TransactionDto correctDto = TransactionDtoDataSetup.getCorrectTransactionDto();
        doNothing().when(validator).validateTransaction(correctDto);
        doNothing().when(validator).isTransactionBalanced(correctDto.getTransactionLineItems());
        doNothing().when(validator).transactionExists(correctDto.getTransactionRefId());
        doThrow(new TransactionValidationException("Currency from transaction lineItem does not match account's currency for one or more lineItems"))
                .when(validator).currenciesMatch(correctDto.getTransactionLineItems());
        try {
            transactionService.recordTransaction(correctDto);
            fail("Should have thrown exception");
        } catch (TransactionValidationException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

}
