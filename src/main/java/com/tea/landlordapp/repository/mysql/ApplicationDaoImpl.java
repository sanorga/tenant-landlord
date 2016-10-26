package com.tea.landlordapp.repository.mysql;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;
//import com.tea.landlordapp.constant.FileStorageBeans;
import com.tea.landlordapp.constant.Globals;
import com.tea.landlordapp.domain.Applicant;
//import com.tea.landlordapp.domain.Applicant2Service;
import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.SearchTerm;
import com.tea.landlordapp.domain.User;
//import com.tea.landlordapp.domain.Application2BoardMembers;
//import com.tea.landlordapp.domain.ApplicationCharge;
//import com.tea.landlordapp.domain.ApplicationInProcess;
//import com.tea.landlordapp.domain.ApplicationPriceChange;
//import com.tea.landlordapp.domain.ApplicationTask;
//import com.tea.landlordapp.domain.BankReferenceVerification;
//import com.tea.landlordapp.domain.BasicBgVerification;
//import com.tea.landlordapp.domain.ClientApplication;
//import com.tea.landlordapp.domain.Contact;
//import com.tea.landlordapp.domain.CrimeWatchVerification;
//import com.tea.landlordapp.domain.DocumentContainer;
//import com.tea.landlordapp.domain.DrivingLicenseVerification;
//import com.tea.landlordapp.domain.Dt5pVerification;
//import com.tea.landlordapp.domain.EmpCreditVerification;
//import com.tea.landlordapp.domain.Employment;
//import com.tea.landlordapp.domain.EmploymentVerification;
//import com.tea.landlordapp.domain.Event;
//import com.tea.landlordapp.domain.EvictionsVerification;
//import com.tea.landlordapp.domain.ExtensBankVerification;
//import com.tea.landlordapp.domain.ExtensiveBgVerification;
//import com.tea.landlordapp.domain.FdleVerification;
//import com.tea.landlordapp.domain.Financial;
//import com.tea.landlordapp.domain.IntBGCVerification;
//import com.tea.landlordapp.domain.InvoicePayment;
//import com.tea.landlordapp.domain.ManualReport;
//import com.tea.landlordapp.domain.Occupant;
//import com.tea.landlordapp.domain.Payment;
//import com.tea.landlordapp.domain.PersonalReferenceVerification;
//import com.tea.landlordapp.domain.Pet;
//import com.tea.landlordapp.domain.PhysicalAbilityVerification;
//import com.tea.landlordapp.domain.QboTransaction;
//import com.tea.landlordapp.domain.QboTransactionDetail;
//import com.tea.landlordapp.domain.Recommendation;
//import com.tea.landlordapp.domain.Reference;
//import com.tea.landlordapp.domain.Report;
//import com.tea.landlordapp.domain.Residence;
//
//import com.tea.landlordapp.domain.SsnVerification;
//import com.tea.landlordapp.domain.Subscriber;
//import com.tea.landlordapp.domain.Subscriber2Service;
//import com.tea.landlordapp.domain.TenantVerification;
//
//import com.tea.landlordapp.domain.Vehicle;
//import com.tea.landlordapp.domain.VehicleRegistrationVerification;
//import com.tea.landlordapp.dto.ApplicationChargeGridRowDto;
//import com.tea.landlordapp.dto.ApplicationDepositGridRowDto;
//import com.tea.landlordapp.dto.ApplicationGridRowDto;
//import com.tea.landlordapp.dto.ApplicationGridRowStatusTipDto;
//import com.tea.landlordapp.dto.ApplicationPaymentGridRowDto;
//import com.tea.landlordapp.dto.ItemFlag;
//import com.tea.landlordapp.dto.JqgridData;
//import com.tea.landlordapp.dto.JqgridFilter;
//import com.tea.landlordapp.dto.JqgridFilterRule;
//import com.tea.landlordapp.dto.JqgridRow;
//import com.tea.landlordapp.dto.QbTransactionDetailGridRowDto;
//import com.tea.landlordapp.dto.Sequence;
//import com.tea.landlordapp.enums.ApplicationStatus;
//import com.tea.landlordapp.enums.AuthSigningStatus;
//import com.tea.landlordapp.enums.ReportStatus;
//import com.tea.landlordapp.enums.SigningStatus;
//import com.tea.landlordapp.exception.RecordNotFoundException;
import com.tea.landlordapp.repository.ApplicationDao;
import com.tea.landlordapp.utility.EncryptionService;
//import com.tea.landlordapp.utility.JqgHelper;

