package com.aik.aikdigitalwrappers.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericSoapResponse {
    private String responseCode;
    private String responseDescription;
    private String rrn;
}
