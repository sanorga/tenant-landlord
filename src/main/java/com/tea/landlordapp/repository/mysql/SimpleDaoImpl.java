package com.tea.landlordapp.repository.mysql;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.AuditableEntity;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.SimpleDao;

@Repository("simpleDao")
@Transactional(readOnly = true)
public class SimpleDaoImpl implements SimpleDao {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 8099832428435629593L;
	
	private transient EntityManager em;

	@Required
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional(readOnly = false)
	public <T> T merge(T entity) {
		return merge(entity, null);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void persist(T entity) {
		persist(entity, null);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> T merge(T entity, User user) {
		setAuditInfo(entity, user);		
		return em.merge(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void persist(T entity, User user) {
		setAuditInfo(entity, user);		
		em.persist(entity);
	}

	@Override
	public <T> T find(Class<T> clazz, Object object) {
		return em.find(clazz, object);
	}
	
	private <T> void setAuditInfo(T entity, User user){
		if (entity instanceof AuditableEntity){
			User logUser = getLogUser(user);
			if (((AuditableEntity)entity).getCreatedBy() == null){
				((AuditableEntity) entity).setCreatedBy(logUser);
			}
			((AuditableEntity) entity).setModifiedBy(logUser);
//			((AuditableEntity) entity).setModifiedDate(null);
		}
	}
	
	private User getLogUser(User user){
		User logUser = user == null? findNullUser(): user;
		return logUser;
	}
	
	private User findNullUser() {
		Query query = em
				.createQuery("select u from User u where u.username = :emailId");
		query.setParameter("emailId", Globals.NULL_USER_EMAIL);

		List<User> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || (list.size() == 0))
			return null;
		User user = list.get(0);

		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void remove(Class<T> clazz, Integer id) {
		T item = em.find(clazz, id);
		if (item != null){
			em.remove(item);
		}
		
	}

}
