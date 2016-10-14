package com.tea.landlordapp.service.helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Property;

@Service("InviteHelper")
public class InviteHelperImpl implements InviteHelper {
	private SystemPropertyDao systemPropertyDao;
	
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
			
		final NumberFormat df = new DecimalFormat("#0.00");
		final Double rentalAmount = p.getRentalAmount();
		final Double rentalDeposit = p.getRentalDeposit();
		
		saleInfo.put("rentalAmount", df.format(rentalAmount));
		saleInfo.put("rentalDeposit", df.format(rentalDeposit));
		
		saleInfo.put("apartmentNo", p.getApartmentNo());
		saleInfo.put("city", p.getCity());
		saleInfo.put("street", p.getStreet());
		saleInfo.put("name", p.getName());
	
		String partnerId = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
		String key = systemPropertyDao
				.getPropertyValue(TransUnionApiParameter.KEY);
		saleInfo.put("partnerId", partnerId);
		saleInfo.put("key", key);

		return saleInfo;
	}

}
