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
import com.tea.landlordapp.dto.RenterDto;
import com.tea.landlordapp.service.UserService;
import com.tea.landlordapp.validation.TeaValidationUtils;

public class RenterDtoValidator implements Validator {

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
      logger.debug("RenterDtoValidator : validate : Initiated ");

      final RenterDto user = (RenterDto) obj;

//      TeaValidationUtils.rejectIfBlank(errors, "username", "required.user.name");
//      TeaValidationUtils.rejectIfNotValidEmail(errors, "username", "invalid.emailId", new Object[]{"User"}, false);      
//      TeaValidationUtils.rejectIfNotValidEmail(errors, "contactEmail", "invalid.emailId", new Object[]{"User"}, false);
      TeaValidationUtils.rejectIfBlank(errors, "firstName", "required.firstName");
      TeaValidationUtils.rejectIfBlank(errors, "lastName", "required.lastName");
      TeaValidationUtils.rejectIfBlank(errors, "address", "required.address", true);
      TeaValidationUtils.rejectIfBlank(errors, "zipcode", "required.zipcode", new Object[]{"RenterDto"}, true);
      TeaValidationUtils.rejectIfBlank(errors, "city", "required.city", true);
      TeaValidationUtils.rejectIfBlank(errors, "phone", "required.phone", true);
      TeaValidationUtils.rejectIfNotValidPhone(errors, "phone", "invalid.phone", new Object[]{"renterDto"}, true);
      TeaValidationUtils.rejectIfNotValidSsn(errors, "socialId", "invalid.ssn", new Object[]{"renterDto"}, true);
//      TeaValidationUtils.rejectIfNotLesserDate(errors, "oldClearDOB", "eighteenYearsAgo", "invalid.dob", new Object[]{"renterDto"});



   }
}
