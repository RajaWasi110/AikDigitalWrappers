package com.aik.aikdigitalwrappers.dto.requests;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class InternalTransferRequestDto {
    private String fromAccount;
    private String toAccount;
    private String amount;
    private String userId;
    private String password;
    private String PurposeOfPayment;
}