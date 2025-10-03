package com.aik.aikdigitalwrappers.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Data
@NoArgsConstructor
public class FullStatementResponseDto {
    private String Response_Code;
    private String Response_Desc;
    private ResponseBody Response;


    @Data
    public static class ResponseBody {
        private List<Transaction> FullStatement;
    }


    @Data
    public static class Transaction {
        private Double BALANCE;
        private Double CV_AMOUNT;
        private Double FC_AMOUNT;
        private String CURRENCY_CODE;
        private String DESCRIPTION;
        private String TRANS_DATE;
        private String OS_TRANS_TYPE;
    }
}