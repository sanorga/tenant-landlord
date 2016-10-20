package com.tea.landlordapp.web;

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
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.IntegerStringKVDto;
//import com.tea.landlordapp.enums.ApplicationPaymentMethod;
import com.tea.landlordapp.enums.UserRole;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.service.InviteService;
import com.tea.landlordapp.service.ListService;
//import com.tea.landlordapp.service.MessageService;
//import com.tea.landlordapp.service.ServiceService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.utility.ServiceUtils;
import com.tea.landlordapp.validation.AnonymousUserValidator;

@Controller
@RequestMapping("/anonymousUser.htm")
@SessionAttributes(types = AnonymousUser.class)
public class AnonymousUserForm extends AbstractDataController {

   @Autowired
   UserService userService;

//   @Autowired
//   ServiceService serviceService;

//   @Autowired
//   MessageService messageService;

   @Autowired
   AnonymousUserValidator anonymousUserValidator;
   
   @Autowired
   ListService listService;
   
   @Autowired
   InviteService inviteService;
   
   @Autowired
   UserDao userDao;

   @RequestMapping(method = RequestMethod.POST)
   public String processSubmit(@ModelAttribute AnonymousUser anonymousUser, BindingResult result, HttpServletRequest request, SessionStatus status, Model model) 
            throws Exception {
      logger.debug("in POST method of anonymousUser.htm with id as.." + anonymousUser.getId());

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
//      Subscriber client = null;
      Property p = anonymousUser.getProperty();
      Property property = new Property();
            
      property.setApartmentNo(p.getApartmentNo());
      property.setCity(p.getCity());
      property.setName(p.getName());
      property.setRentalAmount(p.getRentalAmount());
      property.setRentalDeposit(p.getRentalDeposit());
      property.setStreet(p.getStreet());
      property.setUserId(user.getId());
      property.setZipcode(p.getZipcode());
      property.setState(p.getState());
      
//      property.setPropertyId(0);
      
      if ((anonymousUser.getProperty().getId() == -1) && (anonymousUser.getProperty().getName() != null)){
    	
    	  if (anonymousUser.getSaveNewAddress() == 'Y')
    		  property.setFutureUse(true);
     	 
      }
      property = userService.saveNewProperty(property, user);
//      if (user.hasRole(UserRole.PartnerAdmin.getCode()) || user.hasRole(UserRole.CustomerServiceRep.getCode())) {
//         property = userService.findPropertyById(anonymousUser.getProperty().getId());
//      } else { 
//         property = userService.findProperty(anonymousUser.getProperty().getId());
//      }

      // onCancel, redirect to view users view
      
      property.setId(property.getId());
      anonymousUser.setProperty(property);
      


      // save anonymousUser
//      Property property = null;
//      if (!ObjectUtils.equals(anonymousUser.getProperty().getId(), null) && anonymousUser.getProperty().getId() > 0) {
//         property = userService.findProperty(anonymousUser.getProperty().getId());
//      }
      anonymousUser.setProperty(property);

      // Set Application Fee
//      if (client.getPayPerApplication() == Globals.YES) {
//    	  anonymousUser.setApplicationPaymentMethod(ApplicationPaymentMethod.APPLICANT_PREPAY);
//      } else {
//    	  anonymousUser.setApplicationPaymentMethod(ApplicationPaymentMethod.BILL_CLIENT);
//	}

 //    anonymousUser.setClient(client);
      final AnonymousUser au = userService.saveAnonymousUser(anonymousUser, user);
      status.setComplete();
      logger.debug("anonymous user saved successfully.." + au.getId());

      final boolean flag = true;
      String propertyId = null;
      // send info to TransUnion
      try {
		propertyId = inviteService.invite(anonymousUser);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
      // send email
//      try {
//         if (flag) {
//            messageService.sendAnonymousUserMail(au, request);
//         }
//      } catch (final Exception ex) {
//         // set confirmation message
//         setActionMessage(request, "confirm.mail_send_fail");
//         return "redirect:home.htm";
//      }

      // set message and return
      Integer pId;
		try {
			pId = Integer.valueOf(propertyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "InvalidPropertyId";
		}
		
      setActionMessage(request, "confirm.anonymous_user_save_success");
      property.setPropertyId(pId);
      userDao.saveProperty(property);
      
      
      return "redirect:home.htm";
   }

   private void setAnonymousUserValues(Model model, int userId) {

      // Clients
      final Map<Integer, String> properties = new TreeMap<Integer, String>();
      
//      if (subscriber.isPartner()) {
    	  List<IntegerStringKVDto> list = listService.findPropertyLookupList(userId);
    	  if (list.size()> 0)
    		  properties.put(-1, "None");
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