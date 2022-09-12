package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	
	@Override
	public List<Question> getQuestionList(
			String questionnaireOrCommonQuestionIdStr,
			boolean isQuestionnaire
			) throws Exception {
		try {
			UUID questionnaireOrCommonQuestionId = 
					UUID.fromString(questionnaireOrCommonQuestionIdStr);
			List<Question> questionList = 
					isQuestionnaire 
					? questionDao.findByQuestionnaireId(questionnaireOrCommonQuestionId) 
					: questionDao.findByCommonQuestionIdAndIsTemplateOfCommonQuestion(questionnaireOrCommonQuestionId, true);
			if (questionList == null 
					|| questionList.isEmpty()) return null;
			
			questionList.sort(Comparator.comparing(Question::getUpdateDate).reversed());
			return questionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<Question> getListOfQuestionFromUserAnswer(
			List<Question> questionList,
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			List<Question> questionList2 = 
					questionList
					.stream()
					.filter(item -> 
						userAnswerList
						.stream()
						.map(UserAnswer::getQuestionId)
						.anyMatch(item2 -> item2.equals(item.getQuestionId())))
					.collect(Collectors.toList());
			return questionList2;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
