package com.nghex.exe202.configuration;

import com.nghex.exe202.dto.PayOSProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayOSConfig {

    private final PayOSProperties payOSProperties;

    @Bean
     PayOS payOS() {
        return new PayOS(payOSProperties.getClientId(),
                payOSProperties.getApiKey(),
                payOSProperties.getCheckSumKey());
    }

}
