package com.tea.landlordapp.repository.mysql;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tea.landlordapp.domain.Applicant;
import com.tea.landlordapp.repository.ApplicantDao;

@Repository("applicantDao")
@Transactional(readOnly = true)
public class ApplicantDaoImpl implements ApplicantDao {

	private transient EntityManager em;

	@Required
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Applicant> findDuplicateApplicant(Integer propertyId, String pseudoId, int daysOld) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_YEAR, -1 * daysOld);
		Date dt = cal.getTime();

		String q = "select distinct a from Applicant a where a.pseudoId = :pseudoId and a.application.property.id = :propertyId and a.application.createdDate > :dt";
		Query qry = em.createQuery(q);
		qry.setParameter("pseudoId", pseudoId);
		qry.setParameter("propertyId", propertyId);
		qry.setParameter("dt", dt, TemporalType.TIMESTAMP);
		
		@SuppressWarnings("unchecked")
		List<Applicant> results = qry.getResultList();
		
		return results;
	}

}
