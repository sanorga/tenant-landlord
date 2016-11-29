package com.tea.landlordapp.service.helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.User;

@Service("InviteHelper")
public class InviteHelperImpl implements InviteHelper {
	private SystemPropertyDao systemPropertyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	public InviteHelperImpl(SystemPropertyDao systemPropertyDao			) {
		super();
		this.systemPropertyDao = systemPropertyDao;
	}	
	
	@Override
	public Map<String, String> buildPropertyInfoMap(AnonymousUser au) {
		Map<String, String> propertyInfo = new HashMap<String,String>();
		
		Property p = au.getProperty();
		if (p== null) {
			return null;
		}
		
		User user = userDao.findUser(p.getUserId());
		final NumberFormat df = new DecimalFormat("#0.00");
		final Double rentalAmount = au.getRentalAmount();
		final Double rentalDeposit = au.getRentalDeposit();
		
		propertyInfo.put("RentalAmount", df.format(rentalAmount));
		propertyInfo.put("RentalDeposit", df.format(rentalDeposit));
		propertyInfo.put("PropertyId", "");
		propertyInfo.put("PropertyIdentifier", p.getName());
		propertyInfo.put("Active", "true");
		propertyInfo.put("Zip", p.getZipcode());
		if (p.getPhone() != null) {
			propertyInfo.put("Phone", p.getPhone());
		} 
		else propertyInfo.put("Phone", "3056784433");
		if (p.getExtension() != null) {
			propertyInfo.put("PhoneExtension",p.getExtension());
		}
		else propertyInfo.put("PhoneExtension","111");
		propertyInfo.put("UnitNumber", p.getApartmentNo());
		propertyInfo.put("City", p.getCity());
		propertyInfo.put("Street", p.getStreet());
		propertyInfo.put("State", p.getState());
		propertyInfo.put("Name", p.getName());
		propertyInfo.put("FirstName",user.getFirstName());
		propertyInfo.put("LastName",user.getLastName());
		
		propertyInfo.put("Classification","Conventional");
		propertyInfo.put("IR","2");
		propertyInfo.put("IncludeMedicalCollections","false");
		propertyInfo.put("IncludeForeclosures","false");
		propertyInfo.put("DeclineForOpenBankruptcies","false");
		propertyInfo.put("OpenBankruptcyWindow","0");
		propertyInfo.put("IsFcraAgreeentAccepted","true");
		
		String partnerId = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
		String key = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.KEY);
		propertyInfo.put("partnerId", partnerId);
		propertyInfo.put("key", key);

		return propertyInfo;
	}

	@Override
	public Map<String, String> buildApplicationInfoMap(AnonymousUser au, String propertyIdStr) {
		Map<String, String> applicationInfo = new HashMap<String,String>();
		
		Property p = au.getProperty();
		if (p== null) {
			return null;
		}
		
		User user = userDao.findUser(p.getUserId());
		final NumberFormat df = new DecimalFormat("#0.00");
		final Double rent = au.getRentalAmount();
		final Double deposit = au.getRentalDeposit();
		
		applicationInfo.put("Deposit", df.format(deposit));
		applicationInfo.put("ApplicationId", "0");
		applicationInfo.put("LeaseTermInMonths", "12");
		applicationInfo.put("LandlordPays", "False");
		applicationInfo.put("PropertyId", propertyIdStr);
		applicationInfo.put("Rent", df.format(rent));
		applicationInfo.put("UnitNumber", p.getApartmentNo());
		applicationInfo.put("ProductBundle", "PackageCorePlusEviction");
		
		applicationInfo.put("applicantEmail", au.getEmailId());
		applicationInfo.put("coapplicantEmail",  au.getCoappEmailId());
		
		return applicationInfo;
	}
}
