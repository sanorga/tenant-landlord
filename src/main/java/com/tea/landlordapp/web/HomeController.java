package com.tea.landlordapp.web;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tea.landlordapp.domain.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/home.htm")
public class HomeController extends AbstractDataController{
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(
			@RequestParam(value = "conditionId", required = false) Integer conditionId,
			Model model, HttpServletRequest request) throws ServletException,
			IOException {
	logger.debug("in GET method of home.htm...");

		User user = getAuthenticatedUser();

		model.addAttribute("user", user);

		return "home";
	}
	
}
