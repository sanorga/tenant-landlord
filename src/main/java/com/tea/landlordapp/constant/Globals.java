package com.tea.landlordapp.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

//import com.tea.enums.EmploymentType;
//import com.tea.enums.BankAccountType;
//import com.tea.enums.CheckType;
//import com.tea.enums.RelationshipType;
import com.tea.landlordapp.enums.UserRole;

public class Globals {
	
	private final static String ABANDON_FINAL_REPORT_MESSAGE = "This application has been Terminated. The applicant(s) have not cooperated in completing the information required. Daily emails have been sent since %s.";
	private final static String ABANDON_FINAL_REPORT_SERVICE_MESSAGE = "TE has not been authorized to run this service. Proper signed authorization is required.";

	public String getAbandonFinalReportMessage(Date created){
		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		String msg = String.format(ABANDON_FINAL_REPORT_MESSAGE, fmt.format(created));
		return msg;
	}
	
	public String getAbandonFinalReportServiceMessage(){
		return ABANDON_FINAL_REPORT_SERVICE_MESSAGE;
	}
	
	public Map<String, String> getUserStatusOptions() {
		return instance.userStatusOptions;
	}

	// public Map<String, String> getUserRoleOptions(String userRole) {
	// if (StringUtils.equals(userRole, Globals.SYSTEM_ADMIN)) {
	// return systemUserRoleOptions;
	// } else if (StringUtils.equals(userRole, Globals.PARTNER_ADMIN)
	// || StringUtils.equals(userRole, Globals.CLIENT_ADMIN)
	// || StringUtils.equals(userRole, Globals.INVESTIGATOR)) {
	// return clientUserRoleOptions;
	// }
	//
	// return null;
	// }

	public Map<String, String> getSystemUserRoleOptions(UserRole role) {
		Map<String, String> map = new TreeMap<String, String>();

		switch (role) {
		case SystemAdmin:
			map = addSystemAdminUserRoleSettingOptionsToMap(map);
			break;
		case PartnerAdmin:
			map = addPartnerAdminUserRoleSettingOptionsToMap(map);
			break;
		case Investigator:
			map = addInvestigatorUserRoleSettingOptionsToMap(map);
			break;
		case Administrator:
			map = addPartnerAdminUserRoleSettingOptionsToMap(map);
			break;
		default:
			map = addMinimumSystemUserRoleSettingOptionsToMap(map);
			break;
		}

		return map;
	}
	
	private Map<String,String> addSystemAdminUserRoleSettingOptionsToMap(Map<String,String> map){
		map.put(UserRole.SystemAdmin.getCode(),
				"System Administrator");
		return addPartnerAdminUserRoleSettingOptionsToMap(map);
	}
	
	private Map<String,String> addPartnerAdminUserRoleSettingOptionsToMap(Map<String,String> map){
		map.put(UserRole.PartnerAdmin.getCode(),
				"Partner Administrator");
		map.put(UserRole.Administrator.getCode(),
				"Administrator");
		map.put(UserRole.SuperCS.getCode(),
				"Super CS");
		map.put(UserRole.Compliance.getCode(),
				"Compliance");
		return addInvestigatorUserRoleSettingOptionsToMap(map);
	}
	
	private Map<String,String> addInvestigatorUserRoleSettingOptionsToMap(Map<String,String> map){
		map.put(UserRole.Investigator.getCode(),
				"Investigator");
		return addMinimumSystemUserRoleSettingOptionsToMap(map);
	}
	
	private Map<String,String> addMinimumSystemUserRoleSettingOptionsToMap(Map<String,String> map){
		map.put(UserRole.Accountant.getCode(),
				"Accounting User");
		map.put(UserRole.AccountingSupervisor.getCode(),
				"Accounting Supervisor");
		map.put(UserRole.CustomerServiceRep.getCode(),
				"Customer Service Representative");
		map.put(UserRole.ExternalCustomerServiceRep
				.getCode(), "External Customer Service");
		return map;
	}

	public Map<String, String> getClientUserRoleOptions() {
		return clientUserRoleOptions;
	}

	public Map<String, String> getClientRoleOptions() {
		return clientRoleOptions;
	}
	
	public Map<String, String> getSubscriberStatusOptions() {
		return subscriberStatusOptions;
	}

	public Map<String, String> getSubscriberTypeOptions() {
		return subscriberTypeOptions;
	}

	public Map<String, String> getCountryListOptions() {
		return countryListOptions;
	}

	public Map<String, String> getUsStateListOptions() {
		return usStateListOptions;
	}

	public Map<String, String> getRentalPeriodOptions() {
		return rentalPeriodOptions;
	}

	public TreeMap<String, String> getReportPeriodOptions() {
		return reportPeriodOptions;
	}

	public Map<String, String> getApplicationStatusOptions() {
		return applicationStatusOptions;
	}

	public Map<Character, String> getApplicationStatusStringOptions() {
		return applicationStatusStringOptions;
	}

	public TreeMap<String, String> getApplicationStateOptions() {
		return applicationStateOptions;
	}

	public Map<String, String> getApplicationTypeOptions() {
		return applicationTypeOptions;
	}

	public Map<String, String> getApplicantTypeOptions() {
		return applicantTypeOptions;
	}

	public Map<Character, String> getManagementCompanyStatusStringOptions() {
		return managementCompanyStatusStringOptions;
	}
	public Map<String, String> getResidenceTypeOptions() {
		return residenceTypeOptions;
	}

	public Map<String, String> getStreetTypes() {
		return streetTypes;
	}

	public Map<String, String> getStreetTypeOptions() {
		return streetTypeOptions;
	}

	public Map<String, String> getRentalTypeOptions() {
		return rentalTypeOptions;
	}
	
	public Map<Boolean, String> getRentalTypeOptionsForTenant() {
		return rentalTypeOptionsForTenant;
	}

	public Map<String, String> getRentalTypeOptionsNoPurchase() {
		Map<String, String> rto = new HashMap<String, String>();
		rto.putAll(rentalTypeOptions);
		rto.remove("3");

		return rto;
	}

	public Map<String, String> getOccupancyTypeOptions() {
		return occupancyTypeOptions;
	}

	public Map<String, String> getSalaryTypeOptions() {
		return salaryTypeOptions;
	}

	public Map<String, String> getRecommendationTypeOptions() {
		return recommendationTypeOptions;
	}

	public Map<String, String> getRecommendationValueOptions() {
		return recommendationValueOptions;
	}

	public Map<String, String> getYesNoOptions() {
		return yesNoOptions;
	}

	public Map<Character, String> getYNOptions() {
		return yNOptions;
	}

	public Map<Boolean, String> getTrueFalseOptions() {
		return trueFalseOptions;
	}
	
	public Map<Boolean, String> getTrueFalseOptionsForTenant() {
		return trueFalseOptionsForTenant;
	}

//	public Map<String, String> getBankAccountTypeOptions() {
//		return BankAccountType.getBankAccountTypeOptions();
//		// return bankAccountTypeOptions;
//	}
//	
//	public Map<String, String> getCheckTypeOptions() {
//		return CheckType.getCheckTypeOptions();
//		// return checkTypeOptions;
//	}
//	
//	public Map<String, String> getEmploymentTypeOptions() {
//		return EmploymentType.getEmploymentTypeOptions();
//		// return employmentTypeOptions;
//	}
//
//	public Map<String, String> getEmploymentTypeOptions2() {
//		return EmploymentType.getEmploymentTypeOptions2();
//		// return employmentTypeOptions2;
//	}
//
//	public Map<String, String> getEmploymentTypeOptionsEmail() {
//		return EmploymentType.getEmploymentTypeOptionsEmail();
//		// return employmentTypeOptionsEmail;
//	}
//
//	public Map<String, String> getEmploymentTypeOptionsEmail2() {
//		return EmploymentType.getEmploymentTypeOptionsEmail2();
//		// return employmentTypeOptionsEmail2;
//	}

	public Map<String, String> getEmploymentTypeVerifyOptions() {
		return employmentTypeVerifyOptions;
	}

	public Map<String, String> getCoapplicantTypeOptions() {
		return coapplicantTypeOptions;
	}

	public Map<String, String> getCoapplicantTypeOptionsForTenant() {
		return coapplicantTypeOptionsForTenant;
	}
	
	public Map<String, String> getPropertyOwnerOptions() {
		return propertyOwnerOptions;
	}

	public Map<String, String> getGoodBadOptions() {
		return goodBadOptions;
	}
//
//	public Map<String, String> getRelationshipOptions() {
//		return RelationshipType.getRelationshipOptions();
//		// return relationshipOptionsEmailApp;
//	}
//
//	public Map<String, String> getRelationshipOptionsEmailApp() {
//		return RelationshipType.getRelationshipOptions();
//		// return relationshipOptionsEmailApp;
//	}

	public Map<String, String> getAcctStatusOptions() {
		return acctStatusOptions;
	}

	public Map<String, String> getBankAboutCustomerOptions() {
		return bankAboutCustomerOptions;
	}

	public Map<String, String> getBankBalanceOptions() {
		return bankBalanceOptions;
	}

	public Map<String, String> getPersonalQualitiesOptions() {
		return personalQualitiesOptions;
	}

	public Map<String, String> getExternalAppDateOptions() {
		return externalAppDateOptions;
	}

	public Map<String, String> getMonthsOptions() {
		return monthsOptions;
	}

	public Map<String, String> getMonthsOptions2() {
		return monthsOptions2;
	}

	public Map<String, String> getYearsOptions() {
		return yearsOptions;
	}

	public Map<String, String> getYearsOptions2() {
		return yearsOptions2;
	}

	public Map<Integer, String> getViewApplicationsSortOptions() {
		return viewApplicationsSortOptions;
	}

	public Map<Integer, String> getViewInvoicePaymentsSortOptions() {
		return viewInvoicePaymentsSortOptions;
	}

	public Map<Integer, String> getViewClientsSortOptions() {
		return viewClientsSortOptions;
	}

	public Map<String, String> getOtherServiceOptions() {
		return otherServiceOptions;
	}

	public Map<String, String> getPetTypeOptions() {
		return petTypeOptions;
	}

	public static Globals getInstance() {
		return instance;
	}

	private Globals() {
		// Month Options
		monthsOptions2 = new LinkedHashMap<String, String>();
		monthsOptions2.put("01", "Jan");
		monthsOptions2.put("02", "Feb");
		monthsOptions2.put("03", "Mar");
		monthsOptions2.put("04", "Apr");
		monthsOptions2.put("05", "May");
		monthsOptions2.put("06", "Jun");
		monthsOptions2.put("07", "Jul");
		monthsOptions2.put("08", "Aug");
		monthsOptions2.put("09", "Sep");
		monthsOptions2.put("10", "Oct");
		monthsOptions2.put("11", "Nov");
		monthsOptions2.put("12", "Dec");

		// Year Options
		yearsOptions2 = new LinkedHashMap<String, String>();
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < 10; i++) {
			int yr = thisYear + i;
			yearsOptions2.put(String.format("%02d", yr - 2000),
					String.format("%d", yr));
		}

