package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;

public interface QuestionnaireSessionService {
	
	/**
	 * 取得問卷Session。
	 * @param HttpSession session
	 * @return Object 問卷Session
	 * @throws 取得問卷Session時，發生錯誤。
	 */
	public QuestionnaireSession getQuestionnaireSession(
			HttpSession session
			) throws Exception;
	
	/**
	 * 設置問卷Session。
	 * @param HttpSession session
	 * @param Object 問卷Session
	 * @throws 設置問卷Session時，發生錯誤。
	 */
	public void setQuestionnaireSession(
			HttpSession session,
			QuestionnaireSession questionnaireSession
			) throws Exception;
	
	/**
	 * 創建問卷Session，藉由問卷請求Body。
	 * @param HttpSession session
	 * @param Object 問卷請求Body
	 * @throws 創建問卷Session時，發生錯誤。
	 */
	public void createQuestionnaireSession(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception;
	
	/**
	 * 檢查問卷Session是否改變，藉由問卷請求Body。
	 * @param HttpSession session
	 * @param Object 問卷請求Body
	 * @return boolean 問卷Session是否改變
	 * @throws 檢查問卷Session是否改變時，發生錯誤。
	 */
	public boolean isToUpdateQuestionnaireSessionChange(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception;
	
	/**
	 * 更新問卷Session，藉由問卷請求Body。
	 * @param HttpSession session
	 * @param Object 問卷請求Body
	 * @throws 更新問卷Session時，發生錯誤。
	 */
	public void updateQuestionnaireSession(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception;
	
	/**
	 * 刪除問卷Session。
	 * @param HttpSession session
	 * @throws 刪除問卷Session時，發生錯誤。
	 */
	public void deleteQuestionnaireSession(
			HttpSession session
			) throws Exception;
	
}
