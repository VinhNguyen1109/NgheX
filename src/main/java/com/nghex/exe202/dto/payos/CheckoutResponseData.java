package com.nghex.exe202.dto.payos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutResponseData {
    @NonNull
    private String bin;
    @NonNull
    private String accountNumber;
    @NonNull
    private String accountName;
    @NonNull
    private Integer amount;
    @NonNull
    private String description;
    @NonNull
    private Long orderCode;
    @NonNull
    private String currency;
    @NonNull
    private String paymentLinkId;
    @NonNull
    private String status;
    @NonNull
    private String checkoutUrl;
    @NonNull
    private String qrCode;
}