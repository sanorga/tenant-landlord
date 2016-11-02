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

@Controller
public class NotificationController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	ApiService apiService;
	
	@RequestMapping(value = "/notificationMgmt.htm", method = RequestMethod.GET)
	public void approveApplication(
			@RequestParam(value = "pushNotification", required = true) String pushNotification,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String authJson = request.getHeader("tenant-authorization");
//		if (StringUtils.isBlank(authJson)) {
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, "missing or invalid authorization header");
//			return;
//		}
//		User user = getAuthorizedUser(response, authJson);
//		
		pushNotification = "<PushNotification xmlns=\"http://schemas.turss.com/SmartMove/1.0.0/\"> <entry> <Category>RenterAccept</Category> <ApplicationNumber>38398</ApplicationNumber> <EventText>joe@plumber.com has accepted your request</EventText> </entry> </PushNotification>";
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
		
		if (apiUser != null) {
			apiService.updateApplication(applicationExtId, apiUser.getId(), status, eventText);
		}
	}
	
	private Map<String, String> getAuthorizeResponseXml(String resp) throws ParserConfigurationException, SAXException, IOException {
		
		Map<String, String> mapResponse =  new HashMap<String,String>();
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(resp)));
		doc.getDocumentElement().normalize();
		String nodeNameStr = doc.getDocumentElement().getNodeName();
       	Element root = doc.getDocumentElement();
		
       	if (nodeNameStr == "ApplicationNumber") {
       		mapResponse.put("ApplicationNumber", root.getFirstChild().getNodeValue());
       		return mapResponse;
       	}
       	
       	if (nodeNameStr == "Category") {
       		mapResponse.put("Category", root.getFirstChild().getNodeValue());
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
		           mapResponse.put("ApplicationNumber", eElement.getElementsByTagName("ApplicationNumber").item(0).getTextContent());
		           mapResponse.put("Category", eElement.getElementsByTagName("Category").item(0).getTextContent());
		           mapResponse.put("EventText", eElement.getElementsByTagName("EventText").item(0).getTextContent());
		        }
			}

		return mapResponse;
	}
}
