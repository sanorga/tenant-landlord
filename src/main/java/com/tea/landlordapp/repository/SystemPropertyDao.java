package com.tea.landlordapp.repository;

import java.io.Serializable;


import com.tea.landlordapp.enums.EncryptionPropertyParameter;


public interface SystemPropertyDao extends Serializable {
	public String getPropertyValue(String propertyName);
	public String getPropertyValue(EncryptionPropertyParameter param);

}
