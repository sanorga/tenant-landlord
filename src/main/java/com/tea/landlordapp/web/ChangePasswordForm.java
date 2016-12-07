package com.tea.landlordapp.web;

import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Item;
import com.tea.landlordapp.domain.PasswordPolicy;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.service.ListService;
import com.tea.landlordapp.service.MessageService;
import com.tea.landlordapp.service.SecurityService;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.validation.PasswordChangeValidator;
import com.tea.landlordapp.validation.SecurityValidator;

@Controller
public class ChangePasswordForm extends AbstractDataController {

   @Autowired
   UserService userService;

   @Autowired
   ListService listService;
   
   @Autowired
   SecurityService securityService;

   @Autowired
   PasswordChangeValidator passwordChangeValidator;
   
  @Autowired
   StandardPasswordEncoder standardPasswordEncoder;
  
  @Autowired
  MessageService messageService;

   @RequestMapping(value="/changepwd.htm", method = RequestMethod.GET)
   public String changePassword(ModelMap model, HttpServletRequest request) {
      logger.debug("inside GET method of changepwd.htm...");

//    TeaUserDetails userDetails = (TeaUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = getAuthenticatedUser();
//		SecurityContext ctx = SecurityContextHolder.getContext();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (hasAnyAuthority(new String[]{"must.change.password","change.password"})){
//		if (isAuthorized("must.change.password")){

		      model.addAttribute("user", user);

		      return "changepwd";
		} else {
			// need to send a password reset
			try {
				messageService.sendPasswordResetEmail(user.getUsername());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/logout.htm?reset=true";
		}

   }

   @RequestMapping(value="/changepwd.htm", method = RequestMethod.POST)
   protected String processSubmit(@ModelAttribute("user") User user, 
		   BindingResult result, HttpServletRequest request, ModelMap model) {
      logger.debug("inside POST method of changepwd.htm...");

      User usr = getAuthenticatedUser();

      // onCancel
      if (WebUtils.hasSubmitParameter(request, Globals.PARAM_CANCEL)) {
//         if (usr.isChangePassword() || ObjectUtils.equals(usr.getQuestion1(), null)) {
            return "redirect:login";
//         } else
//            return onCancel1(usr);
      }

      if (StringUtils.isBlank(user.getOldPassword()))
         result.rejectValue("oldPassword", "error.invalid-oldpassword-blank", "Value required.");
      else {
         if (!securityService.checkPassword(usr, user.getOldPassword())) {
            result.rejectValue("password", "error.invalid-old-password", "Value required.");
         }

      }

      if (StringUtils.isBlank(user.getNewPassword()) && StringUtils.isEmpty(user.getNewPassword())) result.rejectValue("newPassword", "error.invalid-newpassword-blank", "Value required.");

      passwordChangeValidator.validate(user, result);
      if (result.hasErrors()) {
         model.addAttribute("user", user);
         return "changepwd";

      }

      // persist user
      usr.setStatus(Globals.ACTIVE);
      if (StringUtils.isNotBlank(user.getNewPassword())) {
    	  securityService.updatePassword(usr, user.getNewPassword());
      }
      
      // force logout
      return "redirect:/logout.htm?message=PC";
   }
   
}
