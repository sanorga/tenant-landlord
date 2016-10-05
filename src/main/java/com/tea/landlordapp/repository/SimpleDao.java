package com.tea.landlordapp.repository;

import java.io.Serializable;

import com.tea.landlordapp.domain.User;

public interface SimpleDao extends Serializable {

	public <T> T merge(T entity);

	public <T> void persist(T entity);

	public <T> T merge(T entity, User user);

	public <T> void persist(T entity, User user);

	public <T> T find(Class<T> clazz, Object object);
	
	public <T> void remove(Class<T> clazz, Integer id);

}
