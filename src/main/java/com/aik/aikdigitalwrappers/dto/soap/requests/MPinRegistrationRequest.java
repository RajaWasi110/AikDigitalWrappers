package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPinRegistrationRequest {

    private String mobileNumber;   // Required - client
    private String dateTime;       // Required - client (yyyyMMddHHmmss)
    private String rrn;            // Required - client
    private String mpin;           // Required - Encrypted
    private String confirmMpin;    // Required - Encrypted
    private String reserved1;      // Optional
    private String reserved2;      // Optional
    private String reserved3;      // Optional
    private String reserved4;      // Optional
    private String reserved5;      // Optional
}
