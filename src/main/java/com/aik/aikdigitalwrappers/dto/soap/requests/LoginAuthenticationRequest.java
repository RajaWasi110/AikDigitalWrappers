package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAuthenticationRequest {
    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String pin;
    private String cnic;
}
