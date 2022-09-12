package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.util.List;

public class UserAnswerDetailForCSV {
	
	private static List<String> headerList = 
			List.of(
					"名稱", 
					"電話", 
					"Email", 
					"年齡", 
					"回答日期", 
					"問題種類", 
					"問題類別", 
					"問題必填", 
					"問題名稱", 
					"問題答案", 
					"回答編號", 
					"回答"
					);
	
	private String userName;
	
	private String phone;
	
	private String email;
	
	private String age;
	
	private String answerDate;
	
	private String questionCategory;
	
	private String questionTyping;
	
	private String questionRequired;
	
	private String questionName;
	
	private String questionAnswer;
	
	private String answerNum;

	private String answer;
	
	public UserAnswerDetailForCSV() {
	}

	public static List<String> getHeaderList() {
		return headerList;
	}

	public static void setHeaderList(List<String> headerList) {
		UserAnswerDetailForCSV.headerList = headerList;
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

	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
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

	public String getQuestionRequired() {
		return questionRequired;
	}

	public void setQuestionRequired(String questionRequired) {
		this.questionRequired = questionRequired;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(String answerNum) {
		this.answerNum = answerNum;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
