package com.tea.landlordapp.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;
//import com.tea.landlordapp.service.helper.RevoPayHelper;
import com.tea.landlordapp.utility.StringHelper;


@Service("inviteService")
public class InviteServiceImpl implements InviteService{
	
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());
	
	

	private SystemPropertyDao systemPropertyDao;

		
	@Autowired
	public InviteServiceImpl(SystemPropertyDao systemPropertyDao) {
		
		this.systemPropertyDao = systemPropertyDao;
		
	}
	
	@Override
	public void invite(AnonymousUser au) throws Exception {

			String apiUrl = systemPropertyDao.getPropertyValue(TransUnionApiParameter.URL);
			String partnerId = systemPropertyDao.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
			String key = systemPropertyDao.getPropertyValue(TransUnionApiParameter.KEY);
			String live = systemPropertyDao.getPropertyValue(TransUnionApiParameter.IS_LIVE);
			
			//create or update property - (POST or PUT)/LandlordApi/V1/Property
			
			StringBuilder aurl = new StringBuilder();
			aurl.append(apiUrl);
			aurl.append("ServerTime");
			String apiCall = aurl.toString();
			
			String response=getRequest(apiCall, null);
			if (response != null) {
				logger.debug(response);
			}
			
//			String xmlString = getXmlString(dto,saleInfo,"CCAuthorize");
//			String sanitizedXml = sanitizeMessageForLogging(xmlString);
//		
//			try {
//				String resp=postRequest(apiUrl, xmlString);
//				// check for null 
//				if (StringUtils.isBlank(resp)) {
//					dto.setSuccessful(false);
//					dto.setVendorResult("ERROR");
//					logger.error("RevoPay CC payment - empty response for request: {}",sanitizedXml);
//					return;
//				}
//					
//				Map<String,String> saleResponse = getAuthorizeResponseXml(resp);
//				//check for Fault
//				String faultcode = saleResponse.get("faultcode");
//				if (!StringUtils.isBlank(faultcode)){
//					// Fault response
//					logger.error(String.format("RevoPay - fault code: %s for request: $s", faultcode, sanitizedXml));
//					dto.setSuccessful(false);
//					dto.setVendorResult(faultcode);
//					return;
//				}
//				
//				String refId = saleResponse.get("RefId");
//				String statusCode = saleResponse.get("StatusCode");
//				String statusMsg = saleResponse.get("StatusMsg");
//				String approvalCode = saleResponse.get("ApprovalCode");
//				switch (statusCode) {
//				case "00":
//					dto.setSuccessful(true);
//					dto.setApprovalCode(approvalCode);
//					dto.setVendorTransactionId(refId);
//					dto.setVendorAmount(Double.valueOf(saleInfo.get("Amount")));
//					dto.setVendorAction(statusMsg);
//					Timestamp stamp = new Timestamp(System.currentTimeMillis());
//					Date date = new Date(stamp.getTime());
//					dto.setPostedDate(date);
//					break;
//				default:
//					logger.error(String.format("RevoPay - status code: %s for request: %s", statusCode, sanitizedXml));
//					dto.setSuccessful(false);
//				}
//				dto.setVendorResult(statusMsg);
//
//			} catch (Exception e) {
//				logger.error("{}", e);
//			}
//			return;
		
		
		//create Application - POST/LandlordApi/V1/Application
		
	}
	
	private String getRequest(String apiCall, String xmlString) {
		// TODO Auto-generated method stub
		String cleanXml = StringHelper.cleanXml(xmlString);
		String sanitizedMsg = sanitizeMessageForLogging(cleanXml);
		URL url;
		StringBuilder sb = new StringBuilder();
		InputStreamReader in = null;
		String result = null;
		try {
			url = new URL(apiCall);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//			connection.setDoInput(true); 
//			connection.setDoOutput(true);
			 
			connection.setRequestMethod("GET"); 
//			connection.setFollowRedirects(true); 

//			connection.setRequestProperty("Content-length",String.valueOf (cleanXml.length())); 
			connection.setRequestProperty("Content-Type","application/xml"); 
			connection.setRequestProperty("Accept","application/xml"); 
			
			// open up the input stream of the connection 
			if (connection != null && connection.getInputStream() != null) {
				in = new InputStreamReader(connection.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
			// write out the data 
//			int queryLength = cleanXml.length(); 
//			output.writeBytes( cleanXml ); 
			//output.close();
			
			int responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();
			
			if (responseCode != 200) {
				logger.error(String.format("Service returned problem message -- %d:%s\\nrequest: %s", responseCode, responseMessage, sanitizedMsg));
				return null;
		    }

			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer inputLine = new StringBuffer();
			String tmp;
			while ((tmp = input.readLine()) != null) {
				inputLine.append(tmp);
			}
			input.close(); 
			result = inputLine.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}

	@Override
	public String postRequest(String apiUrl, String xmlString) {
		String cleanXml = StringHelper.cleanXml(xmlString);
		String sanitizedMsg = sanitizeMessageForLogging(cleanXml);

		try { 
			
//			System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
			URL url = new URL(apiUrl); 
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//			connection.setHostnameVerifier(new RevoPayHostnameVerifier());
			connection.setDoInput(true); 
			connection.setDoOutput(true);
			 
			connection.setRequestMethod("POST"); 
			connection.setFollowRedirects(true); 

			connection.setRequestProperty("Content-length",String.valueOf (cleanXml.length())); 
			connection.setRequestProperty("Content-Type","text/xml"); 

			// open up the output stream of the connection 
			DataOutputStream output = new DataOutputStream( connection.getOutputStream() ); 

			// write out the data 
			int queryLength = cleanXml.length(); 
			output.writeBytes( cleanXml ); 
			//output.close();
			
			int responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();
			
			if (responseCode != 200) {
				logger.error(String.format("Service returned problem message -- %d:%s\\nrequest: %s", responseCode, responseMessage, sanitizedMsg));
				return null;
		    }

			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer inputLine = new StringBuffer();
			String tmp;
			while ((tmp = input.readLine()) != null) {
				inputLine.append(tmp);
			}
			input.close(); 
			
			return inputLine.toString();
		} 
		catch(Exception e) { 
			logger.error(String.format("Error: %s\\nRequest: %s",e.getMessage(), sanitizedMsg));
			return null;
		} 
		
		
	}
	
	private String sanitizeMessageForLogging(String xml){
		String cleanXml = xml.replaceAll("<ApplicationID>.+</ApplicationID>","<ApplicationID>xxx</ApplicationID>");
		cleanXml = cleanXml.replaceAll("<CardNumber>.+</CardNumber>","<CardNumber>xxx</CardNumber>");
		cleanXml = cleanXml.replaceAll("<CardExp>.+</CardExp>","<CardExp>xxx</CardExp>");
		cleanXml = cleanXml.replaceAll("<CardCVV>.+</CardCVV>","<CardCVV>xxx</CardCVV>");
		
		return cleanXml;
	}
}
