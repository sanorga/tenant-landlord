package com.tea.landlordapp.web;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.domain.Role;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.MessageService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.service.SecurityService;
import com.tea.landlordapp.validation.PasswordChangeValidator;
import com.tea.landlordapp.validation.UserValidator;

@Controller
@RequestMapping("/user.htm")
public class UserForm extends AbstractDataController{

	   @Autowired
	   UserService userService;

	   @Autowired
	   UserValidator userValidator;
	   
	   @Autowired
	   SimpleDao simpleDao;
	   
	   @Autowired
	   MessageService messageService;

	   @Autowired
	   SecurityService securityService;

	   @Autowired
	   com.tea.landlordapp.validation.PasswordChangeValidator passwordChangeValidator;
	   
	  @Autowired
	   StandardPasswordEncoder standardPasswordEncoder;
	  
	 @RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId, 
			    Model model,
	            HttpServletRequest request) {
	      logger.debug("in GET method of user.htm with userId as.." + userId + "... and subscriberID as..." );
	      
	      final User loginUser = getAuthenticatedUser();
 
	      // check authorization
//	      if (!hasAnyAuthority(new String[]{"view.system.user","view.client.user"})) {
//	          return unAuthorized(request);
//	       }

//	      Subscriber subscriber;
//	      if (ObjectUtils.equals(subscriberId, null)) {
//	         subscriber = loginUser.getSubscriber();
//	      } else {
//	         subscriber = userService.findSubscriber(subscriberId);
//	      }

	      // create a new user, if passed user id is null
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(userId, null)) {
//	         if (!checkSubscriberAccess(subscriber, loginUser)) {
//	            subscriber = loginUser.getSubscriber();
//	         }
	         user = userService.setupNewUser(null);
//	         user = userService.getNewUser(subscriber);
//	         Role initialRole = userService.findRole(UserRole.ClientUser.getValue());
//	         user.setRole(initialRole);
	      } else {
	         user = simpleDao.find(User.class, userId);

	      }
  
	      setValuesInModel(model);
	      
	      // set data in the model
	      setPreviousActionMessage(request, model);
	      model.addAttribute("user", user);
	      logger.debug("leaving GET method of user.htm with userId as.." + userId + "... and subscriberID as..." );

	      return "user";
	   }
	   
	   
	   private void setValuesInModel(Model model) {
		    
		   Map<String,String> roleOptions;
		   Map<String,String> statusOptions;
		   Map<String,String> usStateListOptions;
//		   final User loginUser = getAuthenticatedUser();
		   
		   roleOptions = getRoleOptions();
		   statusOptions = getStatusOptions();
		   usStateListOptions = getUsStateListOptions();
		   model.addAttribute("userRoleOptions", roleOptions);
		   model.addAttribute("userStatusOptions", statusOptions);
		   model.addAttribute("usStateOptions", usStateListOptions);
		   
		        
	   }
	   
	   private Map<String,String> getStatusOptions(){
		   Map<String,String> statusOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();

		  
		    	  statusOptions = globals.getUserStatusOptions();
		    	 
		   
		   return statusOptions;
	   }	
	   
	   private Map<String,String> getUsStateListOptions(){
			   

			   Map<String,String> usStateListOptions = new TreeMap<String,String>();
			   final Globals globals = Globals.getInstance();

			   	 
			   usStateListOptions = globals.getUsStateListOptions();
		 
			   return usStateListOptions;
		}
		 
		private Map<String,String> getRoleOptions(){
			   Map<String,String> roleOptions = new TreeMap<String,String>();
			   final Globals globals = Globals.getInstance();

			   
			    	  roleOptions = globals.getClientRoleOptions();
			    	  
			   
			   return roleOptions;
		}		 
		 
	   @RequestMapping(method = RequestMethod.POST)
	   protected String processSubmit(@ModelAttribute("user") User user,
			   BindingResult result, HttpServletRequest request, Model model) {
	      logger.debug("inside POST method of changepwd.htm...");

	      User loginUser = getAuthenticatedUser();

	      // onCancel
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
	         return "redirect:/home.htm";
 	      }
	      
	      
	      userValidator.validate(user, result);
	      if (result.hasErrors()) {

	    	  setValuesInModel(model);
		      
		      // set data in the model
		      setPreviousActionMessage(request, model);
		      model.addAttribute("user", user);
	         return "user";
	      }

	      // role
	       final com.tea.landlordapp.domain.Role role = userService.findRole(user.getRole().getRole());

	      // save user
	      user.setRole(role);
	      String roleStr = user.getRole().getRole();
	      
	      if (!StringUtils.isBlank(user.getNewPassword() ) ){
	    	  		  
				      if (StringUtils.isBlank(user.getOldPassword()))
				         result.rejectValue("oldPassword", "error.invalid-oldpassword-blank", "Value required.");
				      else {
				         if (!securityService.checkPassword(loginUser, user.getOldPassword())) {
				            result.rejectValue("password", "error.invalid-old-password", "Value required.");
				         }
				         else {
						         passwordChangeValidator.validate(user, result);
							      if (result.hasErrors()) {
							    	  setValuesInModel(model);
								      
								      // set data in the model
								      setPreviousActionMessage(request, model);
								      model.addAttribute("user", user);
								      return "user";
							      }
				
							      if (StringUtils.isNotBlank(user.getNewPassword())) {
							    	  // persist user
								      securityService.updateYourPassword(user, user.getNewPassword(), loginUser.getPasswordHash());
							    	  setActionMessage(request, "confirm.user_save_success");
							    	  return "redirect:/home.htm";
							      }
				         }
				  }
	      }
	      user.setPasswordHash(loginUser.getPasswordHash());
	      User userUpd = simpleDao.merge(user);
//	      userService.saveYourUser(user);
//	      status.setComplete();
	      setActionMessage(request, "confirm.user_save_success");
	      // force logout
	      return "redirect:/home.htm";
	   }
	   	
}


