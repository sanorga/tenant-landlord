package com.tea.landlordapp.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.ApplicationDao;
import com.tea.landlordapp.repository.SimpleDao;

@Service("apiService")
public class ApiServiceImpl implements ApiService {

	private ApplicationDao applicationDao;
	
	private SimpleDao simpleDao;
	
//	@Autowired
//	private WorkQueueService workQueueService;

	@Autowired
	public ApiServiceImpl(ApplicationDao applicationDao, SimpleDao simpleDao) {
		this.applicationDao = applicationDao;
		this.simpleDao = simpleDao;
	}

//	@Override
//	public List<SimplikateApplicationListItem> getClientApplicationList(
//			Integer userId, List<Integer> propertyIds) {
//		return apiDao.getClientApplicationList(userId, propertyIds);
//	}
//
//	@Override
//	public void approveApplication(Integer applicationId, Integer userId) {
//		setApprovalFlag(applicationId, userId, true);
//	}
//
//	@Override
//	public void disapproveApplication(Integer applicationId, Integer userId) {
//		setApprovalFlag(applicationId, userId, false);
//	}
//	
//	private void setApprovalFlag(Integer appId, Integer userId, Boolean flag){
//		Application app = apiDao.getClientApplication(appId, userId);
//		if (app == null) return;
//		if (app.getStatus() == ApplicationStatus.COMPLETE) {
//			User user = simpleDao.find(User.class, userId);
//			app.setApproved(flag);
//			app.setApprovalDate(new Date());
//			app.setApprovedBy(user);
//			app = simpleDao.merge(app);
//		}
//	}
	
	@Override
	public boolean updateAppStatusFlag(Integer applicationId, Integer userId, String status, String eventText) {
		return setUpdateAppStatus(applicationId, userId, status, eventText);
	}
	
	private boolean setUpdateAppStatus(Integer appExtId, Integer userId, String status, String eventText) {
		boolean flag = false;
		Application app = applicationDao.findApplicationByExtId(appExtId);
		if (app == null) return flag;
		
		User user = simpleDao.find(User.class, userId);
		if (app.getStatus() != "Cancelled" || app.getStatus() != "Declined" || app.getStatus() != "Approved") {
			if (StringUtils.equals(status, "RenterAccept")) 
					status = "RenterAccepted";
			else if (StringUtils.equals(status, "RenterDecline")) 
					status = "RenterDeclined";
				else if (StringUtils.equals(status, "ApplicationComplete")) {
						status = "Completed";
						app.setCanRequestReport(true);
					 }
			app.setStatus(status);
			app = simpleDao.merge(app,user);
			flag = true;
		}
		return flag;
	}

//	@Override
//	public Integer getUnsignedDocumnents() {
//		List<Integer> apps = workQueueService.getAppsWithUnsignedDocumentCount();
//		if (apps.size() > 0) {
//			workQueueService.getSignedDocuments(apps);
//		}
//		return apps.size();
//	}

}
