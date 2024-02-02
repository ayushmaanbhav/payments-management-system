package com.ayushmaanbhav.gatewayProvider.gatewayClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.AbstractUnitTest;
import com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.GatewayClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorConverter;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorMessageRetriever;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = PRIVATE)
public class GatewayClientTest extends AbstractUnitTest {

    @Autowired
    GatewayClient gatewayClient;
    @Mock
    SetuErrorConverter setuErrorConverter;
    @Mock
    SetuErrorMessageRetriever setuErrorMessageRetriever;
    @Mock
    ObjectMapper mapper;

    final RestTemplate restTemplate = mock(RestTemplate.class);

    @Before
    public void setup() {
        ReflectionTestUtils.setField(gatewayClient, "mapper", mapper);
        ReflectionTestUtils.setField(gatewayClient, "restTemplate", restTemplate);
    }

    @Test
    public void makeGatewayRequest() throws ApiException, JsonProcessingException {
//        String baseUrl = BASE_URL;
//        String refreshToken = REFRESH_TOKEN;
//        String merchantId = MERCHANT_ID;
//        HttpHeaders httpHeaders = new HttpHeaders() {{
//            add("X-Setu-Product-Instance-ID", merchantId);
//            add("Authorization", "Bearer " + refreshToken);
//        }};
//
//        SetuPaymentRequest setuPaymentRequest = SetuPaymentRequestDataSetup.getSetuPaymentRequest();
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        ResponseEntity<String> response = new ResponseEntity<>("Test Response Body", responseHeaders, HttpStatus.OK);
//        GenericSetuPaymentResponse<Object> responsePojo = GenericSetuPaymentResponse.builder()
//                .data(SetuCreatePaymentResponseDataDataSetup.getSetuCreatePaymentResponseData())
//                .requestStatus(200)
//                .requestSuccess(true)
//                .build();
//
//        doReturn("TestRequest").when(mapper).writeValueAsString(any(SetuPaymentRequest.class));
//        doReturn("TestHeaders").when(mapper).writeValueAsString(any(HttpHeaders.class));
//        doReturn("TestParams").when(mapper).writeValueAsString(any(MultiValueMap.class));
//
//        doReturn(ErrorCode.INTERNAL_SERVER_ERROR).when(setuErrorConverter).apply(any());
//        doReturn("Test Error Message").when(setuErrorMessageRetriever).apply(any());
//        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(response);
//        doReturn(200).when(mapper).readValue(response.getBody(), new TypeReference<>() {});
//        doReturn(responsePojo).when(mapper).readValue(response.getBody(),
//                new TypeReference<GenericSetuPaymentResponse<SetuCreatePaymentResponseData>>() {});
//
//        GatewayGenericResponse<GenericSetuPaymentResponse<SetuCreatePaymentResponseData>> genericResponse = gatewayClient
//                .makeGatewayRequest(HttpMethod.POST, baseUrl,"payment-links", httpHeaders,
//                        new LinkedMultiValueMap<>(), setuPaymentRequest,
//                        new TypeReference<GenericSetuPaymentResponse<SetuCreatePaymentResponseData>>() {},
//                        new TypeReference<SetuErrorResponse>() {}, setuErrorConverter, setuErrorMessageRetriever);
//
//        System.out.println(genericResponse);
    }
}
