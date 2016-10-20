package com.tea.landlordapp.service;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import com.tea.landlordapp.domain.AnonymousUser;

public interface InviteService {

	public String invite(AnonymousUser au) throws Exception;

	public String postRequest(String apiUrl, String xmlString, String securityToken);

	public String postJSONMessageGetJSON(String apiUrl, String message, String Credentials) throws IOException, XPathExpressionException;

}
