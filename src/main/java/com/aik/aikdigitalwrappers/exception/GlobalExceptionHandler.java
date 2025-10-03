package com.aik.aikdigitalwrappers.exception;


import com.aik.aikdigitalwrappers.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.Instant;


@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ApiErrorResponse> handleExternalApiException(ExternalApiException ex, HttpServletRequest req) {
        log.error("ExternalApiException: {}", ex.getMessage());
        ApiErrorResponse err = new ApiErrorResponse(Instant.now(), 502, "Bad Gateway", ex.getMessage(), req.getRequestURI(), req.getHeader("X-Correlation-Id"));
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        ApiErrorResponse err = new ApiErrorResponse(Instant.now(), 404, "Not Found", ex.getMessage(), req.getRequestURI(), req.getHeader("X-Correlation-Id"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        ApiErrorResponse err = new ApiErrorResponse(Instant.now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI(), req.getHeader("X-Correlation-Id"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).findFirst().orElse(ex.getMessage());
        ApiErrorResponse err = new ApiErrorResponse(Instant.now(), 422, "Validation Failed", message, req.getRequestURI(), req.getHeader("X-Correlation-Id"));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception", ex);
        ApiErrorResponse err = new ApiErrorResponse(Instant.now(), 500, "Internal Server Error", ex.getMessage(), req.getRequestURI(), req.getHeader("X-Correlation-Id"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}