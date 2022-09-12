package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionnaireStatisticsResp extends BaseRtnInfoProp {
	
	@JsonProperty("question_list")
	private List<Question> questionList;
	
	@JsonProperty("user_answer_list")
	private List<UserAnswer> userAnswerList;
	
	public QuestionnaireStatisticsResp() {
	}

	public QuestionnaireStatisticsResp(
			String statusCode, 
			String message, 
			List<Question> questionList, 
			List<UserAnswer> userAnswerList
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.questionList = questionList;
		this.userAnswerList = userAnswerList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public List<UserAnswer> getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List<UserAnswer> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	
}
