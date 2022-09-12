package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;

public class QuestionListResp extends BaseRtnInfoProp {
	
	@JsonProperty("question_session_list")
	private List<QuestionSession> questionSessionList;
	
	public QuestionListResp() {
	}
	
	public QuestionListResp(
			String statusCode, 
			String message, 
			List<QuestionSession> questionSessionList
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.questionSessionList = questionSessionList;
	}

	public List<QuestionSession> getQuestionSessionList() {
		return questionSessionList;
	}

	public void setQuestionSessionList(List<QuestionSession> questionSessionList) {
		this.questionSessionList = questionSessionList;
	}

}
