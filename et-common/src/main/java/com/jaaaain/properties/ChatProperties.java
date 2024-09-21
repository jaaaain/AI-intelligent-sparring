package com.jaaaain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {
    private String proxyHost;
    private String proxyPort;
    private String openaiKey;
    private String model;
    private Double temperature;
}