@Repository("applicationDao")
@Transactional(readOnly = true)
public class ApplicationDaoImpl implements ApplicationDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());

	private transient EntityManager em;
	
	// date format
	SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	private Map<Integer, String> viewApplicationsSortOptions;
	private Map<Integer, String> viewInvoicePaymentsSortOptions;

	@Required
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional(readOnly = false)
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void persist(T entity) {
		em.persist(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void clearHibernateEntityManager() {
		em.clear();
	}

	@Override
	public Integer countAllApplications() throws DataAccessException {
		return countAllApplications(null, null);
	}

	@Override
	public Integer countAllApplications(String condition,
			List<SearchTerm> searchTerms) throws DataAccessException {
		return countApplications(
				"Select Count (a) from Application a where 1 = :id and a.statusCode != 'D' and a.statusCode != 'L' ",
				1, condition, searchTerms);
	}
	
	private Integer countApplications(String startSql, Integer id,
			String condition, List<SearchTerm> searchTerms){
		return countApplications(startSql, id, condition, searchTerms, null);
	}

	private Integer countApplications(String startSql, Integer id,
			String condition, List<SearchTerm> searchTerms, User user) {
		if (StringUtils.isBlank(condition)) {
			condition = "and";
		}

		// String addlQuery = " ";
		final StringBuilder build = new StringBuilder(" ");
		if (!ObjectUtils.equals(searchTerms, null) && searchTerms.size() > 0) {
			try {
				int countSearchTerm = 0;
				for (final SearchTerm s : searchTerms) {
					if (++countSearchTerm == 1) {
						build.append("AND");
					} else {
						build.append(condition);
					}
					if (s.getCriteria().contains("].lastName")) {
						final String encLN = s.getValueAsString();
						build.append(" a.id in (select application.id from Applicant ap where ap.lastName "
								+ s.getModifier() + " " + encLN + ") ");
					} else if (s.getCriteria().contains("Date")) {
						build.append(" " + "date_format(" + s.getCriteria()
								+ ", '%m/%d/%Y') " + s.getModifier() + " "
								+ s.getValueAsString() + " ");
					} else {
						build.append(" " + s.getCriteria() + " "
								+ s.getModifier() + " " + s.getValueAsString()
								+ " ");
					}
				}
			} catch (final Exception ex) {
				logger.error("Unable to create additional query...");
				// addlQuery = "";
				build.append("");
			}
		}
		final String addlQuery = build.toString();
		logger.debug("countApplications query is.. " + startSql + addlQuery);
		final Query query = em.createQuery(startSql + addlQuery);
		query.setParameter("id", id);
		if (user != null)
			query.setParameter("user", user);
		return ((Long) query.getSingleResult()).intValue();
	}
//	
//	@Override
//	public Integer countApplicationsByClient(int clientId) {
//		return countApplicationsByClient(clientId, null, null);
//	}
//
//	@Override
//	public Integer countApplicationsByClient(int id, String condition,
//			List<SearchTerm> searchTerms) {
//		return countApplications(
//				"Select Count (a) from Application a where a.client.id = :id  and a.statusCode in ('C', 'S', 'P') and a.status2 is NULL",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countApplicationsByManager(int clientId, User user) {
//		return countApplicationsByManager(clientId, null, null, user);
//	}
//
//	@Override
//	public Integer countApplicationsByManager(int id, String condition,
//			List<SearchTerm> searchTerms, User user) {
//		return countApplications(
//				"Select Count (a) from Application a where a.client.id = :id  and a.statusCode in ('C', 'S', 'P') and a.status2 is NULL and :user member of a.property.authorizedManagers",
//				id, condition, searchTerms, user);
//	}
//
//	@Override
//	public Integer countApplicationsByCSR(int id) throws DataAccessException {
//		return countApplicationsByCSR(id, null, null);
//	}
//
//	@Override
//	public Integer countApplicationsByCSR(int id, String condition,
//			List<SearchTerm> searchTerms) throws DataAccessException {
//		return countApplications(
//				"Select Count (a) from Application a where a.csr.id = :id and a.statusCode in ('P', 'S')",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countApplicationsByECSR(int id) throws DataAccessException {
//		return countApplicationsByECSR(id, null, null);
//	}
//
//	@Override
//	public Integer countApplicationsByECSR(int id, String condition,
//			List<SearchTerm> searchTerms) throws DataAccessException {
//		return countApplications(
//				"Select Count (a) from Application a where a.externalCsr.id = :id and a.statusCode in ('P', 'S')",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countApplicationsByInvestigator(int id)
//			throws DataAccessException {
//		return countApplicationsByInvestigator(id, null, null);
//	}
//
//	@Override
//	public Integer countApplicationsByInvestigator(int id, String condition,
//			List<SearchTerm> searchTerms) throws DataAccessException {
//		return countApplications(
//				"Select Count (a) from Application a where a.investigator.id = :id and a.statusCode in ('P', 'S')",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countApplicationsByPartner(int id)
//			throws DataAccessException {
//		return countApplicationsByPartner(id, null, null);
//	}
//
//	@Override
//	public Integer countApplicationsByPartner(int id, String condition,
//			List<SearchTerm> searchTerms) throws DataAccessException {
//		return countApplications(
//				"Select Count (a) from Application a where a.client.parent.id = :id  and a.statusCode != 'D' and a.statusCode != 'L'",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countInvoicePaymentsByClient(Subscriber client) {
//		final Query query = em
//				.createQuery("Select count(ip) FROM InvoicePayment ip WHERE ip.client.id = :clientId");
//		query.setParameter("clientId", client.getId());
//
//		return ((Long) query.getSingleResult()).intValue();
//	}
//
//	@Override
//	public Integer countInvoicePaymentsByPartner(Subscriber partner) {
//		final Query query = em
//				.createQuery("Select count(ip) FROM InvoicePayment ip WHERE ip.client.parent.id = :partnerId");
//		query.setParameter("partnerId", partner.getId());
//
//		return ((Long) query.getSingleResult()).intValue();
//	}

//	@Override
//	public Integer countStatusDApplicationsByInvestigator(int id) {
//		return countStatusDApplicationsByInvestigator(id, null, null);
//	}
//
//	@Override
//	public Integer countStatusDApplicationsByInvestigator(int id,
//			String condition, List<SearchTerm> searchTerms) {
//		return countApplications(
//				"Select Count (a.application) from AnonymousUser a where a.application.investigator.id = :id  and a.application.statusCode = 'D'",
//				id, condition, searchTerms);
//	}
//
//	@Override
//	public Integer countStatusDApplicationsByPartner(int id) {
//		return countStatusDApplicationsByPartner(id, null, null);
//	}
//
//	@Override
//	public Integer countStatusDApplicationsByPartner(int id, String condition,
//			List<SearchTerm> searchTerms) {
//		return countApplications(
//				"Select Count (a.application) from AnonymousUser a where a.application.client.parent.id = :id  and a.application.statusCode = 'D'",
//				id, condition, searchTerms);
//	}

	@Override
	@Transactional(readOnly = false)
	public void deleteApplication(Application application) {
		final Query query = em
				.createQuery("delete from Application a where a.id = :applictionId");
		query.setParameter("applictionId", application.getId());
		query.executeUpdate();
	}

//	@Override
//	@Transactional(readOnly = false)
//	public void deleteApplicationInProcess(Integer aipId) {
//		ApplicationInProcess aip = em.find(ApplicationInProcess.class, aipId);
//		deleteApplicationInProcess(aip);
//	}
//	
//	@Override
//	@Transactional(readOnly = false)
//	public void deleteApplicationInProcess(ApplicationInProcess aip) {
//		if (aip != null) {
////		try {
//			logger.debug("In applicationDao Before Deleting  CreatedBY {}", aip.getCreatedBy());
//			logger.debug("Deleting application_in_process: {}", aip.getId());
//			em.remove(aip);
//			logger.debug("After Removing application_in_process: {}", aip.getId());
//			}
////		 catch (HibernateException e) {
////			e.printStackTrace(); 
////		 }
////		}
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void deleteBDApplication(Application application) {
//		final Query query = em
//				.createQuery("delete from Application2BoardMembers a where a.application = :application");
//		query.setParameter("application", application);
//		query.executeUpdate();
//	}
//
//	@Transactional
//	@Override
//	public Integer deleteEvent(Event event) {
//		final Query query = em
//				.createQuery("delete from Event e where e.id = :eventId");
//		query.setParameter("eventId", event.getId());
//		final Integer delVal = query.executeUpdate();
//		return delVal;
//	}
//
//	@Transactional
//	@Override
//	public void deleteInvoicePayment(InvoicePayment invoicePayment) {
//		final Query query = em
//				.createQuery("delete from InvoicePayment ip where ip.id = :invoicePaymentId");
//		query.setParameter("invoicePaymentId", invoicePayment.getId());
//		query.executeUpdate();
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findAllApplicationByECSRs(User usr, Date date,
			String userType) {
		Query query = null;
		if (StringUtils.equals(userType, "EC")) {
			query = em
					.createQuery("select a from Application a where a.externalCsr = :usr and a.statusCode not in ('D', 'M', 'R', 'L') ");
		} else if (StringUtils.equals(userType, "CS")) {
			query = em
					.createQuery("select a from Application a where a.csr = :usr and a.statusCode not in ('D', 'M', 'R', 'L') ");
		}
		query.setParameter("usr", usr);
		// query.setParameter("createdDate", YYYYMMDD.format(date));
		final List<Application> list = query.getResultList();
		if (ObjectUtils.equals(list, null) || list.size() == 0) {
			return null;
		}

		return list;
	}

	@Override
	public List<Application> findAllApplications(Integer pageNo,
			Integer sortBy, String sortType) throws DataAccessException {
		return findAllApplications(null, null, pageNo, sortBy, sortType);
	}

	@Override
	public List<Application> findAllApplications(String condition,
			List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy,
			String sortType) throws DataAccessException {
		String hqlQuery = "select a from Application a";
		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
			// sort by firstName, lastName, ssn
			hqlQuery += ", Applicant app ";
		}
		return findApplications(hqlQuery
				+ " where 1 = :id and a.statusCode != 'D' and a.statusCode != 'L' ", 1, condition,
				searchTerms, pageNo, sortBy, sortType);
	}

	@Override
	public Applicant findApplicant(int id) {
		return em.find(Applicant.class, id);
	}

	@Override
	public Applicant findApplicantWithApplication(int id) {
		Applicant ap = findApplicant(id);
		if (ap == null) return null;
		@SuppressWarnings("unused")
		// guarantees application is retrieved
		Application a = ap.getApplication();
		return ap;
	}

//	@Override
//	public Applicant findApplicantWithResidence(int id) {
//		Applicant ap = findApplicant(id);
//		if (ap == null) return null;
//		
//		// guarantees residences are retrieved
//		List<Residence> residences = ap.getResidences();
//		@SuppressWarnings("unused")
//		int size = residences.size();
//
//		return ap;
//	}

	@Override
	public Application findApplication(int id) {
		return em.find(Application.class, id);
	}

	@Override
	public Application findApplicationWithApplicants(int id) {
		Query q = em.createQuery("select a from Application a join fetch a.applicants where a.id = :id");
		q.setParameter("id", id);
		List<Application> apps = q.getResultList();
		if (apps.isEmpty()) return null;
		return apps.get(0);
//		Application app= em.find(Application.class, id);
//		int appCount = app.getApplicants().size();
//		return app;
	}
//
//	@Override
//	public Application findApplicationWithApplicantsAndReports(int id) {
//		Application app= em.find(Application.class, id);
//		List<Applicant> applicants = app.getApplicants();
//		for (Applicant applicant : applicants) {
//			List<Report> rpts = applicant.getReports();
//			int rptCount = rpts.size();
//		}
//		return app;
//	}
//
//	@Override
//	public Application findApplicationForFinalReport(int id) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a from Application a");
//		sb.append(" join fetch a.applicants ap");
//		sb.append(" where a.id = :id");
//		Query q = em.createQuery(sb.toString());
//		q.setParameter("id", id);
//		List<Application> apps = q.getResultList();
//		if (apps.isEmpty()) return null;
//		Application app = apps.get(0);
//		for (Applicant applicant : app.getApplicants()) {
//			List<Residence> homes = applicant.getResidences();
//			int resCnt = homes.size();
//			List<Employment> emps = applicant.getEmployments();
//			int empCnt = emps.size();
//			List<Reference> references = applicant.getReferences();
//			int refCnt = references.size();
//			List<Report> rpts = applicant.getReports();
//			for (Report rpt : rpts) {
//				List<Recommendation> recs = rpt.getRecommendations();
//				int recCnt = recs.size();
//			}
//			List<Financial> fins = applicant.getFinancials();
//			int finCnt = fins.size();
//			List<Contact> contacts = applicant.getContacts();
//			int conCnt = contacts.size();
//		}
//		List<ApplicationTask> tasks = app.getApplicationTasks();
//		int tskCnt = tasks.size();
//		List<Occupant> occupants = app.getOccupants();
//		int occCnt = occupants.size();
//		List<Vehicle> vehicles = app.getVehicles();
//		int vehCnt = vehicles.size();
//		List<Pet> pets = app.getPets();
//		int petCnt = pets.size();
//		
//		return app;
//	}
//
////	@Override
////	public Application findApplicationWithApplicantsAndReportsAndTasks(int id) {
////		Application app= em.find(Application.class, id);
////		List<Applicant> applicants = app.getApplicants();
////		for (Applicant applicant : applicants) {
////			List<Report> rpts = applicant.getReports();
////			int rptCount = rpts.size();
////		}
////		int tasks = app.getApplicationTasks().size();
////		return app;
////	}
//	
//	@Override
//	public Application findApplicationWithApplicantsAndPets(int id) {
//		Application app= em.find(Application.class, id);
//		int appCount = app.getApplicants().size();
//		int petCount = app.getPets().size();
//		return app;
//	}
	
//	@Override
//	public Application findApplicationWithApplicationCollectionItems(int id) {
//		Application app= em.find(Application.class, id);
//		int appCount = app.getApplicationCollectionItems().size();
//		return app;
//	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public String findApplication(String ssnNumber) {
//
//		final Calendar cal = Calendar.getInstance();
//		// Subtract 30 days from the calendar
//		cal.add(Calendar.DATE, -30);
//		final Date startDate = new Date();
//		final Date endDate = cal.getTime();
//		String displayDate = "";
//
//		final Query query = em
//				.createQuery("Select a from Applicant a where a.ssn_ein = :ssnNumber and date_format(a.application.submissionDate,'%Y/%m/%d') between :endDate and :startDate order by id desc");
//		try {
//			query.setParameter("ssnNumber", ssnNumber);
//		} catch (final Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		query.setParameter("endDate", YYYYMMDDHHMM.format(endDate));
//		query.setParameter("startDate", YYYYMMDDHHMM.format(startDate));
//		// query.setMaxResults(Globals.SSN_DEFAULT_SET);
//		query.setMaxResults(1);
//
//		final List<Applicant> listApplicant = query.getResultList();
//		if (listApplicant.size() > 0) {
//			displayDate = YYYYMMDDHHMM.format(listApplicant.get(0)
//					.getApplication().getSubmissionDate());
//			return displayDate;
//		} else {
//			return displayDate;
//		}
//
//	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Application2BoardMembers> findApplication2BoardMembers(
//			Application application) {
//		final Query query = em
//				.createQuery("SELECT a FROM Application2BoardMembers a WHERE a.application =:application");
//		query.setParameter("application", application);
//
//		final List<Application2BoardMembers> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Application2BoardMembers> findApplication2BoardMembers(
//			User user, Application application, String status) {
//		final char s = status.charAt(0);
//		final Query query = em
//				.createQuery("SELECT a FROM Application2BoardMembers a WHERE a.user = :user and a.statusCode = :status and a.application =:application");
//		query.setParameter("user", user);
//		query.setParameter("status", s);
//		query.setParameter("application", application);
//
//		final List<Application2BoardMembers> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Application> findApplicationByBM(User user, Character status) {
//		String fltr = "";
//		if (ObjectUtils.equals(status, null))
//			fltr = " and a.statusCode = '" + status.toString() + "'";
//		final Query query = em
//				.createQuery("SELECT a.application FROM Application2BoardMembers a WHERE a.user = :user" + fltr + " and a.application.statusCode = 'C'");
//		query.setParameter("user", user);
//
//		final List<Application> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Application> findApplicationByClientForArchive(
//			Subscriber client, Date fromDate, Date toDate) {
//		final Query query = em
//				.createQuery("SELECT a FROM Application a WHERE a.client = :client and a.statusCode in ('C', 'D') and date_format(a.completionDate,'%Y/%m/%d') between :fromDate and :toDate");
//		query.setParameter("client", client);
//		query.setParameter("fromDate", YYYYMMDD.format(fromDate));
//		query.setParameter("toDate", YYYYMMDD.format(toDate));
//		query.setMaxResults(10);
//
//		final List<Application> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@Override
//	public Application findApplicationByEnvelopeId(String envelopeId) {
//		final Query query = em
//				.createQuery("SELECT a FROM Application a WHERE a.envelopeId = :eId");
//		query.setParameter("eId", envelopeId);
//
//		try {
//			final Application aip = (Application) query.getSingleResult();
//			return aip;
//		} catch (Exception e) {
//			// do nothing
//			return null;
//		}
//	}
//
//	@Override
//	public ClientApplication findClientApplicationByEnvelopeId(String envelopeId) {
//		final Query query = em
//				.createQuery("SELECT a FROM ClientApplication a WHERE a.envelopeId = :eId");
//		query.setParameter("eId", envelopeId);
//
//		try {
//			final ClientApplication aip = (ClientApplication) query.getSingleResult();
//
//			return aip;
//		} catch (Exception e) {
//			// do nothing
//			return null;
//		}
//	}
//
//	public Integer findApplicationCountByClientForExcel(Subscriber client,
//			Date fromDate, Date toDate) {
//		final Query query = em
//				.createQuery("SELECT count(id) FROM Application a WHERE a.client = :client and a.statusCode in ('C', 'D') and date_format(a.completionDate,'%Y/%m/%d') between :fromDate and :toDate ");
//		query.setParameter("client", client);
//		query.setParameter("fromDate", YYYYMMDD.format(fromDate));
//		query.setParameter("toDate", YYYYMMDD.format(toDate));
//
//		return ((Long) query.getSingleResult()).intValue();
//	}
//
//	@Override
//	public ApplicationInProcess findApplicationInProcess(int id) {
//		return em.find(ApplicationInProcess.class, id);
//	}
//
//	@Override
//	public ApplicationInProcess findApplicationInProcess(String key) {
//		final Query query = em
//				.createQuery("SELECT a FROM ApplicationInProcess a WHERE a.key = :key");
//		query.setParameter("key", key);
//
//		final ApplicationInProcess aip = (ApplicationInProcess) query
//				.getSingleResult();
//
//		return aip;
//	}
//
//	@Override
//	public ApplicationInProcess findApplicationInProcessByEmail(String email) {
//		Date lastweek = DateUtils.addDays(new Date(), -30);
//		
//		final Query query = em
//				.createQuery("SELECT a FROM ApplicationInProcess a WHERE a.email1 = :key and a.modifiedDate > :refDate order by a.id desc");
//		query.setParameter("key", email);
//		query.setParameter("refDate", lastweek, TemporalType.DATE);
//
//		@SuppressWarnings("unchecked")
//		List<ApplicationInProcess> aips = query.getResultList();
//		if (!aips.isEmpty()){
//			return aips.get(0);
//		} else {
//			return null;
//		}
//	}
//	
	private List<Application> findApplications(String startSql, Integer id,
			String condition, List<SearchTerm> searchTerms, Integer pageNo,
			Integer sortBy, String sortType){
		return findApplications(startSql, id, condition, searchTerms, pageNo, sortBy, sortType, null);
	}

	@SuppressWarnings("unchecked")
	private List<Application> findApplications(String startSql, Integer id,
			String condition, List<SearchTerm> searchTerms, Integer pageNo,
			Integer sortBy, String sortType, User user) {
		if (StringUtils.isBlank(condition)) {
			condition = "and";
		}

		// String addlQuery = " ";
		final StringBuilder build = new StringBuilder(" ");
		if (!ObjectUtils.equals(searchTerms, null) && searchTerms.size() > 0) {
			try {
				int countSearchTerm = 0;
				for (final SearchTerm s : searchTerms) {
					if (++countSearchTerm == 1) {
						build.append("AND");
					} else {
						build.append(condition);
					}
					if (s.getCriteria().contains("].lastName")) {
						final String encLN = s.getValueAsString();
						build.append(" a.id in (select ap.application.id from Applicant ap where upper(ap.lastName) "
								+ s.getModifier() + " " + encLN + ") ");
					} else if (s.getCriteria().contains("Date")) {
						build.append(" " + "date_format(" + s.getCriteria()
								+ ", '%m/%d/%Y') " + s.getModifier() + " "
								+ s.getValueAsString() + " ");
					} else {
						build.append(" " + s.getCriteria() + " "
								+ s.getModifier() + " " + s.getValueAsString()
								+ " ");
					}
				}
			} catch (final Exception ex) {
				logger.error("Unable to create additional query...");
				build.append("");
			}
		}

		// Append Sort By
		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
			// sort by firstName, lastName, ssn
			// Note: order by a.applications[0].firstName is not supported.
			build.append(" AND app.application.id = a.id AND app.applicantType like 'Primary' order by "
					+ getViewApplicationsSortOptions().get(sortBy)
					+ " "
					+ sortType);
		} else {
			build.append(" order by "
					+ getViewApplicationsSortOptions().get(sortBy) + " "
					+ sortType);
		}
		final String addlQuery = build.toString();
		logger.debug("findApplications query is.. " + startSql + addlQuery);
		final Query query = em.createQuery(startSql + addlQuery);
		query.setFirstResult(pageNo * 10);
		query.setMaxResults(10);
		query.setParameter("id", id);
		if (user != null)
			query.setParameter("user", user);

		return query.getResultList();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Application> findApplicationsByAu() {
//		final Query query = em
//				.createQuery("select au.application from AnonymousUser au");
//		final List<Application> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public Boolean checkIfApplicationIsAu(Integer appId) {
//		Boolean result = false;
//		try {
//			final Query query = em
//					.createQuery("select count(*) from AnonymousUser au where au.application.id = :id");
//			query.setParameter("id", appId);
//			Long cnt = (Long) query.getSingleResult();
//			result = cnt > 0;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return result;
//	}

//	@Override
//	public List<Application> findApplicationsByClient(int clientId,
//			Integer pageNo, Integer sortBy, String sortType) {
//		return findApplicationsByClient(clientId, null, null, pageNo, sortBy,
//				sortType);
//	}
//
//	@Override
//	public List<Application> findApplicationsByClient(int id, String condition,
//			List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy,
//			String sortType) {
//		String hqlQuery = "select a from Application a";
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			hqlQuery += ", Applicant app ";
//		}
//		return findApplications(
//				hqlQuery
//						+ " where a.client.id = :id  and a.statusCode in ('N', 'C', 'S', 'P') and a.status2 is NULL",
//				id, condition, searchTerms, pageNo, sortBy, sortType);
//	}
//	
//	@Override
//	public List<Application> findApplicationsByManager(int clientId,
//			Integer pageNo, Integer sortBy, String sortType, User user) {
//		return findApplicationsByManager(clientId, null, null, pageNo, sortBy,
//				sortType, user);
//	}

//	@Override
//	public List<Application> findApplicationsByManager(int id, String condition,
//			List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy,
//			String sortType, User user) {
//		String hqlQuery = "select a from Application a";
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			hqlQuery += ", Applicant app ";
//		}
//		return findApplications(
//				hqlQuery
//						+ " where a.client.id = :id  and a.statusCode in ('N', 'C', 'S', 'P') and a.status2 is NULL and :user member of a.property.authorizedManagers",
//				id, condition, searchTerms, pageNo, sortBy, sortType, user);
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Application> findApplicationsByClient(Integer clientId) {
//		final Query query = em
//				.createQuery("Select a from Application a where a.client.id = :id  and a.statusCode in ('C', 'S', 'P') and a.status2 is NULL");
//		query.setParameter("id", clientId);
//		return query.getResultList();
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findApplicationsByProperty(int propertyId) {
//		final Query query = em
//				.createQuery("Select a from Application a where a.property.id = :id  and a.statusCode in ('C', 'S', 'P') and a.status2 is NULL");
		final Query query = em
				.createQuery("Select a from Application a where a.property.id = :id ");
		query.setParameter("id", propertyId);
		return query.getResultList();
	}

//	@Override
//	public List<Application> findApplicationsByCSR(int id, Integer pageNo,
//			Integer sortBy, String sortType) throws DataAccessException {
//		return findApplicationsByCSR(id, null, null, pageNo, sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findApplicationsByCSR(int id, String condition,
//			List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy,
//			String sortType) throws DataAccessException {
//		String hqlQuery = "select a from Application a";
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			hqlQuery += ", Applicant app ";
//		}
//		return findApplications(hqlQuery
//				+ " where a.csr.id = :id and a.statusCode in ('P', 'S')", id,
//				condition, searchTerms, pageNo, sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findApplicationsByECSR(int id, Integer pageNo,
//			Integer sortBy, String sortType) throws DataAccessException {
//		return findApplicationsByECSR(id, null, null, pageNo, sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findApplicationsByECSR(int id, String condition,
//			List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy,
//			String sortType) {
//		String hqlQuery = "select a from Application a";
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			hqlQuery += ", Applicant app ";
//		}
//		return findApplications(hqlQuery
//				+ " where a.externalCsr.id = :id and a.statusCode in ('P', 'S')",
//				id, condition, searchTerms, pageNo, sortBy, sortType);
//	}

	@Override
	public List<Application> findApplicationsByInvestigator(int id,
			Integer pageNo, Integer sortBy, String sortType)
			throws DataAccessException {
		return findApplicationsByInvestigator(id, null, null, pageNo, sortBy,
				sortType);
	}

	@Override
	public List<Application> findApplicationsByInvestigator(int id,
			String condition, List<SearchTerm> searchTerms, Integer pageNo,
			Integer sortBy, String sortType) throws DataAccessException {
		String hqlQuery = "select a from Application a";
		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
			// sort by firstName, lastName, ssn
			hqlQuery += ", Applicant app ";
		}
		return findApplications(hqlQuery
				+ " where a.investigator.id = :id and a.statusCode in ('P', 'S')",
				id, condition, searchTerms, pageNo, sortBy, sortType);
	}

//	@Override
//	public List<Application> findApplicationsByPartner(int id, Integer pageNo,
//			Integer sortBy, String sortType) throws DataAccessException {
//		return findApplicationsByPartner(id, null, null, pageNo, sortBy,
//				sortType);
//	}
//
//	@Override
//	public List<Application> findApplicationsByPartner(int id,
//			String condition, List<SearchTerm> searchTerms, Integer pageNo,
//			Integer sortBy, String sortType) throws DataAccessException {
//		String hqlQuery = "select a from Application a";
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			hqlQuery += ", Applicant app ";
//		}
//		return findApplications(hqlQuery
//				+ " where a.client.parent.id = :id  and a.statusCode != 'D' and a.statusCode != 'L'", id,
//				condition, searchTerms, pageNo, sortBy, sortType);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Application> findApplicationsByPartner(Integer partnerId) {
//		final Query query = em
//				.createQuery("Select a from Application a where a.client.parent.id = :id  and a.statusCode in ('C', 'S', 'P') and a.status2 is NULL");
//		query.setParameter("id", partnerId);
//		return query.getResultList();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public BankReferenceVerification findBankReferenceVerificationByReport(
//			int id) {
//		final Query query = em
//				.createQuery("select bv from BankReferenceVerification bv where bv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<BankReferenceVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public BasicBgVerification findBasicBgVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select bbv from BasicBgVerification bbv where bbv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<BasicBgVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public CrimeWatchVerification findCrimeWatchVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ebv from CrimeWatchVerification ebv where ebv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<CrimeWatchVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public DrivingLicenseVerification findDrivingLicenseVerificationByReport(
//			int id) {
//		final Query query = em
//				.createQuery("select dlv from DrivingLicenseVerification dlv where dlv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<DrivingLicenseVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Dt5pVerification findDt5pVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select fv from Dt5pVerification fv where fv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<Dt5pVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public EmpCreditVerification findEmpCreditVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ecv from EmpCreditVerification ecv where ecv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<EmpCreditVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public EmploymentVerification findEmploymentVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ev from EmploymentVerification ev where ev.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<EmploymentVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@Override
//	public Event findEvent(Integer id) {
//		return em.find(Event.class, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Event> findEvents() {
//		final Query query = em.createQuery("from Event e");
//		final List<Event> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public EvictionsVerification findEvictionsVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ev from EvictionsVerification ev where ev.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<EvictionsVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public ExtensBankVerification findExtensBankVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ebv from ExtensBankVerification ebv where ebv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<ExtensBankVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public ExtensiveBgVerification findExtensiveBgVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ebv from ExtensiveBgVerification ebv where ebv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<ExtensiveBgVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public DocumentContainer findDocumentContainerByReport(int id) {
//		final Query query = em
//				.createQuery("select ebv from DocumentContainer ebv where ebv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<DocumentContainer> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public FdleVerification findFdleVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select fv from FdleVerification fv where fv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<FdleVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public IntBGCVerification findIntBGCVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ibgv from IntBGCVerification ibgv where ibgv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<IntBGCVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@Override
//	public InvoicePayment findInvoicePayment(Integer id) {
//		return em.find(InvoicePayment.class, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<InvoicePayment> findInvoicePayments(Date startDate,
//			Date endDate, Subscriber client) {
//		final Query query = em
//				.createQuery("SELECT ip FROM InvoicePayment ip WHERE date_format(ip.startDate,'%Y/%m/%d') = :startDate and date_format(ip.endDate,'%Y/%m/%d') = :endDate and ip.client.id = :clientId");
//
//		query.setParameter("startDate", YYYYMMDD.format(startDate));
//		query.setParameter("endDate", YYYYMMDD.format(endDate));
//		query.setParameter("clientId", client.getId());
//
//		final List<InvoicePayment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<InvoicePayment> findInvoicePaymentsByClient(Subscriber client,
//			Integer pageNo, Integer sortBy, String sortType) {
//		final Query query = em
//				.createQuery("FROM InvoicePayment ip WHERE ip.client.id = :clientId order by "
//						+ getViewInvoicePaymentsSortOptions().get(sortBy)
//						+ " "
//						+ sortType);
//		query.setParameter("clientId", client.getId());
//		query.setFirstResult(pageNo * 10);
//		query.setMaxResults(10);
//		final List<InvoicePayment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<InvoicePayment> findInvoicePaymentsByPartner(
//			Subscriber partner, Integer pageNo, Integer sortBy, String sortType) {
//		final Query query = em
//				.createQuery("FROM InvoicePayment ip WHERE ip.client.parent.id = :partnerId order by "
//						+ getViewInvoicePaymentsSortOptions().get(sortBy)
//						+ " "
//						+ sortType);
//		query.setParameter("partnerId", partner.getId());
//		query.setFirstResult(10);
//		query.setMaxResults(10);
//		final List<InvoicePayment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<InvoicePayment> findInvoices(String startSql, String condition,
//			List<SearchTerm> searchTerms) {
//		Query query = null;
//		StringBuffer sql = new StringBuffer(startSql);
//
//		if (!ObjectUtils.equals(searchTerms, null) && searchTerms.size() > 0) {
//			if (StringUtils.isBlank(condition)) {
//				condition = "and";
//			}
//			sql.append(" and ").append(" (");
//			try {
//				for (final SearchTerm st : searchTerms) {
//					if (st.getCriteria().contains("Date")) {
//						sql.append(" " + "date_format(" + st.getCriteria()
//								+ ", '%m/%d/%Y') " + st.getModifier() + " "
//								+ st.getValueAsString() + " " + condition + " ");
//					} else {
//						sql.append(st.getCriteria()).append(" ")
//								.append(st.getModifier())
//								.append(st.getValueAsString()).append(" ")
//								.append(condition).append(" ");
//					}
//				}
//			} catch (final Exception ex) {
//				logger.error("Unable to create additional query...");
//				sql = new StringBuffer("");
//			}
//			sql.delete(sql.length() - 4, sql.length());
//			sql.append(")");
//			sql.trimToSize();
//
//			logger.debug("SQL..." + sql);
//
//			query = em.createQuery(sql.toString());
//
//		} else {
//			query = em.createQuery(sql.toString());
//		}
//
//		return query.getResultList();
//	}
//
//	@Override
//	public List<InvoicePayment> findInvoicesByClient(Integer clientId,
//			String condition, List<SearchTerm> searchTerms) {
//		return findInvoices(
//				"select ip from InvoicePayment ip where ip.client.id="
//						+ clientId, condition, searchTerms);
//	}
//
//	@Override
//	public List<InvoicePayment> findInvoicesByPartner(Integer partnerId,
//			String condition, List<SearchTerm> searchTerms) {
//		return findInvoices(
//				"select ip from InvoicePayment ip where ip.client.parent.id="
//						+ partnerId, condition, searchTerms);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public PhysicalAbilityVerification findPatVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select fv from PhysicalAbilityVerification fv where fv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<PhysicalAbilityVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Payment findPaymentByReport(Integer applicationId) {
//		final Query query = em
//				.createQuery("select p from Payment p where p.application.id = :applicationId");
//		query.setParameter("applicationId", applicationId);
//
//		final List<Payment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Payment findPaymentByReportAlreadyPosted(Integer applicationId) {
//		final Query query = em
//				.createQuery("select p from Payment p where p.application.id = :applicationId and qbo_id is not null");
//		query.setParameter("applicationId", applicationId);
//
//		final List<Payment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Payment> findPayments() {
//		final Query query = em.createQuery("from Payment p");
//		final List<Payment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Payment> findPayments(Date startDate, Date endDate) {
//		final Query query = em
//				.createQuery("from Payment p WHERE date_format(p.createdDate,'%Y/%m/%d') between :startDate and :endDate");
//		query.setParameter("startDate", YYYYMMDD.format(startDate));
//		query.setParameter("endDate", YYYYMMDD.format(endDate));
//		final List<Payment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Payment> findPaymentsByCreatedDate(Date fromDate, Date toDate) {
//		final Query query = em
//				.createQuery("SELECT p FROM Payment p WHERE date_format(p.createdDate,'%Y/%m/%d') between :fromDate and :toDate");
//
//		query.setParameter("fromDate", YYYYMMDD.format(fromDate));
//		query.setParameter("toDate", YYYYMMDD.format(toDate));
//
//		final List<Payment> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public PersonalReferenceVerification findPersonalReferenceVerificationByReport(
//			int id) {
//		final Query query = em
//				.createQuery("select pv from PersonalReferenceVerification pv where pv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<PersonalReferenceVerification> list = query.getResultList();
//		final PersonalReferenceVerification personalReferenceVerification = new PersonalReferenceVerification();
//
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		for (final com.tea.landlordapp.domain.PersonalReferenceVerification pv : list) {
//			personalReferenceVerification.getPersonalReferenceVerifications()
//					.add(pv);
//		}
//
//		return personalReferenceVerification;
//	}
//
//	@Override
//	public Pet findPet(Integer id) {
//		return em.find(Pet.class, id);
//	}
//
//	@Override
//	public Recommendation findRecommendation(int id) {
//		return em.find(Recommendation.class, id);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Recommendation> findRecommendationsByApplicant(int applicantId) {
//		final Query query = em
//				.createQuery("select r from Recommendation r where r.applicant.id = :id and r.report.status != 'I'");
//		query.setParameter("id", applicantId);
//		return query.getResultList();
//	}
//
//	@Override
//	public Report findReport(int id) {
//		return em.find(Report.class, id);
//	}
//
//	@Override
//	public Report findReportWithRecomendations(int id) {
//		Report rpt = em.find(Report.class, id);
//		if (rpt != null){
//			int recCnt = rpt.getRecommendations().size();
//		}
//		return rpt;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Report findReportByCustomData(String txt) {
//		
//		final Query query = em.createQuery("select r from Report r where r.customData = :customData");
//		query.setParameter("customData", txt);
//		List<Report> report = query.getResultList();
//		
//		if(report != null && report.size() > 0){
//			return report.get(0);
//		}
//		
//		return null;
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Report> findReports(Integer vendorId, Date startDate,
//			Date endDate) {
//		final Query query = em
//				.createQuery("select r from Report r where r.service.vendor.id = :vendorId and date_format(r.applicant.application.completionDate,'%Y/%m/%d') between :startDate and :endDate and r.status != 'I'");
//		query.setParameter("vendorId", vendorId);
//		query.setParameter("startDate", YYYYMMDD.format(startDate));
//		query.setParameter("endDate", YYYYMMDD.format(endDate));
//		return query.getResultList();
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Report> findReportsByApplicant(int applicantId) {
//		final Query query = em
//				.createQuery("select r from Report r where r.applicant.id = :id");
//		query.setParameter("id", applicantId);
//		return query.getResultList();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public SsnVerification findSsnVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select ssnv from SsnVerification ssnv where ssnv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<SsnVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	private List<Application> findStatusDApplications(String startSql,
//			Integer id, String condition, List<SearchTerm> searchTerms,
//			Integer pageNo, Integer sortBy, String sortType) {
//		if (StringUtils.isBlank(condition)) {
//			condition = "and";
//		}
//
//		final StringBuilder build = new StringBuilder(" ");
//
//		if (!ObjectUtils.equals(searchTerms, null) && searchTerms.size() > 0) {
//			try {
//				int countSearchTerm = 0;
//				for (final SearchTerm s : searchTerms) {
//					if (++countSearchTerm == 1) {
//						build.append("AND");
//					} else {
//						build.append(condition);
//					}
//					if (s.getCriteria().contains("].lastName")) {
//						final String encLN = s.getValueAsString();
//						build.append("a.id in (select ap.application.id from Applicant ap where upper(ap.lastName) "
//								+ s.getModifier() + " " + encLN + ")");
//					} else if (s.getCriteria().contains("Date")) {
//						build.append(" " + "date_format(" + s.getCriteria()
//								+ ", '%m/%d/%Y') " + s.getModifier() + " "
//								+ s.getValueAsString() + " ");
//					} else {
//						build.append(" " + s.getCriteria() + " "
//								+ s.getModifier() + " " + s.getValueAsString()
//								+ " ");
//					}
//				}
//			} catch (final Exception ex) {
//				logger.error("Unable to create additional query...");
//				// addlQuery = "";
//				build.append("");
//			}
//		}
//
//		// Append Sort By
//		if (sortBy == 3 || sortBy == 4 || sortBy == 5) {
//			// sort by firstName, lastName, ssn
//			// Note: order by a.applications[0].firstName is not supported.
//			build.append(" AND app.application.id = a.id AND app.applicantType like 'Primary' order by "
//					+ getViewApplicationsSortOptions().get(sortBy)
//					+ " "
//					+ sortType);
//		} else {
//			build.append(" order by a.application.submissionDate Desc");
//		}
//		final String addlQuery = build.toString();
//
//		logger.debug("findApplications query is.. " + startSql + addlQuery);
//		final Query query = em.createQuery(startSql + addlQuery);
//		query.setFirstResult(pageNo * 10);
//		query.setMaxResults(10);
//		query.setParameter("id", id);
//		return query.getResultList();
//	}
//
//	@Override
//	public List<Application> findStatusDApplicationsByInvestigator(int userId,
//			Integer pageNo, Integer sortBy, String sortType) {
//		return findStatusDApplicationsByInvestigator(userId, null, null,
//				pageNo, sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findStatusDApplicationsByInvestigator(int id,
//			String condition, List<SearchTerm> searchTerms, Integer pageNo,
//			Integer sortBy, String sortType) {
//		final String hqlQuery = "select a.application from AnonymousUser a";
//		return findStatusDApplications(
//				hqlQuery
//						+ " where a.application.investigator.id = :id and a.application.statusCode ='D'",
//				id, condition, searchTerms, pageNo, sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findStatusDApplicationsByPartner(int partnerId,
//			Integer pageNo, Integer sortBy, String sortType) {
//		return findStatusDApplicationsByPartner(partnerId, null, null, pageNo,
//				sortBy, sortType);
//	}
//
//	@Override
//	public List<Application> findStatusDApplicationsByPartner(int id,
//			String condition, List<SearchTerm> searchTerms, Integer pageNo,
//			Integer sortBy, String sortType) {
//		final String hqlQuery = "select a.application from AnonymousUser a";
//
//		return findStatusDApplications(
//				hqlQuery
//						+ " where a.application.client.parent.id = :id  and a.application.statusCode = 'D'",
//				id, condition, searchTerms, pageNo, sortBy, sortType);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public TenantVerification findTenantVerificationByReport(int id) {
//		final Query query = em
//				.createQuery("select tv from TenantVerification tv where tv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<TenantVerification> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Vehicle findVehicleByApplicationId(int id) {
//		final Query query = em
//				.createQuery("select vrv from Vehicle vrv where vrv.application.id = :id");
//		query.setParameter("id", id);
//
//		final List<Vehicle> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public VehicleRegistrationVerification findVehicleRegistrationVerificationByReport(
//			int id) {
//		final Query query = em
//				.createQuery("select vrv from VehicleRegistrationVerification vrv where vrv.report.id = :id");
//		query.setParameter("id", id);
//
//		final List<VehicleRegistrationVerification> list = query
//				.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		return list.get(0);
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void flushHibernateEntityManager() {
//		em.flush();
//	}
//
//	@Override
//	public List<ItemFlag> getApplicationListCompletedByDate(
//			Subscriber client, Date fromDate, Date toDate) {
//		final Query query = em
//				.createQuery("SELECT new com.tea.landlordapp.dto.ItemFlag(a.id) FROM Application a WHERE a.client = :client and a.statusCode = 'C' and date_format(a.completionDate,'%Y/%m/%d') between :fromDate and :toDate ");
//		query.setParameter("client", client);
//		query.setParameter("fromDate", YYYYMMDD.format(fromDate));
//		query.setParameter("toDate", YYYYMMDD.format(toDate));
//		@SuppressWarnings("unchecked")
//		final List<ItemFlag> list = query.getResultList();
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Integer> getApplicationListCompletedByDate(
//			Date fromDate, Date toDate) {
////		final Query query = em
////				.createQuery("SELECT a.id FROM Application a WHERE a.statusCode = 'C' and date_format(a.completionDate,'%Y/%m/%d') between :fromDate and :toDate ");
//		Query query = em
//				.createNativeQuery("SELECT a.id FROM application a WHERE a.status = 'C' and date_format(a.completion_date,'%Y-%m-%d') between :fromDate and :toDate ");
//		query.setParameter("fromDate", YYYY_MM_DD.format(fromDate));
//		query.setParameter("toDate", YYYY_MM_DD.format(toDate));
////		@SuppressWarnings("unchecked")
////		final List<ItemFlag> list = query.getResultList();
//		return query.getResultList();
//	}
//
//	@Override
//	public BigInteger getSequenceNextValue(Sequence seq) {
//		BigInteger retval = BigInteger.ZERO;
//
//		final Query q = em.createNativeQuery("select nextVal(:seqName)");
//		q.setParameter("seqName", seq.getSequenceName());
//		final BigInteger result = (BigInteger) q.getSingleResult();
//		if (result != null) {
//			retval = result;
//		}
//		return retval;
//	}
//
	private Map<Integer, String> getViewApplicationsSortOptions() {
		if (CollectionUtils.isEmpty(viewApplicationsSortOptions)) {
			viewApplicationsSortOptions = new HashMap<Integer, String>();
			viewApplicationsSortOptions.put(0, "a.reportState");
			viewApplicationsSortOptions.put(1, "a.statusCode");
			viewApplicationsSortOptions.put(2, "a.type");
			viewApplicationsSortOptions.put(3, "app.firstName");
			viewApplicationsSortOptions.put(4, "app.lastName");
			viewApplicationsSortOptions.put(5, "app.ssn");
			viewApplicationsSortOptions.put(6, "a.client.name");
			viewApplicationsSortOptions.put(7, "a.id");
			viewApplicationsSortOptions.put(8, "a.submissionDate");
			viewApplicationsSortOptions.put(9, "a.completionDate");
			viewApplicationsSortOptions.put(10, "a.investigator.firstName");
			viewApplicationsSortOptions.put(11, "a.csr.firstName");
			viewApplicationsSortOptions.put(12, "a.readByClient");
		}

		return viewApplicationsSortOptions;
	}
//
//	private Map<Integer, String> getViewInvoicePaymentsSortOptions() {
//		if (CollectionUtils.isEmpty(viewInvoicePaymentsSortOptions)) {
//			viewInvoicePaymentsSortOptions = new HashMap<Integer, String>();
//			viewInvoicePaymentsSortOptions.put(0, "ip.client.name");
//			viewInvoicePaymentsSortOptions.put(1, "ip.status");
//			viewInvoicePaymentsSortOptions.put(2, "ip.startDate");
//			viewInvoicePaymentsSortOptions.put(3, "ip.endDate");
//			viewInvoicePaymentsSortOptions.put(4, "ip.amount");
//			viewInvoicePaymentsSortOptions.put(5, "ip.paidAmount");
//		}
//
//		return viewInvoicePaymentsSortOptions;
//	}
//
//	@Override
//	public int postApplicantLastName(Hashtable<Integer, String> items) {
//		int updateCount = 0;
//		final Enumeration<Integer> e = items.keys();
//		while (e.hasMoreElements()) {
//			final Integer id = e.nextElement();
//			final String ln = items.get(id);
//			if (!ObjectUtils.equals(ln, null)) {
//				final Query q = em
//						.createNativeQuery("update applicant set last_name = :ln where id = :id");
//				try {
//					q.setParameter("id", id);
//					q.setParameter("ln", ln);
//					q.executeUpdate();
//					updateCount++;
//				} catch (final Exception ex) {
//					// TODO Auto-generated catch block
//					ex.printStackTrace();
//				}
//			}
//
//		}
//		return updateCount;
//	}
//
	@Override
	@Transactional(readOnly = false)
	public Applicant saveApplicant(Applicant applicant)
			throws DataAccessException {
		return em.merge(applicant);

	}

	@Override
	@Transactional(readOnly = false)
	public Application saveApplication(Application application) {
//		if (application.getStatus() == null) application.setStatus(ApplicationStatus.SAVED);
		return em.merge(application);
	}
//	
//	@Override
//	@Transactional(readOnly = false)
//	public ApplicationPriceChange saveApplicationPriceChange(ApplicationPriceChange applicationPriceChange) {
//		return em.merge(applicationPriceChange);
//	}
//
	@Override
	@Transactional(readOnly = false)
	public Application saveApplication(Application application, User user) {
		application.setAuditInfo(user);

		return saveApplication(application);
	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Application2BoardMembers saveApplication2BM(
//			Application2BoardMembers a2B) {
//		return em.merge(a2B);
//	}
//
//	@Override
//	public void saveEvent(com.tea.landlordapp.domain.Event event) {
//		em.merge(event);
//	}
//
//	@Transactional(readOnly = false)
//	@Override
//	public void saveInvoicePayment(InvoicePayment invoicePayment) {
//		em.merge(invoicePayment);
//	}
//	
//	@Transactional(readOnly = false)
//	private void clearRecommendationsAndSave(ManualReport rpt) {
//		rpt.getReport().getRecommendations().clear();
//		saveManualVerificationReport(rpt);
//	}
//
//	@Transactional(readOnly = false)
//	private void clearPVRecommendationsAndSave(ManualReport rpt) {
//		rpt.getReport().getRecommendations().clear();
//		rpt.getReport().setStatus(ReportStatus.Pulled.getCode());
//		saveManualVerificationReport(rpt);
//	}
//	
//	@Override
//	@Transactional(readOnly = false)
//	public void saveManualReport(ManualReport rpt) {
//		if (rpt instanceof PersonalReferenceVerification) {
//			final PersonalReferenceVerification personalReferenceVerification = (PersonalReferenceVerification) rpt;
//			for (final PersonalReferenceVerification pv : personalReferenceVerification
//					.getPersonalReferenceVerifications()) {
//					pv.setAuditInfo(rpt.getCreatedBy());
//				clearPVRecommendationsAndSave(pv);
//			}
//		} else
//			clearRecommendationsAndSave(rpt);
//	}
//	
//
//	@Transactional(readOnly = false)
//	public void saveManualVerificationReport(ManualReport manualReport) {
//		em.merge(manualReport);
//	}
//
////	@Override
////	public void savePayment(Payment payment) {
////		em.merge(payment);
////	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Recommendation saveRecommendation(Recommendation recommendation) {
//		return em.merge(recommendation);
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void saveReport(Report report) {
//		em.merge(report);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Integer> getAutoServicesToRunByApplication(Integer appId) {
//
//		Query query = em
//				.createQuery("select r.id from Report r where r.status in ('N','W')"
//						+ " and r.service.serviceType = 'A'"
//						+ " and r.applicant.application.statusCode in ('N','P','A')"
//						+ " and (r.executionDate <= current_timestamp or r.executionDate is null)"
//						+ " and r.applicant.application.id = :appId");
//				query.setParameter("appId", appId);
//				return query.getResultList();
//		
//		
////		StringBuilder sb = new StringBuilder();
////		sb.append("select distinct r.id");
////		sb.append(" from report r, applicant a, service s, application ap");
////		sb.append(" where r.service_id = s.id");
////		sb.append(" and r.applicant_id = a.id");
////		sb.append(" and a.application_id = ap.id");
////		sb.append(" and s.service_type = 'A'");
////		sb.append(" and (r.status = 'N' or r.status = 'W') ");
////		sb.append(" and ap.status in ('N','P','A')");
////		sb.append(" and (r.execution_date <= current_timestamp or r.execution_date is null)");
////		sb.append(" order by r.execution_date asc");
////		logger.debug(sb.toString());
////		Query query = em.createNativeQuery(sb.toString());
////		query.setMaxResults(10);
////
////		@SuppressWarnings("unchecked")
////		List<Integer> applList = query.getResultList();
////
////		return applList;
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<Integer> getApplicationsForServices() {
//
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("select distinct a.application_id");
//		sb.append(" from report r");
//		sb.append(" inner join applicant a on a.id = r.applicant_id");
//		sb.append(" inner join service s on s.id = r.service_id");
//		sb.append(" inner join application ap on ap.id = a.application_id");
//		sb.append(" and s.service_type = 'A'");
//		sb.append(" and r.status in ('N', 'W')");
//		sb.append(" and ap.status in ('N','P','A')");
//		sb.append(" and (r.execution_date <= current_timestamp or r.execution_date is null)");
//		sb.append(" order by r.execution_date asc");
//
//		
//		logger.debug(sb.toString());
//		Query query = em.createNativeQuery(sb.toString());
//		query.setMaxResults(10);
//
//		@SuppressWarnings("unchecked")
//		List<Integer> applList = query.getResultList();
//
//		return applList;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void updateExecutionDateByApplication(int id) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("update report r, application a, applicant ap");
//		sb.append(" set r.execution_date = current_timestamp + interval 3 minute");
//		sb.append(" where a.id = ").append(id);
//		sb.append(" and a.id = ap.application_id");
//		sb.append(" and ap.id = r.applicant_id");
//
//		Query query = em.createNativeQuery(sb.toString());
//		query.executeUpdate();
//
//		return;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Report> getReportsForServices() {
//		Query query = em
//				.createQuery("select r from Report r where r.status = 'N' and r.service.serviceType = 'A' and r.applicant.application.statusCode in ('N','P','A') and r.executionDate <= current_timestamp order by r.executionDate asc");
//		query.setMaxResults(10);
//		return query.getResultList();
//	}
//	
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Report> getReportsForPServices() {
//		Query query = em
//				.createQuery("select r from Report r where r.status = 'N' and r.service.id in (5, 6, 7) and r.applicant.application.statusCode in ('N', 'P')");
//		query.setMaxResults(10);
//		return query.getResultList();
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void updateExecutionDateByReport(int id) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("update report ");
//		sb.append(" set execution_date = current_timestamp + interval 3 minute");
//		sb.append(" where id = ").append(id);
//
//		Query query = em.createNativeQuery(sb.toString());
//		query.executeUpdate();
//
//		return;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void saveApplicantServicePrices(Applicant applicant) {
//		Query qA2s = em
//				.createQuery("select a2s from Applicant2Service a2s where a2s.applicant = :applicant");
//		qA2s.setParameter("applicant", applicant);
//
//		@SuppressWarnings("unchecked")
//		List<Applicant2Service> a2sList = qA2s.getResultList();
//		if (a2sList == null)
//			return;
//
//		Query qS2s = em
//				.createQuery("select s2s from Subscriber2Service s2s where s2s.subscriber = :subscriber");
//		qS2s.setParameter("subscriber", applicant.getApplication().getClient());
//
//		@SuppressWarnings("unchecked")
//		List<Subscriber2Service> s2sList = qS2s.getResultList();
//		Double appPrc = applicant.getApplication().getPrice() == null ? 0. : applicant.getApplication().getPrice();
//		for (Applicant2Service item: a2sList) {
//			Integer svcId = item.getService().getId();
//			Double newPrice = null;
//			for (Subscriber2Service item2: s2sList) {
//				if (item2.getService().getId() == svcId) {
//					newPrice = item2.getOverridePrice();
//					break;
//				}
//			}
//			if (newPrice == null) {
//				newPrice = item.getService().getStandardPrice();
//			}
//			item.setPrice(newPrice);
//			appPrc += newPrice;
//			applicant.getApplication().setPrice(appPrc);
//			applicant.getApplication().setCost(appPrc);
//			em.merge(item);
//		}
//
//	}
//
//	@Override
//	public EmploymentVerification findEmploymentVerification(int id)
//			throws DataAccessException {
//		return em.find(EmploymentVerification.class, id);
//	}
//
//	@Override
//	public PersonalReferenceVerification findPersonalReferenceVerification(
//			int id) throws DataAccessException {
//		return em.find(PersonalReferenceVerification.class, id);
//	}
//
//	@Override
//	public TenantVerification findTenantVerification(int id)
//			throws DataAccessException {
//		return em.find(TenantVerification.class, id);
//	}
//
//	@Override
//	public byte[] getFileContent(String container, String fileField){
//		String[] temp = container.split("-");
//		String table = temp[0];
//		String id = temp[1];
//		
//		String sql = "select "+fileField+" from "+table+" where id = "+id;
//		byte[] bytes = (byte[]) em.createQuery(sql).getSingleResult();
//		
//		return bytes;
//		
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Application> getApplicationsForMigration(Integer limit) {
//		String sql = "select a from Application a where a.fileStorageBean = :fileStorageBean";
//		Query query = em.createQuery(sql);
//		query.setParameter("fileStorageBean", FileStorageBeans.DBFileStorage.name());
//		List<Application> apps = query.setMaxResults(limit).getResultList();
//		return apps;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Application saveEnvelopeStatus(Integer appId, String envelopeId,
//			SigningStatus status) throws RecordNotFoundException {
//		Application app = null;
//		if (appId != null)
//			app = em.find(Application.class, appId);
//		if (app == null){
//			throw new RecordNotFoundException(String.format("Unable to record status for envelope %s. Application %d not found.", envelopeId, appId));
//		}
//		app.setEnvelopeId(envelopeId);
//		app.setSigningStatus(status);
////		em.flush();
//		return app;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Application saveEnvelopeStatus(Integer appId,
//			SigningStatus status) throws RecordNotFoundException {
//		Application app = null;
//		if (appId != null)
//			app = em.find(Application.class, appId);
//		if (app == null){
//			throw new RecordNotFoundException(String.format("Unable to record envelope status. Application %d not found.", appId));
//		}
//		app.setSigningStatus(status);
//		return app;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Application saveAuthEnvelopeStatus(Integer appId, String envelopeId, String documentId,
//			AuthSigningStatus status) throws RecordNotFoundException {
//		
//		Application app = null;
//		if (appId != null)
//			app = em.find(Application.class, appId);
//		if (app == null){
//			throw new RecordNotFoundException(String.format("Unable to record status for envelope %s. Application %d not found.", envelopeId, appId));
//		}
//		app.setAuthEnvelopeId(envelopeId);
//		app.setAuthDocumentId(documentId);
//		app.setAuthSigningStatus(status);
//		em.flush();
//		return app;
//	}	
//	
//	@Override
//	@Transactional(readOnly = false)
//	public ApplicationInProcess saveAppInProcessEnvelopeStatus(Integer aipId, String envelopeId, String documentId,
//			AuthSigningStatus status) throws RecordNotFoundException {
//		ApplicationInProcess aip = null;
//		if (aipId != null)
//			aip = em.find(ApplicationInProcess.class, aipId);
//		if (aip == null){
//			throw new RecordNotFoundException(String.format("Unable to record status for envelope %s. Application In Process %d not found.", envelopeId, aipId));
//		}
//		aip.setAuthEnvelopeId(envelopeId);
//		aip.setAuthDocumentId(documentId);
//		aip.setAuthSigningStatus(status);
//		em.flush();
//		return aip;
//	}
//	
//	@Override
//	@Transactional(readOnly = false)
//	public ApplicationInProcess saveAppInProcessEnvelopeStatus(Integer aipId, String envelopeId, String documentId, byte[] appPaymentAuth,
//			AuthSigningStatus status) throws RecordNotFoundException {
//		ApplicationInProcess aip = null;
//		if (aipId != null)
//			aip = em.find(ApplicationInProcess.class, aipId);
//		if (aip == null){
//			throw new RecordNotFoundException(String.format("Unable to record status for envelope %s. Application In Process %d not found.", envelopeId, aipId));
//		}
//		aip.setAuthEnvelopeId(envelopeId);
//		aip.setAuthDocumentId(documentId);
//		aip.setAppPaymentAuth(appPaymentAuth);
//		aip.setAuthSigningStatus(status);
//		em.flush();
//		return aip;
//	}	
//	
//	@Override
//	@Transactional(readOnly = false)
//	public void recordNoticeSendDate(Integer applicationId) {
//		recordNoticeSendDate(applicationId, new Date());		
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void recordNoticeSendDate(Integer applicationId, Date date) {
//		if (applicationId == null) return;
//		Application app = em.find(Application.class, applicationId);
//		if (app != null) {
//			app.setNoticeSent(date == null ? new Date(): date);
//		}
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void reopenApplication(Report report) {
//		if (report == null) return;
//		Integer rId = report.getId();
//		if (rId == null) return;
//		
//		final Query query = em
//				.createQuery("select a from Report r inner join r.applicant.application a where r.id = :id and a.statusCode = 'C'");
//		query.setParameter("id", rId);
//
//		@SuppressWarnings("unchecked")
//		final List<Application> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return;
//		}
//
//		Application a = list.get(0);
//		a.setStatus(ApplicationStatus.IN_PROCESS);
//		em.merge(a);
//	}
//
//	@Override
//	public Application findApplicationByReport(Report report) {
//		if (report == null) return null;
//		Integer rId = report.getId();
//		if (rId == null) return null;
//		
//		final Query query = em
//				.createQuery("select a from Report r inner join r.applicant.application a where r.id = :id");
//		query.setParameter("id", rId);
//
//		@SuppressWarnings("unchecked")
//		final List<Application> list = query.getResultList();
//		if (ObjectUtils.equals(list, null) || list.size() == 0) {
//			return null;
//		}
//
//		Application a = list.get(0);
//		return a;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public ApplicationPriceChange saveNewApplicationPriceChange(ApplicationPriceChange apc,
//			Integer applicationId, Integer userId) {
//		Application app = em.find(Application.class, applicationId);
//		User user = em.find(User.class, userId);
//		apc.setApplication(app);
//		apc.setAuditInfo(user);
//		return em.merge(apc);
//		
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public void finalizeApplication(int key, int usrId) {
//		String q = "update applicant set ssn=null, dob=null, date_of_birth=null, ssn_ein=null"
//				+ " where application_id = :appId";
//
//		Query qry = em.createNativeQuery(q);
//		qry.setParameter("appId", key);
//		qry.executeUpdate();
//		
//
//		q = "update application set status='L', archive_by=:usrId, archive_date=now()"
//				+ " where id = :appId";
//
//		qry = em.createNativeQuery(q);
//		qry.setParameter("appId", key);
//		qry.setParameter("usrId", usrId);
//		qry.executeUpdate();
//		
//		q = "delete report"
//				+ " from report"
//				+ " inner join applicant on applicant.id = report.applicant_id"
//				+ " where applicant.application_id = :appId";
//
//		qry = em.createNativeQuery(q);
//		qry.setParameter("appId", key);
//		qry.executeUpdate();
//	}
//	
////	@Override
////	@Transactional(readOnly = false)
////	public void removeReports(int appId){
////		String q = "delete report"
////				+ " from report"
////				+ " inner join applicant on applicant.id = report.applicant_id"
////				+ " where applicant.application_id = :appId";
////
////		Query qry = em.createNativeQuery(q);
////		qry.setParameter("appId", appId);
////		qry.executeUpdate();
////	}
////
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Integer> getReportIdsByServiceName(String label) {
//		Query query = em
//		.createQuery("select r from Report r where r.status = 'N'"
//				+ " and r.service.serviceType = 'A'"
//				+ " and r.applicant.application.statusCode in ('N','P','A')"
//				+ " and r.executionDate <= current_timestamp"
//				+ " and r.service.name = :serviceName"
//				+ " order by r.executionDate asc");
//		query.setMaxResults(20);
//		query.setParameter("serviceName", label);
//		return query.getResultList();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Residence> getResidences(Applicant applicant) {
//		Query query = em.createQuery("select r from Residence r where r.applicant = :applicant");
//		query.setParameter("applicant", applicant);
//		
//		return query.getResultList();
//	}
//
//	@Override
//	public List<Integer> getApplicationListUnsigned() {
//		String q = "select id from Application where status in ('S','N','P') and envelopeId is not null and signingStatusCode = 'S'";
//		Query qry = em.createQuery(q);
//		
//		@SuppressWarnings("unchecked")
//		List<Integer> result = qry.getResultList();
//		
//		return result;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public Application saveEnvelopeStatus(Integer appId, String envelopeId,
//			String documentId, SigningStatus status) {
//		// TODO Auto-generated method stub
//		Application app = null;
//		if (appId != null)
//			app = em.find(Application.class, appId);
//		if (app == null){
//			throw new RecordNotFoundException(String.format("Unable to record status for envelope %s. Application %d not found.", envelopeId, appId));
//		}
//		app.setEnvelopeId(envelopeId);
//		app.setDocumentId(documentId);
//		app.setSigningStatus(status);
//		em.flush();
//		return app;
//	
//	}
//
//	@Override
//	public Applicant findApplicantWithReports(Integer id) {
//
//		Applicant ap = findApplicant(id);
//		if (ap == null) return null;
//		
//		// guarantees residences are retrieved
//		List<Report> reports = ap.getReports();
//		@SuppressWarnings("unused")
//		int size = reports.size();
//
//		return ap;
//	}
//
//	@Override
//	public JqgridData getApplicationPaymentsForGrid(int page, int recordsPerPage, Integer applicationId) {
//
//
//		JqgridData res = new JqgridData();
//		
//		Query qry = em.createQuery("select count(*) from ApplicationCharge d where d.application.id = :txnid and d.payment is not null");
//		qry.setParameter("txnid", applicationId);
//		Long cnt = (Long) qry.getSingleResult();
//
//		int totalPages = 1;
//		int qPage = page > totalPages ? totalPages : page;
//		
//		String baseQuery = "select d from ApplicationCharge d where d.application.id = :txnid and d.payment is not null";
//		
//		qry = em.createQuery(baseQuery);
//		qry.setParameter("txnid", applicationId);
//
//		@SuppressWarnings("unchecked")
//		List<ApplicationCharge> items = qry.getResultList();
//		
//		res.setPage(qPage);
//		res.setRecords(new Long(cnt));
//		res.setTotal(totalPages);
//		List<JqgridRow> rows = new ArrayList<JqgridRow>();
//		
//		for (ApplicationCharge item : items) {
//			ApplicationPaymentGridRowDto dto = new ApplicationPaymentGridRowDto(item);
//			JqgridRow row = dto.getRow();
//			
//			rows.add(row);
//		}
//		
//		res.setRows(rows);
//		
//		return res;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public boolean updateApplicationPayment(ApplicationPaymentGridRowDto dto, User user) {
//		ApplicationCharge detail;
//		try {
//			String op = dto.getOperation();
//			if (Strings.isNullOrEmpty(op) || StringUtils.equalsIgnoreCase(op, "edit")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				detail.setPayment(dto.getAmount());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "add")){
//				detail = new ApplicationCharge();
//				detail.setPayment(dto.getAmount());
//				detail.setTransactionId(dto.getTransactionId());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				Application app = em.find(Application.class, dto.getApplicationId());
//				detail.setApplication(app);
//				detail.setCreatedBy(user);
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "del")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				em.remove(detail);
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("{}",e);
//		}
//		return false;
//	}
//
//	@Override
//	public JqgridData getApplicationDepositsForGrid(int page, int recordsPerPage, Integer applicationId) {
//
//
//		JqgridData res = new JqgridData();
//		
//		Query qry = em.createQuery("select count(*) from ApplicationCharge d where d.application.id = :txnid and d.deposit is not null");
//		qry.setParameter("txnid", applicationId);
//		Long cnt = (Long) qry.getSingleResult();
//
//		int totalPages = 1;
//		int qPage = page > totalPages ? totalPages : page;
//		
//		String baseQuery = "select d from ApplicationCharge d where d.application.id = :txnid and d.deposit is not null";
//		
//		qry = em.createQuery(baseQuery);
//		qry.setParameter("txnid", applicationId);
//
//		@SuppressWarnings("unchecked")
//		List<ApplicationCharge> items = qry.getResultList();
//		
//		res.setPage(qPage);
//		res.setRecords(new Long(cnt));
//		res.setTotal(totalPages);
//		List<JqgridRow> rows = new ArrayList<JqgridRow>();
//		
//		for (ApplicationCharge item : items) {
//			ApplicationDepositGridRowDto dto = new ApplicationDepositGridRowDto(item);
//			JqgridRow row = dto.getRow();
//			
//			rows.add(row);
//		}
//		
//		res.setRows(rows);
//		
//		return res;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public boolean updateApplicationDeposit(ApplicationDepositGridRowDto dto, User user) {
//		ApplicationCharge detail;
//		try {
//			String op = dto.getOperation();
//			if (Strings.isNullOrEmpty(op) || StringUtils.equalsIgnoreCase(op, "edit")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				detail.setDeposit(dto.getAmount());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "add")){
//				detail = new ApplicationCharge();
//				detail.setDeposit(dto.getAmount());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				Application app = em.find(Application.class, dto.getApplicationId());
//				detail.setApplication(app);
//				detail.setCreatedBy(user);
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "del")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				em.remove(detail);
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("{}",e);
//		}
//		return false;
//	}
//
//	@Override
//	public JqgridData getApplicationChargesForGrid(int page, int recordsPerPage, Integer applicationId) {
//
//
//		JqgridData res = new JqgridData();
//		
//		Query qry = em.createQuery("select count(*) from ApplicationCharge d where d.application.id = :txnid and d.amount is not null");
//		qry.setParameter("txnid", applicationId);
//		Long cnt = (Long) qry.getSingleResult();
//
//		int totalPages = 1;
//		int qPage = page > totalPages ? totalPages : page;
//		
//		String baseQuery = "select d from ApplicationCharge d where d.application.id = :txnid and d.amount is not null";
//		
//		qry = em.createQuery(baseQuery);
//		qry.setParameter("txnid", applicationId);
//
//		@SuppressWarnings("unchecked")
//		List<ApplicationCharge> items = qry.getResultList();
//		
//		res.setPage(qPage);
//		res.setRecords(new Long(cnt));
//		res.setTotal(totalPages);
//		List<JqgridRow> rows = new ArrayList<JqgridRow>();
//		
//		for (ApplicationCharge item : items) {
//			ApplicationChargeGridRowDto dto = new ApplicationChargeGridRowDto(item);
//			JqgridRow row = dto.getRow();
//			
//			rows.add(row);
//		}
//		
//		res.setRows(rows);
//		
//		return res;
//	}
//
//	@Override
//	@Transactional(readOnly = false)
//	public boolean updateApplicationCharge(ApplicationChargeGridRowDto dto, User user) {
//		ApplicationCharge detail;
//		try {
//			String op = dto.getOperation();
//			if (Strings.isNullOrEmpty(op) || StringUtils.equalsIgnoreCase(op, "edit")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				detail.setAmount(dto.getAmount());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "add")){
//				detail = new ApplicationCharge();
//				detail.setAmount(dto.getAmount());
//				detail.setDescription(dto.getDescription());
//				detail.setItemQboId(dto.getItemQboId());
//				detail.setServiceDate(dto.getServiceDate());
//				Application app = em.find(Application.class, dto.getApplicationId());
//				detail.setApplication(app);
//				detail.setCreatedBy(user);
//				detail.setModifiedBy(user);
//				em.merge(detail);
//				return true;
//			} else if (StringUtils.equalsIgnoreCase(op, "del")){
//				detail = em.find(ApplicationCharge.class, dto.getId());
//				if (detail.getQboTransaction() != null) return false;
//				em.remove(detail);
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("{}",e);
//		}
//		return false;
//	}
//
	@Override
	public List<Applicant> findApplicantsForApplication(Application application) {
		Query q = em.createQuery("select a from Applicant a where a.application = :application");
		q.setParameter("application", application);
		return q.getResultList();
	}
//
//	@Override
//	public JqgridData getApplicationsForGrid(int page, int recordsPerPage, String sortBy, boolean ascending,
//			Boolean search, JqgridFilter fltr, Integer userId) {
//		JqgridData res = new JqgridData();
//
//		String qSortDir = " ASC";
//		if (!ascending){
//			qSortDir = " DESC";
//		}
////		String where = "where u.id = " + userId.toString();
//		String where = String.format("where u.id = %d and a.status in ('S','N','A','P','C')", userId);
//		String srchElement = "";
//		String orderElement = "";
//		
//		if (search) {
//			for (JqgridFilterRule rule: fltr.getRules()) {
//				switch (rule.getField()) {
//				case "application_id":
//					srchElement = JqgHelper.buildWhereElement(search, "a.id", rule.getData(), rule.getOp());
//					break;
//				case "status":
//					srchElement = JqgHelper.buildWhereElement(search, "a.status", rule.getData(), rule.getOp());
//					break;
//				case "name":
//					srchElement = JqgHelper.buildWhereElement(search,
//							"trim(concat(coalesce(ap.first_name,''),' ', trim(concat(coalesce(ap.middle_initial,''),' ', coalesce(ap.last_name,'')))))",
//							rule.getData(), rule.getOp());
//					break;
//				case "client":
//					srchElement = JqgHelper.buildWhereElement(search,
//							"concat(s.name,if(a.rental_building_name is not null,concat(' / ',a.rental_building_name),''))",
//							rule.getData(), rule.getOp());
//					break;
//				case "submission_date":
//					srchElement = JqgHelper.buildWhereElement(search, "date(a.submission_date)", rule.getData(), rule.getOp());
//					break;
//				case "completion_date":
//					srchElement = JqgHelper.buildWhereElement(search, "date(a.completion_date)", rule.getData(), rule.getOp());
//					break;
//				case "investigator":
//					srchElement = JqgHelper.buildWhereElement(search,
//							"coalesce(trim(concat(iu.first_name,' ',iu.last_name)),'')", rule.getData(), rule.getOp());
//					break;
//				case "csr":
//					srchElement = JqgHelper.buildWhereElement(search,
//							"coalesce(trim(concat(cu.first_name,' ',cu.last_name)),'')", rule.getData(), rule.getOp());
//					break;
//
//				default:
//					break;
//				}
//				if (!Strings.isNullOrEmpty(srchElement))
//					where = where + " and " + srchElement;
//			}
//		}
//		
//		switch (sortBy) {
//		case "id":
//			orderElement = "a.id";
//			break;
//		case "status":
//			orderElement = "a.status";
//			break;
//		case "applicant":
//			orderElement = "trim(concat(coalesce(ap.last_name,''),', ',coalesce(ap.first_name,'')))";
//			break;
//		case "client":
//			orderElement = "concat(s.name,if(a.rental_building_name is not null,concat(' / ',a.rental_building_name),''))";
//			break;
//		case "submission_date":
//			orderElement = "a.submission_date";
//			break;
//		case "completion_date":
//			orderElement = "a.completion_date";
//			break;
//		case "investigator":
//			orderElement = "if(uva.id is null,'',coalesce(trim(concat(iu.first_name,' ',iu.last_name)),''))";
//			break;
//		case "csr":
//			orderElement = "if(uva.id is null,'',coalesce(trim(concat(cu.first_name,' ',cu.last_name)),''))";
//			break;
//
//		default:
//			break;
//	}
//
//		StringBuilder pf = new StringBuilder();
//		pf.append(" from application a");
//		pf.append(" join applicant ap on ap.application_id = a.id and ap.applicant_type = 'Primary'");
//		pf.append(" join subscriber s on s.id = a.subscriber_id");
//		pf.append(" left join property p on p.id = a.property_id");
//		pf.append(" left join application_user cu on cu.id = a.csr_id");
//		pf.append(" left join application_user iu on iu.id = a.investigator_id");
//		pf.append(" cross join application_user u");
//		pf.append(" left join property_authorization pa on pa.property_id = p.id and pa.user_id = u.id ");
//		pf.append(" join role r on r.id = u.role_id");
//		pf.append(" join role2capability r2c on r2c.role_id = r.id");
//		pf.append(" join capability c on c.id = r2c.capability_id ");
//		pf.append(where);
//		pf.append(" and (c.request_id = 'view.all.applications'");
//		pf.append(" or (c.request_id = 'view.partner.applications' and s.parent_id = u.subscriber_id)");
//		pf.append(" or (c.request_id = 'view.client.applications' and s.id = u.subscriber_id)");
//		pf.append(" or (c.request_id = 'view.assigned.applications' and (a.investigator_id = u.id or a.csr_id = u.id) and a.status in ('P','S'))");
//		pf.append(" or (c.request_id = 'view.my.applications' and a.status in ('C','P','S') and a.status2 is null and pa.id is not null))");
////		if (!Strings.isNullOrEmpty(orderElement)){
////			pf.append(" order by ").append(orderElement).append(qSortDir);
////		}
//		Query qPf = em.createNativeQuery("select count(distinct a.id)" + pf.toString());
//DateFormat df = new SimpleDateFormat(Globals.TIME_FORMAT);
//logger.debug(String.format("Begin count and filter for work queue: %s", df.format(new Date())));		
//		BigInteger cntBI = (BigInteger) qPf.getSingleResult();
//		Long cnt = cntBI.longValue();
//
//		int totalPages = (int) (cnt/recordsPerPage);
//		if (totalPages * recordsPerPage < cnt) totalPages ++;
//		int qPage = page > totalPages ? totalPages : page;
//		int skip = (qPage - 1) * recordsPerPage;
//		if (skip < 0) skip = 0;
//		
//
//		String listQuery = "select distinct a.id" + pf.toString();
//		if (!Strings.isNullOrEmpty(orderElement)){
//			listQuery = listQuery + " order by " + orderElement + qSortDir;
////			pf.append(" order by ").append(orderElement).append(qSortDir);
//		}
//		
//		Query qPfList = em.createNativeQuery(listQuery);
//		qPfList.setFirstResult(skip);
//		qPfList.setMaxResults(recordsPerPage);
//		
//		List<Integer> filteredApps = qPfList.getResultList();
//logger.debug(String.format("End count and filter for work queue: %s", df.format(new Date())));
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a.id,");
//		sb.append(" coalesce(  case");
//		sb.append("    when a.passthrough_status = 'C' then 'collected'");
//		sb.append("    when a.passthrough_status = 'R' then 'requested'");
//		sb.append("    when a.passthrough_status = 'W' and a.status = 'C' then 'waiting'");
//		sb.append("  end,'') passthrough, ");
//		sb.append(" '' icons,");
//		sb.append(" if(role.role in ('CU','CA','PM'),if(a.read_by_client = 1,'Read','Unread'),'')read_by_client,");
//		sb.append(" case a.report_state");
//		sb.append("  when 'AP' then 'Approved'");
//		sb.append("  when 'DE' then 'Declined'");
//		sb.append("  when 'PE' then 'Pending'");
//		sb.append("  when 'FR' then 'For Further Review'");
//		sb.append("  when 'PB' then 'Pending Board Approval'");
//		sb.append("  when 'BA' then 'Board Approved'");
//		sb.append("  else ''");
//		sb.append(" end report_state,");
//		sb.append(" a.status,");
//		sb.append(" concat(case a.application_type when 'T' then 'Tenant' when 'E' then 'Employment' else 'Other' end, '-',a.application_source)type_label,");
//		sb.append(" if(uaa.id is null,'',trim(concat(coalesce(pmt.ssl_first_name,''),' ',coalesce(pmt.ssl_last_name,'')))) payee,");
//		sb.append(" trim(concat(coalesce(a1.first_name,''),' ',coalesce(a1.last_name,''))) name,");
//		sb.append(" concat(s.name,if(a.rental_building_name is not null,concat('<br>',a.rental_building_name),'')) client,");
//		sb.append(" coalesce(a.rental_apartment_no,'') unit,");
//		sb.append(" if(uar.id is null,'','Y') view_report,");
//		sb.append(" a.submission_date,");
//		sb.append(" a.completion_date,");
//		sb.append(" if(uva.id is null,'',coalesce(trim(concat(iu.first_name,' ',iu.last_name)),'')) investigator,");
//		sb.append(" if(uva.id is null,'',coalesce(trim(concat(cu.first_name,' ',cu.last_name)),'')) csr,");
//		sb.append(" if(a.approved is null,'',if(a.approved = 1,'Approved','Declined')) approved,");
//		sb.append(" if(a.abandoned = 1,'abandoned','') abandoned,");
//		sb.append(" if(a.has_warnings = 'Y','warn','') warning,");
//		sb.append(" if(a.hold = 'P','hold','') hold,");
//		sb.append(" if(a.rush_fee > 0,'rush','') rush,");
//		sb.append(" if(a1.act_duty_military = 'Y' or a2.act_duty_military = 'Y','military','') military,");
//		sb.append(" case coalesce(a.signing_status,'') when 'S' then 'unsigned' when 'C' then 'signed' else '' end signing_status,");
//		sb.append(" case a.status");
//		sb.append("  when 'N' then 'New'");
//		sb.append("  when 'S' then 'Saved'");
//		sb.append("  when 'A' then 'Assigned'");
//		sb.append("  when 'P' then 'In Process'");
//		sb.append("  when 'C' then 'Complete'");
//		sb.append("  when 'R' then 'Removed'");
//		sb.append("  when 'D' then 'Deleted'");
//		sb.append("  when 'M' then 'Moved'");
//		sb.append("  when 'L' then 'Archived'");
//		sb.append("  else ''");
//		sb.append(" end status_label,");
//		sb.append(" 'service names' status_tip,");
//		sb.append(" if(coalesce(irc.reports,0) > 0,'','Y') reports_complete,");
//		sb.append(" 'Y' reports_complete,");
//		sb.append(" coalesce(a.pdf_access_code,'') pdf_access_code, ");
//		sb.append(" if(a.application_source = 'W' and a.status in ('S','N','P'),'Y','') offer_status_report,");
//		sb.append(" if(a.status in ('S','P') and count(at.id)>0,'Y','') review_docs,");
//		sb.append(" a.application_source source,");
//		sb.append(" if(a.status = 'C' and a.passthrough_status = 'W' and a.approved is null and coalesce(p.application_approval_user ,0) = :userId , 'need_approval','') need_approval,");
//		sb.append(" if(a.approved = 1,'Y','') was_approved,");
//		sb.append(" if(a.approved = 0,'Y','') was_declined,");
//		sb.append(" if(a.status = 'C' and a.passthrough_status = 'W' and a.approved is null ,'Y','') pending_approval,");
//		sb.append(" trim(concat(coalesce(approver.first_name,''), ' ', coalesce(approver.last_name,''))) approver_name,");
//		sb.append(" coalesce(DATE_FORMAT(a.approval_date ,'%b %d %Y %h:%i %p'),'') approval_date,");
//		sb.append(" if(coalesce(a.variable_security_deposit,0) = 1,'Y','') variable_security_deposit");
//		sb.append(" from application a");
//		sb.append(" join subscriber s on s.id = a.subscriber_id");
//		sb.append(" left join property p on p.id = a.property_id");
//		sb.append(" left join applicant a1 on a1.application_id = a.id and a1.applicant_type = 'Primary'");
//		sb.append(" left join applicant a2 on a2.application_id = a.id and a2.applicant_type = 'Co-Applicant'");
//		sb.append(" left join application_user cu on cu.id = a.csr_id");
//		sb.append(" left join application_user iu on iu.id = a.investigator_id");
//		sb.append(" left join application_user approver on approver.id = a.approved_by");
//		sb.append(" left join application_task at on at.application_id = a.id and at.doc_status = 'R' and at.complete = 0 and at.task_type = 'Upload'");
//		sb.append(" left join (");
//		sb.append("   select ap.application_id application_id, count(r.id) reports");
//		sb.append("   from applicant ap");
//		sb.append("   left join report r on r.applicant_id = ap.id");
//		sb.append("   where r.status <> 'C'");
//		sb.append("   group by ap.application_id");
//		sb.append(" ) irc on irc.application_id = a.id");
//		sb.append(" left join payment pmt on pmt.application_id = a.id");
//		sb.append(" cross join application_user u");
//		sb.append(" join role ON role.id = u.role_id");
//		sb.append(" left join user_authority uaa on uaa.id = u.id and uaa.request_id = 'view.appstatus'");
//		sb.append(" left join user_authority uar on uar.id = u.id and uar.request_id = 'view.report'");
//		sb.append(" left join user_authority uva on uva.id = u.id and uva.request_id = 'view.assignments'");
//		sb.append(" where a.id in :appList and u.id = :userId");
//		sb.append(" group by a.id");
//		if(!Strings.isNullOrEmpty(sortBy)){
//			sb.append(" order by ").append(sortBy).append(qSortDir);
//		}
//		
//		if (filteredApps.size() > 0){
//			Query q = em.createNativeQuery(sb.toString(), ApplicationGridRowDto.class);
//			q.setParameter("appList", filteredApps);
//			q.setParameter("userId", userId);
//logger.debug(String.format("Begin content query for work queue: %s", df.format(new Date())));
//			List<ApplicationGridRowDto> result = q.getResultList();
//logger.debug(String.format("End content query for work queue: %s", df.format(new Date())));
//			
//			if (result.size()>0){
//				res.setPage(qPage);
//				res.setRecords(new Long(cnt));
//				res.setTotal(totalPages);
//				
//				StringBuilder sbTip = new StringBuilder();
//				sbTip.append("select a.id,");
//				sbTip.append("coalesce(");
//				sbTip.append("  group_concat(");
//				sbTip.append("    applicantServiceTip.serviceTip,");
//				sbTip.append("      '<br>' order by applicantServiceTip.serviceTip desc separator '<br>'");
//				sbTip.append("  )");
//				sbTip.append(",'') statusTip ");
//				sbTip.append("from application a ");
//				sbTip.append("left join applicant ap on ap.application_id = a.id ");
//				sbTip.append("left join (");
//					sbTip.append("select ap1.id applicant_id, ");
//					sbTip.append("concat(concat(if(ap1.applicant_type = 'Co-Applicant','C: ','P: '), ");
//					sbTip.append("  trim(concat(coalesce(ap1.first_name,''),' ',coalesce(ap1.last_name,'')))),'<br>', ");
//					sbTip.append("  group_concat(s.name order by s.name separator '<br>') ");
//					sbTip.append(") serviceTip ");
//					sbTip.append("from applicant ap1 ");
//					sbTip.append("left join report r on r.applicant_id = ap1.id ");
//					sbTip.append("left join service s on s.id = r.service_id ");
//					sbTip.append("where ap1.application_id in :appList ");
//					sbTip.append("group by ap1.id) applicantServiceTip ");
//				sbTip.append("on applicantServiceTip.applicant_id = ap.id ");
//				sbTip.append("where a.id in :appList ");
//				sbTip.append("group by a.id");
//				
//				Query qtip = em.createNativeQuery(sbTip.toString(),ApplicationGridRowStatusTipDto.class);
//				
//				qtip.setParameter("appList", filteredApps);
//logger.debug(String.format("Begin tooltip query for work queue: %s", df.format(new Date())));
//				List<ApplicationGridRowStatusTipDto> tips = qtip.getResultList();
//logger.debug(String.format("End tooltip query for work queue: %s", df.format(new Date())));
//				
//		
//		
//					for (ApplicationGridRowStatusTipDto tip : tips) {
//						for (ApplicationGridRowDto dto : result) {
//							if(dto.getId().longValue() == tip.getId().longValue()){
//								
//								dto.setStatusTip(tip.getStatusTip());
//								break;
//							}
//						}
//					}
//					
//				
//				
//				List<JqgridRow> rows = new ArrayList<JqgridRow>();
//				for (ApplicationGridRowDto dto : result) {
//					JqgridRow row = dto.getRow();
//					rows.add(row);
//				}
//				res.setRows(rows);
//				
//				return res;
//			}			
//		}
//		
//
//		res.setPage(0);
//		res.setRecords(new Long(0));
//		res.setTotal(0);
//		List<JqgridRow> rows = new ArrayList<JqgridRow>();
//		res.setRows(rows);
//		
//		return res;
//	}
//
//	@Override
//	public String getServiceListTooltip(Integer applicationId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
