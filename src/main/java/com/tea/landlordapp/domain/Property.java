package com.tea.landlordapp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

//import com.tea.repository.ConfigurationDao;
import com.tea.landlordapp.enums.UserRole;

@SuppressWarnings("serial")
@Entity
@Table(name = "property")
public class Property extends AuditableEntity {

//	@Autowired
//	ConfigurationDao configurationDao;

	private static final int BIGIDDELTA = 5000;


	private String name;
	private String street;
	private String propertyIdentifier;
	private String organizationName;
	private String apartmentNo;
	private String city;
	private String state;
	private String zipcode;
	private String county;
	private String country;
	private String rentalPeriod;
	private char statusCode = 'P';
	private char classification = 'C';
	private String propertyNotice;

	private Double rentalAmount;
	private Double ir;	
	private Date availableFrom;


	private boolean active = true;
	private boolean futureUse = false;
	private boolean prefillAddress = false;
	private boolean declineForOpenBkr = false;
	private boolean incForeclosures = false;
	private boolean incMedCollections = false;
	private boolean fcrAccepted = false;
	private boolean requireCompleteApplication = false;
	private boolean addCreditcardSurcharge = false;
	private boolean postQboTxnSubscriber = false;
	private boolean ownerSigningRequired = false;
	private boolean priority = false;

	private int propertyExtId = 0;
	private int organizationId;
	private int userId;
	private int minimumLease = 6;
	private int maximumPetWeight = 0;
	private int maximumPetWeightPurchase = 0;
	private int openBkrWindow = 0;
	
	private String applicationChecklist;

	private int purchasePackageId = 0;
	private int rentalPackageId = 0;
	private int guestPackageId = 0;
	private int purchaseSpousePackageId = 0;
	private int rentalSpousePackageId = 0;

	private double purchaseDeposit = 0;
	private double purchaseOccupancyFee = 0;
	private double purchasePetDeposit = 0;

	private double rentalDeposit = 0;
	private double rentalOccupancyFee = 0;
	private double rentalPetDeposit = 0;

	private double guestDeposit = 0;
	private double guestOccupancyFee = 0;
	private double guestPetDeposit = 0;

	private double rushFee = 0;
	private double teRushFee = 0;

	private boolean allowPurchase = false;
	private boolean allowLease = false;
	private boolean allowGuest = false;

	private boolean useSigningService = false;

//	private String qboId;
//	private String qboSyncToken;
	private String termsItemId;

	private boolean notify = false;
	private String notifyEmail;
//	private String accountingEmail;

	private boolean sendCompletionNotice = false;
	private String noticeUrl;
	
	private String phone;
	private String extension;
	private String paymentProcessor;

	private String depositPaymentProcessor = "RevoPay";
	private String signingProcessor;
	private Boolean externalDeposit = false;
	private Boolean variableSecurityDeposit = false;
//	private String cpId;
	private Boolean lockedPaymentProcessor = false;

	private Set<User> authorizedManagers;
	private Set<User> propertyManagers;
	private Set<User> boardDirectors;
	private java.util.List<Integer> authorizedManagerIds;
	private java.util.List<Integer> propertyManagerIds;
	private java.util.List<Integer> boardDirectorIds;
	private User applicationApprovalUser;
	private int applicationApprovalUserId;
	
//	private java.util.List<DepositTarget> depositTargets;
//
//	private List<AuthorizedIP> authorizedIPs;

	// field for filename in external storage
	private String communityDocument;
	private String leaseTemplatePdfName;
	private String purchaseTemplatePdfName;
	private String guestTemplatePdfName;
	private String authTemplatePdfName;	
	private String addendumTemplatePdfName;
	private boolean termsFileExists = false;
	private boolean leaseTemplateFileExists = false;
	private boolean purchaseTemplateFileExists = false;
	private boolean guestTemplateFileExists = false;
	private boolean addendumTemplateFileExists = false;
	private boolean authTemplateFileExists = false;
	
	// third party signature emails
	private String signerEmail;
	private String signerName;
	
