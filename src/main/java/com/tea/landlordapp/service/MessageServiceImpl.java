package com.tea.landlordapp.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.dto.BinaryFile;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.UserDao;
import com.tea.landlordapp.utility.StringHelper;
import com.tea.landlordapp.utility.UtilityMethods;
import com.tea.utility.ServiceUtils;

public class MessageServiceImpl implements MessageService {

	
	
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());


	
	

	private SimpleDao simpleDao;

	private UserDao userDao;

	@Autowired
	public MessageServiceImpl( SimpleDao simpleDao, UserDao userDao ) {
		super();

		this.simpleDao = simpleDao;
		this.userDao = userDao;
		
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

		if (anonymousUser.getClient().getPayPerApplication() == 'Y') {
			// Getting Client's default External price
			final Double appPrice = anonymousUser.getClient()
					.getApplicationPrice();

			final NumberFormat df = new DecimalFormat("#0.00");
			map.put("Price", df.format(appPrice));
		}

		final String realPath = request.getSession().getServletContext()
				.getRealPath("/");

		map.put("LINK", emailProperties.getProperty(Globals.TEA_ADMIN_URL)
				+ "/submitApplication.htm?" + Globals.ANONYMOUS_USER_URL_ID
				+ "=" + anonymousUser.getId());
		map.put("REFERENCEKEY", anonymousUser.getReference());

		inlineResources = new HashMap<String, String>();
		inlineResources.put("template11_07", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_07.jpg"));
		inlineResources.put("template11_08", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_08.jpg"));
		inlineResources.put("template11_09", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_09.jpg"));
		inlineResources.put("template11_11", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_11.png"));
		inlineResources.put("template11_13", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_13.jpg"));
		inlineResources.put("template11_15", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_15.jpg"));
		inlineResources.put("template11_16", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_16.jpg"));
		inlineResources.put("template11_18", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_18.jpg"));
		inlineResources.put("template11_19", StringHelper.concatWithSingleSlash(realPath,
				"images/anonymousmail/images/template11_19.jpg"));
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
		toList.add(anonymousUser.getCreatedBy().getContactEmail());

		String[] destinations = UtilityMethods.list2StringArray(toList);

		String velocityName = "anonymous_user.vm";
		if (anonymousUser.getClient().getPayPerApplication() == 'Y') {
			velocityName = "anonymous_user_cc.vm";
		}

		BinaryFile bf = null;
		Property prop = anonymousUser.getProperty();
		if (prop != null && fileStorageService.isPropertyTermsDocExists(prop.getId())) {
			bf = new BinaryFile("doc.pdf",
					fileStorageService.getPropertyTermsContent(anonymousUser
							.getProperty().getId()));
		}
		return sendTemplateMail(destinations, map,
				"Tenant Application Submission Link", velocityName,
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
