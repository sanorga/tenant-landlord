package com.tea.landlordapp.enums;

public enum ApplicationState {

		UNKNOWN("Unknown", '-'), INPROGRESS("InProgress", 'P'), COMPLETED("Completed", 'C'),
		APPROVED("Approved", 'A'), DECLINED("Declined", 'D'), ALL("All",'T');
		
		private final char code;
		private final String label;

		ApplicationState(String label, char code) {
			this.code = code;
			this.label = label;
		}

		public char getCode() {
			return code;
		}

		public String getLabel() {
			return label;
		}

		public static ApplicationState getEnum(Character code) {
			if (code == null) return ApplicationState.UNKNOWN;
			switch (code) {
			case 'P':
				return ApplicationState.INPROGRESS;
			case 'C':
				return ApplicationState.COMPLETED;
			case 'A':
				return ApplicationState.APPROVED;
			case 'D':
				return ApplicationState.DECLINED;
			case 'T':
				return ApplicationState.ALL;
			default:
				return ApplicationState.UNKNOWN;
			}
		}
		
		public static String getLabel(Character code){
			return getEnum(code).getLabel();
		}
}
