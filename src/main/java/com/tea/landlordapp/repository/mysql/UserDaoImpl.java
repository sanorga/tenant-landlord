package com.tea.landlordapp.repository.mysql;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.AbstractMap.SimpleEntry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.AnonymousUser;
//import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Capability;
//import com.tea.landlordapp.domain.IndexAuto;
//import com.tea.landlordapp.domain.ManagementCompany;
import com.tea.landlordapp.domain.PasswordPolicy;
//import com.tea.landlordapp.domain.Preference;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.Role;
//import com.tea.landlordapp.domain.SearchTerm;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.domain.Zipcode;
import com.tea.landlordapp.dto.LookupListItem;
import com.tea.landlordapp.dto.SelectionListItem;
import com.tea.landlordapp.repository.UserDao;

@Repository("userDao")
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

	private static final long serialVersionUID = 1L;

	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());

	private transient EntityManager em;

	private Map<Integer, String> viewClientsSortOptions;

	private Map<Integer, String> getViewClientsSortOptions() {
		if (CollectionUtils.isEmpty(viewClientsSortOptions)) {
			viewClientsSortOptions = new HashMap<Integer, String>();
			viewClientsSortOptions.put(0, "s.name");
			viewClientsSortOptions.put(1, "s.address");
			viewClientsSortOptions.put(2, "s.city");
			viewClientsSortOptions.put(3, "s.state");
			viewClientsSortOptions.put(4, "s.parent.name");
		}

		return viewClientsSortOptions;
	}

	@Required
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Property findPropertyByName(String name) {
		Query query = em
				.createQuery("select p from Property p where p.name = :name");
		query.setParameter("name", name);

		List<Property> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;

		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Property findPropertyById(int id) {
		Query query = em
				.createQuery("select p from Property p where p.id = :id");
		query.setParameter("id", id);

		List<Property> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;

		return list.get(0);
	}


	
	@Override
	public TreeMap<Integer, String> getClientDisplayListByParentId(int id) {
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select s.id, s.name");
		sb.append(" from subscriber s");
		sb.append(" where s.parent_id = :id");
		sb.append(" and s.subscriber_type = :type");

		Query qry = em.createNativeQuery(sb.toString());
		qry.setParameter("id", id);
		qry.setParameter("type", 'C');

		@SuppressWarnings("rawtypes")
		Iterator itr = qry.getResultList().iterator();

		while (itr.hasNext()) {
			Object[] dto = (Object[]) itr.next();
			map.put((Integer) dto[0], (String) dto[1]);
		}

		return map;
	}

	@Override
	public List<Integer> getClientIdsByParent(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select s.id");
		sb.append(" from subscriber s");
		sb.append(String.format(" where s.parent_id = %d", id));
		sb.append(String.format(" and s.subscriber_type = '%s'", 'C'));

		Query qry = em.createNativeQuery(sb.toString());

		@SuppressWarnings("unchecked")
		List<Integer> resList = qry.getResultList();
		return resList;
	}

	@Override
	public List<SimpleEntry<String,String>> getClientEmaiListByParent(int id) {
		List<SimpleEntry<String,String>> list = new ArrayList<SimpleEntry<String,String>>();
		StringBuilder sb = new StringBuilder();
		sb.append("select s.name, s.notify_email");
		sb.append(" from subscriber s");
		sb.append(" where s.parent_id = :id");
		sb.append(" and s.subscriber_type = :type");

		Query qry = em.createNativeQuery(sb.toString());
		qry.setParameter("id", id);
		qry.setParameter("type", 'C');

		@SuppressWarnings("rawtypes")
		Iterator itr = qry.getResultList().iterator();

		while (itr.hasNext()) {
			Object[] dto = (Object[]) itr.next();
			list.add(new SimpleEntry<String,String>((String) dto[0], (String) dto[1]));
//			list.add(KeyValue.with((String) dto[0], (String) dto[1]));
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LookupListItem> getClientLookupList(int id) {
		Query query = em
				.createQuery("select new com.tea.dto.LookupListItem(s.id, s.name) from Subscriber s where s.parent.id = :id and s.subscriberType = :type order by s.name");
		query.setParameter("id", id);
		query.setParameter("type", 'C');
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LookupListItem> getClientsWithAppsLookupList(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct s.id, s.name");
		sb.append(" from Application a join a.client s");
		sb.append(" where s.subscriberType = :type");
		if (id > 0)
			sb.append(String.format(" and s.parent.id = %d", id));
		sb.append(" order by s.name");

		Iterator<Object[]> query = em.createQuery(sb.toString())
				.setParameter("type", 'C').getResultList().iterator();

		return buildLookupList(query);
	}

	@Override
	public List<LookupListItem> getClientsWithAppsLookupList() {
		return getClientsWithAppsLookupList(0);
	}

	private List<LookupListItem> buildLookupList(Iterator<Object[]> results) {
		List<LookupListItem> list = new ArrayList<LookupListItem>();
		while (results.hasNext()) {
			Object[] tuple = results.next();
			int id = (Integer) tuple[0];
			String name = (String) tuple[1];
			list.add(new LookupListItem(id, name));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findSalesPeople(int id) {
		Query query = em
				.createQuery("select distinct s.salesPerson from Subscriber s where s.parent.id = :id and s.subscriberType = :type order by s.salesPerson");
		query.setParameter("id", id);
		query.setParameter("type", 'C');
		return query.getResultList();
	}


//	@SuppressWarnings("unchecked")
//	@Override
//	public List<User> findAllUsersByRole(Subscriber subscriber) {
//		Query query = em
//				.createQuery("select distinct u from User u where u.role.role = :role and u.subscriber = :subscriber");
//		query.setParameter("role", "BD");
//		query.setParameter("subscriber", subscriber);
//		List<User> users = query.getResultList();
//		return users;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsersByRole(String role) {
		Query query = em
				.createQuery("select distinct u from User u where u.role.role = :role and u.status = :status");
		query.setParameter("role", role);
		query.setParameter("status", 'A');
		List<User> users = query.getResultList();
		return users;
	}



	@Override
	public User findUser(int id) {
		User user = em.find(User.class, id);
		return user;
	}


//	@Override
//	public AnonymousUser findAnonymousUser(int id) {
//		return em.find(AnonymousUser.class, id);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUsername(String username) {
		Query query = em
				.createQuery("select u from User u where u.username = :emailId");
		query.setParameter("emailId", username);

		List<User> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;
		User user = list.get(0);

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserWithPolicy(String username) {
		Query query = em
				.createQuery("select u from User u where u.username = :emailId");
		query.setParameter("emailId", username);

		List<User> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;
		User user = list.get(0);
		
		@SuppressWarnings("unused")
		PasswordPolicy pol = user.getRole().getPasswordPolicy();

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUser(String role) {
		Query query = em
				.createQuery("select distinct u from User u where u.role.role = :role");
		query.setParameter("role", role);

		List<User> list = query.getResultList();

		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;
		User user = list.get(0);

		return user;
	}

	@SuppressWarnings("unchecked")


	@Override
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		em.merge(user);
	}

	@Override
	@Transactional(readOnly = false)
	public User saveAndReturnUser(User user) {
		return em.merge(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser) {
		return em.merge(anonymousUser);
	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<AnonymousUser> findAnonymousUsers() {
//		Query query = em
//				.createQuery("Select a from AnonymousUser a where a.application.statusCode in ('C', 'S', 'P','N','A','R')");
//		return query.getResultList();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<AnonymousUser> findAnonymousUsers(int pageNoAUser) {
//
//		Query query = em
//				.createQuery("Select a from AnonymousUser a where a.application.statusCode in ('C', 'S', 'P','N','A','R')");
//
//		query.setFirstResult(pageNoAUser * 10);
//		query.setMaxResults(10);
//
//		List<AnonymousUser> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || (list.size() == 0))
//			return null;
//
//		return list;
//
//	}

	@Override
	public Integer countAnonymousUsers() {
		Query query = em.createQuery("Select count(id) from AnonymousUser a");
		return ((Long) query.getSingleResult()).intValue();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public AnonymousUser findAnonymousUserByApplication(int applicationId) {
//		Query query = em
//				.createQuery("select au from AnonymousUser au where au.application.id = :applicationId");
//		query.setParameter("applicationId", applicationId);
//		List<AnonymousUser> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || (list.size() == 0))
//			return null;
//
//		return list.get(0);
//	}

	@Override
	public Property findProperty(int id) {
		return em.find(Property.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> findPropertiesBySubscriber(int subscriberId, User user) {
		boolean filterByPm = (user != null) && !user.hasAnyAuthority(new String[] { "view.all.properties","view.properties"});
		String qry = "select p from Property p where p.subscriber.id = :subscriberId";
		if (filterByPm){
			qry = qry + " and :user member of p.authorizedManagers";
		}
		Query query = em
				.createQuery(qry);
		query.setParameter("subscriberId", subscriberId);
		if (filterByPm)
			query.setParameter("user", user);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> findPropertiesBySubscriberForRebateRpt(int subscriberId, User user) {
		boolean filterByPm = (user != null) && !user.hasAnyAuthority(new String[] { "view.all.properties","view.properties"});
		String qry = "select p from Property p where p.subscriber.id = :subscriberId or (p.id in (select a.property.id from Application a where a.originalClient.id = :subscriberId))";
		if (filterByPm){
			qry = qry + " and :user member of p.authorizedManagers";
		}
		Query query = em
				.createQuery(qry);
		query.setParameter("subscriberId", subscriberId);
		if (filterByPm)
			query.setParameter("user", user);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Property> findPropertiesByManager(User user) {
		Query query = em
				.createQuery("select p from Property p where :user member of p.authorizedManagers");
		query.setParameter("user", user);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Property findPropertyByKey(int subscriberId, String propertyKey) {
		Query query = em
				.createQuery("select p from Property p where p.subscriber.id = :subscriberId and p.propertyKey = :propertyKey");
		query.setParameter("subscriberId", subscriberId);
		query.setParameter("propertyKey", propertyKey);

		List<Property> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;

		return list.get(0);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveProperty(Property property) {
		em.merge(property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRoles() {
		Query query = em.createQuery("select r from Role r");
		return query.getResultList();
	}

	@Override
	public Role findRole(int id) {
		return em.find(Role.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role findRole(String id) {
		Query query = em.createQuery("select r from Role r where role = :id");
		query.setParameter("id", id);
		List<Role> list = query.getResultList();
		if (ObjectUtils.equals(list, null))
			return null;

		return list.get(0);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		em.merge(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Capability> findCapabilities() {
		Query query = em.createQuery("select c from Capability c");
		return query.getResultList();
	}

	@Override
	public Capability findCapability(int id) {
		return em.find(Capability.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveCapability(Capability capability) {
		em.merge(capability);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Zipcode findZipcode(String zipcode) {
		Query query = em
				.createQuery("select z from Zipcode z where zipcode = :zip");
		query.setParameter("zip", zipcode);
		List<Zipcode> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || list.isEmpty())
			return null;

		return list.get(0);
	}


	@Transactional(readOnly = false)
	public void clearHibernateEntityManager() {
		em.clear();
	}

//	@Override
//	public Property findPropertyByBigId(int id) throws DataAccessException {
//		return findProperty(Property.bigId2Id(id));
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectionListItem> getClientSelectionList(int id) {
		Query query = em
				.createQuery("select new com.tea.dto.SelectionListItem(s.id, s.name) from Subscriber s where s.parent.id = :id and s.subscriberType = 'C'");
		query.setParameter("id", id);
		List<SelectionListItem> res = query.getResultList();
		return res;
	}



	@Override
	public User findNullUser() throws DataAccessException {
		return findUserByUsername(Globals.NULL_USER_EMAIL);
	}
	
	@Override
	public User findApiUser() throws DataAccessException {
		return findUserByUsername(Globals.API_USER_EMAIL);
	}

	@Override
	public List<Property> findAuthorizedProperties(User user) {
  		Query query = em
				.createQuery("select pa.property from PropertyAuthorization pa where pa.user = :user");
		query.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Property> res = query.getResultList();
  		return res;
	}

	@Override
	public List<LookupListItem> getPropertyManagerList(Integer subscriberId) {
		Query query = em
				.createQuery("select new com.tea.dto.LookupListItem(u.id, u.firstName, u.lastName) from User u where u.subscriber.id = :subId and u.role.role = 'PM' order by u.firstName, u.lastName");
		query.setParameter("subId", subscriberId);
		@SuppressWarnings("unchecked")
		List<LookupListItem> res = query.getResultList();
		return res;
	}

	@Override
	public List<LookupListItem> getBoardDirectorList(Integer subscriberId) {
		Query query = em
				.createQuery("select new com.tea.dto.LookupListItem(u.id, u.firstName, u.lastName) from User u where u.subscriber.id = :subId and u.role.role = 'BD' order by u.firstName, u.lastName");
		query.setParameter("subId", subscriberId);
		@SuppressWarnings("unchecked")
		List<LookupListItem> res = query.getResultList();
		return res;
	}

	@Override
	public List<LookupListItem> getApplicationApprovalUserList(Integer subscriberId, java.util.List<Integer> authorizedManagerIds) {
		Query query = null;
		if (authorizedManagerIds != null && authorizedManagerIds.size() > 0 ) {
			query = em
					.createQuery("select new com.tea.dto.LookupListItem(u.id, u.firstName, u.lastName) from User u where u.subscriber.id = :subId and ( u.role.role = 'CA' or (u.role.role in  ('BD', 'PM') and u.id in (:authorizedManagerIds)) ) order by u.firstName, u.lastName");
			query.setParameter("authorizedManagerIds", authorizedManagerIds);	
		}
		else {
			query = em
				.createQuery("select new com.tea.dto.LookupListItem(u.id, u.firstName, u.lastName) from User u where u.subscriber.id = :subId and  u.role.role = 'CA'  order by u.firstName, u.lastName");
		}
		query.setParameter("subId", subscriberId);
		
		@SuppressWarnings("unchecked")
		List<LookupListItem> res = query.getResultList();
		return res;
	}
	
	@Override
	public List<LookupListItem> getPropertyList(Integer subscriberId) {
		Query query = em
				.createQuery("select new com.tea.dto.LookupListItem(p.id, p.propertyKey, p.name) from Property p where p.subscriber.id = :subId order by p.propertyKey, p.name");
		query.setParameter("subId", subscriberId);
		@SuppressWarnings("unchecked")
		List<LookupListItem> res = query.getResultList();
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> findPropertiesByManager(int pmId)
			throws DataAccessException {
		User user = findUser(pmId);
		String qry = "select p from Property p where :user member of p.authorizedManagers";

		Query query = em
				.createQuery(qry);
		query.setParameter("user", user);
		return query.getResultList();

	}
	

	
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<Object[]> getPropertyPickList(User user) {
		Query query;
		query = em.createQuery(
				 "select property.id, property.name from Property property");
		return query.getResultList().iterator();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<Object[]> getSubcriberPickList(User user) {
		Query query;
		query = em.createQuery(
				"select subscriber.id, subscriber.name from Subscriber subscriber");
		return query.getResultList().iterator();
	}

	@Override
	public Date getLastPasswordChange(User user) {
		String q = "select dateChanged from PasswordHistory where user = :user order by dateChanged desc";
		Query qry = em.createQuery(q);
		qry.setParameter("user", user);
		qry.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<Date> result = qry.getResultList();
		if (result == null || result.size() == 0) return null;
		
		return result.get(0);
	}

	@Override
	public List<String> getPasswordHistory(Integer userId, int minRememberedPassword) {
		String q = "select password from PasswordHistory where user.id = :userid order by dateChanged desc";
		Query qry = em.createQuery(q);
		qry.setParameter("userid", userId);
		qry.setMaxResults(minRememberedPassword);
		
		@SuppressWarnings("unchecked")
		List<String> result = qry.getResultList();
		
		return result;
	}

	@Override
	public User findUserByUsernameWithCapabilities(String arg0) {
		Query query = em
				.createQuery("select u from User u where u.username = :emailId");
		query.setParameter("emailId", arg0);

		@SuppressWarnings("unchecked")
		List<User> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;
		User user = list.get(0);
		@SuppressWarnings("unused")
		int capCount = user.getRole().getCapabilities().size();

		return user;
	}

	@Override
	public PasswordPolicy findPasswordPolicyByRoleId(Integer roleId) {
		String q = "select r.passwordPolicy from Role r where r.id = :roleId";
		Query query = em.createQuery(q);
		query.setParameter("roleId", roleId);
		
		PasswordPolicy pp = null;
		try {
			pp = (PasswordPolicy) query.getSingleResult();
		} catch (Exception e) {
			// do nothing
		}
		
		return pp;
	}

	@Override
	public List<String> getUsernamesForEmail(String email) {
		String q = "select u.username from User u where u.username = :email";
		Query query = em.createQuery(q);
		query.setParameter("email", email);
		
		@SuppressWarnings("unchecked")
		List<String> results = query.getResultList();
		
		return results;

		
		
	}

	@Override
	public boolean userExists(String username) {
		User user = findUserByUsername(username);
		
		return (user != null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findProperties() {
		Query query = em
				.createQuery("select p.id from Property p");
		return query.getResultList();
	}

	@Override
	public List<User> findEcsrUser(Role role) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property findPropertyByBigId(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
