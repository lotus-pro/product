package com.platform.config.util;

import com.auth0.jwt.JWTExpiredException;
import com.auth0.jwt.JWTVerifyException;
import com.platform.common.cache.Cache;
import com.platform.common.context.SpringContext;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    private TokenUtil() {
    }

    public static String createToken(String identity, String type, int expirySeconds) {
        Map<String, Object> map = new HashMap();
        map.put("iss", "Sunline");
        map.put("identity", identity);
        map.put("type", type);
        Instant now = Instant.now();
        long iatTime = now.toEpochMilli();
        Instant exp = now.plus((long) expirySeconds, ChronoUnit.SECONDS);
        long expTime = exp.toEpochMilli();
        map.put("iat", iatTime);
        map.put("jti", UUID.randomUUID().toString());
        map.put("exp", expTime);
        return JWTUtils.sign(map, expirySeconds);
    }

    public static String getUsernameFromToken(String token) throws JWTVerifyException {
        return getValueFromToken(token, "identity");
    }

    public static String getTypeFromToken(String token) throws JWTVerifyException {
        return getValueFromToken(token, "type");
    }

    public static String getValueFromToken(String token, String key) throws JWTVerifyException {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("argument key can not be null or empty");
        } else {
            Map<String, Object> map = JWTUtils.unsign(token);
            return MapUtils.getString(map, key);
        }
    }

    public static Map<String, Object> validJWT(String token) throws JWTVerifyException {
        String username = null;

        try {
            username = getUsernameFromToken(token);
        } catch (JWTExpiredException var4) {
            log.error("JWT token已经过期", var4);
            throw var4;
        } catch (Exception var5) {
            log.error("JWT token解析异常", var5);
            throw new JWTVerifyException();
        }

        if (StringUtils.isEmpty(username)) {
            log.error("JWT token解析异常，用户编码为空");
            throw new JWTVerifyException();
        } else {
            Cache cache = (Cache) SpringContext.getBean(Cache.class);
            Map<String, Object> userMap = cache.getMap("CACHE_REDIS_AUTH_" + username);
            if (null == userMap) {
                log.error("JWT token解析异常，用户{}未登陆或不存在", username);
                throw new JWTVerifyException();
            } else {
                return userMap;
            }
        }
    }
}
