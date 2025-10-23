package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
public class MPinLoginResponse {

    public MPinLoginResponse(String rrn1, String code, String desc, String datetime, String hash) {
    }
    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String responseDateTime;
    private String hashData;
}
