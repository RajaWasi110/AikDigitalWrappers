package com.aik.aikdigitalwrappers.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BlinkAccountResponse {
    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String hashData;
}