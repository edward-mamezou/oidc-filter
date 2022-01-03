package com.mamezou_tech.example.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oidc")
public class OidcProperties {

    private String issuerURL;

    public String getIssuerURL() {
        return issuerURL;
    }

    public void setIssuerURL(String issuerURL) {
        this.issuerURL = issuerURL;
    }
}
