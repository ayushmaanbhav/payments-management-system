package com.ayushmaanbhav.commonsspring.encryption;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Encrypt implements AttributeConverter<String,String> {
    EncryptionUtil encryptionUtil;

    @Autowired
    public Encrypt(@NonNull EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String s) {
        return encryptionUtil.encrypt(s);
    }

    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionUtil.decrypt(s);
    }
}
