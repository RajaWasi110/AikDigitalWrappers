package com.aik.aikdigitalwrappers.controller;


import com.aik.aikdigitalwrappers.dto.requests.BlinkAccountRequest;
import com.aik.aikdigitalwrappers.dto.responses.BlinkAccountResponse;
import com.aik.aikdigitalwrappers.service.BlinkAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BlinkAccountController {

    private final BlinkAccountService blinkAccountService;

    public BlinkAccountController(BlinkAccountService blinkAccountService) {
        this.blinkAccountService = blinkAccountService;
    }

    // =======================
    // UAT Endpoint
    // =======================
    @PostMapping("/uat/blinkAccount")
    public ResponseEntity<BlinkAccountResponse> createUat(@RequestBody BlinkAccountRequest request) {
        BlinkAccountResponse response = blinkAccountService.createBlinkAccountUat(request);
        return ResponseEntity.ok(response);
    }

    // =======================
    // PROD Endpoint
    // =======================
    @PostMapping("/prod/blinkAccount")
    public ResponseEntity<BlinkAccountResponse> createProd(@RequestBody BlinkAccountRequest request) {
        BlinkAccountResponse response = blinkAccountService.createBlinkAccountProd(request);
        return ResponseEntity.ok(response);
    }
}
