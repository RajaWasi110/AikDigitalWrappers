package com.aik.aikdigitalwrappers.controller;


import com.aik.aikdigitalwrappers.dto.requests.SmsRequestDto;
import com.aik.aikdigitalwrappers.dto.responses.GenericResponseDto;
import com.aik.aikdigitalwrappers.service.WrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {


    private final WrapperService wrapperService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> sendSms(@Valid @RequestBody SmsRequestDto request) {
        GenericResponseDto resp = wrapperService.sendSms(request);
        return ResponseEntity.ok(resp);
    }
}