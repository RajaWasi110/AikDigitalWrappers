package com.aik.aikdigitalwrappers.controller;

import com.aik.aikdigitalwrappers.dto.requests.SmsDispatchRequest;
import com.aik.aikdigitalwrappers.service.SmsDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SmsDispatchController {

    @Autowired
    private SmsDispatchService smsDispatchService;

    @PostMapping("/uat/sendSms")
    public ResponseEntity<Void> sendSmsUat(@RequestBody SmsDispatchRequest request) {
        smsDispatchService.sendSmsUat(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/prod/sendSms")
    public ResponseEntity<Void> sendSmsProd(@RequestBody SmsDispatchRequest request) {
        smsDispatchService.sendSmsProd(request);
        return ResponseEntity.ok().build();
    }
}
