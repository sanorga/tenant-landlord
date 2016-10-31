package com.tea.landlordapp.dto;

import java.io.Serializable;

public class LookupZipcodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531996279329649063L;
	private String city;
	private String state;

	public LookupZipcodeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LookupZipcodeDto(String city, String state) {
		this.city = city;
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
