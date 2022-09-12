package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;

@Repository
public interface UserAnswerDao extends JpaRepository<UserAnswer, UUID> {
	
	public List<UserAnswer> findByQuestionnaireIdAndUserId(UUID questionnaireId, UUID userId); 
	
	public List<UserAnswer> findByQuestionnaireId(UUID questionnaireId);
	
}
