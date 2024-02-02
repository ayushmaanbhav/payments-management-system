package com.ayushmaanbhav.payment.testsetup.paymentorder;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.repository.PaymentOrderRepository;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentOrderSetup {
    PaymentOrderRepository paymentOrderRepository;
    GatewayProviderConfigSetup configSetup;

    public PaymentOrder setupPaymentOrder(PaymentStatus status) {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(status);
        GatewayProviderConfig providerConfig = configSetup.setupGatewayProviderConfig();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup
                .getGatewayProviderPaymentDetail();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);
        paymentOrderRepository.save(paymentOrder);
        return paymentOrder;
    }

}
