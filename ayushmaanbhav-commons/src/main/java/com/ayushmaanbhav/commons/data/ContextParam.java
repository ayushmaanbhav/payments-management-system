package com.ayushmaanbhav.commons.data;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class ContextParam {
    String gatewayProviderConfigId;
    String storeId;
    String customerId;
    String customerPhone;
    String ledgerDebitAccountId;
    String ledgerCreditAccountId;
}
