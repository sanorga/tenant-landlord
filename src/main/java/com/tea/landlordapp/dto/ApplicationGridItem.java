package com.tea.landlordapp.dto;

import java.sql.Timestamp;

public class ApplicationGridItem {

   public ApplicationGridItem(Integer id, 	   Integer applicationExtId, String applicantEmailId, 	String fullName, String firstName,
							  String lastName, Timestamp createdDate, Timestamp modifiedDate, 	String status, String stateLabel,
							  String addressLine1, String city , String creditRecommendationLabel, boolean canRequestReport ) {
      this.setId(id);
	  this.setApplicationExtId(applicationExtId);
	  this.setApplicantEmailId(applicantEmailId);
	  this.setFullName(fullName);
      this.setFirstName(firstName);
      this.setLastName(lastName);
      this.setCreatedDate(createdDate);
	  this.setModifiedDate(modifiedDate);
	  this.setStatus(status);
      this.setStateLabel(stateLabel);
      this.setAddressLine1(addressLine1);
      this.setCity(city);
      this.setCreditRecommendationLabel(creditRecommendationLabel);
      this.setCanRequestReport(canRequestReport);
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getApplicationExtId() {
      return applicationExtId;
   }

   public void setApplicationExtId(Integer applicationExtId) {
      this.applicationExtId = applicationExtId;
   }

     public String getApplicantEmailId() {
      return applicantEmailId;
   }

   public void setApplicantEmailId(String applicantEmailId) {
      this.applicantEmailId = applicantEmailId;
   }

   public String getFullName() {
	      return fullName;
	   }

   public void setFullName(String fullName) {
	     this.fullName = fullName;
   }
	   
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Timestamp getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(Timestamp createdDate) {
      this.createdDate = createdDate;
   }

  public Timestamp getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(Timestamp modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
   
   public String getStateLabel() {
	      return stateLabel;
   }

   public void setStateLabel(String stateLabel) {
	      this.stateLabel = stateLabel;
   }
	   
   public String getAddressLine1() {
	      return addressLine1;
	   }

   public void setAddressLine1(String addressLine1) {
	  this.addressLine1 = addressLine1;
   }
	   
   public String getCity() {
	      return city;
	   }

	public void setCity(String city) {
		  this.city = city;
	}

	public String getCreditRecommendationLabel() {
	      return creditRecommendationLabel;
	}

	public void setCreditRecommendationLabel(String creditRecommendationLabel) {
	  this.creditRecommendationLabel = creditRecommendationLabel;
	}
	
	public boolean getCanRequestReport() {
	     return canRequestReport;
	}

	public void setCanRequestReport(boolean canRequestReport) {
	     this.canRequestReport = canRequestReport;
	}

   private Integer id;
   private Integer applicationExtId;
   private String applicantEmailId;
   private String fullName;
   private String firstName;
   private String lastName;
   private Timestamp createdDate;
   private Timestamp modifiedDate;
   private String status;
   private String stateLabel;
   private String addressLine1;
   private String city;
   private String creditRecommendationLabel;
   private boolean canRequestReport;


}
