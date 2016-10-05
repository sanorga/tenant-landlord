package com.tea.landlordapp.remote;

import java.io.Serializable;

public interface AdminOpsService extends Serializable {
	public String getDuoRequest(String username);

//	public Integer fetchUnsignedDocuments();

	public boolean verifyDuoLogin(String username, String sigResponse);
	
	public void updateCurrentAuthorities(Boolean mfaValid);
}
