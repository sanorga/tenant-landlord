package com.tea.landlordapp.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.enums.UserRole;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.SecurityService;
// import com.tea.landlordapp.utility.UtilityMethods;

public class AbstractDataController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	protected SimpleDao basicDao;
	
	@Autowired
	SecurityService secSvc;

//	protected boolean checkSubscriberAccess(Subscriber subscriber, User user) {
//		final boolean matchOne = user.getSubscriber().getId()
//				.equals(subscriber.getId());
//		final boolean matchEither = matchOne
//				|| user.getSubscriber().getId()
//						.equals(subscriber.getParent().getId());
//
//		if (user.hasRole(UserRole.SystemAdmin.getCode())
//				|| (user.hasRole(UserRole.PartnerAdmin.getCode()) && matchEither)
//				|| (user.hasRole(UserRole.Investigator.getCode()) && matchEither)
//				|| (user.hasRole(UserRole.CustomerServiceRep.getCode()) && matchEither)
//				|| (user.hasRole(UserRole.ExternalCustomerServiceRep.getCode()) && matchEither)
//				|| (user.hasRole(UserRole.Compliance.getCode()) && matchEither)
//				|| (user.hasRole(UserRole.ClientAdmin.getCode()) && matchOne)
//				|| (user.hasRole(UserRole.ClientUser.getCode()) && matchOne)) {
//			return true;
//		}
//
//		return false;
//	}
	
	protected User getAuthenticatedUser(){
		return secSvc.getAuthenticatedUser();
	}
	
	protected TeaUserDetails getUserDetails(){
		return secSvc.getUserDetails();
	}

	protected void clearActionMessage(HttpServletRequest request) {
		request.getSession().removeAttribute("message");
		return;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		final DateFormat df = new SimpleDateFormat(Globals.DATE_FORMAT);
		final CustomDateEditor editor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	protected boolean isAuthorized(HttpServletRequest request, String capability) {

		final User user = getAuthenticatedUser();
		if (ObjectUtils.equals(user, null)) {
			logger.debug("User is not logged in");
			return false;
		}
		return user.hasRole(capability);
	}

//	protected void logError(String msg) {
//		logger.error(String.format("ERROR %s - %s", UtilityMethods.nowString(),
//					msg));
//		
//	}
//
//	protected void logInfo(String msg) {
//		logger.debug(String.format("INFO %s = %s", UtilityMethods.nowString(),
//					msg));
//		
//	}
//
//	protected void logWarning(String msg) {
//		logger.warn(String.format("WARN %s - %s", UtilityMethods.nowString(),
//					msg));
//		
//	}

	protected String onCancel1(User user) {
		return "redirect:home.htm";
	}

	protected String redirectHome() {
		return "redirect:home.htm";
	}

	protected void setActionMessage(HttpServletRequest request, String message) {
		WebUtils.setSessionAttribute(request, "message", message);
		return;
	}

	protected void setLoginActionMessage(HttpServletRequest request, String message) {
		WebUtils.setSessionAttribute(request, "loginMessage", message);
		return;
	}

	@ModelAttribute
	public void setGlobals(ModelMap model) {
		model.addAttribute("globals", Globals.getInstance());
	}

	protected void setPreviousActionMessage(HttpServletRequest request,
			Model model) {

		final String message = (String) WebUtils.getSessionAttribute(request,
				"message");
		if (!StringUtils.isBlank(message)) {
			model.addAttribute("message", message);
			request.getSession().removeAttribute("message");
		}

		return;
	}

	protected String unAuthorized(HttpServletRequest request) {
		setActionMessage(request, "confirm.unauthorized_access");
		return "redirect:home.htm";
	}

	protected String unAuthorizedURLAccess(HttpServletRequest request) {
		setActionMessage(request, "invalid.url");
		return "redirect:viewapplications.htm";
	}

//	protected boolean hasAnyAuthority(HttpServletRequest request,
//			String[] authorities) {
//		final User user = getAuthenticatedUser();
//		if (ObjectUtils.equals(user, null)) {
//			logger.warn("User is not logged in");
//			return false;
//		}
//		return user.hasAnyAuthority(authorities);
//	}

	protected boolean hasAnyAuthority(String[] authorities) {
		if (authorities == null || authorities.length == 0) return true;
		
		for (String string : authorities) {
			if (isAuthorized(string)){
				return true;
			}
		}
		return false;
	}

//	protected boolean hasAllAuthorities(HttpServletRequest request,
//			String[] authorities) {
//		final User user = getAuthenticatedUser();
//		if (ObjectUtils.equals(user, null)) {
//			logger.warn("User is not logged in");
//			return false;
//		}
//		return user.hasAllAuthorities(authorities);
//	}

	protected boolean hasAllAuthorities(String[] authorities) {
		if (authorities == null || authorities.length == 0) return true;
		
		for (String string : authorities) {
			if (!isAuthorized(string)){
				return false;
			}
		}
		return true;
}
	
	protected boolean isAuthorized(String authority){
		try {
			for (GrantedAuthority auth: SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
				if (StringUtils.equalsIgnoreCase(authority, auth.getAuthority())) return true;
			}
		} catch (Exception e) {
			// Do nothing 
		}
		
		return false;
	}
	   
	   @ModelAttribute("myPrimaryRole")
	   public String myPrimaryRole(Authentication authentication){
		   User user = secSvc.getAuthenticatedUser(authentication);
		   if (user == null) return "";
		   return user.getRole().getRole();
	   }
	   
	   @ModelAttribute("isPartnerUser")
	   public boolean isPartnerUser(Authentication authentication){
		   return (authentication != null) && ((TeaUserDetails)authentication.getPrincipal()).isPartnerUser();
	   }
	   
	   @ModelAttribute("isClientUser")
	   public boolean isClientUser(){
		   Authentication authentication = getAuthentication();
		   return (authentication != null) && ((TeaUserDetails)authentication.getPrincipal()).isClientUser();
	   }
	   
//	   @ModelAttribute("isClientUser")
//	   public boolean isClientUser(Authentication authentication){
//		   return (authentication != null) && ((TeaUserDetails)authentication.getPrincipal()).isClientUser();
//	   }
	   
	   protected Authentication getAuthentication(){
		   return SecurityContextHolder.getContext().getAuthentication();
	   }
	   
	   @ModelAttribute("isSystemAdminUser")
	   public boolean isSystemAdminUser(Authentication authentication){
		   return (authentication != null) && ((TeaUserDetails)authentication.getPrincipal()).isSystemAdminUser();
	   }
	   
	   @ModelAttribute("isAccountNotExpired")
	   public boolean isAccountNotExpired(Authentication authentication){
		   return (authentication != null) && ((TeaUserDetails)authentication.getPrincipal()).isAccountNonExpired();
	   }
	   
	   @ModelAttribute("loginUser")
	   public User loginUser(Authentication authentication){
		   return secSvc.getAuthenticatedUser(authentication);
	   }

}
