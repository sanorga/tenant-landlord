package com.tea.landlordapp.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import com.tea.landlordapp.enums.SocialAcctService;
import com.tea.landlordapp.utility.EncryptionService;

@Entity
@Table(name = "user")
	public class User extends AuditableEntity implements UserDetails {

		private static final long serialVersionUID = -1L;
	
		private String openIdIdentifier = null;
		private char status = 'A';
		private char accountType = 'L';

		private String username;
		private String passwordHash;
		private String rePassword;
	    private SocialAcctService socialAcctType;
		private String firstName;
		private String lastName;
		private String address;
		private String address2;
		private String city;
		private String state;
		private String zipcode;
		private String country;
		private String phone;
		private String fax;
		private String oldPassword;
		private String newPassword;

		private Timestamp lastLogin;
		private Role role;

		private Integer randomNo;


		private Integer count = 0;
		
		private Integer loginFailCount = 0;
		private Date lockoutUntil;

		private Integer appCount;
		private Integer closedAppCount;
		private Integer pendingAppCount;
		private String maxTime;
		private String minTime;

		private String avgTime;
		private char pendingWarning;
		
//		private Set<Property> authorizedProperties;
//		private java.util.List<Integer> authorizedPropertyIds;
		
		// Support for MFA
		private boolean enableMFA = false;

		// Accepts Service Agreement and Terms and Conditions.
		private boolean acceptSATC = false;
				
		@Column(name = "openid_identifier")
		public String getOpenIdIdentifier() {
			return openIdIdentifier;
		}

		public void setOpenIdIdentifier(String openIdIdentifier) {
			this.openIdIdentifier = openIdIdentifier;
		}
		
		@Column(name = "email_id")
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Column(name = "password")
		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String password) {
			this.passwordHash = password;
		}

		@Override
		@Transient
		public String getPassword() {
			return getPasswordHash();
		}

		public void setPassword(String pw) {
			if (!passwordMatches(pw.trim()))
				setPasswordHash(EncryptionService.hash(pw.trim()));
		}

		@Column(name = "social_account_type")
		public SocialAcctService getSocialAcctType() {
			return socialAcctType;
		}

		public void setSocialAcctType(SocialAcctService socialAcctType) {
			this.socialAcctType = socialAcctType;
		}

		@Transient
		public boolean isNormalRegistration() {
	        return socialAcctType == null;
	    }
	 
		@Transient
	    public boolean isSocialSignIn() {
	        return socialAcctType != null;
	    }
	    
		@ManyToOne
		@JoinColumn(name = "role_id")
		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		@Column(name = "first_name")
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		@Column(name = "last_name")
		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		@Transient
		public String getFullName(){
			return String.format("%s %s", (StringUtils.isBlank(this.firstName) ? "": this.firstName), 
					(StringUtils.isBlank(this.lastName) ? "": this.lastName)).trim();
		}

		@Column(name = "address")
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		@Column(name = "address2")
		public String getAddress2() {
			return address2;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		@Column(name = "city")
		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		@Column(name = "state")
		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		@Column(name = "zipcode")
		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		@Column(name = "country")
		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		@Column(name = "phone")
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Column(name = "fax")
		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		@Column(name = "status")
		public char getStatus() {
			return status;
		}

		public void setStatus(char status) {
			this.status = status;
		}

		@Column(name = "account_type")
		public char getAccountType() {
			return accountType;
		}

		public void setAccountType(char accountType) {
			this.accountType = accountType;
		}

		@Column(name = "last_login")
		public Timestamp getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(Timestamp lastLogin) {
			this.lastLogin = lastLogin;
		}

		@Transient
		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}

		@Transient
		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		@Transient
		public String getRePassword() {
			return rePassword;
		}

		public void setRePassword(String rePassword) {
			this.rePassword = rePassword;
		}

		@Transient
		public boolean isActive() {
			return ('A' == getStatus());
		}

		@Transient
		public boolean isLandlordAccount() {
			return ('L' == getAccountType());
		}

		
		@Transient
		public boolean isChangePassword() {
			return ('X' == getStatus());
		}
		
		public void setChangePassword() {
			setStatus('X');
		}

		@Transient
		public boolean isLocked() {
			return ('L' == getStatus());
		}


		@Transient
		public Integer getRandomNo() {
			return randomNo;
		}

		public void setRandomNo(Integer randomNo) {
			this.randomNo = randomNo;
		}

		@Column(name = "count")
		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		@Transient
		public Integer getClosedAppCount() {
			return closedAppCount;
		}

		public void setClosedAppCount(Integer closedAppCount) {
			this.closedAppCount = closedAppCount;
		}

		@Transient
		public Integer getPendingAppCount() {
			return pendingAppCount;
		}

		public void setPendingAppCount(Integer pendingAppCount) {
			this.pendingAppCount = pendingAppCount;
		}

		@Transient
		public Integer getAppCount() {
			return appCount;
		}

		public void setAppCount(Integer appCount) {
			this.appCount = appCount;
		}

		@Transient
		public String getAvgTime() {
			return avgTime;
		}

		public void setAvgTime(String avgTime) {
			this.avgTime = avgTime;
		}

		@Transient
		public String getMaxTime() {
			return maxTime;
		}

		public void setMaxTime(String maxTime) {
			this.maxTime = maxTime;
		}

		@Transient
		public String getMinTime() {
			return minTime;
		}

		public void setMinTime(String minTime) {
			this.minTime = minTime;
		}

		@Transient
		public char getPendingWarning() {
			return pendingWarning;
		}

		public void setPendingWarning(char pendingWarning) {
			this.pendingWarning = pendingWarning;
		}

		@Transient
		@Override
		public Collection<Permission> getAuthorities() {
			ArrayList<Permission> capList = new ArrayList<Permission>();
			HashSet<String> perms = new HashSet<String>();
			Role role = getRole();
			if (perms.add(role.getRole())) {
				capList.add(new Permission(role.getRole()));
			}

//			List<Capability> caps = role.getCapabilities();
			for (Capability cap : role.getCapabilities()) {
				if (perms.add(cap.getRequestId()))
					capList.add(new Permission(cap.getRequestId()));
			}

			return capList;
		}
