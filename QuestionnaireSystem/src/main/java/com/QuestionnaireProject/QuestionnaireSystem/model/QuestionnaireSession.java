package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;

public class QuestionnaireSession extends BaseCUDProp {
	
	private static final long serialVersionUID = 5218569499646740741L;

	private UUID questionnaireId;
	
	private String caption;
	
	private String description;
	
	private boolean isEnable;
	
	private LocalDateTime startDate;
	
	@Nullable
	private LocalDateTime endDate;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	public QuestionnaireSession() {
	}
	
	//For database of questionnaire into questionnaireSession
	public QuestionnaireSession(Questionnaire questionnaire) {
		this.questionnaireId = questionnaire.getQuestionnaireId();
		this.caption = questionnaire.getCaption();
		this.description = questionnaire.getDescription();
		this.isEnable = questionnaire.getIsEnable();
		this.startDate = 
				DateTimeUtil
				.Convert
				.convertDateToLocalDateTime(questionnaire.getStartDate());
		this.endDate = 
				questionnaire.getEndDate() == null
				? null 
				: DateTimeUtil.Convert.convertDateToLocalDateTime(questionnaire.getEndDate());
		this.createDate = 
				DateTimeUtil
				.Convert
				.convertDateToLocalDateTime(questionnaire.getCreateDate());
		this.updateDate = 
				DateTimeUtil
				.Convert
				.convertDateToLocalDateTime(questionnaire.getUpdateDate());
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
	}
	
	//For first create questionnaireSession
	public QuestionnaireSession(
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception {
		this.questionnaireId = UUID.fromString(postQuestionnaireReq.getQuestionnaireId());
		this.caption = postQuestionnaireReq.getCaption();
		this.description = postQuestionnaireReq.getDescription();
		this.isEnable = postQuestionnaireReq.getIsEnable();
		this.startDate = 
				DateTimeUtil
				.Convert
				.convertStringToLocalDateTime(postQuestionnaireReq.getStartDate());
		this.endDate = 
				StringUtils.hasText(postQuestionnaireReq.getEndDate())
				? DateTimeUtil
						.Convert
						.convertStringToLocalDateTime(postQuestionnaireReq.getEndDate())
				: null; 
		this.createDate = DateTimeUtil.Func.getLocalDateTime(true);
		this.updateDate = DateTimeUtil.Func.getLocalDateTime(true);
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
	}
	
	public UUID getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(UUID questionnaireId) {
		this.questionnaireId = questionnaireId;
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
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
	
}
