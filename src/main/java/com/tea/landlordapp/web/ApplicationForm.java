package com.tea.landlordapp.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Applicant;
import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.Role;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.ApplicationService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.web.AbstractDataController;

@Controller
@RequestMapping("/application.htm")
@SessionAttributes(types = Application.class)
public class ApplicationForm extends AbstractDataController {

	   @Autowired
	   UserService userService;
	   
	   @Autowired
	   ApplicationService applicationService;

//	   @Autowired
//	   UserValidator userValidator;
//	   
	   @Autowired
	   SimpleDao simpleDao;
	   
//	   @Autowired
//	   MessageService messageService;

	   @RequestMapping(method = RequestMethod.POST)
	   public String processSubmit(@ModelAttribute Application application, BindingResult result, HttpServletRequest request, SessionStatus status, Model model) {
	      logger.debug("in POST method of application.htm with applicationId as.." + application.getId());


	      // get logged in user
	      final User loginUser = getAuthenticatedUser();
	      // onCancel
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//	         Integer sId = loginUser.getSubscriber().getId();
//	     status.setComplete();
	         return String.format("redirect:viewapplications.htm?");
	      }

//	      userValidator.validate(user, result);
//	      if (result.hasErrors()) {
	     setValuesInModel(model); 
	     model.addAttribute("application", application);
//	         model.addAttribute("userRoleOptions", getRoleOptions(user.getSubscriber(), loginUser));
//	         model.addAttribute("application", application);
//	         return "application";
//	      }

	       final Role role = userService.findRole(loginUser.getRole().getRole());

	      // save user
	      loginUser.setRole(role);
	      String roleStr = loginUser.getRole().getRole();
	     
//	      final Set<Property> authProperties = new HashSet<Property>();
//	final List<Integer> authPropertyIds = user.getAuthorizedPropertyIds();
//	if (authPropertyIds != null && authPropertyIds.size() > 0) {
//	for (final Integer ii : authPropertyIds) {
//	authProperties.add(simpleDao.find(Property.class, ii)); 
//	}
//	}
//	if (StringUtils.equals(roleStr, "PM") || StringUtils.equals(roleStr, "BD")){
//	user.setAuthorizedProperties(authProperties); 
//	} 
//	else {
//	user.setAuthorizedProperties(null);  
//	}
//	      userService.saveUser(user, loginUser);
	      applicationService.updateApplication(application, loginUser);
	      
//	      status.setComplete();
//	      if (sendReset){
//	     try {
//	messageService.sendPasswordResetEmail(user.getUsername());
//	WebUtils.setSessionAttribute(request, "message", "confirm.user_save_passw_reset_email_sent");
//	} catch (Exception e) {
//	// do nothing
//	}
//	      } else {
	     // set confirmation message
	     setActionMessage(request, "confirm.application_save_success");
//	      }
	      

	      
	      return "redirect:viewusers.htm?subscriberId=" + loginUser.getId();
	   
}

	   @RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "applicationId", required = false) Integer applicationId,
			   				   @RequestParam(value = "userId", required = false) Integer userId, 
			   					Model model,
	                            HttpServletRequest request) {
	      logger.debug("in GET method of application.htm with applicationId as.." + applicationId );
	      

	      final User loginUser = getAuthenticatedUser();

	      // check authorization
	      if (!hasAnyAuthority(new String[]{"view.my.applications"})) {
	          return unAuthorized(request);
	       }

	      Property property;
	      Application application;
	      Applicant applicant;
	      List<Applicant> applicants;
	      int numOfApplicants = 0;
	      

	      
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(applicationId, null)) {
	          return unAuthorized(request);

	      } else {
	         application = simpleDao.find(Application.class, applicationId);
	         if (ObjectUtils.equals(application, null)) {
	             
	                return unAuthorized(request);
	             
	          }
	         property = application.getProperty();
	         applicants = application.getApplicants();
	         numOfApplicants = applicants.size();
	         
	      }
	      if (application.getCreatedBy().getId() != loginUser.getId()) {
	    	  return unAuthorized(request);
	      }
	      
	      String creditRecommendationLabel = com.tea.landlordapp.enums.CreditRecommendation.getLabel(application.getCreditRecommendation()); 
	      setValuesInModel(model);

	      
	      // set data in the model
	      setPreviousActionMessage(request, model);
	      model.addAttribute("application", application);
	      model.addAttribute("user", loginUser);
	      model.addAttribute("applicants",applicants);
	      model.addAttribute("creditRecommendationLabel", creditRecommendationLabel);
	      logger.debug("leaving GET method of application.htm with userId as.." + loginUser.getId() );

	      return "application"; 
	   }
	   
	   private Map<String,String> getRoleOptions( User loginUser){
		  Map<String,String> roleOptions = new TreeMap<String,String>();
		  final Globals globals = Globals.getInstance();

//	  char subType = subscriber.getSubscriberType();
//	     switch (subType){
//	     case 'C':
//	     roleOptions = globals.getClientUserRoleOptions();
//	     break;
//	     case 'P':
//	     roleOptions = globals.getSystemUserRoleOptions(UserRole.getEnum(loginUser.getPrimaryRoleCode()));
//	     break;
//	     }
	  
		  return roleOptions;
	   }
	  
	   private void setValuesInModel(Model model) {
	   
		  Map<String,String> roleOptions;
		  final User loginUser = getAuthenticatedUser();
		  
		  roleOptions = getRoleOptions(loginUser);
//	  model.addAttribute("userRoleOptions", roleOptions);
//	  model.addAttribute("propertyList", userService.getPropertyList(subscriber.getId())); 
	     
	   }
}
