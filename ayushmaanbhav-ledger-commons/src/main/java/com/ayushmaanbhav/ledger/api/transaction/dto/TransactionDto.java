package com.ayushmaanbhav.ledger.api.transaction.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class TransactionDto {
    @NonNull String transactionRefId;
    @NonNull ZonedDateTime transactionDate;
    @NonNull List<TransactionLineItemDto> transactionLineItems;
}
