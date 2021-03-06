package com.tea.landlordapp.service;

import java.io.IOException;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import com.tea.landlordapp.domain.AnonymousUser;

public interface InviteService {

	public Map<String, String> invite(AnonymousUser au) throws Exception;

	public String postRequest(String apiUrl, String xmlString, String securityToken);

	public Map<String, String> postJSONMessageGetJSON(String apiUrl, String message, String Credentials) throws IOException, XPathExpressionException;

	public Map<String, String> submitProperty(AnonymousUser au) throws Exception;

	public Map<String, String> getReports(Integer applicationExtId) throws Exception;

}
