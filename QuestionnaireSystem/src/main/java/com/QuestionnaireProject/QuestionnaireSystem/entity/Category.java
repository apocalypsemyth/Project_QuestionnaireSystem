package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@Column(name = "category_id")
	@Type(type = "uuid-char")
	private UUID categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "common_question_id")
	@Type(type = "uuid-char")
	private UUID commonQuestionId;
	
	public Category() {
	}
	
	public Category(CommonQuestionSession commonQuestionSession) {
		this.categoryId = UUID.randomUUID();
		this.categoryName = commonQuestionSession.getCommonQuestionName();
		this.commonQuestionId = commonQuestionSession.getCommonQuestionId();
	}
	
	public Category(String categoryName) {
		this.categoryId = UUID.randomUUID();
		this.categoryName = categoryName;
	}
	
	public Category(CommonQuestion commonQuestion) {
		this.categoryId = UUID.randomUUID();
		this.categoryName = commonQuestion.getCommonQuestionName();
		this.commonQuestionId = commonQuestion.getCommonQuestionId();
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public UUID getCommonQuestionId() {
		return commonQuestionId;
	}

	public void setCommonQuestionId(UUID commonQuestionId) {
		this.commonQuestionId = commonQuestionId;
	}
	
}
