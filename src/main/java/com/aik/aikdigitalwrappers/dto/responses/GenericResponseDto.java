package com.aik.aikdigitalwrappers.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GenericResponseDto {
    private String Response_Code;
    private String Response_Desc;
}