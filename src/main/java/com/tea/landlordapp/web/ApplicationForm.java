package com.tea.landlordapp.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
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
import com.tea.landlordapp.service.InviteService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.utility.ServiceUtils;
import com.tea.landlordapp.utility.StringHelper;
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
	   
	   @Autowired
	   InviteService inviteService;
	   
//	   @Autowired
//	   MessageService messageService;

	   @RequestMapping(method = RequestMethod.POST)
	   public String processSubmit(@ModelAttribute Application application, 
			 BindingResult result, HttpServletRequest request, SessionStatus status, Model model) {
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
	      

	      
	      return "redirect:viewapplications.htm?userId=" + loginUser.getId();
	   
}

	   @RequestMapping(method = RequestMethod.GET)
	   public String setupForm(@RequestParam(value = "applicationId", required = false) Integer applicationId,
			   				   @RequestParam(value = "userId", required = false) Integer userId, 
			   					Model model,
	                            HttpServletRequest request) throws Exception {
	      logger.debug("in GET method of application.htm with applicationId as.." + applicationId );
	      

	      final User loginUser = getAuthenticatedUser();
	      String creditRecommendationLabel = null;
	      // check authorization
	      if (!hasAnyAuthority(new String[]{"view.my.applications"})) {
	          return unAuthorized(request);
	       }

	      Property property;
	      Application application;
	      Applicant applicant;
	      List<Applicant> applicants;
	      boolean noHit = false;
	      
	      int numOfApplicants = 0, applicationExtId = 0;
	      

	      
	      User user;
	      Map<String,String> roleOptions;
	      if (ObjectUtils.equals(applicationId, null)) {
	          return unAuthorized(request);

	      } else {
	         application = simpleDao.find(Application.class, applicationId);
	         if (ObjectUtils.equals(application, null)) {
	             
	                return unAuthorized(request);
	             
	          }
	         applicationExtId = application.getApplicationExtId();
	         property = application.getProperty();
	         applicants = application.getApplicants();
	         numOfApplicants = applicants.size();
	         
	         
	      }
	      if (application.getCreatedBy().getId() != loginUser.getId()) {
	    	  return unAuthorized(request);
	      }
	      
	      if (application.isCanRequestReport() == true) {

	      // Call getReport
	    	  
	    	  
	    		Map<String, String> reportMap = inviteService.getReports(applicationExtId);
				if (reportMap == null) 
					logger.info("Notification received and no reports obtained for application  {}", applicationExtId);
				else logger.info("Notification received and reports obtained for application  {}", applicationExtId);
			
		  // Transform report from XML to HTML

					String transReport = null;
	    		  	String creditReport = reportMap.get("creditReport");
	    		  	if (StringUtils.isBlank(creditReport))	{
	    		  		logger.debug("no report found...");
	    		  	}
	    		  	else {		 
	    		  		try {
				    		  	String xslFilePath = StringHelper.concatWithSingleSlash(request.getSession().getServletContext().getRealPath("/"), Globals.XSL_PATH);
				    		    xslFilePath = StringHelper.concatWithSingleSlash(xslFilePath, "TUCredit.xslt");
				    		             	
			//	    		    final String xslFilePath = request.getSession().getServletContext().getRealPath("/" + Globals.XSL_PATH) + "/" + report.getService().getXslUrl();
				    		    transReport = ServiceUtils.transformXmlToHtml(creditReport, xslFilePath);
				    		    StringBuilder sb = new StringBuilder();
				    		   
//				    		    sb.append("<pre xmlns:t=");
//	    		                
//	    		                sb.append("\"");
//	    		                sb.append("http:");
//	    		                sb.append("//xml.equifax.com/XMLSchema\">");
//	    		                sb.append("\r\n\r\n</pre>\r\n");
//	    		                
//	    		                ////xml.equifax.com//XMLSchema/">")
//	    		                String equifaxError = sb.toString();
//	    		                if (StringUtils.isBlank(transReport)) {
//	    		                   logger.error("Transformer returned an empty result.. Setting the result to \"No Hits\".. ");
//	    		                   transReport = "<br><br><strong>No Hit</strong>";
//	    		                   noHit = true;
//	    		                }
//	    		                if (StringUtils.equals(transReport,equifaxError)) {
//	    		                    logger.error("Transformer returned an equifax error ");
//	    		                    transReport = "<br><br><strong>-----Error Requesting Report------</strong>";
//	    		                 }
	    		                
//	    		                report.setTransformedReport(transReport);
	    		             } catch (final Exception e) {
	    		                e.printStackTrace();

	    		                logger.error("Unable to do xml/xsl conversion for...");
	    		                logger.error("XML:\n" + creditReport);
	    		                logger.error("XSL File:\n" + "TUCredit.xslt");
	    		                // report.setTransformedReport(report.getReport());
//	    		                report.setTransformedReport("<br><br><strong>Please contact Administrator</strong>");
	    		                transReport = "<br><br><strong>Please contact Administrator</strong>";
	    		             }
	    		  	}
	    	  
	     	      
	      //getCreditRecommendation
	      
	      
	      }
	      else creditRecommendationLabel = com.tea.landlordapp.enums.CreditRecommendation.UNKNOWN.getLabel();
	      
//	      String creditRecommendationLabel = com.tea.landlordapp.enums.CreditRecommendation.getLabel(application.getCreditRecommendation()); 
	      
	      setValuesInModel(model);

	      
	      // set data in the model
	      setPreviousActionMessage(request, model);
	      model.addAttribute("application", application);
	      model.addAttribute("newStatus", "");
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
