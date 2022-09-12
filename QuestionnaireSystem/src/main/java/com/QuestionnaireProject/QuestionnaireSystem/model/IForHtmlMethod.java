package com.QuestionnaireProject.QuestionnaireSystem.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;

public interface IForHtmlMethod {
	
	/**
	 * 取得每個問題的使用者回答列表。
	 * @param String 問題Id
	 * @param List 使用者回答列表
	 * @return List 每個問題的使用者回答列表
	 */
	default public List<UserAnswer> getQuestionItsUserAnswerList(
			String questionIdStr, 
			List<UserAnswer> userAnswerList
			) {
		UUID questionId = UUID.fromString(questionIdStr);
		List<UserAnswer> questionItsOrAlsoUserItsUserAnswerList =
				userAnswerList
				.stream()
				.filter(item -> item.getQuestionId().equals(questionId))
				.collect(Collectors.toList());
		if (questionItsOrAlsoUserItsUserAnswerList == null 
				|| questionItsOrAlsoUserItsUserAnswerList.isEmpty()) return null;
		return questionItsOrAlsoUserItsUserAnswerList;
	}
	
	/**
	 * 取得每個問題其答案的使用者回答。
	 * @param integer 問題其問題答案Index
	 * @param String 問題Id
	 * @param List 使用者回答列表
	 * @return String 每個問題其答案的使用者回答
	 */
	default public String getEachUserAnswer(
			int questionAnswerIndex,
			String questionIdStr,
			List<UserAnswer> userAnswerList
			) {
		if (userAnswerList == null || userAnswerList.isEmpty()) return null;
		
		int questionAnswerIndexPlus1 = questionAnswerIndex + 1;
		List<UserAnswer> questionItsUserAnswerList = 
				getQuestionItsUserAnswerList(questionIdStr, userAnswerList);
		if (questionItsUserAnswerList == null) return null;
		
		Optional<UserAnswer> eachUserAnswerOp = 
				questionItsUserAnswerList
				.stream()
				.filter(item -> item.getAnswerNum() == questionAnswerIndexPlus1)
				.findFirst();
		if (!eachUserAnswerOp.isPresent()) return null;
		
		UserAnswer eachUserAnswer = eachUserAnswerOp.get();
		if (eachUserAnswer.getQuestionTyping().equals(DataConstant.Key.TEXT)) {
			return eachUserAnswer.getAnswer();
		}
		return "checked";
	}
	
	/**
	 * 檢查問卷或常用問題其問題Session列表是否全部被標記IsDeleted。
	 * @param List 問卷或常用問題Session列表
	 * @return boolean 問卷或常用問題其問題Session列表是否全部被標記IsDeleted
	 */
	default public boolean areAllQuestionSessionListIsDeleted(
			List<QuestionSession> questionSessionList
			) {
		List<QuestionSession> originalQuestionSessionList = 
				questionSessionList
				.stream()
				.filter(item -> !item.getIsCreated())
				.collect(Collectors.toList());
		if (originalQuestionSessionList == null 
				|| originalQuestionSessionList.isEmpty()) {
			return (questionSessionList == null || questionSessionList.isEmpty());
		}
		
		List<QuestionSession> otherQuestionSessionList = 
				questionSessionList
				.stream()
				.filter(item -> item.getIsCreated())
				.collect(Collectors.toList());
		boolean isAllOriginDeleted =
				originalQuestionSessionList
				.stream()
				.map(QuestionSession::getIsDeleted)
				.allMatch(item -> item);
		if (otherQuestionSessionList == null || otherQuestionSessionList.isEmpty()) {
			return isAllOriginDeleted;
		}
		boolean isAllOtherDeleted =
				otherQuestionSessionList
				.stream()
				.map(QuestionSession::getIsDeleted)
				.allMatch(item -> item);
		return (isAllOriginDeleted && isAllOtherDeleted);
	}
	
	/**
	 * 取得每個問題的使用者回答之回答編號統計Map。
	 * @param String 問題Id
	 * @param List 使用者回答列表
	 * @return Map 每個問題的使用者回答之回答編號統計Map
	 */
	default public Map<Integer, Double> getAnswerNumStatMap(
			String questionIdStr, 
			List<UserAnswer> userAnswerList) {
		List<UserAnswer> questionItsUserAnswerList = 
				getQuestionItsUserAnswerList(questionIdStr, userAnswerList);
		if (questionItsUserAnswerList == null 
				|| questionItsUserAnswerList.isEmpty()) return null;
		Map<Integer, Double> answerNumStatMap =
			questionItsUserAnswerList
			.stream()
			.collect(
					Collectors.groupingBy(UserAnswer::getAnswerNum, 
							Collectors.counting())
					)
			.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().doubleValue()));;
		return answerNumStatMap;
	}
	
	/**
	 * 取得每個問題的使用者回答之回答編號總數。
	 * @param Map 每個問題的使用者回答之回答編號統計Map
	 * @return double 每個問題的使用者回答之回答編號總數
	 */
	default public double getAnswerNumTotal(Map<Integer, Double> answerNumStatMap) {
		if (answerNumStatMap == null) return -1.0;
		return answerNumStatMap
				.values()
				.stream()
				.reduce(0.0, Double::sum);
	}
	
	/**
	 * 取得每個問題其答案的使用者回答之統計Map。
	 * @param integer 問題其問題答案Index
	 * @param Map 每個問題的使用者回答之回答編號統計Map
	 * @param double 每個問題的使用者回答之回答編號總數
	 * @return Map 每個問題其答案的使用者回答之統計Map
	 */
	default public Map<String, String> getEachUserAnswerStatMap(
			int questionAnswerIndex, 
			Map<Integer, Double> answerNumStatMap,
			double answerNumTotal
			) {
		Map<String, String> eachUserAnswerStatMap = new HashMap<>();
		if (answerNumStatMap == null || answerNumTotal == -1.0) {
			eachUserAnswerStatMap.put("eachUserAnswerPercentage", "0%");
			eachUserAnswerStatMap.put("eachUserAnswerTotal", "0");
			return eachUserAnswerStatMap;
		}
		int questionAnswerIndexPlus1 = questionAnswerIndex + 1;
		Double prepareEachUserAnswerPercentage = 
				answerNumStatMap.get(questionAnswerIndexPlus1);
		String eachUserAnswerPercentage = 
				prepareEachUserAnswerPercentage == null 
				? "0%" 
				: String.valueOf(Math.round(
						prepareEachUserAnswerPercentage 
						/ answerNumTotal 
						* 100
						)) + "%";
		String eachUserAnswerTotal = 
				prepareEachUserAnswerPercentage == null 
				? "0" 
				: String.valueOf(prepareEachUserAnswerPercentage.intValue());
		eachUserAnswerStatMap.put("eachUserAnswerPercentage", eachUserAnswerPercentage);
		eachUserAnswerStatMap.put("eachUserAnswerTotal", eachUserAnswerTotal);
		return eachUserAnswerStatMap;
	}
	
}
