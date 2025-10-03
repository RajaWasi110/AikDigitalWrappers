package com.aik.aikdigitalwrappers.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInquiryRequestDto {
    @NotBlank
    private String fromAccount;
    private String userId;
    private String password;
}