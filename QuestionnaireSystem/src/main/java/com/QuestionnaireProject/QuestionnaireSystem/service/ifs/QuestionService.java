package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;
import java.util.UUID;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;

public interface QuestionService {
	
	/**
	 * 取得擁有目標問卷或常用問題Id和是否為問卷的問題列表。
	 * @param String 問卷或常用問題Id
	 * @param boolean 是否為問卷
	 * @return List 問題列表
	 * @throws 取得擁有目標問卷或常用問題Id和是否為問卷的問題列表時，發生錯誤。
	 */
	public List<Question> getQuestionList(
			String questionnaireOrCommonQuestionIdStr,
			boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * 取得擁有目標使用者回答的問題列表。
	 * @param List 問題列表
	 * @param List 使用者回答列表
	 * @return List 擁有目標使用者回答的問題列表
	 * @throws 取得擁有目標使用者回答的問題列表時，發生錯誤。
	 */
	public List<Question> getListOfQuestionFromUserAnswer(
			List<Question> questionList,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * 檢查常用問題是否有被問卷套用，藉由常用問題Id。
	 * @param UUID 常用問題Id
	 * @return boolean 常用問題是否有被問卷套用
	 * @throws 檢查常用問題是否有被問卷套用時，發生錯誤。
	 */
	public boolean hasCommonQuestionThatSetByQuestionnaire(
			UUID commonQuestionId
			) throws Exception;
	
}
