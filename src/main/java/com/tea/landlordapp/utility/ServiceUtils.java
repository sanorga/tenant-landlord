package com.tea.landlordapp.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.mail.util.ByteArrayDataSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;

//import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.dto.BinaryFile;
import com.tea.landlordapp.dto.LookupListItem;
import com.tea.landlordapp.utility.UtilityMethods;

public class ServiceUtils {

	protected final static Logger logger = LoggerFactory
			.getLogger("ServiceUtils.class");

	private static final int BUFFER = 2048;
	private static Random random = new Random();

	public static int getRandomNumber(int numberOfDigits) {
		int lo = 1;
		int hi = 1;

		// error checking
		if (numberOfDigits > 9 || numberOfDigits < 1) {
			logger.error("Given number of digists out of bound. Number of digists should be greater than 0 and less than 10. i.e Inbetween  0 to 10.");
			throw new IllegalArgumentException(
					"Number of digists should be greater than 0 and less than 10");
		}

		// low value generation
		for (int i = 0; i < numberOfDigits - 1; i++) {
			lo = lo * 10;
		}
		// high value generation
		for (int i = 0; i < numberOfDigits; i++) {
			hi = hi * 10;
		}
		hi--;

		// handle special case
		if (lo == Integer.MIN_VALUE && hi == Integer.MAX_VALUE) {
			return random.nextInt();
		}

		// Random Number Generation for given interval
		final int n = hi - lo + 1;
		int i = random.nextInt() % n;
		if (i < 0) {
			i = -i;
		}
		return lo + i;
	}

	public static String getRandomString() {
		logger.debug("ServiceUtils : getRandomString : Random String Generating...");
		// Random String
		final String RANDOM_STRING = "RAa0bcLdUD2eHfJgTP8XhiFj61KOklNm9nBoI5pGqYVrs3CtSuMZvwWx1yE7zQ";

		final String randomBaseStr = RANDOM_STRING;
		final StringBuffer referencekey = new StringBuffer();
		final Random r = new Random();
		int RandomInt = 0;
		for (int i = 1; i <= 10; i++) {
			RandomInt = r.nextInt(randomBaseStr.length() - 1);
			referencekey.append(randomBaseStr.charAt(RandomInt));
		}
		return referencekey.toString();
	}
	
	public static void sortById(List<LookupListItem> items){
		class CustomComparator implements Comparator<LookupListItem> {

			@Override
			public int compare(LookupListItem o1, LookupListItem o2) {
				return o1.getId() - o2.getId();
			}
			
		}
		if (items == null || items.size()<2) return;
		Collections.sort(items, new CustomComparator());
		
	}
	
