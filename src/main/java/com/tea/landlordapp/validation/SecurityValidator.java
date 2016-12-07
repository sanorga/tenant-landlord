package com.tea.landlordapp.validation;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tea.landlordapp.domain.User;

public class SecurityValidator implements Validator {

   protected final Log logger = LogFactory.getLog(getClass());

   @SuppressWarnings("rawtypes")
   @Override
   public boolean supports(Class clazz) {
      return User.class.equals(clazz);
   }

   @Override
   public void validate(Object obj, Errors errors) {
      logger.debug("SecurityValidator : validate : Initiated ");

      final User user = (User) obj;

//      if (ObjectUtils.equals(user.getTypedAnswer(), null)) {
         TeaValidationUtils.rejectIfBlank(errors, "guess1", "required.answer1");
         TeaValidationUtils.rejectIfBlank(errors, "guess2", "required.answer2");
         TeaValidationUtils.rejectIfBlank(errors, "guess3", "required.answer3");
//      } else {
//         if (StringUtils.isBlank(user.getTypedAnswer()) && StringUtils.isEmpty(user.getTypedAnswer())) {
//            TeaValidationUtils.rejectIfBlank(errors, "typedAnswer", "required.typrd.aswer");
//         }
//
//      }
         
//         if (user.getQuestion1() < 1) errors.rejectValue("question1", "error.quetsion1_blank");
//         if (user.getQuestion2() < 1) errors.rejectValue("question2", "error.quetsion2_blank");
//         if (user.getQuestion3() < 1) errors.rejectValue("question3", "error.quetsion3_blank");
         
//         if (user.getQuestion1() == user.getQuestion2() 
//        		 || user.getQuestion1() == user.getQuestion3() 
//        		 || user.getQuestion3() == user.getQuestion2()) {
//        	 errors.reject("select.distinct.questions", "Choose 3 different questions");
//         }

   }
   
//   public void validateTypedAnswer(Object obj, Errors errors){
//	   final User user = (User) obj;
//	   
//	   if (StringUtils.isBlank(user.getTypedAnswer())){
//		   errors.reject("required.typrd.aswer");
//	   }
//	   
//   }
}
