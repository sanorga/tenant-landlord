package com.tea.landlordapp.repository;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.dao.DataAccessException;

import com.tea.landlordapp.domain.AnonymousUser;
//import com.tea.landlordapp.domain.AnonymousUser;
import com.tea.landlordapp.domain.Capability;
//import com.tea.landlordapp.domain.IndexAuto;
import com.tea.landlordapp.domain.PasswordPolicy;
//import com.tea.landlordapp.domain.Preference;
import com.tea.landlordapp.domain.Property;
import com.tea.landlordapp.domain.Role;
//import com.tea.landlordapp.domain.SearchTerm;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.domain.Zipcode;
import com.tea.landlordapp.dto.LookupListItem;
import com.tea.landlordapp.dto.SelectionListItem;

public interface UserDao extends Serializable {

   public List<LookupListItem> getClientLookupList(int id) throws DataAccessException;

   public List<LookupListItem> getClientsWithAppsLookupList(int id) throws DataAccessException;

   public List<LookupListItem> getClientsWithAppsLookupList() throws DataAccessException;

   public List<User> findAllUsersByRole(String role) throws DataAccessException;
   
   public User findNullUser() throws DataAccessException;
//
//   public void savePreference(Preference preference) throws DataAccessException;

   public User findUser(int id) throws DataAccessException;

   public List<User> findEcsrUser(Role role) throws DataAccessException;

//   public IndexAuto findIndex() throws DataAccessException;
//
//   public void saveIndex(IndexAuto index) throws DataAccessException;
//
//   public AnonymousUser findAnonymousUser(int id) throws DataAccessException;
//
//   public AnonymousUser findAnonymousUserByApplication(int applicationId) throws DataAccessException;

   public User findUserByUsername(String username) throws DataAccessException;

   public void saveUser(User user) throws DataAccessException;

//   public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser) throws DataAccessException;
//
//   public List<AnonymousUser> findAnonymousUsers() throws DataAccessException;
//
//   public List<AnonymousUser> findAnonymousUsers(int pageNoAUser) throws DataAccessException;

   public Integer countAnonymousUsers() throws DataAccessException;

   public List<Role> findRoles() throws DataAccessException;

   public Role findRole(int id) throws DataAccessException;

   public Role findRole(String id) throws DataAccessException;

   public void saveRole(Role role) throws DataAccessException;

   public List<Capability> findCapabilities() throws DataAccessException;

   public Capability findCapability(int id) throws DataAccessException;

   public void saveCapability(Capability capability) throws DataAccessException;

   public Property findProperty(int id) throws DataAccessException;

   public Property findPropertyByBigId(int id) throws DataAccessException;

   public List<Property> findPropertiesBySubscriber(int subscriberId, User user) throws DataAccessException;

   public List<Property> findPropertiesByManager(User user) throws DataAccessException;

   public List<Property> findPropertiesByManager(int pmId) throws DataAccessException;

   public Property findPropertyByKey(int subscriberId, String propertyKey) throws DataAccessException;

   public void saveProperty(Property property) throws DataAccessException;

   public Zipcode findZipcode(String zipcode);

   public void clearHibernateEntityManager() throws DataAccessException;

   public List<String> findSalesPeople(int id) throws DataAccessException;

   public TreeMap<Integer, String> getClientDisplayListByParentId(int id);

   public List<Integer> getClientIdsByParent(int id);

   public List<SimpleEntry<String,String>> getClientEmaiListByParent(int id);

   public List<SelectionListItem> getClientSelectionList(int id);

	public List<Property> findAuthorizedProperties(User user);
	
	public List<LookupListItem> getPropertyManagerList(Integer subscriberId);
	
	public List<LookupListItem> getBoardDirectorList(Integer subscriberId);
	
	public Iterator<Object[]> getPropertyPickList(User user);
	
	public Iterator<Object[]> getSubcriberPickList(User user);
	
	public Date getLastPasswordChange(User user);

	public List<String> getPasswordHistory(Integer userId, int minRememberedPassword);

	public User findUserByUsernameWithCapabilities(String arg0);

	public PasswordPolicy findPasswordPolicyByRoleId(Integer roleId);

	public List<String> getUsernamesForEmail(String email);

	public boolean userExists(String username);

	public User findUserWithPolicy(String username);
	
	public List<Integer> findProperties();

	public List<LookupListItem> getApplicationApprovalUserList(Integer subscriberId, java.util.List<Integer> authorizedManagerIds);
	
	public List<LookupListItem> getPropertyList(Integer subscriberId);

	public List<Property> findPropertiesBySubscriberForRebateRpt(int subscriberId, User user);

	public User findUser(String role);

	public Property findPropertyByName(String name);

	public AnonymousUser saveAnonymousUser(AnonymousUser anonymousUser);

	public Property findPropertyById(int propertyId);

	public User findApiUser() throws DataAccessException;
}