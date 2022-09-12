package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostCommonQuestionReq;

@Service
public class CommonQuestionSessionServiceImpl implements CommonQuestionSessionService {

	@Override
	public CommonQuestionSession getCommonQuestionSession(
			HttpSession session
			) throws Exception {
		try {
			CommonQuestionSession commonQuestionSession = 
					(CommonQuestionSession) session.getAttribute(SessionConstant.Name.COMMON_QUESTION);
			if (commonQuestionSession == null) return null;
			return commonQuestionSession;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setCommonQuestionSession(
			HttpSession session, 
			CommonQuestionSession commonQuestionSession
			) throws Exception {
		try {
			session.setAttribute(
					SessionConstant.Name.COMMON_QUESTION, 
					commonQuestionSession
					);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void createCommonQuestionSession(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception {
		try {
			CommonQuestionSession newCommonQuestionSession = 
					new CommonQuestionSession(postCommonQuestionReq);
			newCommonQuestionSession.setIsCreated(true);
			setCommonQuestionSession(session, newCommonQuestionSession);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean isToUpdateCommonQuestionSessionChange(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception {
		try {
			CommonQuestionSession toUpdateCommonQuestionSession = 
					getCommonQuestionSession(session);
			if (toUpdateCommonQuestionSession == null)
				throw new Exception("To check common question session is null");
			
			if (toUpdateCommonQuestionSession.getCommonQuestionName()
					.equals(postCommonQuestionReq.getCommonQuestionName())) 
				return false;
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void updateCommonQuestionSession(
			HttpSession session,
			PostCommonQuestionReq postCommonQuestionReq
			) throws Exception {
		try {
			CommonQuestionSession toUpdateCommonQuestionSession = 
					getCommonQuestionSession(session);
			if (toUpdateCommonQuestionSession == null)
				throw new Exception("to update common question session is null");
			
			toUpdateCommonQuestionSession.setCommonQuestionName(postCommonQuestionReq.getCommonQuestionName());
			
			setCommonQuestionSession(session, toUpdateCommonQuestionSession);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void deleteCommonQuestionSession(
			HttpSession session
			) throws Exception {
		try {
			CommonQuestionSession toDeleteCommonQuestionSession = 
					getCommonQuestionSession(session);
			if (toDeleteCommonQuestionSession == null) return;
			session.removeAttribute(SessionConstant.Name.COMMON_QUESTION);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
