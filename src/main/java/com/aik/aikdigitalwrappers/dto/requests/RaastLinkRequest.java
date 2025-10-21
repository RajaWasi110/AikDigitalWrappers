package com.aik.aikdigitalwrappers.dto.requests;

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

}
