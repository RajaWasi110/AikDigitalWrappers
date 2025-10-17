package com.aik.aikdigitalwrappers.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerLimitsRequest {
    @Schema(hidden = true)
    private String UserName;
    @Schema(hidden = true)
    private String Password;
    private String AccountType;
    private String MobileNumber;
    private String DateTime;
    private String ChannelId;
    private String Rrn;
    private String HashData;

}
