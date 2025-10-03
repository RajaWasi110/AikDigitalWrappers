package com.aik.aikdigitalwrappers.controller;


import com.aik.aikdigitalwrappers.dto.requests.InternalTransferRequestDto;
import com.aik.aikdigitalwrappers.dto.requests.TitleFetchRequestDto;
import com.aik.aikdigitalwrappers.dto.responses.GenericResponseDto;
import com.aik.aikdigitalwrappers.dto.responses.TitleFetchResponseDto;
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
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {


    private final WrapperService wrapperService;


    @PostMapping(path = "/internal/title", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleFetchResponseDto> internalTitle(@Valid @RequestBody TitleFetchRequestDto request) {
        TitleFetchResponseDto resp = wrapperService.internalTitleFetch(request);
        return ResponseEntity.ok(resp);
    }


    @PostMapping(path = "/ibft/title", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleFetchResponseDto> ibftTitle(@RequestBody TitleFetchRequestDto request) {
        TitleFetchResponseDto resp = wrapperService.ibftTitleFetch(request);
        return ResponseEntity.ok(resp);
    }


    @PostMapping(path = "/internal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDto> internalTransfer(@RequestBody InternalTransferRequestDto request) {
        GenericResponseDto resp = wrapperService.internalFundTransfer(request);
        return ResponseEntity.ok(resp);
    }
}