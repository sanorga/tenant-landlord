package com.tea.landlordapp.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.exception.CorruptCipherTextException;
import com.tea.landlordapp.exception.CryptoServiceException;

@Service("cryptoService")
public class CryptoServiceImpl implements CryptoService {
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());
	
//	private static String DEFAULT_ALGORITHM = "AES/CTR/NoPadding";
	private static String DEFAULT_ALGORITHM = "AES/GCM/NoPadding";
	private static String DEFAULT_PROVIDER = "BC";
	private static String DEFAULT_MAC = "HmacSHA256";
	private static String DEFAULT_METHOD = "AES";
	
	KeyService keyService;

	@Autowired
	public CryptoServiceImpl(KeyService keySvc) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		super();
		keyService = keySvc;
		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	public void encrypt(DataKeyDto dto) throws CryptoServiceException{
		SecureRandom random = new SecureRandom();
		IvParameterSpec ivSpec = createCtrIvForAES(1, random);
		byte[] iv = ivSpec.getIV();
		byte[] cleartext = dto.dataAsByteArray();
		byte[] cipherText = new byte[0];;
		try {
			keyService.getDataKey(dto);
			Key key = new SecretKeySpec(dto.getClearDataKey(), DEFAULT_METHOD);
			Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			cipherText = cipher.doFinal(cleartext);
		} catch (Exception e) {
			logger.error("{}",e);
			throw new CryptoServiceException(e.getMessage());
		}
		dto.setCtData(ArrayUtils.addAll(iv, cipherText));
	}
	
	@Override
	public void decrypt(DataKeyDto dto) throws CryptoServiceException {
		byte[] cipherText = dto.getCtData();
		byte[] iv = ArrayUtils.subarray(cipherText, 0, 16);
		byte[] cipherMessage = ArrayUtils.subarray(cipherText, 16, cipherText.length);
		byte[] plainText;
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		
		try {
			keyService.getDataKey(dto);
			byte[] dataKey = dto.getClearDataKey();
			Key key = new SecretKeySpec(dataKey, DEFAULT_METHOD);
			Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			plainText = cipher.doFinal(cipherMessage);
		} catch (Exception e) {
			logger.error("{}",e);
			throw new CryptoServiceException(e.getMessage());
		}
		
		dto.setData(plainText);
	}

//	@Override
//	public void encrypt(DataKeyDto dto) throws Exception{
//		keyService.getDataKey(dto);
//		SecureRandom random = new SecureRandom();
//		IvParameterSpec ivSpec = createCtrIvForAES(1, random);
//		Key key = new SecretKeySpec(dto.getClearDataKey(), DEFAULT_METHOD);
//		Mac hMac = Mac.getInstance(DEFAULT_MAC, DEFAULT_PROVIDER);
//		Key hMacKey = new SecretKeySpec(key.getEncoded(), DEFAULT_MAC);
//		byte[] cleartext = dto.dataAsByteArray();
//		Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
//		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//		byte[] cipherText = new byte[cipher.getOutputSize(cleartext.length + hMac.getMacLength())];
//		int ctLength = cipher.update(cleartext,  0, cleartext.length, cipherText, 0);
//		
//		hMac.init(hMacKey);
//		hMac.update(cleartext);
//		ctLength += cipher.doFinal(hMac.doFinal(), 0, hMac.getMacLength(), cipherText, ctLength);
//		
//		byte[] iv = ivSpec.getIV();
//		dto.setCtData(ArrayUtils.addAll(iv, cipherText));
//	}
//	
//	@Override
//	public void decrypt(DataKeyDto dto) throws Exception {
//		keyService.getDataKey(dto);
//		byte[] cipherText = dto.getCtData();
//		byte[] dataKey = dto.getClearDataKey();
//		byte[] iv = Arrays.copyOfRange(cipherText, 0, 16);
//		byte[] cipherMessage = Arrays.copyOfRange(cipherText, 16, cipherText.length);
//		IvParameterSpec ivSpec = new IvParameterSpec(iv);
//		Key key = new SecretKeySpec(dataKey, DEFAULT_METHOD);
//		Mac hMac = Mac.getInstance(DEFAULT_MAC, DEFAULT_PROVIDER);
//		Key hMacKey = new SecretKeySpec(key.getEncoded(), DEFAULT_MAC);
//		Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
//		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
//		
//		byte[] plainText = cipher.doFinal(cipherMessage, 0, cipherMessage.length);
//		int messageLength = plainText.length - hMac.getMacLength();
//		
//		hMac.init(hMacKey);
//		hMac.update(plainText, 0, messageLength);
//		
//		byte[] messageHash = new byte[hMac.getMacLength()];
//		System.arraycopy(plainText,  messageLength, messageHash,  0,  messageHash.length);
//		byte[] message = new byte[messageLength];
//		
//		System.arraycopy(plainText, 0, message, 0, messageLength);
//		
//		if (!MessageDigest.isEqual(hMac.doFinal(), messageHash)){
//			throw new CorruptCipherTextException("CipherText was corrupt.");
//		}
//		
//		dto.setData(message);
//	}

	
	private IvParameterSpec createCtrIvForAES(int messageNumber, SecureRandom random){
		byte[] ivBytes = new byte[16];
		random.nextBytes(ivBytes);
		
		ivBytes[0] = (byte) (messageNumber >> 24);
		ivBytes[1] = (byte) (messageNumber >> 16);
		ivBytes[2] = (byte) (messageNumber >> 8);
		ivBytes[3] = (byte) (messageNumber >> 0);
		
		for (int i = 0; i != 7; i++){
			ivBytes[8 + i] = 0;
		}
		
		ivBytes[15] = 1;
		
		return new IvParameterSpec(ivBytes);
	}

	
}
