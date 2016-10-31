package com.tea.landlordapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.dto.ApplicationGridItem;
import com.tea.landlordapp.repository.DtoHelperDao;
import com.tea.landlordapp.repository.mysql.DtoHelperDaoImpl;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {
	@Autowired
	private DtoHelperDao dtoHelperDao;

	@Override
	public List<ApplicationGridItem> findApplicationGridList(User user)
			throws DataAccessException {
		return DtoHelperDaoImpl.findApplicationGridList(user);
	}

	
	
}
