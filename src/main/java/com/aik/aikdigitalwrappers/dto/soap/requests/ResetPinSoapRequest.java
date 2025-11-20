package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.*;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPinSoapRequest {
    private String userName;
    private String password;
    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String channelId;
    private String terminalId;
    private String newLoginPin;
    private String confirmLoginPin;
    private String cnic;
    private String reserved1;
    private String reserved2;
    private String hashData;
}