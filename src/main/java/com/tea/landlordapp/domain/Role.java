package com.tea.landlordapp.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "role")
public class Role extends AuditableEntity {

	private static final long serialVersionUID = -2323772231411443785L;

	private char isAvailable = 'Y';

	private String role;
	private String description;

	private List<Capability> capabilities = new ArrayList<Capability>();
	private java.util.List<Integer> capabilityIds = new ArrayList<Integer>();

	private PasswordPolicy passwordPolicy;

	@Column(name = "role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "is_available")
	public char getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(char isAvailable) {
		this.isAvailable = isAvailable;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "role2capability", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "capability_id", referencedColumnName = "id"))
	public List<Capability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
		capabilityIds = new java.util.ArrayList<Integer>();
		if (!ObjectUtils.equals(capabilities, null)) {
			for (Capability c : capabilities) {
				capabilityIds.add(c.getId());
			}
		}
	}

	@Transient
	public java.util.List<Integer> getCapabilityIds() {
		return capabilityIds;
	}

	public void setCapabilityIds(java.util.List<Integer> capabilityIds) {
		this.capabilityIds = capabilityIds;
	}

	@ManyToOne
	@JoinColumn(name = "password_policy_id")
	public PasswordPolicy getPasswordPolicy() {
		return passwordPolicy;
	}

	public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
	}
}