package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;

public class TypingListResp extends BaseRtnInfoProp {
	
	@JsonProperty("typing_list")
	private List<Typing> typingList;
	
	public TypingListResp() {
	}
	
	public TypingListResp(String statusCode, String message, List<Typing> typingList) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.typingList = typingList;
	}

	public List<Typing> getTypingList() {
		return typingList;
	}

	public void setTypingList(List<Typing> typingList) {
		this.typingList = typingList;
	}
	
}
