package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPinRegistrationResponse {
    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String hashData;
}
