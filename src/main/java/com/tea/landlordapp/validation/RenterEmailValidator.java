package com.tea.landlordapp.validation;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.service.UserService;

public class RenterEmailValidator implements Validator {

   protected final Log logger = LogFactory.getLog(getClass());

   @Autowired
   UserService userService;

   @SuppressWarnings("rawtypes")
   @Override
   public boolean supports(Class clazz) {
      return User.class.equals(clazz);
   }

   @Override
   public void validate(Object obj, Errors errors) {
      logger.debug("UserValidator : validate : Initiated ");

      final User user = (User) obj;

      TeaValidationUtils.rejectIfNotValidEmail(errors, "username", "invalid.emailId", new Object[]{"User"}, false);      

      if (StringUtils.isNotBlank(user.getUsername())) {
         final User userObj = userService.validateUserByUsername(user.getUsername());
         if (!ObjectUtils.equals(userObj, null)) {
            /* If add new user check given email id is already exist or not */
            if (user.isNew() && !ObjectUtils.equals(userObj, null)) {
               logger.debug("User is new and username already exists in db...");
               errors.reject("exists.username");
            } else if (!user.isNew() && ObjectUtils.equals(userObj, null)) {
               logger.debug("User is not new and username does not exists in db...");
               errors.reject("exists.username");
            } else if (!user.isNew() && !ObjectUtils.equals(userObj.getId(), user.getId())) {
               logger.debug("User is not new and username's do not match...");
               errors.reject("exists.username");
            }
         }
      }
   }
}
