package com.platform.core.wrapper;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public ParameterRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body.getBytes());
        return new ServletInputStream() {
            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener listener) {
            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getParameter(String name) {
        return (String) super.getAttribute(name);
    }

    public String getBody() {
        return this.body;
    }
}
