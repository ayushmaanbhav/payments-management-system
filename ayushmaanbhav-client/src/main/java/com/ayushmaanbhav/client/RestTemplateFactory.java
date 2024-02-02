package com.ayushmaanbhav.client;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.Collections;

public class RestTemplateFactory {

    private int connectTimeout = 10; // 5 seconds
    private int readTimeout = 30; // 25 seconds
    private int maxTotal = 100;
    private int defaultMaxRoute = 100;
    private SSLContext sslContext;
    private ClientHttpRequestInterceptor interceptor;



    public void setConnectTimeoutInSeconds(int timeoutSeconds) {
        this.connectTimeout = timeoutSeconds;
    }

    public void setReadTimeoutInSeconds(int timeoutSeconds) {
        this.readTimeout = timeoutSeconds;
    }

    public void setInterceptor(ClientHttpRequestInterceptor interceptor) {
        this.interceptor = interceptor;
    }


    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setDefaultMaxRoute(int defaultMaxRoute) {
        this.defaultMaxRoute = defaultMaxRoute;
    }

    public RestTemplate getRestTemplate() {
        ClientHttpRequestFactory factory = getRequestFactory(this.connectTimeout, this.readTimeout);
        RestTemplate restTemplate = new RestTemplate();
        if (interceptor != null) {
            restTemplate.setInterceptors(Collections.singletonList(interceptor));
        }
        restTemplate.setRequestFactory(factory);
        restTemplate.setErrorHandler(new RestResponseErrorHandler());
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        return restTemplate;
    }

    private ClientHttpRequestFactory getRequestFactory(int connectTimeout, int readTimeout) {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        //time taken to form a connection
        factory.setConnectTimeout(connectTimeout * 1000);
        //after connection is form, time take to read first byte
        factory.setReadTimeout(readTimeout * 1000);
        PoolingHttpClientConnectionManager connManager = null;
        if (sslContext != null) {
            connManager = new PoolingHttpClientConnectionManager(getSslConnectionSocketFactory());
        } else {
            connManager = new PoolingHttpClientConnectionManager();
        }
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxRoute);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .disableCookieManagement()
                .disableRedirectHandling()
                .disableAutomaticRetries()
                .build();
        factory.setHttpClient(httpClient);
        return factory;

    }

    private Registry<ConnectionSocketFactory> getSslConnectionSocketFactory() {
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[] { "TLSv1.2", "TLSv1.1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionSocketFactory).build();
        return socketFactoryRegistry;

    }

}
