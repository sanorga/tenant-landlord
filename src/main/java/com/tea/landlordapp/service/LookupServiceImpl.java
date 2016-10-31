package com.tea.landlordapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tea.landlordapp.domain.Applicant;
//import com.tea.landlordapp.domain.Packge;
import com.tea.landlordapp.domain.Property;
//import com.tea.landlordapp.domain.StandardPackge;
//import com.tea.landlordapp.domain.StateWisePrice;
//import com.tea.landlordapp.domain.Subscriber;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.domain.Zipcode;
import com.tea.landlordapp.dto.LookupPropertyDto;
import com.tea.landlordapp.dto.LookupZipcodeDto;
import com.tea.landlordapp.dto.UserDetailsDto;
//import com.tea.landlordapp.enums.AuthyApiParameter;
import com.tea.landlordapp.repository.ApplicantDao;
//import com.tea.landlordapp.repository.CustomersDao;
//import com.tea.landlordapp.repository.ServiceDao;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.utility.ServiceUtils;
import com.tea.landlordapp.utility.UtilityMethods;

@Service("lookupService")
public class LookupServiceImpl implements LookupService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8242602206874031952L;
	@Autowired
	UserDao userDao;
	
//	@Autowired
//	ServiceDao serviceDao;
	
	@Autowired
	SystemPropertyDao systemPropertyDao;
	
	@Autowired
	ApplicantDao applicantDao;
	
//	@Autowired
//	CustomersDao customersDao;

	@Override
	public LookupPropertyDto getPropertyDtoById(Integer id) {
		Property property = userDao.findProperty(id);
		if (property != null) {
			LookupPropertyDto propertyFacade = new LookupPropertyDto();
			BeanUtils.copyProperties(property, propertyFacade);
			return propertyFacade;
		}
		return null;
	}

//	@Override
//	public LookupPropertyDto getPropertyDtoByBigId(Integer id) {
//		Property property = userDao.findPropertyByBigId(id);
//		if (property != null && (property.isAllowLease() || property.isAllowPurchase())) {
//			LookupPropertyDto propertyFacade = new LookupPropertyDto();
//			BeanUtils.copyProperties(property, propertyFacade);
//			return propertyFacade;
//		}
//		return null;
//	}

//	@Override
//	public LookupPropertyDto getPropertyDtoByBigIdForGuest(Integer id) {
//		Property property = userDao.findPropertyByBigId(id);
//		if (property != null && property.isAllowGuest()) {
//			LookupPropertyDto propertyFacade = new LookupPropertyDto();
//			BeanUtils.copyProperties(property, propertyFacade);
//			return propertyFacade;
//		}
//		return null;
//	}

	@Override
	public LookupZipcodeDto getCityStateByZip(String zipcode) {
		LookupZipcodeDto zipFacade = null;
	      Zipcode zip = userDao.findZipcode(zipcode);
	      if (!ObjectUtils.equals(zip, null)) {
	         zipFacade = new LookupZipcodeDto(zip.getCity(), zip.getState());
	      }
	      return zipFacade;
	}

//	@Override
//	public Map<Integer, String> getPropertyOptions(String clientVal, String charType) {
//
//		Integer clientId = null;
//		if(StringUtils.equals(charType, "name"))
//			clientId = userDao.findClientByName(clientVal).getId();
//		if(StringUtils.equals(charType, "id"))
//			clientId = Integer.parseInt(clientVal);
//		
//		Map<Integer, String> properties = new TreeMap<Integer, String>();
//		properties.put(-1, "(Select Option)");
//			
//		List<Property> propertiesFromClient = null;
//
//		if (!ObjectUtils.equals(clientId, null))
//			propertiesFromClient = userDao.findPropertiesBySubscriber(clientId, null);
//
//		if ((!ObjectUtils.equals(propertiesFromClient, null)) && (propertiesFromClient.size() > 0)) {
//		
//			for (Property p : propertiesFromClient) {
//				properties.put(p.getId(), "" + p.getPropertyKey());
//			}
//		}
//
//		return properties;
//	}

//	@Override
//	public Map<Integer, String> getPropertyOptionsForRebateRpt(String clientVal, String charType) {
//
//		Integer clientId = null;
//		if(StringUtils.equals(charType, "name"))
//			clientId = userDao.findClientByName(clientVal).getId();
//		if(StringUtils.equals(charType, "id"))
//			clientId = Integer.parseInt(clientVal);
//		
//		Map<Integer, String> properties = new TreeMap<Integer, String>();
//		properties.put(-1, "(Select Option)");
//			
//		List<Property> propertiesFromClient = null;
//
//		if (!ObjectUtils.equals(clientId, null))
//			propertiesFromClient = userDao.findPropertiesBySubscriberForRebateRpt(clientId, null);
//
//		if ((!ObjectUtils.equals(propertiesFromClient, null)) && (propertiesFromClient.size() > 0)) {
//		
////			for (Property p : propertiesFromClient) {
////				properties.put(p.getId(), "" + p.getPropertyKey());
////			}
//		}
//
//		return properties;
//	}
	
