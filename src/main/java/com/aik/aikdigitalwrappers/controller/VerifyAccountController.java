package com.aik.aikdigitalwrappers.controller;


import com.aik.aikdigitalwrappers.dto.soap.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.service.VerifyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class VerifyAccountController {

    @Autowired
    private VerifyAccountService service;

    @PostMapping("/uat/verifyAccount")
    public ResponseEntity<VerifyAccountResponse> verifyAccountUat(@RequestBody VerifyAccountRequest request) {
        return ResponseEntity.ok(service.verifyAccountUat(request));
    }

    @PostMapping("/prod/verifyAccount")
    public ResponseEntity<VerifyAccountResponse> verifyAccountProd(@RequestBody VerifyAccountRequest request) {
        return ResponseEntity.ok(service.verifyAccountProd(request));
    }
}
