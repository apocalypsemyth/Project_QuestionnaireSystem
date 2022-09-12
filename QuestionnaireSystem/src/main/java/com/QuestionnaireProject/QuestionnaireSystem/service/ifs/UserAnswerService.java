package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserAnswerReq;

public interface UserAnswerService {
	
	/**
	 * �擾�g�p�҉񓚗�\��Session�B
	 * @param HttpSession session
	 * @return List �g�p�҉񓚗�\��Session
	 * @throws �擾�g�p�҉񓚗�\��Session���Cᢐ�����B
	 */
	public List<UserAnswer> getUserAnswerList(
			HttpSession session
			) throws Exception;
	
	/**
	 * �擾�g�p�҉񓚗�\�C�S�R���Id�B
	 * @param String ���Id
	 * @return List �g�p�҉񓚗�\�C�S�R���Id
	 * @throws �擾�g�p�҉񓚗�\�C�S�R���Id���Cᢐ�����B
	 */
	public List<UserAnswer> getUserAnswerList(
			String questionnaireIdStr
			) throws Exception;
	
	/**
	 * �擾�g�p�҉񓚗�\�C�S�R�g�p��Id�a�g�p�҉񓚗�\�B
	 * @param String �g�p��Id
	 * @param List �g�p�҉񓚗�\
	 * @return List �g�p�҉񓚗�\�C�S�R�g�p��Id�a�g�p�҉񓚗�\
	 * @throws �擾�g�p�҉񓚗�\�C�S�R�g�p��Id�a�g�p�҉񓚗�\���Cᢐ�����B
	 */
	public List<UserAnswer> getUserAnswerList(
			String userIdStr,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * �ݒu�g�p�҉񓚗�\��Session�B
	 * @param HttpSession session
	 * @param List �g�p�҉񓚗�\
	 * @throws �ݒu�g�p�҉񓚗�\��Session���Cᢐ�����B
	 */
	public void setUserAnswerList(
			HttpSession session,
			List<UserAnswer> userAnswerList
			) throws Exception;
	
	/**
	 * �n���g�p�҉񓚗�\��Session�C�S�R�g�p�҉񓚐���Body�B
	 * @param HttpSession session
	 * @param Object �g�p�҉񓚐���Body
	 * @throws �n���g�p�҉񓚗�\��Session���Cᢐ�����B
	 */
	public void createUserAnswerList(
			HttpSession session, 
			PostUserAnswerReq postUserAnswerReq
			) throws Exception;
	
	/**
	 * �����S���g�p�҉񓚗�\��Session�B
	 * @param HttpSession session
	 * @throws �����S���g�p�҉񓚗�\��Session���Cᢐ�����B
	 */
	public void deleteUserAnswerList(
			HttpSession session
			) throws Exception;
	
}
