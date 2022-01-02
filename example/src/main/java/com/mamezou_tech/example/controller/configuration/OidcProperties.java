package com.mamezou_tech.example.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oidc")
public class OidcProperties {

    private String issuerURL;

    private String baseUrl;

    public String getIssuerURL() {
        return issuerURL;
    }

    public void setIssuerURL(String issuerURL) {
        this.issuerURL = issuerURL;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
