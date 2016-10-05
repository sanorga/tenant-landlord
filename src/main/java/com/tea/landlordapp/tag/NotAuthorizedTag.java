package com.tea.landlordapp.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.apache.commons.lang.ObjectUtils;
import com.tea.landlordapp.utility.SecurityUtils;

public class NotAuthorizedTag extends ConditionalTagSupport {


	   /**
	 * 
	 */
	private static final long serialVersionUID = 4836967690810451996L;
	private String capability;

	   @Override
	   protected boolean condition() throws JspTagException {

	      if (ObjectUtils.equals(pageContext, null)) {
	         throw new IllegalArgumentException("page context is null.");
	      } else if (ObjectUtils.equals(pageContext.getSession(), null)) {
	         throw new IllegalArgumentException("session is null.");
	      }
	      
	      return !SecurityUtils.isAuthorized(capability);
	      

//	      final User user = (User) pageContext.getSession().getAttribute("loginUser");
//	      return !user.hasRole(capability);
	   }

	   public void setCapability(String capability) {
	      this.capability = capability;
	   }
	}