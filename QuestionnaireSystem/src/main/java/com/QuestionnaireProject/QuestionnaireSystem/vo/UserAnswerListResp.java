package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAnswerListResp extends BaseRtnInfoProp {
	
	@JsonProperty("user_answer_list")
	private List<UserAnswer> userAnswerList;
	
	public UserAnswerListResp() {
	}
	
	public UserAnswerListResp(
			String statusCode, 
			String message, 
			List<UserAnswer> userAnswerList
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.userAnswerList = userAnswerList;
	}

	public List<UserAnswer> getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List<UserAnswer> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	
}
