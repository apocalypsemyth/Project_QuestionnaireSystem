package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostQuestionReq {
	
	@JsonProperty("question_id")
	private String questionId;
	
	@JsonProperty("question_id_list")
	private List<String> questionIdList;
	
	@JsonProperty("questionnaire_id")
	private String questionnaireId;
	
	@JsonProperty("question_category")
	private String questionCategory;
	
	@JsonProperty("question_typing")
	private String questionTyping;
	
	@JsonProperty("question_name")
	private String questionName;
	
	@JsonProperty("question_required")
	private boolean questionRequired;
	
	@JsonProperty("question_answer")
	private String questionAnswer;
	
	@JsonProperty("common_question_id")
	private String commonQuestionId;
	
	@JsonProperty("is_template_of_common_question")
	private Boolean isTemplateOfCommonQuestion;
	
	// For setQuestionListOfCommonQuestionOnQuestionnaire
	@JsonProperty("category_id")
	private String categoryId;
	
	public PostQuestionReq() {
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public List<String> getQuestionIdList() {
		return questionIdList;
	}

	public void setQuestionIdList(List<String> questionIdList) {
		this.questionIdList = questionIdList;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getQuestionTyping() {
		return questionTyping;
	}

	public void setQuestionTyping(String questionTyping) {
		this.questionTyping = questionTyping;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public boolean getQuestionRequired() {
		return questionRequired;
	}

	public void setQuestionRequired(boolean questionRequired) {
		this.questionRequired = questionRequired;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getCommonQuestionId() {
		return commonQuestionId;
	}

	public void setCommonQuestionId(String commonQuestionId) {
		this.commonQuestionId = commonQuestionId;
	}
	
	public Boolean getIsTemplateOfCommonQuestion() {
		return isTemplateOfCommonQuestion;
	}

	public void setIsTemplateOfCommonQuestion(Boolean isTemplateOfCommonQuestion) {
		this.isTemplateOfCommonQuestion = isTemplateOfCommonQuestion;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
