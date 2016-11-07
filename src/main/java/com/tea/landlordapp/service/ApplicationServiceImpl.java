package com.tea.landlordapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.Application;
//import com.tea.domain.Subscriber;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.ApplicationGridItem;
import com.tea.landlordapp.repository.ApplicationDao;
import com.tea.landlordapp.repository.DtoHelperDao;
import com.tea.landlordapp.repository.mysql.DtoHelperDaoImpl;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private DtoHelperDao dtoHelperDao;
	
	
	@Override
	public List<ApplicationGridItem> findApplicationGridList(User user, String status, String otherStatus)
			throws DataAccessException {
		return dtoHelperDao.findApplicationGridList(user, status, otherStatus);
	}

	@Override
	public List<Application> findApplicationList(User user) throws DataAccessException {
		return applicationDao.findApplicationList(user);
	}
	
	@Override
	public Application updateApplication(Application application, User loginUser) {
			application.setAuditInfo(loginUser);

		return applicationDao.saveApplication(application);

	}

	
}