		// User Status Options
		petTypeOptions = new TreeMap<String, String>();
		petTypeOptions.put("D", "Dog");
		petTypeOptions.put("C", "Cat");
		petTypeOptions.put("O", "Other - Please Describe");

		// User Status Options
		userStatusOptions = new TreeMap<String, String>();
		userStatusOptions.put("A", "Active");
		userStatusOptions.put("I", "Inactive");
		userStatusOptions.put("L", "Locked");
		userStatusOptions.put("X", "Change Password");

		// // System User Role Options
		// systemUserRoleOptions = new TreeMap<String, String>();
		// systemUserRoleOptions.put(Integer.toString(UserRole.SystemAdmin.getValue()),
		// "System Administrator");
		// systemUserRoleOptions.put(Integer.toString(UserRole.PartnerAdmin.getValue()),
		// "Partner Administrator");
		// systemUserRoleOptions.put(Integer.toString(UserRole.Investigator.getValue()),
		// "Investigator");
		// systemUserRoleOptions.put(Integer.toString(UserRole.CustomerServiceRep.getValue()),
		// "Customer Service Representative");
		// systemUserRoleOptions.put(Integer.toString(UserRole.ExternalCustomerServiceRep.getValue()),
		// "External Customer Service");
		//
		// // Worker User Role Options
		// workerUserRoleOptions = new TreeMap<String, String>();
		// workerUserRoleOptions.put(Integer.toString(UserRole.PartnerAdmin.getValue()),
		// "Partner Administrator");
		// workerUserRoleOptions.put(Integer.toString(UserRole.Investigator.getValue()),
		// "Investigator");
		// workerUserRoleOptions.put(Integer.toString(UserRole.CustomerServiceRep.getValue()),
		// "Customer Service Representative");
		// workerUserRoleOptions.put(Integer.toString(UserRole.ExternalCustomerServiceRep.getValue()),
		// "External Customer Service");

		// Partner User Role Options
		// partnerUserRoleOptions = new TreeMap<String, String>();
		// partnerUserRoleOptions.put(Integer.toString(UserRole.PartnerAdmin.getValue()),
		// "Partner Administrator");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.Investigator.getValue()),
		// "Investigator");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.CustomerServiceRep.getValue()),
		// "Customer Service Representative");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.ExternalCustomerServiceRep.getValue()),
		// "External Customer Service");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.ClientAdmin.getValue()),
		// "Client Administrator");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.ClientUser.getValue()),
		// "Client User");
		// partnerUserRoleOptions.put(Integer.toString(UserRole.BoardDirector.getValue()),
		// "Board Director");
		// partnerUserRoleOptions.put("2", "Partner Administrator");
		// partnerUserRoleOptions.put("3", "Investigator");
		// partnerUserRoleOptions.put("4", "Customer Service Representative");
		// partnerUserRoleOptions.put("8", "External Customer Service");
		// partnerUserRoleOptions.put("5", "Client Administrator");
		// partnerUserRoleOptions.put("6", "Client User");
		// partnerUserRoleOptions.put("7", "Board Director");

		// Client User Role Options
		clientUserRoleOptions = new TreeMap<String, String>();
		clientUserRoleOptions.put(
				UserRole.ClientAdmin.getCode(),
				"Client Administrator");
		clientUserRoleOptions
				.put(UserRole.ClientUser.getCode(),
						"Client User");
		clientUserRoleOptions.put(
				UserRole.BoardDirector.getCode(),
				"Board Director");
		clientUserRoleOptions.put(
				UserRole.PropertyManager.getCode(),
				"Property Manager");
		// Client Role Options
		clientRoleOptions = new TreeMap<String, String>();
		clientRoleOptions.put(
				UserRole.CustomerServiceRep.getCode(),
				"Customer Service Representative");
		clientRoleOptions.put(
				UserRole.Landlord.getCode(),
				"Landlord");
		clientRoleOptions.put(
				UserRole.Renter.getCode(),
				"Renter");
		clientRoleOptions.put(
				UserRole.Realtor.getCode(),
				"Realtor");
		
		// Subscriber Status Options
		subscriberStatusOptions = new TreeMap<String, String>();
		subscriberStatusOptions.put("A", "Active");
		subscriberStatusOptions.put("I", "Inactive");

		// Subscriber Type Options
		subscriberTypeOptions = new TreeMap<String, String>();
		subscriberTypeOptions.put("P", "Partner");
		subscriberTypeOptions.put("C", "Client");

