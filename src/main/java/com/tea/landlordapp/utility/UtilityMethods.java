package com.tea.landlordapp.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Application;
//import com.tea.landlordapp.dto.InternalACHPaymentDto;
//import com.tea.landlordapp.dto.TenantApplicationDto;

public class UtilityMethods {

	private static String digits = "0123456789abcdef";

	public static String bool2Nx(Boolean flag) {
		if (flag == null) {
			return "?";
		}
		if (flag) {
			return "";
		} else {
			return "X";
		}
	}

	public static String bool2YesNo(Boolean flag) {
		if (flag == null) {
			return "??";
		}
		if (flag) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public static Character bool2YN(Boolean flag) {
		if (flag == null) {
			return '?';
		}
		if (flag) {
			return Globals.YES;
		} else {
			return Globals.NO;
		}
	}

	public static Character bool2YN(Boolean flag, Character valIfNull) {
		if (flag == null) {
			return valIfNull;
		}
		if (flag) {
			return Globals.YES;
		} else {
			return Globals.NO;
		}
	}

	public static String bool2Yx(Boolean flag) {
		if (flag == null) {
			return "?";
		}
		if (flag) {
			return "X";
		} else {
			return "";
		}
	}

	// public static String buildAddressLine(String nmbr, String street, String
	// stType, String apt) {
	// String res = "";
	// res = concat(nmbr, street, " ");
	// res = concat(res, stType, " ");
	// res = concat(res, apt, " #");
	//
	// return res;
	// }

	// public static String buildFullName(String fn, String mi, String ln) {
	// String res = "";
	// if (!StringUtils.isBlank(fn)) {
	// res = fn.trim();
	// }
	// if (!StringUtils.isBlank(mi)) {
	// res = String.format("%s %s", res, mi.trim()).trim();
	// }
	// if (!StringUtils.isBlank(ln)) {
	// res = String.format("%s %s", res, ln.trim()).trim();
	// }
	//
	// return res;
	// }

	public static boolean checkNull(Boolean value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	// public static char checkNull(Character value, char defaultValue) {
	// if (value == null) {
	// return defaultValue;
	// } else {
	// return value;
	// }
	// }

	public static int checkNull(Integer value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public static String checkNull(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	// public static String checkNull(String value, String defaultValue) {
	// if (value == null) {
	// return defaultValue;
	// } else {
	// return value;
	// }
	// }

	public static String combineUrlQueryTerms(String term1, String term2) throws UnsupportedEncodingException {
		String res = "";
		String encTerm1;
		String encTerm2;
		if (term1 == null) {
			encTerm1 = "";
		} else {
			encTerm1 = term1.trim();
		}
		if (term2 == null) {
			encTerm2 = "";
		} else {
			encTerm2 = term2.trim();
		}

		if (StringUtils.isEmpty(encTerm1)) {
			if (!StringUtils.isEmpty(encTerm2)) {
				res = encTerm2;
			}
		} else {
			if (!StringUtils.isEmpty(encTerm2)) {
				res = encTerm1 + "&" + encTerm2;
			} else {
				res = encTerm1;
			}
		}
		return res;
	}

	// public static String concat(String s1, String s2, String sep) {
	// if (StringUtils.isBlank(s2)) {
	// if (StringUtils.isBlank(s1)) {
	// return "";
	// } else {
	// return s1.trim();
	// }
	// }
	//
	// final String sa = StringUtils.isBlank(s1) ? "" : s1.trim();
	// final String sb = s2.trim();
	// final String sc = sep == null ? "" : sep;
	//
	// return sa + sc + sb;
	// }

	public static int dom(Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	// public static String getMaskedDate(Date dt) {
	// final String fmt = "MM/dd/'xxxx'";
	// final Format formatter = new SimpleDateFormat(fmt);
	// final String res = formatter.format(dt);
	// return res;
	// }

	// public static String getMaskedSsn(String ssn) {
	// if (StringUtils.isNotBlank(ssn) && ssn.length() > 4) {
	// return "XXX-XX-" + ssn.substring(ssn.length() - 4, ssn.length());
	// } else {
	// return "";
	// }
	// }

	public static String getStreetTypeLabel(String code) {
		String res = "";
		if (!StringUtils.isBlank(code)) {
			// First try the old map
			Map<String, String> map = Globals.getInstance().getStreetTypeOptions();
			res = map.get(code);
			if (res == null) {
				// Now try the new map
				map = Globals.getInstance().getStreetTypes();
				res = map.get(code);
				if (res == null) {
					res = "";
				}
			}

		}

		return res;
	}

	public static boolean isValidDate(String inDate) {
		if (inDate == null) {
			return false;
		}

		// set the format to use as a constructor argument
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		if (inDate.trim().length() != dateFormat.toPattern().length()) {
			return false;
		}

		dateFormat.setLenient(false);

		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (final ParseException pe) {
			return false;
		}
		return true;
	}

	public static int month(Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	public static String nowString() {
		final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	// public static Date parseDateString(String sDate) throws ParseException {
	// Date res = null;
	// if (!StringUtils.isBlank(sDate)) {
	// final Globals globals = Globals.getInstance();
	// if (sDate.contains("-")) {
	// res = globals.yyyymmdd.parse(sDate);
	// } else {
	// res = globals.mmddyyyy.parse(sDate);
	// }
	// }
	// return res;
	// }

	public static int year(Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static Calendar stripTime2Calendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// Set time fields to zero
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	public static Date stripTime(Date date) {
		// 01.Date date = ...;
		// 02.
		// 03.// Get Calendar object set to the date and time of the given Date
		// object
		// 04.Calendar cal = Calendar.getInstance();
		// 05.cal.setTime(date);
		// 06.
		// 07.// Set time fields to zero
		// 08.cal.set(Calendar.HOUR_OF_DAY, 0);
		// 09.cal.set(Calendar.MINUTE, 0);
		// 10.cal.set(Calendar.SECOND, 0);
		// 11.cal.set(Calendar.MILLISECOND, 0);
		// 12.
		// 13.// Put it back in the Date object
		// 14.date = cal.getTime();
		Calendar calendar = stripTime2Calendar(date);

		Date resDate = calendar.getTime();
		return resDate;

	}

	public static Date midnightTomorrow(Date date) {
		Calendar calendar = stripTime2Calendar(date);
		calendar.add(Calendar.HOUR_OF_DAY, 24);

		return calendar.getTime();
	}

	public static String[] list2StringArray(List<String> src) {

		String[] destinations = new String[src.size()];
		for (int i = 0; i < src.size(); i++) {
			destinations[i] = src.get(i);
		}

		return destinations;
	}

	public static String getUniqueString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static <T> byte[] dehydrate(T dto) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(dto);
		oos.flush();
		oos.close();
		bos.close();
		byte[] data = bos.toByteArray();
		return data;
	}

	public static <T> T rehydrate(Class<T> clazz, byte[] data) throws IOException, ClassNotFoundException {
		T dto = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj;
		obj = ois.readObject();
		dto = (T) obj;
		return dto;
	}

//	public static byte[] dehydrateTenantDto(TenantApplicationDto dto) throws IOException {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(bos);
//		oos.writeObject(dto);
//		oos.flush();
//		oos.close();
//		bos.close();
//		byte[] data = bos.toByteArray();
//		return data;
//	}
//
//	public static TenantApplicationDto rehydrateTenantDto(byte[] data) throws IOException, ClassNotFoundException {
//		TenantApplicationDto dto = null;
//		ByteArrayInputStream bis = new ByteArrayInputStream(data);
//		ObjectInputStream ois = new ObjectInputStream(bis);
//		Object obj;
//		obj = ois.readObject();
//		if (obj instanceof TenantApplicationDto) {
//			dto = (TenantApplicationDto) obj;
//		}
//		return dto;
//	}
//
//	public static InternalACHPaymentDto rehydrateInternalACHPaymentDto(InternalACHPaymentDto dto, Application app)
//			throws IOException, ClassNotFoundException {
//
//		dto.setApplicationCollectionItems(app.getApplicationCollectionItems());
//		return dto;
//	}

	public static String getApplicationBaseUrl(HttpServletRequest request) {
		String submitUrl = request.getScheme() + "://" + request.getServerName()
				+ (StringUtils.equalsIgnoreCase("http", request.getScheme())
						? (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
						: (StringUtils.equalsIgnoreCase("https", request.getScheme())
								? (request.getServerPort() == 443 ? "" : ":" + request.getServerPort())
								: ":" + request.getServerPort()))

				+ (StringUtils.isNotBlank(request.getContextPath()) ? request.getContextPath() : "");
		return submitUrl;
	}

	public static String getActualPath(HttpServletRequest request) {
		String imagePath = request.getSession().getServletContext().getRealPath("/");
		return imagePath;
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlCalendar;
	}

	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	public static Date addDays(Date begin, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static String changeDateFormat(String dt, String fromFormat, String toFormat) throws ParseException {
		SimpleDateFormat sFrom = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sTo = new SimpleDateFormat(toFormat);
		Date date = sFrom.parse(dt);
		return sTo.format(date);
	}

	public static String getFormattedDate(Date dob, String format) {
		if (dob == null)
			return "";
		SimpleDateFormat smf = new SimpleDateFormat(format);
		// commented for dob encreption
		String sDob = smf.format(dob);
		return sDob;
	}

	public static String toHex(byte[] data, int length) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;
			buf.append(digits.charAt(v >> 4));
			buf.append(digits.charAt(v & 0xf));
		}
		return buf.toString();
	}

	public static String toHex(byte[] data) {
		return toHex(data, data.length);
	}

	public static String buildPseudoId(String ssn, String passport, Date dob) {
		String datePart = "0/0/0";
		if (dob != null) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			datePart = df.format(dob);
		}

		String idPart = "no id number";
		if (StringUtils.isNotBlank(ssn)) {
			idPart = ssn.replaceAll("[^0-9]", "");
		} else {
			if (StringUtils.isNotBlank(passport)) {
				idPart = passport.toLowerCase().replaceAll("[^0-9a-z]", "");
			}
		}

		byte[] digest;
		try {
			digest = EncryptionService.digest(datePart + idPart);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		String b64Digest = new String(Base64.encodeBase64(digest));

		return b64Digest;
	}
	
	/**
	 * This method returns true if the collection is null or is empty.
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty( Collection<?> collection ){
		if( collection == null || collection.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true of the map is null or is empty.
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty( Map<?, ?> map ){
		if( map == null || map.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the objet is null.
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty( Object object ){
		if( object == null ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty( Object[] array ){
		if( array == null || array.length == 0 ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty( String string ){
		if( string == null || string.trim().length() == 0 ){
			return true;
		}
		return false;
	}
}
