package com.tea.landlordapp.dto;

import java.util.List;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.ObjectUtils;

@Entity
public class IntegerStringKVDto {
	@Id
	@Column(name = "key")
	private Integer key;
	
	@Column(name = "value") 
	private String value;

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static TreeMap<Integer, String> buildLookupMap(List<IntegerStringKVDto> list){
		return buildLookupMap(null,null,list);
	}
	
	public static TreeMap<Integer, String> buildLookupMap(Integer firstKey, String firstValue,
			List<IntegerStringKVDto> list){
		TreeMap<Integer, String> lookupList = new TreeMap<Integer, String>();
		if (firstKey != null) {
			lookupList.put(firstKey, firstValue);
		}
		for (IntegerStringKVDto integerStringKVDto : list) {
			lookupList.put(integerStringKVDto.getKey(), integerStringKVDto.getValue());
		}
		
		return lookupList;
	}
}
