package com.tea.landlordapp.service;

public interface ApiService {


	public boolean updateAppStatusFlag(Integer applicationId, Integer userId, String status, String eventText);
}
