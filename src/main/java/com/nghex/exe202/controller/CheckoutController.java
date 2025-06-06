package com.nghex.exe202.controller;

import com.nghex.exe202.dto.MemberShipDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.Date;
@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckoutController {

    private final PayOS payOS;

    @Value("${NgheX.baseUrl}")
    private String baseUrl;

    @RequestMapping(method = RequestMethod.POST, value = "/create-payment-link")
    public String checkout(@RequestBody MemberShipDto payload) throws Exception {
        try {
            final String productName = "Gói thành viên NhệX tháng 6";
            final String description = "Thanh toan goi thanh vien";
            final String returnUrl = baseUrl + "/seller/membershipSuccess";
            final String cancelUrl = baseUrl + "/seller/membershipSuccess";
            final int price = 3000;
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
            ItemData item = ItemData.builder().name(productName).quantity(1).price(payload.getPrice()).build();
            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(payload.getPrice()).description(description)
                    .returnUrl(returnUrl).cancelUrl(cancelUrl).item(item).build();
            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            return data.getCheckoutUrl();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
