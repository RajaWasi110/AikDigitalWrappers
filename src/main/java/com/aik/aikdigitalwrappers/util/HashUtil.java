package com.aik.aikdigitalwrappers.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class HashUtil {
    public static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Unable to compute hash", e);
        }
    }
}