package com.tea.landlordapp.service;

import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.javatuples.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Applicant;
//import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Capability;
//import com.tea.landlordapp.domain.IndexAuto;
//import com.tea.landlordapp.domain.ManagementCompany;
//import com.tea.landlordapp.domain.Preference;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.Role;
//import com.tea.landlordapp.domain.SearchTerm;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.LookupListItem;
import com.tea.landlordapp.dto.SelectionListItem;
//import com.tea.landlordapp.dto.UserGridItem;
//import com.tea.landlordapp.enums.CustomerStatus;
import com.tea.landlordapp.enums.UserRole;
//import com.tea.landlordapp.repository.DtoHelperDao;
import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final long serialVersionUID = 3219550845709344766L;

	@Autowired
	private UserDao userDao;

//	@Autowired
//	private DtoHelperDao dtoHelperDao;
	
	@Autowired
	private SimpleDao simpleDao;
	

	@Transactional
	@Override
	public void clearHibernateEntityManager() {
		userDao.clearHibernateEntityManager();
	}

	@Override
	public Integer countAnonymousUsers() {
		return userDao.countAnonymousUsers();
	}

	@Override
	public List<User> findAllUsersByRole(String role) {
		return userDao.findAllUsersByRole(role);
	}

//	@Override
//	public AnonymousUser findAnonymousUser(int id) {
//		return userDao.findAnonymousUser(id);
//	}
//
//	@Override
//	public AnonymousUser findAnonymousUserByApplication(int applicationId) {
//		return userDao.findAnonymousUserByApplication(applicationId);
//	}
//
//	@Override
//	public List<AnonymousUser> findAnonymousUsers() {
//		return userDao.findAnonymousUsers();
//	}
//
//	@Override
//	public List<AnonymousUser> findAnonymousUsers(int pageNoAUser) {
//		return userDao.findAnonymousUsers(pageNoAUser);
//	}

	@Override
	public Property findPropertyByName(String name) {
		return userDao.findPropertyByName(name);
	}
	
	@Override
	public Property findPropertyById(int propertyId) {
		return userDao.findPropertyById(propertyId);
	}
	@Override
	public List<Capability> findCapabilities() {
		return userDao.findCapabilities();
	}

	@Override
	public HashMap<Integer, String> findCapabilitiesAsMap() {
		final List<Capability> capabilities = findCapabilities();
		final HashMap<Integer, String> map = new HashMap<Integer, String>(
				capabilities.size());
		for (final Capability c : capabilities) {
			map.put(c.getId(), c.getName());
		}
		return map;
	}

	@Override
	public Capability findCapability(int id) {
		return userDao.findCapability(id);
	}

//	@Override
//	public IndexAuto findIndex() {
//		return userDao.findIndex();
//	}

	@Override
	public User findNullUser() {
		return userDao.findUserByUsername(Globals.NULL_USER_EMAIL);
	}

	@Override
	public List<Property> findPropertiesBySubscriber(int subscriberId, User user) {
		return userDao.findPropertiesBySubscriber(subscriberId, user);
	}

	@Override
	public List<Property> findPropertiesByManager(User user) {
		return userDao.findPropertiesByManager(user);
	}

	@Override
	public Property findProperty(int id) {
		return userDao.findProperty(id);
	}

	// @Override
	// public Property findPropertyByBigId(int id) {
	// return userDao.findPropertyByBigId(id);
	// }

	@Override
	public Property findPropertyByKey(int subscriberId, String propertyKey) {
		return userDao.findPropertyByKey(subscriberId, propertyKey);
	}

	@Override
	public Role findRole(int id) {
		return userDao.findRole(id);
	}

	@Override
	public Role findRole(String id) {
		return userDao.findRole(id);
	}

	@Override
	public List<Role> findRoles() {
		return userDao.findRoles();
	}

	@Override
	public List<String> findSalesPeople(int id) {
		return userDao.findSalesPeople(id);
	}



//	@Override
//	public User findUser(int id) {
//		return userDao.findUser(id);
//	}

//	@Override
//	public User findUser(String email) {
//		return userDao.findUserByEmail(email);
//	}

//	@Override
//	public User findUser(Subscriber sub, String role) {
//		return userDao.findUser(sub, role);
//	}

//	@Override
//	public List<UserGridItem> findUserGridList(Subscriber sub)
//			throws DataAccessException {
//		return dtoHelperDao.findUserGridList(sub);
//	}


	//
	// @Override
	// public Zipcode findZipcode(String zipcode) {
	// return userDao.findZipcode(zipcode);
	// }

	@Override
	public TreeMap<Integer, String> getClientDisplayListByParentId(int id) {
		return userDao.getClientDisplayListByParentId(id);
	}

	@Override
	public List<SimpleEntry<String, String>> getClientEmaiListByParent(int id) {
		return userDao.getClientEmaiListByParent(id);
	}

	@Override
	public List<Integer> getClientIdsByParent(int id) {
		return userDao.getClientIdsByParent(id);
	}

	@Override
	public List<LookupListItem> getClientLookupList(Integer id) {
		return userDao.getClientLookupList(id);
	}


	@Override
	public List<LookupListItem> getClientsWithAppsLookupList() {
		return userDao.getClientsWithAppsLookupList();
	}

	@Override
	public List<LookupListItem> getClientsWithAppsLookupList(Integer id) {
		return userDao.getClientsWithAppsLookupList(id);
	}



