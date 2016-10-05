package com.tea.landlordapp.service;

import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.exception.CryptoServiceException;

public interface CryptoService {
	public void decrypt(DataKeyDto dto) throws CryptoServiceException;
	public void encrypt(DataKeyDto dto) throws CryptoServiceException;
}
