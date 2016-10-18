package com.tea.landlordapp.service;

import com.tea.landlordapp.domain.AnonymousUser;

public interface InviteService {

	public void invite(AnonymousUser au) throws Exception;

	public String postRequest(String apiUrl, String xmlString, String securityToken);

}
