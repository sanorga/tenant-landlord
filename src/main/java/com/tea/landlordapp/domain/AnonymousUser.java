package com.tea.landlordapp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

//import com.tea.landlordapp.enums.ApplicationPaymentMethod;

@Entity
@Table(name = "anonymous_user")
public class AnonymousUser extends AuditableEntity {

   private static final long serialVersionUID = 2753713313840668469L;

   private char status = 'A';
   private char applicationType = 'T';

   private Character isCoapplicantAvailable = 'N';
   private Character isRentalLocationAvailable = 'N';
   private Character isResidenceAvailable = 'N';
   private Character isEmploymentAvailable = 'N';
   private Character isFinancialAvailable = 'N';
   private Character isReferenceAvailable = 'N';
   private Character isContactAvailable = 'N';
   private Character isOccupantsAvailable = 'N';
   private Character isVehicleAvailable = 'N';
   private Character isPetAvailable = 'N';
   private Character isAllPages = 'Y';
   private Character saveNewAddress = 'N';
   private char paymentMethod = 'B';

   private Double price;
//   private double applicantCharge = 0;

   private String emailId;
   private String coappEmailId;
   private String reference;

//   private Subscriber client;
   private Property property;

//   private Application application;
//   private Packge packge;

//   @ManyToOne
//   @JoinColumn(name = "client_id")
//   public Subscriber getClient() {
//      return client;
//   }
//
//   public void setClient(Subscriber client) {
//      this.client = client;
//   }

   @Column(name = "reference")
   public String getReference() {
      return reference;
   }

   public void setReference(String reference) {
      this.reference = reference;
   }

   @Column(name = "email_id")
   public String getEmailId() {
      return emailId;
   }

   public void setEmailId(String emailId) {
      this.emailId = emailId;
   }
   
   @Column(name = "coapp_email_id")
   public String getCoappEmailId() {
      return coappEmailId;
   }

   public void setCoappEmailId(String coappEmailId) {
      this.coappEmailId = coappEmailId;
   }

   @ManyToOne
   @JoinColumn(name = "property_id")
   public Property getProperty() {
      return property;
   }

   public void setProperty(Property property) {
      this.property = property;
   }

//   @OneToOne(cascade = {CascadeType.ALL})
//   @JoinColumn(name = "application_id")
//   public Application getApplication() {
//      return application;
//   }
//
//   public void setApplication(Application application) {
//      this.application = application;
//   }
//
//   @ManyToOne
//   @JoinColumn(name = "package_id")
//   public Packge getPackge() {
//      return packge;
//   }
//
//   public void setPackge(Packge packge) {
//      this.packge = packge;
//   }

   @Column(name = "status")
   public char getStatus() {
      return status;
   }

   public void setStatus(char status) {
      this.status = status;
   }

   @Column(name = "price")
   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

  
   @Column(name = "application_type")
   public char getApplicationType() {
      return applicationType;
   }

   public void setApplicationType(char applicationType) {
      this.applicationType = applicationType;
   }

   @Column(name = "is_coapplicant")
   public Character getIsCoapplicantAvailable() {
      return isCoapplicantAvailable;
   }

   public void setIsCoapplicantAvailable(Character isCoapplicantAvailable) {
      this.isCoapplicantAvailable = isCoapplicantAvailable;
   }

   @Column(name = "is_rental_location")
   public Character getIsRentalLocationAvailable() {
      return isRentalLocationAvailable;
   }

   public void setIsRentalLocationAvailable(Character isRentalLocationAvailable) {
      this.isRentalLocationAvailable = isRentalLocationAvailable;
   }

   @Column(name = "is_residence")
   public Character getIsResidenceAvailable() {
      return isResidenceAvailable;
   }

   public void setIsResidenceAvailable(Character isResidenceAvailable) {
      this.isResidenceAvailable = isResidenceAvailable;
   }

   @Column(name = "is_employment")
   public Character getIsEmploymentAvailable() {
      return isEmploymentAvailable;
   }

   public void setIsEmploymentAvailable(Character isEmploymentAvailable) {
      this.isEmploymentAvailable = isEmploymentAvailable;
   }

   @Column(name = "is_financial")
   public Character getIsFinancialAvailable() {
      return isFinancialAvailable;
   }

   public void setIsFinancialAvailable(Character isFinancialAvailable) {
      this.isFinancialAvailable = isFinancialAvailable;
   }

   @Column(name = "is_reference")
   public Character getIsReferenceAvailable() {
      return isReferenceAvailable;
   }

   public void setIsReferenceAvailable(Character isReferenceAvailable) {
      this.isReferenceAvailable = isReferenceAvailable;
   }

   @Column(name = "is_contact")
   public Character getIsContactAvailable() {
      return isContactAvailable;
   }

   public void setIsContactAvailable(Character isContactAvailable) {
      this.isContactAvailable = isContactAvailable;
   }

   @Column(name = "is_occupants")
   public Character getIsOccupantsAvailable() {
      return isOccupantsAvailable;
   }

   public void setIsOccupantsAvailable(Character isOccupantsAvailable) {
      this.isOccupantsAvailable = isOccupantsAvailable;
   }

   @Column(name = "is_vehicle")
   public Character getIsVehicleAvailable() {
      return isVehicleAvailable;
   }

   public void setIsVehicleAvailable(Character isVehicleAvailable) {
      this.isVehicleAvailable = isVehicleAvailable;
   }

   @Column(name = "is_pet")
   public Character getIsPetAvailable() {
      return isPetAvailable;
   }

   public void setIsPetAvailable(Character isPetAvailable) {
      this.isPetAvailable = isPetAvailable;
   }

   @Column(name = "save_new_address")
   public Character getSaveNewAddress() {
      return saveNewAddress;
   }

   public void setSaveNewAddress(Character saveNewAddress) {
      this.saveNewAddress = saveNewAddress;
   }
   
   @Column(name = "is_all_pages")
   public Character getIsAllPages() {
      return isAllPages;
   }

   public void setIsAllPages(Character isAllPages) {
      this.isAllPages = isAllPages;
   }

   @Transient
   public boolean isActive() {
      return ('A' == getStatus());
   }

//   @Transient
//   public BinaryFile getTermsFile() {
//      if (!ObjectUtils.equals(property, null)) {
//         return property.getTermsFile();
//      } else
//         return null;
//   }

//@Column(name = "applicant_charge")
//   public double getApplicantCharge() {
//	return applicantCharge;
//}
//
//public void setApplicantCharge(double applicantCharge) {
//	this.applicantCharge = applicantCharge;
//}

@Column(name = "payment_method")
   public char getPaymentMethod() {
	return paymentMethod;
}

public void setPaymentMethod(char paymentMethod) {
	this.paymentMethod = paymentMethod;
}
//@Transient
//public ApplicationPaymentMethod getApplicationPaymentMethod() {
//	return ApplicationPaymentMethod.getEnum(paymentMethod);
//}
//
//public void setApplicationPaymentMethod(ApplicationPaymentMethod paymentMethod) {
//	this.paymentMethod = paymentMethod.getCode();
//}
}