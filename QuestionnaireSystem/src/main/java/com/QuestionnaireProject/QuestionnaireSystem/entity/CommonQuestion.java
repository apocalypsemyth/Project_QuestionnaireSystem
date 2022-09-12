package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;

@Entity
@Table(name = "common_question")
public class CommonQuestion {
	
	@Id
	@Column(name = "common_question_id")
	@Type(type = "uuid-char")
	private UUID commonQuestionId;
	
	@Column(name = "common_question_name")
	private String commonQuestionName;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	public CommonQuestion() {
	}
	
	public CommonQuestion(CommonQuestionSession commonQuestionSession) {
		this.commonQuestionId = commonQuestionSession.getCommonQuestionId();
		this.commonQuestionName = commonQuestionSession.getCommonQuestionName();
		this.createDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(commonQuestionSession.getCreateDate());
		this.updateDate = 
				DateTimeUtil
				.Convert
				.convertLocalDateTimeToDate(commonQuestionSession.getUpdateDate());
	}
	
	public UUID getCommonQuestionId() {
		return commonQuestionId;
	}

	public void setCommonQuestionId(UUID commonQuestionId) {
		this.commonQuestionId = commonQuestionId;
	}

	public String getCommonQuestionName() {
		return commonQuestionName;
	}

	public void setCommonQuestionName(String commonQuestionName) {
		this.commonQuestionName = commonQuestionName;
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
