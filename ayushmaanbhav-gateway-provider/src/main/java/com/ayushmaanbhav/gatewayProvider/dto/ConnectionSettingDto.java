package com.ayushmaanbhav.gatewayProvider.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ConnectionSettingDto {
    @NotNull String baseUrl;
    @NotNull String key;
    @NotNull String secretToken;
    String refreshToken;
}
