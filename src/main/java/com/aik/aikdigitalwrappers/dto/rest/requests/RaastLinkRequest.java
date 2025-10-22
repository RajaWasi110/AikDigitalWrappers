package com.aik.aikdigitalwrappers.dto.rest.requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class RaastLinkRequest {
    private String strAppID;
    private String strADC_TranCode;
    private String strDelimiter;
    private String strDate;
    private String strTime;
    private String strNetID;
    private String intRefNum;
    private String intSTAN;
    private String strTranMsg;
    @Schema(hidden = true)
    private String strMerName;
    private String strMCC;
    private String strToken;
    private String ClientIP;
    private String DeviceIP;
    private String Deviceversion;
    private String DeviceType;
    private String DeviceMEI;
    private String ApplicationVersion;
    private String UUID;
    private String CustomerID;
    private String Latitude;
    private String Longitude;
    private String transmissionDateTime;
    private String uidType;
    private String uidValue;
    private String CNIC;
    private String Channeltype;

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

    public String getTransmissionDateTime() {
        return transmissionDateTime;
    }

    public void setTransmissionDateTime(String transmissionDateTime) {
        this.transmissionDateTime = transmissionDateTime;
    }

    public String getUidType() {
        return uidType;
    }

    public void setUidType(String uidType) {
        this.uidType = uidType;
    }

    public String getUidValue() {
        return uidValue;
    }

    public void setUidValue(String uidValue) {
        this.uidValue = uidValue;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getChanneltype() {
        return Channeltype;
    }

    public void setChanneltype(String channeltype) {
        Channeltype = channeltype;
    }
}
