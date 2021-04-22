package com.platform.core.filter;

import com.platform.common.util.ResponseUtil;
import com.platform.core.entity.ProductRole;
import com.platform.core.entity.UserDetailInfo;
import com.platform.core.util.AuthenticationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class FunctionPermissionFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(FunctionPermissionFilter.class);
    //Url字符串匹配
    private final AntPathMatcher urlPathMatcher = new AntPathMatcher("/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            filterChain.doFilter(request, response);
        } else if (!this.hasPermission(request)) {
            ResponseUtil.filterResponseResultError(request, response, 401, "product.error.00005");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean hasPermission(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (this.isPassUrl(requestURI)) {
            return true;
        } else {
            String principal = AuthenticationUtils.getCurrentUserCode();
            if (StringUtils.isEmpty(principal)) {
                log.warn("{}无权限，用户编码为空", requestURI);
                return false;
            } else {
                String userAnonymous = "USER_ANONYMOUS";
                if (userAnonymous.equals(principal)) {
                    return true;
                } else {
                    UserDetailInfo productUser = AuthenticationUtils.getUserFromCache(principal);
                    if (null == productUser) {
                        log.warn("{}无权限或忽略过滤权限，{}用户信息为空", requestURI, principal);
                        return false;
                    } else {
                        ProductRole defaultRole = AuthenticationUtils.getDefaultRole(productUser);
                        if (null == defaultRole) {
                            log.warn("用户{}没有默认角色", productUser.getUserCode());
                            return false;
                        }
                        return true;
//                        else {
//                            return RolePermissionUtil.isSuperAdmin(defaultRole) ? true : this.functionPermission(request, defaultRole);
//                        }
                    }
                }
            }
        }
    }

    private boolean isPassUrl(String url) {
        String[] var3 = new String[]{"/*/system/**"};
        for (int var = 0; var < var3.length; ++var) {
            String passUrlPPattern = var3[var];
            if (this.urlPathMatcher.match(passUrlPPattern, url)) {
                return true;
            }
        }
        return false;
    }

}
