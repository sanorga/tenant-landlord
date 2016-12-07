package com.tea.landlordapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tea.landlordapp.repository.UserDao;



@Service("adminAppService")
public class AdminAppServiceImpl implements AdminAppService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3204071642418638747L;

	@Autowired
	UserDao userDao;
	
	@Autowired
	MessageService messageService;

	@Override
	public boolean userExists(String username) {
		return userDao.userExists(username);
	}

	@Override
	public boolean sendPasswordResetEmail(String username) {
		try {
			return messageService.sendPasswordResetEmail(username);
		} catch (Exception e) {
			return false;
		}
	}
}
