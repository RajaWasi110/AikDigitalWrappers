package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResetPinResponse {

    private String responseCode;
    private String responseDescription;

    public ResetPinResponse(String rrn, String code, String desc, String datetime, String hash) {
    }
}