//	@Override
//	public Map<Integer, String> getPropertyOptionsWithAll(String clientVal, String charType) {
//
//		Integer clientId = null;
//		if(StringUtils.equals(charType, "name"))
//			clientId = userDao.findClientByName(clientVal).getId();
//		if(StringUtils.equals(charType, "id"))
//			clientId = Integer.parseInt(clientVal);
//		
//		Map<Integer, String> properties = new TreeMap<Integer, String>();
//		properties.put(-1, "(Select Option)");
//		
//		
//		List<Property> propertiesFromClient = null;
//
//		if (!ObjectUtils.equals(clientId, null))
//			propertiesFromClient = userDao.findPropertiesBySubscriber(clientId, null);
//
//		if ((!ObjectUtils.equals(propertiesFromClient, null)) && (propertiesFromClient.size() > 0)) {
//			properties.put(-2, "" + "All");
////			for (Property p : propertiesFromClient) {
////				properties.put(p.getId(), "" + p.getPropertyKey());
//			}
//		}
//
//		return properties;
//	}

	@Override
	public Map<Integer, String> getPropertyOptionsByPM(Integer pmId) {

	
		Map<Integer, String> properties = new TreeMap<Integer, String>();
		properties.put(-1, "(Select One)");
		
		List<Property> propertiesFromClient = null;

		if (!ObjectUtils.equals(pmId, null))
			propertiesFromClient = userDao.findPropertiesByManager(pmId);

		if ((!ObjectUtils.equals(propertiesFromClient, null)) && (propertiesFromClient.size() > 0)) {
//			for (Property p : propertiesFromClient) {
//				properties.put(p.getId(), "" + p.getPropertyKey());
//			}
		}

		return properties;
	}
	
	
//	@Override
//	public Map<Integer, String> getSubscriberOptionsByMgmtCompany(String clientVal, String charType) {
//
//		Integer mgmtCompanyId = null;
//		if(StringUtils.equals(charType, "name"))
//			mgmtCompanyId = userDao.findManagementCompanyByName(clientVal).getId();
//		if(StringUtils.equals(charType, "id"))
//			mgmtCompanyId = Integer.parseInt(clientVal);
//		
//		Map<Integer, String> subscribers = new TreeMap<Integer, String>();
//		subscribers.put(-1, "(Select Option)");
//			
//		List<Subscriber> subscribersFromMgmtCompany = null;
//
//		if (!ObjectUtils.equals(mgmtCompanyId, null))
//			subscribersFromMgmtCompany = userDao.findSubscribersByMgmtCompany(mgmtCompanyId, null);
//
//		if ((!ObjectUtils.equals(subscribersFromMgmtCompany, null)) && (subscribersFromMgmtCompany.size() > 0)) {
//		
//			for (Subscriber s : subscribersFromMgmtCompany) {
//				subscribers.put(s.getId(), "" + s.getName());
//			}
//		}
//
//		return subscribers;
//	}

	@Override
	public Map<Integer, String> getPropertyOptionsByPMWithAll(Integer pmId) {

		Map<Integer, String> properties = new TreeMap<Integer, String>();
		properties.put(-1, "(Select One)");
		
		List<Property> propertiesFromClient = null;

		if (!ObjectUtils.equals(pmId, null))
			propertiesFromClient = userDao.findPropertiesByManager(pmId);

		if ((!ObjectUtils.equals(propertiesFromClient, null)) && (propertiesFromClient.size() > 0)) {
			properties.put(-2, "" + "All");
//			for (Property p : propertiesFromClient) {
//				properties.put(p.getId(), "" + p.getPropertyKey());
//			}
		}

		return properties;
	}

//	@Override
//	public Map<Integer, String> getPackageOptions(String name, String type) {
//		Integer clientId = null;
//		if(StringUtils.equals(type, "name"))
//			clientId = userDao.findClientByName(name).getId();
//		if(StringUtils.equals(type, "id"))
//			clientId = Integer.parseInt(name);
//			
//	    Map<Integer, String> packagies = new TreeMap<Integer, String>();
//	    packagies.put(-1, "(Select One)");
//	    
//	    Subscriber client = null;
//
//		  if (!ObjectUtils.equals(clientId, null))
//		  	client = userDao.findSubscriber(clientId);
//		  
//		  if (ObjectUtils.equals(client, null))
//		  	return packagies;
//		  

//		// Extender Packges
//		  if (!ObjectUtils.equals(client.getPackges(), null) && (client.getPackges().size() > 0))
//		  	for (Packge pkg : client.getPackges())
//		  		if (pkg.getStatus() != 'I')
//		  			packagies.put(pkg.getId(), pkg.getName());
//		  
//		  
//		  // Standard packages
//		  if (!ObjectUtils.equals(client.getStandardPackges(), null) && (client.getStandardPackges().size() > 0))
//		  	for (StandardPackge standardPkg : client.getStandardPackges())
//					packagies.put(standardPkg.getPackge().getId(), standardPkg.getPackge().getName());
//
//			return ServiceUtils.getSortedMap(packagies);
//	}

//	@Override
//	public Double getStatewiseCost(String state, Integer year) {
//		StateWisePrice stateWisePrice = serviceDao.findStateWisePrice(state, year);
//		Double price = 0.00;
//		if (!ObjectUtils.equals(stateWisePrice, null))
//			price = stateWisePrice.getPrice();
//
//		return price;
//	}

	@Override
	public Integer getDuplicateApplication(Integer propertyId, Date dob, String ssn, String passportNumber, int daysOld) {
		String pseudoId = UtilityMethods.buildPseudoId(ssn, passportNumber, dob);
		List<Applicant> applicants = applicantDao.findDuplicateApplicant(propertyId, pseudoId, daysOld);
		if (CollectionUtils.isEmpty(applicants)) return 0;
		return applicants.get(0).getApplication().getId();
	}

//	@Override
//	public Map<Long, String> getQboCustomerMapOptions() {
//		return customersDao.getQboCustomerMapOptions();
//	}
//
//	@Override
//	public Map<Long, String> getQboCustomerMapOptionsAllowNew() {
//		return customersDao.getQboCustomerMapOptionsAllowNew();
//	}

}
