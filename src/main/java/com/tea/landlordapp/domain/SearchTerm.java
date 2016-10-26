package com.tea.landlordapp.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class SearchTerm {

   private static final String REPORT_NUMBER = "a.id";
   private static final String CLIENT_ID = "a.client.id";
   private static final String SUBMISSION_DATE = "a.submissionDate";
   private static final String COMPLETION_DATE = "a.completionDate";

   @SuppressWarnings("unused")
   private static final String APPLICATION_STATUS = "a.statusCode";
   private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

   private int id;
   private String criteria;
   private String modifier;
   private String value;
   private String type;
   private CriteriaType criteriaType;

   public enum CriteriaType {
      STRING, DATE, INT, UNKNOWN;
   }

   public int getId() {
      return id;
   };

   public void setId(int id) {
      this.id = id;
   };

   public String getCriteria() {
      return criteria;
   }

   public void setCriteria(String criteria) {
      this.criteria = criteria;
   }

   public String getModifier() {
      return modifier;
   }

   public void setModifier(String modifier) {
      this.modifier = modifier;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public Object getValue() throws ParseException {
      if ((StringUtils.equals(criteria, REPORT_NUMBER)) || (StringUtils.equals(criteria, CLIENT_ID))) {
         return new Integer(value);
      }

      if ((StringUtils.equals(criteria, SUBMISSION_DATE)) || (StringUtils.equals(criteria, COMPLETION_DATE))) {
         return df.parse(value);
      }

      // for all String criteria's
      if (StringUtils.equals(modifier, "like")) return "%" + value + "%";

      return value;
   }

   public Object getValueByType() throws ParseException {
      switch (criteriaType) {
         case INT :
            return new Integer(value);
         case STRING :
            return value.toString();
         case DATE :
            return df.parse(value);
         default :
            return null;
      }

   }

   public String getValueAsString() throws ParseException {
      if ((StringUtils.equals(criteria, REPORT_NUMBER)) || (StringUtils.equals(criteria, CLIENT_ID))) {
         return value;
      }

      if (StringUtils.containsIgnoreCase(criteria, "date")) {
         Date date = df.parse(value);
         return "'" + df.format(date).toString() + "'";
      }

      if (StringUtils.containsIgnoreCase(criteria, "status")) {
         if (value.equalsIgnoreCase("saved"))
            return "'S'";
         else if (value.equalsIgnoreCase("complete"))
            return "'C'";
         else if (value.equalsIgnoreCase("in process"))
            return "'P'";
         else if (value.equalsIgnoreCase("new"))
            return "'N'";
         else if (value.equalsIgnoreCase("new"))
            return "'N'";

         else if (value.equalsIgnoreCase("open"))
            return "'O'";
         else if (value.equalsIgnoreCase("Paid"))
            return "'C'";
         else if (value.equalsIgnoreCase("Partially Paid")) return "'P'";
      }

      // for all String criteria's
      value = StringEscapeUtils.escapeSql(value);
      if (StringUtils.equals(modifier, "like")) return "'%" + value.toUpperCase() + "%'";

      return "'" + value.toUpperCase() + "'".toUpperCase();
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
      try {
         this.criteriaType = CriteriaType.valueOf(StringUtils.upperCase(type));
      } catch (IllegalArgumentException e) {
         this.criteriaType = CriteriaType.UNKNOWN;
      }
   }

   public CriteriaType getCriteriaType() {
      return criteriaType;
   }

   public void setCriteriaType(CriteriaType criteriaType) {
      this.criteriaType = criteriaType;
      this.type = StringUtils.capitalize(StringUtils.lowerCase(criteriaType.toString()));
   }
}
