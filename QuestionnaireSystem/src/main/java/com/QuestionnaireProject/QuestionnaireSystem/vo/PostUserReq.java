package com.QuestionnaireProject.QuestionnaireSystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostUserReq {
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("questionnaire_id")
	private String questionnaireId;
	
	@JsonProperty("user_name")
	private String userName;
	
	private String phone;
	
	private String email;
	
	private String age;
	
	@JsonProperty("str_index")
	private String strIndex;
	
	public PostUserReq() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getStrIndex() {
		return strIndex;
	}
	
	public void setStrIndex(String strIndex) {
		this.strIndex = strIndex;
	}

}
