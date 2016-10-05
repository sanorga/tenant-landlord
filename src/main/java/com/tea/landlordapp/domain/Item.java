package com.tea.landlordapp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item extends AuditableEntity {

   private static final long serialVersionUID = 4524163685024357624L;

   private char isDefault = 'N';

   private Integer sortOrder;

   private String name;
   private String abbr;

   private ReferenceList list;

   @ManyToOne
   @JoinColumn(name = "list_id")
   public ReferenceList getList() {
      return list;
   }

   public void setList(ReferenceList list) {
      this.list = list;
   }

   @Column(name = "sort_order")
   public Integer getSortOrder() {
      return sortOrder;
   }

   public void setSortOrder(Integer sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Column(name = "name")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Column(name = "abbr")
   public String getAbbr() {
      return abbr;
   }

   public void setAbbr(String abbr) {
      this.abbr = abbr;
   }

   @Column(name = "is_default")
   public char getIsDefault() {
      return isDefault;
   }

   public void setIsDefault(char isDefault) {
      this.isDefault = isDefault;
   }
}
