package com.tea.landlordapp.enums;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EmploymentStatus {

		UNKNOWN("Unknown",""), 
		EMPLOYED("Employed","E"), 
		STUDENT("Student","F"), 
		SELFEMPLOYED("Self Employed","S"), 
		NOTEMPLOYED("Not Employed","U");
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());
	
	private final String code;
	private final String label;
	
	EmploymentStatus(String label, String code){
		this.code = code;
		this.label = label;
	}
	
	public String getCode(){
		return code;
	}
	
	public Character getCodeAsCharacter(){
		return code.charAt(0);
	}
	
	public String getLabel() {
		return label;
	}
	
	public static EmploymentStatus getEnum(char code){
		String sCode = Character.toString(code);
		return getEnum(sCode);
	}
	
	public static EmploymentStatus getEnum(String code){
			
		if (StringUtils.equals(code, "E"))
			return EmploymentStatus.EMPLOYED;
		else if (StringUtils.equals(code, "F"))
			return EmploymentStatus.STUDENT;
		else if (StringUtils.equals(code, "S"))
			return EmploymentStatus.SELFEMPLOYED;
		else if (StringUtils.equals(code, "U"))
			return EmploymentStatus.NOTEMPLOYED;
		else return EmploymentStatus.UNKNOWN;
	}
	
	public static Map<String,String> getEmploymentTypeOptions(){
		Map<String,String> map = new TreeMap<String, String>();
	      map.put(EmploymentStatus.UNKNOWN.getCode(), "(Select One)");
	      map.put(EmploymentStatus.EMPLOYED.getCode(), EmploymentStatus.EMPLOYED.getLabel());
	      map.put(EmploymentStatus.STUDENT.getCode(), EmploymentStatus.STUDENT.getLabel());
	      map.put(EmploymentStatus.SELFEMPLOYED.getCode(), EmploymentStatus.SELFEMPLOYED.getLabel());
	      map.put(EmploymentStatus.NOTEMPLOYED.getCode(), EmploymentStatus.NOTEMPLOYED.getLabel());
	      
	      return map;
	}
	
	public static Map<String,String> getEmploymentTypeOptions2(){
		Map<String,String> map = new TreeMap<String, String>();
	      map.put(EmploymentStatus.UNKNOWN.getCode(), "(Select One)");
	      map.put(EmploymentStatus.EMPLOYED.getCode(), EmploymentStatus.EMPLOYED.getLabel());
	      map.put(EmploymentStatus.SELFEMPLOYED.getCode(), EmploymentStatus.SELFEMPLOYED.getLabel());
	      
	      return map;
	}
	
	public static Map<String,String> getEmploymentTypeOptionsEmail(){
		Map<String,String> map = new TreeMap<String, String>();
	      map.put(EmploymentStatus.UNKNOWN.getCode(), "(Select One)");
	      map.put(EmploymentStatus.EMPLOYED.getCode(), EmploymentStatus.EMPLOYED.getLabel());
	      map.put(EmploymentStatus.STUDENT.getCode(), EmploymentStatus.STUDENT.getLabel());
	      map.put(EmploymentStatus.SELFEMPLOYED.getCode(), EmploymentStatus.SELFEMPLOYED.getLabel());
	      map.put(EmploymentStatus.NOTEMPLOYED.getCode(), EmploymentStatus.NOTEMPLOYED.getLabel());
	      
	      return map;
	}
	
	public static Map<String,String> getEmploymentTypeOptionsEmail2(){
		Map<String,String> map = new TreeMap<String, String>();
	      map.put(EmploymentStatus.UNKNOWN.getCode(), "(Select One)");
	      map.put(EmploymentStatus.EMPLOYED.getCode(), EmploymentStatus.EMPLOYED.getLabel());
	      map.put(EmploymentStatus.STUDENT.getCode(), EmploymentStatus.STUDENT.getLabel());
	      map.put(EmploymentStatus.SELFEMPLOYED.getCode(), EmploymentStatus.SELFEMPLOYED.getLabel());
	      
	      return map;
	}


}
