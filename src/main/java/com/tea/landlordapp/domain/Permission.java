package com.tea.landlordapp.domain;

import org.springframework.security.core.GrantedAuthority;

public class Permission implements GrantedAuthority {

   private static final long serialVersionUID = -8635345200443491003L;
   private String authority;

   @SuppressWarnings("unused")
   private Permission() {

   }

   public Permission(String authority) {
      this.authority = authority;
   }

   @Override
   public String getAuthority() {
      return authority;
   }

}
