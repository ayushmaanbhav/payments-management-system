package com.ayushmaanbhav.ledger.testsetup.transaction;

import com.ayushmaanbhav.commons.contstants.AccountOperationType;
import com.ayushmaanbhav.commons.contstants.TransactionTransferEntityType;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ayushmaanbhav.ledger.util.TestValues.*;
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDtoDataSetup {

    public static TransactionDto getCorrectTransactionDto() {
        TransactionLineItemDto lineItem1 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_1, 10000L, AccountOperationType.DEBIT,
                        TransactionTransferEntityType.CURRENCY);
        TransactionLineItemDto lineItem2 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_2, 10000L, AccountOperationType.CREDIT,
                        TransactionTransferEntityType.CURRENCY);
        List<TransactionLineItemDto> lineItemDtos = new ArrayList<>() {{
            add(lineItem1);
            add(lineItem2);
        }};
        return TransactionDto.builder()
                .transactionDate(ZonedDateTime.now())
                .transactionRefId(TRANSACTION_REF_ID)
                .transactionLineItems(lineItemDtos)
                .build();
    }

    public static TransactionDto getFaultySingleLineItemTransactionDto() {
        TransactionLineItemDto lineItem1 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_1, 10000L, AccountOperationType.DEBIT,
                        TransactionTransferEntityType.CURRENCY);
        List<TransactionLineItemDto> lineItemDtos = new ArrayList<>() {{
            add(lineItem1);
        }};
        return TransactionDto.builder()
                .transactionDate(ZonedDateTime.now())
                .transactionRefId(TRANSACTION_REF_ID)
                .transactionLineItems(lineItemDtos)
                .build();
    }

    public static TransactionDto getFaultyBlankRefIdTransactionDto() {
        TransactionLineItemDto lineItem1 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_1, 10000L, AccountOperationType.DEBIT,
                        TransactionTransferEntityType.CURRENCY);
        TransactionLineItemDto lineItem2 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_2, 10000L, AccountOperationType.CREDIT,
                        TransactionTransferEntityType.CURRENCY);
        List<TransactionLineItemDto> lineItemDtos = new ArrayList<>() {{
            add(lineItem1);
            add(lineItem2);
        }};
        return TransactionDto.builder()
                .transactionDate(ZonedDateTime.now())
                .transactionRefId("")
                .transactionLineItems(lineItemDtos)
                .build();
    }

    public static TransactionDto getFaultyAmountTransactionDto() {
        TransactionLineItemDto lineItem1 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_1, 10000L, AccountOperationType.DEBIT,
                        TransactionTransferEntityType.CURRENCY);
        TransactionLineItemDto lineItem2 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_2, 0L, AccountOperationType.CREDIT,
                        TransactionTransferEntityType.CURRENCY);
        List<TransactionLineItemDto> lineItemDtos = new ArrayList<>() {{
            add(lineItem1);
            add(lineItem2);
        }};
        return TransactionDto.builder()
                .transactionDate(ZonedDateTime.now())
                .transactionRefId(TRANSACTION_REF_ID)
                .transactionLineItems(lineItemDtos)
                .build();
    }

    public static TransactionDto getFaultyUnbalancedTransactionDto() {
        TransactionLineItemDto lineItem1 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_1, 10000L, AccountOperationType.DEBIT,
                        TransactionTransferEntityType.CURRENCY);
        TransactionLineItemDto lineItem2 = TransactionLineItemDtoDataSetup
                .getTransactionLineItemDto(ACCOUNT_EXTERNAL_ID_2, 5000L, AccountOperationType.CREDIT,
                        TransactionTransferEntityType.CURRENCY);
        List<TransactionLineItemDto> lineItemDtos = new ArrayList<>() {{
            add(lineItem1);
            add(lineItem2);
        }};
        return TransactionDto.builder()
                .transactionDate(ZonedDateTime.now())
                .transactionRefId(TRANSACTION_REF_ID)
                .transactionLineItems(lineItemDtos)
                .build();
    }


}
