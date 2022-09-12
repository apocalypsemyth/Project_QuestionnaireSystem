package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;

public class DataListForPager {
	
	private boolean hasData;
	
	private int totalRows;
	
	private int pageIndex;
	
	private List<Questionnaire> questionnaireList;
	
	private List<CommonQuestion> commonQuestionList;
	
	private List<User> userList;
	
	public DataListForPager() {
	}

	public boolean getHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<Questionnaire> getQuestionnaireList() {
		return questionnaireList;
	}

	public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
		this.questionnaireList = questionnaireList;
	}

	public List<CommonQuestion> getCommonQuestionList() {
		return commonQuestionList;
	}

	public void setCommonQuestionList(List<CommonQuestion> commonQuestionList) {
		this.commonQuestionList = commonQuestionList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