	public static void sortByLabel(List<LookupListItem> items){
		class CustomComparator implements Comparator<LookupListItem> {

			@Override
			public int compare(LookupListItem o1, LookupListItem o2) {
				if (o1.getId() == -1) return -999999;
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
			
		}
		if (items == null || items.size()<2) return;
		Collections.sort(items, new CustomComparator());
		
	}

//	public static List<Application> getSortedApplicationList(
//			List<Application> applications) {
//		logger.debug("Inside getSortedApplicationList. Sorting the List by primary applicant's first name ...");
//
//		Collections.sort(applications, new Comparator<Object>() {
//
//			@Override
//			public int compare(Object o1, Object o2) {
//				return -1
//						* (((Application) o2).getPrimary().getLastName() + ((Application) o2)
//								.getPrimary().getFirstName())
//								.compareToIgnoreCase(((Application) o1)
//										.getPrimary().getLastName()
//										+ ((Application) o1).getPrimary()
//												.getFirstName());
//			}
//		});
//		return applications;
//	}
	
	public static Map<Integer, String> getSortedMap(Map<Integer, String> map) {
		logger.debug("Inside getSortedMap. Sorting the map by values...");

		if (ObjectUtils.equals(map, null) || map.size() < 1) {
			return map;
		}

		final Map<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();

		final List<Map.Entry<Integer, String>> entriesList = new LinkedList<Map.Entry<Integer, String>>(
				map.entrySet());

		Collections.sort(entriesList, new Comparator<Object>() {

			@Override
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
				return -1
						* ((Map.Entry<Integer, String>) o2).getKey().compareTo(
								((Map.Entry<Integer, String>) o1)
										.getKey());	
						
//						* ((Map.Entry<Integer, String>) o2).getValue()
//								.compareToIgnoreCase(
//										((Map.Entry<Integer, String>) o1)
//												.getValue());
			}
		});

		for (final Map.Entry<Integer, String> entry : entriesList) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static String transformXmlToHtml(String xml, String xslFilePath) {
		logger.debug("ServiceUtils : transformXmlToHtml : Initiated");

		// String html = null ;
		final StringReader xmlReader = new StringReader(xml);
		final File xsltFile = new File(xslFilePath);

		// JAXP reads data using the Source interface
		final Source xsltSource = new StreamSource(xsltFile);

		// the factory pattern supports different XSLT processors
		Transformer trans;
		try {
			trans = TransformerFactory.newInstance().newTransformer(xsltSource);
		} catch (Exception e) {
			logger.error("Unable to create transformer for " + xsltFile);
			return "Unable to create transformer for " + xsltFile;
		}
		// Transformer trans = transFact.newTransformer(xsltSource);

		// convert XML type of String into XML type of Source
		final StreamResult htmlResult = new StreamResult(
				new ByteArrayOutputStream());
		final Source xmlSource = new StreamSource(xmlReader);

		// convert xml into html
		try {
			trans.transform(xmlSource, htmlResult);
		} catch (Exception e) {
			logger.error("Unable to transform xml using " + xsltFile);
			return xml;
		}

		// If color is set Black, change to 'White-#FFFFFF'
		StringBuffer htmlResultString = new StringBuffer(htmlResult
				.getOutputStream().toString());
		final String replaceFromStr = ".report TD {font-family:Verdana,Arial;font-size:8pt;font-weight:normal;color:black;line-height:12pt;}";
		if (StringUtils.contains(htmlResultString.toString(), replaceFromStr)) {
			htmlResultString = new StringBuffer(StringUtils.replace(
					htmlResultString.toString(), "color:black;",
					"color:#FFFFFF;"));
		}

		// convert html type of Result into html type of String
		return htmlResultString.toString();
	}

	public synchronized static void zipFiles(String[] inputFiles,
			String outputFilePath) throws Exception {
		logger.debug("inside zipFile....");
		try {
			BufferedInputStream origin = null;
			final FileOutputStream dest = new FileOutputStream(outputFilePath);
			final ZipOutputStream out = new ZipOutputStream(
					new BufferedOutputStream(dest));
			final byte data[] = new byte[BUFFER];

			for (final String inputFilePath : inputFiles) {
				final FileInputStream fi = new FileInputStream(new File(
						inputFilePath));
				origin = new BufferedInputStream(fi, BUFFER);
				final ZipEntry entry = new ZipEntry(
						new File(inputFilePath).getName());

				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}


	public static void sendMimeMessage(String[] recipientList,
			String subjectText, String fromAddress,
			Map<String, String> inlineResources, BinaryFile attachment,
			final String bodyText, JavaMailSender mailSender)
			throws MessagingException, MailException {
		sendMimeMessage(recipientList, null, null, subjectText, fromAddress,
				inlineResources, attachment, bodyText, mailSender);
	}
	
	public static void sendMimeMessage(List<String> recipientList,
			String subjectText, String fromAddress,
			Map<String, String> inlineResources, BinaryFile attachment,
			final String bodyText, JavaMailSender mailSender)
			throws MessagingException, MailException {
		
//		String[] destinations = (String[]) recipientList.toArray();
		
		String[] destinations = UtilityMethods.list2StringArray(recipientList);
		
		sendMimeMessage(destinations, null, null, subjectText, fromAddress,
				inlineResources, attachment, bodyText, mailSender);
	}
	
	public static void sendMimeMessage(List<String> recipientList, List<String> ccList,
			List<String> bccList, String subjectText, String fromAddress,
			Map<String, String> inlineResources, BinaryFile attachment,
			final String bodyText, JavaMailSender mailSender)
			throws MessagingException, MailException {
		
//		String[] destinations = (String[]) recipientList.toArray();
		
		String[] destinations = UtilityMethods.list2StringArray(recipientList);
		
		sendMimeMessage(destinations, ccList, bccList, subjectText, fromAddress,
				inlineResources, attachment, bodyText, mailSender);
	}

	public static void sendMimeMessage(String[] recipientList, List<String> ccList,
			List<String> bccList, String subjectText, String fromAddress,
			Map<String, String> inlineResources, BinaryFile attachment,
			final String bodyText, JavaMailSender mailSender)
			throws MessagingException, MailException {

		final MimeMessage message = mailSender.createMimeMessage();

//		final String[] recipientAddesses = recipientList
//				.toArray(new String[recipientList.size()]);
//
		final MimeMessageHelper helper = new MimeMessageHelper(message, true);

		for (int i = 0; i < recipientList.length; i++) {
			logger.debug("Send to {}", recipientList[i]);
		}
		helper.setTo(recipientList);

		if (bccList != null && bccList.size() > 0) {
			String[] bccAddresses = bccList.toArray(new String[bccList
					.size()]);
			helper.setBcc(bccAddresses);
		}

		if (ccList != null && ccList.size() > 0) {
			String[] ccAddresses = ccList.toArray(new String[ccList
					.size()]);
			helper.setCc(ccAddresses);
		}

		if (!StringUtils.isBlank(fromAddress)) {
			logger.debug("Send from {}", fromAddress);
			helper.setFrom(fromAddress);

		}
		logger.debug("Send subject {}", subjectText);
		helper.setSubject(subjectText);
		helper.setText(bodyText, true);

		// add inlineResources, if any
		if ((inlineResources != null) && !CollectionUtils.isEmpty(inlineResources)) {
			for (final String name : inlineResources.keySet()) {
				final String file = inlineResources.get(name);
				final FileSystemResource res = new FileSystemResource(new File(
						file));
				helper.addInline(name, res);
			}
		}

		// add attachment, if any
		if (attachment != null) {
			logger.debug("with Attachment");
			helper.addAttachment(attachment.getName(), new ByteArrayDataSource(
					attachment.getContent(), "application/octet-stream"));
		}

		try {
			mailSender.send(message);
		} catch (MailException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	public static void sendMimeMessage(List<String> recipientList,
			String subjectText, 
			String fromAddress,
			Map<String, Resource> inlineResources,
			final String bodyText, 
			JavaMailSender mailSender)
			throws MessagingException, MailException {
		
		String[] destinations = UtilityMethods.list2StringArray(recipientList);		
		
		sendMimeMessage(destinations, subjectText, fromAddress,
				inlineResources, bodyText, mailSender);
	}
	
	public static void sendMimeMessage(String[] recipientList,
								String subjectText, 
								String fromAddress,
								Map<String, Resource> inlineResources,
								final String bodyText, 
								JavaMailSender mailSender)
			throws MessagingException, MailException {

		final MimeMessage message = mailSender.createMimeMessage();

//		final String[] recipientAddesses = recipientList
//				.toArray(new String[recipientList.size()]);
//
		final MimeMessageHelper helper = new MimeMessageHelper(message, true);

		for (int i = 0; i < recipientList.length; i++) {
			logger.debug("Send to {}", recipientList[i]);
		}
		helper.setTo(recipientList);

//		if (bccList != null && bccList.size() > 0) {
//			String[] bccAddresses = bccList.toArray(new String[bccList
//					.size()]);
//			helper.setBcc(bccAddresses);
//		}
//
//		if (ccList != null && ccList.size() > 0) {
//			String[] ccAddresses = ccList.toArray(new String[ccList
//					.size()]);
//			helper.setCc(ccAddresses);
//		}

		if (!StringUtils.isBlank(fromAddress)) {
			logger.debug("Send from {}", fromAddress);
			helper.setFrom(fromAddress);

		}
		logger.debug("Send subject {}", subjectText);
		helper.setSubject(subjectText);
		helper.setText(bodyText, true);

		// add inlineResources, if any
		if ((inlineResources != null) && !CollectionUtils.isEmpty(inlineResources)) {
			for (final String name : inlineResources.keySet()) {
//				final String file = inlineResources.get(name);
//				final FileSystemResource res = new FileSystemResource(new File(
//						file));
				helper.addInline(name, inlineResources.get(name));
			}
		}

//		// add attachment, if any
//		if (attachment != null) {
//			logger.debug("with Attachment");
//			helper.addAttachment(attachment.getName(), new ByteArrayDataSource(
//					attachment.getContent(), "application/octet-stream"));
//		}

		try {
			mailSender.send(message);
		} catch (MailException e) {
			logger.error(e.getMessage());
			throw e;
		}
	
	}
}