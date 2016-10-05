package com.tea.landlordapp.service;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.javatuples.KeyValue;
import org.springframework.dao.DataAccessException;

import com.tea.landlordapp.domain.AnonymousUser;
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

public interface UserService extends Serializable {

	public void clearHibernateEntityManager();

	public Integer countAnonymousUsers();

//	public Integer countClients(Subscriber subscriber);
//
//	public List<Subscriber> findActiveClients(int id);
//
//	public List<Subscriber> findAllClients();
//
//	public List<Subscriber> findAllPartners();

	public List<User> findAllUsersByRole(String Role);

//	public AnonymousUser findAnonymousUser(int id);
//
//	public AnonymousUser findAnonymousUserByApplication(int applicationId);
//
//	public List<AnonymousUser> findAnonymousUsers();
//
//	public List<AnonymousUser> findAnonymousUsers(int pageNoAUser);

	public List<Capability> findCapabilities();

	public HashMap<Integer, String> findCapabilitiesAsMap();

	public Capability findCapability(int id);

//	public Subscriber findClientByName(String name);

//	public List<Subscriber> findClients(int id);
//
//	public List<Subscriber> findClients(int id, String condition,
//			List<SearchTerm> searchTerms);
//
//	public List<Subscriber> findClients(Subscriber client, Integer pageNo,
//			Integer sortBy, String sortType);
//
//	public IndexAuto findIndex();

	public User findNullUser();

//	public List<Subscriber> findPartners(int id, String condition,
//			List<SearchTerm> searchTerms);

	public List<Property> findPropertiesBySubscriber(int subscriberId, User user);

	public List<Property> findPropertiesByManager(User user);

	public Property findProperty(int id);

	// public Property findPropertyByBigId(int id);

	public Property findPropertyByKey(int subscriberId, String propertyKey);

	public Role findRole(int id);

	public Role findRole(String id);

	public List<Role> findRoles();

	public List<String> findSalesPeople(int id);

//	public Subscriber findSubscriber(int id);
//
//	public Subscriber findSubscriberByName(String name);
//
//	public Subscriber findSubscriberByShortName(String shortName);

//	public User findUser(int id);

//	public User findUser(String email);

//	public User findUser(Subscriber sub, String role);

//	public List<UserGridItem> findUserGridList(Subscriber sub)
//			throws DataAccessException;
//
//	public List<User> findUserList(Subscriber sub) throws DataAccessException;

	// public Zipcode findZipcode(String id);

	public TreeMap<Integer, String> getClientDisplayListByParentId(int id);

	public List<SimpleEntry<String, String>> getClientEmaiListByParent(int id);

	public List<Integer> getClientIdsByParent(int id);

	public List<LookupListItem> getClientLookupList(Integer id);

//	public List<Subscriber> getClients(User user, Subscriber subscriber);

	public List<LookupListItem> getClientsWithAppsLookupList();

	public List<LookupListItem> getClientsWithAppsLookupList(Integer id);
	
//	public HashMap<Integer, String> getPropertyManagerList(Integer subscriberId);
//
//	public HashMap<Integer, String> getBoardDirectorList(Integer subscriberId);
//
//	public Subscriber getNewClient(User loginUser);
//
//	public Subscriber getNewPartner(User loginUser);
//
//	public User getNewUser(Subscriber parent);

//	public List<User> getUsers(User user, Subscriber subscriber);

//	public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser,
//			User loginUser);

	public void saveCapability(Capability capability, User loginUser);

//	public void saveIndex(IndexAuto index);

//	public void savePreference(Preference preference, User loginUser);

	public void saveProperty(Property property, User loginUser);

	public void saveRole(Role role, User loginUser);

//	public void saveServices(Subscriber subscriber, User loginUser);
//
//	public void saveSubscriber(Subscriber subscriber, User loginUser);

	public void saveUser(User user, User loginUser);

	public User validateUser(String email, String password);

	public User validateUserByUsername(String username);

//	public List<SelectionListItem> getClientSelectionList(int id);

	public List<Property> findAuthorizedProperties(User user);

//	public User setupNewUser(Subscriber parent);

	public HashMap<Integer, String> getApplicationApprovalUserList(Integer subscriberId, java.util.List<Integer> authorizedManagerIds);

	public HashMap<Integer, String> getPropertyList(Integer subscriberId);

//	public List<User> getSubscriberCaUsers(Subscriber subscriber);
//
//	public ManagementCompany findManagementCompanyByName(String name);
//
//	public ManagementCompany findManagementCompanyById(int managementCompanyId);
	
	public Property saveNewProperty(Property property, User loginUser);

	public User setupNewUser();

	public Property findPropertyByName(String name);

	public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser, User loginUser);

	public Property findPropertyById(int propertyId);

//	public Subscriber saveNewSubscriber(Subscriber subscriber, User loginUser);
	
	
}