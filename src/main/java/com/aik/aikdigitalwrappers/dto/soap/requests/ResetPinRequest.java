package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPinRequest {

    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String newLoginPin;
    private String confirmLoginPin;
    private String cnic;
    private String reserved1;
    private String reserved2;

}
