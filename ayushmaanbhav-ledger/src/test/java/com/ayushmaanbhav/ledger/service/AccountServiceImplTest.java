package com.ayushmaanbhav.ledger.service;

import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.ledger.api.account.dto.CreateAccountRequest;
import com.ayushmaanbhav.ledger.api.account.dto.GetAccountResponse;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.repository.AccountRepository;
import com.ayushmaanbhav.ledger.exception.AccountAlreadyExistsException;
import com.ayushmaanbhav.ledger.exception.AccountNotFoundException;
import com.ayushmaanbhav.ledger.testsetup.account.AccountSetup;
import com.ayushmaanbhav.ledger.testsetup.account.CreateAccountRequestDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ayushmaanbhav.ledger.util.TestValues.*;
import static org.junit.Assert.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceImplTest extends AbstractServiceTest {

    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    AccountSetup accountSetup;
    @Autowired
    AccountRepository accountRepository;

    static String NON_EXISTENT_ACCOUNT_ID = "25f668444c3711eebe560242ac120002";

    @Test
    public void testCreateAccount() throws AccountAlreadyExistsException {
        CreateAccountRequest request = CreateAccountRequestDataSetup.getCreateAccountRequest(ACCOUNT_REQUEST_ID_1);
        GetAccountResponse response = accountService.createAccount(request);
        Account account = accountRepository.selectByExternalId(response.getAccountId());
        assertNotNull(account);
        assertEquals(request.getRequestId(), account.getRequestId());
        assertEquals(request.getType(), account.getType());
        assertEquals(request.getCurrency(), account.getCurrency());
        try {
            accountService.createAccount(request);
            fail("Should have thrown an exception");
        } catch (AccountAlreadyExistsException e) {
            assertEquals(ErrorCode.CONFLICT, e.getErrorCode());
        }
    }

    @Test
    public void testGetAccount() throws AccountNotFoundException {
        Account account = accountSetup.setupAccount(ACCOUNT_REQUEST_ID_1, ACCOUNT_EXTERNAL_ID_1);
        GetAccountResponse response = accountService.getAccount(account.getExternalId());
        assertNotNull(response);
        assertEquals(account.getExternalId(), response.getAccountId());
        assertEquals(account.getType(), response.getType());
        assertEquals(account.getCurrency(), response.getCurrency());
        try {
            accountService.getAccount(NON_EXISTENT_ACCOUNT_ID);
            fail("Should have thrown exception");
        } catch (AccountNotFoundException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
    }

}
