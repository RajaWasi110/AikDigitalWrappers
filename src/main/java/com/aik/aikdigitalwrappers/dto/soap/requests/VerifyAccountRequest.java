package com.aik.aikdigitalwrappers.dto.soap.requests;

import io.swagger.v3.oas.annotations.media.Schema;


public class VerifyAccountRequest {

    @Schema(example = "4521864349575")
    private String cnic;

    @Schema(example = "20251004123630")
    private String dateTime;

    @Schema(example = "03132730408")
    private String mobileNumber;

    @Schema(example = "0861040102325230")
    private String rrn;

    @Schema(example = "")
    private String reserved2;

    @Schema(example = "")
    private String reserved3;

    @Schema(example = "")
    private String reserved4;

    @Schema(example = "")
    private String reserved5;

    @Schema(example = "SHA256 Hash Value")
    private String hashData;


    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getReserved5() {
        return reserved5;
    }

    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }
}
