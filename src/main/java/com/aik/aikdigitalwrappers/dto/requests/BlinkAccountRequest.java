package com.aik.aikdigitalwrappers.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class BlinkAccountRequest {

    // =========================================
    // Hidden (hardcoded) fields
    // =========================================
    @Schema(hidden = true)
    private String userName = "ApiGee@JS";

    @Schema(hidden = true)
    private String password = "ApiGee@JS";

    // =========================================
    // Client-provided fields
    // =========================================
    private String cnic;
    private String dateTime;
    private String mobileNumber;
    private String rrn;
    private String consumerName;
    private String accountTitle;
    private String birthPlace;
    private String presentAddress;
    @Schema(hidden = true)
    private String cnicStatus;
    private String cnicExpiry;
    private String dob;
    private String fatherHusbandName;
    private String motherMaiden;
    private String gender;
    @Schema(hidden = true)
    private String channelId ;
    @Schema(hidden = true)
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
    private String kyc = "1";
    private String fingerTempate;
    private String fingerTempateType;
    private String fingerIndex;
    private String reserved;
    private String reserved2;
    private String reserved3;
    private String reserved4;
    private String reserved5;
    private String hashData;

    // =========================================
    // Getters and Setters
    // =========================================

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

    public String getAccountTitle() { return accountTitle; }
    public void setAccountTitle(String accountTitle) { this.accountTitle = accountTitle; }

    public String getBirthPlace() { return birthPlace; }
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }

    public String getPresentAddress() { return presentAddress; }
    public void setPresentAddress(String presentAddress) { this.presentAddress = presentAddress; }

    public String getCnicStatus() { return cnicStatus; }
    public void setCnicStatus(String cnicStatus) { this.cnicStatus = cnicStatus; }

    public String getCnicExpiry() { return cnicExpiry; }
    public void setCnicExpiry(String cnicExpiry) { this.cnicExpiry = cnicExpiry; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getFatherHusbandName() { return fatherHusbandName; }
    public void setFatherHusbandName(String fatherHusbandName) { this.fatherHusbandName = fatherHusbandName; }

    public String getMotherMaiden() { return motherMaiden; }
    public void setMotherMaiden(String motherMaiden) { this.motherMaiden = motherMaiden; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getCnicIssuanceDate() { return cnicIssuanceDate; }
    public void setCnicIssuanceDate(String cnicIssuanceDate) { this.cnicIssuanceDate = cnicIssuanceDate; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getMobileNetwork() { return mobileNetwork; }
    public void setMobileNetwork(String mobileNetwork) { this.mobileNetwork = mobileNetwork; }

    public String getCnicFrontPic() { return cnicFrontPic; }
    public void setCnicFrontPic(String cnicFrontPic) { this.cnicFrontPic = cnicFrontPic; }

    public String getCnicBackPic() { return cnicBackPic; }
    public void setCnicBackPic(String cnicBackPic) { this.cnicBackPic = cnicBackPic; }

    public String getMiscellaneousPic() { return miscellaneousPic; }
    public void setMiscellaneousPic(String miscellaneousPic) { this.miscellaneousPic = miscellaneousPic; }

    public String getSignaturePic() { return signaturePic; }
    public void setSignaturePic(String signaturePic) { this.signaturePic = signaturePic; }

    public String getBillPic() { return billPic; }
    public void setBillPic(String billPic) { this.billPic = billPic; }

    public String getPurposeOfAccount() { return purposeOfAccount; }
    public void setPurposeOfAccount(String purposeOfAccount) { this.purposeOfAccount = purposeOfAccount; }

    public String getNok() { return nok; }
    public void setNok(String nok) { this.nok = nok; }

    public String getSourceOfIncome() { return sourceOfIncome; }
    public void setSourceOfIncome(String sourceOfIncome) { this.sourceOfIncome = sourceOfIncome; }

    public String getMonthlyExpectedTurnOver() { return monthlyExpectedTurnOver; }
    public void setMonthlyExpectedTurnOver(String monthlyExpectedTurnOver) { this.monthlyExpectedTurnOver = monthlyExpectedTurnOver; }

    public String getKyc() { return kyc; }
    public void setKyc(String kyc) { this.kyc = kyc; }

    public String getFingerTempate() { return fingerTempate; }
    public void setFingerTempate(String fingerTempate) { this.fingerTempate = fingerTempate; }

    public String getFingerTempateType() { return fingerTempateType; }
    public void setFingerTempateType(String fingerTempateType) { this.fingerTempateType = fingerTempateType; }

    public String getFingerIndex() { return fingerIndex; }
    public void setFingerIndex(String fingerIndex) { this.fingerIndex = fingerIndex; }

    public String getReserved() { return reserved; }
    public void setReserved(String reserved) { this.reserved = reserved; }

    public String getReserved2() { return reserved2; }
    public void setReserved2(String reserved2) { this.reserved2 = reserved2; }

    public String getReserved3() { return reserved3; }
    public void setReserved3(String reserved3) { this.reserved3 = reserved3; }

    public String getReserved4() { return reserved4; }
    public void setReserved4(String reserved4) { this.reserved4 = reserved4; }

    public String getReserved5() { return reserved5; }
    public void setReserved5(String reserved5) { this.reserved5 = reserved5; }

    public String getHashData() { return hashData; }
    public void setHashData(String hashData) { this.hashData = hashData; }
}
