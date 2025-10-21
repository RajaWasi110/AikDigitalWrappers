package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.Data;

@Data
public class MPinLoginRequest {
    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String pin;
    private String reserved1;
    private String reserved2;
}
