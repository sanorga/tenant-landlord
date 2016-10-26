package com.tea.landlordapp.repository;

import java.util.List;

import com.tea.landlordapp.domain.Applicant;

public interface ApplicantDao {
	public List<Applicant> findDuplicateApplicant(Integer propertyId, String pseudoId, int daysOld);
}
