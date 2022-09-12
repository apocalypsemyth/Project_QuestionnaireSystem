package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
			boolean isQuestionnaire
			) throws Exception {
		try {
			QuestionSession newQuestionSession = new QuestionSession(postQuestionReq, isQuestionnaire);
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
	public boolean isSetQuestionListOfCommonQuestion(
			List<QuestionSession> questionSessionList,
			boolean isUpdateMode
			) throws Exception {
		try {
			if (isUpdateMode) {
				List<QuestionSession> originalQuestionSessionList = 
						questionSessionList
						.stream()
						.filter(item -> !item.getIsCreated())
						.collect(Collectors.toList());
				if (originalQuestionSessionList == null 
						|| originalQuestionSessionList.isEmpty()) {
					throw new Exception("Original question session list is null");
				}
				List<QuestionSession> otherQuestionSessionList = 
						questionSessionList
						.stream()
						.filter(item -> item.getIsCreated())
						.collect(Collectors.toList());
				boolean isSetWithOrigin = 
						originalQuestionSessionList
						.stream()
						.allMatch(item -> 
							item.getCommonQuestionId() != null
							&& item.getIsTemplateOfCommonQuestion() != null
							&& !item.getIsTemplateOfCommonQuestion()
							&& !item.getIsDeleted());
				if (otherQuestionSessionList == null 
						|| otherQuestionSessionList.isEmpty()) {
					return isSetWithOrigin;
				}
				boolean isSetWithOther = 
						otherQuestionSessionList
						.stream()
						.allMatch(item -> 
							item.getCommonQuestionId() != null
							&& item.getIsTemplateOfCommonQuestion() != null
							&& !item.getIsTemplateOfCommonQuestion()
							&& !item.getIsDeleted());
				return (isSetWithOrigin || isSetWithOther);
			}
			
			if (questionSessionList == null 
					|| questionSessionList.isEmpty()) return false;
			return questionSessionList
					.stream()
					.allMatch(item -> 
					item.getCommonQuestionId() != null
					&& item.getIsTemplateOfCommonQuestion() != null
					&& !item.getIsTemplateOfCommonQuestion()
					&& !item.getIsDeleted());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> getSetPropertyOfQuestionListOfCommonQuestion(
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				item.setQuestionId(UUID.randomUUID());
				item.setQuestionnaireId(null);
				item.setIsCreated(true);
				item.setIsTemplateOfCommonQuestion(false);
				newQuestionSessionList.add(item);
			});
			return newQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setIsDeletedOfQuestionListOrOfCommonQuestion(
			HttpSession session,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				if (!item.getIsCreated()) {
					item.setIsDeleted(true);
					newQuestionSessionList.add(item);
				}
			});
			setQuestionSessionList(session, newQuestionSessionList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void convertQuestionListOfCommonQuestionToQuestionList(
			HttpSession session
			) throws Exception {
		try {
			List<QuestionSession> questionSessionList = getQuestionSessionList(session);
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				item.setQuestionCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
				//為了更新模式時，清除被套用的常用問題其問卷Id
				item.setQuestionnaireId(null);
				item.setCommonQuestionId(null);
				item.setIsTemplateOfCommonQuestion(null);
				item.setIsUpdated(true);
				newQuestionSessionList.add(item);
			});
			setQuestionSessionList(session, newQuestionSessionList);
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
						item.getQuestionCategory().equals(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY)
						&& item.getQuestionnaireId() == null 
						&& item.getCommonQuestionId() == null 
						&& item.getIsTemplateOfCommonQuestion() == null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> convertQuestionListOfCommonQuestionToQuestionList(
			String questionnaireIdStr,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			UUID questionnaireId = UUID.fromString(questionnaireIdStr);
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			questionSessionList.forEach(item -> {
				if (item.getQuestionCategory().equals(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY)
						&& item.getQuestionnaireId() == null 
						&& item.getCommonQuestionId() == null 
						&& item.getIsTemplateOfCommonQuestion() == null) {
					item.setQuestionnaireId(questionnaireId);
				}
				newQuestionSessionList.add(item);
			});
			return newQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<QuestionSession> getQuestionListOfCommonQuestionForChangedIds(
			String questionnaireIdStr,
			List<QuestionSession> questionSessionList,
			boolean isUpdateMode
			) throws Exception {
		try {
			UUID questionnaireId = UUID.fromString(questionnaireIdStr);
			List<QuestionSession> newQuestionSessionList = new ArrayList<>();
			
			if (isUpdateMode) {
				List<QuestionSession> originalQuestionSessionList = 
						questionSessionList
						.stream()
						.filter(item -> !item.getIsCreated())
						.collect(Collectors.toList());
				if (originalQuestionSessionList == null 
						|| originalQuestionSessionList.isEmpty()) {
					throw new Exception("Original question session list is null");
				}
				List<QuestionSession> otherQuestionSessionList = 
						questionSessionList
						.stream()
						.filter(item -> item.getIsCreated())
						.collect(Collectors.toList());
				boolean isSetWithOrigin = 
						originalQuestionSessionList
						.stream()
						.allMatch(item -> 
							item.getCommonQuestionId() != null
							&& item.getIsTemplateOfCommonQuestion() != null
							&& !item.getIsTemplateOfCommonQuestion()
							&& !item.getIsDeleted());
				if (otherQuestionSessionList == null 
						|| otherQuestionSessionList.isEmpty()) {
					if (!isSetWithOrigin) 
						throw new Exception("Original question session list should be of common question, but something error happened");
					else 
						return originalQuestionSessionList;
				}
				if (isSetWithOrigin) {
					return questionSessionList;
				}
				otherQuestionSessionList.forEach(questionSession -> {
					if (!questionSession.getIsDeleted()) {
						questionSession.setQuestionnaireId(questionnaireId);
						newQuestionSessionList.add(questionSession);
					}
				});
				newQuestionSessionList.addAll(originalQuestionSessionList);
				return newQuestionSessionList;
			}
			
			questionSessionList.forEach(questionSession -> {
				if (questionSession.getIsCreated()) {
					questionSession.setQuestionnaireId(questionnaireId);
					newQuestionSessionList.add(questionSession);
				}
			});
			return newQuestionSessionList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
