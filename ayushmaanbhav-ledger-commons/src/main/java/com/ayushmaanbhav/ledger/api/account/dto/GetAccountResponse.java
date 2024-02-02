package com.ayushmaanbhav.ledger.api.account.dto;

import com.ayushmaanbhav.commons.contstants.AccountType;
import com.ayushmaanbhav.commons.contstants.Currency;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GetAccountResponse {
    @NonNull String accountId;
    @NonNull Currency currency;
    @NonNull AccountType type;
}
