package com.tea.landlordapp.web;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Role;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.SecurityService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.validation.UserValidator;

import com.tea.landlordapp.validation.PasswordChangeValidator;

@Controller
@RequestMapping("/newuser.htm")
public class NewUserForm {

	   @Autowired
	   UserService userService;
	   
	   @Autowired
	   UserValidator userValidator;
	   
	   @Autowired
	   SimpleDao simpleDao;
	   
	   @Autowired
	   PasswordChangeValidator passwordChangeValidator;
	   
	   @Autowired
	   SecurityService securityService;
	   
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId,
				@RequestParam(value = "type", required = false) String type,
			   					ModelMap model) {
	      logger.debug("inside GET method of newuser.htm...");
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(userId, null)) 
	         if (StringUtils.isBlank(type))
	        	 user = userService.setupNewUser(null);
	         else user = userService.setupNewUser(type);
	       else 
	         user = simpleDao.find(User.class, userId);
	      
	      setValuesInModel(model);
    	  model.addAttribute("user", user);
	      
    	  
    	  return "newuser";
    	
	   }
	
//	 private Map<String,String> getRoleOptions(){
//		   Map<String,String> roleOptions = new TreeMap<String,String>();
//		   final Globals globals = Globals.getInstance();
//
//		   
//		    	  roleOptions = globals.getClientRoleOptions();
//		    	  
//		   
//		   return roleOptions;
//	   }
	  
	 private Map<String,String> getUsStateListOptions(){
		   

		   Map<String,String> usStateListOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();

		   	 
		   usStateListOptions = globals.getUsStateListOptions();
	 
		   return usStateListOptions;
	   }
	 
	private void setValuesInModel(ModelMap model) {
		    
//		   Map<String,String> roleOptions;
		   Map<String,String> statusOptions;
		   Map<String,String> usStateListOptions;
//		   final User loginUser = getAuthenticatedUser();
		   
//		   roleOptions = getRoleOptions();
		   statusOptions = getStatusOptions();
		   usStateListOptions = getUsStateListOptions();
//		   model.addAttribute("userRoleOptions", roleOptions);
		   model.addAttribute("userStatusOptions", statusOptions);
		   model.addAttribute("usStateOptions", usStateListOptions);
		      
	   }
	   
	   private Map<String,String> getStatusOptions(){
		   Map<String,String> statusOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();
		  
		    	  statusOptions = globals.getUserStatusOptions();
		   
		   return statusOptions;
	   }
	   
	   @RequestMapping(method = RequestMethod.POST)
	   public String processSubmit(@ModelAttribute User user, BindingResult result, HttpServletRequest request, SessionStatus status, ModelMap model) {
	      logger.debug("in POST method of user.htm with userId as.." + user.getId());

	      
	      // onCancel
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//	         Integer sId = user.getSubscriber().getId();
	    	  status.setComplete();
	         return String.format("redirect:login.htm?");
	      }

//	     final User loginUser = getAuthenticatedUser();
	      final User loginUser = userService.findNullUser();
//	      boolean sendReset = false;
//	      if (user.isNew()){
//	    	 final String newPassword = RandomStringUtils.randomAlphanumeric(8);
//	    	 user.setPassword(newPassword);
//	    	 sendReset = true;
//	    	 
//	      }
	     
	      if (StringUtils.isBlank(user.getNewPassword()) && StringUtils.isEmpty(user.getNewPassword())) result.rejectValue("newPassword", "error.invalid-newpassword-blank", "Value required.");

	      passwordChangeValidator.validate(user, result);
	      if (result.hasErrors()) {
	    	  setValuesInModel(model); 
//	         model.addAttribute("userRoleOptions", getRoleOptions(user.getSubscriber(), loginUser));
	         model.addAttribute("user", user);
	         return "newuser";
	      }
	      user.setPassword(user.getNewPassword());
	      
	      userValidator.validate(user, result);
	      if (result.hasErrors()) {
	    	  setValuesInModel(model); 
//	         model.addAttribute("userRoleOptions", getRoleOptions(user.getSubscriber(), loginUser));
	         model.addAttribute("user", user);
	         return "newuser";
	      }

	      // persist user
//	      user.setStatus(Globals.ACTIVE);
//	      if (StringUtils.isNotBlank(user.getNewPassword())) {
//	    	  securityService.updatePassword(user, user.getNewPassword());
//	      }

	       final Role role = userService.findRole(user.getRole().getRole());

	      // save user
	      user.setRole(role);
	      String roleStr = user.getRole().getRole();
	    	   
	      userService.saveUser(user, loginUser);
	      status.setComplete();

	      

	      // force logout
//	      return "redirect:/logout.htm?message=PC";
	      return "redirect:login";
	   }
}
