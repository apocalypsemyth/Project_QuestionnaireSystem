package com.QuestionnaireProject.QuestionnaireSystem.vo;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;

public class UserResp extends BaseRtnInfoProp {
	
	private User user;
	
	public UserResp(String statusCode, String message, User user) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
