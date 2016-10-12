package com.tea.landlordapp.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum TransUnionApiParameter {
	URL("apiurl"),PARTNER_ID("partner.id"), KEY("key"), IS_LIVE("live");
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());

	private final String label;

	TransUnionApiParameter(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
