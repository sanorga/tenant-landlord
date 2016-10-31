package com.tea.landlordapp.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Application;
//import com.tea.landlordapp.domain.ApplicationCollectionItem;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.JqgridData;
import com.tea.landlordapp.dto.JqgridFilter;
import com.tea.landlordapp.dto.StatusResponse;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.ApplicationService;
//import com.tea.landlordapp.utility.ApplicationCollectionItemPick;

@Controller
public class ApplicationController extends AbstractDataController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String formName = "applications";
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	SimpleDao simpleDao;
	

	@RequestMapping(value = "/viewapplications.htm", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
	      if (!hasAnyAuthority(new String[] {"view.my.applications"})) return unAuthorized(request);
	      
	      User user = getAuthenticatedUser();
			String myRole = user.getRole().getRole();
			model.addAttribute("myRole",myRole);
			String vs = "Y";
			if (isAuthorized("view.status")){
				vs = "Y";
			}
			model.addAttribute("viewStatus",isAuthorized("view.status"));
			model.addAttribute("viewAssignments",isAuthorized("view.assignments"));
//			if(isAuthorized("view.status")) {
//				model.addAttribute("viewStatus","viewStatus");
//			}
			if(isAuthorized("view.payment.info")) model.addAttribute("showPayee","Y");
//			applicationService.setInvestigatorAndCsrLookupsIntoModel(user.getSubscriber(), model);
			
			return formName;


	}

	@RequestMapping(value = "applications.json", method = RequestMethod.GET)
	public @ResponseBody String getApplications(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", required = false, defaultValue = "20") Integer size,
			@RequestParam(value = "sidx", required = false, defaultValue = "submission_date") String sortBy,
			@RequestParam(value = "sord", required = false, defaultValue = "desc") String sortType,
			@RequestParam(value = "_search", required = false, defaultValue = "false") Boolean search,
			@RequestParam(value = "filters", required = false) String filterJSON, ModelMap model,
			HttpServletRequest request) throws JsonProcessingException {

DateFormat df = new SimpleDateFormat(Globals.TIME_FORMAT);
logger.debug(String.format("Begin applications.json for work queue: %s", df.format(new Date())));
		User user = getAuthenticatedUser();

		boolean ascending = StringUtils.equalsIgnoreCase(sortType, "desc") ? false : true;
		JqgridFilter fltr = null;
		if(!Strings.isNullOrEmpty(filterJSON)){
			fltr = JqgridFilter.getObject(filterJSON);
		}
		JqgridData apps = applicationService.getApplicationsForGrid(pageNo, size, sortBy, ascending,
				search, fltr ,user.getId());
		
		String output = (new ObjectMapper()).writeValueAsString(apps);
logger.debug(String.format("End applications.json for work queue: %s", df.format(new Date())));
    	
	    return output;
	}

	@RequestMapping(value = "abandonApplications.json", method = RequestMethod.POST)
	public @ResponseBody String abandonApplications(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "selectedApplications[]", required = true) List<Integer>	selectedApplications)
			throws JsonProcessingException {

		StatusResponse sts;
		if(isAuthorized("can.abandon.app")){
			applicationService.abandonApplications(selectedApplications, getAuthenticatedUser());
			
			sts = new StatusResponse(true,"Processed");
			
		} else {
			sts = new StatusResponse(false,"Not Authorized");
		}
		String output = (new ObjectMapper()).writeValueAsString(sts);
    	
	    return output;
	}

	@RequestMapping(value = "deleteApplications.json", method = RequestMethod.POST)
	public @ResponseBody String deleteApplication(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "selectedApplications[]", required = true) List<Integer>	apps)
			throws JsonProcessingException {

		StatusResponse sts;
		if(isAuthorized("remove.application")){
			applicationService.deleteApplications(apps, getAuthenticatedUser());
			
			sts = new StatusResponse(true,"Processed");
			
		} else {
			sts = new StatusResponse(false,"Not Authorized");
		}
		String output = (new ObjectMapper()).writeValueAsString(sts);
    	
	    return output;
	}

