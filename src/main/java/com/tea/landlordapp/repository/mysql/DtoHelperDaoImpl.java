package com.tea.landlordapp.repository.mysql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import com.tea.landlordapp.domain.CommissionSchedule;
//import com.tea.domain.ManagementCompany;
import com.tea.landlordapp.domain.Property;
//import com.tea.landlordapp.domain.SalesRep;
//import com.tea.landlordapp.domain.Subscriber;
import com.tea.landlordapp.domain.User;
//import com.tea.landlordapp.dto.CsrTaskMonitorDto;
import com.tea.landlordapp.dto.IntegerStringKVDto;
import com.tea.landlordapp.enums.ApplicationState;
import com.tea.landlordapp.enums.CreditRecommendation;
//import com.tea.landlordapp.dto.MgmtCompanyDto;
//import com.tea.landlordapp.dto.CommissionScheduleDto;
//import com.tea.landlordapp.dto.SalesRepDto;
//import com.tea.landlordapp.dto.TemporaryApplicationDto;
//import com.tea.landlordapp.dto.UserGridItem;
//import com.tea.landlordapp.dto.mapper.CommissionScheduleMapper;
//import com.tea.landlordapp.exception.RecordNotFoundException;
import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.dto.ApplicationGridItem;
import com.tea.landlordapp.repository.ApplicationDao;
//import com.tea.landlordapp.repository.CommissionScheduleDao;
import com.tea.landlordapp.repository.DtoHelperDao;
import com.tea.landlordapp.repository.UserDao;
//import com.tea.landlordapp.utility.Pair;

@Repository("dtoHelperDao")
@Transactional(readOnly = true)
public class DtoHelperDaoImpl implements com.tea.landlordapp.repository.DtoHelperDao {

   private static final long serialVersionUID = 1L;

   protected transient final Log logger = LogFactory.getLog(getClass());

   private transient EntityManager em;

//   @Autowired
//   UserDaoImpl userDao;
//   
   @Autowired
   ApplicationDao applicationDao;
   
   
   @Autowired
   private Properties emailProperties;
   
//   @Autowired
//   private CommissionScheduleDao commissionScheduleDao;

