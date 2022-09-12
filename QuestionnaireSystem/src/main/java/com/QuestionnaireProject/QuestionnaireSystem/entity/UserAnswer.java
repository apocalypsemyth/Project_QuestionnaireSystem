package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_answer")
public class UserAnswer {
	
	@Id
	@Column(name = "user_answer_id")
	@Type(type = "uuid-char")
	private UUID userAnswerId;
	
	@Column(name = "questionnaire_id")
	@Type(type = "uuid-char")
	private UUID questionnaireId;
	
	@Column(name = "user_id")
	@Type(type = "uuid-char")
	private UUID userId;
	
	@Column(name = "question_id")
	@Type(type = "uuid-char")
	private UUID questionId;
	
	@Column(name = "question_typing")
	private String questionTyping;
	
	@Column(name = "answer_num")
	private Integer answerNum;
	
	@Column(name = "answer")
	private String answer;
	
	public UserAnswer() {
	}

	public UUID getUserAnswerId() {
		return userAnswerId;
	}

	public void setUserAnswerId(UUID userAnswerId) {
		this.userAnswerId = userAnswerId;
	}

	public UUID getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(UUID questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getQuestionId() {
		return questionId;
	}

	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTyping() {
		return questionTyping;
	}

	public void setQuestionTyping(String questionTyping) {
		this.questionTyping = questionTyping;
	}

	public Integer getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
