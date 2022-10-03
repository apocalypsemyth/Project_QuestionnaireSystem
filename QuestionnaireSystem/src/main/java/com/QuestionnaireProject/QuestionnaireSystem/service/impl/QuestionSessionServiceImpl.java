package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionReq;

@Service
public class QuestionSessionServiceImpl implements QuestionSessionService {
	
	@Override
	public QuestionSession getQuestionSession(
			HttpSession session,
			String questionIdStr
			) throws Exception {
		try {
			List<QuestionSession> questionSessionList = getQuestionSessionList(session);
			if (questionSessionList == null) throw new Exception("Question session is null");
			
			Optional<QuestionSession> questionSessionOp = 
					questionSessionList
					.stream()
					.filter(item -> item.getQuestionId().toString().equals(questionIdStr))
					.findFirst();
			if (!questionSessionOp.isPresent()) return null;
			
			return questionSessionOp.get();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionSession> getQuestionSessionList(
			HttpSession session
			) throws Exception {
		try {
			List<QuestionSession> questionSessionList =
					(List<QuestionSession>) 
					session.getAttribute(SessionConstant.Name.QUESTION_LIST);
			if (questionSessionList == null 
					|| questionSessionList.isEmpty()) return null;
			
			questionSessionList.sort((a, b) -> b.getUpdateDate().compareTo(a.getUpdateDate()));
			return questionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> getQuestionSessionList(
			List<Question> questionList
			) throws Exception {
		try {
			List<QuestionSession> questionSessionList = 
					questionList
					.stream()
					.map(question -> new QuestionSession(question))
					.sorted(Comparator.comparing(QuestionSession::getUpdateDate).reversed())
					.collect(Collectors.toList());
			if (questionSessionList.isEmpty() 
					|| questionSessionList == null) return null;
			return questionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setQuestionSessionList(
			HttpSession session,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			session.setAttribute(SessionConstant.Name.QUESTION_LIST, questionSessionList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void createQuestionSession(
			HttpSession session, 
			PostQuestionReq postQuestionReq,
			boolean isQuestionnaire,
			boolean isFromCommonQuestion
			) throws Exception {
		try {
			QuestionSession newQuestionSession = 
					new QuestionSession(postQuestionReq, isQuestionnaire, isFromCommonQuestion);
			newQuestionSession.setIsCreated(true);
			List<QuestionSession> questionSessionList = getQuestionSessionList(session);
			if (questionSessionList == null) questionSessionList = new ArrayList<>();
			
			questionSessionList.add(newQuestionSession);
			setQuestionSessionList(session, questionSessionList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void updateQuestionSession(
			HttpSession session,
			PostQuestionReq postQuestionReq
			) throws Exception {
		try {
			QuestionSession toUpdateQuestionSession = 
					getQuestionSession(session, postQuestionReq.getQuestionId());
			if (toUpdateQuestionSession == null) 
				throw new Exception("To update question session is null");
			
			toUpdateQuestionSession
			.setQuestionCategory(postQuestionReq.getQuestionCategory());
			toUpdateQuestionSession
			.setQuestionTyping(postQuestionReq.getQuestionTyping());
			toUpdateQuestionSession
			.setQuestionName(postQuestionReq.getQuestionName());
			toUpdateQuestionSession
			.setQuestionRequired(postQuestionReq.getQuestionRequired());
			toUpdateQuestionSession
			.setQuestionAnswer(postQuestionReq.getQuestionAnswer());
			toUpdateQuestionSession
			.setUpdateDate(DateTimeUtil.Func.getLocalDateTime(true));
			toUpdateQuestionSession
			.setIsCreated(toUpdateQuestionSession.getIsCreated());
			toUpdateQuestionSession
			.setIsUpdated(true);
			toUpdateQuestionSession
			.setIsDeleted(toUpdateQuestionSession.getIsDeleted());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void deleteQuestionSessionList(
			HttpSession session,
			PostQuestionReq postQuestionReq
			) throws Exception {
		List<String> questionIdList = postQuestionReq.getQuestionIdList();
		String questionnaireId = postQuestionReq.getQuestionnaireId();
		String commonQuestionId = postQuestionReq.getCommonQuestionId();
		boolean hasQuestionnaireId = StringUtils.hasText(questionnaireId);
		boolean hasCommonQuestionId = StringUtils.hasText(commonQuestionId);
		try {
			List<QuestionSession> questionSessionList = getQuestionSessionList(session);
			if (questionSessionList == null) 
				throw new Exception("Question session list is null");
			
			for (var questionId : questionIdList) {
				QuestionSession toDeleteQuestionSession = 
						getQuestionSession(session, questionId);
				if (toDeleteQuestionSession == null) 
					throw new Exception("To delete question session is null");
				
				if (hasQuestionnaireId || hasCommonQuestionId) {
					if (!toDeleteQuestionSession.getIsCreated()) {
						toDeleteQuestionSession.setIsDeleted(true);
						setQuestionSessionList(session, questionSessionList);
						continue;
					}
				}
				
				questionSessionList.remove(toDeleteQuestionSession);
				setQuestionSessionList(session, questionSessionList);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void deleteAllQuestionSessionList(HttpSession session) throws Exception {
		try {
			List<QuestionSession> toDeleteQuestionSessionList =
					getQuestionSessionList(session);
			if (toDeleteQuestionSessionList == null) return;
			session.removeAttribute(SessionConstant.Name.QUESTION_LIST);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> getSetCategoryNameOfQuestionListOfCommonQuestionList(
			String categoryName,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				item.setQuestionCategory(categoryName);
				newQuestionSessionList.add(item);
			});
			return newQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> getQuestionSessionThatIsNotDeletedList(
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> filteredQuestionSessionList =
					questionSessionList
					.stream()
					.filter(item -> !item.getIsDeleted())
					.collect(Collectors.toList());
			if (filteredQuestionSessionList == null 
					|| filteredQuestionSessionList.isEmpty()) {
				return null;
			}
			return filteredQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean isQuestionFromCommonQuestion(
			PostQuestionReq postQuestionReq
			) throws Exception {
		try {
			if (!postQuestionReq.getQuestionCategory()
					.equals(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean hasAtLeastOneRequiredQuestion(
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> filteredQuestionSessionList = 
					questionSessionList
					.stream()
					.filter(item -> !item.getIsDeleted())
					.collect(Collectors.toList());
			if (filteredQuestionSessionList == null 
					|| filteredQuestionSessionList.isEmpty()) return false;
			return filteredQuestionSessionList
					.stream()
					.map(QuestionSession::getQuestionRequired)
					.anyMatch(item -> item);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean isNeedToConvertQuestionListOfCommonQuestionToQuestionList(
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			return questionSessionList
					.stream()
					.anyMatch(item -> 
						!item.getQuestionCategory().equals(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> convertQuestionListOfCommonQuestionToQuestionList(
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				if (!item.getQuestionCategory()
						.equals(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY)) {
					item.setQuestionCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
				}
				newQuestionSessionList.add(item);
			});
			return newQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
