package com.platform.common.config;

import com.platform.common.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class ProductLocaleResolver implements LocaleResolver {
    private static final Logger log = LoggerFactory.getLogger(ProductLocaleResolver.class);

    public ProductLocaleResolver() {
    }

    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        return RequestUtil.getLocale(httpServletRequest);
    }

    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
    }
}
