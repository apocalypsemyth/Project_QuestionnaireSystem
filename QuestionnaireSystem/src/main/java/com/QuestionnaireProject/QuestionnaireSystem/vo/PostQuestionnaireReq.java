package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostQuestionnaireReq {
	
	@JsonProperty("questionnaire_id")
	private String questionnaireId;
	
	@JsonProperty("questionnaire_id_list")
	private List<String> questionnaireIdList;
	
	@JsonProperty("caption")
	private String caption;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("is_enable")
	private boolean isEnable;
	
	@JsonProperty("start_date")
	private String startDate;
	
	@JsonProperty("end_date")
	private String endDate;
	
	public PostQuestionnaireReq() {
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<String> getQuestionnaireIdList() {
		return questionnaireIdList;
	}

	public void setQuestionnaireIdList(List<String> questionnaireIdList) {
		this.questionnaireIdList = questionnaireIdList;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
