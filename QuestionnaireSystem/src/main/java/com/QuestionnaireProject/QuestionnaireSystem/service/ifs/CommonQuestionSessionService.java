package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostCommonQuestionReq;

public interface CommonQuestionSessionService {
	
	/**
	 * 取得常用問題Session。
	 * @param HttpSession session
	 * @return Object 常用問題Session
	 * @throws 取得常用問題Session時，發生錯誤。
	 */
	public CommonQuestionSession getCommonQuestionSession(
			HttpSession session
			) throws Exception;
	
	/**
	 * 設置常用問題Session，藉由常用問題Session。
	 * @param HttpSession session
	 * @param Object 常用問題Session
	 * @throws 設置常用問題Session時，發生錯誤。
	 */
	public void setCommonQuestionSession(
			HttpSession session, 
			CommonQuestionSession commonQuestionSession
			) throws Exception;
	
	/**
	 * 創建常用問題Session，藉由常用問題請求Body。
	 * @param HttpSession session
	 * @param Object 常用問題請求Body
	 * @throws 創建常用問題Session時，發生錯誤。
	 */
	public void createCommonQuestionSession(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception;
	
	/**
	 * 檢查常用問題Session是否改變，藉由常用問題請求Body。
	 * @param HttpSession session
	 * @param Object 常用問題請求Body
	 * @return boolean 常用問題Session是否改變
	 * @throws 檢查常用問題Session是否改變時，發生錯誤。
	 */
	public boolean isToUpdateCommonQuestionSessionChange(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception;
	
	/**
	 * 檢查常用問題其名稱是否為自訂問題，藉由常用問題請求Body。
	 * @param Object 常用問題請求Body
	 * @return boolean 常用問題其名稱是否為自訂問題
	 * @throws 檢查常用問題其名稱是否為自訂問題時，發生錯誤。
	 */
	public boolean whetherNameOfCommonQuestionIsCustomizedQuestion(
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception;
	
	/**
	 * 更新常用問題Session，藉由常用問題請求Body。
	 * @param HttpSession session
	 * @param Object 常用問題請求Body
	 * @throws 更新常用問題Session時，發生錯誤。
	 */
	public void updateCommonQuestionSession(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception;
	
	/**
	 * 刪除常用問題Session。
	 * @param HttpSession session
	 * @throws 刪除常用問題Session時，發生錯誤。
	 */
	public void deleteCommonQuestionSession(
			HttpSession session
			) throws Exception;
	
}
