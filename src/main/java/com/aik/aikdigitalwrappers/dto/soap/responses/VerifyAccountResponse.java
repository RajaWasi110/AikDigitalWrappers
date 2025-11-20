package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "verifyAccountResponse", namespace = "http://tempuri.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerifyAccountResponse {

    @XmlElement(name = "ResponseCode")
    private String responseCode;

    @XmlElement(name = "ResponseDescription")
    private String responseDescription;
}