		// Country List Options
		countryListOptions = new LinkedHashMap<String, String>();
		countryListOptions.put("", "(Select One)");
		countryListOptions.put("DZ", "ALGERIA");
		countryListOptions.put("AS", "AMERICAN SAMOA");
		countryListOptions.put("AD", "ANDORRA ");
		countryListOptions.put("AO", "ANGOLA");
		countryListOptions.put("AI", "ANGUILLA");
		countryListOptions.put("AQ", "ANTARCTICA");
		countryListOptions.put("AG", "ANTIGUA AND BARBUDA");
		countryListOptions.put("AR", "ARGENTINA");
		countryListOptions.put("AM", "ARMENIA");
		countryListOptions.put("AW", "ARUBA");
		countryListOptions.put("AU", "AUSTRALIA");
		countryListOptions.put("AT", "AUSTRIA");
		countryListOptions.put("AZ", "AZERBAIJAN");
		countryListOptions.put("BS", "BAHAMAS");
		countryListOptions.put("BH", "BAHRAIN");
		countryListOptions.put("BD", "BANGLADESH");
		countryListOptions.put("BB", "BARBADOS");
		countryListOptions.put("BY", "BELARUS");
		countryListOptions.put("BE", "BELGIUM");
		countryListOptions.put("BZ", "BELIZE");
		countryListOptions.put("BJ", "BENIN");
		countryListOptions.put("BM", "BERMUDA");
		countryListOptions.put("BT", "BHUTAN");
		countryListOptions.put("BO", "BOLIVIA");
		countryListOptions.put("BA", "BOSNIA AND HERZEGOVINA");
		countryListOptions.put("BW", "BOTSWANA");
		countryListOptions.put("BV", "BOUVET ISLAND");
		countryListOptions.put("BR", "BRAZIL");
		countryListOptions.put("IO", "BRITISH INDIAN OCEAN TERRITORY");
		countryListOptions.put("BN", "BRUNEI DARUSSALAM");
		countryListOptions.put("BG", "BULGARIA");
		countryListOptions.put("BF", "BURKINA FASO");
		countryListOptions.put("BI ", "BURUNDI");
		countryListOptions.put("KH", "CAMBODIA");
		countryListOptions.put("CM", "CAMEROON");
		countryListOptions.put("CA", "CANADA");
		countryListOptions.put("CV", "CAPE VERDE");
		countryListOptions.put("KY", "CAYMAN ISLANDS");
		countryListOptions.put("CF", "CENTRAL AFRICAN REPUBLIC");
		countryListOptions.put("TD", "CHAD");
		countryListOptions.put("CL", "CHILE");
		countryListOptions.put("CN", "CHINA");
		countryListOptions.put("CX", "CHRISTMAS ISLAND");
		countryListOptions.put("CC", "COCOS (KEELING) ISLANDS");
		countryListOptions.put("CO", "COLOMBIA");
		countryListOptions.put("KM", "COMOROS");
		countryListOptions.put("CG", "CONGO");
		countryListOptions.put("CR", "COOK ISLANDS");
		countryListOptions.put("CT", "COSTA RICA");
		countryListOptions.put("CI", "COTE D'IVOIRE");
		countryListOptions.put("HR", "CROATIA");
		countryListOptions.put("CU", "CUBA");
		countryListOptions.put("CY", "CYPRUS");
		countryListOptions.put("CZ", "CZECH REPUBLIC");
		countryListOptions.put("DK", "DENMARK");
		countryListOptions.put("DJ", "DJIBOUTI");
		countryListOptions.put("DM", "DOMINICA");
		countryListOptions.put("DO", "DOMINICAN REPUBLIC");
		countryListOptions.put("EC", "ECUADOR");
		countryListOptions.put("EG", "EGYPT");
		countryListOptions.put("SV", "EL SALVADOR");
		countryListOptions.put("GQ", "EQUATORIAL GUINEA");
		countryListOptions.put("ER", "ERITREA");
		countryListOptions.put("EE", "ESTONIA");
		countryListOptions.put("ET", "ETHIOPIA");
		countryListOptions.put("FK", "FALKLAND ISLANDS");
		countryListOptions.put("FO", "FAROE ISLANDS");
		countryListOptions.put("FJ", "FIJI");
		countryListOptions.put("FI", "FINLAND");
		countryListOptions.put("FR", "FRANCE");
		countryListOptions.put("GF", "FRENCH GUIANA");
		countryListOptions.put("PF", "FRENCH POLYNESIA");
		countryListOptions.put("TF", "FRENCH SOUTHERN TERRITORIES");
		countryListOptions.put("GA", "GABON");
		countryListOptions.put("GM", "GAMBIA");
		countryListOptions.put("GE", "GEORGIA");
		countryListOptions.put("DE", "GERMANY");
		countryListOptions.put("GH", "GHANA");
		countryListOptions.put("GI", "GIBRALTAR");
		countryListOptions.put("GR", "GREECE");
		countryListOptions.put("GL", "GREENLAND");
		countryListOptions.put("GD", "GRENADA");
		countryListOptions.put("GP", "GUADELOUPE");
		countryListOptions.put("GU", "GUAM");
		countryListOptions.put("GT", "GUATEMALA");
		countryListOptions.put("GG", "GUERNSEY");
		countryListOptions.put("GN", "GUINEA");
		countryListOptions.put("GW", "GUINEA-BISSAU");
		countryListOptions.put("GY", "GUYANA");
		countryListOptions.put("HT", "HAITI");
		countryListOptions.put("HM", "HEARD ISLAND AND MCDONALD ISLANDS");
		countryListOptions.put("VA", "HOLY SEE (VATICAN CITY STATE)");
		countryListOptions.put("HN", "HONDURAS");
		countryListOptions.put("HK", "HONG KONG");
		countryListOptions.put("HU", "HUNGARY");
		countryListOptions.put("IS", "ICELAND");
		countryListOptions.put("IN", "INDIA");
		countryListOptions.put("ID", "INDONESIA");
		countryListOptions.put("IR", "IRAN, ISLAMIC REPUBLIC OF");
		countryListOptions.put("IQ", "IRAQ");
		countryListOptions.put("IE", "IRELAND");
		countryListOptions.put("IM", "ISLE OF MAN");
		countryListOptions.put("IL", "ISRAEL");
		countryListOptions.put("IT", "ITALY ");
		countryListOptions.put("JM", "JAMAICA");
		countryListOptions.put("JP", "JAPAN");
		countryListOptions.put("JE", "JERSEY");
		countryListOptions.put("JO", "JORDAN");
		countryListOptions.put("KZ", "KAZAKHSTAN");
		countryListOptions.put("KE", "KENYA");
		countryListOptions.put("KI", "KIRIBATI");
		countryListOptions.put("KP", "KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF");
		countryListOptions.put("KR", "KOREA, REPUBLIC OF");
		countryListOptions.put("KW", "KUWAIT");
		countryListOptions.put("KG", "KYRGYZSTAN");
		countryListOptions.put("LA", "LAO PEOPLE'S DEMOCRATIC REPUBLIC");
		countryListOptions.put("LV", "LATVIA");
		countryListOptions.put("LB", "LEBANON");
		countryListOptions.put("LS", "LESOTHO");
		countryListOptions.put("LR", "LIBERIA");
		countryListOptions.put("LY", "LIBYAN ARAB JAMAHIRIYA");
		countryListOptions.put("LI", "LIECHTENSTEIN");
		countryListOptions.put("LT", "LITHUANIA");
		countryListOptions.put("LU", "LUXEMBOURG");
		countryListOptions.put("MO", "MACAO");
		countryListOptions.put("MK",
				"MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF");
		countryListOptions.put("MG", "MADAGASCAR");
		countryListOptions.put("MW", "MALAWI");
		countryListOptions.put("MY", "MALAYSIA");
		countryListOptions.put("MV", "MALDIVES");
		countryListOptions.put("ML", "MALI");
		countryListOptions.put("MT", "MALTA");
		countryListOptions.put("MH", "MARSHALL ISLANDS");
		countryListOptions.put("MQ", "MARTINIQUE");
		countryListOptions.put("MR", "MAURITANIA");
		countryListOptions.put("MU", "MAURITIUS");
		countryListOptions.put("YT", "MAYOTTE");
		countryListOptions.put("MX ", "MEXICO");
		countryListOptions.put("FM", "MICRONESIA, FEDERATED STATES OF");
		countryListOptions.put("MD", "MOLDOVA");
		countryListOptions.put("MC", "MONACO");
		countryListOptions.put("MN", "MONGOLIA");
		countryListOptions.put("ME", "MONTENEGRO");
		countryListOptions.put("MS", "MONTSERRAT");
		countryListOptions.put("MA", "MOROCCO");
		countryListOptions.put("MZ", "MOZAMBIQUE");
		countryListOptions.put("MM", "MYANMAR");
		countryListOptions.put("NA", "NAMIBIA");
		countryListOptions.put("NR", "NAURU");
		countryListOptions.put("NP", "NEPAL");
		countryListOptions.put("NL", "NETHERLANDS");
		countryListOptions.put("AN", "NETHERLANDS ANTILLES");
		countryListOptions.put("NC", "NEW CALEDONIA");
		countryListOptions.put("NZ", "NEW ZEALAND");
		countryListOptions.put("NI", "NICARAGUA");
		countryListOptions.put("NE", "NIGER");
		countryListOptions.put("NG", "NIGERIA");
		countryListOptions.put("NU", "NIUE");
		countryListOptions.put("NF", "NORFOLK ISLAND");
		countryListOptions.put("MP", "NORTHERN MARIANA ISLANDS");
		countryListOptions.put("NO", "NORWAY");
		countryListOptions.put("OM", "OMAN");
		countryListOptions.put("PK", "PAKISTAN");
		countryListOptions.put("PW", "PALAU");
		countryListOptions.put("PS", "PALESTINIAN TERRITORY, OCCUPIED");
		countryListOptions.put("PA", "PANAMA");
		countryListOptions.put("PG", "PAPUA NEW GUINEA");
		countryListOptions.put("PY", "PARAGUAY");
		countryListOptions.put("PE", "PERU");
		countryListOptions.put("PH", "PHILIPPINES");
		countryListOptions.put("PN", "PITCAIRN");
		countryListOptions.put("PL", "POLAND");
		countryListOptions.put("PT", "PORTUGAL");
		countryListOptions.put("PR", "PUERTO RICO");
		countryListOptions.put("QA", "QATAR");
		countryListOptions.put("RE", "REUNION");
		countryListOptions.put("RO", "ROMANIA");
		countryListOptions.put("RU", "RUSSIAN FEDERATION");
		countryListOptions.put("RW", "RWANDA");
		countryListOptions.put("BL", "SAINT BARTHELEMY");
		countryListOptions.put("SH", "SAINT HELENA");
		countryListOptions.put("KN", "SAINT KITTS AND NEVIS");
		countryListOptions.put("LC", "SAINT LUCIA");
		countryListOptions.put("MF", "SAINT MARTIN");
		countryListOptions.put("PM", "SAINT PIERRE AND MIQUELON");
		countryListOptions.put("VC", "SAINT VINCENT AND THE GRENADINES");
		countryListOptions.put("WS", "SAMOA");
		countryListOptions.put("SM", "SAN MARINO");
		countryListOptions.put("ST", "SAO TOME AND PRINCIPE");
		countryListOptions.put("SA", "SAUDI ARABIA");
		countryListOptions.put("SN", "SENEGAL");
		countryListOptions.put("RS", "SERBIA");
		countryListOptions.put("SC", "SEYCHELLES");
		countryListOptions.put("SL", "SIERRA LEONE");
		countryListOptions.put("SG", "SINGAPORE");
		countryListOptions.put("SK", "SLOVAKIA");
		countryListOptions.put("SI", "SLOVENIA");
		countryListOptions.put("SB", "SOLOMON ISLANDS");
		countryListOptions.put("SO", "SOMALIA");
		countryListOptions.put("ZA", "SOUTH AFRICA");
		countryListOptions.put("GS",
				"SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS");
		countryListOptions.put("ES", "SPAIN");
		countryListOptions.put("LK", "SRI LANKA");
		countryListOptions.put("SD", "SUDAN");
		countryListOptions.put("SR", "SURINAME");
		countryListOptions.put("SJ", "SVALBARD AND JAN MAYEN");
		countryListOptions.put("SZ", "SWAZILAND");
		countryListOptions.put("SE", "SWEDEN");
		countryListOptions.put("CH", "SWITZERLAND");
		countryListOptions.put("SY", "SYRIAN ARAB REPUBLIC");
		countryListOptions.put("TW", "TAIWAN, PROVINCE OF CHINA");
		countryListOptions.put("TJ", "TAJIKISTAN");
		countryListOptions.put("TZ", "TANZANIA, UNITED REPUBLIC OF");
		countryListOptions.put("TH", "THAILAND");
		countryListOptions.put("TL", "TIMOR-LESTE");
		countryListOptions.put("TG", "TOGO");
		countryListOptions.put("TK", "TOKELAU");
		countryListOptions.put("TO", "TONGA");
		countryListOptions.put("TT", "TRINIDAD AND TOBAGO");
		countryListOptions.put("TN", "TUNISIA");
		countryListOptions.put("TR", "TURKEY");
		countryListOptions.put("TM", "TURKMENISTAN");
		countryListOptions.put("TC", "TURKS AND CAICOS ISLANDS");
		countryListOptions.put("TV", "TUVALU");
		countryListOptions.put("UG", "UGANDA");
		countryListOptions.put("UA", "UKRAINE");
		countryListOptions.put("AE", "UNITED ARAB EMIRATES");
		countryListOptions.put("GB", "UNITED KINGDOM");
		countryListOptions.put("US", "UNITED STATES");
		countryListOptions.put("UM", "UNITED STATES MINOR OUTLYING ISLANDS");
		countryListOptions.put("UY", "URUGUAY");
		countryListOptions.put("UZ", "UZBEKISTAN");
		countryListOptions.put("VU", "VANUATU");
		countryListOptions.put("VE", "VENEZUELA");
		countryListOptions.put("VN", "VIET NAM");
		countryListOptions.put("VG", "VIRGIN ISLANDS, BRITISH");
		countryListOptions.put("VI", "VIRGIN ISLANDS, U.S.");
		countryListOptions.put("WF", "WALLIS AND FUTUNA");
		countryListOptions.put("EH", "WESTERN SAHARA");
		countryListOptions.put("YE", "YEMEN");
		countryListOptions.put("ZM", "ZAMBIA");
		countryListOptions.put("ZW", "ZIMBABWE");

