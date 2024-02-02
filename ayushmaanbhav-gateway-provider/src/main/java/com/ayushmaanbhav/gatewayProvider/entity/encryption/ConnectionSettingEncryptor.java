package com.ayushmaanbhav.gatewayProvider.entity.encryption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commonsspring.encryption.EncryptionUtil;
import com.ayushmaanbhav.gatewayProvider.dto.ConnectionSettingDto;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectionSettingEncryptor implements AttributeConverter<ConnectionSettingDto, String> {
    static EncryptionUtil encryptionUtil;
    static ObjectMapper objectMapper;

    @Autowired
    public void setEncryptionUtil(@NonNull EncryptionUtil encryptionUtil, @NonNull ObjectMapper objectMapper) {
        ConnectionSettingEncryptor.encryptionUtil = encryptionUtil;
        ConnectionSettingEncryptor.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(ConnectionSettingDto s) {
        return encryptionUtil.encrypt(objectMapper.writeValueAsString(s));
    }

    @SneakyThrows
    @Override
    public ConnectionSettingDto convertToEntityAttribute(String s) {
        return objectMapper.readValue(encryptionUtil.decrypt(s), ConnectionSettingDto.class);
    }
}
