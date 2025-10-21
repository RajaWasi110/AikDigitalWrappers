package com.aik.aikdigitalwrappers.controller.soap;

import com.aik.aikdigitalwrappers.dto.soap.requests.ResetPinRequest;
import com.aik.aikdigitalwrappers.dto.soap.responses.ResetPinResponse;
import com.aik.aikdigitalwrappers.service.soap.ResetPinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ResetPinController {

    @Autowired
    private ResetPinService resetPinService;

    // ----------- UAT -----------
    @PostMapping("/uat/resetPin")
    public ResponseEntity<ResetPinResponse> resetPinUat(@RequestBody ResetPinRequest request) {
        ResetPinResponse response = resetPinService.resetPinUat(request);
        return ResponseEntity.ok(response);
    }

    // ----------- PROD -----------
    @PostMapping("/prod/resetPin")
    public ResponseEntity<ResetPinResponse> resetPinProd(@RequestBody ResetPinRequest request) {
        ResetPinResponse response = resetPinService.resetPinProd(request);
        return ResponseEntity.ok(response);
    }
}
