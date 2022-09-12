package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostUserAnswerReq {
	
	@JsonProperty("questionnaire_id")
	private String questionnaireId;

	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("user_answer_string_list")
	private List<String> userAnswerStringList;
	
	public PostUserAnswerReq() {
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getUserAnswerStringList() {
		return userAnswerStringList;
	}

	public void setUserAnswerStringList(List<String> userAnswerStringList) {
		this.userAnswerStringList = userAnswerStringList;
	}

}
