package com.tea.landlordapp.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.dao.DataAccessException;

//import com.tea.landlordapp.domain.Subscriber;
import com.tea.landlordapp.domain.User;
//import com.tea.landlordapp.dto.CsrTaskMonitorDto;
import com.tea.landlordapp.dto.IntegerStringKVDto;
//import com.tea.landlordapp.landlordapp.dto.MgmtCompanyDto;
//import com.tea.landlordapp.dto.CommissionScheduleDto;
//import com.tea.landlordapp.dto.SalesRepDto;
//import com.tea.landlordapp.dto.TemporaryApplicationDto;
//import com.tea.landlordapp.dto.UserGridItem;
//import com.tea.landlordapp.exception.RecordNotFoundException;
import com.tea.landlordapp.dto.ApplicationGridItem;
//import com.tea.landlordapp.utility.Pair;

public interface DtoHelperDao extends Serializable {

//   public List<ApplicationGridDto> getApplicationListByBM(User user, String status) throws DataAccessException;

//   public List<CsrTaskMonitorDto> getTaskMonitorList(boolean isECsr);

//   public List<ApplicationGridDto> getApplicationGridDtoList(User user, List<SearchTerm> searchTerms, LogicalOperator searchCondition);

//   public List<UserGridItem> findUserGridList(Subscriber sub) throws DataAccessException;
   
   public List<ApplicationGridItem> findApplicationGridList(User user) throws DataAccessException;
   
//   // Support Temporary Application display controller
//   public List<TemporaryApplicationDto> getTemporaryApplicationList(Integer page, Integer pageSize);
//   public int getTemporaryApplicationPageCount(int pageSize);
//   
//   // Management Company Controller Support
//   public List<MgmtCompanyDto> findManagementCompanyDtos();
//   public MgmtCompanyDto findMgmtCompanyDto(int id);
//   public MgmtCompanyDto saveMgmtCompanyDto(MgmtCompanyDto dto, User user);
//   public Map<Integer, String> findManagementCompanyLookupMap();
//   
//   // Sales Representative Controller Support
//   public List<SalesRepDto> findSalesRepDtos();
//   public SalesRepDto findSalesRepDto(int id);
//   public SalesRepDto saveSalesRepDto(SalesRepDto dto, User user);
//   public List<IntegerStringKVDto> findSalesRepLookupMap();
//   public List<IntegerStringKVDto> findSalesRepLookupMap(List<Integer> excludeIds);
//   public TreeMap<Integer, String> findPotentialSRMangerLookupMap(Integer selfId);
//   public Pair<String, Double> findManagementCommissionRate(Integer subscriberId);
//
//   // Support for Sales Commission 
//   public List<CommissionScheduleDto> findCommissionDtosForProperty(int propertyId);
//   public CommissionScheduleDto saveCommissionScheduleDto(CommissionScheduleDto dto, User user) throws RecordNotFoundException;
//   public void deleteCommissionSchedule(CommissionScheduleDto dto);
//   public void deleteCommissionSchedule(Integer id1,Integer id2,Integer id3,Integer id4,Integer id5);
//   public CommissionScheduleDto findCommissionScheduleDto(int propertyId, int salesRepId);
//
//   public TreeMap<Integer, String> findSubscriberLookupMap(Integer mgmtCompanyId);

}