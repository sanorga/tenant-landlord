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
	      String transReport1 = null;
	      if (application.isCanRequestReport() == true) {

	      // Call getReport
	    	  
	  //commented only for test purposes  	  
//	    		Map<String, String> reportMap = inviteService.getReports(applicationExtId);
//				if (reportMap == null) 
//					logger.info("Notification received and no reports obtained for application  {}", applicationExtId);
//				else logger.info("Notification received and reports obtained for application  {}", applicationExtId);
//			
		 
				// Transform report from XML to HTML

					
//	    		  	String creditReport1 = reportMap.get("creditReport1");
	    		  	
//	    		  	String creditReport1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CreditReport><cred:status><cred:reportDate>2016-11-10T00:00:00</cred:reportDate><cred:recordFound>1</cred:recordFound><cred:frozenFile>false</cred:frozenFile><cred:bureauStatus><cred:code description=\"PHONE APPEND: Area code and phone number not found on new source or on consumer database. No phone number is returned.\" type=\"TU MESSAGE\">99</cred:code><cred:code description=\"** HAWK: Clear for all searches.\" type=\"TU MESSAGE\">99</cred:code></cred:bureauStatus><cred:addressDiscrepancyIndicator>1</cred:addressDiscrepancyIndicator></cred:status><cred:firstName>PATRICIA</cred:firstName><cred:lastName>ABEL</cred:lastName><cred:middleName>A</cred:middleName><cred:birthDate xsi:nil=\"true\"/><cred:addresses><cred:address><cred:streetAddress>471 9TH ST</cred:streetAddress><cred:city>BROOKLYN</cred:city><cred:state>NY</cred:state><cred:postalCode>11215</cred:postalCode><cred:dateReported>2016-03-11T00:00:00</cred:dateReported><cred:sourceIndicator>F</cred:sourceIndicator><cred:addressQualifier>1</cred:addressQualifier></cred:address></cred:addresses><cred:akas/><cred:scores><cred:score><cred:score>498</cred:score><cred:modelTypeIndicator>00P63</cred:modelTypeIndicator><cred:scoreFactor1>Too few bank revolving accounts</cred:scoreFactor1><cred:scoreFactor2>Number of accounts with delinquency</cred:scoreFactor2><cred:scoreFactor3>Level of delinquency on accounts</cred:scoreFactor3><cred:scoreFactor4>Proportion of balances to credit limits is too high on bank revolving or other revolving accounts</cred:scoreFactor4><cred:derogAlertCode>Inquiries impacted the credit score</cred:derogAlertCode></cred:score></cred:scores><cred:fraudIndicators><cred:fraudIndicator>C11</cred:fraudIndicator></cred:fraudIndicators><cred:employments><cred:employment><cred:employerName>WELLS FARGO BANK</cred:employerName><cred:dateVerified>2016-10-01T00:00:00</cred:dateVerified><cred:dateEmployed xsi:nil=\"true\"/></cred:employment><cred:employment><cred:employerName>TFS</cred:employerName><cred:dateVerified>2016-10-01T00:00:00</cred:dateVerified><cred:dateEmployed xsi:nil=\"true\"/></cred:employment></cred:employments><cred:consumerStatements/><cred:consumerRightsStatements/><cred:inquiries><cred:inquiry><cred:subscriberId>ZTR3560255</cred:subscriberId><cred:subscriberName>TURSS</cred:subscriberName><cred:inquiryDate>2016-11-10T00:00:00</cred:inquiryDate><cred:industryCode>Z</cred:industryCode></cred:inquiry><cred:inquiry><cred:subscriberId>BLO02382255</cred:subscriberId><cred:subscriberName>HSBC</cred:subscriberName><cred:inquiryDate>2016-09-30T00:00:00</cred:inquiryDate><cred:industryCode>B</cred:industryCode></cred:inquiry><cred:inquiry><cred:subscriberId>YPE02503488</cred:subscriberId><cred:subscriberName>HILCO</cred:subscriberName><cred:inquiryDate>2016-09-30T00:00:00</cred:inquiryDate><cred:industryCode>Y</cred:industryCode></cred:inquiry></cred:inquiries><cred:publicRecords><cred:publicRecord><cred:publicRecordType>CJ</cred:publicRecordType><cred:memberCode>04871418</cred:memberCode><cred:industryCode>Z</cred:industryCode><cred:dateSettled xsi:nil=\"true\"/><cred:dateReported>2013-07-07T00:00:00</cred:dateReported><cred:dateVerified xsi:nil=\"true\"/><cred:amount xsi:nil=\"true\"/><cred:assetAmount xsi:nil=\"true\"/><cred:accountDesignator>I</cred:accountDesignator><cred:referenceNumber>444429048</cred:referenceNumber><cred:plaintiff>CITIES MANAGEMENT INC</cred:plaintiff><cred:courtType>CI</cred:courtType><cred:dateFiled xsi:nil=\"true\"/><cred:dateFiledOriginal xsi:nil=\"true\"/><cred:liabilitiesAmount>1960</cred:liabilitiesAmount></cred:publicRecord><cred:publicRecord><cred:publicRecordType>CJ</cred:publicRecordType><cred:memberCode>04871382</cred:memberCode><cred:industryCode>Z</cred:industryCode><cred:dateSettled xsi:nil=\"true\"/><cred:dateReported>2013-09-07T00:00:00</cred:dateReported><cred:dateVerified xsi:nil=\"true\"/><cred:amount xsi:nil=\"true\"/><cred:assetAmount xsi:nil=\"true\"/><cred:accountDesignator>I</cred:accountDesignator><cred:referenceNumber>44445465</cred:referenceNumber><cred:plaintiff>CITIES MANAGEMENT INC</cred:plaintiff><cred:courtType>CI</cred:courtType><cred:dateFiled xsi:nil=\"true\"/><cred:dateFiledOriginal xsi:nil=\"true\"/><cred:liabilitiesAmount>1960</cred:liabilitiesAmount></cred:publicRecord><cred:publicRecord><cred:publicRecordType>CJ</cred:publicRecordType><cred:memberCode>04871418</cred:memberCode><cred:industryCode>Z</cred:industryCode><cred:dateSettled xsi:nil=\"true\"/><cred:dateReported>2014-10-02T00:00:00</cred:dateReported><cred:dateVerified xsi:nil=\"true\"/><cred:amount xsi:nil=\"true\"/><cred:assetAmount xsi:nil=\"true\"/><cred:accountDesignator>I</cred:accountDesignator><cred:referenceNumber>444427009</cred:referenceNumber><cred:plaintiff>AMERICAN GENERAL FINAN</cred:plaintiff><cred:courtType>CI</cred:courtType><cred:dateFiled xsi:nil=\"true\"/><cred:dateFiledOriginal xsi:nil=\"true\"/><cred:liabilitiesAmount>4991</cred:liabilitiesAmount></cred:publicRecord><cred:publicRecord><cred:publicRecordType>CJ</cred:publicRecordType><cred:memberCode>04871382</cred:memberCode><cred:industryCode>Z</cred:industryCode><cred:dateSettled xsi:nil=\"true\"/><cred:dateReported>2015-03-07T00:00:00</cred:dateReported><cred:dateVerified xsi:nil=\"true\"/><cred:amount xsi:nil=\"true\"/><cred:assetAmount xsi:nil=\"true\"/><cred:accountDesignator>I</cred:accountDesignator><cred:referenceNumber>444415260</cred:referenceNumber><cred:plaintiff>AMERICAN GENERAL FINAN</cred:plaintiff><cred:courtType>CI</cred:courtType><cred:dateFiled xsi:nil=\"true\"/><cred:dateFiledOriginal xsi:nil=\"true\"/><cred:liabilitiesAmount>4991</cred:liabilitiesAmount></cred:publicRecord></cred:publicRecords><cred:tradeLines><cred:tradeLine><cred:subscriberId>0654N4J7</cred:subscriberId><cred:subscriberName>SPRINGLF FIN</cred:subscriberName><cred:dateReported>2015-06-02T00:00:00</cred:dateReported><cred:terms>048</cred:terms><cred:termsAmountOfPayment>191</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>I</cred:accountType><cred:AccountNumber>4444450031803035</cred:AccountNumber><cred:industryCode>F</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2015-06-02T00:00:00</cred:dateVerified><cred:dateOpened>2013-08-02T00:00:00</cred:dateOpened><cred:dateClosed>2015-06-02T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>09</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>4824</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:loanType>AU</cred:loanType><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/><cred:remarksCode>PRL</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>0382D159</cred:subscriberId><cred:subscriberName>CHASE MHT BK</cred:subscriberName><cred:dateReported>2013-06-24T00:00:00</cred:dateReported><cred:termsAmountOfPayment xsi:nil=\"true\"/><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>R</cred:accountType><cred:AccountNumber>444440025184</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2013-06-24T00:00:00</cred:dateVerified><cred:dateOpened>2010-01-07T00:00:00</cred:dateOpened><cred:dateClosed>2012-10-02T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>09</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=/"true/"/><cred:highCredit xsi:nil=/"true/"/><cred:creditLimit>2000</cred:creditLimit><cred:loanType>CC</cred:loanType><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/><cred:remarksCode>PRL</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>01972038</cred:subscriberId><cred:subscriberName>JCP--MCCBG</cred:subscriberName><cred:dateReported>2016-09-30T00:00:00</cred:dateReported><cred:termsAmountOfPayment xsi:nil=\"true\"/><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>R</cred:accountType><cred:AccountNumber>444443266</cred:AccountNumber><cred:industryCode>D</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2016-09-30T00:00:00</cred:dateVerified><cred:dateOpened>2007-07-02T00:00:00</cred:dateOpened><cred:dateClosed>2011-11-30T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>9B</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>152</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:loanType>CH</cred:loanType><cred:amountPastDue>77</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>77</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/><cred:remarksCode>CLA</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>0701N098</cred:subscriberId><cred:subscriberName>CHASE</cred:subscriberName><cred:dateReported>2016-10-27T00:00:00</cred:dateReported><cred:terms>MIN</cred:terms><cred:termsAmountOfPayment>22</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>R</cred:accountType><cred:AccountNumber>0700793136</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2016-10-27T00:00:00</cred:dateVerified><cred:dateOpened>2015-11-01T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>03</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>758</cred:highCredit><cred:creditLimit>600</cred:creditLimit><cred:loanType>CC</cred:loanType><cred:amountPastDue>32</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>758</cred:balanceAmount><cred:paymentPattern>2111111211</cred:paymentPattern><cred:paymentPatternStartDate>2016-09-27T00:00:00</cred:paymentPatternStartDate><cred:times30DaysLate>2</cred:times30DaysLate><cred:times60DaysLate>0</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate></cred:tradeLine><cred:tradeLine><cred:subscriberId>0321E795</cred:subscriberId><cred:subscriberName>AVCO FINANCE</cred:subscriberName><cred:dateReported>2014-04-26T00:00:00</cred:dateReported><cred:terms>048</cred:terms><cred:termsAmountOfPayment>191</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>I</cred:accountType><cred:AccountNumber>44444000545100115</cred:AccountNumber><cred:industryCode>F</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2014-04-26T00:00:00</cred:dateVerified><cred:dateOpened>2013-08-03T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>6100</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:loanType>AU</cred:loanType><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPattern>X1X1111</cred:paymentPattern><cred:paymentPatternStartDate>2014-03-26T00:00:00</cred:paymentPatternStartDate><cred:times30DaysLate>0</cred:times30DaysLate><cred:times60DaysLate>0</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate><cred:remarksCode>TRL</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>0402D013</cred:subscriberId><cred:subscriberName>CHASE NA</cred:subscriberName><cred:dateReported>2012-12-23T00:00:00</cred:dateReported><cred:termsAmountOfPayment xsi:nil=\"true\"/><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>R</cred:accountType><cred:AccountNumber>444442319</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2012-12-23T00:00:00</cred:dateVerified><cred:dateOpened>2008-06-04T00:00:00</cred:dateOpened><cred:dateClosed>2010-01-14T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>119</cred:highCredit><cred:creditLimit>2000</cred:creditLimit><cred:amountPastDue xsi:nil=\"true\"/><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPattern>1</cred:paymentPattern><cred:paymentPatternStartDate>2012-11-23T00:00:00</cred:paymentPatternStartDate><cred:times30DaysLate>0</cred:times30DaysLate><cred:times60DaysLate>0</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate><cred:remarksCode>CLO</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>0624C174</cred:subscriberId><cred:subscriberName>TD AUTO FIN</cred:subscriberName><cred:dateReported>2012-03-02T00:00:00</cred:dateReported><cred:terms>044</cred:terms><cred:termsAmountOfPayment xsi:nil=\"true\"/><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>I</cred:accountType><cred:AccountNumber>444448616A</cred:AccountNumber><cred:industryCode>F</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2012-03-02T00:00:00</cred:dateVerified><cred:dateOpened>2009-01-08T00:00:00</cred:dateOpened><cred:dateClosed>2012-03-02T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>10114</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:loanType>AU</cred:loanType><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPattern>13222222222122111111111111</cred:paymentPattern><cred:paymentPatternStartDate>2012-02-02T00:00:00</cred:paymentPatternStartDate><cred:times30DaysLate>11</cred:times30DaysLate><cred:times60DaysLate>1</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate><cred:remarksCode>CLO</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>02305002</cred:subscriberId><cred:subscriberName>CB/GRDMNS</cred:subscriberName><cred:dateReported>2011-07-10T00:00:00</cred:dateReported><cred:termsAmountOfPayment xsi:nil=\"true\"/><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>R</cred:accountType><cred:AccountNumber>4444448</cred:AccountNumber><cred:industryCode>D</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2011-07-10T00:00:00</cred:dateVerified><cred:dateOpened>2008-05-09T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>1144</cred:highCredit><cred:creditLimit>0</cred:creditLimit><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate>0</cred:times30DaysLate><cred:times60DaysLate>0</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate></cred:tradeLine><cred:tradeLine><cred:subscriberId>06243001</cred:subscriberId><cred:subscriberName>MAG FED BK</cred:subscriberName><cred:dateReported>2010-04-27T00:00:00</cred:dateReported><cred:termsAmountOfPayment>1002</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>M</cred:accountType><cred:AccountNumber>4444420375</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>C</cred:accountDesignator><cred:dateVerified>2010-04-27T00:00:00</cred:dateVerified><cred:dateOpened>2004-04-18T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>91950</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPattern>X2</cred:paymentPattern><cred:paymentPatternStartDate>2010-03-27T00:00:00</cred:paymentPatternStartDate><cred:times30DaysLate>1</cred:times30DaysLate><cred:times60DaysLate>0</cred:times60DaysLate><cred:times90DaysLate>0</cred:times90DaysLate></cred:tradeLine><cred:tradeLine><cred:subscriberId>09114001</cred:subscriberId><cred:subscriberName>ALTUS MTG</cred:subscriberName><cred:dateReported>2009-10-31T00:00:00</cred:dateReported><cred:termsAmountOfPayment>971</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>M</cred:accountType><cred:AccountNumber>4444422203750000</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>C</cred:accountDesignator><cred:dateVerified>2009-10-31T00:00:00</cred:dateVerified><cred:dateOpened>2004-04-19T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>91950</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/><cred:remarksCode>TRF</cred:remarksCode></cred:tradeLine><cred:tradeLine><cred:subscriberId>0893P001</cred:subscriberId><cred:subscriberName>SIGNAL BANK</cred:subscriberName><cred:dateReported>2007-09-10T00:00:00</cred:dateReported><cred:terms>036</cred:terms><cred:termsAmountOfPayment>89</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>I</cred:accountType><cred:AccountNumber>444441390</cred:AccountNumber><cred:industryCode>B</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2007-09-10T00:00:00</cred:dateVerified><cred:dateOpened>2004-09-07T00:00:00</cred:dateOpened><cred:dateClosed>2007-09-10T00:00:00</cred:dateClosed><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>2650</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:loanType>AU</cred:loanType><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount>0</cred:balanceAmount><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/></cred:tradeLine><cred:tradeLine><cred:subscriberId>0447394F</cred:subscriberId><cred:subscriberName>ITT FIN SVC</cred:subscriberName><cred:dateReported>2007-02-28T00:00:00</cred:dateReported><cred:terms>036</cred:terms><cred:termsAmountOfPayment>69</cred:termsAmountOfPayment><cred:balanceDate xsi:nil=\"true\"/><cred:amount1 xsi:nil=\"true\"/><cred:amount2 xsi:nil=\"true\"/><cred:accountType>I</cred:accountType><cred:AccountNumber>44444024</cred:AccountNumber><cred:industryCode>F</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2007-02-28T00:00:00</cred:dateVerified><cred:dateOpened>2005-12-01T00:00:00</cred:dateOpened><cred:dateClosed xsi:nil=\"true\"/><cred:datePaidOut_x0020_ xsi:nil=\"true\"/><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>01</cred:currentMOP><cred:maximumdelinqDate_x0020_ xsi:nil=\"true\"/><cred:highCredit>2493</cred:highCredit><cred:creditLimit xsi:nil=\"true\"/><cred:termsFrequencyOfPayment>M</cred:termsFrequencyOfPayment><cred:amountPastDue>0</cred:amountPastDue><cred:previous1Date xsi:nil=\"true\"/><cred:previous2Date xsi:nil=\"true\"/><cred:previous3Date xsi:nil=\"true\"/><cred:balanceAmount xsi:nil=\"true\"/><cred:paymentPatternStartDate xsi:nil=\"true\"/><cred:times30DaysLate xsi:nil=\"true\"/><cred:times60DaysLate xsi:nil=\"true\"/><cred:times90DaysLate xsi:nil=\"true\"/></cred:tradeLine></cred:tradeLines><cred:collections><cred:collection><cred:accountType>O</cred:accountType><cred:accountNumber>4444469A0</cred:accountNumber><cred:customerNumber>01PXV001</cred:customerNumber><cred:collectionAgencyName>COMMRCL FIN</cred:collectionAgencyName><cred:creditorsName>CHASE</cred:creditorsName><cred:industryCode>Y</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2016-01-31T00:00:00</cred:dateVerified><cred:dateOpened>2013-06-03T00:00:00</cred:dateOpened><cred:dateClosedIndicator>F</cred:dateClosedIndicator><cred:dateClosed>2013-05-13T00:00:00</cred:dateClosed><cred:datePaidOut xsi:nil=\"true\"/><cred:dateReported>2013-06-03T00:00:00</cred:dateReported><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>09</cred:currentMOP><cred:currentBalance>3062</cred:currentBalance><cred:remarksCode>PRL</cred:remarksCode><cred:highCredit>2787</cred:highCredit><cred:pastDue xsi:nil=\"true\"/></cred:collection><cred:collection><cred:accountType>O</cred:accountType><cred:accountNumber>4444499769</cred:accountNumber><cred:customerNumber>01LPA008</cred:customerNumber><cred:collectionAgencyName>ALLIED INT</cred:collectionAgencyName><cred:creditorsName>NORWEST CHASE</cred:creditorsName><cred:industryCode>Y</cred:industryCode><cred:accountDesignator>I</cred:accountDesignator><cred:dateVerified>2016-10-26T00:00:00</cred:dateVerified><cred:dateOpened>2015-12-31T00:00:00</cred:dateOpened><cred:dateClosedIndicator>F</cred:dateClosedIndicator><cred:dateClosed>2012-12-05T00:00:00</cred:dateClosed><cred:datePaidOut xsi:nil=\"true\"/><cred:dateReported>2015-12-31T00:00:00</cred:dateReported><cred:verificationIndicator>A</cred:verificationIndicator><cred:currentMOP>9B</cred:currentMOP><cred:currentBalance>3232</cred:currentBalance><cred:remarksCode>CLA</cred:remarksCode><cred:highCredit>3043</cred:highCredit><cred:pastDue xsi:nil=\"true\"/></cred:collection></cred:collections><cred:profileSummary><cred:publicRecordCount xsi:nil=\"true\"/><cred:installBalance xsi:nil=\"true\"/><cred:realEstateBalance xsi:nil=\"true\"/><cred:revolveBalance xsi:nil=\"true\"/><cred:pastDueAmount xsi:nil=\"true\"/><cred:monthlyPayment xsi:nil=\"true\"/><cred:realEstatePayment xsi:nil=\"true\"/><cred:revolveAvailPercent xsi:nil=\"true\"/><cred:derogItems><cred:publicRecordCount>4</cred:publicRecordCount><cred:collectionCount>2</cred:collectionCount><cred:negTradelineCount>4</cred:negTradelineCount><cred:histNegTradelineCount>3</cred:histNegTradelineCount><cred:occuranceHistCount>15</cred:occuranceHistCount></cred:derogItems><cred:pastDueItems><cred:revolvingPastDue>32</cred:revolvingPastDue><cred:installmentPastDue>0</cred:installmentPastDue><cred:mortgagePastDue xsi:nil=\"true\"/><cred:openPastDue xsi:nil=\"true\"/><cred:closedWithBalPastDue>77</cred:closedWithBalPastDue><cred:totalPastDue>109</cred:totalPastDue></cred:pastDueItems><cred:revolving><cred:count>5</cred:count><cred:highCredit>1902</cred:highCredit><cred:creditLimit>600</cred:creditLimit><cred:balance>758</cred:balance><cred:monthlyPayment>22</cred:monthlyPayment><cred:percentCreditAvail>60</cred:percentCreditAvail></cred:revolving><cred:installment><cred:count>5</cred:count><cred:highCredit>8593</cred:highCredit><cred:creditLimit>0</cred:creditLimit><cred:balance>0</cred:balance><cred:monthlyPayment>0</cred:monthlyPayment><cred:percentCreditAvail xsi:nil=\"true\"/></cred:installment><cred:mortgage><cred:count>2</cred:count><cred:highCredit xsi:nil=\"true\"/><cred:creditLimit xsi:nil=\"true\"/><cred:balance xsi:nil=\"true\"/><cred:monthlyPayment xsi:nil=\"true\"/><cred:percentCreditAvail xsi:nil=\"true\"/></cred:mortgage><cred:open><cred:count>0</cred:count><cred:highCredit xsi:nil=\"true\"/><cred:creditLimit xsi:nil=\"true\"/><cred:balance xsi:nil=\"true\"/><cred:monthlyPayment xsi:nil=\"true\"/><cred:percentCreditAvail xsi:nil=\"true\"/></cred:open><cred:closedWithBal><cred:count xsi:nil=\"true\"/><cred:highCredit xsi:nil=\"true\"/><cred:creditLimit xsi:nil=\"true\"/><cred:balance>77</cred:balance><cred:monthlyPayment>0</cred:monthlyPayment><cred:percentCreditAvail xsi:nil=\"true\"/></cred:closedWithBal><cred:total><cred:count>12</cred:count><cred:highCredit>10495</cred:highCredit><cred:creditLimit>600</cred:creditLimit><cred:balance>835</cred:balance><cred:monthlyPayment>22</cred:monthlyPayment><cred:percentCreditAvail xsi:nil=\"true\"/></cred:total><cred:numberOfInquiries>3</cred:numberOfInquiries></cred:profileSummary><cred:subscribers/><cred:reportRetrievedOn xsi:nil=\"true\"/></CreditReport>";

	    	  String creditReport1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CreditReport>   <cred:status>     <cred:reportDate>2016-09-01T00:00:00</cred:reportDate>     <cred:recordFound>1</cred:recordFound>     <cred:frozenFile>false</cred:frozenFile>     <cred:bureauStatus>       <cred:code type=\"TU MESSAGE\" description=\"PHONE APPEND: Area code and phone number not found on new source or on consumer database. No phone number is returned.\">99</cred:code>     </cred:bureauStatus>     <cred:addressDiscrepancyIndicator>1</cred:addressDiscrepancyIndicator>   </cred:status>   <cred:firstName>JOSEPH</cred:firstName>   <cred:lastName>TEST</cred:lastName>   <cred:birthDate xsi:nil=\"true\" />   <cred:addresses>     <cred:address>       <cred:streetAddress>39 S GREEN ST</cred:streetAddress>       <cred:city>FANTASY ISLAND</cred:city>       <cred:state>IL</cred:state>       <cred:postalCode>60750</cred:postalCode>       <cred:dateReported>2014-08-31T00:00:00</cred:dateReported>       <cred:sourceIndicator>F</cred:sourceIndicator>       <cred:addressQualifier>1</cred:addressQualifier>     </cred:address>     <cred:address>       <cred:streetAddress>11 RIDGE WY</cred:streetAddress>       <cred:city>FANTASY ISLAND</cred:city>       <cred:state>IL</cred:state>       <cred:postalCode>60750</cred:postalCode>       <cred:dateReported xsi:nil=\"true\" />       <cred:sourceIndicator>F</cred:sourceIndicator>       <cred:addressQualifier>1</cred:addressQualifier>     </cred:address>   </cred:addresses>   <cred:akas>     <cred:aka>       <turss:lastName>TEST,JOSEPH</turss:lastName>       <turss:birthDate xsi:nil=\"true\" />     </cred:aka>   </cred:akas>   <cred:scores>     <cred:score>       <cred:score>647</cred:score>       <cred:modelTypeIndicator>00V25</cred:modelTypeIndicator>       <cred:scoreFactor1>Not enough retail debt experience</cred:scoreFactor1>       <cred:scoreFactor2>Not enough installment debt experience</cred:scoreFactor2>       <cred:scoreFactor3>Available credit on bankcard accounts is too low</cred:scoreFactor3>       <cred:scoreFactor4>Too many inquiries</cred:scoreFactor4>     </cred:score>   </cred:scores>   <cred:fraudIndicators>     <cred:fraudIndicator>C11</cred:fraudIndicator>     <cred:fraudIndicator>41</cred:fraudIndicator>     <cred:fraudIndicator>4501</cred:fraudIndicator>   </cred:fraudIndicators>   <cred:employments>     <cred:employment>       <cred:employerName>ACME</cred:employerName>       <cred:dateVerified>2016-05-01T00:00:00</cred:dateVerified>       <cred:dateEmployed xsi:nil=\"true\" />     </cred:employment>     <cred:employment>       <cred:employerName>ABC CO</cred:employerName>       <cred:dateVerified>2016-05-01T00:00:00</cred:dateVerified>       <cred:dateEmployed xsi:nil=\"true\" />     </cred:employment>   </cred:employments>   <cred:consumerStatements />   <cred:consumerRightsStatements />   <cred:inquiries>     <cred:inquiry>       <cred:subscriberId>ZTR01000197</cred:subscriberId>       <cred:subscriberName>21ST CENTURY</cred:subscriberName>       <cred:inquiryDate>2015-09-09T00:00:00</cred:inquiryDate>       <cred:industryCode>Z</cred:industryCode>     </cred:inquiry>     <cred:inquiry>       <cred:subscriberId>ZTR01000725</cred:subscriberId>       <cred:subscriberName>CENTRIX FINA</cred:subscriberName>       <cred:inquiryDate>2015-09-08T00:00:00</cred:inquiryDate>       <cred:industryCode>Z</cred:industryCode>     </cred:inquiry>   </cred:inquiries>   <cred:publicRecords>     <cred:publicRecord>       <cred:publicRecordType>7D</cred:publicRecordType>       <cred:memberCode>05048079</cred:memberCode>       <cred:industryCode>Z</cred:industryCode>       <cred:dateSettled xsi:nil=\"true\" />       <cred:dateReported>2015-04-15T00:00:00</cred:dateReported>       <cred:dateVerified xsi:nil=\"true\" />       <cred:amount xsi:nil=\"true\" />       <cred:assetAmount xsi:nil=\"true\" />       <cred:accountDesignator>I</cred:accountDesignator>       <cred:referenceNumber>960000281</cred:referenceNumber>       <cred:legalDesignator>JOHN B DOE</cred:legalDesignator>       <cred:plaintiff>JOHN B DOE</cred:plaintiff>       <cred:courtType>FE</cred:courtType>       <cred:courtLocationCity>SPRINGFIEL</cred:courtLocationCity>       <cred:courtLocationState>PA</cred:courtLocationState>       <cred:dateFiled xsi:nil=\"true\" />       <cred:dateFiledOriginal xsi:nil=\"true\" />       <cred:liabilitiesAmount xsi:nil=\"true\" />     </cred:publicRecord>   </cred:publicRecords>   <cred:tradeLines>     <cred:tradeLine>       <cred:subscriberId>01DTV001</cred:subscriberId>       <cred:subscriberName>CAPITAL 1 BK</cred:subscriberName>       <cred:dateReported>2016-02-02T00:00:00</cred:dateReported>       <cred:termsAmountOfPayment xsi:nil=\"true\" />       <cred:balanceDate xsi:nil=\"true\" />       <cred:amount1 xsi:nil=\"true\" />       <cred:amount2 xsi:nil=\"true\" />       <cred:accountType>R</cred:accountType>       <cred:AccountNumber>50008</cred:AccountNumber>       <cred:industryCode>B</cred:industryCode>       <cred:accountDesignator>C</cred:accountDesignator>       <cred:dateVerified>2016-02-02T00:00:00</cred:dateVerified>       <cred:dateOpened>2009-05-04T00:00:00</cred:dateOpened>       <cred:dateClosed xsi:nil=\"true\" />       <cred:datePaidOut_x0020_ xsi:nil=\"true\" />       <cred:verificationIndicator>V</cred:verificationIndicator>       <cred:currentMOP>01</cred:currentMOP>       <cred:maximumdelinqDate_x0020_ xsi:nil=\"true\" />       <cred:highCredit>1002</cred:highCredit>       <cred:creditLimit xsi:nil=\"true\" />       <cred:loanType>CC</cred:loanType>       <cred:amountPastDue>0</cred:amountPastDue>       <cred:previous1Date xsi:nil=\"true\" />       <cred:previous2Date xsi:nil=\"true\" />       <cred:previous3Date xsi:nil=\"true\" />       <cred:balanceAmount>566</cred:balanceAmount>       <cred:paymentPattern>1111111111111X1111111X11111111111111111111111111</cred:paymentPattern>       <cred:paymentPatternStartDate>2016-01-02T00:00:00</cred:paymentPatternStartDate>       <cred:times30DaysLate>0</cred:times30DaysLate>       <cred:times60DaysLate>0</cred:times60DaysLate>       <cred:times90DaysLate>0</cred:times90DaysLate>     </cred:tradeLine>   </cred:tradeLines>   <cred:collections />   <cred:profileSummary>     <cred:publicRecordCount xsi:nil=\"true\" />     <cred:installBalance xsi:nil=\"true\" />     <cred:realEstateBalance xsi:nil=\"true\" />     <cred:revolveBalance xsi:nil=\"true\" />     <cred:pastDueAmount xsi:nil=\"true\" />     <cred:monthlyPayment xsi:nil=\"true\" />     <cred:realEstatePayment xsi:nil=\"true\" />     <cred:revolveAvailPercent xsi:nil=\"true\" />     <cred:derogItems>       <cred:publicRecordCount>1</cred:publicRecordCount>       <cred:collectionCount>0</cred:collectionCount>       <cred:negTradelineCount>0</cred:negTradelineCount>       <cred:histNegTradelineCount>0</cred:histNegTradelineCount>       <cred:occuranceHistCount>0</cred:occuranceHistCount>     </cred:derogItems>     <cred:pastDueItems>       <cred:revolvingPastDue>0</cred:revolvingPastDue>       <cred:installmentPastDue xsi:nil=\"true\" />       <cred:mortgagePastDue xsi:nil=\"true\" />       <cred:openPastDue xsi:nil=\"true\" />       <cred:closedWithBalPastDue xsi:nil=\"true\" />       <cred:totalPastDue>0</cred:totalPastDue>     </cred:pastDueItems>     <cred:revolving>       <cred:count>1</cred:count>       <cred:highCredit>1002</cred:highCredit>       <cred:creditLimit>0</cred:creditLimit>       <cred:balance>566</cred:balance>       <cred:monthlyPayment>0</cred:monthlyPayment>       <cred:percentCreditAvail>44</cred:percentCreditAvail>     </cred:revolving>     <cred:installment>       <cred:count>0</cred:count>       <cred:highCredit xsi:nil=\"true\" />       <cred:creditLimit xsi:nil=\"true\" />       <cred:balance xsi:nil=\"true\" />       <cred:monthlyPayment xsi:nil=\"true\" />       <cred:percentCreditAvail xsi:nil=\"true\" />     </cred:installment>     <cred:mortgage>       <cred:count>0</cred:count>       <cred:highCredit xsi:nil=\"true\" />       <cred:creditLimit xsi:nil=\"true\" />       <cred:balance xsi:nil=\"true\" />       <cred:monthlyPayment xsi:nil=\"true\" />       <cred:percentCreditAvail xsi:nil=\"true\" />     </cred:mortgage>     <cred:open>       <cred:count>0</cred:count>       <cred:highCredit xsi:nil=\"true\" />       <cred:creditLimit xsi:nil=\"true\" />       <cred:balance xsi:nil=\"true\" />       <cred:monthlyPayment xsi:nil=\"true\" />       <cred:percentCreditAvail xsi:nil=\"true\" />     </cred:open>     <cred:closedWithBal>       <cred:count xsi:nil=\"true\" />       <cred:highCredit xsi:nil=\"true\" />       <cred:creditLimit xsi:nil=\"true\" />       <cred:balance xsi:nil=\"true\" />       <cred:monthlyPayment xsi:nil=\"true\" />       <cred:percentCreditAvail xsi:nil=\"true\" />     </cred:closedWithBal>     <cred:total>       <cred:count>1</cred:count>       <cred:highCredit>1002</cred:highCredit>       <cred:creditLimit>0</cred:creditLimit>       <cred:balance>566</cred:balance>       <cred:monthlyPayment>0</cred:monthlyPayment>       <cred:percentCreditAvail xsi:nil=\"true\" />     </cred:total>     <cred:numberOfInquiries>2</cred:numberOfInquiries>   </cred:profileSummary>   <cred:subscribers />   <cred:reportRetrievedOn xsi:nil=\"true\" /> </CreditReport> ";
	  		
	    		  	logger.debug(creditReport1);
	    		  	if (StringUtils.isBlank(creditReport1))	{
	    		  		logger.debug("no report found...");
	    		  	}
	    		  	else {		 
	    		  		try {
				    		  	String xslFilePath = StringHelper.concatWithSingleSlash(request.getSession().getServletContext().getRealPath("/"), Globals.XSL_PATH);
				    		    xslFilePath = StringHelper.concatWithSingleSlash(xslFilePath, "TUCreditReport.xslt");
				    		             	
			//	    		    final String xslFilePath = request.getSession().getServletContext().getRealPath("/" + Globals.XSL_PATH) + "/" + report.getService().getXslUrl();
				    		    transReport1 = ServiceUtils.transformXmlToHtml(creditReport1, xslFilePath);
			//	    		    StringBuilder sb = new StringBuilder();
				    		   

	    		                
//	    		                report.setTransformedReport(transReport);
	    		             } catch (final Exception e) {
	    		                e.printStackTrace();

	    		                logger.error("Unable to do xml/xsl conversion for...");
	    		                logger.error("XML:\n" + creditReport1);
	    		                logger.error("XSL File:\n" + "TUCreditReport.xslt");
	    		                // report.setTransformedReport(report.getReport());
//	    		                report.setTransformedReport("<br><br><strong>Please contact Administrator</strong>");
	    		                transReport1 = "<br><br><strong>Please contact Administrator</strong>";
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
	      model.addAttribute("creditReport1", transReport1);
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