//	@RequestMapping(value = "markReadApplications.json", method = RequestMethod.POST)
//	public @ResponseBody String markReadApplication(ModelMap model, HttpServletRequest request,
//			@RequestParam(value = "selectedApplications[]", required = true) List<Integer>	apps)
//			throws JsonProcessingException {
//
//		StatusResponse sts;
//		if(isClientUser()){
//			applicationService.markReadApplications(apps, getAuthenticatedUser());
//			
//			sts = new StatusResponse(true,"Processed");
//			
//		} else {
//			sts = new StatusResponse(false,"Not Authorized");
//		}
//		String output = (new ObjectMapper()).writeValueAsString(sts);
//    	
//	    return output;
//	}

//	@RequestMapping(value = "assignInvestigatorApplications.json", method = RequestMethod.POST)
//	public @ResponseBody String assignInvestigator(ModelMap model, HttpServletRequest request,
//			@RequestParam(value = "selectedApplications[]", required = true) List<Integer>	apps,
//			@RequestParam(value="selectedInvestigator", required=true) Integer investigator)
//			throws JsonProcessingException {
//
//		StatusResponse sts;
//		if(isAuthorized("reassign.applications")){
//			applicationService.assignInvestigatorToApplications(apps, investigator, getAuthenticatedUser());
//			
//			sts = new StatusResponse(true,"Processed");
//			
//		} else {
//			sts = new StatusResponse(false,"Not Authorized");
//		}
//		String output = (new ObjectMapper()).writeValueAsString(sts);
//    	
//	    return output;
//	}

//	@RequestMapping(value = "assignCsrApplications.json", method = RequestMethod.POST)
//	public @ResponseBody String assignCsr(ModelMap model, HttpServletRequest request,
//			@RequestParam(value = "selectedApplications[]", required = true) List<Integer>	apps,
//			@RequestParam(value="selectedCsr", required=true) Integer csr)
//			throws JsonProcessingException {
//
//		StatusResponse sts;
//		if(isAuthorized("reassign.applications")){
//			applicationService.assignCsrToApplications(apps, csr, getAuthenticatedUser());
//			
//			sts = new StatusResponse(true,"Processed");
//			
//		} else {
//			sts = new StatusResponse(false,"Not Authorized");
//		}
//		String output = (new ObjectMapper()).writeValueAsString(sts);
//    	
//	    return output;
//	}

	@RequestMapping(value = "approveApplication.json", method = RequestMethod.POST)
	public @ResponseBody String approveApplication(ModelMap model, HttpServletRequest request,
			@RequestParam( value="appId", required=true ) Integer	appId)
			throws JsonProcessingException {

		StatusResponse sts;
		String msg = applicationService.updateApproval(appId, getAuthenticatedUser(),true);
		
		sts = new StatusResponse(true,msg);


		String output = (new ObjectMapper()).writeValueAsString(sts);
    	
	    return output;
	}

	@RequestMapping(value = "disapproveApplication.json", method = RequestMethod.POST)
	public @ResponseBody String disapproveApplication(ModelMap model, HttpServletRequest request,
			@RequestParam( value="appId", required=true ) Integer	appId)
			throws JsonProcessingException {

		StatusResponse sts;
		applicationService.updateApproval(appId, getAuthenticatedUser(),false);
		
		sts = new StatusResponse(true,"Processed");


		String output = (new ObjectMapper()).writeValueAsString(sts);
    	
	    return output;
	}
	
//	@RequestMapping(value="applicationDisposition.htm", method=RequestMethod.GET)
//	public String modifySecurityDeposit(HttpServletRequest request, Model model,
//		@RequestParam(value="id", required=true)Integer appId ,
//		@RequestParam(value="approvalUserId", required=true)int approvalUserId) {
//		final User user = getAuthenticatedUser();
//	    Integer userId = user.getId();
//	    	    
//	    if (userId == approvalUserId) {
//		    if (user == null || !user.hasAnyAuthority(new String[] {"view.all.applications",
//																	"view.partner.applications",
//																	"view.client.applications",
//																	"view.assigned.applications",
//																	"view.my.applications"})
//							 || !user.hasAnyAuthority(new String[] {"can.approve.application"})) return unAuthorized(request);
//	    } else 	return unAuthorized(request);			
//		
//	    Application app = applicationService.findApplication(appId);
//	    
//	    List<ApplicationCollectionItem> applicationCollectionItems = app.getApplicationCollectionItems();
//	    List<ApplicationCollectionItemPick> deposits=new ArrayList<ApplicationCollectionItemPick>();
//	    
//	    for (ApplicationCollectionItem applicationCollectionItem: applicationCollectionItems)
//	    {
//	    	ApplicationCollectionItemPick depositPick=new ApplicationCollectionItemPick();
//			depositPick.setId(applicationCollectionItem.getId());
//			depositPick.setDescription(applicationCollectionItem.getDescription());
//			depositPick.setAmount(applicationCollectionItem.getAmount());
//		    deposits.add(depositPick);
//	    }
//		
//		model.addAttribute("appl", app);
//		model.addAttribute("depositList",deposits);
//		return "appDisposition";
//	}

