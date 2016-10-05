package com.tea.landlordapp.dto;

public class LookupListItem {

   private int id;
   private String name;

   public LookupListItem(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public LookupListItem(int id, String firstName, String lastName) {
      this.id = id;
      this.name = String.format("%s %s", firstName, lastName);
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }
}
