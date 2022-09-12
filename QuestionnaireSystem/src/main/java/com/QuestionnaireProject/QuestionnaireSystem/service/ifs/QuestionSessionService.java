package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionReq;

public interface QuestionSessionService {
	
	/**
	 * 取得擁有目標問題Id的問題Session。
	 * @param HttpSession session
	 * @param String 問題Id
	 * @return Object 擁有目標問題Id的問題Session
	 * @throws 取得擁有目標問題Id的問題Session時，發生錯誤。
	 */
	public QuestionSession getQuestionSession(
			HttpSession session,
			String questionIdStr
			) throws Exception;
	
	/**
	 * 取得問題Session列表。
	 * @param HttpSession session
	 * @return List 問題Session列表
	 * @throws 取得問題Session列表時，發生錯誤。
	 */
	public List<QuestionSession> getQuestionSessionList(
			HttpSession session
			) throws Exception;
	
	/**
	 * 取得問題Session列表，藉由問題列表。
	 * @param List 問題列表
	 * @return List 問題Session列表
	 * @throws 取得問題Session列表，藉由問題列表時，發生錯誤。
	 */
	public List<QuestionSession> getQuestionSessionList(
			List<Question> questionList
			) throws Exception;
	
	/**
	 * 設置問題Session列表，藉由問題Session列表。
	 * @param HttpSession session
	 * @param List 問題Session列表
	 * @throws 設置問題Session列表時，發生錯誤。
	 */
	public void setQuestionSessionList(
			HttpSession session,
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 創建問題Session，藉由問題請求Body和是否為問卷詳細頁面。
	 * @param HttpSession session
	 * @param Object 問題請求Body
	 * @param boolean 是否為問卷詳細頁面
	 * @throws 創建問題Session時，發生錯誤。
	 */
	public void createQuestionSession(
			HttpSession session,
			PostQuestionReq postQuestionReq,
			boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * 更新問題Session，藉由問題請求Body。
	 * @param HttpSession session
	 * @param Object 問題請求Body
	 * @throws 更新問題Session時，發生錯誤。
	 */
	public void updateQuestionSession(
			HttpSession session,
			PostQuestionReq postQuestionReq
			) throws Exception;
	
	/**
	 * 刪除問卷Session列表，藉由問題請求Body。
	 * @param HttpSession session
	 * @param Object 問題請求Body
	 * @throws 刪除問卷Session列表時，發生錯誤。
	 */
	public void deleteQuestionSessionList(
			HttpSession session,
			PostQuestionReq postQuestionReq
			) throws Exception;
	
	/**
	 * 刪除全部問卷Session列表。
	 * @param HttpSession session
	 * @throws 刪除全部問卷Session列表時，發生錯誤。
	 */
	public void deleteAllQuestionSessionList(
			HttpSession session
			) throws Exception;
	
	/**
	 * 檢查是否存有至少一個必填問題，藉由問題Session列表。
	 * @param List 問題Session列表
	 * @return boolean 是否存有至少一個必填問題
	 * @throws 檢查是否存有至少一個必填問題時，發生錯誤。
	 */
	public boolean hasAtLeastOneRequiredQuestion(
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 檢查是否套用常用問題其之問題列表於問卷上，藉由問題Session列表和是否為更新模式。
	 * @param List 問題Session列表
	 * @param boolean 是否為更新模式
	 * @return boolean 是否套用常用問題其之問題列表於問卷上
	 * @throws 檢查是否套用常用問題其之問題列表於問卷上時，發生錯誤。
	 */
	public boolean isSetQuestionListOfCommonQuestion(
			List<QuestionSession> questionSessionList,
			boolean isUpdateMode
			) throws Exception;
	
	/**
	 * 取得設置Id、IsCreated和IsTemplateOfCommonQuestion屬性的常用問題其問題Session列表，藉由問題Session列表。
	 * @param List 問題Session列表
	 * @return List 設置Id、IsCreated和IsTemplateOfCommonQuestion屬性的常用問題其問題列表
	 * @throws 取得設置Id、IsCreated和IsTemplateOfCommonQuestion屬性的常用問題其問題列表時，發生錯誤。
	 */
	public List<QuestionSession> getSetPropertyOfQuestionListOfCommonQuestion(
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 設置IsDeleted屬性於問卷或常用問題其之問題Session列表。
	 * @param HttpSession session
	 * @param List 問題Session列表
	 * @throws 設置IsDeleted屬性於問卷或常用問題其之問題列表時，發生錯誤。
	 */
	public void setIsDeletedOfQuestionListOrOfCommonQuestion(
			HttpSession session,
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 部分轉換常用問題其問題列表為問卷其問題列表。
	 * @param HttpSession session
	 * @throws 部分轉換常用問題其問題列表為問卷其問題列表時，發生錯誤。
	 */
	public void convertQuestionListOfCommonQuestionToQuestionList(
			HttpSession session
			) throws Exception;
	
	/**
	 * 是否需要轉換常用問題其問題列表為問卷其問題列表，藉由問題Session列表。
	 * @param List 問題Session列表
	 * @throws 是否需要轉換常用問題其問題列表為問卷其問題列表時，發生錯誤。
	 */
	public boolean isNeedToConvertQuestionListOfCommonQuestionToQuestionList(
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 完全轉換常用問題其問題列表為問卷其問題列表，藉由問卷Id和問題Session列表。
	 * @param List 問題Session列表
	 * @throws 完全轉換常用問題其問題列表為問卷其問題列表時，發生錯誤。
	 */
	public List<QuestionSession> convertQuestionListOfCommonQuestionToQuestionList(
			String questionnaireIdStr,
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 取得改變資料Id的常用問題其之問題列表，藉由問卷Id、問題Session列表和是否為更新模式。
	 * @param String 問卷Id
	 * @param List 問題Session列表
	 * @param boolean 是否為更新模式
	 * @return List 改變資料Id的常用問題其之問題列表
	 * @throws 取得改變資料Id的常用問題其之問題列表時，發生錯誤。
	 */
	public List<QuestionSession> getQuestionListOfCommonQuestionForChangedIds(
			String questionnaireIdStr,
			List<QuestionSession> questionSessionList,
			boolean isUpdateMode
			) throws Exception;
	
}
