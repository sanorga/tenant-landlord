package com.tea.landlordapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.Item;
import com.tea.landlordapp.domain.ReferenceList;
import com.tea.landlordapp.dto.IntegerStringKVDto;
import com.tea.landlordapp.repository.ListDao;

@Service("listService")
public class ListServiceImpl implements ListService {

   private static final long serialVersionUID = 137976015002313221L;

   @Autowired
   private ListDao listDao;

   @Override
   public Item findItem(int id) {
      return listDao.findItem(id);
   }

   @Override
   public List<Item> findItems(int listId) {
      return listDao.findAllItems(listId);
   }


   @Override
   public ReferenceList findList(int id) {
      return listDao.findList(id);
   }

   @Override
   public List<ReferenceList> findLists() {
      return listDao.findAllLists();
   }

@Override
public List<IntegerStringKVDto> findClientLookupList(int parentId) {
	return listDao.findClientLookupLists(parentId);
}

@Override
public List<IntegerStringKVDto> findManagementCompanyLookupList(int parentId) {
	return listDao.findManagementCompanyLookupLists(parentId);
}

@Override
public List<IntegerStringKVDto> findManagementCompanyLookupList() {
	return listDao.findManagementCompanyLookupLists();
}

@Override
public List<IntegerStringKVDto> findPropertyLookupList(int userId) {
	return listDao.findPropertyLookupLists(userId);
	
}
}
