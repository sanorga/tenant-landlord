package com.tea.landlordapp.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.tea.landlordapp.dto.LookupPropertyDto;
import com.tea.landlordapp.dto.LookupZipcodeDto;
import com.tea.landlordapp.dto.UserDetailsDto;

public interface LookupService extends Serializable {

	public LookupPropertyDto getPropertyDtoById(Integer id);
//	public LookupPropertyDto getPropertyDtoByBigId(Integer id);
//	public LookupPropertyDto getPropertyDtoByBigIdForGuest(Integer id);
	
	public LookupZipcodeDto getCityStateByZip(String zipcode);
	
//	public Map<Integer, String> getPropertyOptions(String clnt, String charType);
//	public Map<Integer, String> getPropertyOptionsWithAll(String clnt, String charType);
	public Map<Integer, String> getPropertyOptionsByPM(Integer id);
	public Map<Integer, String> getPropertyOptionsByPMWithAll(Integer id);
//	public Map<Integer, String> getPackageOptions(String name, String type);
//	public Double getStatewiseCost(String state, Integer year);
	public Integer getDuplicateApplication(Integer propertyId, Date dob, String ssn, String passportNumber, int daysOld);
//	public Map<Integer, String> getSubscriberOptionsByMgmtCompany(String clientVal, String charType);
//	public Map<Integer, String> getPropertyOptionsForRebateRpt(String clientVal, String charType);
//	public Map<Long, String> getQboCustomerMapOptions();
//	public Map<Long, String> getQboCustomerMapOptionsAllowNew();
}
