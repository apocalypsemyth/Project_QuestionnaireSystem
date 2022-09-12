package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostCommonQuestionReq;

public class CommonQuestionSession extends BaseCUDProp {
	
	private static final long serialVersionUID = -8933000423761437522L;

	private UUID commonQuestionId;
	
	private String commonQuestionName;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	public CommonQuestionSession() {
	}
	
	//For database of commonQuestion into commonQuestionSession
	public CommonQuestionSession(CommonQuestion commonQuestion) {
		this.commonQuestionId = commonQuestion.getCommonQuestionId();
		this.commonQuestionName = commonQuestion.getCommonQuestionName();
		this.createDate = 
				DateTimeUtil
				.Convert
				.convertDateToLocalDateTime(commonQuestion.getCreateDate());
		this.updateDate = 
				DateTimeUtil
				.Convert
				.convertDateToLocalDateTime(commonQuestion.getUpdateDate());
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
	}
	
	//For first create commonQuestionSession
	public CommonQuestionSession(PostCommonQuestionReq postCommonQuestionReq) {
		this.commonQuestionId = UUID.fromString(postCommonQuestionReq.getCommonQuestionId());
		this.commonQuestionName = postCommonQuestionReq.getCommonQuestionName();
		this.createDate = DateTimeUtil.Func.getLocalDateTime(true);
		this.updateDate = DateTimeUtil.Func.getLocalDateTime(true);
		super.setIsCreated(false);
		super.setIsUpdated(false);
		super.setIsDeleted(false);
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
