package com.aik.aikdigitalwrappers.dto.soap.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "LoginAuthenticationRequest", namespace = "http://tempuri.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginAuthenticationSoapRequest {

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

    @XmlElement(name = "PIN")
    private String pin;

    @XmlElement(name = "Cnic")
    private String cnic;

    @XmlElement(name = "HashData")
    private String hashData;
}
