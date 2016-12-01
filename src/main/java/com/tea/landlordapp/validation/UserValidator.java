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

public class UserValidator implements Validator {

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

      TeaValidationUtils.rejectIfBlank(errors, "username", "required.user.name");
//      TeaValidationUtils.rejectIfNotValidEmail(errors, "contactEmail", "invalid.emailId", new Object[]{"User"}, false);
      TeaValidationUtils.rejectIfBlank(errors, "firstName", "required.firstName");
      TeaValidationUtils.rejectIfBlank(errors, "lastName", "required.lastName");
      TeaValidationUtils.rejectIfBlank(errors, "address", "required.address", true);
      TeaValidationUtils.rejectIfBlank(errors, "zipcode", "required.zipcode", new Object[]{"User"}, true);
      TeaValidationUtils.rejectIfBlank(errors, "city", "required.city", true);
      TeaValidationUtils.rejectIfNotValidPhone(errors, "phone", "invalid.phone", new Object[]{"user"}, true);
      TeaValidationUtils.rejectIfNotValidPhone(errors, "fax", "invalid.fax", new Object[]{"User"}, true);

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