	private String landlordId;
//	private String llcId;
	
//	private QboCustomerMap qboCustomerMap;
//	private Long qboCustomerMapId;

//	@OneToMany(mappedBy = "property", cascade = { CascadeType.ALL })
//	public List<AuthorizedIP> getAuthorizedIPs() {
//		return authorizedIPs;
//	}
//
//	public void setAuthorizedIPs(List<AuthorizedIP> authorizedIPs) {
//		this.authorizedIPs = authorizedIPs;
//	}   
//   
//	private java.util.List<PropertyCollectionItem> propertyCollectionItems = LazyList.decorate(
//			new ArrayList<PropertyCollectionItem>(),
//			FactoryUtils.instantiateFactory(PropertyCollectionItem.class));

	
// 
//   @Column(name = "property_key")
//   public String getPropertyKey() {
//      return propertyKey;
//   }
//
//   public void setPropertyKey(String propertyKey) {
//      this.propertyKey = propertyKey;
//   }

   @Column(name = "name")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

	@Column(name = "property_ext_id")
	public int getPropertyExtId() {
		return propertyExtId;
	}

	public void setPropertyExtId(int propertyExtId) {
		this.propertyExtId = propertyExtId;
	}

	@Column(name = "organization_id")
	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	
	@Column(name = "open_bkr_window")
	public int getOpenBkrWindow() {
		return openBkrWindow;
	}

	public void setOpenBkrWindow(int openBkrWindow) {
		this.openBkrWindow = openBkrWindow;
	}
	
	@Column(name = "property_identifier")
	public String getPropertyIdentifier() {
		return propertyIdentifier;
	}

	public void setPropertyIdentifier(String propertyIdentifier) {
		this.propertyIdentifier = propertyIdentifier;
	}
	
	@Column(name = "organization_name")
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	
//   @Column(name = "house_no")
//   public String getHouseNo() {
//      return houseNo;
//   }
//
//   public void setHouseNo(String houseNo) {
//      this.houseNo = houseNo;
//   }

   @Column(name = "street")
   public String getStreet() {
      return street;
   }

   public void setStreet(String street) {
      this.street = street;
   }

//   @Column(name = "street_type")
//   public String getStreetType() {
//      return streetType;
//   }
//
//   public void setStreetType(String streetType) {
//      this.streetType = streetType;
//   }

   @Column(name = "unit_no")
   public String getApartmentNo() {
      return apartmentNo;
   }

   public void setApartmentNo(String apartmentNo) {
      this.apartmentNo = apartmentNo;
   }

   @Column(name = "city")
   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   @Column(name = "state")
   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   @Column(name = "zipcode")
   public String getZipcode() {
      return zipcode;
   }

   public void setZipcode(String zipcode) {
      this.zipcode = zipcode;
   }

   @Column(name = "county")
   public String getCounty() {
      return county;
   }

   public void setCounty(String county) {
      this.county = county;
   }

   @Column(name = "country")
   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   @Column(name = "rental_amount")
   public Double getRentalAmount() {
      return rentalAmount;
   }

   public void setRentalAmount(Double rentalAmount) {
      this.rentalAmount = rentalAmount;
   }

   @Column(name = "rental_period")
   public String getRentalPeriod() {
      return rentalPeriod;
   }

   public void setRentalPeriod(String rentalPeriod) {
      this.rentalPeriod = rentalPeriod;
   }

   @Column(name = "ir")
   public Double getIr() {
      return ir;
   }

   public void setIr(Double rentalAmount) {
      this.ir = ir;
   }
   @Transient
   public String getAddressHtml() {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%s<br>",  getStreet()));
      sb.append(String.format("%s, %s %s", getCity(), getState(), getZipcode()));

      return sb.toString();
   }

   @Transient
   public String getAddressLine1() {
      return String.format("%s", getStreet());
   }

   @Transient
   public String getCityLine() {
      return String.format("%s, %s %s", getCity(), getState(), getZipcode());
   }

//   @Column(name = "client_code")
//   public String getClientCode() {
//      return clientCode;
//   }
//
//   public void setClientCode(String clientCode) {
//      this.clientCode = clientCode;
//   }
//   
	@Column(name = "classification")
	private char getClassification() {
		return classification;
	}

	private void setClassification(char classification) {
		this.classification = classification;
	}
	
//   @Column(name = "application_fee_individual")
//   public Double getApplicationFeeIndividual() {
//      return applicationFeeIndividual;
//   }
//
//   public void setApplicationFeeIndividual(Double applicationFeeIndividual) {
//      this.applicationFeeIndividual = applicationFeeIndividual;
//   }

//   @Column(name = "application_fee_couple")
//   public Double getApplicationFeeCouple() {
//      return applicationFeeCouple;
//   }
//
//   public void setApplicationFeeCouple(Double applicationFeeCouple) {
//      this.applicationFeeCouple = applicationFeeCouple;
//   }

//   @Transient
//   public int getBigId() {
//      if (this.getId() == null) return 0;
//      return this.getId() + BIGIDDELTA;
//   }
//
//   public void setBigId(int dummy) {
//      // do nothing
//   }
//
//   @Transient
//   public int getBigId(int base) {
//      return base + BIGIDDELTA;
//   }
//
//   public static int bigId2Id(int bigId) {
//      return bigId - BIGIDDELTA;
//   }

