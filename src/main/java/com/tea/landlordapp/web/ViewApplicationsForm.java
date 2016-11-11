package com.tea.landlordapp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.constant.Globals;
//import com.tea.landlordapp.domain.Subscriber;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.ApplicationGridItem;
import com.tea.landlordapp.enums.ApplicationState;
import com.tea.landlordapp.dto.ApplicationGridItem;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.service.ApplicationService;

@Controller
@RequestMapping("/viewapplications.htm")
public class ViewApplicationsForm extends AbstractDataController{

	  @Autowired
	   private ApplicationService applicationService;

	   @RequestMapping(method = RequestMethod.POST)
	   public String processFormSubmission(@RequestParam(value = "userId", required = false) Integer userId, HttpServletRequest request) {
	      logger.debug("in POST method of viewapplications.htm...");

	      // get logged in user using the id stored in the session
	      final User user = getAuthenticatedUser();

//	      Subscriber subscriber;
//	      if (ObjectUtils.equals(subscriberId, null)) {
//	         subscriber = user.getSubscriber();
//	      } else {
//	         subscriber = userService.findSubscriber(subscriberId);
//	      }


	      // on new user
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_INPROGRESS)) {
		         return "redirect:viewapplications.htm?userId=" + user.getId() + "&state=" + ApplicationState.INPROGRESS.getCode();
		      }
	      
	      // on new user
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_COMPLETED)) {
	         return "redirect:viewapplications.htm?userId=" + user.getId() + "&state="  + ApplicationState.COMPLETED.getCode();
	      }
	      
	      // on new user
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_DECLINED)) {
	         return "redirect:viewapplications.htm?userId=" + user.getId() + "&state="  + ApplicationState.DECLINED.getCode();
	      }
	      
	      // on new user
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_APPROVED)) {
	         return "redirect:viewapplications.htm?userId=" + user.getId() + "&state="  + ApplicationState.APPROVED.getCode();
	      }
	      
	      // on new user
	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_ALLSTATUS)) {
	         return "redirect:viewapplications.htm?userId=" + user.getId() ;
	      }
	      
	      // default
	      return onCancel1(user);
	   }

	   @RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "userId", required = false) Integer userId, 
			   @RequestParam(value = "state", required = false) Character state, 
			   @RequestParam(value = "status", required = false) String status, 
			   @RequestParam(value = "otherStatus", required = false) String otherStatus, 
			   					Model model, HttpServletRequest request) {
	      logger.debug("in GET method of viewapplications.htm for userId..." + userId);
	      

	      final User user = getAuthenticatedUser();

	      // check authorization
	      if (!hasAnyAuthority(new String[]{"view.my.applications"})) {
	          return unAuthorized(request);
	       }

	      // get logged in user using the id stored in the session and the operating subscriber
//	      Subscriber subscriber;
//	      if (ObjectUtils.equals(subscriberId, null)) {
//	         subscriber = user.getSubscriber();
//	      } else {
//	         subscriber = userService.findSubscriber(subscriberId);
//	      }

//	      // check user access to the subscriber
//	      if (!checkSubscriberAccess(subscriber, user)) {
//	         subscriber = user.getSubscriber();
//	      }
	     
	      final List<ApplicationGridItem> userApplications = applicationService.findApplicationGridList(user, state);
	     
	      // set the user in the model
	      model.addAttribute("user", user);
	      model.addAttribute("userApplications", userApplications);
	      setPreviousActionMessage(request, model);
	      return "viewapplications";
	   }
}
