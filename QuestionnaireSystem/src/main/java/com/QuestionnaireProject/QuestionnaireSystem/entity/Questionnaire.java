package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {
	
	@Id
	@Column(name = "questionnaire_id")
	@Type(type = "uuid-char")
	private UUID questionnaireId;
	
	@Column(name = "caption")
	private String caption;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_enable")
	private boolean isEnable;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	@Nullable
	private Date endDate;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	public Questionnaire() {
	}
	
	public Questionnaire(PostQuestionnaireReq postQuestionnaireReq) throws Exception {
		this.questionnaireId = UUID.fromString(postQuestionnaireReq.getQuestionnaireId());
		this.caption = postQuestionnaireReq.getCaption();
		this.description = postQuestionnaireReq.getDescription();
		this.isEnable = postQuestionnaireReq.getIsEnable();
		this.startDate = 
				DateTimeUtil
				.Convert
				.convertStringToDate(postQuestionnaireReq.getStartDate());
		this.endDate = 
				postQuestionnaireReq.getEndDate() == null
				? null 
				: DateTimeUtil
					.Convert
					.convertStringToDate(postQuestionnaireReq.getEndDate());
		this.createDate = DateTimeUtil.Func.getDate();
		this.updateDate = DateTimeUtil.Func.getDate();
	}
	
	public Questionnaire(QuestionnaireSession questionnaireSession) throws Exception {
		this.questionnaireId = questionnaireSession.getQuestionnaireId();
		this.caption = questionnaireSession.getCaption();
		this.description = questionnaireSession.getDescription();
		this.isEnable = questionnaireSession.getIsEnable();
		this.startDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(questionnaireSession.getStartDate());
		this.endDate = 
				questionnaireSession.getEndDate() == null
				? null 
				: DateTimeUtil
					.Convert
					.convertLocalDateTimeToDate(questionnaireSession.getEndDate());
		this.createDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(questionnaireSession.getCreateDate());
		this.updateDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(questionnaireSession.getUpdateDate());
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
}
