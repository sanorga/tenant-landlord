package com.tea.landlordapp.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

public class AuthenticationEventLogger implements org.springframework.context.ApplicationListener<AbstractAuthenticationEvent> {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationEventLogger.class);
	
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		// TODO Auto-generated method stub
		Authentication authentication = event.getAuthentication();
		
		if (event instanceof AbstractAuthenticationFailureEvent){
			
			if (event instanceof AuthenticationFailureBadCredentialsEvent){
				logger.warn("Login An attempt was made to log in with bad credentials for user: " + authentication.getName());
			}
			if (event instanceof AuthenticationFailureCredentialsExpiredEvent){
				logger.warn("Login An attempt was made to log in with expired credentials for user: " + authentication.getName());
			}
			if (event instanceof AuthenticationFailureDisabledEvent){
				logger.warn("Login An attempt was made to log in by disabled user: " + authentication.getName());			
			}
			if (event instanceof AuthenticationFailureExpiredEvent){
				logger.warn("Login An attempt was made to log in by expired user: " + authentication.getName());	
			}
			if (event instanceof AuthenticationFailureLockedEvent){
				logger.warn("Login An attempt was made to log in by locked user: " + authentication.getName());	
			}
			if (event instanceof AuthenticationFailureProviderNotFoundEvent){
				logger.error("Login Authentication provider not found trying to log in user: " + authentication.getName());
			}
			if (event instanceof AuthenticationFailureServiceExceptionEvent){
				logger.error(String.format("Login Service exception trying to log in user %s. %s", authentication.getName(), ((AbstractAuthenticationFailureEvent) event).getException().getMessage()));
			}
		}

		if (event instanceof AuthenticationSuccessEvent){
			logger.info("Login successful by user: " + authentication.getName());
		}
		
	}

}
