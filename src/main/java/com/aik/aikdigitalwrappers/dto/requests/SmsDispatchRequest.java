package com.aik.aikdigitalwrappers.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class SmsDispatchRequest {

    @Schema(hidden = true)
    private String strAppID;
    @Schema(hidden = true)
    private String strADC_TranCode;
    @Schema(hidden = true)
    private String strDelimiter;
    private String strDate;
    private String strTime;
    @Schema(hidden = true)
    private String strNetID;
    private String intRefNum;
    private String intSTAN;
    @Schema(hidden = true)
    private String strTranMsg;
    @Schema(hidden = true)
    private String strMCC;
    @Schema(hidden = true)
    private String strToken;
    private String Channeltype;
    private String MobileNo;
    private String message;

    // Getters and setters
    public String getStrAppID() { return strAppID; }
    public void setStrAppID(String strAppID) { this.strAppID = strAppID; }

    public String getStrADC_TranCode() { return strADC_TranCode; }
    public void setStrADC_TranCode(String strADC_TranCode) { this.strADC_TranCode = strADC_TranCode; }

    public String getStrDelimiter() { return strDelimiter; }
    public void setStrDelimiter(String strDelimiter) { this.strDelimiter = strDelimiter; }

    public String getStrDate() { return strDate; }
    public void setStrDate(String strDate) { this.strDate = strDate; }

    public String getStrTime() { return strTime; }
    public void setStrTime(String strTime) { this.strTime = strTime; }

    public String getStrNetID() { return strNetID; }
    public void setStrNetID(String strNetID) { this.strNetID = strNetID; }

    public String getIntRefNum() { return intRefNum; }
    public void setIntRefNum(String intRefNum) { this.intRefNum = intRefNum; }

    public String getIntSTAN() { return intSTAN; }
    public void setIntSTAN(String intSTAN) { this.intSTAN = intSTAN; }

    public String getStrTranMsg() { return strTranMsg; }
    public void setStrTranMsg(String strTranMsg) { this.strTranMsg = strTranMsg; }

    public String getStrMCC() { return strMCC; }
    public void setStrMCC(String strMCC) { this.strMCC = strMCC; }

    public String getStrToken() { return strToken; }
    public void setStrToken(String strToken) { this.strToken = strToken; }

    public String getChanneltype() { return Channeltype; }
    public void setChanneltype(String channeltype) { Channeltype = channeltype; }

    public String getMobileNo() { return MobileNo; }
    public void setMobileNo(String mobileNo) { MobileNo = mobileNo; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
