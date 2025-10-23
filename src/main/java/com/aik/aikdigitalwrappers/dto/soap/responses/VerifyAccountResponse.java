package com.aik.aikdigitalwrappers.dto.soap.responses;

public class VerifyAccountResponse {

    private String rrn;
    private String responseCode;
    private String responseDescription;
    private String mobileNumber;
    private String cnic;
    private String hashData;
    private String rawResponse;

    // --- Constructors ---
    public VerifyAccountResponse() {
    }

    public VerifyAccountResponse(String rrn, String responseCode, String responseDescription,
                                    String mobileNumber, String cnic, String hashData, String rawResponse) {
        this.rrn = rrn;
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.mobileNumber = mobileNumber;
        this.cnic = cnic;
        this.hashData = hashData;
        this.rawResponse = rawResponse;
    }

    // --- Getters and Setters ---
    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }
}