   @Transient
   public String getAddressLine() {
      return String.format(" %s %s, %s, %s %s",  street, apartmentNo, city, state, zipcode);
   }

//   @Column(name = "require_complete_application")
//   public boolean isRequireCompleteApplication() {
//      return requireCompleteApplication;
//   }
//
//   public void setRequireCompleteApplication(boolean requireCompleteApplication) {
//      this.requireCompleteApplication = requireCompleteApplication;
//   }

   @Column(name = "active")
   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }
   
   @Column(name = "future_use")
   public boolean isFutureUse() {
      return futureUse;
   }

   public void setFutureUse(boolean futureUse) {
      this.futureUse = futureUse;
   }  
   
   @Column(name = "decline_for_open_bkr")
   public boolean isDeclineForOpenBkr() {
      return declineForOpenBkr;
   }

   public void setDeclineForOpenBkr(boolean declineForOpenBkr) {
      this.declineForOpenBkr = declineForOpenBkr;
   }
   @Column(name = "inc_foreclosures")
   public boolean isIncForeclosures() {
      return incForeclosures;
   }

   public void setIncForeclosures(boolean incForeclosures) {
      this.incForeclosures = incForeclosures;
   }
   @Column(name = "inc_med_collections")
   public boolean isIncMedCollections() {
      return incMedCollections;
   }

   public void setIncMedCollections (boolean incMedCollections) {
      this.incMedCollections = incMedCollections;
   }

   @Column(name = "is_fcra_accepted")
   public boolean isFcrAccepted() {
      return fcrAccepted;
   }

   public void setFcrAccepted(boolean fcrAccepted) {
      this.fcrAccepted = fcrAccepted;
   }
   
//   @Column(name = "minimum_lease")
//   public int getMinimumLease() {
//      return minimumLease;
//   }
//
//   public void setMinimumLease(int minimumLease) {
//      this.minimumLease = minimumLease;
//   }
//
//   @Column(name = "maximum_pet_weight")
//   public int getMaximumPetWeight() {
//      return maximumPetWeight;
//   }
//
//   public void setMaximumPetWeight(int maximumPetWeight) {
//      this.maximumPetWeight = maximumPetWeight;
//   }
//
//   @Column(name = "application_checklist")
//   public String getApplicationChecklist() {
//      return applicationChecklist;
//   }
//
//   public void setApplicationChecklist(String applicationChecklist) {
//      this.applicationChecklist = applicationChecklist;
//   }
//
//   @Transient
//   public String getApplicationChecklistHtml() {
//      return StringEscapeUtils.escapeJavaScript(getApplicationChecklist());
//   }
//
//   @Column(name = "application_fee_roommate")
//   public Double getApplicationFeeRoommate() {
//      return applicationFeeRoommate;
//   }
//
//   public void setApplicationFeeRoommate(Double applicationFeeRoommate) {
//      this.applicationFeeRoommate = applicationFeeRoommate;
//   }
//
//   @ManyToOne
//   @JoinColumn(name = "purchase_package_id")
//   public Packge getPurchasePackage() {
//      return purchasePackage;
//   }
//
//   public void setPurchasePackage(Packge purchasePackage) {
//      this.purchasePackage = purchasePackage;
//   }
//
//   @ManyToOne
//   @JoinColumn(name = "purchase_spouse_pkg_id")
//   public Packge getPurchaseSpousePackage() {
//      return purchaseSpousePackage;
//   }
//
//   public void setPurchaseSpousePackage(Packge pkg) {
//      this.purchaseSpousePackage = pkg;
//   }
//
//   @ManyToOne
//   @JoinColumn(name = "rental_package_id")
//   public Packge getRentalPackage() {
//      return rentalPackage;
//   }
//
//   public void setRentalPackage(Packge rentalPackage) {
//      this.rentalPackage = rentalPackage;
//   }
//
//   @ManyToOne
//   @JoinColumn(name = "rental_spouse_pkg_id")
//   public Packge getRentalSpousePackage() {
//      return rentalSpousePackage;
//   }
//
//   public void setRentalSpousePackage(Packge pkg) {
//      this.rentalSpousePackage = pkg;
//   }

