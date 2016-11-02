package com.tea.landlordapp.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

//import com.tea.enums.ApplicationStatus;


@SuppressWarnings("unchecked")
@Entity
@Table(name = "application")
public class Application extends AuditableEntity  {

	private static final long serialVersionUID = -7074309962261297086L;

	private Character coApplicantAvailable = 'N';

	private Character creditRecommendation;
	
	private Character selectedBundle;

	private Character hasWarnings;

	private Double rentalAmount;

	private Double rentalDeposit = 0.0;

    private int leaseTermMonths = 12;
    
    private int applicationExtId;

	private boolean landlordPays = true;

	private Date submissionDate;

	private Property property;
	
	private String creditPolicy;
	
	private String status;

	private String apartmentNo;

	private AnonymousUser anonymousUser;

	private java.util.List<Applicant> applicants = LazyList.decorate(
			new ArrayList<Applicant>(),
			FactoryUtils.instantiateFactory(Applicant.class));



	@ManyToOne
	@JoinColumn(name = "property_id")
	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}


//	@Column(name = "rental_address")
//	public String getRentalAddress() {
//		return rentalAddress;
//	}
//
//	public void setRentalAddress(String address) {
//		this.rentalAddress = address;
//	}

	@Column(name = "unit_no")
	public String getApartmentNo() {
		return apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}

