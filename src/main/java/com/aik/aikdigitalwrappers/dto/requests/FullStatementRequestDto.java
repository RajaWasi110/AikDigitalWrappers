package com.aik.aikdigitalwrappers.dto.requests;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class FullStatementRequestDto {
    private String fromDate;
    private String toDate;
    private String AccountNo;
    private String CNIC;
}