		// US State List Options
		usStateListOptions = new TreeMap<String, String>();
		usStateListOptions.put("", "(Select One)");
		usStateListOptions.put("AL", "ALABAMA");
		usStateListOptions.put("AK", "ALASKA");
		usStateListOptions.put("AZ", "ARIZONA");
		usStateListOptions.put("AR", "ARKANSAS");
		usStateListOptions.put("CA", "CALIFORNIA");
		usStateListOptions.put("CO", "COLORADO");
		usStateListOptions.put("CT", "CONNECTICUT");
		usStateListOptions.put("DE", "DELAWARE");
		usStateListOptions.put("DC", "DISTRICT OF COLUMBIA");
		usStateListOptions.put("FL", "FLORIDA");
		usStateListOptions.put("GA", "GEORGIA");
		usStateListOptions.put("ID", "IDAHO");
		usStateListOptions.put("IL", "ILLINOIS");
		usStateListOptions.put("IN", "INDIANA");
		usStateListOptions.put("IA", "IOWA");
		usStateListOptions.put("KS", "KANSAS");
		usStateListOptions.put("KY", "KENTUCKY");
		usStateListOptions.put("LA", "LOUISIANA");
		usStateListOptions.put("ME", "MAINE");
		usStateListOptions.put("MD", "MARYLAND");
		usStateListOptions.put("MA", "MASSACHUSETTS");
		usStateListOptions.put("MI", "MICHIGAN");
		usStateListOptions.put("MN", "MINNESOTA");
		usStateListOptions.put("MS", "MISSISSIPPI");
		usStateListOptions.put("MO", "MISSOURI");
		usStateListOptions.put("MT", "MONTANA");
		usStateListOptions.put("NE", "NEBRASKA");
		usStateListOptions.put("NV", "NEVADA");
		usStateListOptions.put("NH", "NEW HAMPSHIRE");
		usStateListOptions.put("NJ", "NEW JERSEY");
		usStateListOptions.put("NM", "NEW MEXICO");
		usStateListOptions.put("NY", "NEW YORK");
		usStateListOptions.put("NC", "NORTH CAROLINA");
		usStateListOptions.put("ND", "NORTH DAKOTA");
		usStateListOptions.put("OH", "OHIO");
		usStateListOptions.put("OK", "OKLAHOMA");
		usStateListOptions.put("OR", "OREGON");
		usStateListOptions.put("PA", "PENNSYLVANIA");
		usStateListOptions.put("RI", "RHODE ISLAND");
		usStateListOptions.put("SC", "SOUTH CAROLINA");
		usStateListOptions.put("SD", "SOUTH DAKOTA");
		usStateListOptions.put("TN", "TENNESSEE");
		usStateListOptions.put("TX", "TEXAS");
		usStateListOptions.put("UT", "UTAH");
		usStateListOptions.put("VT", "VERMONT");
		usStateListOptions.put("VA", "VIRGINIA");
		usStateListOptions.put("WA", "WASHINGTON");
		usStateListOptions.put("WV", "WEST VIRGINIA");
		usStateListOptions.put("WI", "WISCONSIN");
		usStateListOptions.put("WY", "WYOMING");
		usStateListOptions.put("HI", "HAWAII");
		usStateListOptions.put("AS", "AMERICAN SAMOA");
		usStateListOptions.put("GU", "GUAM");
		usStateListOptions.put("MP", "NORTHERN MARIANA ISLANDS");
		usStateListOptions.put("PR", "PUERTO RICO");
		usStateListOptions.put("VI", "VIRGIN ISLANDS");
		usStateListOptions.put("FM", "FEDERATED STATES OF MICRONESIA");
		usStateListOptions.put("MH", "MARSHALL ISLANDS");
		usStateListOptions.put("PW", "PALAU");
		usStateListOptions.put("UM", "U.S. MINOR OUTLYING ISLANDS");
		usStateListOptions.put("AB", "ALBERTA");
		usStateListOptions.put("BC", "BRITISH COLUMBIA");
		usStateListOptions.put("MB", "MANITOBA");
		usStateListOptions.put("NB", "NEW BRUNSWICK");
		usStateListOptions.put("NL", "NEWFOUNDLAND AND LABRADOR");
		usStateListOptions.put("NS", "NOVA SCOTIA");
		usStateListOptions.put("NT", "NORTHWEST TERRITORIES");
		usStateListOptions.put("NU", "NUNAVUT");
		usStateListOptions.put("ON", "ONTARIO");
		usStateListOptions.put("PE", "PRINCE EDWARD ISLAND");
		usStateListOptions.put("QC", "QUEBEC");
		usStateListOptions.put("SK", "SASKATCHEWAN");
		usStateListOptions.put("YT", "YUKON");

		// Rental Period Options
		rentalPeriodOptions = new TreeMap<String, String>();
		rentalPeriodOptions.put("", "(Select One)");
		rentalPeriodOptions.put("2", "Daily");
		rentalPeriodOptions.put("3", "Weekly");
		rentalPeriodOptions.put("1", "Monthly");
		rentalPeriodOptions.put("4", "Quarterly");
		rentalPeriodOptions.put("5", "Yearly");

		reportPeriodOptions = new TreeMap<String, String>();
		reportPeriodOptions.put("", "(Select One)");
		reportPeriodOptions.put("3", "3 Years");
		reportPeriodOptions.put("7", "7 Years");

		// Applicant Type Options
		applicantTypeOptions = new TreeMap<String, String>();
		applicantTypeOptions.put("", "(Select One)");
		applicantTypeOptions.put("Co-Applicant", "Co-Applicant");
		applicantTypeOptions.put("Co-Signer", "Co-Signer");
		applicantTypeOptions.put("Primary", "Primary");
		applicantTypeOptions.put("Roommate", "Roommate");
		applicantTypeOptions.put("Spouse", "Spouse");

		// Application Type Options
		applicationTypeOptions = new TreeMap<String, String>();
		applicationTypeOptions.put("T", "Tenant");
		applicationTypeOptions.put("E", "Employment");

		// Application Source Options
		applicationSourceOptions = new TreeMap<String, String>();
		applicationSourceOptions.put("M", "Manual");
		applicationSourceOptions.put("E", "Email");
		applicationSourceOptions.put("W", "Web");
		applicationSourceOptions.put("O", "Other");

		// Application Status Options
		applicationStatusOptions = new TreeMap<String, String>();
		applicationStatusOptions.put("N", "New");
		applicationStatusOptions.put("P", "Pending");
		applicationStatusOptions.put("C", "Completed");
		applicationStatusOptions.put("R", "Remove");
		applicationStatusOptions.put("D", "Delete");

		// Application Status String Options
		applicationStatusStringOptions = new LinkedHashMap<Character, String>();
		applicationStatusStringOptions.put('C', "Complete");
		applicationStatusStringOptions.put('N', "New");
		applicationStatusStringOptions.put('S', "Saved");
		applicationStatusStringOptions.put('A', "Assigned");
		applicationStatusStringOptions.put('P', "In Process");
		applicationStatusStringOptions.put('R', "Remove");
		applicationStatusStringOptions.put('D', "Delete");

		// Application Status String Options
		applicationStateOptions = new TreeMap<String, String>();
		applicationStateOptions.put("", " ");
		applicationStateOptions.put("AP", "Approved");
		applicationStateOptions.put("DE", "Declined");
		applicationStateOptions.put("PE", "Pending");
		applicationStateOptions.put("FR", "For Further Review");
		applicationStateOptions.put("PB", "Pending Board Approval");
		applicationStateOptions.put("BA", "Board Approved");

		// Management Company Status String Options
		managementCompanyStatusStringOptions = new LinkedHashMap<Character, String>();
		managementCompanyStatusStringOptions.put('A', "Active");
		managementCompanyStatusStringOptions.put('I', "Inactive");
		managementCompanyStatusStringOptions.put('P', "In Process");
					
		// Residence Type Options
		residenceTypeOptions = new TreeMap<String, String>();
		residenceTypeOptions.put("0", "(Select One)");
		residenceTypeOptions.put("3", "Corp-Housing");
		residenceTypeOptions.put("1", "Apartment Community");
		residenceTypeOptions.put("2", "Employer");

		// // Employment Type Options
		// employmentTypeOptions = new TreeMap<String, String>();
		// employmentTypeOptions.put("", "(Select One)");
		// employmentTypeOptions.put(DISABILITY, "Disabled");
		// employmentTypeOptions.put(EMPLOYED, "Employed");
		// employmentTypeOptions.put(FULLTIMESTUDENT, "Full Time Student");
		// employmentTypeOptions.put(WIFE, "Homemaker");
		// employmentTypeOptions.put(RETIRED, "Retired");
		// employmentTypeOptions.put(SELFEMPLOYED, "Self Employed");
		// employmentTypeOptions.put(UNEMPLOYED, "Unemployed");
		// Employment Type Options
		// employmentTypeOptions = new TreeMap<String, String>();
		// employmentTypeOptions.put(EmploymentType.UNKNOWN.getCode(),
		// "(Select One)");
		// employmentTypeOptions.put(EmploymentType.DISABLED.getCode(),
		// EmploymentType.DISABLED.getLabel());
		// employmentTypeOptions.put(EmploymentType.EMPLOYED.getCode(),
		// EmploymentType.EMPLOYED.getLabel());
		// employmentTypeOptions.put(EmploymentType.STUDENT.getCode(),
		// EmploymentType.STUDENT.getLabel());
		// employmentTypeOptions.put(EmploymentType.WIFE.getCode(),
		// EmploymentType.WIFE.getLabel());
		// employmentTypeOptions.put(EmploymentType.RETIRED.getCode(),
		// EmploymentType.RETIRED.getLabel());
		// employmentTypeOptions.put(EmploymentType.SELFEMPLOYED.getCode(),
		// EmploymentType.SELFEMPLOYED.getLabel());
		// employmentTypeOptions.put(EmploymentType.UNEMPLOYED.getCode(),
		// EmploymentType.UNEMPLOYED.getLabel());
		//
		// // Employment Type Options
		// employmentTypeOptions2 = new TreeMap<String, String>();
		// employmentTypeOptions2.put(EmploymentType.UNKNOWN.getCode(),
		// "(Select One)");
		// employmentTypeOptions2.put(EmploymentType.EMPLOYED.getCode(),
		// EmploymentType.EMPLOYED.getLabel());
		// employmentTypeOptions2.put(EmploymentType.SELFEMPLOYED.getCode(),
		// EmploymentType.SELFEMPLOYED.getLabel());
		//
		// // Employment Type Options
		// employmentTypeOptionsEmail = new TreeMap<String, String>();
		// employmentTypeOptionsEmail.put(EmploymentType.UNKNOWN.getCode(),
		// "(Select One)");
		// employmentTypeOptionsEmail.put(EmploymentType.DISABLED.getCode(),
		// EmploymentType.DISABLED.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.EMPLOYED.getCode(),
		// EmploymentType.EMPLOYED.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.STUDENT.getCode(),
		// EmploymentType.STUDENT.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.WIFE.getCode(),
		// EmploymentType.WIFE.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.RETIRED.getCode(),
		// EmploymentType.RETIRED.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.SELFEMPLOYED.getCode(),
		// EmploymentType.SELFEMPLOYED.getLabel());
		// employmentTypeOptionsEmail.put(EmploymentType.UNEMPLOYED.getCode(),
		// EmploymentType.UNEMPLOYED.getLabel());
		//
		// // Employment Type Options
		// employmentTypeOptionsEmail2 = new TreeMap<String, String>();
		// employmentTypeOptionsEmail2.put(EmploymentType.UNKNOWN.getCode(),
		// "(Select One)");
		// employmentTypeOptionsEmail2.put(EmploymentType.DISABLED.getCode(),
		// EmploymentType.DISABLED.getLabel());
		// employmentTypeOptionsEmail2.put(EmploymentType.EMPLOYED.getCode(),
		// EmploymentType.EMPLOYED.getLabel());
		// employmentTypeOptionsEmail2.put(EmploymentType.STUDENT.getCode(),
		// EmploymentType.STUDENT.getLabel());
		// employmentTypeOptionsEmail2.put(EmploymentType.WIFE.getCode(),
		// EmploymentType.WIFE.getLabel());
		// employmentTypeOptionsEmail2.put(EmploymentType.SELFEMPLOYED.getCode(),
		// EmploymentType.SELFEMPLOYED.getLabel());

