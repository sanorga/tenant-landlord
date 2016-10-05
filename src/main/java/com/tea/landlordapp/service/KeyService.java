package com.tea.landlordapp.service;

import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.exception.CryptoServiceException;

public interface KeyService {
	public void getDataKey(DataKeyDto keyDto) throws CryptoServiceException;
}
