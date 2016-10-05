package com.tea.landlordapp.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "list")
public class ReferenceList extends AuditableEntity {

   private static final long serialVersionUID = 364590006306320853L;

   private char useAbbr = 'Y';

   private String name;
   private Set<Item> items;

   @Column(name = "name")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Column(name = "use_abbr")
   public char getUseAbbr() {
      return useAbbr;
   }

   public void setUseAbbr(char useAbbr) {
      this.useAbbr = useAbbr;
   }

   @OneToMany(mappedBy = "list", cascade = {CascadeType.ALL})
   public Set<Item> getItems() {
      return items;
   }

   public void setItems(Set<Item> items) {
      this.items = items;
   }
}