package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import org.springframework.ui.Model;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;

public interface ModelService {
	
	/**
	 * 設置擁有目標Fragment名稱的Fragment。
	 * @param Model model
	 * @param String Fragment名稱
	 * @return Model model
	 * @throws 設置擁有目標Fragment名稱的Fragment時，發生錯誤。
	 */
	public Model setFragmentName(
			Model model, 
			String fragmentName
			) throws Exception;
	
	/**
	 * 設置資料(問卷或常用問題)列表頁面之顯示，藉由是否為後台和問卷頁面。
	 * @param Model model
	 * @param boolean 是否為後台頁面
	 * @param boolean 是否為問卷頁面
	 * @return Model model
	 * @throws 設置資料(問卷或常用問題)列表頁面之顯示時，發生錯誤。
	 */
	public Model setDataListModel(
			Model model, 
			Boolean isBackAdmin, 
			Boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * 設置前台頁面側邊連結之顯示，藉由是否為問卷列表頁面。
	 * @param Model model
	 * @param boolean 是否為問卷列表頁面
	 * @return Model model
	 * @throws 設置前台頁面側邊連結之顯示時，發生錯誤。
	 */
	public Model setIsQuestionnaireList(
			Model model, 
			boolean isQuestionnaireList
			) throws Exception;
	
	/**
	 * 設置使用者Model，以顯示使用者資訊於前台確認問卷頁面。
	 * @param Model model
	 * @param Object 使用者
	 * @return Model model
	 * @throws 設置使用者Model時，發生錯誤。
	 */
	public Model setUserModel(
			Model model,
			User user
			) throws Exception;
	
	/**
	 * 設置使用者列表Model，以顯示使用者列表於後台問卷詳細頁面之使用者分頁。
	 * @param Model model
	 * @param List 使用者列表
	 * @return Model model
	 * @throws 設置使用者列表Model時，發生錯誤。
	 */
	public Model setUserListModel(
			Model model,
			List<User> userList
			) throws Exception;
	
	/**
	 * 設置使用者回答列表Model，以顯示使用者回答資訊於前台確認問卷或後台問卷詳細頁面之使用者分頁。
	 * @param Model model
	 * @param List 使用者回答列表
	 * @return Model model
	 * @throws 設置使用者回答列表Model時，發生錯誤。
	 */
	public Model setUserAnswerListModel(
			Model model,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 設置問卷Model，以顯示問卷資訊於前台回答、確認或統計問卷頁面。
	 * @param Model model
	 * @param Object 問卷
	 * @return Model model
	 * @throws 設置問卷Model時，發生錯誤。
	 */
	public Model setQuestionnaireModel(
			Model model, 
			Questionnaire questionnaire
			) throws Exception;
	
	/**
	 * 設置問卷Model，以顯示問卷資訊於後台問卷詳細頁面。
	 * @param Model model
	 * @param Object 問卷Session
	 * @return Model model
	 * @throws 設置問卷Model時，發生錯誤。
	 */
	public Model setQuestionnaireModel(
			Model model, 
			QuestionnaireSession questionnaireSession
			) throws Exception;
	
	/**
	 * 設置常用問題Model，以顯示常用問題資訊於後台常用問題詳細頁面。
	 * @param Model model
	 * @param Object 常用問題Session
	 * @return Model model
	 * @throws 設置常用問題Model時，發生錯誤。
	 */
	public Model setCommonQuestionModel(
			Model model,
			CommonQuestionSession commonQuestionSession
			) throws Exception;
	
	/**
	 * 設置問題列表Model，以顯示問題資訊於前台回答或確認問卷頁面。
	 * @param Model model
	 * @param List 問題列表
	 * @return Model model
	 * @throws 設置問題列表Model時，發生錯誤。
	 */
	public Model setQuestionListModel(
			Model model,
			List<Question> questionList
			) throws Exception;
	
	/**
	 * 設置問題列表Model，藉由問題Session，以顯示問題列表於後台問卷或常用問題詳細頁面。
	 * @param Model model
	 * @param List 問題Session列表
	 * @return Model model
	 * @throws 設置問題列表Model，藉由問題Session時，發生錯誤。
	 */
	public Model setQuestionListModelWithQuestionSession(
			Model model,
			List<QuestionSession> questionSessionList
			) throws Exception;
	
	/**
	 * 設置啟用後台問卷詳細頁面的控制項與否，藉由是否過了結束日期或已有使用者於問卷。
	 * @param Model model
	 * @param boolean 是否過了結束日期或已有使用者於問卷
	 * @return Model model
	 * @throws 設置啟用後台問卷詳細頁面的控制項與否時，發生錯誤。
	 */
	public Model setIsOverDateOrHasUser(
			Model model,
			boolean isOverDateOrHasUser
			) throws Exception;
	
	/**
	 * 設置啟用後台常用問題詳細頁面的控制項與否，藉由常用問題是否有被問卷套用。
	 * @param Model model
	 * @param boolean 常用問題是否有被問卷套用
	 * @return Model model
	 * @throws 設置啟用後台常用問題詳細頁面的控制項與否時，發生錯誤。
	 */
	public Model setHasCommonQuestionThatSetByQuestionnaire(
			Model model, 
			boolean hasCommonQuestionThatSetByQuestionnaire
			) throws Exception;
	
}
