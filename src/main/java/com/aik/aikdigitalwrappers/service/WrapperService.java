package com.aik.aikdigitalwrappers.service;


import com.aik.aikdigitalwrappers.client.ExternalApiClient;
import com.aik.aikdigitalwrappers.dto.requests.*;
import com.aik.aikdigitalwrappers.dto.responses.*;
import com.aik.aikdigitalwrappers.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WrapperService {


    private final ExternalApiClient externalApiClient;


    @Value("${external.endpoints.sms-send}")
    private String smsEndpoint;


    @Value("${external.endpoints.balance-inquiry}")
    private String balanceEndpoint;


    @Value("${external.endpoints.ministatement}")
    private String ministatementEndpoint;


    @Value("${external.endpoints.fullstatement}")
    private String fullstatementEndpoint;


    @Value("${external.endpoints.internal-title-fetch}")
    private String internalTitleFetchEndpoint;


    @Value("${external.endpoints.internal-fund-transfer}")
    private String internalFundTransferEndpoint;


    @Value("${external.endpoints.ibft-titlefetch}")
    private String ibftTitleFetchEndpoint;


    public GenericResponseDto sendSms(SmsRequestDto req) {
        GenericResponseDto response = externalApiClient.post(smsEndpoint, req, GenericResponseDto.class);
        if (response == null) throw new NotFoundException("SMS service returned no response");
        return response;
    }


    public BalanceResponseDto inquireBalance(BalanceInquiryRequestDto req) {
        BalanceResponseDto response = externalApiClient.post(balanceEndpoint, req, BalanceResponseDto.class);
        if (response == null) throw new NotFoundException("Balance service returned no response");
        return response;
    }


    public GenericResponseDto miniStatement(MiniStatementRequestDto req) {
        GenericResponseDto response = externalApiClient.post(ministatementEndpoint, req, GenericResponseDto.class);
        if (response == null) throw new NotFoundException("Ministatement service returned no response");
        return response;
    }


    public FullStatementResponseDto fullStatement(FullStatementRequestDto req) {
        FullStatementResponseDto response = externalApiClient.post(fullstatementEndpoint, req, FullStatementResponseDto.class);
        if (response == null) throw new NotFoundException("Full statement service returned no response");
        return response;
    }


    public TitleFetchResponseDto internalTitleFetch(TitleFetchRequestDto req) {
        TitleFetchResponseDto response = externalApiClient.post(internalTitleFetchEndpoint, req, TitleFetchResponseDto.class);
        if (response == null) throw new NotFoundException("Internal title fetch returned no response");
        return response;
    }


    public GenericResponseDto internalFundTransfer(InternalTransferRequestDto req) {
        GenericResponseDto response = externalApiClient.post(internalFundTransferEndpoint, req, GenericResponseDto.class);
        if (response == null) throw new NotFoundException("Internal fund transfer returned no response");
        return response;
    }


    public TitleFetchResponseDto ibftTitleFetch(TitleFetchRequestDto req) {
        TitleFetchResponseDto response = externalApiClient.post(ibftTitleFetchEndpoint, req, TitleFetchResponseDto.class);
        if (response == null) throw new NotFoundException("IBFT title fetch returned no response");
        return response;
    }
}