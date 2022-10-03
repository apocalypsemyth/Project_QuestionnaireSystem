package com.QuestionnaireProject.QuestionnaireSystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostCategory {
	
	@JsonProperty("category_id")
	private String categoryId;
	
	public PostCategory() {
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
