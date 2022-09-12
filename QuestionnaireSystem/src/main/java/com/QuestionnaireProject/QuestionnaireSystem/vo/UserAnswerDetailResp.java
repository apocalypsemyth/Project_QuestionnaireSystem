package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAnswerDetailResp extends BaseRtnInfoProp {
	
	@JsonProperty("question_session_list")
	private List<QuestionSession> questionSessionList;
	
	private User user;
	
	@JsonProperty("user_answer_list")
	private List<UserAnswer> userAnswerList;
	
	public UserAnswerDetailResp() {
	}
	
	public UserAnswerDetailResp(
			String statusCode, 
			String message, 
			List<QuestionSession> questionSessionList, 
			User user, 
			List<UserAnswer> userAnswerList
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.questionSessionList = questionSessionList;
		this.user = user;
		this.userAnswerList = userAnswerList;
	}

	public List<QuestionSession> getQuestionSessionList() {
		return questionSessionList;
	}

	public void setQuestionSessionList(List<QuestionSession> questionSessionList) {
		this.questionSessionList = questionSessionList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserAnswer> getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List<UserAnswer> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	
}
