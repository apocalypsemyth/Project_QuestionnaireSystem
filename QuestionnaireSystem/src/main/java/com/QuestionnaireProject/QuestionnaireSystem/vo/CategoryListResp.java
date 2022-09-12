package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;

public class CategoryListResp extends BaseRtnInfoProp {
	
	@JsonProperty("category_list")
	private List<Category> categoryList;
	
	public CategoryListResp() {
	}
	
	public CategoryListResp(
			String statusCode, 
			String message, 
			List<Category> categoryList
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.categoryList = categoryList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
}
