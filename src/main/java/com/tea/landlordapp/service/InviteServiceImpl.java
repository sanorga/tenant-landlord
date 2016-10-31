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
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.xml.xpath.XPathExpressionException;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.service.helper.InviteHelper;
//import com.tea.landlordapp.service.helper.RevoPayHelper;
import com.tea.landlordapp.utility.StringHelper;
//import com.tea.landlordapp.utility.HttpURLConnectionFacade;
//import com.tea.landlord.exception.DocusignMessageException;
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
	public Map<String, String> invite(AnonymousUser au) throws Exception {
			
			Map<String, String> result = new HashMap<String,String>();
			String propertyExtIdStr = null;
			String apiUrl = systemPropertyDao.getPropertyValue(TransUnionApiParameter.URL);
			String partnerId = systemPropertyDao.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
			String key = systemPropertyDao.getPropertyValue(TransUnionApiParameter.KEY);
			String live = systemPropertyDao.getPropertyValue(TransUnionApiParameter.IS_LIVE);
	
			if (au.getProperty() == null) {
				return null;
			}
		
			Property p = au.getProperty();
			Integer propertyExtId = p.getPropertyExtId();
			propertyExtIdStr = propertyExtId.toString();
			
				
			
			//-----------------------------------------------------------------
			//Create Application - (POST)/LandlordApi/V1/Application
			//-----------------------------------------------------------------

			Map<String, String> applicationResultMap = addApplication(apiUrl, partnerId, key, au, propertyExtIdStr);
			if (applicationResultMap == null) {
				logger.debug("no application obtained");
				return null;
			}
			String applicationExtIdStr = applicationResultMap.get("applicationExtIdStr");
			String applicantEmail= applicationResultMap.get("applicantEmail");
			String coapplicantEmail = applicationResultMap.get("coapplicantEmail");
			if (applicationExtIdStr == "0" || applicationExtIdStr == null){
				logger.debug("no application id obtained");
				return null;
			}
		
			result.put("applicationExtIdStr", applicationExtIdStr);
			result.put("applicantEmail", applicantEmail);
			result.put("coapplicantEmail",coapplicantEmail);
			return result;
	}

	@Override
	public Map<String, String> submitProperty (AnonymousUser au) throws Exception {
			
			Map<String, String> result = new HashMap<String,String>();
			String propertyExtIdStr = null, organizationIdStr = null;
			String apiUrl = systemPropertyDao.getPropertyValue(TransUnionApiParameter.URL);
			String partnerId = systemPropertyDao.getPropertyValue(TransUnionApiParameter.PARTNER_ID);
			String key = systemPropertyDao.getPropertyValue(TransUnionApiParameter.KEY);
			String live = systemPropertyDao.getPropertyValue(TransUnionApiParameter.IS_LIVE);
	
			if (au.getProperty() == null) {
				return null;
			}
		
			Property p = au.getProperty();
			Integer propertyExtId = p.getPropertyExtId();
			Integer organizationId = p.getOrganizationId();
			String organizationName = p.getOrganizationName();
			String propertyIdentifier = p.getPropertyIdentifier();
			
			if (propertyExtId == 0) {
				//-----------------------------------------------------------------
				//No property in SmartMove - Create property in SmartMove - (POST)/LandlordApi/V1/Property
				//-----------------------------------------------------------------
				Map<String, String> propertyResultMap = addProperty(apiUrl, partnerId, key, au);
				if (propertyResultMap == null) {
					logger.debug("no property obtained");
					return null;
				}
				
				propertyExtIdStr = propertyResultMap.get("propertyExtIdStr");
				if (propertyExtIdStr == "0" || propertyExtIdStr == null){
					logger.debug("no property id obtained");
					return null;
				}
				propertyIdentifier = propertyResultMap.get("propertyIdentifier");
				organizationIdStr = propertyResultMap.get("organizationIdStr");
				organizationName = propertyResultMap.get("organizationName");
	//			propertyIdStr = "41704";
			}
			else {
				if (organizationId == 0  || organizationName == null || propertyIdentifier == null) {
					logger.debug("property already in SmartMove but with incomplete information");
					return null;
					}
					
				propertyExtIdStr = propertyExtId.toString();
				organizationIdStr = organizationId.toString();
				
			}
			
			result.put("propertyExtIdStr", propertyExtIdStr);
			result.put("propertyIdentifier", propertyIdentifier);
			result.put("organizationIdStr", organizationIdStr);
			result.put("organizationName", organizationName);

			return result;
	}
	
	private Map<String,String> addApplication (String apiUrl, String partnerId, 
								   String key, AnonymousUser au,
								   String propertyIdStr) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		
		String apiCall = null, response=null;
		StringBuilder aurl = null;
		Map<String, String> responseMap;
		String responseCodeStr = null, responseMessage = null;
		Integer responseCode = 0;
		
		//call
		aurl = new StringBuilder();
		aurl.append(apiUrl);
		aurl.append("Application");
		apiCall = aurl.toString();
			
		Map<String, String> applicationInfo = inviteHelper.buildApplicationInfoMap(au, propertyIdStr);
		
