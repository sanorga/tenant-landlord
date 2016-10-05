package com.tea.landlordapp.service;

import java.io.Serializable;
import java.util.List;

import com.tea.landlordapp.domain.Item;
import com.tea.landlordapp.domain.ReferenceList;
import com.tea.landlordapp.dto.IntegerStringKVDto;

public interface ListService extends Serializable {

   public Item findItem(int id);

   public List<Item> findItems(int listId);

   public ReferenceList findList(int id);

   public List<ReferenceList> findLists();
   
   public List<IntegerStringKVDto> findClientLookupList(int parentId);
   
   public List<IntegerStringKVDto> findPropertyLookupList(int userId);

   public List<IntegerStringKVDto> findManagementCompanyLookupList(int parentId);

   public List<IntegerStringKVDto> findManagementCompanyLookupList();
}
