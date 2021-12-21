package com.mamezou_tech.example.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oidc")
public class OidcProperties {

    private String region;

    private String poolid;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPoolid() {
        return poolid;
    }

    public void setPoolid(String poolid) {
        this.poolid = poolid;
    }
}
