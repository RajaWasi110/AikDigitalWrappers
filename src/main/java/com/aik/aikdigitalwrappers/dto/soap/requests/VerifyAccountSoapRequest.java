package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VerifyAccountSoapRequest {
    private String userName;
    private String password;
    private String cnic;
    private String dateTime;
    private String mobileNumber;
    private String rrn;
    private String transactionType;
    private String channelId;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
    private String hashData;

    public VerifyAccountSoapRequest() {

    }
}