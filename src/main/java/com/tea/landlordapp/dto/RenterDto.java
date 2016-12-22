package com.tea.landlordapp.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;



import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.enums.EmploymentStatus;
import com.tea.landlordapp.enums.IncomeFrequency;


public class RenterDto implements Serializable{

	public RenterDto() {
		super();
	}

	public RenterDto(User user){
		this();	
		this.userId = user.getId();
//		this.openIdIdentifier = user.getOpenIdIdentifier();
		this.status = user.getStatus();
//		this.accountType = user.getAccountType();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.address = user.getAddress();
//		this.address2 = user.getAddress2();
		this.city = user.getCity();
		this.state = user.getState();
		this.zipcode = user.getZipcode();
		this.country = user.getCountry();
		this.phone = user.getPhone();
//		this.fax = user.getFax();
		this.income = 0.0;
		this.employmentStatus = EmploymentStatus.UNKNOWN;
		this.setIncomeFrequency(IncomeFrequency.ANNUAL);
//		this.socialId = new String();
//		this.oldClearDOB = null;
		
	}
	
	// variables
	
	private Integer userId;
	private String openIdIdentifier = null;
	private char status = 'A';
	private char accountType = 'L';

	private String username;
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
	private double income;
	private EmploymentStatus employmentStatus;
	private IncomeFrequency incomeFrequency;

	private String socialId;

	private Date oldClearDOB;

	private boolean acceptSATC = false;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenIdIdentifier() {
		return openIdIdentifier;
	}

	public void setOpenIdIdentifier(String openIdIdentifier) {
		this.openIdIdentifier = openIdIdentifier;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getAccountType() {
		return accountType;
	}

	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatus employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public IncomeFrequency getIncomeFrequency() {
		return incomeFrequency;
	}

	public void setIncomeFrequency(IncomeFrequency incomeFrequency) {
		this.incomeFrequency = incomeFrequency;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public Date getOldClearDOB() {
		return oldClearDOB;
	}

	public void setOldClearDOB(Date oldClearDOB) {
		this.oldClearDOB = oldClearDOB;
	}

	public boolean isAcceptSATC() {
		return acceptSATC;
	}

	public void setAcceptSATC(boolean acceptSATC) {
		this.acceptSATC = acceptSATC;
	}
	


}
