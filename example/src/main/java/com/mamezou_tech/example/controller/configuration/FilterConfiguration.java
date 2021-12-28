package com.mamezou_tech.example.controller.configuration;

import com.mamezou_tech.example.controller.filter.InvokeFilter;
import com.mamezou_tech.example.controller.filter.OpenIDTokenFilter;
import com.mamezou_tech.example.controller.filter.RBACFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        FilterRegistrationBean<OpenIDTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OpenIDTokenFilter(properties.getRegion(), properties.getPoolid()));
        registrationBean.setOrder(1);
        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<RBACFilter> rbacFilter() {
        logger.info("created RBACFilter()");
        FilterRegistrationBean<RBACFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RBACFilter());
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<InvokeFilter> invokeFilter() {
        logger.info("created InvokeFilter()");
        FilterRegistrationBean<InvokeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new InvokeFilter(properties.getBaseUrl()));
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
