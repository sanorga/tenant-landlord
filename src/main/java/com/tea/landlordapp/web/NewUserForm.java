package com.tea.landlordapp.web;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.UserService;

@Controller
@RequestMapping("/newuser.htm")
public class NewUserForm {

	   @Autowired
	   UserService userService;
	   
	   @Autowired
	   SimpleDao simpleDao;
	   
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId, ModelMap model) {
	      logger.debug("inside GET method of newuser.htm...");
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(userId, null)) 
	         user = userService.setupNewUser("USER");
	       else 
	         user = simpleDao.find(User.class, userId);
	      
	      setValuesInModel(model);
    	  model.addAttribute("user", user);
	      
	      return "newuser";
	   }
	
	 private Map<String,String> getRoleOptions(){
		   Map<String,String> roleOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();

		   
		    	  roleOptions = globals.getClientRoleOptions();
		    	  
		   
		   return roleOptions;
	   }
	  
	 private Map<String,String> getUsStateListOptions(){
		   

		   Map<String,String> usStateListOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();

		   	 
		   usStateListOptions = globals.getUsStateListOptions();
	 
		   return usStateListOptions;
	   }
	 
	private void setValuesInModel(ModelMap model) {
		    
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
}
