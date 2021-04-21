//package com.platform.core.filter;
//
//import com.auth0.jwt.JWTExpiredException;
//import com.auth0.jwt.JWTVerifyException;
//import com.platform.common.enums.StatusEnum;
//import com.platform.common.util.ResponseUtil;
//import com.platform.core.entity.UserDetailInfo;
//import com.platform.core.util.AuthenticationUtils;
//import com.platform.core.util.TokenUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationTokenFilter.class);
//    private final boolean offlineUserEnable;
//
//    public JwtAuthorizationTokenFilter(boolean offlineUserEnable) {
//        this.offlineUserEnable = offlineUserEnable;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if (null != SecurityContextHolder.getContext().getAuthentication()) {
//            chain.doFilter(request, response);
//        } else {
//            String authToken = request.getHeader("token");
//            if (StringUtils.isEmpty(authToken)) {
//                chain.doFilter(request, response);
//            } else {
//                Map userMap;
//                try {
//                    userMap = TokenUtil.validJWT(authToken);
//                } catch (JWTExpiredException var9) {
//                    ResponseUtil.filterResponse(request, response, StatusEnum.JWT_EXPIRE.getCode(), "product.error.00002");
//                    return;
//                } catch (JWTVerifyException e) {
//                    ResponseUtil.filterResponse(request, response, StatusEnum.JWT_ERROR.getCode(), "product.error.00001");
//                    return;
//                }
//
//                UserDetailInfo productUser = (UserDetailInfo) userMap.get("userInfo");
//                String userCode = productUser.getUserCode();
//                if (this.offlineUserEnable) {
//                    String cacheAccessToken = (String) userMap.get("accessToken");
//                    if (StringUtils.isNotBlank(cacheAccessToken) && !StringUtils.equals(authToken, cacheAccessToken)) {
//                        log.error("JWT token 已被覆盖,用户：{}", userCode);
//                        ResponseUtil.filterResponse(request, response, StatusEnum.OFFLINE_USER.getCode(), "jraf.error.0020");
//                        return;
//                    }
//                }
//
//                if (userCode != null) {
//                    AuthenticationUtils.setAuthentication(request, userCode);
//                    chain.doFilter(request, response);
//                }
//            }
//
//        }
//    }
//}
