package com.tea.landlordapp.service;

import java.io.Serializable;

public interface AdminAppService extends Serializable {

	
	public boolean userExists(String username);

	public boolean sendPasswordResetEmail(String username);
}
