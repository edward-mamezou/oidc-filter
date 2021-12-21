package com.mamezou_tech.example.controller.configuration;

import com.mamezou_tech.example.controller.filter.OpenIDTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OidcProperties.class)
public class FilterConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

    @Autowired
    private OidcProperties properties;

    @Bean
    public FilterRegistrationBean<OpenIDTokenFilter> openIDTokenFilter() {
        logger.info("created OpenIDTokenFilter(" + properties.getRegion() + "," + properties.getPoolid() + ")");
        OpenIDTokenFilter filter = new OpenIDTokenFilter(properties.getRegion(), properties.getPoolid());
        return new FilterRegistrationBean<>(filter);
    }
}
