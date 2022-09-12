package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.UserAnswerDetailForCSV;
import com.QuestionnaireProject.QuestionnaireSystem.repository.CategoryDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.CommonQuestionDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionnaireDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.UserAnswerDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.UserDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.DataTransactionalService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.util.StringUtil;

@Service
public class DataTransactionalServiceImpl implements DataTransactionalService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private CommonQuestionDao commonQuestionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAnswerDao userAnswerDao;
	
	@Override
	@Transactional
	public void saveUserAndUserAnswerList(
			User user, 
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			userDao.save(user);
			userAnswerDao.saveAll(userAnswerList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional
	public void saveQuestionnaire(
			QuestionnaireSession questionnaireSession, 
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			Questionnaire toSaveQuestionnaire = new Questionnaire(questionnaireSession);
			questionnaireDao.save(toSaveQuestionnaire);
			
			for (var questionSession : questionSessionList) {
				if (!questionSession.getIsCreated()) {
					if (questionSession.getIsDeleted()) {
						Optional<Question> toDeleteQuestion = 
								questionDao
								.findById(questionSession.getQuestionId());
						
						if (!toDeleteQuestion.isPresent()) 
							throw new Exception("To delete question is not found");
						questionDao.delete(toDeleteQuestion.get());
					} 
					else if (questionSession.getIsUpdated()) {
						Optional<Question> questionOp = 
								questionDao
								.findById(questionSession.getQuestionId());
						
						if (!questionOp.isPresent()) 
							throw new Exception("To update question is not found");
						Question toUpdateQuestion = questionOp.get();
						toUpdateQuestion
						.setQuestionCategory(questionSession.getQuestionCategory());
						toUpdateQuestion
						.setQuestionTyping(questionSession.getQuestionTyping());
						toUpdateQuestion
						.setQuestionName(questionSession.getQuestionName());
						toUpdateQuestion
						.setQuestionAnswer(questionSession.getQuestionAnswer());
						toUpdateQuestion
						.setQuestionRequired(questionSession.getQuestionRequired());
						toUpdateQuestion
						.setUpdateDate(
								DateTimeUtil
								.Convert
								.convertLocalDateTimeToDate(
										questionSession
										.getUpdateDate()
										)
								);
						toUpdateQuestion
						.setCommonQuestionId(questionSession.getCommonQuestionId());
						toUpdateQuestion
						.setIsTemplateOfCommonQuestion(questionSession.getIsTemplateOfCommonQuestion());
						
						questionDao.save(toUpdateQuestion);
					}
				} 
				else if (!questionSession.getIsDeleted()) {
					Question newOrToUpdatedQuestion = new Question(questionSession);
					questionDao.save(newOrToUpdatedQuestion);
				} 
				else
					continue;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional
	public void saveCommonQuestion(
			CommonQuestionSession commonQuestionSession,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			CommonQuestion toSaveCommonQuestion = new CommonQuestion(commonQuestionSession);
			commonQuestionDao.save(toSaveCommonQuestion);
			
			UUID commonQuestionId = commonQuestionSession.getCommonQuestionId();
			String commonQuestionName = commonQuestionSession.getCommonQuestionName();
			Category previousCategory = 
					categoryDao.findByCommonQuestionId(commonQuestionId);
			if (previousCategory == null) {
				Category toSaveCategory = new Category(commonQuestionSession);
				categoryDao.save(toSaveCategory);
			}
			else {
				previousCategory.setCategoryName(commonQuestionName);
				categoryDao.save(previousCategory);
			}
			
			for (var questionSession : questionSessionList) {
				if (!questionSession.getIsCreated()) {
					if (questionSession.getIsDeleted()) {
						Optional<Question> toDeleteQuestion = 
								questionDao
								.findById(questionSession.getQuestionId());
						
						if (!toDeleteQuestion.isPresent()) 
							throw new Exception("To delete question is not found");
						questionDao.delete(toDeleteQuestion.get());
					} 
					else if (questionSession.getIsUpdated()) {
						Optional<Question> questionOp = 
								questionDao
								.findById(questionSession.getQuestionId());
						
						if (!questionOp.isPresent()) 
							throw new Exception("To update question is not found");
						Question toUpdateQuestion = questionOp.get();
						toUpdateQuestion
						.setQuestionCategory(questionSession.getQuestionCategory());
						toUpdateQuestion
						.setQuestionTyping(questionSession.getQuestionTyping());
						toUpdateQuestion
						.setQuestionName(questionSession.getQuestionName());
						toUpdateQuestion
						.setQuestionAnswer(questionSession.getQuestionAnswer());
						toUpdateQuestion
						.setQuestionRequired(questionSession.getQuestionRequired());
						toUpdateQuestion
						.setUpdateDate(
								DateTimeUtil
								.Convert
								.convertLocalDateTimeToDate(questionSession.getUpdateDate())
								);
						
						questionDao.save(toUpdateQuestion);
					}
				} 
				else if (!questionSession.getIsDeleted()) {
					Question newOrToUpdatedQuestion = new Question(questionSession);
					questionDao.save(newOrToUpdatedQuestion);
				} 
				else
					continue;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional
	public void deleteQuestionnaireList(
			List<String> questionnaireIdListStr
			) throws Exception {
		try {
			for (var questionnaireIdStr : questionnaireIdListStr) {
				UUID questionnaireId = UUID.fromString(questionnaireIdStr);
				List<Question> toDeleteQuestionList = 
						questionDao.findByQuestionnaireId(questionnaireId);
				List<User> toDeleteUserList = 
						userDao.findByQuestionnaireId(questionnaireId);
				List<UserAnswer> toDeleteUserAnswerList = 
						userAnswerDao.findByQuestionnaireId(questionnaireId);
				Optional<Questionnaire> toDeleteQuestionnaire = 
						questionnaireDao.findById(questionnaireId);
				
				if (toDeleteQuestionList == null || toDeleteQuestionList.isEmpty())
					throw new Exception("To delete question list are not found");
				if (!toDeleteQuestionnaire.isPresent()) 
					throw new Exception("To delete questionnaire is not found");
				
				if (toDeleteUserList != null && !toDeleteUserList.isEmpty())
					userDao.deleteAll(toDeleteUserList);
				if (toDeleteUserAnswerList != null && !toDeleteUserAnswerList.isEmpty())
					userAnswerDao.deleteAll(toDeleteUserAnswerList);
				
				questionDao.deleteAll(toDeleteQuestionList);
				questionnaireDao.delete(toDeleteQuestionnaire.get());
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	@Transactional
	public void deleteCommonQuestionList(
			List<String> commonQuestionIdListStr
			) throws Exception {
		try {
			for (var commonQuestionIdStr : commonQuestionIdListStr) {
				UUID commonQuestionId = UUID.fromString(commonQuestionIdStr);
				Category toDeleteCategory = 
						categoryDao.findByCommonQuestionId(commonQuestionId);
				List<Question> toDeleteQuestionList = 
						questionDao
						.findByCommonQuestionIdAndIsTemplateOfCommonQuestion(commonQuestionId, true);
				Optional<CommonQuestion> toDeleteCommonQuestion = 
						commonQuestionDao
						.findById(commonQuestionId);
				
				if (toDeleteCategory == null)
					throw new Exception("To delete category is not found");
				if (toDeleteQuestionList == null || toDeleteQuestionList.isEmpty())
					throw new Exception("To delete question list are not found");
				if (!toDeleteCommonQuestion.isPresent()) 
					throw new Exception("To delete commonQuestion is not found");
				
				categoryDao.delete(toDeleteCategory);
				questionDao.deleteAll(toDeleteQuestionList);
				commonQuestionDao.delete(toDeleteCommonQuestion.get());
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean isValidQueryString(
			HttpServletRequest request,
			boolean isQuestionnaire
			) throws Exception {
		String queryString = request.getQueryString();
		if (!StringUtils.hasText(queryString)) return true;
		
		if (!StringUtil.Func.isValidQueryString(queryString)) return false;
		
		Map<String, String[]> paramMap = request.getParameterMap();
		List<String> paramKeyListOfTemplate = 
				isQuestionnaire 
					? List.of("index", "keyword", "startDate", "endDate") 
					: List.of("index", "keyword");
		List<String> paramKeyList = 
				paramMap
				.keySet()
				.stream()
				.collect(Collectors.toList());
		List<String> paramKeyListOfResult = 
				paramKeyList
				.stream()
				.filter(item -> 
					paramKeyListOfTemplate
					.stream()
					.anyMatch(item2 -> item.equals(item2))
					)
				.collect(Collectors.toList());
		if (paramKeyListOfResult == null 
				|| paramKeyListOfResult.isEmpty()) return false;
		if (paramKeyListOfResult.size() 
				!= paramKeyList.size()) return false;
		List<String> paramValueList = new ArrayList<>();
		for (var param : paramMap.entrySet()) {
			for (var value : param.getValue()) {
				if (StringUtils.hasText(value))
					paramValueList.add(value);
			}
		}
		if (paramValueList.size() != paramKeyListOfResult.size()) return false;
		String queryStringOfResult = "";
		for (int i = 0; i < paramKeyListOfResult.size(); i++) {
			queryStringOfResult += 
					paramKeyListOfResult.get(i) 
					+ "=" 
					+ paramValueList.get(i) 
					+ "&";
		}
		if (queryStringOfResult.length() - 1 != queryString.length()) return false;
		
		if (queryString.contains("index=")) {
			if (!StringUtils.hasText(request.getParameter("index"))) return false;
			
			if (queryString.contains("keyword=")) {
				if (queryString.contains("startDate") 
						|| queryString.contains("endDate") 
						|| !StringUtils.hasText(request.getParameter("keyword"))) {
					return false;
				}
				else
					return true;
			}
			else if (queryString.contains("startDate=") 
					|| queryString.contains("endDate=")) {
				if (!(queryString.contains("startDate=") 
					&& queryString.contains("endDate="))) {
					return false;
				}
				if (queryString.contains("keyword") 
						|| !StringUtils.hasText(request.getParameter("startDate")) 
						|| !StringUtils.hasText(request.getParameter("endDate"))) {
					return false;
				}
				else
					return true;
			}
			else
				return true;
		}
		else 
			return false;
	}
	
	@Override
	public boolean isOverDateOrHasUser(
			QuestionnaireSession questionnaireSession, 
			List<User> userListInSession
			) throws Exception {
		try {
			if (questionnaireSession == null) return false;
			LocalDateTime endDate = questionnaireSession.getEndDate();
			boolean isOverDate = 
					endDate == null 
					? false 
					: endDate.isBefore(DateTimeUtil.Func.getLocalDateTime(false));
			boolean hasUser = 
					userListInSession == null 
					? false 
					: userListInSession.isEmpty() 
					? false 
					: true;
			return (isOverDate || hasUser);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<UserAnswerDetailForCSV> getUserAnswerDetailForCSVList(
			List<QuestionSession> questionSessionList,
			List<User> userList,
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			List<UserAnswerDetailForCSV> userAnswerDetailForCSVList = new ArrayList<>();
			
			for (var user : userList) {
				List<UserAnswer> eachUserItsUserAnswerList = 
						userAnswerList
						.stream()
						.filter(item -> item.getUserId().equals(user.getUserId()))
						.collect(Collectors.toList());
				List<QuestionSession> eachUserItsQuestionSessionList = 
						questionSessionList
						.stream()
						.filter(item -> eachUserItsUserAnswerList
								.stream()
								.map(UserAnswer::getQuestionId)
								.anyMatch(item2 -> item2.equals(item.getQuestionId())))
						.collect(Collectors.toList());
				UserAnswerDetailForCSV userAnswerDetailForCSV = new UserAnswerDetailForCSV();
				
				userAnswerDetailForCSV.setUserName(user.getUserName());
				userAnswerDetailForCSV.setPhone(user.getPhone());
				userAnswerDetailForCSV.setEmail(user.getEmail());
				userAnswerDetailForCSV.setAge(user.getAge());
				userAnswerDetailForCSV.setAnswerDate(DateTimeUtil.Convert.convertDateToString(user.getAnswerDate()));
				// 為第一個問題其在CSV檔案的第一個列
				QuestionSession preQuestionSession = eachUserItsQuestionSessionList.get(0);
				eachUserItsQuestionSessionList.remove(0);
				List<UserAnswer> preUserAnswerList =
					eachUserItsUserAnswerList
					.stream()
					.filter(item -> item.getQuestionId().equals(preQuestionSession.getQuestionId()))
					.collect(Collectors.toList());
				eachUserItsUserAnswerList.removeAll(preUserAnswerList);
				preUserAnswerList.sort((a, b) -> a.getAnswerNum().compareTo(b.getAnswerNum()));
				UserAnswer preUserAnswer = preUserAnswerList.get(0);
				preUserAnswerList.remove(0);
				userAnswerDetailForCSV.setQuestionCategory(preQuestionSession.getQuestionCategory());
				userAnswerDetailForCSV.setQuestionTyping(preQuestionSession.getQuestionTyping());
				userAnswerDetailForCSV.setQuestionRequired(String.valueOf(preQuestionSession.getQuestionRequired()));
				userAnswerDetailForCSV.setQuestionName(preQuestionSession.getQuestionName());
				userAnswerDetailForCSV.setQuestionAnswer(preQuestionSession.getQuestionAnswer());
				userAnswerDetailForCSV.setAnswerNum(String.valueOf(preUserAnswer.getAnswerNum()));
				userAnswerDetailForCSV.setAnswer(preUserAnswer.getAnswer());
				userAnswerDetailForCSVList.add(userAnswerDetailForCSV);
				// 為第一個問題其剩餘使用者回答在CSV檔案第一之後的列
				if (preUserAnswerList != null && !preUserAnswerList.isEmpty()) {
					for (var preUserAnswerItem : preUserAnswerList) {
						UserAnswerDetailForCSV innerUserAnswerDetailForCSV = new UserAnswerDetailForCSV();
						innerUserAnswerDetailForCSV.setUserName("");
						innerUserAnswerDetailForCSV.setPhone("");
						innerUserAnswerDetailForCSV.setEmail("");
						innerUserAnswerDetailForCSV.setAge("");
						innerUserAnswerDetailForCSV.setAnswerDate("");
						innerUserAnswerDetailForCSV.setQuestionCategory("");
						innerUserAnswerDetailForCSV.setQuestionTyping("");
						innerUserAnswerDetailForCSV.setQuestionRequired("");
						innerUserAnswerDetailForCSV.setQuestionName("");
						innerUserAnswerDetailForCSV.setQuestionAnswer(preQuestionSession.getQuestionAnswer());
						innerUserAnswerDetailForCSV.setAnswerNum(String.valueOf(preUserAnswerItem.getAnswerNum()));
						innerUserAnswerDetailForCSV.setAnswer(preUserAnswerItem.getAnswer());
						userAnswerDetailForCSVList.add(innerUserAnswerDetailForCSV);
					}
				}
				// 檢查是否還有問題
				if (eachUserItsQuestionSessionList == null 
						|| eachUserItsQuestionSessionList.isEmpty()) continue;
				for (var questionSession : eachUserItsQuestionSessionList) {
					UserAnswerDetailForCSV innerUserAnswerDetailForCSV = new UserAnswerDetailForCSV();
					innerUserAnswerDetailForCSV.setUserName("");
					innerUserAnswerDetailForCSV.setPhone("");
					innerUserAnswerDetailForCSV.setEmail("");
					innerUserAnswerDetailForCSV.setAge("");
					innerUserAnswerDetailForCSV.setAnswerDate("");
					innerUserAnswerDetailForCSV.setQuestionCategory(questionSession.getQuestionCategory());
					innerUserAnswerDetailForCSV.setQuestionTyping(questionSession.getQuestionTyping());
					innerUserAnswerDetailForCSV.setQuestionRequired(String.valueOf(questionSession.getQuestionRequired()));
					innerUserAnswerDetailForCSV.setQuestionName(questionSession.getQuestionName());
					innerUserAnswerDetailForCSV.setQuestionAnswer(questionSession.getQuestionAnswer());
					List<UserAnswer> eachQuestionItsUserAnswerList = 
							eachUserItsUserAnswerList
							.stream()
							.filter(item -> item.getQuestionId().equals(questionSession.getQuestionId()))
							.collect(Collectors.toList());
					eachUserItsUserAnswerList.removeAll(eachQuestionItsUserAnswerList);
					eachQuestionItsUserAnswerList.sort((a, b) -> a.getAnswerNum().compareTo(b.getAnswerNum()));
					UserAnswer eachUserAnswer = eachQuestionItsUserAnswerList.get(0);
					eachQuestionItsUserAnswerList.remove(0);
					eachUserItsUserAnswerList.remove(eachUserAnswer);
					innerUserAnswerDetailForCSV.setAnswerNum(String.valueOf(eachUserAnswer.getAnswerNum()));
					innerUserAnswerDetailForCSV.setAnswer(eachUserAnswer.getAnswer());
					userAnswerDetailForCSVList.add(innerUserAnswerDetailForCSV);
					// 檢查此問題是否還有使用者回答
					if (eachQuestionItsUserAnswerList == null 
							|| eachQuestionItsUserAnswerList.isEmpty()) continue;
					for (var questionItsUserAnswer : eachQuestionItsUserAnswerList) {
						UserAnswerDetailForCSV lastUserAnswerDetailForCSV = new UserAnswerDetailForCSV();
						lastUserAnswerDetailForCSV.setUserName("");
						lastUserAnswerDetailForCSV.setPhone("");
						lastUserAnswerDetailForCSV.setEmail("");
						lastUserAnswerDetailForCSV.setAge("");
						lastUserAnswerDetailForCSV.setAnswerDate("");
						lastUserAnswerDetailForCSV.setQuestionCategory("");
						lastUserAnswerDetailForCSV.setQuestionTyping("");
						lastUserAnswerDetailForCSV.setQuestionRequired("");
						lastUserAnswerDetailForCSV.setQuestionName("");
						lastUserAnswerDetailForCSV.setQuestionAnswer(questionSession.getQuestionAnswer());
						lastUserAnswerDetailForCSV.setAnswerNum(String.valueOf(questionItsUserAnswer.getAnswerNum()));
						lastUserAnswerDetailForCSV.setAnswer(questionItsUserAnswer.getAnswer());
						userAnswerDetailForCSVList.add(lastUserAnswerDetailForCSV);
					}
				}
			}
			
			return userAnswerDetailForCSVList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public ByteArrayInputStream getDataToCSV(
			List<UserAnswerDetailForCSV> userAnswerDetailForCSVList
			) throws Exception {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			if (userAnswerDetailForCSVList == null || userAnswerDetailForCSVList.isEmpty())
				throw new Exception("User answer detail for csv list is null");
			
			csvPrinter.printRecord(UserAnswerDetailForCSV.getHeaderList());
			for (var userAnswerDetailForCSV : userAnswerDetailForCSVList) {
				List<String> data = 
						Arrays.asList(
								userAnswerDetailForCSV.getUserName(), 
								userAnswerDetailForCSV.getPhone(), 
								userAnswerDetailForCSV.getEmail(), 
								userAnswerDetailForCSV.getAge(),
								userAnswerDetailForCSV.getAnswerDate(),
								userAnswerDetailForCSV.getQuestionCategory(),
								userAnswerDetailForCSV.getQuestionTyping(),
								userAnswerDetailForCSV.getQuestionRequired(),
								userAnswerDetailForCSV.getQuestionName(), 
								userAnswerDetailForCSV.getQuestionAnswer(),
								userAnswerDetailForCSV.getAnswerNum(),
								userAnswerDetailForCSV.getAnswer()
								);
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
	    } catch (Exception e) {
	      throw new Exception(e);
	    }
	}
	
}
