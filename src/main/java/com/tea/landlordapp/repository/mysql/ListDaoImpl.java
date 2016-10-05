package com.tea.landlordapp.repository.mysql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tea.landlordapp.domain.Item;
import com.tea.landlordapp.domain.ReferenceList;
import com.tea.landlordapp.dto.IntegerStringKVDto;
import com.tea.landlordapp.repository.ListDao;

@Repository("listDao")
@Transactional(readOnly = true)
public class ListDaoImpl implements ListDao {

   private static final long serialVersionUID = 1L;

   protected transient final Logger logger = LoggerFactory.getLogger(getClass());

   private transient EntityManager em;

   @Required
   @PersistenceContext
   public void setEntityManager(EntityManager em) {
      this.em = em;
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<ReferenceList> findAllLists() {
      Query query = em.createQuery("select l from com.tea.domain.List l");
      return (List<ReferenceList>) query.getResultList();
   }

   @Override
   public ReferenceList findList(int id) {
      return em.find(ReferenceList.class, id);
   }

   @Override
   public Item findItem(int id) {
      return em.find(Item.class, id);
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Item> findAllItems(int listId) {
      Query query = em.createQuery("select i from com.tea.domain.Item i where i.list.id = :listId order by i.sortOrder");
      query.setParameter("listId", listId);
      List<com.tea.landlordapp.domain.Item> item = query.getResultList();

      if (ObjectUtils.equals(item, null) || (item.size() == 0)) return null;

      return item;
   }

@Override
public List<IntegerStringKVDto> findClientLookupLists(int parentId) {
	StringBuilder sb = new StringBuilder();
	sb.append("select id `key`, name `value`");
	sb.append(" from subscriber");
	sb.append(" where parent_id = :parentId");

	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
	query.setParameter("parentId", parentId);
	@SuppressWarnings("unchecked")
	List<IntegerStringKVDto> list = query.getResultList();
	
	return list;
}

@Override
public List<IntegerStringKVDto> findManagementCompanyLookupLists(int managementCompanyId) {
	StringBuilder sb = new StringBuilder();
	sb.append("select id `key`, name `value`");
	sb.append(" from management_company");
	sb.append(" where id = :mnagementCompanyId");

	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
	query.setParameter("managementCompanyId", managementCompanyId);
	@SuppressWarnings("unchecked")
	List<IntegerStringKVDto> list = query.getResultList();
	
	return list;
}

@Override
public List<IntegerStringKVDto> findManagementCompanyLookupLists() {
	StringBuilder sb = new StringBuilder();
	sb.append("select id `key`, name `value`");
	sb.append(" from management_company");
	sb.append(" where status_code = 'A'");

	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
//	query.setParameter("managementCompanyId", managementCompanyId);
	@SuppressWarnings("unchecked")
	List<IntegerStringKVDto> list = query.getResultList();
	
	return list;
}

@Override
public List<IntegerStringKVDto> findPropertyLookupLists(int userId) {
	StringBuilder sb = new StringBuilder();
	sb.append("select id `key`, name `value`");
	sb.append(" from property");
	sb.append(" where user_id = :userId");

	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
	query.setParameter("userId", userId);
	@SuppressWarnings("unchecked")
	List<IntegerStringKVDto> list = query.getResultList();
	
	return list;
}

}
