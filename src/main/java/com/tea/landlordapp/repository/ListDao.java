package com.tea.landlordapp.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tea.landlordapp.domain.Item;
import com.tea.landlordapp.domain.ReferenceList;
import com.tea.landlordapp.dto.IntegerStringKVDto;

public interface ListDao extends Serializable {

   public List<ReferenceList> findAllLists() throws DataAccessException;

   public ReferenceList findList(int id) throws DataAccessException;

   public Item findItem(int id) throws DataAccessException;

   public List<Item> findAllItems(int listId) throws DataAccessException;
   
   public List<IntegerStringKVDto> findClientLookupLists(int parentId);

   public List<IntegerStringKVDto> findManagementCompanyLookupLists(int managementCompanyId);
   
   public List<IntegerStringKVDto> findManagementCompanyLookupLists();
   
   public List<IntegerStringKVDto> findPropertyLookupLists(int userId);

   
   
}