//	@RequestMapping(value="applicationDisposition.htm", method=RequestMethod.POST)
//	public String saveAppCollectionItem(HttpServletRequest request, Model model, 
//			   @ModelAttribute(value = "appId")Integer appId,
//			   @ModelAttribute(value = "unitCode")String unitCode
//			) {
//	
//		if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//			return  "redirect:viewapplications.htm";
//		}
//	
//		if (WebUtils.hasSubmitParameter(request, "_approve")) {
//			if(appId != null){
//	    	  
//	    	  Application app = applicationService.findApplication(appId);
//    		  if(!Strings.isNullOrEmpty(unitCode)) {
//    			  app.setUnitCode(unitCode);
//    			  app = simpleDao.merge(app);
//    		  }
//	    		  
//    		  String msg = applicationService.updateApproval(app.getId(), getAuthenticatedUser(),true);
//    	      setActionMessage(request, msg);
//	      }
//		}
//	    	  
//    	  return "redirect:viewapplications.htm";
//		
//	}   
	
//	@RequestMapping(value="viewapplications.htm", method = RequestMethod.POST)
//	public String processFormSubmission(HttpServletRequest request, HttpServletResponse response, 
//			   @ModelAttribute(value = "appId")Integer appId,
//			   @ModelAttribute(value = "unitCode")String unitCode) throws Exception {
//	      logger.debug("in POST method of viewapplications.htm...");
//	      
//	      // on cancel
//	      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//	         return redirectHome();
//	      }
//
//			User user = getAuthenticatedUser();
//	      
//	      if(appId != null){
//	    	  
//	    	  Application app=applicationService.findApplication(appId);
//	    	  if (WebUtils.hasSubmitParameter(request, "_approve")) {
//	    		  if(!Strings.isNullOrEmpty(unitCode)) {
//	    			  app.setUnitCode(unitCode);
//	    			  simpleDao.merge(app);
//	    		  }
//	    		  
//	    		  applicationService.updateApproval(appId, getAuthenticatedUser(),true);
//	 	      } else {
//	 	    	  return "redirect:viewapplications.htm";
//	 	      }
//	    	  
//	    	  return updateApproveDisapproveState(request, state, user, app);
//	      }
//
//	   }
//
//
//		
//	private String updateApproveDisapproveState(HttpServletRequest request, 
//			String state, final User user, Application app)
//			throws URISyntaxException, ClassNotFoundException, IOException {
//		  
////		  if (newApp.getApproved() && newApp.getExternalDeposit()){
//		  if ( StringUtils.equalsIgnoreCase(state, "AP") && app.getExternalDeposit()){
//			  if (!externalDepositService.getExternalDepositFromApplicant(app)){
//				  setActionMessage(request, "external.deposit.service.not.successful");
//				  logger.debug("externalDepositService was not successful");
//				  return "redirect:viewapplications.htm";
//			  }
//		  }
//
//		  app.setApproved(StringUtils.equalsIgnoreCase(state, "AP"));
//		  app.setApprovedBy(user);
//		  app.setApprovalDate(new Date());
//		  Application newApp = saveApplicationService.saveApplication(app, user);
//		  
//		  String queryParam = new URI(request.getHeader("referer")).getQuery();
//		  return "redirect:viewapplications.htm?"+queryParam;
//	}

	@RequestMapping(value = "requestDeposits.json", method = RequestMethod.POST)
	public @ResponseBody String requestDeposits(ModelMap model, HttpServletRequest request,
			@RequestParam( value="appId", required=true ) Integer	appId)
			throws JsonProcessingException {

		StatusResponse sts;
		String msg = applicationService.requestDeposits(appId, getAuthenticatedUser());
		
		sts = new StatusResponse(true,msg);


		String output = (new ObjectMapper()).writeValueAsString(sts);
    	
	    return output;
	}

	
	
}
