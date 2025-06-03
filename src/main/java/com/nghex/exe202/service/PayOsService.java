package com.nghex.exe202.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nghex.exe202.dto.payos.*;
import com.nghex.exe202.util.enums.payos.SignatureUtils;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class PayOsService {
    private final String clientId;
    private final String apiKey;
    private final String checksumKey;
    private final String partnerCode;

    private static final String PAYOS_BASE_URL = PayOSConstant.PAYOS_BASE_URL;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public PayOsService(String clientId, String apiKey, String checksumKey, String partnerCode) {
        this.clientId = clientId;
        this.apiKey = apiKey;
        this.checksumKey = checksumKey;
        this.partnerCode = partnerCode;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public PayOsService(String clientId, String apiKey, String checksumKey) {
        this(clientId, apiKey, checksumKey, null);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Charset", "UTF-8");
        headers.set("x-client-id", clientId);
        headers.set("x-api-key", apiKey);
        if (partnerCode != null) {
            headers.set("x-partner-code", partnerCode);
        }
        return headers;
    }

    public CheckoutResponseData createPaymentLink(PaymentData paymentData) throws Exception {
        String url = PAYOS_BASE_URL + "/v2/payment-requests";

        paymentData.setSignature(SignatureUtils.createSignatureOfPaymentRequest(paymentData, checksumKey));
        HttpEntity<PaymentData> requestEntity = new HttpEntity<>(paymentData, buildHeaders());

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpStatusCodeException e) {
            throw new Exception("Call api failed! HTTP status: " + e.getStatusCode(), e);
        }

        String responseData = responseEntity.getBody();
        PayOSResponse<CheckoutResponseData> res = objectMapper.readValue(responseData,
                new TypeReference<PayOSResponse<CheckoutResponseData>>() {});

        if (!"00".equals(res.getCode())) {
            throw new PayOSException(res.getCode(), res.getDesc());
        }

        String paymentLinkResSignature = SignatureUtils.createSignatureFromObj(res.getData(), checksumKey);
        if (!paymentLinkResSignature.equals(res.getSignature())) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("DATA_NOT_INTEGRITY"));
        }

        return res.getData();
    }

    public PaymentLinkData getPaymentLinkInformation(Long orderId) throws Exception {
        String url = PAYOS_BASE_URL + "/v2/payment-requests/" + orderId;
        HttpEntity<Void> requestEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpStatusCodeException e) {
            throw new Exception("Call api failed! HTTP status: " + e.getStatusCode(), e);
        }

        String responseData = responseEntity.getBody();
        PayOSResponse<PaymentLinkData> res = objectMapper.readValue(responseData,
                new TypeReference<PayOSResponse<PaymentLinkData>>() {});

        if (!"00".equals(res.getCode())) {
            throw new PayOSException(res.getCode(), res.getDesc());
        }

        String paymentLinkResSignature = SignatureUtils.createSignatureFromObj(res.getData(), checksumKey);
        if (!paymentLinkResSignature.equals(res.getSignature())) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("DATA_NOT_INTEGRITY"));
        }

        return res.getData();
    }

    public String confirmWebhook(String webhookUrl) throws Exception {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("INVALID_PARAMETER"));
        }

        String url = PAYOS_BASE_URL + "/confirm-webhook";

        String jsonPayload = "{\"webhookUrl\":\"" + webhookUrl + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, buildHeaders());

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PayOSException(PayOSConstant.ERROR_CODE.get("INTERNAL_SERVER_ERROR"),
                        PayOSConstant.ERROR_MESSAGE.get("INTERNAL_SERVER_ERROR"));
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new PayOSException(PayOSConstant.ERROR_CODE.get("UNAUTHORIZED"),
                        PayOSConstant.ERROR_MESSAGE.get("UNAUTHORIZED"));
            }
            throw new PayOSException(PayOSConstant.ERROR_CODE.get("INTERNAL_SERVER_ERROR"),
                    PayOSConstant.ERROR_MESSAGE.get("INTERNAL_SERVER_ERROR"));
        }

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return webhookUrl;
        } else {
            throw new PayOSException(PayOSConstant.ERROR_CODE.get("INTERNAL_SERVER_ERROR"),
                    PayOSConstant.ERROR_MESSAGE.get("INTERNAL_SERVER_ERROR"));
        }
    }

    public PaymentLinkData cancelPaymentLink(long orderId, String cancellationReason) throws Exception {
        String url = PAYOS_BASE_URL + "/v2/payment-requests/" + orderId + "/cancel";

        String jsonPayload = cancellationReason != null && !cancellationReason.isEmpty()
                ? "{\"cancellationReason\":\"" + cancellationReason + "\"}"
                : "{}";

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, buildHeaders());

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpStatusCodeException e) {
            throw new PayOSException(PayOSConstant.ERROR_CODE.get("INTERNAL_SERVER_ERROR"),
                    PayOSConstant.ERROR_MESSAGE.get("INTERNAL_SERVER_ERROR"));
        }

        String responseData = responseEntity.getBody();
        PayOSResponse<PaymentLinkData> res = objectMapper.readValue(responseData,
                new TypeReference<PayOSResponse<PaymentLinkData>>() {});

        if (!"00".equals(res.getCode())) {
            throw new PayOSException(res.getCode(), res.getDesc());
        }

        String paymentLinkResSignature = SignatureUtils.createSignatureFromObj(res.getData(), checksumKey);
        if (!paymentLinkResSignature.equals(res.getSignature())) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("DATA_NOT_INTEGRITY"));
        }

        return res.getData();
    }

    public WebhookData verifyPaymentWebhookData(Webhook webhookBody) throws Exception {
        WebhookData data = webhookBody.getData();
        String signature = webhookBody.getSignature();

        if (signature == null || signature.isEmpty()) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("NO_SIGNATURE"));
        }

        String signData = SignatureUtils.createSignatureFromObj(data, checksumKey);
        if (!signData.equals(signature)) {
            throw new Exception(PayOSConstant.ERROR_MESSAGE.get("DATA_NOT_INTEGRITY"));
        }
        return data;
    }
}
