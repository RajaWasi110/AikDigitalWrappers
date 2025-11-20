package com.aik.aikdigitalwrappers.controller.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.LoginAuthenticationRequest;
import com.aik.aikdigitalwrappers.dto.soap.requests.LoginAuthenticationSoapRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.LoginAuthenticationResponse;
import com.aik.aikdigitalwrappers.service.soap.LoginAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LoginAuthenticationController {

    @Autowired
    private LoginAuthenticationService loginAuthenticationService;

    // ----------- UAT -----------
    @PostMapping("/uat/loginAuthentication")
    public ResponseEntity<LoginAuthenticationResponse> loginAuthenticationUat(@RequestBody LoginAuthenticationRequest request) {
        LoginAuthenticationResponse response = loginAuthenticationService.loginAuthenticationUat(request);
        return ResponseEntity.ok(response);
    }

    // ----------- PROD -----------
    @PostMapping("/prod/loginAuthentication")
    public ResponseEntity<LoginAuthenticationResponse> loginAuthenticationProd(@RequestBody LoginAuthenticationSoapRequest request) {
        LoginAuthenticationResponse response = loginAuthenticationService.loginAuthenticationProd(request);
        return ResponseEntity.ok(response);
    }
}
