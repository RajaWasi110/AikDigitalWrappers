package com.aik.aikdigitalwrappers.dto.rest.responses;

public class RaastInquiryResponse {
    private String responseCode;
    private String responseDescription;
    private String beneName;
    private String beneBank;
    private String beneIban;
    private String beneAliasType;
    private String beneAliasValue;
    private String transactionId;
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

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getBeneBank() {
        return beneBank;
    }

    public void setBeneBank(String beneBank) {
        this.beneBank = beneBank;
    }

    public String getBeneIban() {
        return beneIban;
    }

    public void setBeneIban(String beneIban) {
        this.beneIban = beneIban;
    }

    public String getBeneAliasType() {
        return beneAliasType;
    }

    public void setBeneAliasType(String beneAliasType) {
        this.beneAliasType = beneAliasType;
    }

    public String getBeneAliasValue() {
        return beneAliasValue;
    }

    public void setBeneAliasValue(String beneAliasValue) {
        this.beneAliasValue = beneAliasValue;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }
}
