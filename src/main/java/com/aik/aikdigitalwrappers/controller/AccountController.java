package com.aik.aikdigitalwrappers.controller;


import com.aik.aikdigitalwrappers.dto.requests.BalanceInquiryRequestDto;
import com.aik.aikdigitalwrappers.dto.requests.FullStatementRequestDto;
import com.aik.aikdigitalwrappers.dto.requests.MiniStatementRequestDto;
import com.aik.aikdigitalwrappers.dto.responses.BalanceResponseDto;
import com.aik.aikdigitalwrappers.dto.responses.FullStatementResponseDto;
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
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {


    private final WrapperService wrapperService;


    @PostMapping(path = "/balance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceResponseDto> balance(@Valid @RequestBody BalanceInquiryRequestDto request) {
        BalanceResponseDto resp = wrapperService.inquireBalance(request);
        return ResponseEntity.ok(resp);
    }


    @PostMapping(path = "/ministatement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ministatement(@RequestBody MiniStatementRequestDto request) {
        return ResponseEntity.ok(wrapperService.miniStatement(request));
    }


    @PostMapping(path = "/fullstatement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FullStatementResponseDto> fullstatement(@RequestBody FullStatementRequestDto request) {
        FullStatementResponseDto resp = wrapperService.fullStatement(request);
        return ResponseEntity.ok(resp);
    }
}