package com.tea.landlordapp.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tea.landlordapp.domain.Applicant;
import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.SearchTerm;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.enums.ApplicationState;
//import com.tea.landlordapp.domain.Application2BoardMembers;
//import com.tea.landlordapp.domain.ApplicationInProcess;
//import com.tea.landlordapp.domain.ApplicationPriceChange;
//import com.tea.landlordapp.domain.BankReferenceVerification;
//import com.tea.landlordapp.domain.BasicBgVerification;
//import com.tea.landlordapp.domain.ClientApplication;
//import com.tea.landlordapp.domain.CrimeWatchVerification;
//import com.tea.landlordapp.domain.DocumentContainer;
//import com.tea.landlordapp.domain.DrivingLicenseVerification;
//import com.tea.landlordapp.domain.Dt5pVerification;
//import com.tea.landlordapp.domain.EmpCreditVerification;
//import com.tea.landlordapp.domain.EmploymentVerification;
//import com.tea.landlordapp.domain.Event;
//import com.tea.landlordapp.domain.EvictionsVerification;
//import com.tea.landlordapp.domain.ExtensBankVerification;
//import com.tea.landlordapp.domain.ExtensiveBgVerification;  
//import com.tea.landlordapp.domain.FdleVerification;
//import com.tea.landlordapp.domain.IntBGCVerification;
//import com.tea.landlordapp.domain.InvoicePayment;
//import com.tea.landlordapp.domain.ManualReport;
//import com.tea.landlordapp.domain.Payment;
//import com.tea.landlordapp.domain.PersonalReferenceVerification;
//import com.tea.landlordapp.domain.Pet;
//import com.tea.landlordapp.domain.PhysicalAbilityVerification;
//import com.tea.landlordapp.domain.Recommendation;
//import com.tea.landlordapp.domain.Report;
//import com.tea.landlordapp.domain.Residence;
//import com.tea.landlordapp.domain.SearchTerm;
//import com.tea.landlordapp.domain.SsnVerification;
//import com.tea.landlordapp.domain.Subscriber;
//import com.tea.landlordapp.domain.TenantVerification;
//import com.tea.landlordapp.landlordapp.domain.User;
//import com.tea.landlordapp.domain.Vehicle;
//import com.tea.landlordapp.domain.VehicleRegistrationVerification;
//import com.tea.landlordapp.dto.ApplicationChargeGridRowDto;
//import com.tea.landlordapp.dto.ApplicationDepositGridRowDto;
//import com.tea.landlordapp.dto.ApplicationGridRowDto;
//import com.tea.landlordapp.dto.ApplicationPaymentGridRowDto;
//import com.tea.landlordapp.dto.ItemFlag;
//import com.tea.landlordapp.dto.JqgridData;
//import com.tea.landlordapp.dto.JqgridFilter;
//import com.tea.landlordapp.dto.Sequence;
//import com.tea.landlordapp.enums.AuthSigningStatus;
//import com.tea.landlordapp.enums.SigningStatus;
//import com.tea.landlordapp.exception.RecordNotFoundException;

public interface ApplicationDao extends Serializable {
	
	public <T> T merge(T entity);
	public <T> void persist(T entity);

   public void clearHibernateEntityManager() throws DataAccessException;

   public Integer countAllApplications() throws DataAccessException;

