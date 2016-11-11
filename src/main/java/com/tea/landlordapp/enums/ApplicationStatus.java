package com.tea.landlordapp.enums;

import org.apache.commons.lang.StringUtils;

public enum ApplicationStatus {
	UNKNOWN("Unknown", '-'), RENTERACCEPTED("RenterAccepted", 'Y'), RENTERDECLINED("RenterDeclined", 'N'), COMPLETED("Completed", 'C'),
	SUBMITTED("Submitted", 'S'), CANCELLED("Cancelled", 'E'), APPROVED("Approved", 'A'), APPROVEDWITHCONDITION("ApprovedWithCondition",'W'),
	DECLINED("Declined", 'D');
	
	private final char code;
	private final String label;

	ApplicationStatus(String label, char code) {
		this.code = code;
		this.label = label;
	}

	public char getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static ApplicationStatus getEnum(Character code) {
		if (code == null) return ApplicationStatus.UNKNOWN;
		switch (code) {
		case 'Y':
			return ApplicationStatus.RENTERACCEPTED;
		case 'N':
			return ApplicationStatus.RENTERDECLINED;
		case 'C':
			return ApplicationStatus.COMPLETED;
		case 'S':
			return ApplicationStatus.SUBMITTED;
		case 'E':
			return ApplicationStatus.CANCELLED;
		case 'A':
			return ApplicationStatus.APPROVED;
		case 'W':
			return ApplicationStatus.APPROVEDWITHCONDITION;
		case 'D':
			return ApplicationStatus.DECLINED;
		default:
			return ApplicationStatus.UNKNOWN;
		}
	}
	
	public static String getLabel(Character code){
		return getEnum(code).getLabel();
	}
	
	public static char getCode(String label) {
		
		if (StringUtils.equals(label, ApplicationStatus.APPROVED.getLabel()))
			return ApplicationStatus.APPROVED.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.COMPLETED.getLabel()))
			return ApplicationStatus.COMPLETED.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.DECLINED.getLabel()))
			return ApplicationStatus.DECLINED.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.SUBMITTED.getLabel()))
			return ApplicationStatus.SUBMITTED.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.APPROVEDWITHCONDITION.getLabel()))
			return ApplicationStatus.APPROVEDWITHCONDITION.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.RENTERACCEPTED.getLabel()))
			return ApplicationStatus.RENTERACCEPTED.getCode();
		
		if (StringUtils.equals(label, ApplicationStatus.RENTERDECLINED.getLabel()))
			return ApplicationStatus.RENTERDECLINED.getCode();

		else return '-';
		
	}
	
}
