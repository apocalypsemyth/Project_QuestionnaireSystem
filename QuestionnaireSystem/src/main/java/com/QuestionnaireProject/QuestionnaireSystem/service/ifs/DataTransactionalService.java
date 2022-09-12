package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.UserAnswerDetailForCSV;

public interface DataTransactionalService {
	
	/**
	 * 儲存問卷的使用者和使用者回答列表。
	 * @param Object 使用者
	 * @param List 使用者回答列表
	 * @throws 儲存問卷的使用者和使用者回答列表時，發生錯誤。
	 */
	public void saveUserAndUserAnswerList(
			User user, 
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 儲存問卷和其問題列表。
	 * @param Object 問卷Session
	 * @param List 問題Session列表
	 * @throws 儲存問卷和其問題列表時，發生錯誤。
	 */
	public void saveQuestionnaire(
			QuestionnaireSession questionnaireSession, 
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 儲存常用問題、其問題列表和其問題種類。
	 * @param Object 常用問題Session
	 * @param List 問題Session列表
	 * @throws 儲存常用問題、其問題列表和其問題種類時，發生錯誤。
	 */
	public void saveCommonQuestion(
			CommonQuestionSession commonQuestionSession,
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 刪除問卷、其問題、其使用者和其使用者回答列表。
	 * @param List 問卷Id列表
	 * @throws 刪除問卷、其問題、其使用者和其使用者回答時，發生錯誤。
	 */
	public void deleteQuestionnaireList(
			List<String> questionnaireIdListStr
			) throws Exception;
	
	/**
	 * 刪除常用問題、其問題和其問題種類列表。
	 * @param List 常用問題Id列表
	 * @throws 刪除常用問題、其問題或其問題種類時，發生錯誤。
	 */
	public void deleteCommonQuestionList(
			List<String> commonQuestionIdListStr
			) throws Exception;
	
	/**
	 * 檢查是否為合法的QueryString。
	 * @param HttpServletRequest request
	 * @param boolean 是否為問卷列表頁面
	 * @return boolean 是否為合法的QueryString
	 * @throws 檢查是否為合法的QueryString時，發生錯誤。
	 */
	public boolean isValidQueryString(
			HttpServletRequest request,
			boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * 檢查問卷是否過了結束日期或已有使用者的回答。
	 * @param Object 問卷Session
	 * @param List 使用者列表
	 * @return boolean 問卷是否過了結束日期或已有使用者的回答
	 * @throws 檢查問卷是否過了結束日期或已有使用者的回答時，發生錯誤。
	 */
	public boolean isOverDateOrHasUser(
			QuestionnaireSession questionnaireSession, 
			List<User> userListInSession
			) throws Exception;
	
	/**
	 * 取得為了CSV之使用者回答詳細列表，藉由問題、使用者和使用者回答列表。
	 * @param List 問題Session列表
	 * @param List 使用者列表
	 * @param List 使用者回答列表
	 * @return List 使用者回答詳細列表
	 * @throws 取得為了CSV之使用者回答詳細列表時，發生錯誤。
	 */
	public List<UserAnswerDetailForCSV> getUserAnswerDetailForCSVList(
			List<QuestionSession> questionSessionList,
			List<User> userList,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 取得CSV檔案，藉由為了CSV之使用者回答詳細列表。
	 * @param List 為了CSV之使用者回答詳細列表
	 * @return ByteArrayInputStream CSV檔案
	 * @throws 取得CSV檔案時，發生錯誤。
	 */
	public ByteArrayInputStream getDataToCSV(
			List<UserAnswerDetailForCSV> userAnswerDetailForCSVList
			) throws Exception;
	
}
