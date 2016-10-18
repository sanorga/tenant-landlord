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
	public Map<String, String> buildInfoMap(AnonymousUser au) {
		Map<String, String> saleInfo = new HashMap<String,String>();
		
		Property p = au.getProperty();
		if (p== null) {
			return null;
		}
		
		User user = userDao.findUser(p.getUserId());
		final NumberFormat df = new DecimalFormat("#0.00");
		final Double rentalAmount = p.getRentalAmount();
		final Double rentalDeposit = p.getRentalDeposit();
		
		saleInfo.put("RentalAmount", df.format(rentalAmount));
		saleInfo.put("RentalDeposit", df.format(rentalDeposit));
		saleInfo.put("PropertyId", "");
		saleInfo.put("ApartmentNumber", p.getApartmentNo());
		saleInfo.put("City", p.getCity());
		saleInfo.put("Street", p.getStreet());
		saleInfo.put("Name", p.getName());
		saleInfo.put("FirstName",user.getFirstName());
		saleInfo.put("LastName",user.getLastName());
		
		saleInfo.put("Classification","Conventional");
		saleInfo.put("IR","2");
		saleInfo.put("IncludeMedicalCollections","false");
		saleInfo.put("IncludeForeclosures","false");
		saleInfo.put("DeclineForOpenBankruptcies","false");
		saleInfo.put("OpenBankruptcyWindow","0");
		saleInfo.put("IsFcraAgreeentAccepted","true");
		
		String partnerId = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
		String key = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.KEY);
		saleInfo.put("partnerId", partnerId);
		saleInfo.put("key", key);

		return saleInfo;
	}

}
