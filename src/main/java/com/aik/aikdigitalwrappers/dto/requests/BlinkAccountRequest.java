package com.aik.aikdigitalwrappers.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BlinkAccountRequest {

    // Hide from Swagger / API documentation
    @Schema(hidden = true)
    private String userName;

    @Schema(hidden = true)
    private String password;

    @NotBlank
    @Size(min = 13, max = 13)
    private String cnic;

    @NotBlank
    @Size(min = 14, max = 14)
    private String dateTime;

    @NotBlank
    @Size(min = 11, max = 11)
    private String mobileNumber;

    @NotBlank
    @Size(min = 12, max = 16)
    private String rrn;

    @NotBlank
    private String consumerName;

    private String accountTitle;
    private String birthPlace;
    private String presentAddress;
    private String cnicStatus;
    private String cnicExpiry;
    private String dob;
    private String fatherHusbandName;
    private String motherMaiden;
    private String gender;
    private String channelId;
    private String accountType;
    private String trackingId;
    private String cnicIssuanceDate;
    private String emailAddress;
    private String mobileNetwork;
    private String cnicFrontPic;
    private String cnicBackPic;
    private String miscellaneousPic;
    private String signaturePic;
    private String billPic;
    private String purposeOfAccount;
    private String nok;
    private String sourceOfIncome;
    private String monthlyExpectedTurnOver;
    private String kyc;
    private String fingerTempate;
    private String fingerTempateType;
    private String fingerIndex;
    private String reserved;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
    private String hashData;

    // =======================
    // Getters and Setters
    // =======================
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getRrn() { return rrn; }
    public void setRrn(String rrn) { this.rrn = rrn; }

    public String getConsumerName() { return consumerName; }
    public void setConsumerName(String consumerName) { this.consumerName = consumerName; }

    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getHashData() { return hashData; }
    public void setHashData(String hashData) { this.hashData = hashData; }

    // All other optional fields (accountTitle, birthPlace, etc.)
    // Include getters and setters if needed
}
