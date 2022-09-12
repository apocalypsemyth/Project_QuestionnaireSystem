package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostCommonQuestionReq {
	
	@JsonProperty("common_question_id")
	private String commonQuestionId;
	
	@JsonProperty("common_question_id_list")
	private List<String> commonQuestionIdList;
	
	@JsonProperty("common_question_name")
	private String commonQuestionName;
	
	public PostCommonQuestionReq() {
	}

	public String getCommonQuestionId() {
		return commonQuestionId;
	}

	public void setCommonQuestionId(String commonQuestionId) {
		this.commonQuestionId = commonQuestionId;
	}

	public List<String> getCommonQuestionIdList() {
		return commonQuestionIdList;
	}

	public void setCommonQuestionIdList(List<String> commonQuestionIdList) {
		this.commonQuestionIdList = commonQuestionIdList;
	}

	public String getCommonQuestionName() {
		return commonQuestionName;
	}

	public void setCommonQuestionName(String commonQuestionName) {
		this.commonQuestionName = commonQuestionName;
	}

}
