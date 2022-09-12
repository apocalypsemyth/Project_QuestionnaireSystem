package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import com.QuestionnaireProject.QuestionnaireSystem.model.IForHtmlMethod;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;

@Entity
@Table(name = "question")
public class Question implements Serializable, IForHtmlMethod {
	
	private static final long serialVersionUID = -8318251409231666300L;

	@Id
	@Column(name = "question_id")
	@Type(type = "uuid-char")
	private UUID questionId;
	
	@Column(name = "questionnaire_id")
	@Type(type = "uuid-char")
	private UUID questionnaireId;
	
	@Column(name = "question_category")
	private String questionCategory;
	
	@Column(name = "question_typing")
	private String questionTyping;
	
	@Column(name = "question_name")
	private String questionName;
	
	@Column(name = "question_required")
	private boolean questionRequired;
	
	@Column(name = "question_answer")
	private String questionAnswer;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "common_question_id")
	@Type(type = "uuid-char")
	@Nullable
	private UUID commonQuestionId;
	
	@Column(name = "is_template_of_common_question")
	@Nullable
	private Boolean isTemplateOfCommonQuestion;
	
	public Question() {
	}
	
	public Question(QuestionSession questionSession) {
		this.questionId = questionSession.getQuestionId();
		this.questionnaireId = questionSession.getQuestionnaireId();
		this.questionCategory = questionSession.getQuestionCategory();
		this.questionTyping = questionSession.getQuestionTyping();
		this.questionName = questionSession.getQuestionName();
		this.questionRequired = questionSession.getQuestionRequired();
		this.questionAnswer = questionSession.getQuestionAnswer();
		this.createDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(questionSession.getCreateDate());
		this.updateDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(questionSession.getUpdateDate());
		this.commonQuestionId = 
				questionSession.getCommonQuestionId() == null 
				? null 
				: questionSession.getCommonQuestionId();
		this.isTemplateOfCommonQuestion = 
				questionSession.getIsTemplateOfCommonQuestion() == null 
				? null 
				: questionSession.getIsTemplateOfCommonQuestion();
	}

	public UUID getQuestionId() {
		return questionId;
	}

	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}

	public UUID getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(UUID questionnaireId) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public UUID getCommonQuestionId() {
		return commonQuestionId;
	}

	public void setCommonQuestionId(UUID commonQuestionId) {
		this.commonQuestionId = commonQuestionId;
	}

	public Boolean getIsTemplateOfCommonQuestion() {
		return isTemplateOfCommonQuestion;
	}

	public void setIsTemplateOfCommonQuestion(Boolean isTemplateOfCommonQuestion) {
		this.isTemplateOfCommonQuestion = isTemplateOfCommonQuestion;
	}
	
}
