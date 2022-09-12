package com.QuestionnaireProject.QuestionnaireSystem.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.model.BaseRtnInfoProp;

public class QuestionnaireListResp extends BaseRtnInfoProp {
	
	@JsonProperty("questionnaire_list")
	private List<Questionnaire> questionnaireList;
	
	@JsonProperty("total_data")
	private int totalData;
	
	@JsonProperty("total_rows")
	private int totalRows;
	
	@JsonProperty("page_size")
	private int pageSize;
	
	@JsonProperty("page_index")
	private int pageIndex;
	
	public QuestionnaireListResp() {
	}
	
	public QuestionnaireListResp(
			String statusCode, 
			String message, 
			List<Questionnaire> questionnaireList,
			int totalData,
			int totalRows,
			int pageSize,
			int pageIndex
			) {
		super.setStatusCode(statusCode);
		super.setMessage(message);
		this.questionnaireList = questionnaireList;
		this.totalData = totalData;
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
	}

	public List<Questionnaire> getQuestionnaireList() {
		return questionnaireList;
	}

	public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
		this.questionnaireList = questionnaireList;
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
