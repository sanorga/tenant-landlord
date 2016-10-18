package com.tea.landlordapp.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.service.helper.InviteHelper;
//import com.tea.landlordapp.service.helper.RevoPayHelper;
import com.tea.landlordapp.utility.StringHelper;
import com.tea.landlordapp.constant.Globals;

@Service("inviteService")
public class InviteServiceImpl implements InviteService{
	
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());
	
	private InviteHelper inviteHelper;

	private SystemPropertyDao systemPropertyDao;

		
	@Autowired
	public InviteServiceImpl(InviteHelper inviteHelper, SystemPropertyDao systemPropertyDao) {
		
		this.inviteHelper = inviteHelper;
		this.systemPropertyDao = systemPropertyDao;
		
	}
	
	@Override
	public void invite(AnonymousUser au) throws Exception {

			String apiUrl = systemPropertyDao.getPropertyValue(TransUnionApiParameter.URL);
			String partnerId = systemPropertyDao.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
			String key = systemPropertyDao.getPropertyValue(TransUnionApiParameter.KEY);
			String live = systemPropertyDao.getPropertyValue(TransUnionApiParameter.IS_LIVE);
			String apiCall = null, response=null;
			StringBuilder aurl = null;
			
			//-----------------------------------------------------------------
			//Create or update property - (POST or PUT)/LandlordApi/V1/Property
			//-----------------------------------------------------------------
			
			//  Create Header authorization Field
			//  1- Get Server Time
			aurl = new StringBuilder();
			aurl.append(apiUrl);
			aurl.append("ServerTime");
			apiCall = aurl.toString();
			
			// get Server time
			response=getRequest(apiCall, null);
			if (response == null) {
				logger.debug("no server time obtained");
				return;
			}
			
			Map<String,String> saleResponse = getAuthorizeResponseXml(response);
			String serverTime = saleResponse.get("dateTime");
			String serverTimeUpd = null;
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSSSSSS'Z'");
//			Date date = df.parse(serverTime);
//			
//			DateFormat dfnew = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss");

//			serverTimeUpd = dfnew.format(date);
			
			int zIndex = serverTime.indexOf('Z');
			int serverTimeLength = serverTime.length();
			if (zIndex == serverTimeLength - 1)				
				serverTimeUpd = serverTime.substring(0,zIndex - 8);
			else serverTimeUpd = serverTime;
			
			//  2- get a token
			
			String token = getSecurityToken(partnerId, key, serverTimeUpd);
			
			if (token == null){
				logger.debug("no token obtained");
				return;
			}
			
			// 3- add property
			
			aurl = new StringBuilder();
			aurl.append(apiUrl);
			aurl.append("Property");
			apiCall = aurl.toString();
			
			String credentials = null;
			StringBuilder strb = new StringBuilder();
			strb.append("smartmovepartner ");
			strb.append("partnerId=");
			strb.append("\"");
			strb.append(String.format("%3s", partnerId));
			strb.append("\"");
			strb.append(", serverTime=");
			strb.append("\"");
			strb.append(String.format("%19s", serverTimeUpd));
			strb.append("\"");
			strb.append(", securityToken=");
			strb.append("\"");
			strb.append(String.format("%s", token));
			strb.append("\"");
			credentials = strb.toString();
			
			Map<String, String> saleInfo = inviteHelper.buildInfoMap(au);
			
			if (Double.valueOf(saleInfo.get("RentalAmount")) <= 0) {
				return;
			}
			
			String xmlString = getXmlString(saleInfo,"ADDPROPERTY");
			String sanitizedXml = sanitizeMessageForLogging(xmlString);
			
			// post property
			response=postRequest(apiCall, xmlString, credentials);
			if (StringUtils.isBlank(response)) {
				logger.debug("no property request obtained");
				return;
			}
			
			Map<String,String> propertyResponse = getAuthorizeResponseXml(response);
			String propertyId = saleResponse.get("propertyId");
			
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
	
	private String getSecurityToken(String partnerId, String securityKey, String serverTime) {
		// TODO Auto-generated method stub
		
		// Concatenate the partnerId and the serverTime   
		String inputString = partnerId + serverTime;
		
		// Convert security key into ASCII bytes using utf8 encoding   
//		byte[] securityKeyBytes = UTF8Encoding.ASCII.GetBytes(securityKey);   
		byte[] securityKeyBytes = null;
		try {
			securityKeyBytes = securityKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create an HMACSHA1 hashing object that has been seeded with the security key bytes   
//		HMACSHA1 hasher = new HMACSHA1(securityKeyBytes);
        SecretKeySpec signingKey = new SecretKeySpec(securityKeyBytes, "HmacSHA1");
        Mac mac=null;
		try {
			mac = Mac.getInstance("HmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] inputBytes = null;
		try {
			inputBytes = inputString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] raw = mac.doFinal(inputBytes);
		String securityToken = Base64.getEncoder().encodeToString(raw); 

        
        
		// Convert input string into ASCII bytes using utf8 encoding   
//		byte[] inputBytes = UTF8Encoding.ASCII.GetBytes(inputString.toCharArray());

		
		// Compute the has value   
//		byte[] hash = hasher.ComputeHash(inputBytes);
		
		// Convert back to a base 64 string   
//		String securityToken = Convert.ToBase64String(hash);  

		return securityToken; 

	}

	private String getRequest(String apiCall, String xmlString) {
		// TODO Auto-generated method stub
		
		String cleanXml=null, sanitizedMsg = null;
		
		if (xmlString != null) {
			cleanXml = StringHelper.cleanXml(xmlString);
			sanitizedMsg = sanitizeMessageForLogging(cleanXml);
		}
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
		 
			if (cleanXml != null) 
				connection.setRequestProperty("Content-length",String.valueOf (cleanXml.length()));
		
			connection.setRequestProperty("Content-Type","application/xml"); 
			connection.setRequestProperty("Accept","application/xml"); 
			
			
			int responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();
			
			if (responseCode != 200) {
				logger.error(String.format("Service returned problem message -- %d:%s\\nrequest: %s", responseCode, responseMessage, sanitizedMsg));
				return null;
		    }
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
			result = sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}

	@Override
	public String postRequest(String apiUrl, String cleanXml, String credentials) {
		
		String sanitizedMsg = null;
		
//		if (xmlString != null) {
//			cleanXml = StringHelper.cleanXml(xmlString);
//			sanitizedMsg = sanitizeMessageForLogging(cleanXml);
//		}
		
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
			
			if (cleanXml!= null)
				connection.setRequestProperty("Content-length",String.valueOf (cleanXml.length())); 
			
//			connection.setRequestProperty("Content-Type","text/xml"); 
			connection.setRequestProperty("Content-Type","application/xml"); 
			connection.setRequestProperty("Accept","application/xml"); 
			// open up the output stream of the connection 
			
//			String Credentials = String.format("{0}:{1},{2}:{3},{4}:{5}",
//					"[USERNAME]", "[PASSWORD]");
			connection.setRequestProperty("Authorization", credentials);
			
			DataOutputStream output = new DataOutputStream( connection.getOutputStream() ); 

			// write out the data 
			int queryLength = cleanXml.length(); 
			output.writeBytes( cleanXml ); 
//			//output.close();
			
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
	
	private Map<String, String> getAuthorizeResponseXml(String resp) throws ParserConfigurationException, SAXException, IOException {
		
		String serverTime = null;
		Map<String, String> mapResponse =  new HashMap<String,String>();
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(resp)));
		doc.getDocumentElement().normalize();
		String nodeNameStr = doc.getDocumentElement().getNodeName();
       	Element root = doc.getDocumentElement();
		
       	if (nodeNameStr == "dateTime") {
       		mapResponse.put("dateTime", root.getFirstChild().getNodeValue());
       		return mapResponse;
       	}
       	
       	if (nodeNameStr == "PropertyId") {
       		mapResponse.put("propertyId", root.getFirstChild().getNodeValue());
       		return mapResponse;
       	}
       	
		if (nodeNameStr == "Fault"){
	           mapResponse.put("faultcode", root.getElementsByTagName("faultcode").item(0).getTextContent());
	           mapResponse.put("faultmessage", root.getElementsByTagName("faultmessage").item(0).getTextContent());
	   	}
		else {
			mapResponse.put("faultcode","");
			NodeList nList = doc.getElementsByTagName("dateTime");
//			NodeList nList = doc.getElementsByTagName("RSData");
	        	        
	        Node nNode = nList.item(0);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		           Element eElement = (Element) nNode;
		           mapResponse.put("dateTime", eElement.getElementsByTagName("dateTime").item(0).getTextContent());
//		           mapResponse.put("RefId", eElement.getElementsByTagName("RefId").item(0).getTextContent());
//		           mapResponse.put("StatusCode", eElement.getElementsByTagName("StatusCode").item(0).getTextContent());
//		           mapResponse.put("StatusMsg", eElement.getElementsByTagName("StatusMsg").item(0).getTextContent());
//		           mapResponse.put("ApprovalCode", eElement.getElementsByTagName("ApprovalCode").item(0).getTextContent());
		        }
			}

		return mapResponse;
	}
	
	private String getXmlString(Map<String, String> saleInfo, String inviteFunction) {

		
//		String transId = UUID.randomUUID().toString().replaceAll("-", "");
		
//		if (dto.getAipId() != null) {
//			transId = "AIP-" + dto.getAipId().toString();
//		}
//		if (dto.getAppId() != null) {
//			transId = "APP-" + dto.getAppId().toString();
//		}
		String xmlString = null, cleanXml=null;
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		
		switch (inviteFunction) {
		case "ADDPROPERTY":
			sb.append("<PropertyIdentifier>");
			sb.append(saleInfo.get(Globals.TU_PROPERTY_IDENTIFIER));
			sb.append("</PropertyIdentifier>");
			sb.append("<Active>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_ACTIVE)));
			sb.append("</Active>");
			sb.append("<Name>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_NAME)));
			sb.append("</Name>");
			sb.append("<UnitNumber>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_UNIT_NUMBER)));
			sb.append("</UnitNumber>");
			sb.append("<FirstName>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_FIRSTNAME)));
			sb.append("</FirstName>");
			sb.append("<LastName>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_LASTNAME)));
			sb.append("</LastName>");
			sb.append("<Street>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_STREET)));
			sb.append("</Street>");
			sb.append("<City>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_CITY)));
			sb.append("</City>");
			sb.append("<State>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_STATE)));
			sb.append("</State>");
			sb.append("<Zip>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_ZIP)));
			sb.append("</Zip>");
			sb.append("<Phone>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_PHONE)));
			sb.append("</Phone>");
			sb.append("<PhoneExtension>");
			sb.append(String.format("%s",saleInfo.get(Globals.TU_PHONE_EXTENSION)));
			sb.append("</PhoneExtension>");
			sb.append("<Questions>");
			sb.append("<Question>");
			sb.append("<QuestionId>");
			sb.append("1");
			sb.append("</QuestionId>");
			sb.append("<QuestionText>");
			sb.append("How would you describe your property?");
			sb.append("</QuestionText>");
			sb.append("<Options>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("A");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("B");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("C");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Low-end property offering few, if any, amenities or conveniences to renters (for example laundry facilities, fitness facility, etc.). If the property has been recently built, the quality of materials used for common features of the property and/or unit(s) is average or bulk building supplies. For somewhat aged properties, few, if any, renovations have been applied to update common-use features (for example water heaters, appliances, bathroom fixtures, paint, etc.). 'C' properties employ a high risk factor reflecting a lower quality offering to potential renters and allowing lower standards for potential lessees.");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("</Options>");
			sb.append("<SelectedAnswer>");
			sb.append("A");
			sb.append("</SelectedAnswer>");
			sb.append("</Question>");

			sb.append("<Question>");
			sb.append("<QuestionId>");
			sb.append("2");
			sb.append("</QuestionId>");
			sb.append("<QuestionText>");
			sb.append("How does your unit(s)'s rent compare to others in the neighborhood?");
			sb.append("</QuestionText>");
			sb.append("<Options>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("A");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be significantly higher than expected rent");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("B");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be somewhat higher than expected rent");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("C");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be just above the expected rent");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("</Options>");
			sb.append("<SelectedAnswer>");
			sb.append("C");
			sb.append("</SelectedAnswer>");
			sb.append("</Question>");			
			
			sb.append("<Question>");
			sb.append("<QuestionId>");
			sb.append("3");
			sb.append("</QuestionId>");
			sb.append("<QuestionText>");
			sb.append("What do you expect the average income of your potential applicants to be?");
			sb.append("</QuestionText>");
			sb.append("<Options>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("A");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be much greater than the average income for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("B");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be at the average income for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("C");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Average applicant income will be below the average income for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("</Options>");
			sb.append("<SelectedAnswer>");
			sb.append("C");
			sb.append("</SelectedAnswer>");
			sb.append("</Question>");
			
			sb.append("<Question>");
			sb.append("<QuestionId>");
			sb.append("4");
			sb.append("</QuestionId>");
			sb.append("<QuestionText>");
			sb.append("Do you expect the average income of your potential applicnats to be above, at, or below the average income of other tenants in the neighborhood?");
			sb.append("</QuestionText>");
			sb.append("<Options>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("A");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expected rent will be greater than the average rent for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("B");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expected rent will be at the average rent for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("C");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expected rent will be below the average rent for the area");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("</Options>");
			sb.append("<SelectedAnswer>");
			sb.append("B");
			sb.append("</SelectedAnswer>");
			sb.append("</Question>");
			
			sb.append("<Question>");
			sb.append("<QuestionId>");
			sb.append("5");
			sb.append("</QuestionId>");
			sb.append("<QuestionText>");
			sb.append("Do you expect many applicants to apply for your unit(s)?");
			sb.append("</QuestionText>");
			sb.append("<Options>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("A");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expect many applicants and good visibility for these units");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("B");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expect a steady number of applicants with average visibility for these unit(s)");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("<Answer>");
			sb.append("<AnswerText>");
			sb.append("C");
			sb.append("</AnswerText>");
			sb.append("<AnswerDescription>");
			sb.append("Expect few applicants");
			sb.append("</AnswerDescription>");
			sb.append("</Answer>");
			sb.append("</Options>");
			sb.append("<SelectedAnswer>");
			sb.append("A");
			sb.append("</SelectedAnswer>");
			sb.append("</Question>");
			
			sb.append("</Questions>");
			
			
			sb.append("<Classification>");
			sb.append("Conventional");
			sb.append("</Classification>");
			sb.append("<IR>");
			sb.append("2");
			sb.append("</IR>");
			sb.append("<IncludeMedicalCollections>");
			sb.append("false");
			sb.append("</IncludeMedicalCollections>");
			sb.append("<IncludeForeclosures>");
			sb.append("false");
			sb.append("</IncludeForeclosures>");
			sb.append("<DeclineOpenBankruptcies>");
			sb.append("false");
			sb.append("</DeclineOpenBankruptcies>");
			sb.append("<OpenBankruptcyWindow>");
			sb.append("0");
			sb.append("</OpenBankruptcyWindow>");
			sb.append("<IsFcraAgreementAccepted>");
			sb.append("true");
			sb.append("</IsFcraAgreementAccepted>");


//			sb.append("<CFee>");
//			sb.append(String.format("%s","0.00"));
			xmlString = sb.toString();
			break;
		case "ECAUTHORIZE":

			sb.append("<ApplicationID>");
			sb.append(String.format("%s",saleInfo.get(Globals.RP_APPLICATION_ID)));

			sb.append("<ReferenceNumber>");
			sb.append(String.format("%s",""));
			sb.append("</ReferenceNumber>");

			xmlString = sb.toString();
			break;
		case "VOID":
			sb.append("<Void>");
			sb.append("<RVData>");
			sb.append("<ApplicationID>");
			sb.append(String.format("%s",saleInfo.get(Globals.RP_APPLICATION_ID)));
			sb.append("</ApplicationID>");
			sb.append("<MerchantID>");
			sb.append(String.format("%s",saleInfo.get(Globals.RP_MERCHANT_ID)));
			sb.append("</MerchantID>");
			sb.append("<RefId>");
			sb.append(String.format("%s",""));
			sb.append("</RefId>");	
			sb.append("</RVData>");
			sb.append("<Void>");
			xmlString = sb.toString();
			break;
		case "REFUND":
			sb.append("<Refund>");

			xmlString = sb.toString();
			break;
		default:
//			dto.setSuccessful(false);
			return xmlString;
		}
		if (xmlString != null) {
			cleanXml = StringHelper.cleanXml(xmlString);
		}
		
		return cleanXml;
	}

	
}
