package com.tea.landlordapp.web;

import java.util.HashMap;
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
import com.tea.landlordapp.dto.RenterDto;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.SecurityService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.validation.UserValidator;

import com.tea.landlordapp.validation.PasswordChangeValidator;
import com.tea.landlordapp.validation.RenterDtoValidator;
import com.tea.landlordapp.validation.RenterEmailValidator;

@Controller
// @RequestMapping("/newrenteruser.htm")
public class NewRenterUserForm extends AbstractDataController{

	   @Autowired
	   UserService userService;
	   
	   @Autowired
	   RenterEmailValidator renterEmailValidator;
	   
	   @Autowired
	   RenterDtoValidator renterDtoValidator;
	   
	   @Autowired
	   SimpleDao simpleDao;
	   
	   @Autowired
	   PasswordChangeValidator passwordChangeValidator;
	   
	   @Autowired
	   SecurityService securityService;
	   
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "/newrenteruser.htm", method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId,
			   					ModelMap model) {
	      logger.debug("inside GET method of newuser.htm...");
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(userId, null)) 
	         
	        user = userService.setupNewUser("TE");
	       else 
	         user = simpleDao.find(User.class, userId);
	      
	      setValuesInModel(model);
    	  model.addAttribute("user", user);
	      
    	  return "newrenteruser";
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

	   @RequestMapping(value = "/newrenteruser.htm", method = RequestMethod.POST)
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
	         return "newrenteruser";
	      }
	      user.setPassword(user.getNewPassword());
	      
	      renterEmailValidator.validate(user, result);
	      if (result.hasErrors()) {
	    	  setValuesInModel(model); 
//	         model.addAttribute("userRoleOptions", getRoleOptions(user.getSubscriber(), loginUser));
	         model.addAttribute("user", user);
	         return "newrenteruser";
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
	    	   
	      User newUser = userService.saveAndReturnUser(user, loginUser);
	      status.setComplete();
	      Integer userId = newUser.getId();
    	  	  
	      if (userId == null){
	      	status.setComplete();
	        return String.format("redirect:login.htm?");
	      }
	      // force logout
//	      return "redirect:/logout.htm?message=PC";
	      return "redirect:newrenterinfo.htm?userId=" + userId;
	   }

		@RequestMapping(value = "/newrenterinfo.htm", method = RequestMethod.GET)
		   public String setupNewRenterInfoForm(@RequestParam(value = "userId", required = false) Integer userId,
				   					ModelMap model) {
			  
			  
		      logger.debug("inside GET method of newrenterinfo.htm...");
		      User user;
		      Map<String,String> roleOptions;
		      if (ObjectUtils.equals(userId, null)) 
		         
		        user = userService.setupNewUser("TE");
		       else 
		         user = simpleDao.find(User.class, userId);
		      
		      RenterDto renterDto = new RenterDto(user);
		      setValuesInModel(model);
	    	  model.addAttribute("renterDto", renterDto);
		      
	    	  return "newrenterinfo";
		   }
		
		@RequestMapping(value = "/newrenterinfo.htm", method = RequestMethod.POST)
		   public String processNewRenterInfoForm (@ModelAttribute RenterDto renterDto, BindingResult result, HttpServletRequest request, SessionStatus status, ModelMap model) {
			  
		      logger.debug("inside POST method of newrenterinfo.htm...");
		      
		      //
		      
		      // onCancel
		      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//		         Integer sId = user.getSubscriber().getId();
		    	  status.setComplete();
		         return String.format("redirect:login.htm?");
		      }

//		     final User loginUser = getAuthenticatedUser();
		     final User loginUser = userService.findNullUser();
		     

		      renterDtoValidator.validate(renterDto, result);
		      if (result.hasErrors()) {
		    	  setValuesInModel(model); 

		         return "newrenterinfo";
		      }
		       
		      User user;
			  int userId = renterDto.getUserId();
			  if (ObjectUtils.equals(userId, null)) 
			    	 return String.format("redirect:login.htm?");
			  else 
			         user = simpleDao.find(User.class, userId);
			  
			  if (user == null) {
				  return String.format("redirect:login.htm?");
			  }
			  
			  user.setHasPersonalInfo(true);
			  user.setHasPaymentInfo(true);
			  
			  //create renter user in Smartmove database
			  
			  //-------------------------------------------
		      // Submit Renter to Report Vendor if Needed
		      //-------------------------------------------
			  Map<String, String> renterResultMap = new HashMap<String,String>();
			  
				      try {
						renterResultMap = inviteService.submitRenter(user);
						
				      } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				      }
				  
				      
				      if (renterResultMap == null) {
							logger.debug("process error");
						    setActionMessage(request, "confirm.unsuccessful_renter_creation");
						    return "redirect:home.htm";
						}
				      
//				      propertyExtIdStr = propertyResultMap.get("propertyExtIdStr");
				     
				    
			  
			  
			  
			  //create user in Tenant database
		      userService.updateUser(user, renterDto, loginUser);
   		     
		      status.setComplete();

		      return String.format("redirect:login.htm?");
		}
}
