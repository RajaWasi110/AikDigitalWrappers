package com.aik.aikdigitalwrappers.dto.rest.responses;

import java.util.List;

public class CustomerLimitsResponse {

    private String ResponseCode;
    private String ResponseDescription;
    private String ResponseDateTime;
    private String Rrn;
    private String HashData;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }

    public String getResponseDateTime() {
        return ResponseDateTime;
    }

    public void setResponseDateTime(String responseDateTime) {
        ResponseDateTime = responseDateTime;
    }

    public String getRrn() {
        return Rrn;
    }

    public void setRrn(String rrn) {
        Rrn = rrn;
    }

    public String getHashData() {
        return HashData;
    }

    public void setHashData(String hashData) {
        HashData = hashData;
    }
}