		// // Employment Type Options
		// employmentTypeOptions2 = new TreeMap<String, String>();
		// employmentTypeOptions2.put("", "(Select One)");
		// employmentTypeOptions2.put(EMPLOYED, "Employed");
		// employmentTypeOptions2.put(SELFEMPLOYED, "Self Employed");
		//
		// // Employment Type Options
		// employmentTypeOptionsEmail = new TreeMap<String, String>();
		// employmentTypeOptionsEmail.put("", "(Select One)");
		// employmentTypeOptionsEmail.put(DISABILITY, "Disabled");
		// employmentTypeOptionsEmail.put(EMPLOYED, "Employed");
		// employmentTypeOptionsEmail.put(FULLTIMESTUDENT, "Full Time Student");
		// employmentTypeOptionsEmail.put(WIFE, "Homemaker");
		// employmentTypeOptionsEmail.put(RETIRED, "Retired");
		// employmentTypeOptionsEmail.put(SELFEMPLOYED, "Self Employed");
		// employmentTypeOptionsEmail.put(UNEMPLOYED, "Unemployed");
		//
		// // Employment Type Options
		// employmentTypeOptionsEmail2 = new TreeMap<String, String>();
		// employmentTypeOptionsEmail2.put("", "(Select One)");
		// employmentTypeOptionsEmail2.put(DISABILITY, "Disabled");
		// employmentTypeOptionsEmail2.put(EMPLOYED, "Employed");
		// employmentTypeOptionsEmail2.put(FULLTIMESTUDENT,
		// "Full Time Student");
		// employmentTypeOptionsEmail2.put(WIFE, "Homemaker");
		// employmentTypeOptionsEmail2.put(SELFEMPLOYED, "Self Employed");

		// Employment type verify Options
		employmentTypeVerifyOptions = new TreeMap<String, String>();
		employmentTypeVerifyOptions.put("", "(Select One)");
		employmentTypeVerifyOptions.put("2", "Full Time");
		employmentTypeVerifyOptions.put("1", "Part Time");

		// Co-Applicant Type Options
		coapplicantTypeOptions = new TreeMap<String, String>();
		coapplicantTypeOptions.put("", "(Select One)");
		coapplicantTypeOptions.put("S", "Spouse");
		coapplicantTypeOptions.put("F", "Friend");
		coapplicantTypeOptions.put("O", "Other");

		// Co-Applicant Type Options for Tenant
		coapplicantTypeOptionsForTenant = new TreeMap<String, String>();
		coapplicantTypeOptionsForTenant.put("", "(Select One)");
		coapplicantTypeOptionsForTenant.put("0", "No");
		coapplicantTypeOptionsForTenant.put("1", "Legal Husband/Wife");
		coapplicantTypeOptionsForTenant.put("2", "Roommate/Other");
		coapplicantTypeOptionsForTenant.put("3", "I am moving in with a current resident");
		
		// USPS Street Types
		streetTypes = new TreeMap<String, String>();
		streetTypes.put("", "(NONE)");
		streetTypes.put("ALY", "ALLEY");
		streetTypes.put("ANX", "ANNEX");
		streetTypes.put("ARC", "ARCADE");
		streetTypes.put("AVE", "AVENUE");
		streetTypes.put("BYU", "BAYOU");
		streetTypes.put("BCH", "BEACH");
		streetTypes.put("BND", "BEND");
		streetTypes.put("BLF", "BLUFF");
		streetTypes.put("BLFS", "BLUFFS");
		streetTypes.put("BTM", "BOTTOM");
		streetTypes.put("BLVD", "BOULEVARD");
		streetTypes.put("BR", "BRANCH");
		streetTypes.put("BRG", "BRIDGE");
		streetTypes.put("BRK", "BROOK");
		streetTypes.put("BRKS", "BROOKS");
		streetTypes.put("BG", "BURG");
		streetTypes.put("BGS", "BURGS");
		streetTypes.put("BYP", "BYPASS");
		streetTypes.put("CP", "CAMP");
		streetTypes.put("CYN", "CANYON");
		streetTypes.put("CPE", "CAPE");
		streetTypes.put("CSWY", "CAUSEWAY");
		streetTypes.put("CTR", "CENTER");
		streetTypes.put("CTRS", "CENTERS");
		streetTypes.put("CIR", "CIRCLE");
		streetTypes.put("CIRS", "CIRCLES");
		streetTypes.put("CLF", "CLIFF");
		streetTypes.put("CLFS", "CLIFFS");
		streetTypes.put("CLB", "CLUB");
		streetTypes.put("CMN", "COMMON");
		streetTypes.put("CMNS", "COMMONS");
		streetTypes.put("COR", "CORNER");
		streetTypes.put("CORS", "CORNERS");
		streetTypes.put("CRSE", "COURSE");
		streetTypes.put("CT", "COURT");
		streetTypes.put("CTS", "COURTS");
		streetTypes.put("CV", "COVE");
		streetTypes.put("CVS", "COVES");
		streetTypes.put("CRK", "CREEK");
		streetTypes.put("CRES", "CRESCENT");
		streetTypes.put("CRST", "CREST");
		streetTypes.put("XING", "CROSSING");
		streetTypes.put("XRD", "CROSSROAD");
		streetTypes.put("XRDS", "CROSSROADS");
		streetTypes.put("CURV", "CURVE");
		streetTypes.put("DL", "DALE");
		streetTypes.put("DM", "DAM");
		streetTypes.put("DV", "DIVIDE");
		streetTypes.put("DR", "DRIVE");
		streetTypes.put("DRS", "DRIVES");
		streetTypes.put("EST", "ESTATE");
		streetTypes.put("ESTS", "ESTATES");
		streetTypes.put("EXPY", "EXPRESSWAY");
		streetTypes.put("EXT", "EXTENSION");
		streetTypes.put("EXTS", "EXTENSIONS");
		streetTypes.put("FALL", "FALL");
		streetTypes.put("FLS", "FALLS");
		streetTypes.put("FRY", "FERRY");
		streetTypes.put("FLD", "FIELD");
		streetTypes.put("FLDS", "FIELDS");
		streetTypes.put("FLT", "FLAT");
		streetTypes.put("FLTS", "FLATS");
		streetTypes.put("FRD", "FORD");
		streetTypes.put("FRDS", "FORDS");
		streetTypes.put("FRST", "FOREST");
		streetTypes.put("FRG", "FORGE");
		streetTypes.put("FRGS", "FORGES");
		streetTypes.put("FRK", "FORK");
		streetTypes.put("FRKS", "FORKS");
		streetTypes.put("FT", "FORT");
		streetTypes.put("FWY", "FREEWAY");
		streetTypes.put("GDN", "GARDEN");
		streetTypes.put("GDNS", "GARDENS");
		streetTypes.put("GTWY", "GATEWAY");
		streetTypes.put("GLN", "GLEN");
		streetTypes.put("GLNS", "GLENS");
		streetTypes.put("GRN", "GREEN");
		streetTypes.put("GRNS", "GREENS");
		streetTypes.put("GRV", "GROVE");
		streetTypes.put("GRVS", "GROVES");
		streetTypes.put("HBR", "HARBOR");
		streetTypes.put("HBRS", "HARBORS");
		streetTypes.put("HVN", "HAVEN");
		streetTypes.put("HTS", "HEIGHTS");
		streetTypes.put("HWY", "HIGHWAY");
		streetTypes.put("HL", "HILL");
		streetTypes.put("HLS", "HILLS");
		streetTypes.put("HOLW", "HOLLOW");
		streetTypes.put("INLT", "INLET");
		streetTypes.put("IS", "ISLAND");
		streetTypes.put("ISS", "ISLANDS");
		streetTypes.put("ISLE", "ISLE");
		streetTypes.put("JCT", "JUNCTION");
		streetTypes.put("JCTS", "JUNCTIONS");
		streetTypes.put("KY", "KEY");
		streetTypes.put("KYS", "KEYS");
		streetTypes.put("KNL", "KNOLL");
		streetTypes.put("KNLS", "KNOLLS");
		streetTypes.put("LK", "LAKE");
		streetTypes.put("LKS", "LAKES");
		streetTypes.put("LAND", "LAND");
		streetTypes.put("LNDG", "LANDING");
		streetTypes.put("LN", "LANE");
		streetTypes.put("LGT", "LIGHT");
		streetTypes.put("LGTS", "LIGHTS");
		streetTypes.put("LF", "LOAF");
		streetTypes.put("LCK", "LOCK");
		streetTypes.put("LCKS", "LOCKS");
		streetTypes.put("LDG", "LODGE");
		streetTypes.put("LOOP", "LOOP");
		streetTypes.put("MALL", "MALL");
		streetTypes.put("MNR", "MANOR");
		streetTypes.put("MNRS", "MANORS");
		streetTypes.put("MDW", "MEADOW");
		streetTypes.put("MDWS", "MEADOWS");
		streetTypes.put("MEWS", "MEWS");
		streetTypes.put("ML", "MILL");
		streetTypes.put("MLS", "MILLS");
		streetTypes.put("MSN", "MISSION");
		streetTypes.put("MTWY", "MOTORWAY");
		streetTypes.put("MT", "MOUNT");
		streetTypes.put("MTN", "MOUNTAIN");
		streetTypes.put("MTNS", "MOUNTAINS");
		streetTypes.put("NCK", "NECK");
		streetTypes.put("ORCH", "ORCHARD");
		streetTypes.put("OVAL", "OVAL");
		streetTypes.put("OPAS", "OVERPASS");
		streetTypes.put("PARK", "PARK");
		streetTypes.put("PARK", "PARKS");
		streetTypes.put("PKWY", "PARKWAY");
		streetTypes.put("PKWY", "PARKWAYS");
		streetTypes.put("PASS", "PASS");
		streetTypes.put("PSGE", "PASSAGE");
		streetTypes.put("PATH", "PATH");
		streetTypes.put("PIKE", "PIKE");
		streetTypes.put("PNE", "PINE");
		streetTypes.put("PNES", "PINES");
		streetTypes.put("PL", "PLACE");
		streetTypes.put("PLN", "PLAIN");
		streetTypes.put("PLNS", "PLAINS");
		streetTypes.put("PLZ", "PLAZA");
		streetTypes.put("PT", "POINT");
		streetTypes.put("PTS", "POINTS");
		streetTypes.put("PRT", "PORT");
		streetTypes.put("PRTS", "PORTS");
		streetTypes.put("PR", "PRAIRIE");
		streetTypes.put("RADL", "RADIAL");
		streetTypes.put("RAMP", "RAMP");
		streetTypes.put("RNCH", "RANCH");
		streetTypes.put("RPD", "RAPID");
		streetTypes.put("RPDS", "RAPIDS");
		streetTypes.put("RST", "REST");
		streetTypes.put("RDG", "RIDGE");
		streetTypes.put("RDGS", "RIDGES");
		streetTypes.put("RIV", "RIVER");
		streetTypes.put("RD", "ROAD");
		streetTypes.put("RDS", "ROADS");
		streetTypes.put("RTE", "ROUTE");
		streetTypes.put("ROW", "ROW");
		streetTypes.put("RUE", "RUE");
		streetTypes.put("RUN", "RUN");
		streetTypes.put("SHL", "SHOAL");
		streetTypes.put("SHLS", "SHOALS");
		streetTypes.put("SHR", "SHORE");
		streetTypes.put("SHRS", "SHORES");
		streetTypes.put("SKWY", "SKYWAY");
		streetTypes.put("SPG", "SPRING");
		streetTypes.put("SPGS", "SPRINGS");
		streetTypes.put("SPUR", "SPUR");
		streetTypes.put("SPUR", "SPURS");
		streetTypes.put("SQ", "SQUARE");
		streetTypes.put("SQS", "SQUARES");
		streetTypes.put("STA", "STATION");
		streetTypes.put("STRA", "STRAVENUE");
		streetTypes.put("STRM", "STREAM");
		streetTypes.put("ST", "STREET");
		streetTypes.put("STS", "STREETS");
		streetTypes.put("SMT", "SUMMIT");
		streetTypes.put("TER", "TERRACE");
		streetTypes.put("TRWY", "THROUGHWAY");
		streetTypes.put("TRCE", "TRACE");
		streetTypes.put("TRAK", "TRACK");
		streetTypes.put("TRFY", "TRAFFICWAY");
		streetTypes.put("TRL", "TRAIL");
		streetTypes.put("TRLR", "TRAILER");
		streetTypes.put("TUNL", "TUNNEL");
		streetTypes.put("TPKE", "TURNPIKE");
		streetTypes.put("UPAS", "UNDERPASS");
		streetTypes.put("UN", "UNION");
		streetTypes.put("UNS", "UNIONS");
		streetTypes.put("VLY", "VALLEY");
		streetTypes.put("VLYS", "VALLEYS");
		streetTypes.put("VIA", "VIADUCT");
		streetTypes.put("VW", "VIEW");
		streetTypes.put("VWS", "VIEWS");
		streetTypes.put("VLG", "VILLAGE");
		streetTypes.put("VLGS", "VILLAGES");
		streetTypes.put("VL", "VILLE");
		streetTypes.put("VIS", "VISTA");
		streetTypes.put("WALK", "WALK");
		streetTypes.put("WALK", "WALKS");
		streetTypes.put("WALL", "WALL");
		streetTypes.put("WAY", "WAY");
		streetTypes.put("WAYS", "WAYS");
		streetTypes.put("WL", "WELL");
		streetTypes.put("WLS", "WELLS");

