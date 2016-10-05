package com.tea.landlordapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "zipcode")
public class Zipcode extends AuditableEntity {

   private static final long serialVersionUID = 7350067239692225896L;

   private String zipcode;
   private String city;
   private String county;
   private String state;

   @Column(name = "city")
   public String getCity() {
      return city;
   }

   @Column(name = "county")
   public String getCounty() {
      return county;
   }

   @Column(name = "state")
   public String getState() {
      return state;
   }

   @Column(name = "zipcode")
   public String getZipcode() {
      return zipcode;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public void setCounty(String county) {
      this.county = county;
   }

   public void setState(String state) {
      this.state = state;
   }

   public void setZipcode(String zipcode) {
      this.zipcode = zipcode;
   }

}