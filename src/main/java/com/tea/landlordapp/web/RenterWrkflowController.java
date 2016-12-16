package com.tea.landlordapp.web;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.enums.UserRole;
import com.tea.landlordapp.repository.SimpleDao;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/renterwrkflow.htm")
public class RenterWrkflowController extends AbstractDataController{
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	   @Autowired
	   SimpleDao simpleDao;
	   
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(
			@RequestParam(value = "conditionId", required = false) Integer conditionId,
			@RequestParam(value = "userId", required = false) Integer userId, 
			Model model, HttpServletRequest request) throws ServletException,
			IOException {
		logger.debug("in GET method of renterwrkflow.htm...");

		User loginUser = getAuthenticatedUser();
		
	      // check authorization
	      if (!hasAnyAuthority(new String[]{"view.my.applications"})) {
	          return unAuthorized(request);
	       }
	     
	    User user = null;
	    if (ObjectUtils.equals(userId, null)) {
	    	user = simpleDao.find(User.class, userId);
	    }
	    else return "login";
	    
		model.addAttribute("user", user);

		if (!StringUtils.equals(user.getRole().getRole() , UserRole.Renter.getCode())){
			return "login";
		}
		// To add logic if personal information was confirmed
		// If there is an application then request IDMA?
		
		return "newrenterpersonalinfo";
	}
	
}
