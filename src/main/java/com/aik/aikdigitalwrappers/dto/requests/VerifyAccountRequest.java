package com.aik.aikdigitalwrappers.dto.requests;

import lombok.Data;

@Data
public class VerifyAccountRequest {
    private String cnic;
    private String dateTime;
    private String mobileNumber;
    private String rrn;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
}