//
//		@ManyToMany(fetch = FetchType.EAGER)
//		@Fetch(FetchMode.SUBSELECT)
//		@JoinTable(name = "property_authorization", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "property_id", referencedColumnName = "id"))
//		public Set<Property> getAuthorizedProperties() {
//			return authorizedProperties;
//		}
//
//		public void setAuthorizedProperties(Set<Property> authorizedProperties) {
//			this.authorizedProperties = authorizedProperties;
//			authorizedPropertyIds = new ArrayList<Integer>();
//			if (authorizedProperties != null) {
//				for (Property p : authorizedProperties) {
//					authorizedPropertyIds.add(p.getId());
//				}
//			}
//		}
//		
//		@Transient
//		public java.util.List<Integer> getAuthorizedPropertyIds() {
//			return authorizedPropertyIds;
//		}
//
//		public void setAuthorizedPropertyIds(java.util.List<Integer> authorizedPropertyIds) {
//			this.authorizedPropertyIds = authorizedPropertyIds;
//		}
//			
		@Transient
		@Override
		public boolean isAccountNonExpired() {
			return (getStatus() != 'I');
		}

		@Transient
		@Override
		public boolean isAccountNonLocked() {
			return (getStatus() != 'L');
		}

		@Transient
		@Override
		public boolean isCredentialsNonExpired() {
			return (getStatus() != 'X');
		}

		@Transient
		@Override
		public boolean isEnabled() {
			return (getStatus() == 'A');
		}

		@Transient
		public String getPrimaryRoleCode() {
			return getRole().getRole();
		}

		@Transient
		public boolean hasRole(String role) {
			boolean res = false;
			if (role == null || StringUtils.isBlank(role)) return false;
			for (Permission perm : getAuthorities()) {
				if (StringUtils.equalsIgnoreCase(perm.getAuthority(), role))
					return true;
			}
			return res;
		}

		@Transient
		public boolean hasAnyAuthority(String[] authorities) {
			if (authorities == null || authorities.length < 1)
				return false;

			for (String authority : authorities) {

				if (hasRole(authority)) return true;
			}
			return false;
		}

		@Transient
		public boolean hasAllAuthorities(String[] authorities) {
			boolean res = true;
			
			if (authorities == null || authorities.length < 1)
				return false;

			for (String authority : authorities) {
				res = res && hasRole(authority);
			}
			return res;
		}



		@Transient
		public boolean passwordMatches(String pw) {
			String myPw = getPassword();

			return EncryptionService.hashMatches(pw, myPw);
		}



		@Column(name = "enable_mfa")
		public boolean isEnableMFA() {
			return enableMFA;
		}

		public void setEnableMFA(boolean enableMFA) {
			this.enableMFA = enableMFA;
		}

		@Column(name = "accept_satc")
		public boolean isAcceptSATC() {
			return acceptSATC;
		}

		public void setAcceptSATC(boolean acceptSATC) {
			this.acceptSATC = acceptSATC;
		}

		@Column(name = "login_fail_count")
		public Integer getLoginFailCount() {
			return loginFailCount;
		}

		public void setLoginFailCount(Integer loginFailCount) {
			this.loginFailCount = loginFailCount;
		}

		@Column(name = "lockout_until")
		public Date getLockoutUntil() {
			return lockoutUntil;
		}

		public void setLockoutUntil(Date lockoutUntil) {
			this.lockoutUntil = lockoutUntil;
		}


}

