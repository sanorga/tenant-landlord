package com.tea.landlordapp.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.tea.landlordapp.constant.Globals;

@SuppressWarnings("deprecation")
public class TeaValidationUtils extends ValidationUtils {

   protected static final Log logger = LogFactory.getLog("TeaValidationUtils");

   private static Pattern phone10 = Pattern.compile("\\({1}[0-9]{3}\\)\\s{1}[0-9]{3}-[0-9]{4}");
   private static Pattern ssn = Pattern.compile("[0-9]{3}-?[0-9]{2}-?[0-9]{4}");
   private static Pattern amount = Pattern.compile("[0-9,]{1,10}(\\.[0-9]{1,2})?");
   // private static Pattern number3 = Pattern.compile("[0-9]{0,3}");
   private static Pattern unsignedInteger = Pattern.compile("^([0-9]{1,})$");

   private static SimpleDateFormat dateMMDDYYYY = new SimpleDateFormat(Globals.DATE_FORMAT);
   private static EmailValidator emailValidator = EmailValidator.getInstance();

   private static String defaultErrorMessage = "Validation Error: Message not found.";

   public static void rejectIfBlank(Errors errors, String field, String errorCode) {
      rejectIfBlank(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfBlank(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfBlank(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfBlank(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");
      final String value = (String) errors.getFieldValue(field);

      if (StringUtils.isBlank(value) && blankOk) {
         return;
      }

      rejectIfEmptyOrWhitespace(errors, field, errorCode, args);
   }

   // Check to see date2 (from field2) is greater than date1 (from field1)
   public static void rejectIfNotGreaterDate(Errors errors, String field1, String field2, String errorCode) {
      rejectIfNotGreaterDate(errors, field1, field2, errorCode, new Object[]{""});
   }

   // Check date1(field1) is empty or not
   public static void rejectIfNotGreaterDate(Errors errors, String field1, String field2, String errorCode, boolean blankOk) {
      final String field1Str = errors.getFieldValue(field1).toString();
      if (blankOk && StringUtils.isBlank(field1Str)) {
         return;
      } else {
         rejectIfNotGreaterDate(errors, field1, field2, errorCode, new Object[]{""});
      }
   }

   public static void rejectIfNotGreaterDate(Errors errors, String field1, String field2, String errorCode, Object[] args) {
      Assert.notNull(errors, "Errors object must not be null");

      try {
         final Date value1 = dateMMDDYYYY.parse((String) errors.getFieldValue(field1));

         Date value2;
         if (StringUtils.isBlank(field2)) {
            value2 = new Date();
            value2 = DateUtils.setHours(value2, 0);
            value2 = DateUtils.setMinutes(value2, 0);
            value2 = DateUtils.setSeconds(value2, 0);
            value2 = DateUtils.setMilliseconds(value2, 0);
         } else {
            value2 = dateMMDDYYYY.parse((String) errors.getFieldValue(field2));
         }

         if (!ObjectUtils.equals(value1, null) && !ObjectUtils.equals(value2, null) && value1.before(value2)) {
            errors.reject(errorCode, args, defaultErrorMessage);
         }
      } catch (final ParseException ex) {
         errors.reject(errorCode, args, defaultErrorMessage);
      }
   }

   // Check to see date2 (from field2) is lesser than date1 (from field1)
   public static void rejectIfNotLesserDate(Errors errors, String field1, String field2, String errorCode) {
      rejectIfNotLesserDate(errors, field1, field2, errorCode, new Object[]{""});
   }

   public static void rejectIfNotLesserDate(Errors errors, String field1, String field2, String errorCode, Object[] args) {
      Assert.notNull(errors, "Errors object must not be null");
      
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR,  -18);
      Date dateEighteenYearsAgo = cal.getTime();
            
      try {
         final Date value1 = dateMMDDYYYY.parse((String) errors.getFieldValue(field1));

         Date value2;
         if (StringUtils.isBlank(field2)) {
            value2 = new Date();
         }
         else if (StringUtils.equals(field2,"eighteenYearsAgo")) {
        		value2 = dateEighteenYearsAgo;
        		}
        		else {
        		value2 = dateMMDDYYYY.parse((String) errors.getFieldValue(field2));
        		}
         if (!ObjectUtils.equals(value1, null) && !ObjectUtils.equals(value2, null) && value2.before(value1)) {
            errors.reject(errorCode, args, defaultErrorMessage);
         }
      } catch (final ParseException ex) {
         errors.reject(errorCode, args, defaultErrorMessage);
      }
   }

   // Amount Validation
   public static void rejectIfNotValidAmount(Errors errors, String field, String errorCode) {
      rejectIfNotValidAmount(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidAmount(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidAmount(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidAmount(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");

      final Object value = errors.getFieldValue(field);

      if (value instanceof String) {
         if (StringUtils.isBlank((String) value) && blankOk) {
            return;
         }

         if (!amount.matcher((String) value).matches()) {
            errors.rejectValue(field, errorCode, args, defaultErrorMessage);
            return;
         }
      } else if (value instanceof Double) {
         if ((Double) value < 0 || (Double) value > 250) {
            errors.rejectValue(field, errorCode, args, defaultErrorMessage);
            return;
         }
      }
   }

   public static void rejectIfNotValidAmount2(Errors errors, String field, String errorCode) {
      rejectIfNotValidAmount(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidAmount2(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidAmount2(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidAmount2(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");

      final Object value = errors.getFieldValue(field);

      if (value instanceof String) {
         if (StringUtils.isBlank((String) value) && blankOk) {
            return;
         }

         if (!amount.matcher((String) value).matches()) {
            errors.rejectValue(field, errorCode, args, defaultErrorMessage);
            return;
         }
      } else if (value instanceof Double) {
         if ((Double) value < 0) {
            errors.rejectValue(field, errorCode, args, defaultErrorMessage);
            return;
         }
      }
   }

   public static void rejectIfNotValidDate(Errors errors, String field, String errorCode) {
      rejectIfNotValidDate(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidDate(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidDate(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidDate(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");
      final String strValue = (String) errors.getFieldValue(field);

      if (StringUtils.isBlank(strValue) && blankOk) {
         return;
      }

      if (strValue.length() != 10) {
         errors.rejectValue(field, errorCode, args, defaultErrorMessage);
      }

      try {
         dateMMDDYYYY.parse((String) errors.getFieldValue(field));
      } catch (final ParseException ex) {
         // errors.rejectValue(field, errorCode, args, defaultErrorMessage);
      }
   }

   public static void rejectIfNotValidDropDownOption(Errors errors, String field, String errorCode) {
      rejectIfNotValidDropDownOption(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidDropDownOption(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidDropDownOption(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidDropDownOption(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");

      final Object fieldValue = errors.getFieldValue(field);
      if (fieldValue instanceof Integer) {
         if (((fieldValue == null) || ObjectUtils.equals(fieldValue, -1)) && !blankOk) {
            errors.rejectValue(field, errorCode, args, defaultErrorMessage);
            return;
         }
         return;
      }
      
//      Object val = errors.getFieldValue(field);
      try {
    	  String sVal = (String) fieldValue;
    	  if (StringUtils.isBlank(sVal) && blankOk) return;
      } catch (Exception e) {
    	  // do nothing
      }

      rejectIfEmptyOrWhitespace(errors, field, errorCode, args);
   }

   public static void rejectIfNotValidEmail(Errors errors, String field, String errorCode) {
      rejectIfNotValidEmail(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidEmail(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidEmail(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidEmail(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");
      final String value = (String) errors.getFieldValue(field);

      if (StringUtils.isBlank(value) && blankOk) {
         return;
      }

      if (!emailValidator.isValid(value)) {
         errors.rejectValue(field, errorCode, args, defaultErrorMessage);
         return;
      }
   }

   // Number Validation
   public static void rejectIfNotValidNumber(Errors errors, String field, String errorCode) {
      rejectIfNotValidNumber(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidNumber(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidNumber(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidNumber(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");

      if (ObjectUtils.equals(errors.getFieldValue(field), null)) {
         return;
      }

      final String value = errors.getFieldValue(field).toString();

      if (StringUtils.isBlank(value) && blankOk) {
         return;
      }

      if (!unsignedInteger.matcher(value).matches()) {
         errors.rejectValue(field, errorCode, args, defaultErrorMessage);
         return;
      }
   }

   public static void rejectIfNotValidPhone(Errors errors, String field, String errorCode) {
      rejectIfNotValidPhone(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidPhone(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidPhone(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidPhone(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");
      final String value = (String) errors.getFieldValue(field);

      if (StringUtils.isBlank(value) && blankOk) {
         return;
      }

      if (!phone10.matcher(value).matches()) {
         errors.rejectValue(field, errorCode, args, defaultErrorMessage);
      }
   }

   public static void rejectIfNotValidSsn(Errors errors, String field, String errorCode) {
      rejectIfNotValidSsn(errors, field, errorCode, new Object[]{""}, false);
   }

   public static void rejectIfNotValidSsn(Errors errors, String field, String errorCode, boolean blankOk) {
      rejectIfNotValidSsn(errors, field, errorCode, new Object[]{""}, blankOk);
   }

   public static void rejectIfNotValidSsn(Errors errors, String field, String errorCode, Object[] args, boolean blankOk) {
      Assert.notNull(errors, "Errors object must not be null");
      final String value = (String) errors.getFieldValue(field);

      if (StringUtils.isBlank(value) && blankOk) {
         return;
      }

      if (!ssn.matcher(value).matches()) {
         errors.rejectValue(field, errorCode, args, defaultErrorMessage);
         return;
      }
   }

   public static void rejectIfNull(Errors errors, String field, String errorCode, Object[] args) {
      Assert.notNull(errors, "Errors object must not be null");

      ValidationUtils.rejectIfEmpty(errors, field, errorCode);
   }
}