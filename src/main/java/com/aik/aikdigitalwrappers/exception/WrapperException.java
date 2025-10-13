package com.aik.aikdigitalwrappers.exception;

public class WrapperException extends RuntimeException {
    private final String code;

    public WrapperException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
