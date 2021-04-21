package com.platform.common.util;

import com.platform.common.config.ProductMessageSource;
import com.platform.common.context.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-21 16:26
 */
public class I18NUtils {

    private I18NUtils() {
    }

    public static String getMessage(String code, @Nullable Object... args) {
        return getMessageOrDefault(code, code, (Locale)null, args);
    }

    public static String getMessageOrDefault(String code, String defaultValue, @Nullable Object... args) {
        return getMessageOrDefault(code, defaultValue, (Locale)null, args);
    }

    public static String getMessage(String code, Locale locale, @Nullable Object... args) {
        return getMessageOrDefault(code, code, locale, args);
    }

    public static String getMessageOrDefault(String code, String defaultValue, Locale locale, @Nullable Object... args) {
        if (StringUtils.isBlank(code)) {
            return null;
        } else {
            MessageSource messageSource = (MessageSource) SpringContext.getBean(ProductMessageSource.class);
            Locale tmpLocale = locale == null ? LocaleContextHolder.getLocale() : locale;
            return messageSource.getMessage(code, args, defaultValue, tmpLocale);
        }
    }

    /** @deprecated */
    @Deprecated
    public static String getMessage(String code, @Nullable Object[] args, Locale locale) {
        if (StringUtils.isBlank(code)) {
            return null;
        } else {
            MessageSource messageSource = (MessageSource) SpringContext.getBean(MessageSource.class);
            Locale tmpLocale = locale == null ? LocaleContextHolder.getLocale() : locale;
            return messageSource.getMessage(code, args, code, tmpLocale);
        }
    }
}
