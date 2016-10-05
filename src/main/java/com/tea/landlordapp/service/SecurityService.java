package com.tea.landlordapp.service;

import java.io.Serializable;

import org.springframework.security.core.Authentication;

import com.tea.landlordapp.domain.PasswordPolicy;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.TeaUserDetails;

public interface SecurityService extends Serializable {

	public void updatePassword(User user, String newPassword);

	public boolean passwordIsPolicyCompliant(String password, PasswordPolicy policy);

	public boolean passwordInRecentHistory(Integer userId, String password,
			PasswordPolicy policy);

	public boolean checkPassword(User user, String pw);

	public User getAuthenticatedUser();

	public TeaUserDetails getUserDetails();

	public User getAuthenticatedUser(Authentication authentication);

	public void updateCurrentAuthorities(Boolean mfaValid);

}
