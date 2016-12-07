package com.tea.landlordapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.dto.BinaryFile;

public interface MessageService {
	public boolean sendApplicationClientMail(int appId) throws Exception;
	public boolean sendAnonymousUserMail(AnonymousUser anonymousUser,
			HttpServletRequest request) throws Exception;
	public boolean sendTemplateMail(String[] senderList, HashMap<String, Object> map, String subjectFileName,
			String textFileName, Map<String, String> inlineResources, BinaryFile attachment) throws Exception;
	public boolean sendPasswordResetEmail(String username) throws Exception;
	
}
