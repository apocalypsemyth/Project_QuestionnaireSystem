package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserReq;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "user_id")
	@Type(type = "uuid-char")
	private UUID userId;
	
	@Column(name = "questionnaire_id")
	@Type(type = "uuid-char")
	private UUID questionnaireId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private String age;
	
	@Column(name = "answer_date")
	private Date answerDate;
	
	public User() {
	}
	
	public User(PostUserReq postUserReq) {
		this.userId = UUID.fromString(postUserReq.getUserId());
		this.questionnaireId = UUID.fromString(postUserReq.getQuestionnaireId());
		this.userName = postUserReq.getUserName();
		this.phone = postUserReq.getPhone();
		this.email = postUserReq.getEmail();
		this.age = postUserReq.getAge();
		this.answerDate = DateTimeUtil.Func.getDate();
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(UUID questionnaireId) {
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

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
}
