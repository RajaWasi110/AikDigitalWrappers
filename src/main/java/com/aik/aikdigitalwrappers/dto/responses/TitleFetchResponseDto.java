package com.aik.aikdigitalwrappers.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TitleFetchResponseDto {
    private String Response_Code;
    private String Response_Desc;
    private ReturnBody returnObj;


    @Data
    public static class ReturnBody {
        private String accountTitle;
        private String Account;
        private String status;
        private String statusDescription;
    }
}