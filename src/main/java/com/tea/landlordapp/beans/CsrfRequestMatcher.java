package com.tea.landlordapp.beans;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class CsrfRequestMatcher implements RequestMatcher {
	
	private AntPathRequestMatcher dsCallbackMatcher = new AntPathRequestMatcher("/rcvSigningMsg.htm**", "POST");
	private AntPathRequestMatcher duoLoginMatcher = new AntPathRequestMatcher("/duologin", "POST");
	private AntPathRequestMatcher apiMatcher = new AntPathRequestMatcher("/api/**", null);
	private AntPathRequestMatcher dataMatcher = new AntPathRequestMatcher("/data/**", "POST");
	private AntPathRequestMatcher notificationMatcher = new AntPathRequestMatcher("/notificationMgmt.htm", "POST");
	private AntPathRequestMatcher getMatcher = new AntPathRequestMatcher("/**", "GET");
	private AntPathRequestMatcher headMatcher = new AntPathRequestMatcher("/**", "HEAD");

	@Override
	public boolean matches(HttpServletRequest arg0) {
		if (getMatcher.matches(arg0)) return false;
//		if (traceMatcher.matches(arg0)) return false;
		if (headMatcher.matches(arg0)) return false;
//		if (optionsMatcher.matches(arg0)) return false;
		
		if (dsCallbackMatcher.matches(arg0)){
			return false;
		}
		
		if (apiMatcher.matches(arg0)){
			return false;
		}
		
//		if (dataMatcher.matches(arg0)){
//			return false;
//		}
		
		if (duoLoginMatcher.matches(arg0)){
			return false;
		}
		
		if (notificationMatcher.matches(arg0)){
			return false;
		}
		
		return true;
	}

}
