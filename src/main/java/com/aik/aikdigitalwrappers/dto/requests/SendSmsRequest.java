package com.aik.aikdigitalwrappers.dto.requests;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SendSmsRequest {
 @NotBlank private String strAppID;
 @NotBlank private String strADC_TranCode;
 @NotBlank private String strDelimiter;

 @NotBlank
 @Size(min=4, max=4, message = "Date must be MMyy")
 private String strDate;
 @NotBlank
 @Size(min=6,max=6,message="Time must be hhmmss")
 private String strTime;
 @NotBlank
 private String strNetID;
 @NotBlank
 @Size(min=8,max=8)
 private Long intRefNum;
 @NotBlank
 @Size(min=6,max=6)
 private Long intSTAN;
 @NotBlank
 private String strTranMsg;
 @NotBlank
 private String strMCC;
 @NotBlank
 private String strToken;
 @NotBlank
 private String Channeltype;
 @Size(min=11,max=11)
 @NotBlank
 private String MobileNo;

 @NotBlank
 private String message;

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

 public Long getIntRefNum() {
  return intRefNum;
 }

 public void setIntRefNum(Long intRefNum) {
  this.intRefNum = intRefNum;
 }

 public Long getIntSTAN() {
  return intSTAN;
 }

 public void setIntSTAN(Long intSTAN) {
  this.intSTAN = intSTAN;
 }

 public String getStrTranMsg() {
  return strTranMsg;
 }

 public void setStrTranMsg(String strTranMsg) {
  this.strTranMsg = strTranMsg;
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

 public String getChanneltype() {
  return Channeltype;
 }

 public void setChanneltype(String channeltype) {
  Channeltype = channeltype;
 }

 public String getMobileNo() {
  return MobileNo;
 }

 public void setMobileNo(String mobileNo) {
  MobileNo = mobileNo;
 }

 public String getMessage() {
  return message;
 }

 public void setMessage(String message) {
  this.message = message;
 }
}
