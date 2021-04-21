package com.platform.core.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import java.io.IOException;
import java.nio.charset.Charset;

public class JwtFilter extends BasicAuthenticationInterceptor {

    public JwtFilter(String username, String password) {
        super(username, password);
    }

    public JwtFilter(String username, String password, Charset charset) {
        super(username, password, charset);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return super.intercept(request, body, execution);
    }
}
