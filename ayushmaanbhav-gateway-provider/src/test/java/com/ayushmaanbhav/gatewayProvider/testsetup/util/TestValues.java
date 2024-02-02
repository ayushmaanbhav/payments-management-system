package com.ayushmaanbhav.gatewayProvider.testsetup.util;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
public class TestValues {
    public static String REQUEST_ID = "1";
    public static String PAYMENT_ORDER_EXTERNAL_ID = "eg8a0289a62146ed9a0ab47660e2c641";
    public static String LINE_ITEM_EXTERNAL_ID = "ye8a0289a62146ed9a0ab47660e2c641";
    public static String PHONE = "9889881000";
    public static Long NORMALISED_AMOUNT = 10000L;
    public static Integer LINK_EXPIRY_TIME = 1;

    public static String PROVIDER_CONFIG_EXTERNAL_ID = "be8a0289a62136ed9a0ab47665e2c641";
    public static GatewayProvider PROVIDER = GatewayProvider.SETU;
    public static String MERCHANT_ID = "12123456712";
    public static Boolean CONFIG_DISABLED = false;
    public static String GATEWAY_CONNECTION_EXTERNAL_ID = "be8a0289a62136ed8a0ab47665e2c641";
    public static Boolean TOKEN_REFRESH = true;
    public static String BASE_URL = "https://baseurl/api/";
    public static String KEY = "ae8a0289a62136ed8a0ab47665e2c641";
    public static String SECRET = "ce8a0289a62136ed8a0ab47665e2c641";
    public static String PROVIDER_ORDER_ID = "1237231484029371956";
    public static String WEB_URL = "https://testweburl/.com";
    public static String DEEP_LINK = "upi://pay?testdeeplink";
    public static String API_CLIENT_CLASS =
            "com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.client.CreatePaymentClient";
    public static String PROVIDER_STATUS = "BILL_CREATED";
    public static String HEADERS_STRING = "{TEST_HEADERS}";
    public static String REQUEST_STRING = "{TEST_REQUEST}";
    public static String RESPONSE_STRING = "{TEST_RESPONSE}";
    public static String REFRESH_TOKEN = "hsfjhgfwfjkfhqorhwlfhaohfalkforyajfaklfjaklfjsklfjsal";
}
