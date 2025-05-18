package com.nghex.exe202.model;

import com.nghex.exe202.util.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentLinkReferenceId;
    private PaymentStatus status;
}
