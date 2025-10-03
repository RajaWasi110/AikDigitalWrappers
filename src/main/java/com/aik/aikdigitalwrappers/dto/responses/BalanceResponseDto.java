package com.aik.aikdigitalwrappers.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BalanceResponseDto {

    @JsonProperty("Response_Code")
    private String responseCode;

    @JsonProperty("Response_Desc")
    private String responseDesc;

    @JsonProperty("return")
    private ReturnData returnData;

    @Data
    @NoArgsConstructor
    public static class ReturnData {
        @JsonProperty("amount")
        private Double amount;

        @JsonProperty("branchCode")
        private Integer branchCode;

        @JsonProperty("fromAccount")
        private String fromAccount;

        @JsonProperty("CURRENCY_CODE")
        private String currencyCode;
    }
}
