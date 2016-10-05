package com.tea.landlordapp.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditableEntity extends RootEntity {

   private Timestamp createdDate;
   private Timestamp modifiedDate;
   private User modifiedBy;
   private User createdBy;

   @ManyToOne
   @JoinColumn(name = "created_by")
   public User getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(User createdBy) {
      this.createdBy = createdBy;
   }

   @Column(name = "created_date", insertable=false, updatable=false)
   public Timestamp getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(Timestamp createdDate) {
      this.createdDate = createdDate;
   }

   @ManyToOne
   @JoinColumn(name = "modified_by")
   public User getModifiedBy() {
      return modifiedBy;
   }

   public void setModifiedBy(User modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   @Column(name = "modified_date", insertable=false, updatable=false)
   public Timestamp getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(Timestamp modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public void setAuditInfo(User user) {
      if (this.isNew()) {
         this.setCreatedBy(user);
//         this.setCreatedDate(null);
      } 
      this.setModifiedBy(user);
//      this.setModifiedDate(null);
   }
}
