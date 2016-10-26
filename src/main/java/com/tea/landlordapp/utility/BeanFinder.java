package com.tea.landlordapp.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tea.landlordapp.service.CryptoService;

@Component
public class BeanFinder implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	public static CryptoService getCryptoService() {
		return (CryptoService) applicationContext.getBean("cryptoService");
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}

}
