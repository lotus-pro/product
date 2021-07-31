package com.platform.core.util;

import cn.hutool.core.map.MapUtil;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(JWTUtils.class);
    private static final long serialVersionUID = 7640141043291365235L;

//    private static final String SECRET = "!@#$%^12345@$%^&*ertyuERTYUIfghjVBNGH";
    private static final String SECRET = "!@#$%12345@$%&*zengzheng";
    private static final String BODY = "body";

    public JWTUtils() {
    }

    public static String sign(Map<String, Object> object, int expirySeconds) {
        JWTSigner signer = new JWTSigner(SECRET);
        Map<String, Object> claims = new HashMap();
        claims.put("body", object);
        JWTSigner.Options options = new JWTSigner.Options();
        options.setExpirySeconds(expirySeconds);
        return signer.sign(claims, options);
    }

    public static Map<String, Object> unsign(String token) throws JWTVerifyException {
        JWTVerifier verifier = new JWTVerifier(SECRET);
        Map claims = null;

        try {
            claims = verifier.verify(token);
        } catch (NoSuchAlgorithmException var3) {
            log.error("NoSuchAlgorithmException", var3);
        } catch (InvalidKeyException var4) {
            log.error("InvalidKeyException", var4);
        } catch (IOException var5) {
            log.error("IOException", var5);
        } catch (SignatureException var6) {
            log.error("SignatureException", var6);
        }

        return MapUtil.getAny(claims, "body");
    }
}
