package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserAnswerReq;

public interface UserAnswerService {
	
	/**
	 * 取得使用者回答列表於Session。
	 * @param HttpSession session
	 * @return List 使用者回答列表於Session
	 * @throws 取得使用者回答列表於Session時，發生錯誤。
	 */
	public List<UserAnswer> getUserAnswerList(
			HttpSession session
			) throws Exception;
	
	/**
	 * 取得使用者回答列表，藉由問卷Id。
	 * @param String 問卷Id
	 * @return List 使用者回答列表，藉由問卷Id
	 * @throws 取得使用者回答列表，藉由問卷Id時，發生錯誤。
	 */
	public List<UserAnswer> getUserAnswerList(
			String questionnaireIdStr
			) throws Exception;
	
	/**
	 * 取得使用者回答列表，藉由使用者Id和使用者回答列表。
	 * @param String 使用者Id
	 * @param List 使用者回答列表
	 * @return List 使用者回答列表，藉由使用者Id和使用者回答列表
	 * @throws 取得使用者回答列表，藉由使用者Id和使用者回答列表時，發生錯誤。
	 */
	public List<UserAnswer> getUserAnswerList(
			String userIdStr,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 設置使用者回答列表於Session。
	 * @param HttpSession session
	 * @param List 使用者回答列表
	 * @throws 設置使用者回答列表於Session時，發生錯誤。
	 */
	public void setUserAnswerList(
			HttpSession session,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 創建使用者回答列表於Session，藉由使用者回答請求Body。
	 * @param HttpSession session
	 * @param Object 使用者回答請求Body
	 * @throws 創建使用者回答列表於Session時，發生錯誤。
	 */
	public void createUserAnswerList(
			HttpSession session, 
			PostUserAnswerReq postUserAnswerReq
			) throws Exception;
	
	/**
	 * 刪除全部使用者回答列表於Session。
	 * @param HttpSession session
	 * @throws 刪除全部使用者回答列表於Session時，發生錯誤。
	 */
	public void deleteUserAnswerList(
			HttpSession session
			) throws Exception;
	
}
