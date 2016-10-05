package com.tea.landlordapp.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tea.landlordapp.service.helper.SSLHelper;

public class SSLInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	SSLHelper sslHelperService;

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {

		String url = arg0.getServletPath();
		
		// always allow healthcheck
		if (StringUtils.contains(url, "healthcheck"))
			return true;

		// ensure ssl connection
		if (sslHelperService.checkSSLAndRedirect(arg0, arg1)) return false;
		
		return true;
	}

}
