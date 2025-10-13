package com.aik.aikdigitalwrappers.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInquiryRequestDto {
    @NotBlank
    private String fromAccount;
    private String userId;
    private String password;
}