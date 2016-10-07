package com.tea.landlordapp.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tea.landlordapp.domain.Capability;
import com.tea.landlordapp.domain.Role;
import com.tea.landlordapp.domain.User;

public class TeaUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3880768881050417317L;
	
	private Collection<? extends GrantedAuthority> authorities;
	private String openIdIdentifier;
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Integer userId;
	private boolean mfaDesired;
	private boolean mfaRequired;
	private boolean secondFactorValid = false;
//	private int daysToExpiration;
	private String primaryRole;
	private boolean partnerUser;
	private boolean clientUser;
	private boolean systemAdminUser;
	private boolean questions;

	public TeaUserDetails(Collection<? extends GrantedAuthority> authorities,
			String openIdIdentifier,
			String password, String username, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired,
			boolean enabled, Integer userId, boolean mfaDesired,
			boolean mfaRequired, boolean secondFactorValid,
			String primaryRole, boolean partnerUser,
			boolean clientUser, boolean systemAdminUser, boolean questions) {
		super();
		this.authorities = authorities;
		this.openIdIdentifier = openIdIdentifier;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.userId = userId;
		this.mfaDesired = mfaDesired;
		this.mfaRequired = mfaRequired;
		this.secondFactorValid = secondFactorValid;
//		this.daysToExpiration = daysToExpiration;
		this.primaryRole = primaryRole;
		this.partnerUser = partnerUser;
		this.clientUser = clientUser;
		this.systemAdminUser = systemAdminUser;
		this.questions = questions;
	}

//	public TeaUserDetails(Collection<? extends GrantedAuthority> authorities,
//			String password, String username, boolean accountNonExpired,
//			boolean accountNonLocked, boolean credentialsNonExpired,
//			boolean enabled, Integer userId, boolean mfaDesired,
//			boolean mfaRequired, boolean secondFactorValid, int daysToExpiration) {
//		super();
//		this.authorities = authorities;
//		this.password = password;
//		this.username = username;
//		this.accountNonExpired = accountNonExpired;
//		this.accountNonLocked = accountNonLocked;
//		this.credentialsNonExpired = credentialsNonExpired;
//		this.enabled = enabled;
//		this.userId = userId;
//		this.mfaDesired = mfaDesired;
//		this.mfaRequired = mfaRequired;
//		this.secondFactorValid = secondFactorValid;
//		this.daysToExpiration = daysToExpiration;
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public String getOpenIdIdentifier() {
		return openIdIdentifier;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public Integer getUserId(){
		return userId;
	}

	public boolean isMfaDesired() {
		return mfaDesired;
	}

	public boolean isMfaRequired() {
		return mfaRequired;
	}

	public boolean isSecondFactorValid() {
		return secondFactorValid;
	}

	public void setSecondFactorValid(boolean secondFactorValid) {
		this.secondFactorValid = secondFactorValid;
	}

//	public int getDaysToExpiration() {
//		return daysToExpiration;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrimaryRole() {
		return primaryRole;
	}

	public boolean isPartnerUser() {
		return partnerUser;
	}

	public boolean isClientUser() {
		return clientUser;
	}

	public boolean isSystemAdminUser() {
		return systemAdminUser;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + (clientUser ? 1231 : 1237);
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + (mfaDesired ? 1231 : 1237);
		result = prime * result + (mfaRequired ? 1231 : 1237);
		result = prime * result + ((openIdIdentifier == null) ? 0 : openIdIdentifier.hashCode());
		result = prime * result + (partnerUser ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((primaryRole == null) ? 0 : primaryRole.hashCode());
		result = prime * result + (questions ? 1231 : 1237);
		result = prime * result + (secondFactorValid ? 1231 : 1237);
		result = prime * result + (systemAdminUser ? 1231 : 1237);
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeaUserDetails other = (TeaUserDetails) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (clientUser != other.clientUser)
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (enabled != other.enabled)
			return false;
		if (mfaDesired != other.mfaDesired)
			return false;
		if (mfaRequired != other.mfaRequired)
			return false;
		if (openIdIdentifier == null) {
			if (other.openIdIdentifier != null)
				return false;
		} else if (!openIdIdentifier.equals(other.openIdIdentifier))
			return false;
		if (partnerUser != other.partnerUser)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (primaryRole == null) {
			if (other.primaryRole != null)
				return false;
		} else if (!primaryRole.equals(other.primaryRole))
			return false;
		if (questions != other.questions)
			return false;
		if (secondFactorValid != other.secondFactorValid)
			return false;
		if (systemAdminUser != other.systemAdminUser)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public boolean isQuestions() {
		return questions;
	}


}
