package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;

public class CommonQuestionListResp extends BaseRtnInfoProp {
	
	@JsonProperty("common_question_list")
	private List<CommonQuestion> commonQuestionList;
	
	@JsonProperty("total_data")
	private int totalData;
	
	@JsonProperty("total_rows")
	private int totalRows;
	
	@JsonProperty("page_size")
	private int pageSize;
	
	@JsonProperty("page_index")
	private int pageIndex;
	
	public CommonQuestionListResp() {
	}
	
	public CommonQuestionListResp(
			String statusCode, 
			String message, 
			List<CommonQuestion> commonQuestionList,
			int totalData,
			int totalRows,
			int pageSize,
			int pageIndex
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.commonQuestionList = commonQuestionList;
		this.totalData = totalData;
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
	}

	public List<CommonQuestion> getCommonQuestionList() {
		return commonQuestionList;
	}

	public void setCommonQuestionList(List<CommonQuestion> commonQuestionList) {
		this.commonQuestionList = commonQuestionList;
	}

	public int getTotalData() {
		return totalData;
	}

	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
