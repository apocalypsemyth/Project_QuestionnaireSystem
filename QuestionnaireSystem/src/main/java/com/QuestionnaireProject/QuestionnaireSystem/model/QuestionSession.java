package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.Nullable;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionReq;

public class QuestionSession extends BaseCUDProp implements IForHtmlMethod {
	
	private static final long serialVersionUID = 7630012462234510566L;
	
	private UUID questionId;
	
	private UUID questionnaireId;
	
	private String questionCategory;
	
	private String questionTyping;
	
	private String questionName;
	
	private boolean questionRequired;
	
	private String questionAnswer;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	@Nullable
	private UUID commonQuestionId;
	
	@Nullable
	private Boolean isTemplateOfCommonQuestion;
	
	public QuestionSession() {
	}
	
	public QuestionSession(Question question) {
		this.questionId = question.getQuestionId();
		this.questionnaireId = question.getQuestionnaireId();
		this.questionCategory = question.getQuestionCategory();
		this.questionTyping = question.getQuestionTyping();
		this.questionName = question.getQuestionName();
		this.questionRequired = question.getQuestionRequired();
		this.questionAnswer = question.getQuestionAnswer();
		this.createDate = DateTimeUtil.Convert.convertDateToLocalDateTime(question.getCreateDate());
		this.updateDate = DateTimeUtil.Convert.convertDateToLocalDateTime(question.getUpdateDate());
		this.commonQuestionId = 
				question.getCommonQuestionId() == null 
				? null 
				: question.getCommonQuestionId();
		this.isTemplateOfCommonQuestion = 
				question.getIsTemplateOfCommonQuestion() == null 
				? null 
				: question.getIsTemplateOfCommonQuestion();
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
	}
	
	public QuestionSession(PostQuestionReq postQuestionReq, boolean isQuestionnaire) {
		this.questionId = UUID.randomUUID();
		this.questionnaireId = 
				isQuestionnaire 
				? UUID.fromString(postQuestionReq.getQuestionnaireId()) 
				: UUID.randomUUID();
		this.questionCategory = postQuestionReq.getQuestionCategory();
		this.questionTyping = postQuestionReq.getQuestionTyping();
		this.questionName = postQuestionReq.getQuestionName();
		this.questionRequired = postQuestionReq.getQuestionRequired();
		this.questionAnswer = postQuestionReq.getQuestionAnswer();
		this.createDate = DateTimeUtil.Func.getLocalDateTime(true);
		this.updateDate = DateTimeUtil.Func.getLocalDateTime(true);
		this.commonQuestionId = 
				postQuestionReq.getCommonQuestionId() == null 
				? null 
				: UUID.fromString(postQuestionReq.getCommonQuestionId());
		this.isTemplateOfCommonQuestion = 
				postQuestionReq.getIsTemplateOfCommonQuestion() == null 
				? null 
				: postQuestionReq.getIsTemplateOfCommonQuestion();
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
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
