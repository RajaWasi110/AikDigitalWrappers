package com.aik.aikdigitalwrappers.controller;

import com.aik.aikdigitalwrappers.dto.requests.RaastInquiryRequest;
import com.aik.aikdigitalwrappers.dto.responses.RaastInquiryResponse;
import com.aik.aikdigitalwrappers.service.RaastInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RaastInquiryController {
    private final RaastInquiryService raastInquiryService;

    public RaastInquiryController(RaastInquiryService raastInquiryService) {
        this.raastInquiryService = raastInquiryService;
    }

    //UAT Endpoint
    @PostMapping("/uat/raastInquiry")
    public ResponseEntity<RaastInquiryResponse> createRaastInquiryUat(@RequestBody RaastInquiryRequest request) {
        RaastInquiryResponse response = raastInquiryService.createRaastInquiryUat(request);
        return ResponseEntity.ok(response);
    }

    //PROD Endpoint
    @PostMapping("/prod/raastInquiry")
    public ResponseEntity<RaastInquiryResponse> createRaastInquiryProd(@RequestBody RaastInquiryRequest request) {
        RaastInquiryResponse response = raastInquiryService.createRaastInquiryProd(request);
        return ResponseEntity.ok(response);
    }
}