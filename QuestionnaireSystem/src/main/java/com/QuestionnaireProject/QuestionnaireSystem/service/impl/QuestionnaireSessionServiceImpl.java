package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;

@Service
public class QuestionnaireSessionServiceImpl implements QuestionnaireSessionService {
	
	@Override
	public QuestionnaireSession getQuestionnaireSession(
			HttpSession session
			) throws Exception {
		try {
			QuestionnaireSession questionnaireSession = 
					(QuestionnaireSession) session.getAttribute(SessionConstant.Name.QUESTIONNAIRE);
			if (questionnaireSession == null) return null;
			return questionnaireSession;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void setQuestionnaireSession(
			HttpSession session,
			QuestionnaireSession questionnaireSession
			) throws Exception {
		try {
			session.setAttribute(
					SessionConstant.Name.QUESTIONNAIRE, 
					questionnaireSession
					);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void createQuestionnaireSession(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception {
		try {
			QuestionnaireSession newQuestionnaireSession = 
					new QuestionnaireSession(postQuestionnaireReq);
			setQuestionnaireSession(session, newQuestionnaireSession);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean isToUpdateQuestionnaireSessionChange(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception {
		try {
			QuestionnaireSession toUpdateQuestionnaireSession = 
					getQuestionnaireSession(session);
			if (toUpdateQuestionnaireSession == null) 
				throw new Exception("To check questionnaire session is null");
			
			LocalDateTime startDateLdt = 
					DateTimeUtil
					.Convert
					.convertStringToLocalDateTime(postQuestionnaireReq.getStartDate());
			LocalDateTime endDateLdt = 
					StringUtils.hasText(postQuestionnaireReq.getEndDate())
					? DateTimeUtil
						.Convert
						.convertStringToLocalDateTime(postQuestionnaireReq.getEndDate())
					: null;
			if (toUpdateQuestionnaireSession.getCaption().equals(postQuestionnaireReq.getCaption())
					&& toUpdateQuestionnaireSession.getDescription().equals(postQuestionnaireReq.getDescription())
					&& toUpdateQuestionnaireSession.getIsEnable() == postQuestionnaireReq.getIsEnable()
					&& toUpdateQuestionnaireSession.getStartDate().equals(startDateLdt)) {
				if (toUpdateQuestionnaireSession.getEndDate() == null) {
					if (toUpdateQuestionnaireSession.getEndDate() == endDateLdt)
						return false;
					else
						return true;
				}
				else {
					if (toUpdateQuestionnaireSession.getEndDate().equals(endDateLdt))
						return false;
					else
						return true;
				}
			}
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void updateQuestionnaireSession(
			HttpSession session,
			PostQuestionnaireReq postQuestionnaireReq
			) throws Exception {
		try {
			QuestionnaireSession toUpdateQuestionnaireSession = 
					getQuestionnaireSession(session);
			if (toUpdateQuestionnaireSession == null) 
				throw new Exception("To update questionnaire session is null");
			
			LocalDateTime startDateLdt = 
					DateTimeUtil
					.Convert
					.convertStringToLocalDateTime(postQuestionnaireReq.getStartDate());
			LocalDateTime endDateLdt = 
					StringUtils.hasText(postQuestionnaireReq.getEndDate())
					? DateTimeUtil
						.Convert
						.convertStringToLocalDateTime(postQuestionnaireReq.getEndDate())
					: null;
			toUpdateQuestionnaireSession.setCaption(postQuestionnaireReq.getCaption());
			toUpdateQuestionnaireSession.setDescription(postQuestionnaireReq.getDescription());
			toUpdateQuestionnaireSession.setIsEnable(postQuestionnaireReq.getIsEnable());
			toUpdateQuestionnaireSession.setStartDate(startDateLdt);
			toUpdateQuestionnaireSession.setEndDate(endDateLdt);
			toUpdateQuestionnaireSession.setUpdateDate(
					DateTimeUtil
					.Func
					.getLocalDateTime(true)
					);
			
			setQuestionnaireSession(session, toUpdateQuestionnaireSession);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void deleteQuestionnaireSession(HttpSession session) throws Exception {
		try {
			QuestionnaireSession toDeleteQuestionnaireSession = 
					getQuestionnaireSession(session);
			if (toDeleteQuestionnaireSession == null) return;
			session.removeAttribute(SessionConstant.Name.QUESTIONNAIRE);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
