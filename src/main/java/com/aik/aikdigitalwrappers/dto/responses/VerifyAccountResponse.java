package com.aik.aikdigitalwrappers.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VerifyAccountResponse {
    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String mobileNumber;
    private String cnic;
    private String hashData;
}