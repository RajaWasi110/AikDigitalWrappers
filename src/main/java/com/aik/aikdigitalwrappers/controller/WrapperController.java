package com.aik.aikdigitalwrappers.controller;

import com.aik.aikdigitalwrappers.dto.requests.BlinkAccountRequest;
import com.aik.aikdigitalwrappers.dto.requests.VerifyAccountRequest;
import com.aik.aikdigitalwrappers.dto.responses.BlinkAccountResponse;
import com.aik.aikdigitalwrappers.dto.responses.GenericSoapResponse;
import com.aik.aikdigitalwrappers.dto.responses.VerifyAccountResponse;
import com.aik.aikdigitalwrappers.exception.WrapperException;
import com.aik.aikdigitalwrappers.service.RestIntegrationService;
import com.aik.aikdigitalwrappers.service.SoapIntegrationService;
import com.aik.aikdigitalwrappers.service.SoapClientService;
import com.aik.aikdigitalwrappers.config.ExternalApiProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wrappers")
@RequiredArgsConstructor
public class WrapperController {

    private final SoapIntegrationService soapIntegrationService;
    private final RestIntegrationService restIntegrationService;
    private final SoapClientService soapClient;
    private final ExternalApiProperties props;

    // ✅ SOAP Endpoint: Verify Account
    @PostMapping("/verify-account")
    public ResponseEntity<GenericSoapResponse> verifyAccount(@Valid @RequestBody VerifyAccountRequest req) {
        try {
            String xml = buildSoapBody(req);
            String response = soapClient.sendSoap(
                    props.getEndpoints().getVerifyAccountSoapAction(),
                    xml
            );

            // Mock handling
            if (response != null && response.trim().startsWith("{")) {
                return ResponseEntity.ok(new GenericSoapResponse("00", "Mock Success (DEV)", req.getRrn()));
            }

            // Real SOAP parsing (for later)
            return ResponseEntity.ok(new GenericSoapResponse("00", "Real SOAP Response", req.getRrn()));

        } catch (Exception e) {
            throw new WrapperException("99", "Failed to call SOAP service: " + e.getMessage());
        }
    }

    // ✅ REST Endpoint: Blink Account
    @PostMapping("/blink-account")
    public ResponseEntity<BlinkAccountResponse> blinkAccount(@Valid @RequestBody BlinkAccountRequest req) {
        BlinkAccountResponse resp = restIntegrationService.blinkAccount(req);
        return ResponseEntity.ok(resp);
    }

    // ✅ (Optional) If you also have a dedicated VerifyAccountResponse from SoapIntegrationService
    @PostMapping("/soap/verify-account-v2")
    public ResponseEntity<VerifyAccountResponse> verifyAccountV2(@Valid @RequestBody VerifyAccountRequest req) {
        VerifyAccountResponse resp = soapIntegrationService.verifyAccount(req);
        return ResponseEntity.ok(resp);
    }

    // ✅ Helper method to build SOAP body
    private String buildSoapBody(VerifyAccountRequest req) {
        return String.format("""
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
              <soapenv:Header/>
              <soapenv:Body>
                <tem:verifyAccountRequest>
                  <UserName>ApiGee@JS</UserName>
                  <Password>ApiGee@JS</Password>
                  <Cnic>%s</Cnic>
                  <DateTime>%s</DateTime>
                  <MobileNumber>%s</MobileNumber>
                  <Rrn>%s</Rrn>
                  <TransactionType>01</TransactionType>
                  <ChannelId>APIGEE</ChannelId>
                  <Reserved1>01</Reserved1>
                  <Reserved2>%s</Reserved2>
                  <Reserved3>%s</Reserved3>
                  <Reserved4>%s</Reserved4>
                  <Reserved5>%s</Reserved5>
                  <HashData>TESTHASHDATA</HashData>
                </tem:verifyAccountRequest>
              </soapenv:Body>
            </soapenv:Envelope>
            """,
                req.getCnic(),
                req.getDateTime(),
                req.getMobileNumber(),
                req.getRrn(),
                req.getReserved2(),
                req.getReserved3(),
                req.getReserved4(),
                req.getReserved5()
        );
    }
}
