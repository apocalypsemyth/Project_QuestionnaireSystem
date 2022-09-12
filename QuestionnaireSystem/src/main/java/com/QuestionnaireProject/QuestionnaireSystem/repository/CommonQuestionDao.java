package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;

@Repository
public interface CommonQuestionDao extends JpaRepository<CommonQuestion, UUID> {

	public List<CommonQuestion> findByCommonQuestionNameContaining(
			String commonQuestionName, 
			Pageable pageable
			);
	
}
