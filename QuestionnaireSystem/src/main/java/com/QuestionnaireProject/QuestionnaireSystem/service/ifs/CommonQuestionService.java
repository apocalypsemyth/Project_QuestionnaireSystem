package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;

public interface CommonQuestionService {
	
	/**
	 * 檢查是否存有常用問題。
	 * @return boolean 是否存有常用問題
	 * @throws 檢查是否存有常用問題時，發生錯誤。
	 */
	public boolean hasCommonQuestion() throws Exception;
	
	/**
	 * 取得存有常用問題總數。
	 * @return integer 存有常用問題總數
	 * @throws 取得存有常用問題總數時，發生錯誤。
	 */
	public int getCommonQuestionTotal() throws Exception;
	
	/**
	 * 取得擁有目標常用問題Id的常用問題。
	 * @param String 常用問題Id
	 * @return Object 擁有目標常用問題Id的常用問題
	 * @throws 取得擁有目標常用問題Id的常用問題時，發生錯誤。
	 */
	public CommonQuestion getCommonQuestion(
			String commonQuestionIdStr
			) throws Exception;
	
	/**
	 * 取得符合QueryString條件的為了Pager之資料列表(常用問題列表和資料總數)。
	 * @param String 關鍵字
	 * @param int 每頁大小
	 * @param int 頁數
	 * @return Object 符合QueryString條件的為了Pager之資料列表
	 * @throws 取得符合QueryString條件的為了Pager之資料列表時，發生錯誤。
	 */
	public DataListForPager getCommonQuestionList(
			String keyword, 
			int pageSize, 
			int pageIndex
			) throws Exception;
	
}
