package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MPinRegistrationResponse {

    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String hashData;

    // Optional constructor for quick response creation
    public MPinRegistrationResponse(String rrn, String responseCode, String responseDescription, String hash) {
        this.rrn = rrn;
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }
}
