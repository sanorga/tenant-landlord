package com.tea.landlordapp.web;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.duosecurity.duoweb.DuoParams;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Role;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.enums.UserRole;
import com.tea.landlordapp.remote.AdminOpsService;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.service.SecurityService;
import com.tea.landlordapp.service.TeaUserDetailsService;
import com.tea.landlordapp.service.UserService;

@Controller
public class CustomLoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AdminOpsService adminOpsService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TeaUserDetailsService teaUserDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String showLogin(@RequestParam(value="message", required=false) String message,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="reset", required=false) Boolean reset,
			Model model, HttpServletRequest request){
		if (StringUtils.isNotBlank(message)) {
			if (StringUtils.equalsIgnoreCase("confirm.mail_send_success", message))
				model.addAttribute("message", "Password reset mail sent successfully");
			else if (StringUtils.equalsIgnoreCase(message, "IC"))
				model.addAttribute("message", "Invalid username or password");
			else if (StringUtils.equalsIgnoreCase(message, "PC"))
				model.addAttribute("message", "Password change was successful");
			else if (StringUtils.equalsIgnoreCase(message, "confirm.mail_send_fail"))
				model.addAttribute("message", "Unable to send reset email. Contact Customer Service");
			else if (StringUtils.equalsIgnoreCase(message, "LO"))
				model.addAttribute("message", "Logout was successful");
		}

		
		if (reset != null){
			model.addAttribute("reset", reset);
		}
		
		if (email != null){
			model.addAttribute("reset", reset);
		}
		
	      User user;
	      Map<String,String> roleOptions;
	      user = userService.setupUser();
	      model.addAttribute("user", user);   
		return "sslogin";
	}

	@RequestMapping(value = "/googlelogin.htm", method = RequestMethod.GET)
	private String showGoogleLogin(@RequestParam(value="message", required=false) String message,
			@RequestParam(value="reset", required=false) Boolean reset,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="idToken", required=false)String idToken,			
			Model model, @ModelAttribute User user, HttpServletRequest request){
		if (StringUtils.isNotBlank(message)) {
			if (StringUtils.equalsIgnoreCase("confirm.mail_send_success", message))
				model.addAttribute("message", "Password reset mail sent successfully");
			else if (StringUtils.equalsIgnoreCase(message, "IC"))
				model.addAttribute("message", "Invalid username or password");
			else if (StringUtils.equalsIgnoreCase(message, "PC"))
				model.addAttribute("message", "Password change was successful");
			else if (StringUtils.equalsIgnoreCase(message, "confirm.mail_send_fail"))
				model.addAttribute("message", "Unable to send reset email. Contact Customer Service");
			else if (StringUtils.equalsIgnoreCase(message, "LO"))
				model.addAttribute("message", "Logout was successful");
		}

		if (reset != null){
			model.addAttribute("reset", reset);
		}
		String username = null;
//		TeaUserDetails uDets = securityService.getUserDetails();
//		if (uDets != null) { 
//			username = uDets.getUsername();
//		}
		
		User googleAcctUser = userDao.findUserByUsername(email);
		
		
//		UserDetails userDetails = teaUserDetailsService.loadUserByGoogleAcct(email);
//		User loginUser = securityService.getAuthenticatedUser();
//		if (userDetails != null) {
//		
//			username = userDetails.getUsername();
//		}
		if (googleAcctUser != null) {
			username = googleAcctUser.getUsername();
			user = googleAcctUser;

		}
		else {
		   
//		     boolean sendReset = false;
//		     if (user.isNew()){
		    	 final String newPassword = "unused";
		    	 user.setPassword(newPassword);
		    	 user.setFirstName(name);
		    	 Role initialRole = userService.findRole(UserRole.Landlord.getCode());
		         user.setRole(initialRole);
//		         userService.saveUser(user, user);
//		    	 sendReset = true;
//		 		UserDetails userDetails = teaUserDetailsService.loadUserByGoogleAcct(email);
//		 		if (userDetails != null) {
//		 			
//		 				username = userDetails.getUsername();
//		 		}
		     
			
		}

	      model.addAttribute("user", user);   
		return "redirect:home.htm";
	}
	
	@RequestMapping(value = "/googlelogin.htm", method = RequestMethod.POST)
	private String doGoogleLogin(@RequestParam(value = "sig_response") String sigResponse,
			@RequestParam(value="email", required=false) String email){
		TeaUserDetails uDets = securityService.getUserDetails();
		String username = uDets.getUsername();
		
		boolean isValid = adminOpsService.verifyDuoLogin(username, sigResponse);
		
		if (isValid){
			adminOpsService.updateCurrentAuthorities(true);
			return "redirect:home.htm";
		} else {
			return "redirect:logout.htm";
		}
	}


	
	@RequestMapping(value = "/duologin", method = RequestMethod.GET)
	private String showDuoLogin(@RequestParam(value="message", required=false) String message, Model model, HttpServletRequest request){
		TeaUserDetails uDets = securityService.getUserDetails();
		String username = uDets.getUsername();
		
		String sigRequest = adminOpsService.getDuoRequest(username);

		model.addAttribute("sig_request",sigRequest);
		model.addAttribute("duoHost",DuoParams.host);
		return "duologin";
	}

	@RequestMapping(value = "/duologin", method = RequestMethod.POST)
	private String doMFALogin(@RequestParam(value = "sig_response") String sigResponse){
		TeaUserDetails uDets = securityService.getUserDetails();
		String username = uDets.getUsername();
		
		boolean isValid = adminOpsService.verifyDuoLogin(username, sigResponse);
		
		if (isValid){
			adminOpsService.updateCurrentAuthorities(true);
			return "redirect:home.htm";
		} else {
			return "redirect:logout.htm";
		}
	}


	
	   private Map<String,String> getRoleOptions(){
		   Map<String,String> roleOptions = new TreeMap<String,String>();
		   final com.tea.landlordapp.constant.Globals globals = com.tea.landlordapp.constant.Globals.getInstance();

//		   char subType = subscriber.getSubscriberType();
//		      switch (subType){
//		      case 'C':
		    	  roleOptions = globals.getClientUserRoleOptions();
//		    	  break;
//		      case 'P':
//		    	  roleOptions = globals.getSystemUserRoleOptions(UserRole.getEnum(loginUser.getPrimaryRoleCode()));
//		    	  break;
//		      }
		   
		   return roleOptions;
	   }
	
}
