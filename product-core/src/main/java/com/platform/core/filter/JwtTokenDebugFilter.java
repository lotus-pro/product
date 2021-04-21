package com.platform.core.filter;

import com.platform.core.util.AuthenticationUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenDebugFilter extends OncePerRequestFilter {
    private final boolean debugEnabled = true;

    public JwtTokenDebugFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.isDebug(request)) {
            AuthenticationUtils.setAnonymous(request);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isDebug(HttpServletRequest request) {
        String authToken = request.getHeader("token");
        return this.debugEnabled && "test".equals(authToken);
    }
}
