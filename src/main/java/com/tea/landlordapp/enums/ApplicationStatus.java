package com.tea.landlordapp.enums;

public enum ApplicationStatus {
	UNKNOWN("Unknown", '-'), RENTERACCEPTED("RenterAccepted", 'Y'), RENTERDECLINED("RenterDeclined", 'N'), COMPLETED("Completed", 'C'),
	SUBMITTED("Submitted", 'S'), CANCELLED("Cancelled", 'E'), APPROVED("Approved", 'A'), DECLINED("Declined", 'D');
	
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
		case 'D':
			return ApplicationStatus.DECLINED;
		default:
			return ApplicationStatus.UNKNOWN;
		}
	}
	
	public static String getLabel(Character code){
		return getEnum(code).getLabel();
	}
}
