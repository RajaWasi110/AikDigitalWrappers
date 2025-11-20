package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MPinRegistrationSoapRequest {

    private String userName;
    private String password;
    private String mobileNumber;
    private String dateTime;
    private String rrn;
    private String channelId;
    private String terminalId;
    private String mpin;
    private String confirmMpin;
    private String reserved1;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
    private String hashData;
}
