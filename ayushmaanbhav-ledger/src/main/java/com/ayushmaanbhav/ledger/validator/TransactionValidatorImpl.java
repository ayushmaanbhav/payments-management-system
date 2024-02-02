package com.ayushmaanbhav.ledger.validator;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.TransactionTransferEntityType;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import com.ayushmaanbhav.ledger.entity.Account;
import com.ayushmaanbhav.ledger.entity.repository.AccountRepository;
import com.ayushmaanbhav.ledger.entity.repository.TransactionRepository;
import com.ayushmaanbhav.ledger.exception.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionValidatorImpl implements TransactionValidator {
    AccountRepository accountDao;
    TransactionRepository transactionDao;

    @Override
    public void validateTransaction(TransactionDto request) throws TransactionValidationException {
        if (Strings.isBlank(request.getTransactionRefId())
                || request.getTransactionLineItems().size() < 2
                || request.getTransactionLineItems().stream().anyMatch(lI -> lI.getNormalisedAmount() <= 0)) {
            throw new TransactionValidationException("Incomplete transaction request, amounts should be > 0");
        }
    }

    @Override
    public void transactionExists(String transactionRefId) throws TransactionAlreadyExistsException {
        if (transactionDao.existsByTransactionRefId(transactionRefId)) {
            throw new TransactionAlreadyExistsException(transactionRefId);
        }
    }

    @Override
    public void isTransactionBalanced(Iterable<TransactionLineItemDto> transactionLineItems)
            throws UnbalancedTransactionLineItemsException {
        MultiValueMap<Pair<Currency, TransactionTransferEntityType>, TransactionLineItemDto> lineItemsByCurrencyEntity =
                sortByCurrencyAndTransferEntityType(transactionLineItems);
        for (Pair<Currency, TransactionTransferEntityType> cePair : lineItemsByCurrencyEntity.keySet()) {
            checkTransactionLineItemsBalanced(lineItemsByCurrencyEntity.get(cePair));
        }
    }

    @Override
    public void currenciesMatch(Iterable<TransactionLineItemDto> lineItems) throws TransactionValidationException, AccountNotFoundException {
        for (TransactionLineItemDto lineItem : lineItems) {
            Account account = accountDao.selectByExternalId(lineItem.getAccountId());
            if (account == null) {
                throw new AccountNotFoundException(lineItem.getAccountId());
            }
            if (!account.getCurrency().equals(lineItem.getCurrency())) {
                throw new TransactionValidationException(
                        "Currency from transaction lineItem does not match account's currency for one or more lineItems");
            }
        }
    }

    @Override
    public void validBalance(Iterable<TransactionLineItemDto> lineItems) throws InsufficientFundsException {
        //for (String accountId : getAccountIds(lineItems)) {
            //Account account = accountDao.selectByExternalId(accountId);
            //if (account.isOverdrawn()) {
            //    throw new InsufficientFundsException(accountId);
            //}
        //}
    }

    private void checkTransactionLineItemsBalanced(Iterable<TransactionLineItemDto> lineItems)
            throws UnbalancedTransactionLineItemsException {
        long sumDebits = 0L;
        long sumCredits = 0L;
        for (var lineItem : lineItems) {
            switch (lineItem.getOperationType()) {
                case DEBIT: sumDebits += lineItem.getNormalisedAmount(); break;
                case CREDIT: sumCredits += lineItem.getNormalisedAmount(); break;
            }
        }
        if (sumDebits != sumCredits) {
            throw new UnbalancedTransactionLineItemsException("Transaction lineItems not balanced");
        }
    }

    private MultiValueMap<Pair<Currency, TransactionTransferEntityType>, TransactionLineItemDto>
    sortByCurrencyAndTransferEntityType(Iterable<TransactionLineItemDto> lineItems) {
        MultiValueMap<Pair<Currency, TransactionTransferEntityType>, TransactionLineItemDto> lineItemsByCurrency =
                new LinkedMultiValueMap<>();
        for (var lineItem : lineItems) {
            lineItemsByCurrency.add(Pair.of(lineItem.getCurrency(), lineItem.getTransferEntityType()), lineItem);
        }
        return lineItemsByCurrency;
    }

    private Set<String> getAccountIds(Iterable<TransactionLineItemDto> lineItems) {
        Set<String> getAccountIds = new HashSet<>();
        for (var lineItem: lineItems) {
            getAccountIds.add(lineItem.getAccountId());
        }
        return getAccountIds;
    }
}