//   @Column(name = "purchase_deposit")
//   public double getPurchaseDeposit() {
//      return purchaseDeposit;
//   }
//
//   public void setPurchaseDeposit(double purchaseDeposit) {
//      this.purchaseDeposit = purchaseDeposit;
//   }
//
//   @Column(name = "purchase_occupancy_fee")
//   public double getPurchaseOccupancyFee() {
//      return purchaseOccupancyFee;
//   }
//
//   public void setPurchaseOccupancyFee(double purchaseOccupancyFee) {
//      this.purchaseOccupancyFee = purchaseOccupancyFee;
//   }

//   @Column(name = "purchase_pet_deposit")
//   public double getPurchasePetDeposit() {
//      return purchasePetDeposit;
//   }
//
//   public void setPurchasePetDeposit(double purchasePetDeposit) {
//      this.purchasePetDeposit = purchasePetDeposit;
//   }

   @Column(name = "rental_deposit")
   public double getRentalDeposit() {
      return rentalDeposit;
   }

   public void setRentalDeposit(double rentalDeposit) {
      this.rentalDeposit = rentalDeposit;
   }

//   @Column(name = "rental_occupancy_fee")
//   public double getRentalOccupancyFee() {
//      return rentalOccupancyFee;
//   }
//
//   public void setRentalOccupancyFee(double rentalOccupancyFee) {
//      this.rentalOccupancyFee = rentalOccupancyFee;
//   }
//
//   @Column(name = "rental_pet_deposit")
//   public double getRentalPetDeposit() {
//      return rentalPetDeposit;
//   }
//
//   public void setRentalPetDeposit(double rentalPetDeposit) {
//      this.rentalPetDeposit = rentalPetDeposit;
//   }
//
//   @Column(name = "rush_fee")
//   public double getRushFee() {
//      return rushFee;
//   }
//
//   public void setRushFee(double rushFee) {
//      this.rushFee = rushFee;
//   }
//
//   @Column(name = "te_rush_fee")
//   public double getTeRushFee() {
//      return teRushFee;
//   }
//
//   public void setTeRushFee(double teRushFee) {
//      this.teRushFee = teRushFee;
//   }
//
//   @Transient
//   public int getPurchasePackageId() {
//      return purchasePackageId;
//   }
//
//   public void setPurchasePackageId(int purchasePackageId) {
//      this.purchasePackageId = purchasePackageId;
//   }
//
//   @Transient
//   public int getRentalPackageId() {
//      return rentalPackageId;
//   }
//
//   public void setRentalPackageId(int rentalPackageId) {
//      this.rentalPackageId = rentalPackageId;
//   }
//
//   @Transient
//   public int getPurchaseSpousePackageId() {
//      return purchaseSpousePackageId;
//   }
//
//   public void setPurchaseSpousePackageId(int purchasePackageId) {
//      this.purchaseSpousePackageId = purchasePackageId;
//   }
//
//   @Transient
//   public int getRentalSpousePackageId() {
//      return rentalSpousePackageId;
//   }
//
//   public void setRentalSpousePackageId(int rentalPackageId) {
//      this.rentalSpousePackageId = rentalPackageId;
//   }
//
//   @Column(name = "allow_purchase_option")
//   public boolean isAllowPurchase() {
//      return allowPurchase;
//   }
//
//   public void setAllowPurchase(boolean allowPurchase) {
//      this.allowPurchase = allowPurchase;
//   }
//
//   @Column(name = "use_signing_service")
//   public boolean isUseSigningService() {
//      return useSigningService;
//   }
//
//   public void setUseSigningService(boolean useSigningService) {
//      this.useSigningService = useSigningService;
//   }
//
//	@Column(name = "allow_guest_option")
//	public boolean isAllowGuest() {
//		return allowGuest;
//	}
//
//	public void setAllowGuest(boolean allowGuest) {
//		this.allowGuest = allowGuest;
//	}
//
//	@Column(name = "allow_lease_option")
//	public boolean isAllowLease() {
//		return allowLease;
//	}
//
//	public void setAllowLease(boolean allowLease) {
//		this.allowLease = allowLease;
//	}
//
//	@Column(name = "allow_pets_purchase")
//	public boolean isAllowPetsPurchase() {
//		return allowPetsPurchase;
//	}
//
//	public void setAllowPetsPurchase(boolean allowPetsPurchase) {
//		this.allowPetsPurchase = allowPetsPurchase;
//	}
//
//	@Column(name = "maximum_pet_weight_purchase")
//	public int getMaximumPetWeightPurchase() {
//		return maximumPetWeightPurchase;
//	}
//
//	public void setMaximumPetWeightPurchase(int maximumPetWeightPurchase) {
//		this.maximumPetWeightPurchase = maximumPetWeightPurchase;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "guest_package_id")
//	public Packge getGuestPackage() {
//		return guestPackage;
//	}
//
//	public void setGuestPackage(Packge guestPackage) {
//		this.guestPackage = guestPackage;
//	}
//
//	@Transient
//	public int getGuestPackageId() {
//		return guestPackageId;
//	}
//
//	public void setGuestPackageId(int guestPackageId) {
//		this.guestPackageId = guestPackageId;
//	}
//
//	@Column(name = "guest_deposit")
//	public double getGuestDeposit() {
//		return guestDeposit;
//	}
//
//	public void setGuestDeposit(double guestDeposit) {
//		this.guestDeposit = guestDeposit;
//	}
//
//	@Column(name = "guest_occupancy_fee")
//	public double getGuestOccupancyFee() {
//		return guestOccupancyFee;
//	}
//
//	public void setGuestOccupancyFee(double guestOccupancyFee) {
//		this.guestOccupancyFee = guestOccupancyFee;
//	}
//
//	@Column(name = "guest_pet_deposit")
//	public double getGuestPetDeposit() {
//		return guestPetDeposit;
//	}
//
//	public void setGuestPetDeposit(double guestPetDeposit) {
//		this.guestPetDeposit = guestPetDeposit;
//	}

