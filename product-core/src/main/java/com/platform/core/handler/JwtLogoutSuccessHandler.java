package com.platform.core.handler;

import com.platform.common.util.ResponseUtil;
import com.platform.core.util.AuthenticationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		String usercode = (String) request.getAttribute("usercode");
		AuthenticationUtils.removeUserMap(usercode);
		response.setHeader("token", null);

		ResponseUtil.response(response, authentication);

	}
}
