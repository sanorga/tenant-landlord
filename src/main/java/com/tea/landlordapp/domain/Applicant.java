package com.tea.landlordapp.domain;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
// import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.dto.DataKeyDto;
import com.tea.landlordapp.exception.CryptoServiceException;
import com.tea.landlordapp.service.CryptoService;

//import com.tea.utility.EncryptionService;
import com.tea.landlordapp.utility.StringHelper;

@SuppressWarnings("unchecked")
@Entity
@Table(name = "applicant")
public class Applicant extends CryptoEnabledEntity {

   private static final long serialVersionUID = -2591985426980566373L;
//	protected transient final Logger logger = LoggerFactory
//			.getLogger(getClass());
//	
//	private CryptoService cryptoService = null;
   

   private String applicantType;
   private String firstName;
   private String lastName;
   private String middleInitial;
   private int    applicationExtId;
//   private String ssn;

   private String emailAddress;


   private Application application;

   @ManyToOne
   @JoinColumn(name = "application_id")
   public Application getApplication() {
      return application;
   }

   public void setApplication(Application application) {
      this.application = application;
   }

   @Column(name = "applicant_type")
   public String getApplicantType() {
      return applicantType;
   }

   public void setApplicantType(String applicantType) {
      this.applicantType = applicantType;
   }

   @Column(name = "first_name")
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   @Column(name = "last_name")
   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   @Column(name = "middle_initial")
   public String getMiddleInitial() {
      return middleInitial;
   }

   public void setMiddleInitial(String middleInitial) {
      this.middleInitial = middleInitial;
   }

 

 

   @Column(name = "email_address")
   public String getEmailAddress() {
      return emailAddress;
   }

   public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
   }

 



   @Transient
   public String getFullName() {
      return StringHelper.buildFullName(firstName, middleInitial, lastName);
   }

@Override
public void sanitize() {
	// TODO Auto-generated method stub
	
}


}
