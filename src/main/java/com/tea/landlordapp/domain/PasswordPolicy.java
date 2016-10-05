package com.tea.landlordapp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "password_policy")
public class PasswordPolicy implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2542157157597146407L;
	private Integer id;
	private String description;
	private int minLength = 8;
	private int maxLength = 20;
	private int maxAge = 90;
	private int minUppercase = 1;
	private int minLowercase = 1;
	private int minDigit = 1;
	private int minSpecialCharacter = 1;
	private int minRememberedPassword = 4;
	private int minMatches = 3;
	private boolean requireMfa = false;
	private int attemptsAllowed = 0;
	private int lockoutMinutes = 0;

	@Column(name = "attempts_allowed")
	public int getAttemptsAllowed() {
		return attemptsAllowed;
	}

	public void setAttemptsAllowed(int attemptsAllowed) {
		this.attemptsAllowed = attemptsAllowed;
	}

	@Column(name = "reset_lockout_minutes")
	public int getLockoutMinutes() {
		return lockoutMinutes;
	}

	public void setLockoutMinutes(int lockoutMinutes) {
		this.lockoutMinutes = lockoutMinutes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "min_length")
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	@Column(name = "max_length")
	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Column(name = "max_age")
	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	@Column(name = "min_uppercase")
	public int getMinUppercase() {
		return minUppercase;
	}

	public void setMinUppercase(int minUppercase) {
		this.minUppercase = minUppercase;
	}

	@Column(name = "min_lowercase")
	public int getMinLowercase() {
		return minLowercase;
	}

	public void setMinLowercase(int minLowercase) {
		this.minLowercase = minLowercase;
	}

	@Column(name = "min_digit")
	public int getMinDigit() {
		return minDigit;
	}

	public void setMinDigit(int minDigit) {
		this.minDigit = minDigit;
	}

	@Column(name = "min_special_character")
	public int getMinSpecialCharacter() {
		return minSpecialCharacter;
	}

	public void setMinSpecialCharacter(int minSpecialCharacter) {
		this.minSpecialCharacter = minSpecialCharacter;
	}

	@Column(name = "min_remembered_password")
	public int getMinRememberedPassword() {
		return minRememberedPassword;
	}

	public void setMinRememberedPassword(int minRememberedPassword) {
		this.minRememberedPassword = minRememberedPassword;
	}

	@Column(name = "min_matches")
	public int getMinMatches() {
		return minMatches;
	}

	public void setMinMatches(int minMatches) {
		this.minMatches = minMatches;
	}

	@Column(name = "require_mfa")
	public boolean isRequireMfa() {
		return requireMfa;
	}

	public void setRequireMfa(boolean requireMfa) {
		this.requireMfa = requireMfa;
	}
}
