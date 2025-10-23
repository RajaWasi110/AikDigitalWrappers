package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPinResponse {

    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String responseDateTime;
    private String hashData;
    private String rawResponse; // to store the complete SOAP response if needed

    // Optional constructor without rawResponse
    public ResetPinResponse(String rrn, String responseCode, String responseDescription,
                            String responseDateTime, String hashData) {
        this.rrn = rrn;
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.responseDateTime = responseDateTime;
        this.hashData = hashData;
    }
}
