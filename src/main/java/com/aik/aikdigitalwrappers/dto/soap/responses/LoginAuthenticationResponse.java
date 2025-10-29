package com.aik.aikdigitalwrappers.dto.soap.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAuthenticationResponse {
    private String responseCode;
    private String responseDescription;
}
