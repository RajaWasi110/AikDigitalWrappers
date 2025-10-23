package com.aik.aikdigitalwrappers.exception;

/**
 * Thrown when an external HTTP or SOAP/API call fails.
 * Provides a clear message, status code, and optional details (like raw response).
 */
public class ExternalServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String details; // Holds raw SOAP/HTTP response or exception message

    // --- Default constructor ---
    public ExternalServiceException() {
        super("External service exception occurred");
        this.statusCode = 500;
        this.details = null;
    }

    // --- Constructor with message, status code, and details ---
    public ExternalServiceException(String message, int statusCode, String details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }

    // --- Constructor with message and status code ---
    public ExternalServiceException(String message, int statusCode) {
        this(message, statusCode, null);
    }

    // --- Constructor with message and cause ---
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 500;  // default status code for internal failures
        this.details = cause != null ? cause.getMessage() : null;
    }

    // --- Getters ---
    public int getStatusCode() {
        return statusCode;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ExternalServiceException{" +
                "message=" + getMessage() +
                ", statusCode=" + statusCode +
                ", details='" + details + '\'' +
                '}';
    }
}