		// Street Type Options
		streetTypeOptions = new TreeMap<String, String>();
		streetTypeOptions.put("", "(None)");
		streetTypeOptions.put("AL", "Alley");
		streetTypeOptions.put("AX", "Annex");
		streetTypeOptions.put("AD", "Arcade");
		streetTypeOptions.put("AV", "Avenue");
		streetTypeOptions.put("BY", "Bayou");
		streetTypeOptions.put("BH", "Beach");
		streetTypeOptions.put("BD", "Bend");
		streetTypeOptions.put("BF", "Bluff");
		streetTypeOptions.put("BL", "Bluffs");
		streetTypeOptions.put("BM", "Bottom");
		streetTypeOptions.put("BV", "Boulevard");
		streetTypeOptions.put("BN", "Branch");
		streetTypeOptions.put("BE", "Bridge");
		streetTypeOptions.put("BK", "Brook");
		streetTypeOptions.put("BS", "Brooks");
		streetTypeOptions.put("BG", "Burg");
		streetTypeOptions.put("BU", "Burgs");
		streetTypeOptions.put("BP", "Bypass");
		streetTypeOptions.put("CN", "Center");
		streetTypeOptions.put("CI", "Circle");
		streetTypeOptions.put("CT", "Court");
		streetTypeOptions.put("CR", "Crescent");
		streetTypeOptions.put("DA", "Dale");
		streetTypeOptions.put("DR", "Drive");
		streetTypeOptions.put("EX", "Expressway");
		streetTypeOptions.put("FR", "Freeway");
		streetTypeOptions.put("GA", "Garden");
		streetTypeOptions.put("GT", "Gate");
		streetTypeOptions.put("GR", "Grove");
		streetTypeOptions.put("HI", "Heights");
		streetTypeOptions.put("HY", "Highway");
		streetTypeOptions.put("HL", "Hill");
		streetTypeOptions.put("KN", "Knoll");
		streetTypeOptions.put("LN", "Lane");
		streetTypeOptions.put("LP", "Loop");
		streetTypeOptions.put("MA", "Mall");
		streetTypeOptions.put("OV", "Oval");
		streetTypeOptions.put("PK", "Park");
		streetTypeOptions.put("PA", "Parkway");
		streetTypeOptions.put("PI", "Pike");
		streetTypeOptions.put("Pl", "Place");
		streetTypeOptions.put("PZ", "Plaza");
		streetTypeOptions.put("PT", "Point");
		streetTypeOptions.put("RD", "Road");
		streetTypeOptions.put("RT", "Route");
		streetTypeOptions.put("RO", "Row ");
		streetTypeOptions.put("RN", "Run");
		streetTypeOptions.put("SQ", "Square");
		streetTypeOptions.put("ST", "Street");
		streetTypeOptions.put("TE", "Terrace");
		streetTypeOptions.put("TY", "Thruway");
		streetTypeOptions.put("TR", "Trail");
		streetTypeOptions.put("TP", "Turnpike");
		streetTypeOptions.put("VI", "Viaduct");
		streetTypeOptions.put("WK", "Walk");
		streetTypeOptions.put("WY", "Way");

		// Rental Type Options
		rentalTypeOptions = new TreeMap<String, String>();
		rentalTypeOptions.put("", "(Select One)");
		rentalTypeOptions.put("2", "Lease");
		rentalTypeOptions.put("3", "Purchase");
		rentalTypeOptions.put("1", "Rent");
		rentalTypeOptions.put("4", "Guest");

		// Purchase True or False - Rental Type Options for Tenant
		rentalTypeOptionsForTenant = new HashMap<Boolean, String>();
		Boolean nullValue = null;
		rentalTypeOptionsForTenant.put(nullValue, "(Select One)");
		rentalTypeOptionsForTenant.put(false, "Rent/Lease");
		rentalTypeOptionsForTenant.put(true, "Purchase");
		
		// Occupancy Type Options
		occupancyTypeOptions = new TreeMap<String, String>();
		occupancyTypeOptions.put("", "(Select One)");
		occupancyTypeOptions.put("2", "Lease");
		occupancyTypeOptions.put("3", "Purchase");

		// Salary Type Options
		salaryTypeOptions = new TreeMap<String, String>();
		salaryTypeOptions.put("", "(Select One)");
		salaryTypeOptions.put("1", "Monthly");
		salaryTypeOptions.put("2", "Semi-Monthly");
		salaryTypeOptions.put("3", "Bi-Weekly");
		salaryTypeOptions.put("4", "Weekly");
		salaryTypeOptions.put("5", "Annually");

		// Recommendation Type Options
		recommendationTypeOptions = new TreeMap<String, String>();
		recommendationTypeOptions.put("", "(Select One)");
		recommendationTypeOptions.put("1", "Rental History");
		recommendationTypeOptions.put("2", "Employment");
		recommendationTypeOptions.put("3", "Credit");
		recommendationTypeOptions.put("4", "Income");
		recommendationTypeOptions.put("5", "Public Records");

		// Recommendation Value Options
		recommendationValueOptions = new TreeMap<String, String>();
		recommendationValueOptions.put("", "(Select One)");
		recommendationValueOptions.put("1", "Meets Criteria");
		recommendationValueOptions.put("2", "Does not Meet Criteria");
		recommendationValueOptions.put("3", "Referral");
		recommendationValueOptions.put("4", "Need More Data");
		recommendationValueOptions.put("5", "Warning");
		recommendationValueOptions.put("6", "Review for further instructions");

		// Yes No Options
		yesNoOptions = new TreeMap<String, String>();
		yesNoOptions.put("Y", "Yes");
		yesNoOptions.put("N", "No");
		
		// Y N Options
		yNOptions = new TreeMap<Character, String>();
		yNOptions.put('Y', "Yes");
		yNOptions.put('N', "No");

		// true/false Options
		trueFalseOptions = new TreeMap<Boolean, String>();
		trueFalseOptions.put(true, "Yes");
		trueFalseOptions.put(false, "No");

		// true/false Options for Tenant
		trueFalseOptionsForTenant = new HashMap<Boolean, String>();
		Boolean nullVal = null;
		trueFalseOptionsForTenant.put(nullVal,"(Select One)");
		trueFalseOptionsForTenant.put(true, "Yes");
		trueFalseOptionsForTenant.put(false, "No");
		
		// Property Owner Options
		propertyOwnerOptions = new TreeMap<String, String>();
		propertyOwnerOptions.put("", "(Select One)");
		propertyOwnerOptions.put("3", "Assistant Manager");
		propertyOwnerOptions.put("2", "Manager");
		propertyOwnerOptions.put("1", "Owner");

		// Good Bad Options
		goodBadOptions = new TreeMap<String, String>();
		goodBadOptions.put("G", "Good");
		goodBadOptions.put("B", "Bad");

		// // Character verification Relationship Options
		// relationshipOptionsEmailApp = new TreeMap<String, String>();
		// relationshipOptionsEmailApp.put("", "(Select One)");
		// relationshipOptionsEmailApp.put("16", "Business Partner");
		// relationshipOptionsEmailApp.put("15", "Co-worker");
		// relationshipOptionsEmailApp.put("18", "Colleague");
		// relationshipOptionsEmailApp.put("12", "Friend");
		// Character verification Relationship Options
		// relationshipOptionsEmailApp = new TreeMap<String, String>();
		// relationshipOptionsEmailApp.put(RelationshipType.UNKNOWN.getCode(),
		// "(Select One)");
		// relationshipOptionsEmailApp.put(RelationshipType.BUSINESSPARTNER.getCode(),
		// RelationshipType.BUSINESSPARTNER.getLabel());
		// relationshipOptionsEmailApp.put(RelationshipType.COWORKER.getCode(),
		// RelationshipType.COWORKER.getLabel());
		// relationshipOptionsEmailApp.put(RelationshipType.COLLEAGUE.getCode(),
		// RelationshipType.COLLEAGUE.getLabel());
		// relationshipOptionsEmailApp.put(RelationshipType.FRIEND.getCode(),
		// RelationshipType.FRIEND.getLabel());

		// Bank Account Status Options
		acctStatusOptions = new TreeMap<String, String>();
		acctStatusOptions.put("", "(Select One)");
		acctStatusOptions.put("1", "Satisfactory");
		acctStatusOptions.put("2", "Unsatisfactory");
		acctStatusOptions.put("3", "Closed");

