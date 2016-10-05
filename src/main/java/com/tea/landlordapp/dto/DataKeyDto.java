package com.tea.landlordapp.dto;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.tea.landlordapp.constant.Globals;

public class DataKeyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2284118288055757588L;

	private SimpleDateFormat sdf = new SimpleDateFormat(Globals.DATE_FORMAT);
	
	private String masterKeyName = "";
	private byte[] encryptedDataKey = new byte[0];
	private byte[] clearDataKey = new byte[0];
	private byte[] ptData = new byte[0];
	private byte[] ctData = new byte[0];

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(clearDataKey);
		result = prime * result + Arrays.hashCode(ctData);
		result = prime * result + Arrays.hashCode(encryptedDataKey);
		result = prime * result + ((masterKeyName == null) ? 0 : masterKeyName.hashCode());
		result = prime * result + Arrays.hashCode(ptData);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataKeyDto other = (DataKeyDto) obj;
		if (!Arrays.equals(clearDataKey, other.clearDataKey))
			return false;
		if (!Arrays.equals(ctData, other.ctData))
			return false;
		if (!Arrays.equals(encryptedDataKey, other.encryptedDataKey))
			return false;
		if (masterKeyName == null) {
			if (other.masterKeyName != null)
				return false;
		} else if (!masterKeyName.equals(other.masterKeyName))
			return false;
		if (!Arrays.equals(ptData, other.ptData))
			return false;
		return true;
	}

	private byte[] getPtData() {
		return ptData;
	}

	private void setPtData(byte[] ptData) {
		this.ptData = ptData;
	}

	public byte[] getCtData() {
		return ctData;
	}

	public void setCtData(byte[] ctData) {
		this.ctData = ctData;
	}

	public DataKeyDto() {
		// TODO Auto-generated constructor stub
	}

	public DataKeyDto(String masterKeyName, byte[] encryptedDataKey) {
		super();
		this.masterKeyName = masterKeyName;
		this.encryptedDataKey = encryptedDataKey;
	}

	public DataKeyDto(String masterKeyName, byte[] encryptedDataKey, byte[] cipherText) {
		super();
		this.masterKeyName = masterKeyName;
		this.encryptedDataKey = encryptedDataKey;
		this.ctData = cipherText;
	}

	public String getMasterKeyName() {
		return masterKeyName;
	}

	public void setMasterKeyName(String masterKeyName) {
		this.masterKeyName = masterKeyName;
	}

	public byte[] getEncryptedDataKey() {
		return encryptedDataKey;
	}

	public void setEncryptedDataKey(byte[] encryptedDataKey) {
		this.encryptedDataKey = encryptedDataKey;
	}

	public byte[] getClearDataKey() {
		return clearDataKey;
	}

	public void setClearDataKey(byte[] clearDataKey) {
		this.clearDataKey = clearDataKey;
	}
	
	public byte[] dataAsByteArray(){
		return getPtData();
	}
	
	public String dataAsString(){
		return new String(ptData,StandardCharsets.UTF_8);
	}
	
	public Date dataAsDate(){
		String clearText = dataAsString();
		Date res = new Date(Long.parseLong(clearText));
		return res;
	}
	
	public void setData(byte[] data){
		setPtData(data);
	}
	
	public void setData(String data){
		setPtData(data.getBytes(StandardCharsets.UTF_8));
	}
	
	public void setData(Date data){
		String clearText = Long.toString(data.getTime());
		setData(clearText);
	}

}
