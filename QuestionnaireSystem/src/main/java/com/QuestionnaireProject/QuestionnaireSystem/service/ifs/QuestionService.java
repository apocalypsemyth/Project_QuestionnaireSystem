package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;

public interface QuestionService {
	
	/**
	 * æ¾iLÚWâÉ½ípâèIda¥Û×âÉIâèñ\B
	 * @param String âÉ½ípâèId
	 * @param boolean ¥Û×âÉ
	 * @return List âèñ\
	 * @throws æ¾iLÚWâÉ½ípâèIda¥Û×âÉIâèñ\Cá¢¶öëB
	 */
	public List<Question> getQuestionList(
			String questionnaireOrCommonQuestionIdStr,
			boolean isQuestionnaire
			) throws Exception;
	
	/**
	 * æ¾iLÚWgpÒñIâèñ\B
	 * @param List âèñ\
	 * @param List gpÒññ\
	 * @return List iLÚWgpÒñIâèñ\
	 * @throws æ¾iLÚWgpÒñIâèñ\Cá¢¶öëB
	 */
	public List<Question> getListOfQuestionFromUserAnswer(
			List<Question> questionList,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
}