		// In Bank About Customer Options
		bankAboutCustomerOptions = new TreeMap<String, String>();
		bankAboutCustomerOptions.put("", "(Select One)");
		bankAboutCustomerOptions
				.put("1",
						"A valued customer with a good reputation and financial responsibility");
		bankAboutCustomerOptions.put("2",
				"Honest and reliable, but limited capital resources");
		bankAboutCustomerOptions.put("3", "Unknown to us");
		bankAboutCustomerOptions.put("4",
				"A new customer - our experience is limited");

		// In Bank Balance Options
		bankBalanceOptions = new TreeMap<String, String>();
		bankBalanceOptions.put("", "(Select One)");
		bankBalanceOptions.put("1", "Low");
		bankBalanceOptions.put("2", "Moderate");
		bankBalanceOptions.put("3", "Medium");
		bankBalanceOptions.put("4", "High");

		// Personal Qualities
		personalQualitiesOptions = new TreeMap<String, String>();
		personalQualitiesOptions.put("1", "Responsible");
		personalQualitiesOptions.put("2", "Irresponsible");
		personalQualitiesOptions.put("3", "Trustworthy");
		personalQualitiesOptions.put("4", "UnTrustworthy");
		personalQualitiesOptions.put("5", "Honest");
		personalQualitiesOptions.put("6", "Dishonest");
		personalQualitiesOptions.put("7", "Recommended");
		personalQualitiesOptions.put("8", "Hardworking Individual");
		personalQualitiesOptions.put("9", "Independent");
		personalQualitiesOptions.put("10", "Friendly");
		personalQualitiesOptions.put("11", "Outgoing");
		personalQualitiesOptions.put("12", "No Comment");

		// External user's date opetions
		externalAppDateOptions = new LinkedHashMap<String, String>();
		for (int i = 0; i > -3; i--) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, (i));
			String day = mmddyyyy.format(cal.getTime());
			externalAppDateOptions.put(day, day);
		}

		// Years Options
		yearsOptions = new LinkedHashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		for (int i = -80; i <= 160; i++) {
			yearsOptions.put(String.valueOf(cal.get(Calendar.YEAR) + i),
					String.valueOf(cal.get(Calendar.YEAR) + i));
		}

		// Months Options
		monthsOptions = new LinkedHashMap<String, String>();
		monthsOptions.put("1", "January");
		monthsOptions.put("2", "February");
		monthsOptions.put("3", "March");
		monthsOptions.put("4", "April");
		monthsOptions.put("5", "May");
		monthsOptions.put("6", "June");
		monthsOptions.put("7", "July");
		monthsOptions.put("8", "August");
		monthsOptions.put("9", "September");
		monthsOptions.put("10", "October");
		monthsOptions.put("11", "November");
		monthsOptions.put("12", "December");

		// View Applications Sort Options
		viewApplicationsSortOptions = new HashMap<Integer, String>();
		viewApplicationsSortOptions.put(0, "a.reportState");
		viewApplicationsSortOptions.put(1, "a.statusCode");
		viewApplicationsSortOptions.put(2, "a.type");
		viewApplicationsSortOptions.put(3, "app.firstName");
		viewApplicationsSortOptions.put(4, "app.lastName");
		viewApplicationsSortOptions.put(5, "app.ssn");
		viewApplicationsSortOptions.put(6, "a.client.name");
		viewApplicationsSortOptions.put(7, "a.id");
		viewApplicationsSortOptions.put(8, "a.submissionDate");
		viewApplicationsSortOptions.put(9, "a.completionDate");
		viewApplicationsSortOptions.put(10, "a.investigator.firstName");
		viewApplicationsSortOptions.put(11, "a.csr.firstName");
		viewApplicationsSortOptions.put(12, "a.readByClient");

		// View Invoice Payments Sort Options
		viewInvoicePaymentsSortOptions = new HashMap<Integer, String>();
		viewInvoicePaymentsSortOptions.put(0, "ip.client.name");
		viewInvoicePaymentsSortOptions.put(1, "ip.status");
		viewInvoicePaymentsSortOptions.put(2, "ip.startDate");
		viewInvoicePaymentsSortOptions.put(3, "ip.endDate");
		viewInvoicePaymentsSortOptions.put(4, "ip.amount");
		viewInvoicePaymentsSortOptions.put(5, "ip.paidAmount");

		// View Clients Sort Options
		viewClientsSortOptions = new HashMap<Integer, String>();
		viewClientsSortOptions.put(0, "s.name");
		viewClientsSortOptions.put(1, "s.address");
		viewClientsSortOptions.put(2, "s.city");
		viewClientsSortOptions.put(3, "s.state");
		viewClientsSortOptions.put(4, "s.parent.name");

		// Other Service Options
		otherServiceOptions = new HashMap<String, String>();
		otherServiceOptions.put("Vehicle and Registration Information",
				"Vehicle and Registration Information");
	}

	public TreeMap<String, String> getApplicationSourceOptions() {
		return applicationSourceOptions;
	}

	public void setApplicationSourceOptions(
			TreeMap<String, String> applicantSourceOptions) {
		this.applicationSourceOptions = applicantSourceOptions;
	}

	public final static String DEFAULT_EMAIL = "accounting@tenantevaluation.com";
	
	public final static char TENANTAPPLICATION = 'T';
	public final static char EMPLOYMENTAPPLICATION = 'E';

	public final static char YES = 'Y';
	public final static char NO = 'N';

	public final static char ACTIVE = 'A';
	public final static char INACTIVE = 'I';
	public final static char CHANGEPWD = 'X';
	public final static char LOCKED = 'L';

	public final static char PARTNER = 'P';
	public final static char CLIENT = 'C';
	public final static char SYSTEM = 'S';

	public static final char CURRENT = 'C';
	public static final char PREVIOUS = 'P';
	public static final char PRIOR = 'X';

//	public final static String SYSTEM_ADMIN = "SA";
//	public final static String PARTNER_ADMIN = "PA";
//	public final static String INVESTIGATOR = "IN";
//	public final static String CUSTOMER_SERVICE_REP = "CS";
//	public final static String EXTERNAL_CUSTOMER_SERVICE_REP = "EC";
//	public final static String CLIENT_ADMIN = "CA";
//	public final static String CLIENT_USER = "CU";
//	public final static String PROPERTY_MANAGER = "PM";
//	public final static String COMPLIANCE = "CM";
//	public final static String BOARD_DIRECTOR = "BD";
//	public final static String SUPER_CS = "SC";
//	public final static String ADMINISTRATOR = "JA";
	
	public final static int CREDIT_SCORE_WARNING_THRESHOLD = 620;
	public final static Character TRIVIAL_WORK_TYPES[] = { 'R', 'U', 'H', 'W',
			'D', 'F' };
	public final static Integer DELETE_ROWS_PER_PAGE = 10;
	public final static Integer TOTAL_ROWS_PER_PAGE = 10;

	// // For Employment Type Options
	// public final static String EMPLOYED = "E";
	// public final static String SELFEMPLOYED = "S";
	// public final static String RETIRED = "R";
	// public final static String UNEMPLOYED = "U";
	// // public final static String HOME = "H";
	// public final static String WIFE = "W";
	// public final static String DISABILITY = "D";
	// public final static String FULLTIMESTUDENT = "F";

	// Recommendation value
	public final static String MEET_CRITERIA = "1";
	public final static String NOT_MEET_CRITERIA = "2";
	public final static String REFERRAL = "3";
	public final static String NEED_MORE_DATA = "4";
	public final static String WARNING = "5";
	public final static String REVIEW_FOR_FURTHER_INSTRUCTIONS = "6";

	// For WebService
	public final static String XSL_PATH = "WEB-INF/xsl";

	// Our Application URL
