package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentStatus;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuPaymentStatusMapper implements OneWayMapper<SetuPaymentStatus, PaymentStatus> {

    @Override
    public @NonNull PaymentStatus forward(@NonNull SetuPaymentStatus input) throws ApiException {
        switch (input) {
            case BILL_CREATED:
                return PaymentStatus.ACTIVE;
            case PAYMENT_FAILED:
            case BILL_EXPIRED:
                return PaymentStatus.FAIL;
            case PAYMENT_SUCCESSFUL:
            case CREDIT_RECEIVED:
            case SETTLEMENT_FAILED:
            case SETTLEMENT_SUCCESSFUL:
                return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.ACTIVE;
    }
}
