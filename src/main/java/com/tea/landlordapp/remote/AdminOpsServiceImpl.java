package com.tea.landlordapp.remote;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duosecurity.duoweb.DuoParams;
import com.duosecurity.duoweb.DuoWeb;
import com.duosecurity.duoweb.DuoWebException;
import com.tea.landlordapp.dto.TeaUserDetails;
import com.tea.landlordapp.repository.UserDao;
//import com.tea.landlordapp.service.WorkQueueService;
import com.tea.landlordapp.service.SecurityService;

@Service("adminOpsService")
public class AdminOpsServiceImpl implements AdminOpsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683379913107507191L;
	
	@Autowired
	UserDao userDao;
	
//	@Autowired
//	WorkQueueService workQueueService;
	
	@Autowired
	SecurityService securityService;

	@Override
	public String getDuoRequest(String username) {
		String sigRequest = "";

			sigRequest = DuoWeb.signRequest(DuoParams.ikey,
					DuoParams.skey,
					DuoParams.akey,
					username);
		
		return sigRequest;
	}

//	@Override
//	public Integer fetchUnsignedDocuments() {
//		List<Integer> apps = workQueueService.getAppsWithUnsignedDocumentCount();
//		if (apps.size() > 0) {
//			workQueueService.getSignedDocuments(apps);
//		}
//		return apps.size();
//	}

	public boolean verifyDuoLogin(String username, String sigResponse) {
		// TODO Auto-generated method stub
		String verResponse;
		try {
			verResponse = DuoWeb.verifyResponse(DuoParams.ikey, DuoParams.skey, DuoParams.akey, sigResponse);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| DuoWebException | IOException e) {
			logger.error("Duo authentication threw an exception for " + username);
			return false;
		}
		
		return StringUtils.equalsIgnoreCase(username, verResponse);
	}

	@Override
	public void updateCurrentAuthorities(Boolean mfaValid) {
		securityService.updateCurrentAuthorities(mfaValid);
	}

}