   @Required
   @PersistenceContext
   public void setEntityManager(EntityManager em) {
      this.em = em;
   }

//   @Override
//   @SuppressWarnings("unchecked")
//   public List<ApplicationGridDto> getApplicationListByBM(User user, String status) {
//      String stat;
//      if (StringUtils.isBlank(status))
//         stat = "P";
//      else
//         stat = status;
//
//      StringBuilder sb = new StringBuilder();
//      sb.append("select new com.tea.dto.ApplicationGridDto(");
//      sb.append("a.id, a.hasWarnings, a2b.status,");
//      sb.append(" a.submissionDate, a.completionDate, a.reportState, a.type,");
//      sb.append(" coalesce(ap.firstName,''), coalesce(ap.lastName,''), coalesce(ap.encryptedSsn,''), a.client.name, a.rentalBuildingName,");
//      sb.append(" '', '', '', '', '', '', ''");
//      sb.append(")");
//      sb.append(" FROM Application2BoardMembers a2b");
//      sb.append(" join a2b.application a");
//      sb.append(" left join a.applicants ap");
//      sb.append(" WHERE a2b.user = :user and a2b.status = :status");
//      sb.append(" and ap.applicantType = 'Primary'");
//
//      Query query = em.createQuery(sb.toString());
//      query.setParameter("user", user);
//      query.setParameter("status", stat);
//
//      List<ApplicationGridDto> appList = query.getResultList();
//
//      return appList;
//   }

//   public List<CsrTaskMonitorDto> getTaskMonitorList(boolean isECsr) {
//      String csrClause = "a.csr_id = u.id and a.csr_id is not null";
//      if (isECsr) csrClause = csrClause.replaceAll("csr", "ecsr");
//
//      String qry = new StringBuilder("select coalesce(comp.name, pend.name) name, comp.appsClosed, pend.appsPending, comp.avgTime avgTimeToClose")
//               .append(", comp.maxTime maxTimeToClose, comp.minTime minTimeToClose,")
//               .append("pend.avgTime avgTimePending, pend.maxTime maxTimePending, coalesce(comp.id, pend.id) csrId")
//               .append(" from (select  concat(u.first_name ,' ', u.last_name) name, count(a.id) appsClosed,")
//               .append("avg(timestampdiff(minute,a.created_date,a.completion_date)+60) avgTime ,")
//               .append("max(timestampdiff(minute,a.created_date,a.completion_date)+60) maxTime,")
//               .append("min(timestampdiff(minute,a.created_date,a.completion_date)+60) minTime, u.id")
//               .append(" from application a, application_user u where ")
//               .append(csrClause)
//               .append(" and a.status in ('C')")
//               .append(" and DATE_FORMAT(a.completion_date,'%Y-%m-%d') >= DATE_FORMAT(now(),'%Y-%m-01')")
//               .append(" group by u.id, u.first_name, u.last_name) comp left join")
//               .append(" (select  concat(u.first_name ,' ', u.last_name) name, count(a.id) appsPending,")
//               .append("avg(timestampdiff(minute,a.created_date,now())+60) avgTime ,")
//               .append("max(timestampdiff(minute,a.created_date,now())+60) maxTime, u.id")
//               .append(" from application a, application_user u where ")
//               .append(csrClause)
//               .append(" and a.status in ('P')")
//               .append(" group by u.id, u.first_name, u.last_name) pend on comp.name = pend.name union ")
//               .append("select coalesce(comp.name, pend.name) name, comp.appsClosed, pend.appsPending, comp.avgTime avgTimeToClose,")
//               .append("comp.maxTime maxTimeToClose, comp.minTime minTimeToClose,")
//               .append("pend.avgTime avgTimePending, pend.maxTime maxTimePending, coalesce(comp.id, pend.id) csrId")
//               .append(" from (select  concat(u.first_name ,' ', u.last_name) name, count(a.id) appsClosed,")
//               .append("avg(timestampdiff(minute,a.created_date,a.completion_date)+60) avgTime ,")
//               .append("max(timestampdiff(minute,a.created_date,a.completion_date)+60) maxTime,")
//               .append("min(timestampdiff(minute,a.created_date,a.completion_date)+60) minTime, u.id")
//               .append(" from application a, application_user u where ")
//               .append(csrClause)
//               .append(" and a.status in ('C')")
//               .append(" and DATE_FORMAT(a.completion_date,'%Y-%m-%d') >= DATE_FORMAT(now(),'%Y-%m-01')")
//               .append(" group by u.id, u.first_name, u.last_name) comp right join")
//               .append(" (select  concat(u.first_name ,' ', u.last_name) name, count(a.id) appsPending,")
//               .append("avg(timestampdiff(minute,a.created_date,now())+60) avgTime ,")
//               .append("max(timestampdiff(minute,a.created_date,now())+60) maxTime, u.id")
//               .append(" from application a, application_user u where ")
//               .append(csrClause)
//               .append(" and a.status in ('P')")
//               .append(" group by u.id, u.first_name, u.last_name) pend on comp.name = pend.name")
//               .toString();
//
//      List<CsrTaskMonitorDto> list = new ArrayList<CsrTaskMonitorDto>();
//
//      @SuppressWarnings("rawtypes")
//      Iterator qList = em.createNativeQuery(qry).getResultList().iterator();
//
//      while (qList.hasNext()) {
//         Object[] oSvc = (Object[]) qList.next();
//         String nm = (String) oSvc[0];
//
//         Integer ncl = 0;
//         if (!ObjectUtils.equals(oSvc[1], null)) ncl = ((BigInteger) oSvc[1]).intValue();
//         Integer np = 0;
//         if (!ObjectUtils.equals(oSvc[2], null)) np = ((BigInteger) oSvc[2]).intValue();
//         Integer avC = 0;
//         if (!ObjectUtils.equals(oSvc[3], null)) avC = ((BigDecimal) oSvc[3]).intValue();
//         Integer mxC = 0;
//         if (!ObjectUtils.equals(oSvc[4], null)) mxC = ((BigInteger) oSvc[4]).intValue();
//         Integer mnC = 0;
//         if (!ObjectUtils.equals(oSvc[5], null)) mnC = ((BigInteger) oSvc[5]).intValue();
//         Integer avP = 0;
//         if (!ObjectUtils.equals(oSvc[6], null)) avP = ((BigDecimal) oSvc[6]).intValue();
//         Integer mxP = 0;
//         if (!ObjectUtils.equals(oSvc[7], null)) mxP = ((BigInteger) oSvc[7]).intValue();
//         Integer id = 0;
//         if (!ObjectUtils.equals(oSvc[8], null)) id = (Integer) oSvc[8];
//         CsrTaskMonitorDto item = new CsrTaskMonitorDto(id, nm, ncl, np, avC, mxC, mnC, avP, mxP);
//         list.add(item);
//
//      }
//
//      return list;
//   }

//   @Override
//   public List<ApplicationGridDto> getApplicationGridDtoList(User user, List<SearchTerm> searchTerms, LogicalOperator logOp) {
//
//      class ApplicantReportDto {
//
//         private ApplicantReportDto(Integer appId, Integer applicantId, String applicantType, String serviceName, Character reportStatus, Integer serviceId) {
//            this.appId = appId;
//            this.applicantId = applicantId;
//            this.applicantType = applicantType;
//            this.serviceName = serviceName;
//            this.reportStatus = reportStatus;
//            this.serviceId = serviceId;
//         }
//
//         private Integer appId;
//         private Integer applicantId;
//         private String applicantType;
//         private String serviceName;
//         private Character reportStatus;
//         private Integer serviceId;
//
//         private String getParentHashKey() {
//            return String.format("%d,%d%s", appId, applicantId, applicantType);
//         }
//      }
//
//      class ServiceDto {
//
//         private Integer id;
//         private String name;
//         private Integer appId;
//         private Integer applicantId;
//         private String applicantType;
//
//         private ServiceDto(Integer id, String name, Integer appId, Integer applicantId, String applicantType) {
//            this.id = id;
//            this.name = name;
//            this.appId = appId;
//            this.applicantId = applicantId;
//            this.applicantType = applicantType;
//         }
//
//         private String getParentHashKey() {
//            return String.format("%d,%d%s", appId, applicantId, applicantType);
//         }
//      }
//
//      if (ObjectUtils.equals(user, null)) return null;
//      String userFilter = "";
//      UserRole primaryRole = UserRole.getEnum(user.getPrimaryRoleCode());
//
//      // create role based where clause
//      switch (primaryRole) {
//         case SystemAdmin :
//            userFilter = "a.status not in ('D')";
//            break;
//         case PartnerAdmin :
//            userFilter = String.format("clnt.parent_id = %d and a.status not in ('D')", user.getSubscriber().getId());
//            break;
//         case Investigator :
//            userFilter = String.format("a.investigator_id = %d and a.status in ('P','S')", user.getId());
//            break;
//         case CustomerServiceRep :
//            userFilter = String.format("a.csr_id = %d and a.status in ('P','S')", user.getId());
//            break;
//         case ClientAdmin :
//            userFilter = String.format("clnt.id = %d and a.status in ('C','P','S') and a.status2 is null", user.getSubscriber().getId());
//            break;
//         case ClientUser :
//            userFilter = String.format("clnt.id = %d and a.status in ('C','P','S') and a.status2 is null", user.getSubscriber().getId());
//            break;
//         case ExternalCustomerServiceRep :
//            userFilter = String.format("a.ecsr_id = %d and a.status in ('P','S')", user.getId());
//            break;
//         default :
//            throw new InvalidParameterException("User has invalid role assigned");
//      }
//
//      // create common join clause
//      StringBuilder appSelectorJoinClause = new StringBuilder();
//      appSelectorJoinClause.append("left join subscriber clnt on a.subscriber_id = clnt.id");
//      appSelectorJoinClause.append(" left join application_user inv on a.investigator_id = inv.id");
//      appSelectorJoinClause.append(" left join application_user csr on a.csr_id = csr.id");
//      appSelectorJoinClause.append(" left join application_user xcsr on a.ecsr_id = xcsr.id");
//
//      // main application query
//      StringBuilder sb = new StringBuilder();
//      sb.append("select a.id,coalesce(a.has_warnings,'N'),a.status,a.submission_date, a.completion_date,");
//      sb.append("coalesce(a.report_state,''), a.application_type, coalesce(ap.first_name,''), coalesce(ap.last_name,''), coalesce(ap.ssn_ein,''),");
//      sb.append("coalesce(clnt.name,''), coalesce(a.rental_building_name,''),coalesce(inv.first_name,''),coalesce(inv.last_name,''),");
//      sb.append("coalesce(csr.first_name,''),coalesce(csr.last_name,''),coalesce(xcsr.first_name,''),coalesce(xcsr.last_name,''),");
//      sb.append("trim(concat(coalesce(p.ssl_first_name,''),' ',coalesce(p.ssl_last_name,''))) payer");
//      sb.append(" from applicant ap");
//      sb.append(" join application a on ap.application_id = a.id ");
//      sb.append(appSelectorJoinClause);
//      sb.append(" left join ");
//      sb.append("(");
//      sb.append("select p2.application_id, min(p2.id) pmtid");
//      sb.append(" from payment p2 group by p2.application_id");
//      sb.append(")");
//      sb.append(" pmts on pmts.application_id = a.id");
//      sb.append(" left join payment p on p.id = pmts.pmtid");
//      sb.append(" where ap.applicant_type = 'Primary'");
//      if (userFilter.length() > 0) sb.append(" and " + userFilter);
//
//      Query qApps = em.createNativeQuery(sb.toString());
//      @SuppressWarnings("unchecked")
//      List<Object[]> oApps = qApps.getResultList();
//      Iterator<Object[]> itApps = oApps.iterator();
//
//      // result application list
//      List<ApplicationGridDto> appList = new ArrayList<ApplicationGridDto>();
//
//      // searchable list of applications for later
//      Hashtable<Integer, ApplicationGridDto> apps = new Hashtable<Integer, ApplicationGridDto>(appList.size());
//
//      // load the lists
//      while (itApps.hasNext()) {
//         Object[] item = itApps.next();
//         String ssn = (String)item[9];
//         ssn = StringHelper.getMaskedSsn(ssn);
//         ApplicationGridDto dto = new ApplicationGridDto((Integer) item[0], new Character(((String) item[1]).charAt(0)), (Character) item[2], (Date) item[3], (Date) item[4], (String) item[5],
//                  (Character) item[6], (String) item[7], (String) item[8], (String) item[9], ssn, (String) item[11], (String) item[12], (String) item[13], (String) item[14],
//                  (String) item[15], (String) item[16], (String) item[17], (String) item[18]);
//         appList.add(dto);
//         apps.put((Integer) item[0], dto);
//      }
//
//      // get applicable report list
//      sb = new StringBuilder();
//      sb.append("select ");
//      sb.append("a.id,ap.id,ap.applicant_type,s.name,r.status,r.service_id");
//      sb.append(" from application a");
//      sb.append(" join applicant ap on ap.application_id = a.id");
//      sb.append(" join report r on r.applicant_id = ap.id");
//      sb.append(" join service s on r.service_id = s.id ");
//      sb.append(appSelectorJoinClause);
//      if (userFilter.length() > 0) {
//         sb.append(" where " + userFilter);
//      }
//      sb.append(" order by ap.applicant_type desc");
//
//      @SuppressWarnings("unchecked")
//      Iterator<Object[]> qReps = em.createNativeQuery(sb.toString()).getResultList().iterator();
//
//      List<ApplicantReportDto> repList = new ArrayList<ApplicantReportDto>();
//      while (qReps.hasNext()) {
//         Object[] item = qReps.next();
//         ApplicantReportDto dto = new ApplicantReportDto((Integer) item[0], (Integer) item[1], (String) item[2], (String) item[3], (Character) item[4], (Integer) item[5]);
//         repList.add(dto);
//      }
//
//      // get applicable service list both explicit and packaged
//      sb = new StringBuilder();
//      sb.append("select ");
//      sb.append("ap.id, a.id,svc.id, svc.name, ap.applicant_type");
//      sb.append(" from application a");
//      sb.append(" join applicant ap on ap.application_id = a.id");
//      sb.append(" join applicant2service a2s on a2s.applicant_id = ap.id");
//      sb.append(" join service svc on svc.id = a2s.service_id ");
//      sb.append(appSelectorJoinClause);
//      if (userFilter.length() > 0) {
//         sb.append(" where " + userFilter);
//      }
//      sb.append(" union ");
//      sb.append("select ");
//      sb.append("ap.id, a.id,svc.id, svc.name, ap.applicant_type");
//      sb.append(" from application a");
//      sb.append(" join applicant ap on ap.application_id = a.id");
//      sb.append(" join package pkg on pkg.id = ap.package_id");
//      sb.append(" join package2service p2s on pkg.id = p2s.package_id");
//      sb.append(" join service svc on svc.id = p2s.service_id ");
//      sb.append(appSelectorJoinClause);
//      if (userFilter.length() > 0) {
//         sb.append(" where " + userFilter);
//      }
//
//      List<ServiceDto> svcs = new ArrayList<ServiceDto>();
//      @SuppressWarnings("unchecked")
//      Iterator<Object[]> qApplicantServices = em.createNativeQuery(sb.toString()).getResultList().iterator();
//      while (qApplicantServices.hasNext()) {
//         Object[] oSvc = qApplicantServices.next();
//         ServiceDto svc = new ServiceDto((Integer) oSvc[2], (String) oSvc[3], (Integer) oSvc[0], (Integer) oSvc[1], (String) oSvc[4]);
//         svcs.add(svc);
//      }
//
//      // searchable list of applicants
//      Hashtable<String, ApplicationGridApplicant> applicants = new Hashtable<String, ApplicationGridApplicant>(appList.size());
//
//      // add report objects
//      for (ApplicantReportDto rep : repList) {
//         if (apps.containsKey(rep.appId)) {
//            ApplicationGridDto thisApp = apps.get(rep.appId);
//            ApplicationGridApplicant saveApplicant = null;
//            ApplicationGridApplicantReport thisRep = new ApplicationGridApplicantReport(rep.reportStatus, rep.serviceName, rep.serviceId);
//            if (applicants.containsKey(rep.getParentHashKey())) {
//               saveApplicant = applicants.get(rep.getParentHashKey());
//            } else {
//               saveApplicant = new ApplicationGridApplicant(rep.applicantId, rep.applicantType);
//               applicants.put(rep.getParentHashKey(), saveApplicant);
//               thisApp.getApplicants().add(saveApplicant);
//            }
//            saveApplicant.getReports().add(thisRep);
//         }
//      }
//
//      // add service objects
//      for (ServiceDto svc : svcs) {
//         if (apps.containsKey(svc.appId)) {
//            ApplicationGridDto thisApp = apps.get(svc.appId);
//            ApplicationGridApplicant saveApplicant = null;
//            ApplicationGridApplicantService thisSvc = new ApplicationGridApplicantService(svc.id, svc.name);
//            if (applicants.containsKey(svc.getParentHashKey())) {
//               saveApplicant = applicants.get(svc.getParentHashKey());
//            } else {
//               saveApplicant = new ApplicationGridApplicant(svc.applicantId, svc.applicantType);
//               applicants.put(svc.getParentHashKey(), saveApplicant);
//               thisApp.getApplicants().add(saveApplicant);
//            }
//            saveApplicant.getServices().add(thisSvc);
//         }
//      }
//
//      if (ObjectUtils.equals(searchTerms, null) || ObjectUtils.equals(logOp, null) | searchTerms.isEmpty())
//      // bypass the filter
//         return appList;
//
//      // Filter the list
//      List<ApplicationGridDto> fltList = new ArrayList<ApplicationGridDto>();
//
//      for (ApplicationGridDto dto : appList) {
//         boolean matches = false;
//         switch (logOp) {
//            case AND :
//               for (SearchTerm st : searchTerms) {
//                  matches = dto.matches(st);
//                  if (!matches) break;
//               }
//               break;
//            case OR :
//               for (SearchTerm st : searchTerms) {
//                  matches = dto.matches(st);
//                  if (matches) break;
//               }
//               break;
//         }
//         if (matches) fltList.add(dto);
//
//      }
//      return fltList;
//   }

   
   public List<ApplicationGridItem> findApplicationGridList(com.tea.landlordapp.domain.User user, ApplicationState state) throws DataAccessException {
	      List<ApplicationGridItem> items = new ArrayList<ApplicationGridItem>();
	      List<com.tea.landlordapp.domain.Application> applications = null;
	      if (state != ApplicationState.UNKNOWN) {
	    		 applications = applicationDao.findApplicationListByState(user, state);
	      }
	      else {
	    	  applications = applicationDao.findApplicationList(user);
	      }
	      for (Application application : applications) {
	         ApplicationGridItem item = new ApplicationGridItem(application.getId(), application.getApplicationExtId(),
	        		 											application.getApplicants().get(0).getEmailAddress(),
	        		 											application.getApplicants().get(0).getFullName(),
	        		 											application.getApplicants().get(0).getFirstName(),
	        		 											application.getApplicants().get(0).getLastName(),
	        		 											application.getCreatedDate(), application.getModifiedDate(),
	        		 											application.getStatus(),
	        		 											application.getState().getLabel(), application.getProperty().getAddressLine1(),
	        		 											application.getProperty().getCity(),
	        		 											CreditRecommendation.getLabel(application.getCreditRecommendation()),
	        		 											application.isCanRequestReport());
	         items.add(item);
	      }

	      return items;
	   }

   
//   public List<UserGridItem> findUserGridList(Subscriber sub) throws DataAccessException {
//      List<UserGridItem> items = new ArrayList<UserGridItem>();
//      List<User> users = userDao.findUserList(sub);
//
//      for (User user : users) {
//         UserGridItem item = new UserGridItem(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getPrimaryRoleCode(), user.getSubscriber().getName(), user.getLastLogin(),
//                  user.getStatus());
//         items.add(item);
//      }
//
//      return items;
//   }
//
//@Override
//public List<TemporaryApplicationDto> getTemporaryApplicationList(Integer page,
//		Integer pageSize) {
//	String qString = "select id, trim(concat(coalesce(first_name, ''),' ', coalesce(last_name,''))) applicant_name"
//			+ " from application_in_process"
//			+ " order by created_date desc";
//	if (!ObjectUtils.equals(page, null) && !ObjectUtils.equals(pageSize, null))
//		qString = qString + String.format(" limit %d, %d", page*pageSize, pageSize);
//	
//	Query query = em.createNativeQuery(qString, TemporaryApplicationDto.class);
//	@SuppressWarnings("unchecked")
//	List<TemporaryApplicationDto> list = query.getResultList();
//	
//	String baseUrl = emailProperties.getProperty("tea-tenant.url");
//	
//	for (TemporaryApplicationDto temporaryApplicationDto : list) {
//		temporaryApplicationDto.setBaseUrl(baseUrl);
//	}
//	
//	return list;
//}
//
//@Override
//public int getTemporaryApplicationPageCount(int pageSize) {
//	int res = 0;
//	
//	Query query = em.createQuery("select count(a.id) From com.tea.domain.ApplicationInProcess a");
//	Number totalRecs = (Number) query.getSingleResult();
//	
//	if (totalRecs != null) {
//		Double dbl = totalRecs.doubleValue();
//		res = (int) Math.ceil(dbl/pageSize);
//	}
//	
//	
//	return res;
//}
//
//@Override
//public List<MgmtCompanyDto> findManagementCompanyDtos() {
//	String qString = "select id, name, address, address2, city, state, zipcode,"
//			+ " contact_name, contact_phone, contact_email, client, status_code, commission_rate,"
//			+ " qbo_id, qbo_sync_token, hidden"
//			+ " from management_company order by name";
//	
//	Query query = em.createNativeQuery(qString, MgmtCompanyDto.class);
//	@SuppressWarnings("unchecked")
//	List<MgmtCompanyDto> list = query.getResultList();
//	
//	return list;
//}
//
//@Override
//public MgmtCompanyDto findMgmtCompanyDto(int id) {
//	String qString = "select id, name, address, address2, city, state, zipcode,"
//			+ " contact_name, contact_phone, contact_email, client, status_code, commission_rate,"
//			+ " qbo_id, qbo_sync_token, hidden"
//			+ " from management_company"
//			+ " where id = :id";
//	
//	Query query = em.createNativeQuery(qString, MgmtCompanyDto.class);
//	query.setParameter("id", id);
//	
//	MgmtCompanyDto res = (MgmtCompanyDto) query.getSingleResult();
//	
//	return res;
//}
//
//@Override
//@Transactional(readOnly = false)
//public MgmtCompanyDto saveMgmtCompanyDto(MgmtCompanyDto dto, User user) {
//	ManagementCompany managementCompany = null;
//	if (dto.getDtoId() != null) {
//		// saving existing record
//		managementCompany = em.find(ManagementCompany.class, dto.getDtoId());
//	} else {
//		// create a new one
//		managementCompany = new ManagementCompany();
//	}
//	managementCompany.setAddress(dto.getAddress());
//	managementCompany.setAddress2(dto.getAddress2());
//	managementCompany.setCity(dto.getCity());
//	managementCompany.setState(dto.getState());
//	managementCompany.setZipcode(dto.getZipcode());
//	managementCompany.setName(dto.getName());
//	managementCompany.setContactName(dto.getContactName());
//	managementCompany.setContactEmail(dto.getContactEmail());
//	managementCompany.setContactPhone(dto.getContactPhone());
//	managementCompany.setClient(dto.isClient());
//	managementCompany.setStatusCode(dto.getStatusCode());
//	managementCompany.setCommissionRate(dto.getCommissionRate());
//	managementCompany.setQboId(dto.getQboId());
//	managementCompany.setQboSyncToken(dto.getQboSyncToken());
//	managementCompany.setHidden(dto.isHidden());
//	managementCompany.setAuditInfo(user);
//	
//	managementCompany = em.merge(managementCompany);
//	
//	dto.setDtoId(managementCompany.getId());
//	dto.setAddress(managementCompany.getAddress());
//	dto.setAddress2(managementCompany.getAddress2());
//	dto.setCity(managementCompany.getCity());
//	dto.setState(managementCompany.getState());
//	dto.setZipcode(managementCompany.getZipcode());
//	dto.setName(managementCompany.getName());
//	dto.setContactName(managementCompany.getContactName());
//	dto.setContactPhone(managementCompany.getContactPhone());
//	dto.setContactEmail(managementCompany.getContactEmail());
//	dto.setCommissionRate(managementCompany.getCommissionRate());
//	dto.setQboId(managementCompany.getQboId());
//	dto.setQboSyncToken(managementCompany.getQboSyncToken());
//	dto.setHidden(managementCompany.isHidden());
//	return dto;
//}
//
//
//@Override
//public List<SalesRepDto> findSalesRepDtos() {
//	String qString = "select sr1.id, sr1.name, sr1.address, sr1.address2, sr1.city, sr1.state, sr1.zipcode,"
//			+ " sr1.phone, sr1.email, sr1.manager_id, sr2.name manager_name"
//			+ " from sales_rep sr1"
//			+ " left join sales_rep sr2 on sr1.manager_id = sr2.id"
//			+ " order by name";
//	
//	Query query = em.createNativeQuery(qString, SalesRepDto.class);
//	@SuppressWarnings("unchecked")
//	List<SalesRepDto> list = query.getResultList();
//	
//	return list;
//}
//
//@Override
//public SalesRepDto findSalesRepDto(int id) {
//	String qString = "select sr1.id, sr1.name, sr1.address, sr1.address2, sr1.city, sr1.state, sr1.zipcode,"
//			+ " sr1.phone, sr1.email, sr1.manager_id, sr2.name manager_name"
//			+ " from sales_rep sr1"
//			+ " left join sales_rep sr2 on sr1.manager_id = sr2.id"
//			+ " where sr1.id = :id";
//	
//	Query query = em.createNativeQuery(qString, SalesRepDto.class);
//	query.setParameter("id", id);
//	
//	SalesRepDto res = (SalesRepDto) query.getSingleResult();
//	
//	return res;
//}
//
//@Override
//@Transactional(readOnly = false)
//public SalesRepDto saveSalesRepDto(SalesRepDto dto, User user) {
//	SalesRep entity = null;
//	if (dto.getDtoId() != null) {
//		// saving existing record
//		entity = em.find(SalesRep.class, dto.getDtoId());
//	} else {
//		// create a new one
//		entity = new SalesRep();
//	}
//	SalesRep manager = null;
//	if ((dto.getManagerId() != null) && (dto.getManagerId() > 0)) {
//		manager = em.find(SalesRep.class, dto.getManagerId());
//	}
//	
//	entity.setAddress(dto.getAddress());
//	entity.setAddress2(dto.getAddress2());
//	entity.setCity(dto.getCity());
//	entity.setState(dto.getState());
//	entity.setZipcode(dto.getZipcode());
//	entity.setName(dto.getName());
//	entity.setEmail(dto.getEmail());
//	entity.setPhone(dto.getPhone());
//	entity.setManager(manager);
//	
//	entity.setAuditInfo(user);
//	
//	entity = em.merge(entity);
//	
//	dto.setDtoId(entity.getId());
//	dto.setAddress(entity.getAddress());
//	dto.setAddress2(entity.getAddress2());
//	dto.setCity(entity.getCity());
//	dto.setState(entity.getState());
//	dto.setZipcode(entity.getZipcode());
//	dto.setName(entity.getName());
//	dto.setPhone(entity.getPhone());
//	dto.setEmail(entity.getEmail());
//	
//	return dto;
//}

//@Override
//public List<IntegerStringKVDto> findSalesRepLookupMap(){
//	String queryString = "select sr.id `key`, sr.name `value` from sales_rep sr order by sr.name";
//	
//	Query query = em.createNativeQuery(queryString, IntegerStringKVDto.class);
//	@SuppressWarnings("unchecked")
//	List<IntegerStringKVDto> list = query.getResultList();
//	
//	return list;
//}
//
//@Override
//public List<IntegerStringKVDto> findSalesRepLookupMap(List<Integer> excludeIds){
//	StringBuilder q = new StringBuilder("select sr.id `key`, sr.name `value` from sales_rep sr");
//	if (excludeIds.size() > 0) {
//		StringBuilder sb = new StringBuilder(" where not sr.id in (");
//		boolean first = true;
//		for (Integer integer : excludeIds) {
//			if (!first) sb.append(",");
//			sb.append(integer.toString());
//			first = false;
//		}
//		sb.append(")");
//		q.append(sb);
//	}
//	q.append(" order by sr.name");
//	Query query = em.createNativeQuery(q.toString(), IntegerStringKVDto.class);
//	@SuppressWarnings("unchecked")
//	List<IntegerStringKVDto> list = query.getResultList();
//	
//	return list;
//}
//
//@Override
//public TreeMap<Integer, String> findPotentialSRMangerLookupMap(Integer selfId) {
//	StringBuilder sb = new StringBuilder();
//	sb.append("select sr1.id `key`, sr1.name `value`");
//	sb.append(" from sales_rep sr1");
//	if (selfId != null) {
//		sb.append(String.format(" where sr1.id <> %d", selfId));
//		sb.append(String.format(" and sr1.id not in (select id from sales_rep where manager_id = %d)", selfId));
//		sb.append(" and sr1.id not in ");
//		sb.append(" (select s1.id from sales_rep s1");
//		sb.append(" left join sales_rep s2 on s1.manager_id = s2.id");
//		sb.append(String.format(" where s2.manager_id = %d)", selfId));
//		sb.append(" and sr1.id not in ");
//		sb.append(" (select s1.id from sales_rep s1");
//		sb.append(" left join sales_rep s2 on s1.manager_id = s2.id");
//		sb.append(" left join sales_rep s3 on s2.manager_id = s3.id");
//		sb.append(String.format(" where s3.manager_id = %d)", selfId));
//		sb.append(" and sr1.id not in ");
//		sb.append(" (select s1.id from sales_rep s1");
//		sb.append(" left join sales_rep s2 on s1.manager_id = s2.id");
//		sb.append(" left join sales_rep s3 on s2.manager_id = s3.id");
//		sb.append(" left join sales_rep s4 on s3.manager_id = s4.id");
//		sb.append(String.format(" where s4.manager_id = %d)", selfId));
//	}
//	sb.append(" order by sr1.name");
//	
//	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
//	@SuppressWarnings("unchecked")
//	List<IntegerStringKVDto> list = query.getResultList();
//	
//	return IntegerStringKVDto.buildLookupMap(-1, "(none)", list);
//	
//}
//
//@Override
//public Map<Integer, String> findManagementCompanyLookupMap() {
//	String qString = "select id `key`, name `value`"
//			+ " from management_company order by name";
//	
//	Query query = em.createNativeQuery(qString, IntegerStringKVDto.class);
//	@SuppressWarnings("unchecked")
//	List<IntegerStringKVDto> list = query.getResultList();
//	
//	return IntegerStringKVDto.buildLookupMap(-1, "(none)", list);
//}
//
//@Override
//public TreeMap<Integer, String> findSubscriberLookupMap(Integer mgmtCompanyId) {
//	StringBuilder sb = new StringBuilder();
//	sb.append("select s.id `key`, s.name `value`");
//	sb.append(" from subscriber s");
//	if (mgmtCompanyId != null) {
//		sb.append(String.format(" where s.management_company_id = %d", mgmtCompanyId));
//		
//	}
//	sb.append(" order by s.name");
//	
//	Query query = em.createNativeQuery(sb.toString(), IntegerStringKVDto.class);
//	@SuppressWarnings("unchecked")
//	List<IntegerStringKVDto> list = query.getResultList();
//	
//	return IntegerStringKVDto.buildLookupMap(-1, "(none)", list);
//	
//}
//
//@Override
//public List<CommissionScheduleDto> findCommissionDtosForProperty(int propertyId) {
//	List<CommissionSchedule> csList = commissionScheduleDao.findCommissionSchedulesForProperty(propertyId, null);
//
//	List<CommissionScheduleDto> dtos = CommissionScheduleMapper.mapList(csList);
//	
//	return dtos;
//}
//
//@Override
//public CommissionScheduleDto findCommissionScheduleDto(int propertyId, int salesRepId) {
//	List<CommissionSchedule> csList = commissionScheduleDao.findCommissionSchedulesForProperty(propertyId, salesRepId);
//	CommissionScheduleDto dto = CommissionScheduleMapper.mapSingle(csList);
//	return dto;
//}
//
//@Override
//@Transactional
//public CommissionScheduleDto saveCommissionScheduleDto(CommissionScheduleDto dto, User user) throws RecordNotFoundException {
//	
//	Property p = em.find(Property.class, dto.getPropertyId());
//	if (p == null) throw new RecordNotFoundException(String.format("Property for id: %d not found", dto.getPropertyId()));
//	SalesRep sr = em.find(SalesRep.class, dto.getSrId());
//	if (sr == null) throw new RecordNotFoundException(String.format("Sales Rep for id: %d not found", dto.getSrId()));
//	Calendar calendar = Calendar.getInstance();
//	Date firstDate1 = dto.getFirstValidDate();
//	calendar.setTime(firstDate1);
//	calendar.add(Calendar.YEAR, 1);
//	Date firstDate2 = calendar.getTime();
//	calendar.add(Calendar.HOUR, -24);
//	Date lastDate1 = calendar.getTime();
//	calendar.add(Calendar.YEAR, 1);
//	Date lastDate2 = calendar.getTime();
//	calendar.add(Calendar.HOUR, 24);
//	Date firstDate3 = calendar.getTime();
//	calendar.add(Calendar.YEAR, 1);
//	Date firstDate4 = calendar.getTime();
//	calendar.add(Calendar.HOUR, -24);
//	Date lastDate3 = calendar.getTime();
//	calendar.add(Calendar.YEAR, 1);
//	Date lastDate4 = calendar.getTime();
//	calendar.add(Calendar.HOUR, 24);
//	Date firstDate5 = calendar.getTime();
//	calendar.add(Calendar.YEAR, 1);
//	calendar.add(Calendar.HOUR, -24);
//	Date lastDate5 = calendar.getTime();
//	
//	CommissionSchedule work = null;
//	// First year
//	work = persistYearlyCsComponent(dto.getId1(),dto.getYear1Rate(),firstDate1,lastDate1,p,sr,user);
//	dto.setId1(work.getId());
//	work = null;
//	
//	// Second year
//	work = persistYearlyCsComponent(dto.getId2(),dto.getYear2Rate(),firstDate2,lastDate2,p,sr,user);
//	dto.setId2(work.getId());
//	work = null;
//	
//	// Third year
//	work = persistYearlyCsComponent(dto.getId3(),dto.getYear3Rate(),firstDate3,lastDate3,p,sr,user);
//	dto.setId3(work.getId());
//	work = null;
//	
//	// Fourth year
//	work = persistYearlyCsComponent(dto.getId4(),dto.getYear4Rate(),firstDate4,lastDate4,p,sr,user);
//	dto.setId4(work.getId());
//	work = null;
//	
//	// Fifth year
//	work = persistYearlyCsComponent(dto.getId5(),dto.getYear5Rate(),firstDate5,lastDate5,p,sr,user);
//	dto.setId5(work.getId());
//	work = null;
//	
//	return dto;
//}
//
//@Autowired
//ApplicationDao applicationDao;
//
//
//
//private CommissionSchedule persistYearlyCsComponent(Integer id, Double rate, Date begin, Date end,
//		Property p, SalesRep sr, User user) {
//	CommissionSchedule work = null;
//	if (!ObjectUtils.equals(id, null)) {
//		work = em.find(CommissionSchedule.class, id);
//	}
//	if (ObjectUtils.equals(work, null))
//			work = new CommissionSchedule();
//	
//	work.setCommissionRate(rate);
//	work.setFirstValidDate(begin);
//	work.setLastValidDate(end);
//	work.setProperty(p);
//	work.setSalesRep(sr);
//	work.setAuditInfo(user);
//	em.merge(work);
//
//	return work;
//}
//
//@Override
//public void deleteCommissionSchedule(CommissionScheduleDto dto) {
//	deleteCommissionSchedule(dto.getId1(),
//							dto.getId2(),
//							dto.getId3(),
//							dto.getId4(),
//							dto.getId5());
//}
//@Override
//public void deleteCommissionSchedule(Integer id1,Integer id2,Integer id3,Integer id4,Integer id5) {
//	deleteOneCommissionSchedule(id1);
//	deleteOneCommissionSchedule(id2);
//	deleteOneCommissionSchedule(id3);
//	deleteOneCommissionSchedule(id4);
//	deleteOneCommissionSchedule(id5);
//}
//private void deleteOneCommissionSchedule(Integer id) {
//	if (!ObjectUtils.equals(id, null)) {
//		commissionScheduleDao.deleteCommissionSchedule(id);
//	}
//}
//
//@Override
//public Pair<String, Double> findManagementCommissionRate(Integer subscriberId) {
//	String mName = "";
//	Double mRateDouble = 0.;
//	Subscriber subscriber = em.find(Subscriber.class, subscriberId);
//	if (!ObjectUtils.equals(subscriber, null)) {
//		ManagementCompany managementCompany = subscriber.getManagementCompany();
//		if (!ObjectUtils.equals(managementCompany, null)) {
//			mName = managementCompany.getName();
//			mRateDouble = managementCompany.getCommissionRate();
//		}
//	}
//	return new Pair<String,Double>(mName,mRateDouble);
//}

}