//	@Column(name = "application_fee_guest")
//	public Double getApplicationFeeGuest() {
//		return applicationFeeGuest;
//	}
//
//	public void setApplicationFeeGuest(Double applicationFeeGuest) {
//		this.applicationFeeGuest = applicationFeeGuest;
//	}

//	@Column(name = "qbo_id")
//	public String getQboId() {
//		return qboId;
//	}
//
//	public void setQboId(String qboId) {
//		this.qboId = qboId;
//	}
//
//	@Column(name = "qbo_sync_token")
//	public String getQboSyncToken() {
//		return qboSyncToken;
//	}
//
//	public void setQboSyncToken(String qboSyncToken) {
//		this.qboSyncToken = qboSyncToken;
//	}

//	@Transient
//	public double getTotalTenantApplicantCharge(int occupants, boolean married,
//			boolean pets) {
//		double res = rentalDeposit + rentalOccupancyFee;
//		if (pets)
//			res += rentalPetDeposit;
//		if (occupants > 1) {
//			if (married) {
//				res += applicationFeeCouple;
//			} else {
//				res += (applicationFeeIndividual * occupants);
//			}
//		}
//		return res;
//	}

//	@Column(name = "signing_service_fee")
//	public double getSigningServiceFee() {
//		return signingServiceFee;
//	}
//
//	public void setSigningServiceFee(double signingServiceFee) {
//		this.signingServiceFee = signingServiceFee;
//	}
//
//	@Column(name = "add_creditcard_surcharge")
//	public boolean isAddCreditcardSurcharge() {
//		return addCreditcardSurcharge;
//	}
//
//	public void setAddCreditcardSurcharge(boolean addCreditcardSurcharge) {
//		this.addCreditcardSurcharge = addCreditcardSurcharge;
//	}
//
//	@Column(name = "notify_submission")
//	public boolean isNotify() {
//		return notify;
//	}
//
//	public void setNotify(boolean notify) {
//		this.notify = notify;
//	}

//	@Column(name = "notify_email")
//	public String getNotifyEmail() {
//		return notifyEmail;
//	}
//
//	public void setNotifyEmail(String notifyEmail) {
//		this.notifyEmail = notifyEmail;
//	}
//
//	@Transient
//	public String[] getNotificationAddresses() {
//		if (StringUtils.isBlank(this.notifyEmail)
//				|| StringUtils.isBlank(this.notifyEmail.trim()))
//			return new String[] {};
//		return this.notifyEmail.split("[\\s,;:]+");
//	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "extension")
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

//	@Transient
//	public String[] getAccountingAddresses() {
//		if (StringUtils.isBlank(this.accountingEmail)
//				|| StringUtils.isBlank(this.accountingEmail.trim()))
//			return new String[] {};
//		return this.accountingEmail.split("[\\s,;:]+");
//	}

