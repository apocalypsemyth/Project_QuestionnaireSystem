package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserListResp extends BaseRtnInfoProp {
	
	@JsonProperty("user_list")
	private List<User> userList;
	
	@JsonProperty("total_rows")
	private int totalRows;
	
	@JsonProperty("page_index")
	private int pageIndex;
	
	public UserListResp() {
	}
	
	public UserListResp(
			String statusCode, 
			String message, 
			List<User> userList, 
			int totalRows, 
			int pageIndex
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.userList = userList;
		this.totalRows = totalRows;
		this.pageIndex = pageIndex;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
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

}
