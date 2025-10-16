package com.aik.aikdigitalwrappers.dto.soap.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VerifyAccountResponse {

    @Schema(example = "00")
    private String responseCode;

    @Schema(example = "Successful")
    private String responseDescription;

    @Schema(example = "03132730408")
    private String mobileNumber;

    @Schema(example = "4521864349575")
    private String cnic;

    @Schema(example = "0861040102325230")
    private String rrn;

    @Schema(example = "01563a83d74fa1ea50bf467209414f261afc1a16f973d4c6d06790e395c1f033")
    private String hashData;

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

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }
}
