package com.gogobuy.houseDA.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lianjia")
@Data
@EnableConfigurationProperties
public class LianJiaProperties {
    private String appCookie;
}
