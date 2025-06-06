package com.nghex.exe202.controller;

import com.nghex.exe202.entity.*;
import com.nghex.exe202.repository.CartItemRepository;
import com.nghex.exe202.repository.CartRepository;
import com.nghex.exe202.response.ApiResponse;
import com.nghex.exe202.response.PaymentLinkResponse;
import com.nghex.exe202.service.*;
import com.nghex.exe202.util.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;

import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentController {
    private final PayOS payOS;

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, String> payload) {
        try {
            String webhookUrl = payload.get("webhookUrl");
            var data = payOS.confirmWebhook(webhookUrl);
            return ResponseEntity.ok(Map.of("message", "Xác nhận thành công", "data", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
