package com.tea.landlordapp.utility;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tea.landlordapp.constant.Globals;

public class StringHelper {
	
	public static String nullToEmpty(String in){
		return StringUtils.isBlank(in) ? "": in;
	}

	public static String makeSimple(String original){
		if (StringUtils.isBlank(original)) return "";
		String pattern = "[^A-Za-z0-9:\\-/\\(\\)\\s\\+,&;]";
		return original.replaceAll(pattern, "_");
	}
	
	public static String makeSimpleLowerCase(String original){
		if (StringUtils.isBlank(original)) return "";
		String pattern = "[^A-Za-z0-9:\\-/\\(\\)\\s\\+,&;]";
		String s = original.replaceAll(pattern, "_");
		s = s.toLowerCase();
		return s;
	}
	
	public static String cleanXml(String input){
		String regex = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";			
		String cleanXml = input.replaceAll(regex, "");
		
		return cleanXml;
	}
	
	public static String concatWithSingleSlash(String s1, String s2){
		String first = s1;
		String second = s2;
		if (s1.endsWith("/")){
			first = s1.substring(0, s1.length() - 1);
		}
		if (s2.startsWith("/")){
			second = s2.substring(1);
		}
		
		return first + "/" + second;
	}
	
	public static String concatenateAndTrim(String first, String second, String separator){
		String s1 = "";
		String s2 = "";
		String ss = "";
		String sr = "";
		if (StringUtils.isNotBlank(first)){
			s1 = first.trim();
		}
		if (StringUtils.isNotBlank(second)){
			s2 = second.trim();
		}
		if (separator != null){
			ss = separator;
		}
		sr = s1 + ss + s2;
		return sr.trim();
	}
	
	public static String makeLegalFileName(String input) {
		      return input.replaceAll("[\\/:\"*?<>|]", "-").replace(' ', '_');
	}

	   public static String buildFullName(String fn, String mi, String ln) {
	      String res = "";
	      if (!StringUtils.isBlank(fn)) {
	         res = fn.trim();
	      }
	      if (!StringUtils.isBlank(mi)) {
	         res = String.format("%s %s", res, mi.trim()).trim();
	      }
	      if (!StringUtils.isBlank(ln)) {
	         res = String.format("%s %s", res, ln.trim()).trim();
	      }

	      return res;
	   }

	   public static String buildAddressLine(String nmbr, String street, String stType, String apt) {
	      String res = "";
	      res = concat(nmbr, street, " ");
	      res = concat(res, stType, " ");
	      res = concat(res, apt, " #");

	      return res;
	   }

	   public static String concat(String s1, String s2, String sep) {
	      if (StringUtils.isBlank(s2)) {
	         if (StringUtils.isBlank(s1)) {
	            return "";
	         } else {
	            return s1.trim();
	         }
	      }

	      final String sa = StringUtils.isBlank(s1) ? "" : s1.trim();
	      final String sb = s2.trim();
	      final String sc = sep == null ? "" : sep;

	      return sa + sc + sb;
	   }

	   public static Date parseDateString(String sDate) throws ParseException {
	      Date res = null;
	      if (!StringUtils.isBlank(sDate)) {
	         final Globals globals = Globals.getInstance();
	         if (sDate.contains("-")) {
	            res = globals.yyyymmdd.parse(sDate);
	         } else {
	        	 res = globals.mmddyyyy.parse(sDate);
	         }
	      }
	      return res;
	   }

	   public static String getMaskedSsn(String ssn) {
	      if (StringUtils.isNotBlank(ssn) && ssn.length() > 4) {
	         return "XXX-XX-" + ssn.substring(ssn.length() - 4, ssn.length());
	      } else {
	         return "";
	      }
	   }

	   public static String getMaskedDate(Date dt) {
	      final String fmt = "MM/dd/'xxxx'";
	      final Format formatter = new SimpleDateFormat(fmt);
	      final String res = formatter.format(dt);
	      return res;
	   }
	   
	   public static String blockLast4(String in){
		   if (StringUtils.isBlank(in)) return "";
		   
		   if (in.length() >= 4){
			  String front = in.substring(0, in.length() - 4);
			  return front + "xxxx";
		   } else {
			   String out = "";
			   for (int i = 0; i < in.length(); i++){
				   out = out + "x";
			   }
			   return out;
		   }
	   }
	   
	   public static String showLast4(String in){
		   if (StringUtils.isBlank(in)) return "";
		   
		   if (in.length() >= 4){
			  String last4 = in.substring(in.length()-4);
			  if (in.length() >= 7)
				  return "xxx" + last4;
			  else {
				  String out = "";
				  for (int i=0; i < in.length() - 4; i++){
					  out = out + "x";
				  }
				  return out + last4;
			  }
		   } else {
			   return in;
		   }
	   }
	   
	   public static String showFirst6(String in){
		   if (StringUtils.isBlank(in)) return "";
		   
		   if (in.length() >= 6){
			  String first6 = in.substring(0, 6);
			  if (in.length() >= 9)
				  return first6 + "xxx";
			  else {
				  String out = "";
				  for (int i=0; i < in.length() - 6; i++){
					  out = out + "x";
				  }
				  return first6 + out;
			  }
		   } else {
			   return in;
		   }
	   }

}
