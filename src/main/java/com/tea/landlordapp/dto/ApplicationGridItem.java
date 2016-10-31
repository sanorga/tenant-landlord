package com.tea.landlordapp.dto;

import java.sql.Timestamp;

public class ApplicationGridItem {

   public ApplicationGridItem(Integer id, 	   Integer propertyExtId, String applicantEmailId, 	String firstName,
							  String lastName, Timestamp createdDate, Timestamp modifiedDate, 	String status) {
      this.setId(id);
	  this.setPropertyExtId(propertyExtId);
	  this.setApplicantEmailId(applicantEmailId);
      this.setFirstName(firstName);
      this.setLastName(lastName);
      this.setCreatedDate(createdDate);
	  this.setModifiedDate(modifiedDate);
      this.setStatus(status);
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getPropertyExtId() {
      return propertyExtId;
   }

   public void setPropertyExtId(Integer propertyExtId2) {
      this.propertyExtId = propertyExtId2;
   }

     public String getApplicantEmailId() {
      return applicantEmailId;
   }

   public void setApplicantEmailId(String applicantEmailId) {
      this.applicantEmailId = applicantEmailId;
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

   private Integer id;
   private Integer propertyExtId;
   private String applicantEmailId;
   private String firstName;
   private String lastName;
   private Timestamp createdDate;
   private Timestamp modifiedDate;
   private String status;


}
