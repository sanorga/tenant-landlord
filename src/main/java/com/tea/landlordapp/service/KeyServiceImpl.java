package com.tea.landlordapp.service;

import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.DataKeySpec;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.enums.EncryptionPropertyParameter;
import com.tea.landlordapp.exception.CryptoServiceException;
import com.tea.landlordapp.repository.SystemPropertyDao;

@Service("keyService")
public class KeyServiceImpl implements KeyService {
	private AWSKMSClient client;
	
	private SystemPropertyDao systemPropertyDao;

	@Autowired
	public KeyServiceImpl(AWSKMSClient client,
			SystemPropertyDao systemPropertyDao) {
		super();
		this.client = client;
		this.systemPropertyDao = systemPropertyDao;
	}

	@Override
	public void getDataKey(DataKeyDto keyDto) throws CryptoServiceException {
		if (keyDto == null) throw new CryptoServiceException("missing dataKeyDto");
		if (keyDto.getEncryptedDataKey() == null || keyDto.getEncryptedDataKey().length < 1){
			// need a new Key
			String masterKey = systemPropertyDao.getPropertyValue(EncryptionPropertyParameter.DEFAULT_MASTER_KEY_ALIAS);
			GenerateDataKeyRequest request = new GenerateDataKeyRequest().withKeyId(masterKey).withKeySpec(DataKeySpec.AES_256);
			GenerateDataKeyResult result = client.generateDataKey(request);
			
			keyDto.setMasterKeyName(masterKey);
			keyDto.setEncryptedDataKey(result.getCiphertextBlob().array());
			keyDto.setClearDataKey(result.getPlaintext().array());
			
			
		} else {
			// decrypt existing key
			ByteBuffer ciphertextBlob = ByteBuffer.wrap(keyDto.getEncryptedDataKey());
			
			DecryptRequest req = new DecryptRequest().withCiphertextBlob(ciphertextBlob);
			ByteBuffer plainText = client.decrypt(req).getPlaintext();
			keyDto.setClearDataKey(plainText.array());

		}
	}

}
