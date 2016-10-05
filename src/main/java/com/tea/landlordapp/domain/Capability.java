package com.tea.landlordapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "capability")
public class Capability extends AuditableEntity {

   private static final long serialVersionUID = 7350067239692225896L;

   private char isAvailable = 'Y';

   private String name;
   private String requestId;

   @Column(name = "name")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Column(name = "request_id")
   public String getRequestId() {
      return requestId;
   }

   public void setRequestId(String requestId) {
      this.requestId = requestId;
   }

   @Column(name = "is_available")
   public char getIsAvailable() {
      return isAvailable;
   }

   public void setIsAvailable(char isAvailable) {
      this.isAvailable = isAvailable;
   }

}