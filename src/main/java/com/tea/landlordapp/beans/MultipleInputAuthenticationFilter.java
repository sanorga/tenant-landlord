package com.tea.landlordapp.beans;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MultipleInputAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Override
	protected String obtainUsername (HttpServletRequest request) {
		String username = request.getParameter(getUsernameParameter());
		return username;
	}


}
