package com.tea.landlordapp.enums;

public enum CreditRecommendation {

	UNKNOWN("Unknown", '6'), UNAVAILABLE("Unavailable", '0'), ACCEPT("Accept", '1'), LOWACCEPT("LowAccept", '2'),
	CONDITIONAL("Conditional", '3'), DECLINE("Decline", '4'), REFER("Refer", '5');
	
	private final char code;
	private final String label;

	CreditRecommendation(String label, char code) {
		this.code = code;
		this.label = label;
	}

	public char getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static CreditRecommendation getEnum(Character code) {
		if (code == null) return CreditRecommendation.UNKNOWN;
		switch (code) {
		case '0':
			return CreditRecommendation.UNAVAILABLE;
		case '1':
			return CreditRecommendation.ACCEPT;
		case '2':
			return CreditRecommendation.LOWACCEPT;
		case '3':
			return CreditRecommendation.CONDITIONAL;
		case '4':
			return CreditRecommendation.DECLINE;
		case '5':
			return CreditRecommendation.REFER;
		case '6':
		default:
			return CreditRecommendation.UNKNOWN;
		}
	}
	
	public static String getLabel(Character code){
		return getEnum(code).getLabel();
	}
}
