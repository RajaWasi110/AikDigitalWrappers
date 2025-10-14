package com.aik.aikdigitalwrappers.controller;

import com.aik.aikdigitalwrappers.service.SendSmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendSmsController {

    private final SendSmsService sendSmsService;

    public SendSmsController(SendSmsService sendSmsService) {
        this.sendSmsService = sendSmsService;
    }
    //UAt EndPoint
    @PostMapping("/uat/sendSms")
    public ResponseEntity<>

}
