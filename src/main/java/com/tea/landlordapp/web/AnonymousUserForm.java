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
   public String processSubmit(@ModelAttribute AnonymousUser anonymousUser, BindingResult result, HttpServletRequest request, SessionStatus status, Model model) 
            throws Exception {
      logger.debug("in POST method of anonymousUser.htm with id as.." + anonymousUser.getId());
      String propertyExtIdStr = null, organizationIdStr = null, propertyIdentifier = null,
    		  organizationName = null, applicationExtIdStr = null;
      Integer pId = 0, propertyExtId = -1, organizationId = 0, applicationExtId = 0;
	  String applicantEmail=null, coapplicantEmail=null;
      
      // login User
      final User user = getAuthenticatedUser();
      Map<String, String> propertyResultMap = new HashMap<String,String>();
      Map<String, String> applicationResultMap = new HashMap<String,String>();
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
//      Subscriber client = null;
 
       Property p = anonymousUser.getProperty();
       pId = p.getId();
       Property property = userService.findProperty(pId);
       if (property != null) {
    	   logger.debug("property already exists in Tenant Evaluation Database: property id#" + property.getId());
       }
       else {
    	      property = new Property();
    	   	  property.setApartmentNo(p.getApartmentNo());
    	      property.setCity(p.getCity());
    	      property.setName(p.getName());
    	      property.setStreet(p.getStreet());
    	      property.setUserId(user.getId());
    	      property.setZipcode(p.getZipcode());
    	      property.setState(p.getState());  
    	      if ((anonymousUser.getProperty().getId() == -1) && (anonymousUser.getProperty().getName() != null)){
 	    	      if (anonymousUser.getSaveNewAddress() == 'Y')
	        		  property.setFutureUse(true);
    	      }
    	      property = userService.saveNewProperty(property, user);
    	      property.setId(property.getId());
       }
       anonymousUser.setProperty(property);
      
      
//    if (user.hasRole(UserRole.PartnerAdmin.getCode()) || user.hasRole(UserRole.CustomerServiceRep.getCode())) {
//    property = userService.findPropertyById(anonymousUser.getProperty().getId());
// } else { 
//    property = userService.findProperty(anonymousUser.getProperty().getId());
// }

     
       // save anonymousUser
//      Property property = null;
//      if (!ObjectUtils.equals(anonymousUser.getProperty().getId(), null) && anonymousUser.getProperty().getId() > 0) {
//         property = userService.findProperty(anonymousUser.getProperty().getId());
//      }

      // Set Application Fee
//      if (client.getPayPerApplication() == Globals.YES) {
//    	  anonymousUser.setApplicationPaymentMethod(ApplicationPaymentMethod.APPLICANT_PREPAY);
//      } else {
//    	  anonymousUser.setApplicationPaymentMethod(ApplicationPaymentMethod.BILL_CLIENT);
//	}

 
 //     final AnonymousUser au = userService.saveAnonymousUser(anonymousUser, user);
//     status.setComplete();
      logger.debug("anonymous user .." + anonymousUser.getId());

      final boolean flag = true;
      
	  propertyExtId = property.getPropertyExtId();
				
	  if (propertyExtId == 0 || propertyExtId == null) {
		      //-------------------
		      // Submit Property
		      //-------------------
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
		      // set message and return
		
				try {
					  propertyExtId = Integer.valueOf(propertyExtIdStr);
				      property.setPropertyExtId(propertyExtId);
				      property.setPropertyIdentifier(propertyIdentifier);
				      organizationId = Integer.valueOf(organizationIdStr);
				      property.setOrganizationId(organizationId);
				      property.setOrganizationName(organizationName);
				      
				      userDao.saveProperty(property);
				      
				      //save application and applicants
				      
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
			 anonymousUser.setProperty(property);
//			 final AnonymousUser anonymousUserUpd = userService.saveAnonymousUser(au, user);
			 logger.debug("anonymous user saved successfully.." + anonymousUser.getId());
	  }
	  
	  
     //-------------------
     // Submit Application
     //-------------------
   

	  boolean isNotTest = true;
	  if (isNotTest){
    	
	  try {
		applicationResultMap = inviteService.invite(anonymousUser);
		
	  } catch (Exception e) {
		// TODO Auto-generated catch block
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
      // set message and return
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
		      application.setSelectedBundle('1');
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
         if (flag) {
            messageService.sendAnonymousUserMail(anonymousUserUpd, request);
         }
      } catch (final Exception ex) {
         // set confirmation message
         setActionMessage(request, "confirm.mail_send_fail");
         return "redirect:home.htm";
      }
      setActionMessage(request, "confirm.successful_invitation_to_apply");

      return "redirect:home.htm";
   }

   private void setAnonymousUserValues(Model model, int userId) {

      // Clients
      final Map<Integer, String> properties = new TreeMap<Integer, String>();
      
//      if (subscriber.isPartner()) {
    	  List<IntegerStringKVDto> list = listService.findPropertyLookupList(userId);
    	  if (list.size()> 0)
    		  properties.put(-1, "Select One");
    	  for (final IntegerStringKVDto s : list) {
            properties.put(s.getKey(), StringEscapeUtils.escapeJavaScript(s.getValue()));
         }
//      } else {
//         clients.put(subscriber.getId(), subscriber.getName());
//      }

      logger.debug("in Get method of anonymousUser.htm with Clients size" + properties.size());
      model.addAttribute("clients", ServiceUtils.getSortedMap(properties));
		final Map <Character, String> yNOptions = Globals.getInstance()
				.getYNOptions();
		model.addAttribute("yNOptions", yNOptions);
   }

   @RequestMapping(method = RequestMethod.GET)
   public String setupForm(@RequestParam(value = "propertyId", required = false) Integer propertyId, Model model, HttpServletRequest request) {
      logger.debug("in GET method of anonymoususer.htm with a propertyID as..." + propertyId);
 
      // get user and propertyId
//      Property property;
      final User user = getAuthenticatedUser();
      int userId = user.getId();
//      if (ObjectUtils.equals(subscriberId, null)) {
//         subscriber = user.getSubscriber();
//      } else {
//         subscriber = userService.findSubscriber(subscriberId);
//      }

      // set model data
      setPreviousActionMessage(request, model);
      setAnonymousUserValues(model, userId);

      final AnonymousUser au = new AnonymousUser();
      au.setPrice((double) 0);
//      au.setClient(new Subscriber());
      au.setProperty(new Property());
//      au.setPackge(new Packge());
      au.setReference(ServiceUtils.getRandomString());
      model.addAttribute("anonymousUser", au);
      return "anonymousUser";
   }
}