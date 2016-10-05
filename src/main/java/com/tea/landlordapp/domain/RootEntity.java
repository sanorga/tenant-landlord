package com.tea.landlordapp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class RootEntity implements Serializable {


	   /**
	 * 
	 */
	private static final long serialVersionUID = -7513453937082238552L;
	private Integer id;

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id")
	   public Integer getId() {
	      return id;
	   }

	   public void setId(Integer id) {
	      this.id = id;
	   }

	   @Transient
	   public boolean isNew() {
	      return (this.id == null);
	   }

}
