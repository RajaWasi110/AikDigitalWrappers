package com.aik.aikdigitalwrappers.dto.requests;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TitleFetchRequestDto {
    private String Account;
    private String iban;
}