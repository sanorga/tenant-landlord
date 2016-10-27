package com.tea.landlordapp.service;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.base.Strings;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Applicant;
import com.tea.landlordapp.domain.Application;
//import com.tea.landlordapp.domain.ApplicationCollectionItem;
import com.tea.landlordapp.domain.Property;
//import com.tea.landlordapp.domain.QboCustomerMap;
//import com.tea.landlordapp.domain.QboTransaction;
//import com.tea.landlordapp.domain.Subscriber;
import com.tea.landlordapp.domain.User;
//import com.tea.landlordapp.domain.WebAppConfiguration;
import com.tea.landlordapp.dto.BinaryFile;
//import com.tea.landlordapp.dto.ClientApplicationDto;
//import com.tea.landlordapp.dto.StringStringKVDto;
//import com.tea.landlordapp.dto.TenantApplicationDto;
//import com.tea.landlordapp.dto.TenantApplicationDto.CollectionItem;
//import com.tea.landlordapp.enums.AlertParameter;
//import com.tea.landlordapp.enums.ApplicationStatus;
//import com.tea.landlordapp.enums.ImageDocType;
//import com.tea.landlordapp.enums.RebatePaymentMethod;
import com.tea.landlordapp.enums.UserRole;
//import com.tea.landlordapp.exception.FileDownloadException;
//import com.tea.landlordapp.repository.CustomersDao;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.SystemPropertyDao;
import com.tea.landlordapp.repository.UserDao;
//import com.tea.landlordapp.utility.EmailHelper;
import com.tea.landlordapp.utility.ServiceUtils;
import com.tea.landlordapp.utility.StringHelper;
import com.tea.landlordapp.utility.UtilityMethods;


@Service("messageService")
public class MessageServiceImpl implements MessageService {

	
	
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());


	
	private JavaMailSender mailSender;

	private SimpleDao simpleDao;

	private UserDao userDao;

	private Properties emailProperties;
	
	private VelocityEngine velocityEngine;
	


	@Autowired
	public MessageServiceImpl(JavaMailSender mailSender, VelocityEngine velocityEngine, SimpleDao simpleDao,
			UserDao userDao, 
			Properties emailProperties) {
		super();
		this.mailSender = mailSender;
		this.velocityEngine = velocityEngine;
		this.simpleDao = simpleDao;
		this.userDao = userDao;
		this.emailProperties = emailProperties;
	}
	
	@Override
	public boolean sendApplicationClientMail(int appId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendAnonymousUserMail(AnonymousUser anonymousUser,
			HttpServletRequest request) throws Exception {

		final HashMap<String, Object> map = new HashMap<String, Object>();

		Map<String, String> inlineResources;
		map.put("DATE", new Date());
		map.put("USER", anonymousUser.getEmailId());

//		if (anonymousUser.getClient().getPayPerApplication() == 'Y') {
//			// Getting Client's default External price
//			final Double appPrice = anonymousUser.getClient()
//					.getApplicationPrice();
//
//			final NumberFormat df = new DecimalFormat("#0.00");
//			map.put("Price", df.format(appPrice));
//		}
		String address = anonymousUser.getProperty().getAddressLine();
		final String realPath = request.getSession().getServletContext()
				.getRealPath("/");

		map.put("LINK", emailProperties.getProperty(Globals.TEA_LANDLORDAPP_URL)
				+ "/submitApplication.htm?" + Globals.ANONYMOUS_USER_URL_ID
				+ "=" + anonymousUser.getId());
		map.put("LINK", emailProperties.getProperty(Globals.SMARTMOVE_NEW_ACCT_URL)
				+ "/Create-Account.page?SubDomainId=1&UserType=1&Email=" + anonymousUser.getEmailId());

		map.put("REFERENCEKEY", anonymousUser.getReference());
		map.put("ADDRESS", anonymousUser.getProperty().getAddressLine());
		inlineResources = new HashMap<String, String>();
		inlineResources.put("template11_07", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_07.jpg"));
		inlineResources.put("template11_08", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_08.jpg"));
		inlineResources.put("template11_09", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_09.jpg"));
//		inlineResources.put("template11_11", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_11.png"));
//		inlineResources.put("template11_13", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_13.jpg"));
//		inlineResources.put("template11_15", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_15.jpg"));
//		inlineResources.put("template11_16", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_16.jpg"));
//		inlineResources.put("template11_18", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_18.jpg"));
//		inlineResources.put("template11_19", StringHelper.concatWithSingleSlash(realPath,
//				"images/anonymousmail/images/template11_19.jpg"));
		inlineResources.put("testing_01", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/testing_01.jpg"));
		inlineResources.put("testing_02", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/testing_02.jpg"));
		inlineResources.put("testing_03", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/testing_03.jpg"));
		inlineResources.put("spacer", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/spacer.gif"));

		final List<String> toList = new ArrayList<String>();
		toList.add(anonymousUser.getEmailId());
		toList.add(anonymousUser.getCreatedBy().getUsername());

		String[] destinations = UtilityMethods.list2StringArray(toList);

		String velocityName = "anonymous_user.vm";
//		if (anonymousUser.getClient().getPayPerApplication() == 'Y') {
//			velocityName = "anonymous_user_cc.vm";
//		}

		BinaryFile bf = null;
//		Property prop = anonymousUser.getProperty();
//		if (prop != null && fileStorageService.isPropertyTermsDocExists(prop.getId())) {
//			bf = new BinaryFile("doc.pdf",
//					fileStorageService.getPropertyTermsContent(anonymousUser
//							.getProperty().getId()));
//		}
		return sendTemplateMail(destinations, map,
				"Invitation to Apply", velocityName,
				inlineResources, bf);
	}

	@Override
	public boolean sendTemplateMail(String[] senderList,
			HashMap<String, Object> map, String subjectFileName,
			String textFileName, Map<String, String> inlineResources,
			BinaryFile attachment) throws Exception {
//		@SuppressWarnings("deprecation")
		final String textFile = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, textFileName, "UTF-8", map);
		
//		StringWriter writer = new StringWriter();
//		VelocityEngineUtils.mergeTemplate(velocityEngine, textFileName, map, writer);
		

		String fromAddress = emailProperties.getProperty("email.from");
		try {
			ServiceUtils.sendMimeMessage(senderList, subjectFileName,
					fromAddress, inlineResources, attachment, textFile,
					mailSender);
			logger.debug("Mail sent successfully");
			return true;
		} catch (Exception e) {
			logger.error("Mail sending failed: {}", e.getMessage());
			return false;
		}

	}
}
