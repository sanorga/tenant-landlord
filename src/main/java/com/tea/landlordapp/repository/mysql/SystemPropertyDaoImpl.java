package com.tea.landlordapp.repository.mysql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tea.landlordapp.domain.SystemProperty;
import com.tea.landlordapp.enums.EncryptionPropertyParameter;
import com.tea.landlordapp.enums.TransUnionApiParameter;
import com.tea.landlordapp.repository.SystemPropertyDao;

@Repository("systemPropertyDao")
@Transactional(readOnly = true)
public class SystemPropertyDaoImpl implements
		SystemPropertyDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -656485551489550233L;
	private transient EntityManager em;

	@Required
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public String getPropertyValue(String propertyName) {
		if (StringUtils.isBlank(propertyName)) return null;
		
		Query q = em.createQuery("select p.value from SystemProperty p where p.name = :propertyName");
		q.setParameter("propertyName", propertyName);
		try {
			String sp = (String)q.getSingleResult();
			return sp;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public String getPropertyValue(EncryptionPropertyParameter param) {
		return getPropertyValue(param.getLabel());
	}
	
	@Override
	public String getPropertyValue(TransUnionApiParameter param) {
		return getPropertyValue(param.getLabel());
	}

}
