package com.platform.common.util;

import com.platform.common.exception.CommonException;
import org.springframework.lang.Nullable;


public interface Assert {

    static void isNull(@Nullable Object object, String message, Object... params) {
        if (object == null) {
            throw new CommonException(I18NUtils.getMessage(message, params), new Object[0]);
        }
    }

    static void isNotNull(@Nullable Object object, String message, Object... params) {
        if (object != null) {
            throw new CommonException(I18NUtils.getMessage(message, params), new Object[0]);
        }
    }

    static void isTrue(boolean equals, String message, Object... params) {
        if (equals) {
            throw new CommonException(I18NUtils.getMessage(message, params), new Object[0]);
        }
    }

    static void isFalse(boolean equals, String message, Object... params) {
        if (!equals) {
            throw new CommonException(I18NUtils.getMessage(message, params), new Object[0]);
        }
    }
}
