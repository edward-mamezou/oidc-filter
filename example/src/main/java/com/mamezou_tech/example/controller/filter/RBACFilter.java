package com.mamezou_tech.example.controller.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RBACFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RBACFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Map<String, Object> claims = (Map<String, Object>) request.getAttribute("authorizer.claims");
        String path = ((HttpServletRequest) request).getRequestURI();
        String role = (String) claims.get("custom:role");
        // TODO ここで path と role で RBAC の制御を行う
        logger.info(path + ": " + role);
        chain.doFilter(request, response);
    }
}