   public Integer countAllApplications(String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countApplicationsByClient(int id) throws DataAccessException;
//
//   public Integer countApplicationsByClient(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countApplicationsByManager(int id, User user) throws DataAccessException;
//
//   public Integer countApplicationsByManager(int id, String condition, List<SearchTerm> searchTerms, User user) throws DataAccessException;
//
//   public Integer countApplicationsByCSR(int id) throws DataAccessException;
//
//   public Integer countApplicationsByCSR(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countApplicationsByECSR(int id) throws DataAccessException;
//
//   public Integer countApplicationsByECSR(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countApplicationsByInvestigator(int id) throws DataAccessException;
//
//   public Integer countApplicationsByInvestigator(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countApplicationsByPartner(int id) throws DataAccessException;
//
//   public Integer countApplicationsByPartner(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countInvoicePaymentsByClient(Subscriber client) throws DataAccessException;
//
//   public Integer countInvoicePaymentsByPartner(Subscriber partner) throws DataAccessException;

//   public Integer countStatusDApplicationsByInvestigator(int id) throws DataAccessException;

//   public Integer countStatusDApplicationsByInvestigator(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public Integer countStatusDApplicationsByPartner(int id) throws DataAccessException;
//
//   public Integer countStatusDApplicationsByPartner(int id, String condition, List<SearchTerm> searchTerms) throws DataAccessException;

   public void deleteApplication(Application application) throws DataAccessException;

//   public void deleteApplicationInProcess(ApplicationInProcess aip);
//   public void deleteApplicationInProcess(Integer aipID);
//
//   public void deleteBDApplication(Application application) throws DataAccessException;
//
//   public Integer deleteEvent(Event event) throws DataAccessException;
//
//   public void deleteInvoicePayment(InvoicePayment invoicePayment) throws DataAccessException;

   public List<Application> findAllApplicationByECSRs(User usr, Date date, String userType) throws DataAccessException;

   public List<Application> findAllApplications(Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;

   public List<Application> findAllApplications(String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;

   public Applicant findApplicant(int id) throws DataAccessException;

   public Application findApplication(int id) throws DataAccessException;

   public Application findApplicationWithApplicants(int id) throws DataAccessException;
   
//   public Application findApplicationWithApplicationCollectionItems(int id);
//
//   public String findApplication(String ssnNumber) throws DataAccessException;

//   public List<Application2BoardMembers> findApplication2BoardMembers(Application app) throws DataAccessException;
//
//   public List<Application2BoardMembers> findApplication2BoardMembers(User user, Application app, String Status) throws DataAccessException;

//   public List<Application> findApplicationByBM(User user, Character status) throws DataAccessException;
//
//   public Application findApplicationByEnvelopeId(String envelopeId);
//   public ClientApplication findClientApplicationByEnvelopeId(String envelopeId);
//
//   public ApplicationInProcess findApplicationInProcess(int id);
//
//   public ApplicationInProcess findApplicationInProcess(String key);
//
//   public ApplicationInProcess findApplicationInProcessByEmail(String email);
   
//   public List<Application> findApplicationsByAu() throws DataAccessException;

//   public List<Application> findApplicationsByClient(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByClient(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByClient(Integer clientId) throws DataAccessException;
//
//   public List<Application> findApplicationsByManager(int id, Integer pageNo, Integer sortBy, String sortType, User user) throws DataAccessException;
//
//   public List<Application> findApplicationsByManager(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType, User user) throws DataAccessException;
//
//   public List<Application> findApplicationsByCSR(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByCSR(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByECSR(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByECSR(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType);

   public List<Application> findApplicationsByInvestigator(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;

   public List<Application> findApplicationsByInvestigator(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;

//   public List<Application> findApplicationsByPartner(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByPartner(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findApplicationsByPartner(Integer partnerId) throws DataAccessException;
//
//   public BankReferenceVerification findBankReferenceVerificationByReport(int id) throws DataAccessException;
//
//   public BasicBgVerification findBasicBgVerificationByReport(int id) throws DataAccessException;
//
//   public CrimeWatchVerification findCrimeWatchVerificationByReport(int id) throws DataAccessException;
//
//   public DrivingLicenseVerification findDrivingLicenseVerificationByReport(int id) throws DataAccessException;

//   public Dt5pVerification findDt5pVerificationByReport(int id) throws DataAccessException;
//
//   public EmpCreditVerification findEmpCreditVerificationByReport(int id) throws DataAccessException;
//
//   public EmploymentVerification findEmploymentVerificationByReport(int id) throws DataAccessException;
//   public EmploymentVerification findEmploymentVerification(int id) throws DataAccessException;
//
//   public Event findEvent(Integer id) throws DataAccessException;
//
//   public List<Event> findEvents() throws DataAccessException;
//
//   public EvictionsVerification findEvictionsVerificationByReport(int id) throws DataAccessException;
//
//   public ExtensBankVerification findExtensBankVerificationByReport(int id) throws DataAccessException;
//
//   public ExtensiveBgVerification findExtensiveBgVerificationByReport(int id) throws DataAccessException;
//   public DocumentContainer findDocumentContainerByReport(int id) throws DataAccessException;
//
//   public FdleVerification findFdleVerificationByReport(int id) throws DataAccessException;
//
//   public IntBGCVerification findIntBGCVerificationByReport(int id) throws DataAccessException;
//
//   public InvoicePayment findInvoicePayment(Integer id);
//
//   public List<InvoicePayment> findInvoicePayments(Date startDate, Date endDate, Subscriber client) throws DataAccessException;
//
//   public List<InvoicePayment> findInvoicePaymentsByClient(Subscriber client, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<InvoicePayment> findInvoicePaymentsByPartner(Subscriber partner, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<InvoicePayment> findInvoices(String startSql, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public List<InvoicePayment> findInvoicesByClient(Integer clientId, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public List<InvoicePayment> findInvoicesByPartner(Integer partnerId, String condition, List<SearchTerm> searchTerms) throws DataAccessException;
//
//   public PhysicalAbilityVerification findPatVerificationByReport(int id) throws DataAccessException;
//
//   public Payment findPaymentByReport(Integer applicationId);
//   
//   public List<Payment> findPayments();
//
//   public List<Payment> findPayments(Date startDate, Date endDate) throws DataAccessException;
//
//   public List<Payment> findPaymentsByCreatedDate(Date fromDate, Date toDate);
//
//   public PersonalReferenceVerification findPersonalReferenceVerificationByReport(int id) throws DataAccessException;
//   public PersonalReferenceVerification findPersonalReferenceVerification(int id) throws DataAccessException;
//
//   public Pet findPet(Integer id);
//
//   public Recommendation findRecommendation(int id) throws DataAccessException;
//
//   public List<Recommendation> findRecommendationsByApplicant(int applicantId) throws DataAccessException;
//
//   public Report findReport(int id) throws DataAccessException;
//   public Report findReportWithRecomendations(int id) throws DataAccessException;
//
//   public List<Report> findReports(Integer vendorId, Date startDate, Date endDate) throws DataAccessException;
//
//   public List<Report> findReportsByApplicant(int id) throws DataAccessException;
//
//   public SsnVerification findSsnVerificationByReport(int id) throws DataAccessException;
//
//   public List<Application> findStatusDApplicationsByInvestigator(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findStatusDApplicationsByInvestigator(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findStatusDApplicationsByPartner(int id, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public List<Application> findStatusDApplicationsByPartner(int id, String condition, List<SearchTerm> searchTerms, Integer pageNo, Integer sortBy, String sortType) throws DataAccessException;
//
//   public TenantVerification findTenantVerificationByReport(int id) throws DataAccessException;
//   public TenantVerification findTenantVerification(int id) throws DataAccessException;
//
//   public Vehicle findVehicleByApplicationId(int id) throws DataAccessException;
//
//   public VehicleRegistrationVerification findVehicleRegistrationVerificationByReport(int id) throws DataAccessException;
//
//   public void flushHibernateEntityManager() throws DataAccessException;
//
//   public List<ItemFlag> getApplicationListCompletedByDate(Subscriber client, Date fromDate, Date toDate) throws DataAccessException;
//
//   public BigInteger getSequenceNextValue(Sequence seq);
//
//   public int postApplicantLastName(Hashtable<Integer, String> items);
//
   public Applicant saveApplicant(Applicant applicant) throws DataAccessException;
//
   public Application saveApplication(Application application) throws DataAccessException;
//
//   public Application2BoardMembers saveApplication2BM(Application2BoardMembers a2B) throws DataAccessException;
//
//   public void saveEvent(com.tea.domain.Event event) throws DataAccessException;
//
//   public void saveInvoicePayment(InvoicePayment invoicePayment) throws DataAccessException;
//   public ApplicationPriceChange saveNewApplicationPriceChange(ApplicationPriceChange apc,
//			Integer applicationId, Integer userId);
//
//   public void saveManualReport(ManualReport report) throws DataAccessException;
//
////   public void savePayment(Payment payment) throws DataAccessException;
//
//   public Recommendation saveRecommendation(Recommendation recommendation) throws DataAccessException;
//
//   public void saveReport(Report report) throws DataAccessException;
//   
//   public void saveApplicantServicePrices(Applicant applicant);
//   
//   /* To support Service Getter - Start */
//   public List<Integer> getApplicationsForServices();
//   public List<Report> getReportsForServices();
//   public List<Report> getReportsForPServices();
//   public void updateExecutionDateByApplication(int id);
//   public void updateExecutionDateByReport(int id);
   public Application saveApplication(Application application, User user) throws DataAccessException;   
   /* To support Service Getter - End */

//   public Report findReportByCustomData(String txt); 
//
//   public byte[] getFileContent(String container, String fileField);
//
//   public List<Application> getApplicationsForMigration(Integer limit);   
//   
//   public Application saveEnvelopeStatus(Integer appId, String envelopeId, SigningStatus status) throws RecordNotFoundException;
//   public Application saveEnvelopeStatus(Integer appId, SigningStatus status) throws RecordNotFoundException;
//   public Application saveAuthEnvelopeStatus(Integer appId, String envelopeId, String documentId,
//			AuthSigningStatus status) throws RecordNotFoundException;
//   
//   public ApplicationInProcess saveAppInProcessEnvelopeStatus(Integer aipId, String envelopeId, String documentId, byte[] appPaymentAuth,
//			AuthSigningStatus status) throws RecordNotFoundException;
//   public ApplicationInProcess saveAppInProcessEnvelopeStatus(Integer aipId,String envelopeId, String documentId, AuthSigningStatus status)
//			throws RecordNotFoundException;
//   
//   public void recordNoticeSendDate(Integer applicationId);
//   public void recordNoticeSendDate(Integer applicationId, Date date);
//   
//   public void reopenApplication(Report report);
//   public Application findApplicationByReport(Report report);
//   
//   public ApplicationPriceChange saveApplicationPriceChange(ApplicationPriceChange applicationPriceChange);
//   
//   public void finalizeApplication(int key, int usrId);
////   public List<Integer> getAutoServicesToRun();
//public List<Integer> getReportIdsByServiceName(String label);
//public List<Integer> getAutoServicesToRunByApplication(Integer appId);
//
//public List<Residence> getResidences(Applicant applicant);
//// public void removeReports(int appId);
//public List<Integer> getApplicationListCompletedByDate(Date fromDate, Date toDate);
//
//public List<Integer> getApplicationListUnsigned();
public Applicant findApplicantWithApplication(int id);
//public Application saveEnvelopeStatus(Integer appId, String envelopeID,
//		String documentId, SigningStatus status);
// public Application findApplicationWithApplicantsAndPets(int id);
//public Applicant findApplicantWithResidence(int id);
public List<Application> findApplicationsByProperty(int propertyId);
//public Payment findPaymentByReportAlreadyPosted(Integer applicationId);
//public Applicant findApplicantWithReports(Integer id);
//public Application findApplicationWithApplicantsAndReports(int id);
//public Application findApplicationWithApplicantsAndReportsAndTasks(int id);
//public Boolean checkIfApplicationIsAu(Integer appId);
//public Application findApplicationForFinalReport(int id);
//public JqgridData getApplicationChargesForGrid(int page, int recordsPerPage, Integer applicationId);
public List<Applicant> findApplicantsForApplication(Application application);
//public Applicant ensureEmploymentRetrieval(Applicant applicant);
//public Application ensureApplicantRetrieval(Application application);
//public Applicant ensureReportRetrieval(Applicant applicant);
//public Applicant ensureResidenceRetrieval(Applicant applicant);
//public Report ensureRecommendationRetrieval(Report parent);
//public Applicant ensureFinancialRetrieval(Applicant applicant);
//public boolean updateApplicationCharge(ApplicationChargeGridRowDto dto, User user);
//public JqgridData getApplicationPaymentsForGrid(int page, int recordsPerPage, Integer applicationId);
//public boolean updateApplicationPayment(ApplicationPaymentGridRowDto dto, User user);
//public JqgridData getApplicationDepositsForGrid(int page, int recordsPerPage, Integer applicationId);
//public boolean updateApplicationDeposit(ApplicationDepositGridRowDto dto, User user);
//public JqgridData getApplicationsForGrid(int page, int recordsPerPage, String sortBy, boolean ascending,
//		Boolean search, JqgridFilter fltr, Integer userId);
//public String getServiceListTooltip(Integer applicationId);
public List<Application> findApplicationList(User user) throws DataAccessException;
public List<Application> findApplicationList(User user, String status) throws DataAccessException;
public List<Application> findApplicationListByState(User user, ApplicationState state) throws DataAccessException;
public List<Application> findApplicationList(User user, String status, String otherStatus, String anotherStatus) throws DataAccessException;
public Application findApplicationByExtId(Integer appExtId);


}
