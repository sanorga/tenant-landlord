package com.tea.landlordapp.dto;

import org.springframework.security.core.GrantedAuthority;

public class TeaAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2263716406436303490L;
	private String authority;
	
	public TeaAuthority() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaAuthority(String authority) {
		super();
		this.setAuthority(authority);
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
