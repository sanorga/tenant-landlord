package com.tea.landlordapp.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tea.landlordapp.domain.Application;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.ApplicationGridItem;

public interface ApplicationService {

	public List<ApplicationGridItem> findApplicationGridList(User user, String status, String otherStatus)
			throws DataAccessException;

	public List<Application> findApplicationList(User user) throws DataAccessException;

	public Application updateApplication(Application application, User loginUser);

}
