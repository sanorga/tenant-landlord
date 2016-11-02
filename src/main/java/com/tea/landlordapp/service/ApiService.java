package com.tea.landlordapp.service;

public interface ApiService {

	public void updateApplication(Integer applicationId, Integer userId, String status, String eventText);
}
