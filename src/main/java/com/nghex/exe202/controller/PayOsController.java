package com.nghex.exe202.controller;

import com.nghex.exe202.dto.payos.*;
import com.nghex.exe202.service.PayOsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentOS")
public class PayOsController {
    /**
     * Client ID of the PayOS payment channel
     */
    private final String clientId = "5ad0fe44-36b6-4d6c-8881-0ae717737469";

    /**
     * API Key of the PayOS payment channel
     */
    private final String apiKey = "807c3996-9b25-4d4f-b21a-72ea6d736c2f";

    /**
     * Checksum Key of the PayOS payment channel
     */
    private final String checksumKey = "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657";

    @PostMapping("/payment-link")
    public ResponseEntity<?> createPaymentLink(@RequestBody PaymentData paymentData) {
        PayOsService payOsService = new PayOsService("5ad0fe44-36b6-4d6c-8881-0ae717737469",
                "807c3996-9b25-4d4f-b21a-72ea6d736c2f",
                "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657");
        try {
            CheckoutResponseData responseData = payOsService.createPaymentLink(paymentData);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payment-link/{orderId}")
    public ResponseEntity<?> getPaymentLinkInformation(@PathVariable Long orderId) {
        PayOsService payOsService = new PayOsService("5ad0fe44-36b6-4d6c-8881-0ae717737469",
                "807c3996-9b25-4d4f-b21a-72ea6d736c2f",
                "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657");
        try {
            PaymentLinkData paymentLinkData = payOsService.getPaymentLinkInformation(orderId);
            return ResponseEntity.ok(paymentLinkData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xác nhận webhook URL
    @PostMapping("/webhook/confirm")
    public ResponseEntity<?> confirmWebhook(@RequestParam String webhookUrl) {
        PayOsService payOsService = new PayOsService("5ad0fe44-36b6-4d6c-8881-0ae717737469",
                "807c3996-9b25-4d4f-b21a-72ea6d736c2f",
                "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657");
        try {
            String result = payOsService.confirmWebhook(webhookUrl);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Hủy payment link
    @PostMapping("/payment-link/{orderId}/cancel")
    public ResponseEntity<?> cancelPaymentLink(@PathVariable Long orderId,
                                               @RequestParam(required = false) String cancellationReason) {
        PayOsService payOsService = new PayOsService("5ad0fe44-36b6-4d6c-8881-0ae717737469",
                "807c3996-9b25-4d4f-b21a-72ea6d736c2f",
                "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657");
        try {
            PaymentLinkData canceledData = payOsService.cancelPaymentLink(orderId, cancellationReason);
            return ResponseEntity.ok(canceledData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xác thực dữ liệu webhook
    @PostMapping("/webhook/verify")
    public ResponseEntity<?> verifyPaymentWebhookData(@RequestBody Webhook webhookBody) {
        PayOsService payOsService = new PayOsService("5ad0fe44-36b6-4d6c-8881-0ae717737469",
                "807c3996-9b25-4d4f-b21a-72ea6d736c2f",
                "b89e427b1c27612e3a3e20ef31f14b3870b81753ba6aadd708dd47f19c19f657");
        try {
            WebhookData data = payOsService.verifyPaymentWebhookData(webhookBody);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
