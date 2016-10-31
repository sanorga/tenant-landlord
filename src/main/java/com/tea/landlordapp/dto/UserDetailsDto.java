package com.tea.landlordapp.dto;

import java.io.Serializable;

public class UserDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2564262599683178575L;
	
	private Integer id;
	private String mfaId;
	private Boolean mfaEnabled;
	private Boolean mfaRequired;

	public UserDetailsDto(Integer id, String mfaId, Boolean mfaEnabled,
			Boolean mfaRequired) {
		super();
		this.id = id;
		this.mfaId = mfaId;
		this.mfaEnabled = mfaEnabled;
		this.mfaRequired = mfaRequired;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMfaId() {
		return mfaId;
	}

	public void setMfaId(String mfaId) {
		this.mfaId = mfaId;
	}

	public Boolean getMfaEnabled() {
		return mfaEnabled;
	}

	public void setMfaEnabled(Boolean mfaEnabled) {
		this.mfaEnabled = mfaEnabled;
	}

	public Boolean getMfaRequired() {
		return mfaRequired;
	}

	public void setMfaRequired(Boolean mfaRequired) {
		this.mfaRequired = mfaRequired;
	}

}
