package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPinLoginRequest {
    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String pin;
    private String reserved1;
    private String reserved2;
}
