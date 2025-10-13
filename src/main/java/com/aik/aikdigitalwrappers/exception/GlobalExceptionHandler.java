package com.aik.aikdigitalwrappers.exception;

import com.aik.aikdigitalwrappers.dto.responses.GenericSoapResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrapperException.class)
    public ResponseEntity<GenericSoapResponse> handleWrapperException(WrapperException ex) {
        GenericSoapResponse response = new GenericSoapResponse(
                ex.getCode(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericSoapResponse> handleGenericException(Exception ex) {
        GenericSoapResponse response = new GenericSoapResponse(
                "99",
                "Internal error: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
