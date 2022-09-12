package com.QuestionnaireProject.QuestionnaireSystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;

public class QuestionnaireResp extends BaseRtnInfoProp {
	
	@JsonProperty("questionnaire_session")
	private QuestionnaireSession questionnaireSession;
	
	public QuestionnaireResp() {
	}
	
	public QuestionnaireResp(
			String statusCode, 
			String message, 
			QuestionnaireSession questionnaireSession
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.questionnaireSession = questionnaireSession;
	}

	public QuestionnaireSession getQuestionnaireSession() {
		return questionnaireSession;
	}

	public void setQuestionnaireSession(QuestionnaireSession questionnaireSession) {
		this.questionnaireSession = questionnaireSession;
	}

}
