package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;

public interface QuestionnaireService {
	
	/**
	 * 取得擁有目標問卷Id的問卷。
	 * @param String 問卷Id
	 * @return Object 擁有目標問卷Id的問卷
	 * @throws 取得擁有目標問卷Id的問卷時，發生錯誤。
	 */
	public Questionnaire getQuestionnaire(
			String questionnaireIdStr
			) throws Exception;
	
	/**
	 * 檢查是否存有問卷。
	 * @return boolean 是否存有問卷
	 * @throws 檢查是否存有問卷時，發生錯誤。
	 */
	public boolean hasQuestionnaire() throws Exception;
	
	/**
	 * 取得存有問卷總數。
	 * @return integer 存有問卷總數
	 * @throws 取得存有問卷總數時，發生錯誤。
	 */
	public int getQuestionnaireTotal() throws Exception;
	
	/**
	 * 檢查是否需要或取消啟用於問卷列表，藉由檢查其開始和結束日期。
	 * @param List 問卷列表
	 * @throws 檢查是否需要或取消啟用於問卷列表時，發生錯誤。
	 */
	public boolean checkDateOfQuestionnaireList(
			List<Questionnaire> questionnaireList
			) throws Exception;
	
	/**
	 * 設置問卷列表的啟用。
	 * @param List 問卷列表
	 * @throws 設置問卷列表的啟用時，發生錯誤。
	 */
	public void setIsEnableOfQuestionnaireList(
			List<Questionnaire> questionnaireList
			) throws Exception;
	
	/**
	 * 取得問卷列表。
	 * @return List 問卷列表
	 * @throws 取得問卷列表時，發生錯誤。
	 */
	public List<Questionnaire> getQuestionnaireList() throws Exception;
	
	/**
	 * 取得符合QueryString條件的為了Pager之資料列表(問卷列表和資料總數)。
	 * @param String 關鍵字
	 * @param String 開始日期
	 * @param String 結束日期
	 * @param int 每頁大小
	 * @param int 頁數
	 * @return Object 符合QueryString條件的為了Pager之資料列表
	 * @throws 取得符合QueryString條件的為了Pager之資料列表時，發生錯誤。
	 */
	public DataListForPager getQuestionnaireList(
			String keyword,
            String startDateStr,
            String endDateStr,
            int pageSize,
            int pageIndex
            ) throws Exception;
	
}
