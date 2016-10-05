package com.tea.landlordapp.service.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("sslHelperService")
public class SSLHelper {
	
	@Value("${TEA_REQUIRE_SSL}")
	String requireSSL;

	public boolean checkSSLAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		boolean didRedirect = false;
		
		boolean isSsl = isRequestSecure(request);
		boolean ensureSSL = Boolean.getBoolean(requireSSL);
//		boolean ensureSSL = requireSSL != null ? requireSSL: true;
		if (ensureSSL && !isSsl) {
			buildSslRedirect(request, response);
			didRedirect = true;
		}
		
		return didRedirect;
	}
	
	private boolean isRequestSecure(HttpServletRequest request) {
		String reqUrl = request.getRequestURL().toString();
		boolean isSsl = reqUrl.toLowerCase().startsWith("https://");
		if (request.getHeader("x-forwarded-proto") != null) {
			String proto = request.getHeader("x-forwarded-proto");
			isSsl = (StringUtils.equalsIgnoreCase("https", proto));
		}

		return isSsl;
	}
	
	private void buildSslRedirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String reqUrl = request.getRequestURL().toString()
				.replace("http://", "https://");
		redirect(request, response, reqUrl);
	}
	
	private void redirect(HttpServletRequest request,
			HttpServletResponse response, String reqUrl) throws IOException {
		String query = request.getQueryString();
		if (StringUtils.isNotBlank(query))
			reqUrl = reqUrl + "?" + query;
		response.sendRedirect(reqUrl);
	}

}
