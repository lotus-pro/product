package com.platform.config.util;

import com.platform.common.cache.Cache;
import com.platform.common.context.SpringContext;
import com.platform.config.entity.ProductRole;
import com.platform.config.entity.UserDetailInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class AuthenticationUtils {

    private AuthenticationUtils() {
    }

    public static UserDetailInfo getCurrentUser() {
        String userCode = getCurrentUserCode();
        return getUserFromCache(userCode);
    }

    public static String getCurrentUserCode() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            return null;
        } else {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                return user.getUsername();
            } else {
                return principal.toString();
            }
        }
    }

    public static UserDetailInfo getUserFromCache(String userCode) {
        if (StringUtils.isEmpty(userCode)) {
            return null;
        } else {
            Map<String, Object> userMap = getUserMap(userCode);
            Object userObj = MapUtils.getMap(userMap, "userInfo");
            return null == userObj ? null : (UserDetailInfo) userObj;
        }
    }

    public static Map<String, Object> getUserMap(String userCode) {
        if (StringUtils.isEmpty(userCode)) {
            return null;
        } else {
            Cache cache = (Cache) SpringContext.getBean(Cache.class);
            return cache.getMap("CACHE_REDIS_AUTH_" + userCode);
        }
    }

    public static void removeUserMap(String userCode) {
        if (!StringUtils.isEmpty(userCode)) {
            Cache cache = (Cache) SpringContext.getBean(Cache.class);
            cache.del(new String[]{"CACHE_REDIS_AUTH_" + userCode});
        }
    }

    public static Map<String, Object> getCurrentUserMap() {
        String userCode = getCurrentUserCode();
        return StringUtils.isEmpty(userCode) ? null : getUserMap(userCode);
    }

    public static void setUserMap(String userCode, Map<String, Object> userMap) {
        if (null != userMap) {
            if (null == userMap.get("userInfo")) {
                throw new IllegalArgumentException("map key name 'userInfo' can not be null");
            } else {
                Cache cache = (Cache) SpringContext.getBean(Cache.class);
                long accessTokenExpireTimestamp = MapUtils.getLong(userMap, "access_token_expire_timestamp");
                cache.setMap("CACHE_REDIS_AUTH_" + userCode, userMap, accessTokenExpireTimestamp);
            }
        }
    }

    public static void setUserCache(UserDetailInfo user) {
        Map<String, Object> userMap = getUserMap(user.getUserCode());
        if (null != userMap) {
            userMap.put("userInfo", user);
            setUserMap(user.getUserCode(), userMap);
        }
    }

    public static void setAuthentication(HttpServletRequest request, String username) {
        List<GrantedAuthority> gas = new ArrayList();
        gas.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, (Object) null, gas);
        authentication.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void setAnonymous(HttpServletRequest request) {
        setAuthentication(request, "USER_ANONYMOUS");
    }

    public static ProductRole getDefaultRole(UserDetailInfo pteUser) {
        return getRole(pteUser, pteUser.getDefaultRole());
    }

    public static ProductRole getRole(UserDetailInfo pteUser, String roleCode) {
        if (null != pteUser && !StringUtils.isEmpty(roleCode)) {
            List<ProductRole> roles = pteUser.getRoles();
            if (CollectionUtils.isNotEmpty(roles)) {
                Iterator var3 = roles.iterator();

                while (var3.hasNext()) {
                    ProductRole role = (ProductRole) var3.next();
                    if (roleCode.equals(role.getRoleCode())) {
                        return role;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public static ProductRole getDefaultRole() {
        UserDetailInfo productUser = getCurrentUser();
        return productUser != null ? getDefaultRole(productUser) : null;
    }
}
