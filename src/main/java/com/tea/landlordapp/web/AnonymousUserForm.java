package com.tea.landlordapp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Applicant;
import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.IntegerStringKVDto;
//import com.tea.landlordapp.enums.ApplicationPaymentMethod;
import com.tea.landlordapp.enums.UserRole;
import com.tea.landlordapp.repository.ApplicantDao;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.service.InviteService;
import com.tea.landlordapp.service.ListService;
//import com.tea.landlordapp.service.MessageService;
//import com.tea.landlordapp.service.ServiceService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.utility.ServiceUtils;
import com.tea.landlordapp.validation.AnonymousUserValidator;
import com.tea.landlordapp.service.MessageService;
import com.tea.landlordapp.repository.ApplicationDao;

@Controller
@RequestMapping("/anonymousUser.htm")
@SessionAttributes(types = AnonymousUser.class)
public class AnonymousUserForm extends AbstractDataController {

   @Autowired
   UserService userService;
   
//   @Autowired
//   ServiceService serviceService;

   @Autowired
   MessageService messageService;

   @Autowired
   AnonymousUserValidator anonymousUserValidator;
   
   @Autowired
   ListService listService;
   
   @Autowired
   InviteService inviteService;
   
   @Autowired
   UserDao userDao;

   @Autowired
   ApplicationDao applicationDao;

   
   @RequestMapping(method = RequestMethod.POST)
   public String processSubmit(@ModelAttribute AnonymousUser anonymousUser, BindingResult result, 
		   						HttpServletRequest request, SessionStatus status, Model model) throws Exception {
	   
      logger.debug("in POST method of anonymousUser.htm with id as.." + anonymousUser.getId());
      
      String propertyExtIdStr = null, organizationIdStr = null, propertyIdentifier = null,
    		  organizationName = null, applicationExtIdStr = null;
      Integer pId = 0;
      Integer propertyExtId = -1, organizationId = 0, applicationExtId = 0;
	  String applicantEmail=null, coapplicantEmail=null;
	  Map<String, String> propertyResultMap = new HashMap<String,String>();
      Map<String, String> applicationResultMap = new HashMap<String,String>();
      
      // login User
      final User user = getAuthenticatedUser();
      
      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
          status.setComplete();
          return onCancel1(user);
      }
      
      if (ObjectUtils.equals(anonymousUser.getProperty(), null)) {
          result.reject("property.id", "Please select the property");
      }

       // Validation
      anonymousUserValidator.validate(anonymousUser, result);
      if (result.hasErrors()) {
          setAnonymousUserValues(model, user.getId());
          return "anonymousUser";
      }
      
      if (anonymousUser.getCoappEmailId()!= null){
    	  anonymousUser.setIsCoapplicantAvailable(Globals.YES);
      }
      
      Property property = null;
      Property p = anonymousUser.getProperty();
      
      pId = p.getId();
      if (pId != null) 
    	  property = userService.findProperty(pId);
      if (property != null) {
    	   logger.debug("property already exists in Tenant Evaluation Database: property id#" + pId);
      }
      else {
    	  logger.debug("property does not exists in Tenant Evaluation Database: property id#" );
    	      property = new Property();
    	   	  property.setApartmentNo(p.getApartmentNo());
    	      property.setCity(p.getCity());
    	      property.setName(p.getName());
    	      property.setStreet(p.getStreet());
    	      property.setUserId(user.getId());
    	      property.setZipcode(p.getZipcode());
    	      property.setState(p.getState());
    	      if ((anonymousUser.getProperty().getId() == null) && (anonymousUser.getProperty().getName() != null)) {
    	    	  if (anonymousUser.getSaveNewAddress() == 'Y')
	        		  property.setFutureUse(true);
    	      }
    	      else
    	      if ((anonymousUser.getProperty().getId() == -1) && (anonymousUser.getProperty().getName() != null)){
 	    	      if (anonymousUser.getSaveNewAddress() == 'Y')
	        		  property.setFutureUse(true);
    	      }
    	      property = userService.saveNewProperty(property, user);
    	      property.setId(property.getId());
      }
      anonymousUser.setProperty(property);

      logger.debug("anonymous user .." + anonymousUser.getId());

      final boolean flag = true;
      
	  propertyExtId = property.getPropertyExtId();

	  //-------------------------------------------
      // Submit Property to Report Vendor if Needed
      //-------------------------------------------
	  if (propertyExtId == 0 || propertyExtId == null) {
		      
		  	  
		      try {
				propertyResultMap = inviteService.submitProperty(anonymousUser);
				
		      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }
		  
		      
		      if (propertyResultMap == null) {
					logger.debug("process error");
				    setActionMessage(request, "confirm.unsuccessful_invitation_to_apply");
				    return "redirect:home.htm";
				}
		      
		      propertyExtIdStr = propertyResultMap.get("propertyExtIdStr");
		      propertyIdentifier = propertyResultMap.get("propertyIdentifier");
		      organizationIdStr = propertyResultMap.get("organizationIdStr");
		      organizationName = propertyResultMap.get("organizationName");
		    
		
				try {
					  propertyExtId = Integer.valueOf(propertyExtIdStr);
				      property.setPropertyExtId(propertyExtId);
				      property.setPropertyIdentifier(propertyIdentifier);
				      organizationId = Integer.valueOf(organizationIdStr);
				      property.setOrganizationId(organizationId);
				      property.setOrganizationName(organizationName);
				      
				      userDao.saveProperty(property);
				      
				     				      
				} catch (Exception e) {
					e.printStackTrace();
					
				}
				
			 anonymousUser.setProperty(property);
//			 final AnonymousUser anonymousUserUpd = userService.saveAnonymousUser(au, user);
			 logger.debug("anonymous user saved successfully.." + anonymousUser.getId());
	  }

