package com.tea.landlordapp.enums;

import org.apache.commons.lang.StringUtils;

public enum UserRole {
	NoRole(""), 
	SystemAdmin("SA"), 
	PartnerAdmin("PA"), 
	Investigator("IN"), 
	CustomerServiceRep("CS"), 
	ClientAdmin("CA"), 
	ClientUser("CU"), 
	BoardDirector("BD"), 
	ExternalCustomerServiceRep("EC"), 
	NoPrivileges("XX"), 
	PropertyManager("PM"), 
	SuperCS("SC"), 
	Administrator("JA"), 
	Compliance("CM"), 
	Automation("AI"), 
	Accountant("AC"), 
	AccountingSupervisor("AS");

	private String code;

	private UserRole(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static UserRole getEnum(String role) {
		UserRole result = UserRole.NoRole;
		for (UserRole item : UserRole.values()) {
			if (StringUtils.equalsIgnoreCase(role, item.getCode())) {
				result = item;
				break;
			}
		}
		return result;
	}

}
