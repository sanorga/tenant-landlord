package com.tea.landlordapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tea.landlordapp.repository.SimpleDao;
import com.tea.landlordapp.repository.UserDao;

public class MessageServiceImpl implements MessageService {

	
	
	
	protected transient final Logger logger = LoggerFactory
			.getLogger(getClass());


	
	

	private SimpleDao simpleDao;

	private UserDao userDao;

	@Autowired
	public MessageServiceImpl( SimpleDao simpleDao, UserDao userDao ) {
		super();

		this.simpleDao = simpleDao;
		this.userDao = userDao;
		
	}

	@Override
	public boolean sendApplicationClientMail(int appId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
