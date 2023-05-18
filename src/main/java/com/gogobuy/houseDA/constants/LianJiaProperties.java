package com.gogobuy.houseDA.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "lianjia")
@Data
@EnableConfigurationProperties
public class LianJiaProperties {


    private List<String> followQueryApi;

    private String followHouseWebApi;
    private String unfollowHouseWebApi;

    private String communityQueryWebApi;

    private String webCookie;

    private String houseDetailWebApi;

}
