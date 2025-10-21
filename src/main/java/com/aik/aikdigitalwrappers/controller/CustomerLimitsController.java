package com.aik.aikdigitalwrappers.controller;

import com.aik.aikdigitalwrappers.dto.requests.CustomerLimitsRequest;
import com.aik.aikdigitalwrappers.dto.responses.CustomerLimitsResponse;
import com.aik.aikdigitalwrappers.service.CustomerLimitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CustomerLimitsController {
    @Autowired
    private CustomerLimitsService customerLimitsService;


    //UAT Endpoint
    @PostMapping("/uat/customerLimits")
    public ResponseEntity<CustomerLimitsResponse> createCustomerLimitsUat(@RequestBody CustomerLimitsRequest request) {
        CustomerLimitsResponse response = customerLimitsService.createCustomerLimitsUat(request);
        return ResponseEntity.ok(response);
    }

    //PROD Endpoint
    @PostMapping("/prod/customerLimits")
    public ResponseEntity<CustomerLimitsResponse> createCustomerLimitsProd(@RequestBody CustomerLimitsRequest request) {
        CustomerLimitsResponse response = customerLimitsService.createCustomerLimitsProd(request);
        return ResponseEntity.ok(response);
    }
}


