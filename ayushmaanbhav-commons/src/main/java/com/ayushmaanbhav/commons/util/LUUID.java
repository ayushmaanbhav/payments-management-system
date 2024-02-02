package com.ayushmaanbhav.commons.util;

import java.util.UUID;

public class LUUID {
    public static String generateUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
