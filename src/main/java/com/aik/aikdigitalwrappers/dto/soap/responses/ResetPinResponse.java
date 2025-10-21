package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPinResponse {
    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String responseDateTime;
    private String hashData;
}