//	@Column(name = "post_qbo_txn_subscriber")
//	public boolean isPostQboTxnSubscriber() {
//		return postQboTxnSubscriber;
//	}
//
//	public void setPostQboTxnSubscriber(boolean postQboTxnSubscriber) {
//		this.postQboTxnSubscriber = postQboTxnSubscriber;
//	}

//	@Column(name = "notice_url")
//	public String getNoticeUrl() {
//		return noticeUrl;
//	}
//
//	public void setNoticeUrl(String noticeUrl) {
//		this.noticeUrl = noticeUrl;
//	}
//
//	@Column(name = "send_completion_notice")
//	public boolean isSendCompletionNotice() {
//		return sendCompletionNotice;
//	}
//
//	public void setSendCompletionNotice(Boolean sendCompletionNotice) {
//		this.sendCompletionNotice = sendCompletionNotice;
//	}
//
//	@Column(name = "terms_qbo_id")
//	public String getTermsItemId() {
//		return termsItemId;
//	}
//
//	public void setTermsItemId(String termsItemId) {
//		this.termsItemId = termsItemId;
//	}
//
//
//	@ManyToMany(fetch = FetchType.EAGER)
//	@Fetch(FetchMode.SUBSELECT)
//	@JoinTable(name = "property_authorization", joinColumns = @JoinColumn(name = "property_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//	public Set<User> getAuthorizedManagers() {
//		return authorizedManagers;
//	}
//
//	public void setAuthorizedManagers(Set<User> authorizedManagers) {
//		this.authorizedManagers = authorizedManagers;
//		authorizedManagerIds = new ArrayList<Integer>();
//		if (authorizedManagers != null) {
//			for (User u : authorizedManagers) {
//				authorizedManagerIds.add(u.getId());
//			}
//		}
//	}
//
//	@Transient
//	public Set <User> getPropertyManagers() {
//		propertyManagers = new HashSet <User>();
//		if (authorizedManagers != null){
//			for (User u : authorizedManagers){
//					if ( StringUtils.equals(u.getRole().getRole(), UserRole.PropertyManager.getCode() )){
//						propertyManagers.add(u);
//					}
//			}
//		}
//		return propertyManagers;
//	}
//
//	public void setPropertyManagers( Set<User> propertyManagers) {
//		this.propertyManagers = propertyManagers;
//		propertyManagerIds = new ArrayList<Integer>();
//		if (propertyManagers != null) {
//			for (User u : propertyManagers) {
//				propertyManagerIds.add(u.getId());
//			}
//		}
//		Set<User> authorizedMgrs = new HashSet<User>();
//		for (User u : propertyManagers){
//			authorizedMgrs.add(u);
//		}
//		for (User u : this.getBoardDirectors()){
//			authorizedMgrs.add(u);
//		}
//		this.setAuthorizedManagers(authorizedMgrs);
//
//	}
//	
//	@Transient
//	public Set <User> getBoardDirectors() {
//		boardDirectors = new HashSet<User>();
//		if (authorizedManagers != null){
//			for (User u : authorizedManagers){
//					if ( StringUtils.equals(u.getRole().getRole(), UserRole.BoardDirector.getCode()) ){
//						boardDirectors.add(u);
//					}
//			}
//		}
//		return boardDirectors;
//	}
//
//	public void setBoardDirectors( Set<User> boardDirectors) {
//		this.boardDirectors = boardDirectors;
//		boardDirectorIds = new ArrayList<Integer>();
//		if (boardDirectors != null) {
//			for (User u : boardDirectors) {
//				boardDirectorIds.add(u.getId());
//			}
//		}
//		Set<User> authorizedMgrs = new HashSet<User>();
//		for (User u : boardDirectors){
//			authorizedMgrs.add(u);
//		}
//		for (User u : this.getPropertyManagers()){
//			authorizedMgrs.add(u);
//		}
//		this.setAuthorizedManagers(authorizedMgrs);
//	}
//		
//		
//	@Transient
//	public java.util.List<Integer> getAuthorizedManagerIds() {
//		return authorizedManagerIds;
//	}
//
//	public void setAuthorizedManagerIds(java.util.List<Integer> authorizedManagerIds) {
//		this.authorizedManagerIds = authorizedManagerIds;
//	}
//	
//	@Transient
//	public java.util.List<Integer> getPropertyManagerIds() {
//		return propertyManagerIds;
//	}
//
//	public void setPropertyManagerIds(java.util.List<Integer> propertyManagerIds) {
//		this.propertyManagerIds = propertyManagerIds;
//	}
//
//	@Transient
//	public java.util.List<Integer> getBoardDirectorIds() {
//		return boardDirectorIds;
//	}
//
//	public void setBoardDirectorIds(java.util.List<Integer> boardDirectorIds) {
//		this.boardDirectorIds = boardDirectorIds;
//	}
//	
//	@Column(name = "community_document")
//	public String getCommunityDocument() {
//		return communityDocument;
//	}
//
//	public void setCommunityDocument(String communityDocument) {
//		this.communityDocument = communityDocument;
//	}
//
//	@Column(name = "lease_template_file_name")
//	public String getLeaseTemplatePdfName() {
//		return leaseTemplatePdfName;
//	}
//
//	public void setLeaseTemplatePdfName(String leaseTemplatePdfName) {
//		this.leaseTemplatePdfName = leaseTemplatePdfName;
//	}
//
//	@Column(name = "purchase_template_file_name")
//	public String getPurchaseTemplatePdfName() {
//		return purchaseTemplatePdfName;
//	}
//
//	public void setPurchaseTemplatePdfName(String purchaseTemplatePdfName) {
//		this.purchaseTemplatePdfName = purchaseTemplatePdfName;
//	}
//
//	@Column(name = "guest_template_file_name")
//	public String getGuestTemplatePdfName() {
//		return guestTemplatePdfName;
//	}
//
//	public void setGuestTemplatePdfName(String guestTemplatePdfName) {
//		this.guestTemplatePdfName = guestTemplatePdfName;
//	}
//
//	@Column(name = "auth_template_file_name")
//	public String getAuthTemplatePdfName() {
//		return authTemplatePdfName;
//	}
//
//	public void setAuthTemplatePdfName(String authTemplatePdfName) {
//		this.authTemplatePdfName = authTemplatePdfName;
//	}
//	
//	@Transient
//	public boolean isTermsFileExists() {
//		return termsFileExists;
//	}
//
//	public void setTermsFileExists(boolean termsFileExists) {
//		this.termsFileExists = termsFileExists;
//	}
//
//	@Transient
//	public boolean isLeaseTemplateFileExists() {
//		return leaseTemplateFileExists;
//	}
//
//	public void setLeaseTemplateFileExists(boolean leaseTemplateFileExists) {
//		this.leaseTemplateFileExists = leaseTemplateFileExists;
//	}
//
//	@Transient
//	public boolean isPurchaseTemplateFileExists() {
//		return purchaseTemplateFileExists;
//	}
//
//	public void setPurchaseTemplateFileExists(boolean purchaseTemplateFileExists) {
//		this.purchaseTemplateFileExists = purchaseTemplateFileExists;
//	}
//
//	@Transient
//	public boolean isGuestTemplateFileExists() {
//		return guestTemplateFileExists;
//	}
//
//	public void setGuestTemplateFileExists(boolean guestTemplateFileExists) {
//		this.guestTemplateFileExists = guestTemplateFileExists;
//	}
//
//	@Transient
//	public boolean isAuthTemplateFileExists() {
//		return authTemplateFileExists;
//	}
//
//	public void setAuthTemplateFileExists(boolean authTemplateFileExists) {
//		this.authTemplateFileExists = authTemplateFileExists;
//	}
//	
//	@Column(name = "signer_email")
//	public String getSignerEmail() {
//		return signerEmail;
//	}
//
//	public void setSignerEmail(String signingEmail) {
//		this.signerEmail = signingEmail;
//	}
//
//	@Column(name = "signer_name")
//	public String getSignerName() {
//		return signerName;
//	}
//
//	public void setSignerName(String signerName) {
//		this.signerName = signerName;
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "application_approval_user")
//		public User getApplicationApprovalUser() {
//	   return applicationApprovalUser;
//	}
//
//	public void setApplicationApprovalUser(User applicationApprovalUser) {
//	   this.applicationApprovalUser = applicationApprovalUser;
//	   if (applicationApprovalUser != null) {
//		   setApplicationApprovalUserId(applicationApprovalUser.getId());
//	   }
// 	}	
//	
//	@Transient
//	public int getApplicationApprovalUserId() {
//		return applicationApprovalUserId;
//	}
//
//	public void setApplicationApprovalUserId(int applicationApprovalUserId) {
//		this.applicationApprovalUserId = applicationApprovalUserId;
//	}
//
//	@Column(name = "payment_processor")
//	public String getPaymentProcessor() {
//		return paymentProcessor;
//	}
//
//	public void setPaymentProcessor(String paymentProcessor) {
//		this.paymentProcessor = paymentProcessor;
//	}
//	
//
//
//	@Column(name = "deposit_payment_processor")
//	public String getDepositPaymentProcessor() {
//		return depositPaymentProcessor;
//	}
//
//	public void setDepositPaymentProcessor(String depositPaymentProcessor) {
//		this.depositPaymentProcessor = depositPaymentProcessor;
//	}
//	
//	
//	@Column(name = "signing_processor")
//	public String getSigningProcessor() {
//		return signingProcessor;
//	}
//
//	public void setSigningProcessor(String signingProcessor) {
//		this.signingProcessor = signingProcessor;
//	}
//	
//
//	
//	@Column(name = "addendum_file_name")
//	public String getAddendumTemplatePdfName() {
//		return addendumTemplatePdfName;
//	}
//
//	public void setAddendumTemplatePdfName(String addendumTemplatePdfName) {
//		this.addendumTemplatePdfName = addendumTemplatePdfName;
//	}
//
//	@Transient
//	public boolean isAddendumTemplateFileExists() {
//		return addendumTemplateFileExists;
//	}
//
//	public void setAddendumTemplateFileExists(boolean addendumTemplateFileExists) {
//		this.addendumTemplateFileExists = addendumTemplateFileExists;
//	}
//
//	@Column(name = "owner_signature_required")
//	public boolean isOwnerSigningRequired() {
//		return ownerSigningRequired;
//	}
//
//	public void setOwnerSigningRequired(boolean ownerSigningRequired) {
//		this.ownerSigningRequired = ownerSigningRequired;
//	}
//
//	@Column(name = "external_deposit")
//	public Boolean getExternalDeposit() {
//		if(externalDeposit == null) return false;
//		return externalDeposit;
//	}
//
//	public void setExternalDeposit(Boolean externalDeposit) {
//		if(externalDeposit == null) externalDeposit = false;
//		this.externalDeposit = externalDeposit;
//	}

