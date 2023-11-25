package com.urlshotener.url.shortner.hashing;

import org.springframework.stereotype.Component;

@Component
public class CustomBase62 {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encodeBase62(byte[] input) {
        StringBuilder result = new StringBuilder();
        long value = 0;

        for (byte b : input) {
            value = (value << 8) | (b & 0xFF);
        }

        while (value > 0) {
            result.insert(0, BASE62_CHARS.charAt((int) (value % 62)));
            value /= 62;
        }

        return result.toString();
    }
}
