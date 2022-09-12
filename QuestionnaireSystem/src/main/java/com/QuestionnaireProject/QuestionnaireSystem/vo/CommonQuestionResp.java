package com.QuestionnaireProject.QuestionnaireSystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;

public class CommonQuestionResp extends BaseRtnInfoProp {
	
	@JsonProperty("common_question_session")
	private CommonQuestionSession commonQuestionSession;
	
	public CommonQuestionResp() {
	}
	
	public CommonQuestionResp(
			String statusCode, 
			String message, 
			CommonQuestionSession commonQuestionSession) {
		super.setStatusCode(statusCode);
		this.setMessage(message);
		this.commonQuestionSession = commonQuestionSession;
	}

	public CommonQuestionSession getCommonQuestionSession() {
		return commonQuestionSession;
	}

	public void setCommonQuestionSession(CommonQuestionSession commonQuestionSession) {
		this.commonQuestionSession = commonQuestionSession;
	}

}
