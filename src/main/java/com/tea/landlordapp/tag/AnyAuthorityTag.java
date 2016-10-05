package com.tea.landlordapp.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.utility.SecurityUtils;

public class AnyAuthorityTag extends ConditionalTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863717844433601226L;
	String[] authorities;

	@Override
	protected boolean condition() throws JspTagException {

		if (ObjectUtils.equals(pageContext, null)) {
			throw new IllegalArgumentException("page context is null.");
		} else if (ObjectUtils.equals(pageContext.getSession(), null)) {
			throw new IllegalArgumentException("session is null.");
		}
		


	      
//	      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean res = false;
			for (String auth : authorities) {
//				boolean authorized = false;
//				for (GrantedAuthority a: authentication.getAuthorities()){
//					authorized = StringUtils.equalsIgnoreCase(a.getAuthority(),auth);
//					if (authorized) break;
//				}
				res = res || SecurityUtils.isAuthorized(auth);
				if (res)
					break;
			}
			return res;
		
		
		
		
		
		
		
		
		
		
		
		

//		final User user = (User) pageContext.getSession().getAttribute(
//				"loginUser");
//
//		boolean res = false;
//		for (String auth : authorities) {
//			res = res || user.hasRole(auth);
//			if (res)
//				break;
//		}
//		return res;
	}

	public void setCapability(String capability) {
		this.authorities = capability.split(",");
	}

}