//	@Override
//	public List<User> getUsers(User user, Subscriber subscriber) {
//
//		List<User> users = new ArrayList<User>();
//		if (!ObjectUtils.equals(subscriber, null)) {
//			users = userDao.findAllUsersByRole(subscriber);
//			return users;
//		}
//		return users;
//	}

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {

		final UserDetails user = userDao.findUserByUsername(arg0);
		return user;
	}

	@Override
	public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser,
			User loginUser) {
		// get the current timestamp
		final Timestamp ts = new Timestamp(System.currentTimeMillis());
			anonymousUser.setAuditInfo(loginUser);


		return userDao.saveAnonymousUser(anonymousUser);
	}

	@Override
	public void saveCapability(Capability capability, User loginUser) {

		capability.setAuditInfo(loginUser);
		userDao.saveCapability(capability);
	}

//	@Override
//	public void saveIndex(IndexAuto index) {
//		userDao.saveIndex(index);
//	}
//
//	@Override
//	public void savePreference(Preference preference, User loginUser) {
//			preference.setAuditInfo(loginUser);
//
//		userDao.savePreference(preference);
//		return;
//	}

	@Override
	public Property saveNewProperty(Property property, User loginUser) {
			property.setAuditInfo(loginUser);

		property = simpleDao.merge(property);
		return property;
	}

	@Override
	public void saveProperty(Property property, User loginUser) {
			property.setAuditInfo(loginUser);

		userDao.saveProperty(property);
	}

	@Override
	public Application saveNewApplication(Application application, User loginUser) {
			application.setAuditInfo(loginUser);
			application.setStatus("Submitted");
		application = simpleDao.merge(application);
		return application;
	}
	
	@Override
	public void saveRole(Role role, User loginUser) {
			role.setAuditInfo(loginUser);

		userDao.saveRole(role);
	}

	@Override
	public void saveUser(User user, User loginUser) {

		user.setAuditInfo(loginUser);
		userDao.saveUser(user);
		return;
	}

	@Override
	public User validateUser(String email, String password) {
		final User user = userDao.findUserByUsername(email);
		if (ObjectUtils.equals(user, null)) {
			return null;
		}

		if (!user.passwordMatches(password)) {
			return null;
		}

		return user;
	}

	@Override
	public User validateUserByUsername(String email) {
		final User user = userDao.findUserByUsername(email);
		if (ObjectUtils.equals(user, null)) {
			return null;
		}

		return user;
	}

	@Override
	public List<Property> findAuthorizedProperties(User user) {
		return userDao.findAuthorizedProperties(user);
	}

//	@Override
//	public HashMap<Integer, String> getPropertyManagerList(Integer subscriberId) {
//		final List<LookupListItem> items = userDao.getPropertyManagerList(subscriberId);
//		HashMap<Integer, String> map = new HashMap<Integer, String>(items.size());
//		for (LookupListItem item: items){
//			map.put(item.getId(), item.getName());
//		}
//		
//		return map;
//	}

	@Override
	public HashMap<Integer, String> getPropertyList(Integer subscriberId) {
		final List<LookupListItem> items = userDao.getPropertyList(subscriberId);
		HashMap<Integer, String> map = new HashMap<Integer, String>(items.size());
		for (LookupListItem item: items){
			map.put(item.getId(), item.getName());
		}
		
		return map;
	}

//	@Override
//	public HashMap<Integer, String> getBoardDirectorList(Integer subscriberId) {
//		final List<LookupListItem> items = userDao.getBoardDirectorList(subscriberId);
//		HashMap<Integer, String> map = new HashMap<Integer, String>(items.size());
//		for (LookupListItem item: items){
//			map.put(item.getId(), item.getName());
//		}
//		
//		return map;
//	}

	@Override
	public HashMap<Integer, String> getApplicationApprovalUserList(Integer subscriberId, java.util.List<Integer> authorizedManagerIds) {
		final List<LookupListItem> items = userDao.getApplicationApprovalUserList(subscriberId, authorizedManagerIds);
		HashMap<Integer, String> map = new HashMap<Integer, String>(items.size());
		for (LookupListItem item: items){
			map.put(item.getId(), item.getName());
		}
		
		return map;
	}
	
//	@Override
//	public HashMap<Integer, String> findCapabilitiesAsMap() {
//		final List<Capability> capabilities = findCapabilities();
//		final HashMap<Integer, String> map = new HashMap<Integer, String>(
//				capabilities.size());
//		for (final Capability c : capabilities) {
//			map.put(c.getId(), c.getName());
//		}
//		return map;
//	}
	
//	@Override
//	public ManagementCompany findManagementCompanyByName(String name) {
//		return userDao.findManagementCompanyByName(name);
//	}
//	
//	@Override
//	public ManagementCompany findManagementCompanyById(int managementCompanyId) {
//		return userDao.findManagementCompanyById(managementCompanyId);
//	}
	
	@Override
	public User setupNewUser() {
		User user = new User();
		user.setStatus(Globals.CHANGEPWD);


		return user;
	}
	@Override
	public User setupNewUser(String type) {
		User user = new User();
//		user.setSubscriber(parent);
		user.setState("FL");
		user.setCountry("US");
		user.setStatus(Globals.ACTIVE);
		Role initialRole = new Role();
		initialRole = findRole(UserRole.CustomerServiceRep.getCode());
//		if (parent.isPartner()){
//			initialRole = findRole(UserRole.CustomerServiceRep.getCode());
//		} else {
//			initialRole = findRole(UserRole.ClientUser.getCode());
//		}
        user.setRole(initialRole);
		return user;
	}
}