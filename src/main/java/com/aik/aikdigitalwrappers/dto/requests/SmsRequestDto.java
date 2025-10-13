package com.aik.aikdigitalwrappers.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



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