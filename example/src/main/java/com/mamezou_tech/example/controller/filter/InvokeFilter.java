package com.mamezou_tech.example.controller.filter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(InvokeFilter.class);

    private final String baseUrl;

    public InvokeFilter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        URL url = new URL(baseUrl + path);
        logger.info(url.toExternalForm());
        // TODO サイドカーパターンで使用する場合は、ここで、chain.doFilter() ではなく、HttpClient 等を使って、アプリケーションコンテナの API を使用する。
        chain.doFilter(request, response); 
    }
    
}
