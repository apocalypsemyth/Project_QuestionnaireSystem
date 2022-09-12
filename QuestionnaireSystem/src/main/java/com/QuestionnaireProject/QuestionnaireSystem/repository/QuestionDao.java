package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, UUID> {
	
	public List<Question> findByQuestionnaireId(UUID questionnaireId);
	
	public List<Question> findByCommonQuestionId(UUID commonQuestionId);

	public List<Question> findByCommonQuestionIdAndIsTemplateOfCommonQuestion(
			UUID commonQuestionId, 
			Boolean isTemplateOfCommonQuestion
			);
	
}
