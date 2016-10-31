package com.tea.landlordapp.dto;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class LookupPropertyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2734936999968160919L;
	private Integer id;
	private String name;
	private String houseNo;
	private String street;
	private String streetType;
	private String apartmentNo;
	private String city;
	private String state;
	private String zipcode;
	private String county;
	private String country;
	private String rentalPeriod;

	private Double rentalAmount = 0d;

	public LookupPropertyDto() {
		id = null;
		name = "";
		houseNo = "";
		street = "";
		streetType = "";
		apartmentNo = "";
		city = "";
		state = "";
		zipcode = "";
		county = "";
		country = "";
		rentalPeriod = "";
	}

	public String getName() {
		return StringUtils.isBlank(name) ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHouseNo() {
		return StringUtils.isBlank(houseNo) ? "" : houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return StringUtils.isBlank(street) ? "" : street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetType() {
		return StringUtils.isBlank(streetType) ? "" : streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	public String getApartmentNo() {
		return StringUtils.isBlank(apartmentNo) ? "" : apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}

	public String getCity() {
		return StringUtils.isBlank(city) ? "" : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return StringUtils.isBlank(state) ? "" : state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return StringUtils.isBlank(zipcode) ? "" : zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCounty() {
		return StringUtils.isBlank(county) ? "" : county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return StringUtils.isBlank(country) ? "" : country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRentalPeriod() {
		return StringUtils.isBlank(rentalPeriod) ? "" : rentalPeriod;
	}

	public void setRentalPeriod(String rentalPeriod) {
		this.rentalPeriod = rentalPeriod;
	}

	public Double getRentalAmount() {
		return ObjectUtils.equals(rentalAmount, null) ? 0 : rentalAmount;
	}

	public void setRentalAmount(Double rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
