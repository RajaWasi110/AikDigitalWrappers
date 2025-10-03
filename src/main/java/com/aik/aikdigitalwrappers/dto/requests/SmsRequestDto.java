package com.aik.aikdigitalwrappers.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestDto {
    @NotBlank
    private String MobileNo;
    @NotBlank
    private String message;
    private String strToken;
    private String intRefNum;
}