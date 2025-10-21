package com.aik.aikdigitalwrappers.controller.rest;

import com.aik.aikdigitalwrappers.dto.rest.requests.BlinkAccountRequest;
import com.aik.aikdigitalwrappers.dto.rest.responses.BlinkAccountResponse;
import com.aik.aikdigitalwrappers.service.rest.BlinkAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BlinkAccountController {

    @Autowired
    private BlinkAccountService blinkAccountService;

    // ✅ UAT Endpoint
    @PostMapping("/uat/blinkAccount")
    public ResponseEntity<BlinkAccountResponse> createUat(@RequestBody BlinkAccountRequest request) {
        BlinkAccountResponse response = blinkAccountService.createBlinkAccountUat(request);
        return ResponseEntity.ok(response);
    }

    // ✅ PROD Endpoint
    @PostMapping("/prod/blinkAccount")
    public ResponseEntity<BlinkAccountResponse> createProd(@RequestBody BlinkAccountRequest request) {
        BlinkAccountResponse response = blinkAccountService.createBlinkAccountProd(request);
        return ResponseEntity.ok(response);
    }
}
