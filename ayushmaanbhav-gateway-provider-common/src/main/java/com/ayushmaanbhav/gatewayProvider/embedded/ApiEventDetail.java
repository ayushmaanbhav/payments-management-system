package com.ayushmaanbhav.gatewayProvider.embedded;

import com.ayushmaanbhav.commons.contstants.ContentType;
import com.ayushmaanbhav.gatewayProvider.constant.ApiEventType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class ApiEventDetail {
    @NonNull @Enumerated(EnumType.STRING) ApiEventType eventType;
    @NonNull String event;
    @NonNull String httpMethod;
    @NonNull String headers;
    @NonNull String queryParams;
    @NonNull String request;
    @NonNull String httpStatusCode;
    String response;
    @NonNull @Enumerated(EnumType.STRING) ContentType contentType;
}
