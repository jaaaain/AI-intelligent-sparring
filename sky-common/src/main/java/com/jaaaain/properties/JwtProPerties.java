package com.jaaaain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtProPerties {
    private String secretKey;
    private Long ttl;
    private String tokenName;
}
