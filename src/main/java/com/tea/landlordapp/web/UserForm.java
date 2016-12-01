package com.tea.landlordapp.web;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.MessageService;
import com.tea.landlordapp.service.UserService;

@Controller
@RequestMapping("/user.htm")
public class UserForm extends AbstractDataController{

	   @Autowired
	   UserService userService;

//	   @Autowired
//	   UserValidator userValidator;
//	   
	   @Autowired
	   SimpleDao simpleDao;
	   
	   @Autowired
	   MessageService messageService;


	 @RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId, Model model,
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
	         user = userService.setupNewUser("USER");
//	         user = userService.getNewUser(subscriber);
//	         Role initialRole = userService.findRole(UserRole.ClientUser.getValue());
//	         user.setRole(initialRole);
	      } else {
	         user = simpleDao.find(User.class, userId);
//	         subscriber = user.getSubscriber();
//
//	         if (!checkSubscriberAccess(user.getSubscriber(), loginUser)) {
//	            return unAuthorized(request);
//	         }
	      }

//	      Set<Property> authProperties = user.getAuthorizedProperties();
//	      user.setAuthorizedProperties(authProperties);
	      
	      setValuesInModel(model);
//	      roleOptions = getRoleOptions(subscriber, loginUser);
//	      model.addAttribute("userRoleOptions", roleOptions);
//	      model.addAttribute("propertyList", userService.getPropertyList(subscriber.getId())); 
	      
	      // set data in the model
	      setPreviousActionMessage(request, model);
	      model.addAttribute("user", user);
	      logger.debug("leaving GET method of user.htm with userId as.." + userId + "... and subscriberID as..." );

	      return "user";
	   }
	   
//	   private Map<String,String> getRoleOptions(Subscriber subscriber, User loginUser){
//		   Map<String,String> roleOptions = new TreeMap<String,String>();
//		   final Globals globals = Globals.getInstance();
//
//		   char subType = subscriber.getSubscriberType();
//		      switch (subType){
//		      case 'C':
//		    	  roleOptions = globals.getClientUserRoleOptions();
//		    	  break;
//		      case 'P':
//		    	  roleOptions = globals.getSystemUserRoleOptions(UserRole.getEnum(loginUser.getPrimaryRoleCode()));
//		    	  break;
//		      }
//		   
//		   return roleOptions;
//	   }

	   private Map<String,String> getRoleOptions(Character type){
		   Map<String,String> roleOptions = new TreeMap<String,String>();
		   final Globals globals = Globals.getInstance();

		   
		      switch (type){
		      case 'C':
		    	  roleOptions = globals.getClientUserRoleOptions();
		    	  break;
		      case 'P':
//		    	  roleOptions = globals.getSystemUserRoleOptions(UserRole.getEnum(loginUser.getPrimaryRoleCode()));
		    	  break;
		      }
		   
		   return roleOptions;
	   }
	   
	   private void setValuesInModel(Model model) {
		    
		   Map<String,String> roleOptions;
//		   final User loginUser = getAuthenticatedUser();
		   
		   roleOptions = getRoleOptions('C');
		   model.addAttribute("userRoleOptions", roleOptions);
//		   model.addAttribute("propertyList", userService.getPropertyList(subscriber.getId())); 
		      
	   }
	
	
	
}
