package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, UUID> {
	
	public Category findByCategoryName(String categoryName);
	
	public Category findByCommonQuestionId(UUID commonQuestionId);
	
}