//		if (Double.valueOf(applicationInfo.get("RentalAmount")) <= 0) {
//			return "Error:ZeroRentalAmount";
//		}
		
//		String xmlString = getXmlString(propertyInfo,"ADDPROPERTY");
		String jsonString = getJSONString(applicationInfo,"ADDAPPLICATION");
//		String sanitizedXml = sanitizeMessageForLogging(jsonString);
		
		// get credentials
		String credentials = getCredentials(apiUrl, partnerId, key);
		if (credentials == null){
			logger.debug("no credentials obtained");
			return null;
		}
		
		// post property
//		response=postRequest(apiCall, xmlString, credentials);
		responseMap=postJSONMessageGetJSON(apiCall, jsonString, credentials);
		
		responseCodeStr = responseMap.get("responseCode");
		responseCode = Integer.valueOf(responseCodeStr);
		if (responseCode != 201) {
			logger.debug("no application request obtained");
			return null;
		}
		
		response = responseMap.get("response");
		JsonObject applicationResponseJson = getAuthorizeResponseJson(response);
		String applicationExtIdStr = applicationResponseJson.get("ApplicationId").getAsString();
		Integer applicationExtId;
		try {
			applicationExtId = Integer.valueOf(applicationExtIdStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		JsonElement jsonElement = new JsonObject();
		String email = null;
		JsonArray jsonArray = applicationResponseJson.getAsJsonArray("Applicants");
		for(int i=0; i<jsonArray.size(); i++){
			email = jsonArray.get(i).getAsString();
//			email = jsonElement.getAsJsonObject().get("EmailAddress").getAsString();
			if (i==0){
				responseMap.put("applicantEmail",email);		
			}
			else responseMap.put("coapplicantEmail",email);
		}
		responseMap.put("applicationExtIdStr",applicationExtIdStr);		
		return responseMap;
				
	}
	
	private Map<String, String> addProperty(String apiUrl, String partnerId, String key, AnonymousUser au) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		
		Map<String, String> responseMap;
		String responseCodeStr = null, responseMessage = null;
		Integer responseCode = 0;
		String apiCall = null, response=null;
		StringBuilder aurl = null;
		
		//call
		aurl = new StringBuilder();
		aurl.append(apiUrl);
		aurl.append("Property");
		apiCall = aurl.toString();
			
		Map<String, String> propertyInfo = inviteHelper.buildPropertyInfoMap(au);
		
//		if (Double.valueOf(propertyInfo.get("RentalAmount")) <= 0) {
//			return "Error:ZeroRentalAmount";
//		}
		
//		String xmlString = getXmlString(propertyInfo,"ADDPROPERTY");
		String jsonString = getJSONString(propertyInfo,"ADDPROPERTY");
//		String sanitizedXml = sanitizeMessageForLogging(jsonString);
		
		// get credentials
		String credentials = getCredentials(apiUrl, partnerId, key);
		if (credentials == null){
			logger.debug("no credentials obtained");
			return null;
		}
		
		// post property
//		response=postRequest(apiCall, xmlString, credentials);
		responseMap=postJSONMessageGetJSON(apiCall, jsonString, credentials);

		responseCodeStr = responseMap.get("responseCode");
		responseCode = Integer.valueOf(responseCodeStr);
		if (responseCode != 201) {
			logger.debug("no property request obtained");
			return null;
		}
		
		response = responseMap.get("response");
		JsonObject propertyResponseJson = getAuthorizeResponseJson(response);
		String propertyExtIdStr = propertyResponseJson.get("PropertyId").getAsString();
		String organizationIdStr = propertyResponseJson.get("OrganizationId").getAsString();
		String organizationName = propertyResponseJson.get("OrganizationName").getAsString();
		String propertyIdentifier = propertyResponseJson.get("PropertyIdentifier").getAsString();
		Integer propertyExtId;
		try {
			propertyExtId = Integer.valueOf(propertyExtIdStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		responseMap.put("propertyExtIdStr",propertyExtIdStr);	
		responseMap.put("organizationIdStr",organizationIdStr);
		responseMap.put("propertyIdentifier",propertyIdentifier);
		responseMap.put("organizationName",organizationName);
		return responseMap;
				
	}
			
	
	private String getCredentials(String apiUrl, String partnerId, String key) throws ParserConfigurationException, SAXException, IOException{
		
		// 1- get serverTime
		String serverTime = getServerTime(apiUrl);
		if (serverTime == null){
			logger.debug("no server time obtained");
			return "Error:NoServerTimeObtained";
		}
		
		//  2- get a token
		String token = getSecurityToken(apiUrl, partnerId, key, serverTime);
		
		if (token == null){
			logger.debug("no token obtained");
			return "Error:NoTokenObtained";
		}
		
		//  3- get credentials
		String credentials = null;
		StringBuilder strb = new StringBuilder();
		strb.append("smartmovepartner ");
		strb.append("partnerid=");
		strb.append("\"");
		strb.append(String.format("%3s", partnerId));
		strb.append("\"");
		strb.append(",servertime=");
		strb.append("\"");
		strb.append(String.format("%19s", serverTime));
		strb.append("\"");
		strb.append(",securitytoken=");
		strb.append("\"");
		strb.append(String.format("%s", token));
		strb.append("\"");
		credentials = strb.toString();
		
		return credentials;
	}
	
	private String getServerTime(String apiUrl) throws ParserConfigurationException, SAXException, IOException  {

		//  1- Get Server Time
		StringBuilder aurl = new StringBuilder();
		aurl.append(apiUrl);
		aurl.append("ServerTime");
		String apiCall = aurl.toString();
		
		// get Server time
		String response=getRequest(apiCall, null);
		if (response == null) {
			logger.debug("no server time obtained");
			return "Error:NoServerTimeObtained";
		}
		
		Map<String,String> saleResponse = getAuthorizeResponseXml(response);
		String serverTime = saleResponse.get("dateTime");
		String serverTimeUpd = null;
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSSSSSS'Z'");
//		Date date = df.parse(serverTime);
//		
//		DateFormat dfnew = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss");

//		serverTimeUpd = dfnew.format(date);
		
		int zIndex = serverTime.indexOf('Z');
		int serverTimeLength = serverTime.length();
		if (zIndex == serverTimeLength - 1)				
			serverTimeUpd = serverTime.substring(0,zIndex - 8);
		else serverTimeUpd = serverTime;
		return serverTimeUpd;
	
	}
	
	private String getSecurityToken(String apiUrl, String partnerId, String securityKey, String serverTimeUpd) throws ParserConfigurationException, SAXException, IOException {
		
		// Concatenate the partnerId and the serverTime   
		String inputString = partnerId + serverTimeUpd;
		
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
	public String postRequest(String apiUrl, String xmlString, String credentials) {
		
		String sanitizedMsg = null, cleanXml = null;
		
		if (xmlString != null) {
			cleanXml = StringHelper.cleanXml(xmlString);
			sanitizedMsg = sanitizeMessageForLogging(cleanXml);
		}
		
		try { 
			
//			System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
			URL url = new URL(apiUrl); 
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//			connection.setHostnameVerifier(new RevoPayHostnameVerifier());
			connection.setDoInput(true); 
			connection.setDoOutput(true);
			 
			connection.setRequestMethod("POST"); 
//			connection.setFollowRedirects(true); 
			
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
				logger.error(String.format("Service returned problem message -- %d:%s\\nrequest: %s", responseCode, responseMessage, cleanXml));
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
	
	private JsonObject getAuthorizeResponseJson(String resp) {
		JsonParser parser = new JsonParser();
		JsonObject resJson = (JsonObject) parser.parse(resp);
		return resJson;
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
	
//	private String getXmlString(Map<String, String> propertyInfo, String inviteFunction) {
//	
//		String xmlString = null, cleanXml=null;
//		StringBuilder sb = new StringBuilder("\""?xml version=\"1.0\" encoding=\"utf-8\"?>");
//		
//		switch (inviteFunction) {
//		case "ADDPROPERTY":
//			sb.append("{");
//
////			sb.append("\""PropertyId>");
////			sb.append(String.format("%s",propertyInfo.get(Globals.TU_ID)));
////			sb.append("\""/PropertyId>");
//			sb.append("\""PropertyIdentifier>");
//			sb.append(propertyInfo.get(Globals.TU_PROPERTY_IDENTIFIER));
//			sb.append("\""/PropertyIdentifier>");
//			sb.append("\""Active>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_ACTIVE)));
//			sb.append("\""/Active>");
//			sb.append("\""Name>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_NAME)));
//			sb.append("\""/Name>");
//			sb.append("\""UnitNumber>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_UNIT_NUMBER)));
//			sb.append("\""/UnitNumber>");
//			sb.append("\""FirstName>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_FIRSTNAME)));
//			sb.append("\""/FirstName>");
//			sb.append("\""LastName>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_LASTNAME)));
//			sb.append("\""/LastName>");
//			sb.append("\""Street>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_STREET)));
//			sb.append("\""/Street>");
//			sb.append("\""City>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_CITY)));
//			sb.append("\""/City>");
//			sb.append("\""State>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_STATE)));
//			sb.append("\""/State>");
//			sb.append("\""Zip>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_ZIP)));
//			sb.append("\""/Zip>");
//			sb.append("\""Phone>");
//			sb.append(String.format("%s",propertyInfo.get(Globals.TU_PHONE)));
//			sb.append("\""/Phone>");
//			if (propertyInfo.get(Globals.TU_PHONE_EXTENSION)!= null) {
//				sb.append("\""PhoneExtension>");
//				sb.append(String.format("%s",propertyInfo.get(Globals.TU_PHONE_EXTENSION)));
//				sb.append("\""/PhoneExtension>");
//			}
//			sb.append("\""Questions>");
//			sb.append("\""e>");
//			sb.append("\""QuestionId>");
//			sb.append("1");
//			sb.append("\""/QuestionId>");
//			sb.append("\""QuestionText>");
//			sb.append("How would you describe your property?");
//			sb.append("\""/QuestionText>");
//			sb.append("\""Options>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("A");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("B");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("C");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Low-end property offering few, if any, amenities or conveniences to renters (for example laundry facilities, fitness facility, etc.). If the property has been recently built, the quality of materials used for common features of the property and/or unit(s) is average or bulk building supplies. For somewhat aged properties, few, if any, renovations have been applied to update common-use features (for example water heaters, appliances, bathroom fixtures, paint, etc.). 'C' properties employ a high risk factor reflecting a lower quality offering to potential renters and allowing lower standards for potential lessees.");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""/Options>");
//			sb.append("\""SelectedAnswer>");
//			sb.append("A");
//			sb.append("\""/SelectedAnswer>");
//			sb.append("\""/e>");
//
//			sb.append("\""e>");
//			sb.append("\""QuestionId>");
//			sb.append("2");
//			sb.append("\""/QuestionId>");
//			sb.append("\""QuestionText>");
//			sb.append("How does your unit(s)'s rent compare to others in the neighborhood?");
//			sb.append("\""/QuestionText>");
//			sb.append("\""Options>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("A");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be significantly higher than expected rent");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("B");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be somewhat higher than expected rent");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("C");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be just above the expected rent");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""/Options>");
//			sb.append("\""SelectedAnswer>");
//			sb.append("C");
//			sb.append("\""/SelectedAnswer>");
//			sb.append("\""/e>");			
//			
//			sb.append("\""e>");
//			sb.append("\""QuestionId>");
//			sb.append("3");
//			sb.append("\""/QuestionId>");
//			sb.append("\""QuestionText>");
//			sb.append("What do you expect the average income of your potential applicants to be?");
//			sb.append("\""/QuestionText>");
//			sb.append("\""Options>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("A");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be much greater than the average income for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("B");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be at the average income for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("C");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Average applicant income will be below the average income for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""/Options>");
//			sb.append("\""SelectedAnswer>");
//			sb.append("C");
//			sb.append("\""/SelectedAnswer>");
//			sb.append("\""/e>");
//			
//			sb.append("\""e>");
//			sb.append("\""QuestionId>");
//			sb.append("4");
//			sb.append("\""/QuestionId>");
//			sb.append("\""QuestionText>");
//			sb.append("Do you expect the average income of your potential applicnats to be above, at, or below the average income of other tenants in the neighborhood?");
//			sb.append("\""/QuestionText>");
//			sb.append("\""Options>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("A");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expected rent will be greater than the average rent for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("B");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expected rent will be at the average rent for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("C");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expected rent will be below the average rent for the area");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""/Options>");
//			sb.append("\""SelectedAnswer>");
//			sb.append("B");
//			sb.append("\""/SelectedAnswer>");
//			sb.append("\""/e>");
//			
//			sb.append("\""e>");
//			sb.append("\""QuestionId>");
//			sb.append("5");
//			sb.append("\""/QuestionId>");
//			sb.append("\""QuestionText>");
//			sb.append("Do you expect many applicants to apply for your unit(s)?");
//			sb.append("\""/QuestionText>");
//			sb.append("\""Options>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("A");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expect many applicants and good visibility for these units");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("B");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expect a steady number of applicants with average visibility for these unit(s)");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""e>");
//			sb.append("\""AnswerText>");
//			sb.append("C");
//			sb.append("\""/AnswerText>");
//			sb.append("\""AnswerDescription>");
//			sb.append("Expect few applicants");
//			sb.append("\""/AnswerDescription>");
//			sb.append("\""/e>");
//			sb.append("\""/Options>");
//			sb.append("\""SelectedAnswer>");
//			sb.append("A");
//			sb.append("\""/SelectedAnswer>");
//			sb.append("\""/e>");
//			
//			sb.append("\""/Questions>");
//			
//			
//			sb.append("\""Classification>");
//			sb.append("Conventional");
//			sb.append("\""/Classification>");
//			sb.append("\""IR>");
//			sb.append("2.0");
//			sb.append("\""/IR>");
////			sb.append("\""IncludeMedicalCollections>");
////			sb.append("false");
////			sb.append("\""/IncludeMedicalCollections>");
////			sb.append("\""IncludeForeclosures>");
////			sb.append("false");
////			sb.append("\""/IncludeForeclosures>");
////			sb.append("\""DeclineForOpenBankruptcies>");
////			sb.append("false");
////			sb.append("\""/DeclineForOpenBankruptcies>");
////			sb.append("\""OpenBankruptcyWindow>");
////			sb.append("0");
////			sb.append("\""/OpenBankruptcyWindow>");
//			sb.append("\""IsFcraAgreementAccepted>");
//			sb.append("true");
//			sb.append("\""/IsFcraAgreementAccepted>");
//			sb.append("\""/Property>");
//
//
////			sb.append("\""CFee>");
////			sb.append(String.format("%s","0.00"));
//			xmlString = sb.toString();
//			break;
//
//		default:
////			dto.setSuccessful(false);
//			return xmlString;
//		}
//		if (xmlString != null) {
//			cleanXml = StringHelper.cleanXml(xmlString);
//		}
//		
//		return cleanXml;
//	}

	private String getJSONString(Map<String, String> entityInfo, String inviteFunction) {
		
		String jsonString = null, cleanXml=null;
		StringBuilder sb = new StringBuilder();
		
		switch (inviteFunction) {
		case "ADDPROPERTY":
			sb.append("{");
//			sb.append("\"PropertyId\": \"");
//			sb.append(String.format("%s",entityInfo.get(Globals.TU_ID)));
//			sb.append("\","PropertyId\": \"");
			sb.append("\"PropertyIdentifier\": \"");
			sb.append(entityInfo.get(Globals.TU_PROPERTY_IDENTIFIER));
			sb.append("\",");
			sb.append("\"Active\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_ACTIVE)));
			sb.append("\",");
			sb.append("\"Name\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_NAME)));
			sb.append("\",");
			sb.append("\"UnitNumber\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_UNIT_NUMBER)));
			sb.append("\",");
			sb.append("\"FirstName\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_FIRSTNAME)));
			sb.append("\",");
			sb.append("\"LastName\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_LASTNAME)));
			sb.append("\",");
			sb.append("\"Street\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_STREET)));
			sb.append("\",");
			sb.append("\"City\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_CITY)));
			sb.append("\",");
			sb.append("\"State\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_STATE)));
			sb.append("\",");
			sb.append("\"Zip\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_ZIP)));
			sb.append("\",");
			sb.append("\"Phone\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_PHONE)));
			sb.append("\",");
			if (entityInfo.get(Globals.TU_PHONE_EXTENSION)!= null) {
				sb.append("\"PhoneExtension\": \"");
				sb.append(String.format("%s",entityInfo.get(Globals.TU_PHONE_EXTENSION)));
				sb.append("\",");
			}
			sb.append("\"Questions\": ");
			sb.append("[{");
			sb.append("\"QuestionId\": \"");
			sb.append("1");
			sb.append("\",");
			sb.append("\"QuestionText\": \"");
			sb.append("How would you describe your property?");
			sb.append("\",");
			sb.append("\"Options\": ");
			sb.append("[{");
			sb.append("\"AnswerText\": \"");
			sb.append("A");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("B");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("C");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Low-end property offering few, if any, amenities or conveniences to renters (for example laundry facilities, fitness facility, etc.). If the property has been recently built, the quality of materials used for common features of the property and/or unit(s) is average or bulk building supplies. For somewhat aged properties, few, if any, renovations have been applied to update common-use features (for example water heaters, appliances, bathroom fixtures, paint, etc.). 'C' properties employ a high risk factor reflecting a lower quality offering to potential renters and allowing lower standards for potential lessees.");
			sb.append("\"}],");
			sb.append("\"SelectedAnswer\": \"");
			sb.append("A");
			sb.append("\"},{");

			sb.append("\"QuestionId\": \"");
			sb.append("2");
			sb.append("\",");
			sb.append("\"QuestionText\": \"");
			sb.append("How does your unit(s)'s rent compare to others in the neighborhood?");
			sb.append("\",");
			sb.append("\"Options\": ");
			sb.append("[{");
			sb.append("\"AnswerText\": \"");
			sb.append("A");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be significantly higher than expected rent");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("B");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be somewhat higher than expected rent");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("C");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be just above the expected rent");
			sb.append("\"}],");
			sb.append("\"SelectedAnswer\": \"");
			sb.append("C");
			sb.append("\"},{");

			sb.append("\"QuestionId\": \"");
			sb.append("3");
			sb.append("\",");
			sb.append("\"QuestionText\": \"");
			sb.append("What do you expect the average income of your potential applicants to be?");
			sb.append("\",");
			sb.append("\"Options\": ");
			sb.append("[{");
			sb.append("\"AnswerText\": \"");
			sb.append("A");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be much greater than the average income for the area");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("B");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be at the average income for the area");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("C");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Average applicant income will be below the average income for the area");
			sb.append("\"}],");
			sb.append("\"SelectedAnswer\": \"");
			sb.append("C");
			sb.append("\"},{");
			
			sb.append("\"QuestionId\": \"");
			sb.append("4");
			sb.append("\",");
			sb.append("\"QuestionText\": \"");
			sb.append("Do you expect the average income of your potential applicnats to be above, at, or below the average income of other tenants in the neighborhood?");
			sb.append("\",");
			sb.append("\"Options\": ");
			sb.append("[{");
			sb.append("\"AnswerText\": \"");
			sb.append("A");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expected rent will be greater than the average rent for the area");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("B");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expected rent will be at the average rent for the area");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("C");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expected rent will be below the average rent for the area");
			sb.append("\"}],");
			sb.append("\"SelectedAnswer\": \"");
			sb.append("B");
			sb.append("\"},{");
			
			sb.append("\"QuestionId\": \"");
			sb.append("5");
			sb.append("\",");
			sb.append("\"QuestionText\": \"");
			sb.append("Do you expect many applicants to apply for your unit(s)?");
			sb.append("\",");
			sb.append("\"Options\": ");
			sb.append("[{");
			sb.append("\"AnswerText\": \"");
			sb.append("A");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expect many applicants and good visibility for these units");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("B");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expect a steady number of applicants with average visibility for these unit(s)");
			sb.append("\"},{");
			sb.append("\"AnswerText\": \"");
			sb.append("C");
			sb.append("\",");
			sb.append("\"AnswerDescription\": \"");
			sb.append("Expect few applicants");
			sb.append("\"}],");
			sb.append("\"SelectedAnswer\": \"");
			sb.append("A");
			sb.append("\"}],");
			
			
			sb.append("\"Classification\": \"");
			sb.append("Conventional");
			sb.append("\",");
			sb.append("\"IR\": \"");
			sb.append("2");
			sb.append("\",");
//			sb.append("\"IncludeMedicalCollections\": \"");
//			sb.append("false");
//			sb.append("\","IncludeMedicalCollections\": \"");
//			sb.append("\"IncludeForeclosures\": \"");
//			sb.append("false");
//			sb.append("\""/IncludeForeclosures\": \"");
			sb.append("\"IncludeMedicalCollections\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_INCLUDE_MEDICAL_COLLECTIONS)));
			sb.append("\",");
			sb.append("\"IncludeForeclosures\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_INCLUDE_FORECLOSURES)));
			sb.append("\",");
			sb.append("\"DeclineForOpenBankruptcies\": \"");
			sb.append("false");
			sb.append("\",");
			sb.append("\"OpenBankruptcyWindow\": \"");
			sb.append("6");
			sb.append("\",");
			sb.append("\"IsFcraAgreementAccepted\": \"");
			sb.append("true");
			sb.append("\"");
			sb.append("}");


//			sb.append("\""CFee>");
//			sb.append(String.format("%s","0.00"));
			jsonString = sb.toString();
			break;
		case "ADDAPPLICATION":
			sb.append("{");
			sb.append("\"Applicants\": [ \"");
			sb.append(entityInfo.get(Globals.TU_APPLICANT_EMAIL));
			sb.append("\"");
			if (StringUtils.isNotBlank(entityInfo.get(Globals.TU_COAPPLICANT_EMAIL) )){
				sb.append(",\"");
				sb.append(entityInfo.get(Globals.TU_COAPPLICANT_EMAIL));
				sb.append("\"");
			}
			sb.append("],");
			sb.append("\"PropertyId\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_ID)));
			sb.append("\",");	
			sb.append("\"LeaseTermInMonths\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_LEASE_TERM_IN_MONTHS)));
			sb.append("\",");	
			sb.append("\"Rent\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_RENT)));
			sb.append("\",");
			sb.append("\"Deposit\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_DEPOSIT)));
			sb.append("\",");
			sb.append("\"UnitNumber\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_UNIT_NUMBER)));
			sb.append("\",");
			sb.append("\"ProductBundle\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_PRODUCT_BUNDLE)));
			sb.append("\",");
			sb.append("\"LandLordPays\": \"");
			sb.append(String.format("%s",entityInfo.get(Globals.TU_LANDLORD_PAYS)));
			sb.append("\" }");
			jsonString = sb.toString();
			break;
		default:
//			dto.setSuccessful(false);
			return jsonString;
		}
//		if (jsonString != null) {
//			cleanXml = StringHelper.cleanXml(xmlString);
//		}
//		
//		return cleanXml;
		
		return jsonString;
	}
	
	
	@Override
	public Map<String, String> postJSONMessageGetJSON(String apiUrl, String message, String credentials)
			throws IOException, XPathExpressionException {
		
		Map<String,String> response = new HashMap<String,String>();
		// try {
		
//		message = "{ \"PropertyIdentifier\": \"Hanks PropertyIdentifier\", \"Active\": \"false\", \"Name\": \"Hanks Property Name\", \"UnitNumber\":\"Abcd 123\",\"FirstName\": \"Tom\", \"LastName\": \"Hanks\", \"Street\": \"123 Mickey Mouse St\",\"City\": \"Littleton\", \"State\": \"CO\", \"Zip\": \"80124\", \"Phone\": \"3031432323\", \"PhoneExtension\": \"1111\", \"Questions\": [ { \"QuestionId\": \"1\", \"QuestionText\": \"How would you describe your property?\", \"Options\": [ { \"AnswerText\": \"A\", \"AnswerDescription\": \"High end property offering many amenities and convenience to renters (for example on-site fitness facility with modern, maintained equipment or easy access to the same, laundry facilities within the unit or within the immediate building, etc.). If the property is new, it employs higher-end equipment, appliances and convenience features throughout the facility and/or unit(s). If the property is aged, it has been significantly renovated recently to modernize key areas within the property (for example parking areas, parking garages or overhangs, gated community, etc.) and key features (for example appliances, furnishing, cabinets, bathroom fixtures, etc.). 'A' properties will have a lower risk factor, requiring higher standards of renters in regards to income and credit history.\" },{ \"AnswerText\": \"B\", \"AnswerDescription\": \"Moderate living property offering some key amenities and/or conveniences to renters (for example laundry facilities within the building or in a facility very near by, easily accessible, though not on-site, fitness facility with modern equipment, etc.). If the property has been recently built, the quality of materials used for common features of the property and/or unit(s) is good, though not high-end. For somewhat aged properties, significant renovations have been applied to improve common-use features (for example water heaters, appliances, bathroom fixtures, paint, etc.). 'B' properties employ a moderate risk factor that will eliminate applicants with poor credit histories while still accepting moderate to good credit histories and income.\" }, { \"AnswerText\": \"C\", \"AnswerDescription\": \"Low-end property offering few, if any, amenities or conveniences to renters (for example laundry facilities, fitness facility, etc.). If the property has been recently built, the quality of materials used for common features of the property and/or unit(s) is average or bulk building supplies. For somewhat aged properties, few, if any, renovations have been applied to update common-use features (for example water heaters, appliances, bathroom fixtures, paint, etc.). 'C' properties employ a high risk factor reflecting a lower quality offering to potential renters and allowing lower standards for potential lessees.\" } ], \"SelectedAnswer\": \"A\" }, {\"QuestionId\": \"2\", \"QuestionText\": \"How does your unit(s)'s rent compare to others in the neighborhood?\", \"Options\": [ { \"AnswerText\": \"A\", \"AnswerDescription\": \"Average applicant income will be significantly higher than expected rent\" }, { \"AnswerText\": \"B\", \"AnswerDescription\": \"Average applicant income will be somewhat higher than expected rent\" }, { \"AnswerText\": \"C\", \"AnswerDescription\": \"Average applicant income will be just above the expected rent\" } ], \"SelectedAnswer\": \"C\" }, {\"QuestionId\": \"3\", \"QuestionText\": \"What do you expect the average income of your potential applicants to be?\", \"Options\": [{ \"AnswerText\": \"A\", \"AnswerDescription\": \"Average applicant income will be much greater than the average income for the area\" }, { \"AnswerText\": \"B\", \"AnswerDescription\": \"Average applicant income will be at the average income for the area\"}, { \"AnswerText\": \"C\", \"AnswerDescription\": \"Average applicant income will be below the average income for the area\" } ], \"SelectedAnswer\": \"C\" },{ \"QuestionId\": \"4\", \"QuestionText\": \"Do you expect the average income of your potential applicnats to be above, at, or below the average income of other tenants in the neighborhood?\", \"Options\": [ { \"AnswerText\": \"A\", \"AnswerDescription\": \"Expected rent will be greaterthan the average rent for the area\" }, { \"AnswerText\": \"B\", \"AnswerDescription\": \"Expected rent will be at the average rent for the area\" }, { \"AnswerText\": \"C\", \"AnswerDescription\": \"Expected rent will be below the average rent for the area\" } ], \"SelectedAnswer\": \"B\" }, { \"QuestionId\": \"5\", \"QuestionText\": \"Do you expect many applicants to apply for your unit(s)? \", \"Options\": [ { \"AnswerText\": \"A\", \"AnswerDescription\": \"Expect many applicants and good visibility for these units\" }, {\"AnswerText\": \"B\", \"AnswerDescription\": \"Expect a steady number of applicants with average visibility for these unit(s)\" }, { \"AnswerText\": \"C\", \"AnswerDescription\": \"Expect few applicants\" } ], \"SelectedAnswer\": \"A\" } ], \"Classification\": \"Conventional\",\"IR\": \"2\", \"IncludeMedicalCollections\": \"false\", \"IncludeForeclosures\": \"false\", \"DeclineForOpenBankruptcies\": \"false\", \"OpenBankruptcyWindow\": \"6\", \"IsFcraAgreementAccepted\": \"true\" }";
		
//		URL baseUrl = new URL(getBaseURL());
//		HttpURLConnectionFacade conn = connectionFactoryService.getConnection(baseUrl);
		
		URL url = new URL(apiUrl); 
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
//		final HttpURLConnection conn = (HttpURLConnection) new URL(getBaseURL()
//				+ urlExtension).openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
//		conn.setRequestProperty("X-DocuSign-Authentication", getAuthStr());
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Content-Length",
				Integer.toString(message.length()));
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", credentials);
//		logger.debug(baseUrl.toString());
		logger.debug(message);
//		logger.debug("X-DocuSign-Authentication - " + getAuthStr());
		logger.debug("Content-Type - application/json");
		logger.debug("Accept - application/json");
		// write the body of the request...
		
		DataOutputStream output = new DataOutputStream( conn.getOutputStream() ); 

		// write out the data 
		int queryLength = message.length(); 
		output.writeBytes( message ); 
//		//output.close();
		
		int responseCode = conn.getResponseCode();
		String responseMessage = conn.getResponseMessage();
		final NumberFormat nf = new DecimalFormat("##0");
		response.put("responseCode",nf.format(responseCode));
		response.put("responseMessage", responseMessage);
		
		if (responseCode != 201) {
			logger.error(String.format("Service returned problem message -- %d:%s\\nrequest: %s", responseCode, responseMessage, message));
			return response;
	    }
		
		BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer inputLine = new StringBuffer();
		String tmp;
		while ((tmp = input.readLine()) != null) {
			inputLine.append(tmp);
		}
		input.close(); 
		
		String resp = inputLine.toString();
		response.put("response", resp);

		return response;
	
		
//		conn.sendOutput(message);
//		final byte[] outputBytes = message.getBytes();
//		final OutputStream dos = conn.getOutputStream();
//		dos.write(outputBytes);
//		dos.flush();
//		dos.close();

//		final int status = conn.getResponseCode(); // triggers the request
//		final String msg = conn.getResponseMessage();
//		String body = conn.getBody();
//		InputStream in = conn.getInputStream();
//		String encoding = conn.getContentEncoding();
//		encoding = encoding == null ? "UTF-8" : encoding;
//		String body = IOUtils.toString(in, encoding);
//		if (status != 200) {
//			logger.error(String.format(
//					"Invalid response retrieving document list: %d", status));
//			throw new DocusignMessageException(String.format(
//					"Invalid response retrieving document list: %d", status),
//					status, msg);
//		}

//		return body;
		// } catch (IOException e) {
		// logger.error(
		// "Unable to delete envelope {}.  Error: {}",
		// envelopeId, e.getMessage());
		// }
	}
}
