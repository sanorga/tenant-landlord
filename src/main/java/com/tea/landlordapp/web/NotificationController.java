package com.tea.landlordapp.web;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.xml.sax.SAXException;

import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.service.ApiService;
import com.tea.landlordapp.service.InviteService;


@Controller
public class NotificationController {
	
	private final Logger log = LoggerFactory
			.getLogger(NotificationController.class);
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	InviteService inviteService;
	
	@RequestMapping(value = "/notificationMgmt.htm", method = RequestMethod.GET)
	public void approveApplication(
			@RequestParam(value = "pushNotification", required = false) String pushNotification,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String authJson = request.getHeader("tenant-authorization");
//		if (StringUtils.isBlank(authJson)) {
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, "missing or invalid authorization header");
//			return;
//		}
//		User user = getAuthorizedUser(response, authJson);
//		
//		pushNotification = "<PushNotification xmlns=\"http://schemas.turss.com/SmartMove/1.0.0/\"> <entry> <Category>RenterAccept</Category> <ApplicationNumber>38398</ApplicationNumber> <EventText>joe@plumber.com has accepted your request</EventText> </entry> </PushNotification>";
		pushNotification = "<NotificationEntity> <Category>ApplicationComplete</Category> <ApplicationNumber>38001</ApplicationNumber> <EventText>Testing Push Notification</EventText> </NotificationEntity>";
		
		User apiUser = null;
			
		apiUser = userDao.findApiUser();
			
		Integer applicationExtId=null;
		String applicationExtIdStr= null, externalStatus= null, status=null, eventText = null;
		
		Map<String, String> notificationResponse = null;
		try {
			notificationResponse = getAuthorizeResponseXml(pushNotification);
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applicationExtIdStr = notificationResponse.get("ApplicationNumber");
		applicationExtId = Integer.valueOf(applicationExtIdStr);
		externalStatus = notificationResponse.get("Category");
		eventText = notificationResponse.get("EventText");
		
		boolean updateFlag = false, gotReportsFlag = false;
		
		if (apiUser != null) {
			updateFlag = apiService.updateAppStatusFlag(applicationExtId, apiUser.getId(), externalStatus, eventText);
		}
		
		if (updateFlag) {
			log.info("Notification received and status updated for application  {}", applicationExtId);
			if (StringUtils.equals(externalStatus,"ApplicationComplete")){
				Map<String, String> result = inviteService.getReports(applicationExtId);
				if (result == null) 
					log.info("Notification received and reports obtained for application  {}", applicationExtId);
				else log.info("Notification received and no reports obtained for application  {}", applicationExtId);
			}
			
		}
		else log.info("Notification received no status updated for application  {}", applicationExtId);
		
		
	}

	@RequestMapping(value = "/notificationMgmt.htm", method = RequestMethod.POST)
	public void approveApplicationOther(
			@RequestParam(value = "pushNotification", required = false) String pushNotification,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String authJson = request.getHeader("tenant-authorization");
//		if (StringUtils.isBlank(authJson)) {
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, "missing or invalid authorization header");
//			return;
//		}
//		User user = getAuthorizedUser(response, authJson);
//		
//		pushNotification = "<PushNotification xmlns=\"http://schemas.turss.com/SmartMove/1.0.0/\"> <entry> <Category>RenterAccept</Category> <ApplicationNumber>38398</ApplicationNumber> <EventText>joe@plumber.com has accepted your request</EventText> </entry> </PushNotification>";
		pushNotification = "<NotificationEntity> <Category>RenterAccept</Category> <ApplicationNumber>38374</ApplicationNumber> <EventText>Testing Push Notification</EventText> </NotificationEntity>";
		User apiUser = null;
			
		apiUser = userDao.findApiUser();
			
		Integer applicationExtId=null;
		String applicationExtIdStr= null, status= null, eventText = null;
		
		Map<String, String> notificationResponse = null;
		try {
			notificationResponse = getAuthorizeResponseXml(pushNotification);
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applicationExtIdStr = notificationResponse.get("ApplicationNumber");
		applicationExtId = Integer.valueOf(applicationExtIdStr);
		status = notificationResponse.get("Category");
		eventText = notificationResponse.get("EventText");
		
		boolean updateFlag = false;
		
		if (apiUser != null) {
			updateFlag = apiService.updateAppStatusFlag(applicationExtId, apiUser.getId(), status, eventText);
		}
		
		if (updateFlag)
			log.info("Notification received and staus updated for application  {}", applicationExtId);
		else log.info("Notification received no status updated for application  {}", applicationExtId);
	
	}
	
	private Map<String, String> getAuthorizeResponseXml(String resp) throws ParserConfigurationException, SAXException, IOException {
		
		Map<String, String> mapResponse =  new HashMap<String,String>();
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(resp)));
		doc.getDocumentElement().normalize();
		String nodeNameStr = doc.getDocumentElement().getNodeName();
		if (nodeNameStr == "NotificationEntity") {
			Element eElement = doc.getDocumentElement();
			mapResponse.put("ApplicationNumber", eElement.getElementsByTagName("ApplicationNumber").item(0).getTextContent());
	        mapResponse.put("Category", eElement.getElementsByTagName("Category").item(0).getTextContent());
	        mapResponse.put("EventText", eElement.getElementsByTagName("EventText").item(0).getTextContent());
		}	
		        
			

		return mapResponse;
	}
}
