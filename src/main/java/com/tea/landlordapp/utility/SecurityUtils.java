package com.tea.landlordapp.utility;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tea.landlordapp.domain.User;

public class SecurityUtils {
	
	public static boolean isAuthorized(String role){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      
	      return hasAuthority(role, auth);
	}

	private static boolean hasAuthority(String role, Authentication auth) {
		for (GrantedAuthority a: auth.getAuthorities()){
	    	  if(StringUtils.equalsIgnoreCase(a.getAuthority(),role)){
	    		  return true;
	    	  }
	      }
	      return false;
	}
	
	public static boolean isAuthorized(Authentication authentication, String authority){
		return (authentication != null && hasAuthority(authority, authentication));
	}
}