//	@Column(name = "cp_id")
//	public String getCpId() {
//		return cpId;
//	}
//
//	public void setCpId(String cpId) {
//		this.cpId = cpId;
//	}

//	@Column(name = "lock_pmt_processor")
//	public Boolean getLockedPaymentProcessor() {
//		return lockedPaymentProcessor;
//	}
//
//	public void setLockedPaymentProcessor(Boolean lockedPaymentProcessor) {
//		this.lockedPaymentProcessor = lockedPaymentProcessor;
//	}
//	
//	@Transient
//	public boolean isPassthroughAuthorized(){
////		return (getExternalDeposit() && StringUtils.isNotBlank(getCpId()) && getLockedPaymentProcessor());
//		return (getExternalDeposit() && getLockedPaymentProcessor());
//	}
//	
//	@Column(name = "variable_security_deposit")
//	public boolean getVariableSecurityDeposit() {
//		return variableSecurityDeposit;
//	}
//
//	public void setVariableSecurityDeposit(boolean variableSecurityDeposit) {
//		this.variableSecurityDeposit = variableSecurityDeposit;
//	}

	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


//	@Column(name = "priority")
//	public boolean isPriority() {
//		return priority;
//	}
//
//	public void setPriority(boolean priority) {
//		this.priority = priority;
//	}
//
//	@Column(name = "status_code")
//	private char getStatusCode() {
//		return statusCode;
//	}
//
//	private void setStatusCode(char status) {
//		this.statusCode = status;
//	}
	
//	@Transient
//	public CustomerStatus getStatus(){
//		return CustomerStatus.getEnum(getStatusCode());
//	}
//	
//	public void setStatusValue(CustomerStatus sts){
//		setStatusCode(sts.getCode());
//	}
//
//	@ManyToOne
//	@JoinColumn(name = "qbo_customer_map_id")
//	public QboCustomerMap getQboCustomerMap() {
//		return qboCustomerMap;
//	}
//
//	public void setQboCustomerMap(QboCustomerMap qboCustomerMap) {
//		this.qboCustomerMap = qboCustomerMap;
//	}

//	@Transient
//	public Long getQboCustomerMapId() {
//		return qboCustomerMapId;
//	}
//
//	public void setQboCustomerMapId(Long qboCustomerMapId) {
//		this.qboCustomerMapId = qboCustomerMapId;
//	}
//	
//	@Column(name = "property_notice")
//	public String getPropertyNotice() {
//		return propertyNotice;
//	}

//	public void setPropertyNotice(String propertyNotice) {
//		this.propertyNotice = propertyNotice;
//	}
}