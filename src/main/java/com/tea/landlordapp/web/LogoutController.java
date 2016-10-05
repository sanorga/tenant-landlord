package com.tea.landlordapp.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/logout.htm")
public class LogoutController extends AbstractDataController {

   @RequestMapping(method = RequestMethod.GET)
   public String handleRequestInternal(@RequestParam(value="reset", required=false)Boolean reset, ModelMap model, HttpServletRequest request) throws Exception {

	   SecurityContextHolder.clearContext();
	   WebUtils.setSessionAttribute(request, "loginUser", null);
	   if (reset != null){
		   return "redirect:/login?reset=true";
	   }
      return "redirect:/login";
   }
}
