package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.repository.UserAnswerDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.util.StringUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserAnswerReq;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
	
	@Autowired
	private UserAnswerDao userAnswerDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserAnswer> getUserAnswerList(
			HttpSession session
			) throws Exception {
		try {
			List<UserAnswer> userAnswerList = 
					(List<UserAnswer>) 
					session.getAttribute(SessionConstant.Name.USER_ANSWER_LIST);
			if (userAnswerList == null || userAnswerList.isEmpty()) return null;
			return userAnswerList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<UserAnswer> getUserAnswerList(
			String questionnaireIdStr
			) throws Exception {
		try {
			UUID questionnaireId = UUID.fromString(questionnaireIdStr);
			List<UserAnswer> userAnswerList = 
					userAnswerDao.findByQuestionnaireId(questionnaireId);
			if (userAnswerList == null || userAnswerList.isEmpty()) return null;
			return userAnswerList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<UserAnswer> getUserAnswerList(
			String userIdStr,
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			UUID userId = UUID.fromString(userIdStr);
			List<UserAnswer> filteredUserAnswerList = 
					userAnswerList
					.stream()
					.filter(item -> item.getUserId().equals(userId))
					.collect(Collectors.toList());
			if (filteredUserAnswerList == null 
					|| filteredUserAnswerList.isEmpty()) return null;
			return filteredUserAnswerList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setUserAnswerList(
			HttpSession session,
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			session.setAttribute(SessionConstant.Name.USER_ANSWER_LIST, userAnswerList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void createUserAnswerList(
			HttpSession session, 
			PostUserAnswerReq postUserAnswerReq
			) throws Exception {
		try {
			List<String> userAnswerStringList = 
					postUserAnswerReq.getUserAnswerStringList();
			List<UserAnswer> userAnswerList = new ArrayList<>();
			
			for (var userAnswer : userAnswerStringList) {
				UserAnswer newUserAnswer = new UserAnswer();
				newUserAnswer.setUserAnswerId(UUID.randomUUID());
				newUserAnswer.setQuestionnaireId(UUID.fromString(postUserAnswerReq.getQuestionnaireId()));
				newUserAnswer.setUserId(UUID.fromString(postUserAnswerReq.getUserId()));
				
				List<String> questionId_AnswerNum_Answer_QuestionTypingList = 
						new ArrayList<>(Arrays.asList(userAnswer.split("_")));
				
				String questionIdStr = 
						questionId_AnswerNum_Answer_QuestionTypingList
						.stream()
						.filter(item -> StringUtil.Func.isValidUUID(item))
						.findFirst()
						.get();
				newUserAnswer.setQuestionId(UUID.fromString(questionIdStr));
				questionId_AnswerNum_Answer_QuestionTypingList.remove(questionIdStr);
				
				String questionTyping = 
						questionId_AnswerNum_Answer_QuestionTypingList
						.stream()
						.filter(item -> 
							item.equals(DataConstant.Key.SINGLE_SELECT) 
							|| item.equals(DataConstant.Key.MULTIPLE_SELECT) 
							|| item.equals(DataConstant.Key.TEXT))
						.findFirst()
						.get();
				newUserAnswer.setQuestionTyping(questionTyping);
				questionId_AnswerNum_Answer_QuestionTypingList.remove(questionTyping);
				
				String answerNum = 
						questionId_AnswerNum_Answer_QuestionTypingList
						.stream()
						.filter(item -> StringUtil.Func.isNumeric(item))
						.findFirst()
						.get();
				newUserAnswer.setAnswerNum(Integer.parseInt(answerNum));
				questionId_AnswerNum_Answer_QuestionTypingList.remove(answerNum);
				
				String answer = questionId_AnswerNum_Answer_QuestionTypingList.get(0);
				newUserAnswer.setAnswer(answer);
				
				userAnswerList.add(newUserAnswer);
			}
			
			setUserAnswerList(session, userAnswerList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void deleteUserAnswerList(
			HttpSession session
			) throws Exception {
		try {
			session.removeAttribute(SessionConstant.Name.USER_ANSWER_LIST);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
