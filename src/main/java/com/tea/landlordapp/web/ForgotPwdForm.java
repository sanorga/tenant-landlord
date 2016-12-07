package com.tea.landlordapp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tea.landlordapp.service.AdminAppService;

@Controller
@RequestMapping("/forgotpwd.htm")
public class ForgotPwdForm {

   protected final Log logger = LogFactory.getLog(getClass());

   @Autowired
   AdminAppService adminAppService;

   @RequestMapping(method = RequestMethod.POST)
   protected ModelAndView processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
      logger.debug("inside POST method of forgotpwd.htm...");

      final String username = request.getParameter("username");
      
      if (!adminAppService.userExists(username)){
    	  return new ModelAndView("forgotpwd", "message", "error.user.not.found");
      }
      
      try {
          if (adminAppService.sendPasswordResetEmail(username)) {
             return new ModelAndView("redirect:/login", "loginMessage", "confirm.mail_send_success");
          } else {
             return new ModelAndView("redirect:/login", "loginMessage", "confirm.mail_send_fail");
          }
       } catch (final Exception ex) {
          // mail not sent.
          return new ModelAndView("redirect:/login", "loginMessage", "confirm.mail_send_fail");
       }


   }

   @RequestMapping(method = RequestMethod.GET)
   public String setupForm(ModelMap model) {
      logger.debug("inside GET method of forgot.htm...");

      return "forgotpwd";
   }
}