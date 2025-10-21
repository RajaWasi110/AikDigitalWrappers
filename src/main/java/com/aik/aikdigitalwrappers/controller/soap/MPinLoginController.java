package com.aik.aikdigitalwrappers.controller.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.MPinLoginRequest;
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

    // UAT Endpoint
    @PostMapping("/uat/mpinLogin")
    public ResponseEntity<MPinLoginResponse> loginUat(@RequestBody MPinLoginRequest request) {
        MPinLoginResponse response = mPinLoginService.loginMPinUat(request);
        return ResponseEntity.ok(response);
    }

    // PROD Endpoint
    @PostMapping("/prod/mpinLogin")
    public ResponseEntity<MPinLoginResponse> loginProd(@RequestBody MPinLoginRequest request) {
        MPinLoginResponse response = mPinLoginService.loginMPinProd(request);
        return ResponseEntity.ok(response);
    }
}
