package com.aik.aikdigitalwrappers.dto.requests;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MiniStatementRequestDto {
    private String Account_Number;
    private String CNIC;
}