package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "LoginPinRequest", namespace = "http://tempuri.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class MPinLoginSoapRequest {

    @XmlElement(name = "UserName")
    private String userName;

    @XmlElement(name = "Password")
    private String password;

    @XmlElement(name = "MobileNumber")
    private String mobileNumber;

    @XmlElement(name = "DateTime")
    private String dateTime;

    @XmlElement(name = "Rrn")
    private String rrn;

    @XmlElement(name = "ChannelId")
    private String channelId;

    @XmlElement(name = "TerminalId")
    private String terminalId;

    @XmlElement(name = "PIN")
    private String pin;

    @XmlElement(name = "Reserved1")
    private String reserved1;

    @XmlElement(name = "Reserved2")
    private String reserved2;

    @XmlElement(name = "HashData")
    private String hashData;
}
