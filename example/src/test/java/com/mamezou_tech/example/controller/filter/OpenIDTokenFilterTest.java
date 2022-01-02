package com.mamezou_tech.example.controller.filter;

import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OpenIDTokenFilterTest {

    private static Logger logger = LoggerFactory.getLogger(OpenIDTokenFilterTest.class);

    @Test
    public void test() throws Exception {
        String issuerurl = System.getenv("OIDC_ISSUERURL");
        OpenIDTokenFilter filter = new OpenIDTokenFilter(issuerurl);
        Map<String, KeyItem> keys = filter.openIDConnectGetKeys();
        Map<String, Algorithm> algorithms = filter.toAlgorithms(keys);
        logger.info(algorithms.toString());
        return;
    }
}
