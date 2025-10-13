package com.aik.aikdigitalwrappers.dto.requests;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BlinkAccountRequest {
    private String cnic;
    private String dateTime;
    private String mobileNumber;
    private String rrn;
    private String consumerName;
    private String accountTitle;
// ... add other fields as needed
}