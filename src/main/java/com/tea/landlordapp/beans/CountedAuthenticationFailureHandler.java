package com.tea.landlordapp.beans;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tea.landlordapp.domain.PasswordPolicy;
import com.tea.landlordapp.domain.User;
import com.tea.landlordapp.repository.UserDao;


public class CountedAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
	
	@Autowired
	private UserDao userDao;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		super.onAuthenticationFailure(request, response, exception);
		
		String usernameParameter = usernamePasswordAuthenticationFilter.getUsernameParameter();
		String username = request.getParameter(usernameParameter);
		String acctType = request.getParameter("socialService");
		User user = userDao.findUserWithPolicy(username);
		if (user == null && StringUtils.equals(acctType, "1")) {
			//save account for the first time
		}
		if (user != null){
			PasswordPolicy pol = user.getRole().getPasswordPolicy();
			int maxAttempts = pol.getAttemptsAllowed();
			if (maxAttempts > 0){
				int attempts = user.getLoginFailCount();
				attempts ++;
				if (attempts >= maxAttempts){
					if (pol.getLockoutMinutes() > 0){
						long t = (new Date()).getTime();
						Date lockoutUntil = new Date(t + pol.getLockoutMinutes() * 60000);
						user.setLockoutUntil(lockoutUntil);
					} else {
						user.setStatus('L');
						user.setLockoutUntil(null);
					}
					user.setLoginFailCount(0);
				} else {
					user.setLoginFailCount(attempts);
				}
				userDao.saveUser(user);
			}
		}
		
	}

}