//	public final static String TEA_ADMIN_URL = "tea.url";
//	public final static String TEA_TENANT_URL = "tea-tenant.url";
	public final static String TEA_GUEST_URL = "tea-guest.url";
	public final static String SMARTMOVE_NEW_ACCT_URL = "smartmove_newacct.url";
	
	// External Application URL
	public final static String TEA_LANDLORDAPP_URL = "tea-landlordapp.url";
	// Application Form PDF File
	public final static String APP_FORM = "html/application.pdf";

	// Anonymouse User URL Parameter
	public final static String ANONYMOUS_USER_URL_ID = "anonymousId";

	// // Random String
	// public final static String RANDOM_STRING =
	// "RAa0bcLdUD2eHfJgTP8XhiFj61KOklNm9nBoI5pGqYVrs3CtSuMZvwWx1yE7zQ";

	// Random Number
	public final static int NUMBER_OF_DIGITS = 4;

	public final static String PRIMARY_APPLICANT = "Primary";
	public final static String CO_APPLICANT = "Co-Applicant";
	private TreeMap<String, String> yesNoOptions;
	private TreeMap<Character, String> yNOptions;
	private TreeMap<Boolean, String> trueFalseOptions;
	private HashMap<Boolean, String> trueFalseOptionsForTenant;
	private TreeMap<String, String> petTypeOptions;
	private TreeMap<String, String> userStatusOptions;
	// private TreeMap<String, String> systemUserRoleOptions;
	// private TreeMap<String, String> workerUserRoleOptions;
	private TreeMap<String, String> clientUserRoleOptions;
	private TreeMap<String, String> clientRoleOptions;
	private TreeMap<String, String> coapplicantTypeOptions;
	private TreeMap<String, String> coapplicantTypeOptionsForTenant;
	private TreeMap<String, String> subscriberStatusOptions;
	private TreeMap<String, String> subscriberTypeOptions;
	private LinkedHashMap<String, String> countryListOptions;
	private TreeMap<String, String> usStateListOptions;
	private TreeMap<String, String> rentalPeriodOptions;
	private TreeMap<String, String> reportPeriodOptions;
	private TreeMap<String, String> applicantTypeOptions;
	private TreeMap<String, String> applicationSourceOptions;
	private TreeMap<String, String> applicationTypeOptions;
	private TreeMap<String, String> applicationStatusOptions;
	private LinkedHashMap<Character, String> applicationStatusStringOptions;
	private TreeMap<String, String> applicationStateOptions;
	private LinkedHashMap<Character, String> managementCompanyStatusStringOptions;
	private TreeMap<String, String> residenceTypeOptions;
	private TreeMap<String, String> streetTypes;
	private TreeMap<String, String> streetTypeOptions;
	private TreeMap<String, String> rentalTypeOptions;
	private HashMap<Boolean, String> rentalTypeOptionsForTenant;
	private TreeMap<String, String> occupancyTypeOptions;
	private TreeMap<String, String> salaryTypeOptions;
	private TreeMap<String, String> recommendationTypeOptions;
	private TreeMap<String, String> recommendationValueOptions;
	private TreeMap<String, String> employmentTypeVerifyOptions;
	// private TreeMap<String, String> employmentTypeOptions;
	// private TreeMap<String, String> employmentTypeOptions2;
	// private TreeMap<String, String> employmentTypeOptionsEmail;
	// private TreeMap<String, String> employmentTypeOptionsEmail2;
	private TreeMap<String, String> propertyOwnerOptions;
	private TreeMap<String, String> goodBadOptions;
	// @SuppressWarnings("unused")
	// private TreeMap<String, String> relationshipOptions;
	// private TreeMap<String, String> relationshipOptionsEmailApp;
	private TreeMap<String, String> acctStatusOptions;
	private TreeMap<String, String> bankAboutCustomerOptions;
	private TreeMap<String, String> bankBalanceOptions;
	private TreeMap<String, String> personalQualitiesOptions;
	private LinkedHashMap<String, String> externalAppDateOptions;
	private LinkedHashMap<String, String> monthsOptions;
	private LinkedHashMap<String, String> monthsOptions2;
	private LinkedHashMap<String, String> yearsOptions;
	private LinkedHashMap<String, String> yearsOptions2;
	private Map<Integer, String> viewApplicationsSortOptions;
	private Map<Integer, String> viewInvoicePaymentsSortOptions;
	private Map<Integer, String> viewClientsSortOptions;
	private Map<String, String> otherServiceOptions;

	public final static int MAX_PDF_FILE_SIZE = 20485760;
	public final static Integer VIEW_CLIENTAPPLICATIONS_DEFAULT_SORT_COLUMN = 0;
	public final static Integer VIEW_APPLICATIONS_DEFAULT_SORT_COLUMN = 8;
	public final static Integer VIEW_INVOICE_DEFAULT_SORT_COLUMN = 3;
	public final static Integer VIEW_CLIENT_DEFAULT_SORT_COLUMN = 0;

	public final static Integer PDF_FILE_ATTACHMENT_LENGTH_SIZE = 47;

	public final static String SORT_TYPE_ASC = "Asc";
	public final static String SORT_TYPE_DESC = "Desc";

	public final static String PARAM_DELETE = "_delete";
	public final static String PARAM_REMOVE = "_remove";
	public final static String PARAM_CANCEL = "_cancel";
	public final static String PARAM_CANCELLED = "_cancelled";
	public final static String PARAM_FINISH = "_finish";
	public final static String PARAM_RESET_EMAIL = "_resetemail";
	public final static String PARAM_COMPLETE = "_complete";
	public final static String PARAM_COMPLETED = "_completed";
	public final static String PARAM_PENDING = "_pending";
	public final static String PARAM_RENTERRESPONDED = "_rresp";
	public final static String PARAM_SUBMITTED = "_sub";
	public final static String PARAM_NEW = "_new";
	public final static String PARAM_INPROGRESS= "_inprogress";
	public final static String PARAM_ANONYMOUS = "_anonymous";
	public final static String PARAM_PRINT = "_print";
	public final static String PARAM_SAVE = "_save";
	public final static String PARAM_REASSIGN = "_reassign";
	public final static String PARAM_ARCHIVE = "_archive";
	public final static String PARAM_MARKREAD = "_markread";

	public final static String PARAM_INPROCESS = "_inprocess";

	public final static String PARAM_APPROVAL = "_approval";
	public final static String PARAM_APPROVED = "_approved";
	public final static String PARAM_ALLSTATUS = "_allstatus";
	public final static String PARAM_DECLINE = "_decline";
	public final static String PARAM_DECLINED = "_declined";
	public final static String PARAM_FURTHER_REVIEW = "_review";

	// Transfirst Central variables
	public final static String TC_MERCHANT_ID = "MerchantId";
	public final static String TC_REG_KEY = "RegKey";
	public final static String TC_AMOUNT = "Amount";
	public final static String CARD_NUMBER = "CardNumber";
	public final static String CARD_HOLDER_NAME = "CardHolderName";
	public final static String EXPIRATION = "Expiration";
	public final static String CVV2 = "CVV2";
	public final static String REFID = "RefID";
	public final static String ADDRESS = "Address";
	public final static String ZIPCODE = "ZipCode";
	public final static String TRACK_DATA = "TrackData";
	public final static String USER_ID = "UserId";
	public final static String TAX_INDICATOR = "TaxIndicator";
	public final static String SALES_TAXAMOUNT = "SaleTaxAmount";
	public final static String PONUMBER = "PONumber";
	public final static String MCC = "MCC";
	public final static String MERCH_ZIP = "MerchZip";
	public final static String MERCH_CUSTPNUM = "MerchCustPNum";
	public final static String INSTALLMENT_NUMBER = "InstallmentNum";
	public final static String INSTALLMENT_OF = "InstallmentOf";
	public final static String POSIND = "POSInd";
	public final static String POS_CONDITION_CODE = "POSConditionCode";
	public final static String ECOMIND = "EComInd";
	public final static String AUTHCHARIND = "AuthCharInd";
	public final static String CARD_CERT_DATA = "CardCertData";
	public final static String CAVV_DATA = "CAVVData";
	public final static String PAYMENT_DESC = "PaymentDesc";
	// TC ACH
	public final static String TC_ACH_TRANSROUTE = "TransRoute";
	public final static String TC_ACH_BANKACCOUNTNO = "BankAccountNo";
	public final static String TC_ACH_BANKACCOUNTTYPE = "BankAccountType";
	public final static String TC_ACH_CHECKTYPE = "CheckType";
	public final static String TC_ACH_CHECKWRITERNAME = "Name";
	public final static String TC_ACH_PROCESSDATE = "ProcessDate";

	//TransUnion variables
	public final static String TU_ID = "PropertyId";
	public final static String TU_PROPERTY_IDENTIFIER = "PropertyIdentifier";
	public final static String TU_ACTIVE = "Active";
	public final static String TU_NAME = "Name";
	public final static String TU_UNIT_NUMBER = "UnitNumber";
	public final static String TU_FIRSTNAME = "FirstName";
	public final static String TU_LASTNAME = "LastName";
	public final static String TU_STREET = "Street";
	public final static String TU_CITY = "City";
	public final static String TU_STATE = "State";
	public final static String TU_ZIP = "Zip";
	public final static String TU_PHONE = "Phone";
	public final static String TU_PHONE_EXTENSION = "PhoneExtension";
	public final static String TU_QUESTIONS = "Questions";
	public final static String TU_QUESTION = "Question";
	public final static String TU_QUESTION_ID = "QuestionId";
	public final static String TU_INCLUDE_MEDICAL_COLLECTIONS = "IncludeMedicalCollections";
	public final static String TU_INCLUDE_FORECLOSURES = "IncludeForeclosures";
	public final static String TU_RENT = "Rent";
	public final static String TU_DEPOSIT = "Deposit";
	public final static String TU_PRODUCT_BUNDLE = "ProductBundle";
	public final static String TU_LANDLORD_PAYS = "LandlordPays";
	public final static String TU_LEASE_TERM_IN_MONTHS = "LeaseTermInMonths";
	public final static String TU_APPLICANT_EMAIL = "applicantEmail";
	public final static String TU_COAPPLICANT_EMAIL = "coapplicantEmail";
	public final static String TU_APPLICATION_ID = "ApplicationId";
	
	//RevoPay variables
	public final static String RP_MERCHANT_ID = "MerchantId";
	public final static String RP_AMOUNT = "Amount";
	public final static String RP_APPLICATION_ID = "ApplicationId";
	public final static String NAME = "Name";
	public final static String CARD_TYPE = "CardType";
	public final static String APPLICATION_ID = "appId";
	// RevoPay ACH
	public final static String RP_ACH_TRANSROUTE = "TransRoute";
	public final static String RP_ACH_BANKACCOUNTNO = "BankAccountNo";
	public final static String RP_ACH_BANKACCOUNTTYPE = "BankAccountType";
	public final static String RP_ACH_CHECKTYPE = "CheckType";
	public final static String RP_ACH_PROCESSDATE = "ProcessDate";
		
	// MVR properties
	public final static String MVRMODE = "MVRMODE";
	public final static String MVR_LOGIN = "LogedIn";
	public final static String MVR_ORDERSTATE = "OrderState";
	public final static String MVR_USER = "Cuser";
	public final static String MVR_ACCOUNTNO = "Ccode";
	public final static String MVR_PASSWORD = "Cpass";
	public final static String MVR_PROCESS = "Process";
	public final static String MVR_LICENSE_NO = "Clicense1";
	public final static String MVR_HISTORY = "Chistory1";
	public final static String MVR_FNAME = "Cfname1";
	public final static String MVR_MIDDLE = "Cmiddle1";
	public final static String MVR_LNAME = "CLname1";
	public final static String MVR_SSN = "Csocial1";
	public final static String MVR_DOB = "Cdate1";
	public final static String MVR_IPCHECK = "NoIpCheck";
	public final static String MVR_XMLVERSION = "xmlversion";
	public final static String MVR_OUTPUT_TYPE = "CustomOutPutType";
	public final static String MVR_DPPACODE = "DPPACode";
	public final static String MVR_UPTO7YEAR = "UPTO7YEAR";

	public final static int NULL_ID = -1;

	public final static String DATE_FORMAT = "MM/dd/yyyy";
	public final static String DATE_FORMAT_QB = "MM/dd/yy";
	public final static String DATE_FORMAT_2 = "yyyy-MM-dd";
	public final static String DATE_FORMAT_ISO_DATE_ONLY = "yyyy-MM-dd";
	public final static String DATE_TIME_FORMAT_ISO = "yyyy-MM-dd hh:mm:ss";
	public final static String TIME_FORMAT = "HH:mm:ss.SSS";
	public final static String DATE_FORMAT_MYSQL = "yyyy-MM-dd";

	// public final static String DATE_FORMAT_QB1 = "yyyy/MM/dd";
	public final SimpleDateFormat mmddyyyy = new SimpleDateFormat(DATE_FORMAT,
			new Locale("en", "US"));
	public final SimpleDateFormat yyyymmdd = new SimpleDateFormat(
			DATE_FORMAT_2, new Locale("en", "US"));

	public final static Integer PAGINATION_ROWS_PER_PAGE = 10;
	public final static Integer PAGINATION_DEFAULT_PAGE = 0;
	public final static Integer SSN_DEFAULT_SET = 1;

	public final static Integer TC_PAYMENT_SERVICE_ID = 22;

	public final static String DATE_FORMAT2 = "MMddyy";

	public final static char SubscriberTypeCodeClient = 'C';
	public final static char SubscriberTypeCodePartner = 'P';

	public final String AUTH_MEMBER_ID = "Member ID";
	public final String AUTH_PASSWORD = "Password";
	public final String AUTH_BUREAU = "Bureau";
	public final String AUTH_PRODUCT = "Product";

	public final static String NULL_USER_EMAIL = "no email";
	
	public final static String API_USER_EMAIL = "smartmove";

	// Transfirst gateway Input string values
	private static Globals instance = new Globals();
}
