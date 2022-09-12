package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, UUID> {
	
	public List<Questionnaire> findByCaptionContaining(String keyword, Pageable pageable);
	
	@Query("select q "
			+ "from Questionnaire q "
			+ "where q.endDate is null and q.startDate >= :startDate and q.startDate < :endDatePlus1 "
			+ "or q.startDate >= :startDate and q.endDate < :endDatePlus1")
	public List<Questionnaire> filterByStartAndEndDate(
			@Param("startDate") Date startDate, 
			@Param("endDatePlus1") Date endDatePlus1,
			Pageable pageable
			);
	
}