     //-------------------------------------
     // Submit Application to Report Vendor
     //-------------------------------------
 
	  boolean isNotTest = true;
	  if (isNotTest){
    	
		  try {
			applicationResultMap = inviteService.invite(anonymousUser);
			
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	 
	      if (applicationResultMap == null) {
				logger.debug("process error");
			    setActionMessage(request, "confirm.unsuccessful_invitation_to_apply");
			    return "redirect:home.htm";
			}
	      
	      applicationExtIdStr = applicationResultMap.get("applicationExtIdStr");
	      applicantEmail = applicationResultMap.get("applicantEmail");
	      coapplicantEmail = applicationResultMap.get("coapplicantEmail");
	    
	  }
	  else {
	      applicationExtIdStr = "38001";
		  applicantEmail = "sanorga@tenantevaluation.com";
	      coapplicantEmail = "tenant.developer@gmail.com";
	  }
	
	  try {
			  //save application 
			  Application application = new Application();
			  applicationExtId = Integer.valueOf(applicationExtIdStr);
		      application.setApplicationExtId(applicationExtId);
		      application.setRentalAmount(anonymousUser.getRentalAmount());
		      application.setRentalDeposit(anonymousUser.getRentalDeposit());
		      application.setLeaseTermMonths(12);    
		      application.setLandlordPays(true);    
		      application.setProperty(property);
		      application.setApartmentNo(property.getApartmentNo());
		      application.setCreditRecommendation('6'); 
		      application.setSelectedBundle('2');
		      if ( application.getApplicants().size() == 2) {
					application.setCoApplicantAvailable(Globals.YES);
			  } else {
					application.setCoApplicantAvailable(Globals.NO);
			  }
		      Application app = userService.saveNewApplication(application, user);
    	      application.setId(application.getId());
		      
    	      //save applicants
    	      Applicant applicant = new Applicant();
    	      applicant.setApplication(app);
    	      applicant.setEmailAddress(applicantEmail);
    	      applicant.setApplicantType("1");
    	      applicationDao.saveApplicant(applicant);
    	      if (coapplicantEmail != null) {
    	    	  applicant = new Applicant();
	    	      applicant.setApplication(app);
	    	      applicant.setEmailAddress(coapplicantEmail);
	    	      applicant.setApplicantType("2");
	    	      applicationDao.saveApplicant(applicant);
    	      }
      	    	  
		      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setActionMessage(request, "confirm.unsuccessful_invitation_to_apply");

		    return "redirect:home.htm";
		}
		 
		final AnonymousUser anonymousUserUpd = userService.saveAnonymousUser(anonymousUser, user);

	      // send email
		try {
	         
	      messageService.sendAnonymousUserMails(anonymousUserUpd, request);
	        
		} catch (final Exception ex) {
	         // set confirmation message
	         setActionMessage(request, "confirm.mail_send_fail");
	         return "redirect:home.htm";
		}
		setActionMessage(request, "confirm.successful_invitation_to_apply");

		return "redirect:home.htm";
   }
   

   private void setAnonymousUserValues(Model model, int userId) {

      final Map<Integer, String> properties = new TreeMap<Integer, String>();
      

      List<IntegerStringKVDto> list = listService.findPropertyLookupList(userId);
      if (list.size()> 0)
    	  properties.put(-1, "Select One");

      for (final IntegerStringKVDto s : list) {
            properties.put(s.getKey(), StringEscapeUtils.escapeJavaScript(s.getValue()));
         }


      logger.debug("in Get method of anonymousUser.htm with Properties size" + properties.size());
      model.addAttribute("properties", ServiceUtils.getSortedMap(properties));
		final Map <Character, String> yNOptions = Globals.getInstance()
				.getYNOptions();
		model.addAttribute("yNOptions", yNOptions);
   }

   
   @RequestMapping(method = RequestMethod.GET)
   public String setupForm(@RequestParam(value = "propertyId", required = false) Integer propertyId, Model model, HttpServletRequest request) {
      logger.debug("in GET method of anonymoususer.htm with a propertyID as..." + propertyId);
 
      final User user = getAuthenticatedUser();
      int userId = user.getId();

      // set model data
      setPreviousActionMessage(request, model);
      setAnonymousUserValues(model, userId);

      final AnonymousUser au = new AnonymousUser();
      au.setPrice((double) 0);

      au.setProperty(new Property());

      au.setReference(ServiceUtils.getRandomString());
      model.addAttribute("anonymousUser", au);
      return "anonymousUser";
   }
}