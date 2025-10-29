package com.aik.aikdigitalwrappers.controller.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinRegistrationRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinRegistrationResponse;
import com.aik.aikdigitalwrappers.service.soap.MPinRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MPinRegistrationController {

    @Autowired
    private MPinRegistrationService mPinRegistrationService;

    // ----------- UAT -----------
    @PostMapping("/uat/mpinRegistration")
    public ResponseEntity<MPinRegistrationResponse> registerUat(@RequestBody MPinRegistrationRequest request) {
        MPinRegistrationResponse response = mPinRegistrationService.mpinRegistrationUat(request);
        return ResponseEntity.ok(response);
    }

    // ----------- PROD -----------
    @PostMapping("/prod/mpinRegistration")
    public ResponseEntity<MPinRegistrationResponse> registerProd(@RequestBody MPinRegistrationRequest request) {
        MPinRegistrationResponse response = mPinRegistrationService.mpinRegistrationProd(request);
        return ResponseEntity.ok(response);
    }
}
