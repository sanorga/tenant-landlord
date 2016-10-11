package com.tea.landlordapp.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MultipleInputAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;
	@Override
	protected String obtainUsername (HttpServletRequest request) {
		String username = request.getParameter(getUsernameParameter());
//		String username = request.getParameter("j_username");
		return username;
	}
	
	@Override
	 public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String acctType = obtainAcctType(request);
       
        	
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        if (StringUtils.equals(acctType,"1"))	{
        	return this.getAuthenticationManager().authenticate(authRequest);
        }
        else {
        return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

	protected String obtainAcctType (HttpServletRequest request) {
		String acctType = request.getParameter("socialService");

		return acctType;
	}
	
}
