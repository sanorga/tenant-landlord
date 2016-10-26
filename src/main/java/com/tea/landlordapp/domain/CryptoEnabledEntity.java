package com.tea.landlordapp.domain;

import javax.persistence.MappedSuperclass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.exception.CryptoServiceException;
import com.tea.landlordapp.service.CryptoService;
import com.tea.landlordapp.utility.BeanFinder;

@MappedSuperclass
public abstract class CryptoEnabledEntity extends AuditableEntity {

	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());
	
	private CryptoService cryptoService = null;
	
	private void initCryptoService(){
		if (cryptoService == null){
			cryptoService = BeanFinder.getCryptoService();
		}
	}

	protected void encrypt(DataKeyDto dkd) throws CryptoServiceException{
		initCryptoService();
		try {
			cryptoService.encrypt(dkd);
		} catch (CryptoServiceException e) {
			logger.error("{}", e);
			throw e;
		}
	}

	protected void decrypt(DataKeyDto dkd) throws CryptoServiceException{
		initCryptoService();
		try {
			cryptoService.decrypt(dkd);
		} catch (CryptoServiceException e) {
			logger.error("{}", e);
			throw e;
		}
	}
	
	public abstract void sanitize();

}
