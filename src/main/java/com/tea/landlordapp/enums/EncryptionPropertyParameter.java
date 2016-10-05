package com.tea.landlordapp.enums;

public enum EncryptionPropertyParameter {
	DEFAULT_MASTER_KEY_ALIAS("default.master.key.alias");
	
	private final String label;

	EncryptionPropertyParameter(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
