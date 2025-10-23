package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    // Optional constructor without reserved fields
    public MPinRegistrationRequest(String mpin, String confirmMpin, String dateTime,
                                   String mobileNumber, String rrn) {
        this.mpin = mpin;
        this.confirmMpin = confirmMpin;
        this.dateTime = dateTime;
        this.mobileNumber = mobileNumber;
        this.rrn = rrn;
    }
}
