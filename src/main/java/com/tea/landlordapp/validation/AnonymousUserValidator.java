package com.tea.landlordapp.validation;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tea.landlordapp.domain.AnonymousUser;

public class AnonymousUserValidator implements Validator {

   protected final Log logger = LogFactory.getLog(getClass());

   @SuppressWarnings("rawtypes")
   @Override
   public boolean supports(Class clazz) {
      return AnonymousUser.class.equals(clazz);
   }

   @Override
   public void validate(Object obj, Errors errors) {
      logger.debug("AnonymousUserValidator : validate : Initiated ");

      final AnonymousUser au = (AnonymousUser) obj;

      TeaValidationUtils.rejectIfNotValidEmail(errors, "emailId", "invalid.emailId", new Object[]{"Anonymous User"}, false);
      if (ObjectUtils.equals(au.getApplicationType(), 'T')) {
         TeaValidationUtils.rejectIfNotValidNumber(errors, "property.id", "required.property", false);
      }

   }
}
