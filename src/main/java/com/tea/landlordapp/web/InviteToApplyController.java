package com.tea.landlordapp.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tea.landlordapp.repository.SimpleDao;

/**
 * Handles requests for the Invite to Apply page.
 */
@Controller
public class InviteToApplyController {
	private static final Logger logger = LoggerFactory.getLogger(InviteToApplyController.class);
	
//	private SimpleDao simpleDao;
//	@Autowired
//	public InviteToApplyController(SimpleDao simpleDao) {
//		super();
//		this.simpleDao = simpleDao;
//		
//	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/example.htm", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "menu";
	}
}
