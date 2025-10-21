package com.aik.aikdigitalwrappers.dto.rest.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RaastInquiryRequest {
    private String strAppID;
    private String strADC_TranCode;
    private String strDelimiter;
    @Size(min=4,max=4,message = "Date must be MMdd")
    private String strDate;

    @Size(min=6,max=6,message = "Time must be HHmmss")
    private String strTime;

    private String strNetID;

    @Size(min=8,max=8)
    private String intRefNum;

    @Size(min=8,max=8)
    private String intSTAN;

    private String strTranMsg;
    private String strMerName;
    private String strMCC;
    private String strToken;
    @Size(message = "IP should be in this format 0.0.0.0")
    private String ClientIP;

    @Size(message = "IP should be in this format 0.0.0.0")
    private String DeviceIP;

    private String Deviceversion;
    private String DeviceType;
    @Size(message = "MEI should be in this format 0.0.0.0")
    private String DeviceMEI;

    private String ApplicationVersion;
    private String UUID;
    private String CustomerID;
    private String Bene_Alias_Type;
    private String Bene_Alias_Value;
    private String Bene_Iban;
    private String Channeltype;
    private String Latitude;
    private String Longitude;
    private String HashData;

    public String getStrAppID() {
        return strAppID;
    }

    public void setStrAppID(String strAppID) {
        this.strAppID = strAppID;
    }

    public String getStrADC_TranCode() {
        return strADC_TranCode;
    }

    public void setStrADC_TranCode(String strADC_TranCode) {
        this.strADC_TranCode = strADC_TranCode;
    }

    public String getStrDelimiter() {
        return strDelimiter;
    }

    public void setStrDelimiter(String strDelimiter) {
        this.strDelimiter = strDelimiter;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrNetID() {
        return strNetID;
    }

    public void setStrNetID(String strNetID) {
        this.strNetID = strNetID;
    }

    public String getIntRefNum() {
        return intRefNum;
    }

    public void setIntRefNum(String intRefNum) {
        this.intRefNum = intRefNum;
    }

    public String getIntSTAN() {
        return intSTAN;
    }

    public void setIntSTAN(String intSTAN) {
        this.intSTAN = intSTAN;
    }

    public String getStrTranMsg() {
        return strTranMsg;
    }

    public void setStrTranMsg(String strTranMsg) {
        this.strTranMsg = strTranMsg;
    }

    public String getStrMerName() {
        return strMerName;
    }

    public void setStrMerName(String strMerName) {
        this.strMerName = strMerName;
    }

    public String getStrMCC() {
        return strMCC;
    }

    public void setStrMCC(String strMCC) {
        this.strMCC = strMCC;
    }

    public String getStrToken() {
        return strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getClientIP() {
        return ClientIP;
    }

    public void setClientIP(String clientIP) {
        ClientIP = clientIP;
    }

    public String getDeviceIP() {
        return DeviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        DeviceIP = deviceIP;
    }

    public String getDeviceversion() {
        return Deviceversion;
    }

    public void setDeviceversion(String deviceversion) {
        Deviceversion = deviceversion;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceMEI() {
        return DeviceMEI;
    }

    public void setDeviceMEI(String deviceMEI) {
        DeviceMEI = deviceMEI;
    }

    public String getApplicationVersion() {
        return ApplicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        ApplicationVersion = applicationVersion;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getBene_Alias_Type() {
        return Bene_Alias_Type;
    }

    public void setBene_Alias_Type(String bene_Alias_Type) {
        Bene_Alias_Type = bene_Alias_Type;
    }

    public String getBene_Alias_Value() {
        return Bene_Alias_Value;
    }

    public void setBene_Alias_Value(String bene_Alias_Value) {
        Bene_Alias_Value = bene_Alias_Value;
    }

    public String getBene_Iban() {
        return Bene_Iban;
    }

    public void setBene_Iban(String bene_Iban) {
        Bene_Iban = bene_Iban;
    }

    public String getChanneltype() {
        return Channeltype;
    }

    public void setChanneltype(String channeltype) {
        Channeltype = channeltype;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getHashData() {
        return HashData;
    }

    public void setHashData(String hashData) {
        HashData = hashData;
    }
}
