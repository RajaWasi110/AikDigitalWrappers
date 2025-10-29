package com.aik.aikdigitalwrappers.controller.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.MPinLoginResponse;
import com.aik.aikdigitalwrappers.service.soap.MPinLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MPinLoginController {

    @Autowired
    private MPinLoginService mPinLoginService;

    // ----------- UAT -----------
    @PostMapping("/uat/mpinLogin")
    public ResponseEntity<MPinLoginResponse> mpinLoginUat(@RequestBody MPinLoginRequest request) {
        MPinLoginResponse response = mPinLoginService.mPinLoginUat(request);
        return ResponseEntity.ok(response);
    }

    // ----------- PROD -----------
    @PostMapping("/prod/mpinLogin")
    public ResponseEntity<MPinLoginResponse> mpinLoginProd(@RequestBody MPinLoginSoapRequest request) {
        MPinLoginResponse response = mPinLoginService.mPinLoginProd(request);
        return ResponseEntity.ok(response);
    }
}
