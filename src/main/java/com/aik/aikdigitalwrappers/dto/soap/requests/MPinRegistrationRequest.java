package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.Data;

@Data
public class MPinRegistrationRequest {
    private String mpin;
    private String confirmMpin;
    private String dateTime;
    private String mobileNumber;
    private String rrn;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
}
