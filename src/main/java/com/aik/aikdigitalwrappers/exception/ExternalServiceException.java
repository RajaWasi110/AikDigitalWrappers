package com.aik.aikdigitalwrappers.exception;

/**
 * Thrown when an external HTTP/API call fails and we want to surface a clear error.
 */
public class ExternalServiceException extends RuntimeException {
    private final int statusCode;
    private final Object details;

    public ExternalServiceException(String message, int statusCode, Object details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }

    public ExternalServiceException(String message, int statusCode) {
        this(message, statusCode, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getDetails() {
        return details;
    }
}
