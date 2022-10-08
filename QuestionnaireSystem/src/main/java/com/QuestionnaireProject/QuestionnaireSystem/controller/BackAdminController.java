package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.ModelConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.PageConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.UrlConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CategoryService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.DataTransactionalService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.ModelService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.TypingService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;

@Controller
@RequestMapping(UrlConstant.Path.BACK_ADMIN)
public class BackAdminController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private QuestionnaireSessionService questionnaireSessionService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionSessionService questionSessionService;
	
	@Autowired
	private CommonQuestionService commonQuestionService;
	
	@Autowired
	private CommonQuestionSessionService commonQuestionSessionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAnswerService userAnswerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TypingService typingService;
	
	@Autowired
	private DataTransactionalService dataTransactionalService;
	
	@Autowired
	private ModelService modelService;
	
	@GetMapping(UrlConstant.Path.QUESTIONNAIRE_LIST)
	public String getBackAdminQuestionnaireList(
			Model model,
			RedirectAttributes redirectAttributes
			) throws Exception {
		try {
			model = modelService.setPageTitle(model, PageConstant.Title.QUESTIONNAIRE_LIST);
			model = modelService.setFragmentName(model, ModelConstant.Value.DATA_LIST);
			model = modelService.setDataListModel(model, true, true);
			List<Questionnaire> questionnaireList = questionnaireService.getQuestionnaireList();
			if (questionnaireList != null) {
				boolean hasNotStartOrOverEndDate = 
						questionnaireService.checkDateOfQuestionnaireList(questionnaireList);
				if (hasNotStartOrOverEndDate) {
					questionnaireService.setIsEnableOfQuestionnaireList(questionnaireList);
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Path.BACK_ADMIN 
					+ UrlConstant.Path.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Path.BACK_ADMIN;
	}
	
	@GetMapping(UrlConstant.Path.QUESTIONNAIRE_DETAIL)
	public String getBackAdminQuestionnaireDetail(
			HttpSession session,
			Model model, 
			RedirectAttributes redirectAttributes,
			@RequestParam(value = UrlConstant.QueryParam.ID, required = false) String questionnaireIdStr,
			HttpServletRequest request
			) throws Exception {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		if (isUpdateMode == null) 
			session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, StringUtils.hasText(questionnaireIdStr));
		else 
			session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, isUpdateMode);
		isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode == null)
				throw new Exception("isUpdateMode session is null");
			boolean isValidQueryString = 
					dataTransactionalService.isValidQueryString(request, null);
			boolean hasQueryString = StringUtils.hasText(request.getQueryString());
			if (isUpdateMode && !hasQueryString) {
				throw new Exception("Query string must be have in update mode");
			}
			if (!isUpdateMode && hasQueryString) {
				session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, hasQueryString);
				isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			}
			if (!isValidQueryString)
				throw new Exception("Query string is invalid");
			
			model = modelService.setPageTitle(model, PageConstant.Title.QUESTIONNAIRE_DETAIL);
			model = modelService.setFragmentName(model, ModelConstant.Value.QUESTIONNAIRE_DETAIL_CONTAINER);
			// For questionList.html aLink
			model = modelService.setDataListModel(model, null, true);
			
			List<Category> categoryList = categoryService.getCategoryList();
			if (categoryList == null) {
				categoryService.createCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				categoryService.createCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
			}
			else {
				if (!categoryService.hasCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY, categoryList)) {
					categoryService.createCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				}
				if (!categoryService.hasCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY, categoryList)) {
					categoryService.createCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
				}
			}
			List<Typing> typingList = typingService.getTypingList();
			if (typingList == null) {
				typingService.createTyping(DataConstant.Key.MULTIPLE_SELECT);
				typingService.createTyping(DataConstant.Key.SINGLE_SELECT);
				typingService.createTyping(DataConstant.Key.TEXT);
			}
			else {
				if (!typingService.hasTyping(DataConstant.Key.MULTIPLE_SELECT, typingList)) {
					typingService.createTyping(DataConstant.Key.MULTIPLE_SELECT);
				}
				if (!typingService.hasTyping(DataConstant.Key.SINGLE_SELECT, typingList)) {
					typingService.createTyping(DataConstant.Key.SINGLE_SELECT);
				}
				if (!typingService.hasTyping(DataConstant.Key.TEXT, typingList)) {
					typingService.createTyping(DataConstant.Key.TEXT);
				}
			}
			
			QuestionnaireSession questionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			List<User> userListInSession = 
					userService.getUserList(session);
			List<UserAnswer> userAnswerListInSession = 
					userAnswerService.getUserAnswerList(session);
			if (isUpdateMode) {
				if (questionnaireSession != null 
						&& !questionnaireSession.getQuestionnaireId().toString().equals(questionnaireIdStr)) {
					throw new Exception(RtnInfo.FAILED.getMessage());
				}
				if (questionnaireSession == null || questionSessionList == null) {
					Questionnaire questionnaire = 
							questionnaireService.getQuestionnaire(questionnaireIdStr);
					List<Question> questionList = 
							questionService.getQuestionList(questionnaireIdStr, true);
					if (questionnaire == null || questionList == null) {
						throw new Exception(RtnInfo.NOT_FOUND.getMessage());
					}
					questionnaireSessionService.setQuestionnaireSession(session, new QuestionnaireSession(questionnaire));
					questionnaireSession = 
							questionnaireSessionService.getQuestionnaireSession(session);
					List<QuestionSession> builtQuestionSessionList = 
							questionSessionService.getQuestionSessionList(questionList);
					questionSessionService.setQuestionSessionList(session, builtQuestionSessionList);
					questionSessionList = 
							questionSessionService.getQuestionSessionList(session);
					if (questionnaireSession == null || questionSessionList == null) {
						throw new Exception(RtnInfo.FAILED.getMessage());
					}
				}
				if (userListInSession == null || userAnswerListInSession == null) {
					List<User> userList = 
							userService.getUserList(questionnaireIdStr);
					List<UserAnswer> userAnswerList = 
							userAnswerService.getUserAnswerList(questionnaireIdStr);
					if (userList != null && userAnswerList != null) {
						userService.setUserList(session, userList);
						userAnswerService.setUserAnswerList(session, userAnswerList);
					}
					userListInSession = userList;
					userAnswerListInSession = userAnswerList;
				}
			}
			
			boolean isOverDateOrHasUser = 
					dataTransactionalService
					.isOverDateOrHasUser(questionnaireSession, userListInSession);
			model = modelService.setIsOverDateOrHasUser(model, isOverDateOrHasUser);
			
			model = modelService.setQuestionnaireModel(model, questionnaireSession);
			model = modelService.setQuestionListModelWithQuestionSession(model, questionSessionList);
			model = modelService.setUserListModel(model, userListInSession);
			model = modelService.setUserAnswerListModel(model, userAnswerListInSession);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Path.BACK_ADMIN 
					+ UrlConstant.Path.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Path.BACK_ADMIN;
	}
	
	@GetMapping(UrlConstant.Path.COMMON_QUESTION_LIST)
	public String getBackAdminCommonQuestionList(
			Model model,
			RedirectAttributes redirectAttributes
			) throws Exception {
		try {
			model = modelService.setPageTitle(model, PageConstant.Title.COMMON_QUESTION_LIST);
			model = modelService.setFragmentName(model, ModelConstant.Value.DATA_LIST);
			model = modelService.setDataListModel(model, true, false);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Path.BACK_ADMIN 
					+ UrlConstant.Path.COMMON_QUESTION_LIST;
		}
		return UrlConstant.Path.BACK_ADMIN;
	}
	
	@GetMapping(UrlConstant.Path.COMMON_QUESTION_DETAIL)
	public String getBackAdminCommonQuestionDetail(
			HttpSession session, 
			Model model, 
			RedirectAttributes redirectAttributes,
			@RequestParam(value = UrlConstant.QueryParam.ID, required = false) String commonQuestionIdStr,
			HttpServletRequest request
			) throws Exception {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		if (isUpdateMode == null) 
			session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, StringUtils.hasText(commonQuestionIdStr));
		else 
			session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, isUpdateMode);
		isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode == null)
				throw new Exception("isUpdateMode session is null");
			boolean isValidQueryString = 
					dataTransactionalService.isValidQueryString(request, null);
			boolean hasQueryString = StringUtils.hasText(request.getQueryString());
			if (isUpdateMode && !hasQueryString) {
				throw new Exception("Query string must be have in update mode");
			}
			if (!isUpdateMode && hasQueryString) {
				session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, hasQueryString);
				isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			}
			if (!isValidQueryString)
				throw new Exception("Query string is invalid");
			
			model = modelService.setPageTitle(model, PageConstant.Title.COMMON_QUESTION_DETAIL);
			model = modelService.setFragmentName(model, ModelConstant.Value.COMMON_QUESTION_DETAIL);
			// For questionList.html aLink
			model = modelService.setDataListModel(model, null, false);
			
			List<Category> categoryList = categoryService.getCategoryList();
			if (categoryList == null) {
				categoryService.createCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				categoryService.createCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
			}
			else {
				if (!categoryService.hasCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY, categoryList)) {
					categoryService.createCategory(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				}
				if (!categoryService.hasCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY, categoryList)) {
					categoryService.createCategory(DataConstant.Key.CUSTOMIZED_QUESTION_OF_CATEGORY);
				}
			}
			List<Typing> typingList = typingService.getTypingList();
			if (typingList == null) {
				typingService.createTyping(DataConstant.Key.MULTIPLE_SELECT);
				typingService.createTyping(DataConstant.Key.SINGLE_SELECT);
				typingService.createTyping(DataConstant.Key.TEXT);
			}
			else {
				if (!typingService.hasTyping(DataConstant.Key.MULTIPLE_SELECT, typingList)) {
					typingService.createTyping(DataConstant.Key.MULTIPLE_SELECT);
				}
				if (!typingService.hasTyping(DataConstant.Key.SINGLE_SELECT, typingList)) {
					typingService.createTyping(DataConstant.Key.SINGLE_SELECT);
				}
				if (!typingService.hasTyping(DataConstant.Key.TEXT, typingList)) {
					typingService.createTyping(DataConstant.Key.TEXT);
				}
			}
			
			CommonQuestionSession commonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (isUpdateMode) {
				if (commonQuestionSession != null 
						&& !commonQuestionSession.getCommonQuestionId().toString().equals(commonQuestionIdStr)) {
					throw new Exception(RtnInfo.FAILED.getMessage());
				}
				if (commonQuestionSession == null || questionSessionList == null) {
					CommonQuestion commonQuestion = 
							commonQuestionService.getCommonQuestion(commonQuestionIdStr);
					List<Question> questionList = 
							questionService.getQuestionList(commonQuestionIdStr, false);
					if (commonQuestion == null || questionList == null) {
						throw new Exception(RtnInfo.NOT_FOUND.getMessage());
					}
					commonQuestionSessionService
					.setCommonQuestionSession(session, new CommonQuestionSession(commonQuestion));
					commonQuestionSession = 
							commonQuestionSessionService.getCommonQuestionSession(session);
					List<QuestionSession> builtQuestionSessionList = 
							questionSessionService.getQuestionSessionList(questionList);
					questionSessionService.setQuestionSessionList(session, builtQuestionSessionList);
					questionSessionList = 
							questionSessionService.getQuestionSessionList(session);
					if (commonQuestionSession == null || questionSessionList == null) {
						throw new Exception(RtnInfo.FAILED.getMessage());
					}
				}
				if (commonQuestionSession != null) {
					UUID commonQuestionId = commonQuestionSession.getCommonQuestionId();
					boolean hasCommonQuestionThatSetByQuestionnaire =
							questionService
							.hasCommonQuestionThatSetByQuestionnaire(commonQuestionId);
					model = modelService.setHasCommonQuestionThatSetByQuestionnaire(model, hasCommonQuestionThatSetByQuestionnaire);
				}
			}
			
			model = modelService.setCommonQuestionModel(model, commonQuestionSession);
			model = modelService.setQuestionListModelWithQuestionSession(model, questionSessionList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Path.BACK_ADMIN 
					+ UrlConstant.Path.COMMON_QUESTION_LIST;
		}
		return UrlConstant.Path.BACK_ADMIN;
	}
	
}
