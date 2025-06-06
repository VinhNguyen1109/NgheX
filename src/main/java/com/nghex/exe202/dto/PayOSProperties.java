package com.nghex.exe202.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payos")
@Data
public class PayOSProperties {
    private String baseUrl;
    private String clientId;
    private String apiKey;
    private String checkSumKey;
}