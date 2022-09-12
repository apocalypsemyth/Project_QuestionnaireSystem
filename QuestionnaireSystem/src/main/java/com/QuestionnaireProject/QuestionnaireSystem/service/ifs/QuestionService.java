package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;

public interface QuestionService {
	
	/**
	 * �擾�i�L�ڕW��Ɉ���p���Id�a���ۈז�ɓI����\�B
	 * @param String ��Ɉ���p���Id
	 * @param boolean ���ۈז��
	 * @return List ����\
	 * @throws �擾�i�L�ڕW��Ɉ���p���Id�a���ۈז�ɓI����\���Cᢐ�����B
	 */
	public List<Question> getQuestionList(
			String questionnaireOrCommonQuestionIdStr,
			boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * �擾�i�L�ڕW�g�p�҉񓚓I����\�B
	 * @param List ����\
	 * @param List �g�p�҉񓚗�\
	 * @return List �i�L�ڕW�g�p�҉񓚓I����\
	 * @throws �擾�i�L�ڕW�g�p�҉񓚓I����\���Cᢐ�����B
	 */
	public List<Question> getListOfQuestionFromUserAnswer(
			List<Question> questionList,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
}