//	@Column(name = "rental_city")
//	public String getRentalCity() {
//		return rentalCity;
//	}
//
//	public void setRentalCity(String city) {
//		this.rentalCity = city;
//	}
//
//	@Column(name = "rental_state")
//	public String getRentalState() {
//		return rentalState;
//	}
//
//	public void setRentalState(String state) {
//		this.rentalState = state;
//	}
//
//	@Column(name = "rental_zipcode")
//	public String getRentalZipcode() {
//		return rentalZipcode;
//	}
//
//	public void setRentalZipcode(String rentalZipcode) {
//		this.rentalZipcode = rentalZipcode;
//	}
//
//	@Column(name = "rental_country")
//	public String getRentalCountry() {
//		return rentalCountry;
//	}
//
//	public void setRentalCountry(String country) {
//		this.rentalCountry = country;
//	}

	
	@Column(name = "application_ext_id")
	public int getApplicationExtId() {
		return applicationExtId;
	}

	public void setApplicationExtId(int applicationExtId) {
		this.applicationExtId = applicationExtId;
	}

	
	@Column(name = "lease_term_months")
	public int getLeaseTermMonths() {
		return leaseTermMonths;
	}

	public void setLeaseTermMonths(int leaseTermMonths) {
		this.leaseTermMonths = leaseTermMonths;
	}

	@Column(name = "credit_recommendation")
	public Character getCreditRecommendation() {
		return creditRecommendation;
	}

	public void setCreditRecommendation(Character creditRecommendation) {
		this.creditRecommendation = creditRecommendation;
	}

	@Transient
	public String getCreditRecommendationLabel(){
		if(creditRecommendation == null) creditRecommendation = '0';
		
		switch (creditRecommendation) {
		case '1':
			return "Accept";
		case '2':
			return "LowAccept";
		case '3':
			return "Conditional";
		case '4':
			return "Decline";
		case '5':
			return "Refer";
		case '6':
			return "Pending";
		default:
			return "";
		}
	}
	
	@Column(name = "selected_bundle")
	public Character getSelectedBundle() {
		return selectedBundle;
	}

	public void setSelectedBundle(Character selectedBundle) {
		this.selectedBundle = selectedBundle;
	}

	@Transient
	public String getSelectedBundleLabel(){
		if(selectedBundle == null) selectedBundle = '0';
		
		switch (selectedBundle) {
		case '1':
			return "PackageCore";
		case '2':
		default:
			return "";
		}
	}

	@Column(name = "rental_amount")
	public Double getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(Double rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	@Column(name = "credit_policy")
	public String getCreditPolicy() {
		return creditPolicy;
	}

	public void setCreditPolicy(String creditPolicy) {
		this.creditPolicy = creditPolicy;
	}

//	@Column(name = "start_date")
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	@Column(name = "submission_date")
//	public Date getSubmissionDate() {
//		return submissionDate;
//	}
//
//	public void setSubmissionDate(Date submissionDate) {
////		if (this.submissionDate == null){
//			this.submissionDate = submissionDate;
////		}
//	}
//
//	@Column(name = "assigned_date")
//	public Date getAssignedDate() {
//		return assignedDate;
//	}
//
//	public void setAssignedDate(Date assignedDate) {
//		this.assignedDate = assignedDate;
//	}
//
//	@Column(name = "completion_date")
//	public Date getCompletionDate() {
//		return completionDate;
//	}
//
//	private void setCompletionDate(Date completionDate) {
//		if (this.completionDate == null){
//			this.completionDate = completionDate;
//		}
//	}
//
//	@Column(name = "application_type")
//	public Character getType() {
//		return type;
//	}
//
//	public void setType(Character type) {
//		this.type = type;
//	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
//	private void sanitizeApplicants(){
//		for (Applicant person: applicants){
//			person.sanitize();
//		}
//	}
	
//	@Transient
//	public String getStatusLabel(){
//		return com.tea.enums.ApplicationStatus.getLabel(getStatusCode());
//	}
//
//	@Transient
//	public ApplicationStatus getStatus() {
//		return ApplicationStatus.getEnum(getStatusCode());
//	}
//	
//	public void setStatus(com.tea.enums.ApplicationStatus status){
//		if (Objects.equals(this.statusCode, new Character(status.getCode()))) return;
//		
//		boolean wasComplete = Objects.equals(this.statusCode, ApplicationStatus.COMPLETE.getCode());
//		boolean isFinal = this.qbTransaction != null;
//		
//		setStatusCode(status.getCode());
//		
//		if (status == ApplicationStatus.SAVED && this.signingStatusCode == SigningStatus.Sent.getCode()){
//			setSigningStatus(SigningStatus.Ignore);
//		}
//		
//		switch (status) {
//		case COMPLETE:
//			Date now = new Date(System.currentTimeMillis());
//			setCompletionDate(now);
//			if (!isFinal) {
//				SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
//				String period = sdf.format(now);
//				setQboPostingMonth(period);
//			}
//			break;
//		case LOCKED:
//			sanitizeApplicants();
//			break;
//		default:
//			if (wasComplete && this.abandoned) setAbandoned(false);
//			if (!isFinal) setQboPostingMonth(null);
//			break;
//		}
//	}
//
//	@Transient
//	@Deprecated
//	public String getStatusString() {
//		return ApplicationStatus.getLabel(getStatusCode());
//	}
//	
//	@Transient
//	public String getTypeLabel(){
//		return com.tea.enums.ApplicationType.getLabel(getType());
//	}
//	
//	public void setType(com.tea.enums.ApplicationType type){
//		setType(type.getCode());
//	}
//
//	@Column(name = "status2")
//	public Character getStatus2() {
//		return status2;
//	}
//
//	public void setStatus2(Character status2) {
//		this.status2 = status2;
//	}
//

	@OneToMany(mappedBy = "application", cascade = { CascadeType.ALL })
	public java.util.List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(java.util.List<Applicant> applicants) {
		this.applicants = applicants;
	}
	

	@Transient
	public Character getCoApplicantAvailable() {
		return coApplicantAvailable;
	}

	public void setCoApplicantAvailable(Character option) {
		this.coApplicantAvailable = option;
	}


	@Transient
	public boolean isCoApplicant() {
		return ('Y' == coApplicantAvailable);
	}



	@Transient
	public Applicant getPrimary() {
		Applicant primary = null;
		for (Applicant a : applicants) {
			if (StringUtils.equals(a.getApplicantType(), "Primary")) {
				primary = a;
				break;
			}
		}

		return primary;
	}
//
//	@Transient
//	public String getTypeString() {
//		return com.tea.enums.ApplicationType.getEnum(getType()).getLabel();
//	}
//
//
//	@Transient
//	public AnonymousUser getAnonymousUser() {
//		return anonymousUser;
//	}
//
//	public void setAnonymousUser(AnonymousUser anonymousUser) {
//		this.anonymousUser = anonymousUser;
//	}





	@Column(name = "rental_deposit")
	public double getRentalDeposit() {
		return rentalDeposit;
	}

	public void setRentalDeposit(double rentalDeposit) {
		this.rentalDeposit = rentalDeposit;
	}




//	@Transient
//	public double getTotalApplicantCharge() {
//		Double totAppChargeAmt = 0d;
//		for (ApplicationCollectionItem aitem : this.applicationCollectionItems) {	
//			if (aitem.getAmount() != null)
//			totAppChargeAmt = totAppChargeAmt + aitem.getAmount();
//		}
//	return totAppChargeAmt;
//	}


//	@Transient
//	public Applicant getFirstApplicant() {
//		return this.getApplicants().get(0);
//	}
//
//	@Transient
//	public Applicant getSecondaryApplicant() {
//		if (this.getApplicants().size() < 2) return null;
//		return this.getApplicants().get(1);
//	}
//	


	@Column(name = "landlord_pays")
	public boolean isLandlordPays() {
		return landlordPays;
	}

	public void setLandlordPays(boolean landlordPays) {
		this.landlordPays = landlordPays;
	}

	
//	@Transient
//	public Map<Integer, String> getPropertiesMap() {
//		return propertiesMap;
//	}
//
//	public void setPropertiesMap(Map<Integer, String> propertiesMap) {
//		this.propertiesMap = propertiesMap;
//	}


//	@Transient
//	public String getCustomAppName() {
//		if(getId() != null && getId() > 0){
//			
//			customApplication = "a"+getId()+"-custom.pdf";
//			return customApplication;
//			
//		}
//		return null;
//	}

//	@Transient
//	public String getStandardAppName() {
//		if(getId() != null && getId() > 0){
//			standardApplication = "a"+getId()+"-standard.pdf";
//			return standardApplication;
//		}
//		return null;
//	}






	@Transient
	public Integer getApplicationId() {
		return getId();
	}


	@Transient
	public Integer getPropertyId() {
		if (property != null){
			return property.getId();
		}
		return null;
	}

	@Transient
	public String getPropertyName() {
		if (property != null){
			return property.getName();
		}
		return null;
	}


	@Transient
	public String getApplicantNames() {
		StringBuilder strBld = new StringBuilder();
		for (Applicant applicant : getApplicants()) {
            strBld.append(applicant.getFirstName() + " " + applicant.getLastName() + ", ");
            applicant = null;
         }
		return strBld.toString().replaceAll(",\\s$", "");
	}

	@Transient
	public int getApplicantCount() {
		if (applicants == null) return 0;
		return applicants.size();
	}

//	@Transient
//	public String getPrimarySsn() {
//		return applicants.get(0).getSsn();
//	}

	@Transient
	public String getUnit() {
		return getApartmentNo();
	}

	

//	@Transient
//	public String getItemDescription() {
//		String applicantName = "";
//        StringBuilder sb = new StringBuilder();
//        
//		for (Applicant applicant : getApplicants()) {
//            applicantName = applicantName + applicant.getFirstName() + " " + applicant.getLastName() + ", ";
//            if (!ObjectUtils.equals(applicant.getPackge(), null) && !StringUtils.isBlank(applicant.getPackge().getName())) {
//            	sb.append(applicant.getPackge().getName() + ", ");
//            }
//            if (applicant.getServices() != null && applicant.getServices().size() > 0){
//            	for (Iterator<Service> iterator = applicant.getServices().iterator(); iterator
//						.hasNext();) {
//					Service svc = (Service) iterator.next();
//					sb.append(svc.getName() + ", ");
//				}
//            }
//		}
//         return sb.toString();
//	}


	


}
