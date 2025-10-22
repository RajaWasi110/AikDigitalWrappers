package com.aik.aikdigitalwrappers.controller.rest;

import com.aik.aikdigitalwrappers.dto.rest.requests.RaastLinkRequest;
import com.aik.aikdigitalwrappers.dto.rest.responses.RaastLinkResponse;
import com.aik.aikdigitalwrappers.service.rest.RaastLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class RaastLinkController {
    @Autowired
    private RaastLinkService raastLinkService;

    //UAT Endpoint
    @PostMapping("/uat/raastLink")
    public ResponseEntity<RaastLinkResponse> createUat(@RequestBody RaastLinkRequest request){
        RaastLinkResponse response = raastLinkService.createRaastLinkUat(request);
        return ResponseEntity.ok(response);
    }

    //PROD Endpoint
    @PostMapping("/prod/raastLink")
    public ResponseEntity<RaastLinkResponse> createProd(@RequestBody RaastLinkRequest request){
        RaastLinkResponse response = raastLinkService.createRaastLinkProd(request);
        return ResponseEntity.ok(response);
    }

}
