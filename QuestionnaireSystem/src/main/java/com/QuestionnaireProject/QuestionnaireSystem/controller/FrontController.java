package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.QuestionnaireProject.QuestionnaireSystem.constant.ModelConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.UrlConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.ModelService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.util.StringUtil;

@Controller
@RequestMapping(UrlConstant.Key.ROOT)
public class FrontController {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAnswerService userAnswerService;
	
	@Autowired
	private ModelService modelService;
	
	// front
	@GetMapping(UrlConstant.Key.QUESTIONNAIRE_LIST)
	public String getFrontQuestionnaireList(
			Model model,
			RedirectAttributes redirectAttributes
			) throws Exception {
		try {
			model = modelService.setFragmentName(model, ModelConstant.Value.DATA_LIST);
			// For front questionnaireList page
			model = modelService.setIsQuestionnaireList(model, true);
			model = modelService.setDataListModel(model, false, true);
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
					+ UrlConstant.Key.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Key.FRONT;
	}
	
	@GetMapping(UrlConstant.Key.ANSWERING_QUESTIONNAIRE_DETAIL)
	public String getFrontAnsweringQuestionnaireDetail(
			HttpSession session,
			Model model, 
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "ID", required = false) String questionnaireIdStr
			) throws Exception {
		try {
			model = modelService.setFragmentName(model, ModelConstant.Value.ANSWERING_QUESTIONNAIRE_DETAIL);
			model = modelService.setIsQuestionnaireList(model, false);
			
			Questionnaire questionnaire = 
					questionnaireService.getQuestionnaire(questionnaireIdStr);
			List<Question> questionList = 
					questionService.getQuestionList(questionnaireIdStr, true);
			User user = userService.getUser(session);
			List<UserAnswer> userAnswerList = userAnswerService.getUserAnswerList(session);
			if (!StringUtil.Func.isValidUUID(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire != null 
					&& !questionnaire.getQuestionnaireId().toString().equals(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire == null || questionList == null) {
				throw new Exception(RtnInfo.NOT_FOUND.getMessage());
			}
			
			model = modelService.setQuestionnaireModel(model, questionnaire);
			model = modelService.setQuestionListModel(model, questionList);
			model = modelService.setUserModel(model, user);
			model = modelService.setUserAnswerListModel(model, userAnswerList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Key.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Key.FRONT;
	}
	
	@GetMapping(UrlConstant.Key.CHECKING_QUESTIONNAIRE_DETAIL)
	public String getFrontCheckingQuestionnaireDetail(
			HttpSession session,
			Model model, 
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "ID", required = false) String questionnaireIdStr
			) throws Exception {
		try {
			model = modelService.setFragmentName(model, ModelConstant.Value.CHECKING_QUESTIONNAIRE_DETAIL);
			model = modelService.setIsQuestionnaireList(model, false);
			
			Questionnaire questionnaire = 
					questionnaireService.getQuestionnaire(questionnaireIdStr);
			List<Question> questionList = 
					questionService.getQuestionList(questionnaireIdStr, true);
			User user = userService.getUser(session);
			if (!StringUtil.Func.isValidUUID(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire != null 
					&& !questionnaire.getQuestionnaireId().toString().equals(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire == null || questionList == null || user == null) {
				throw new Exception(RtnInfo.NOT_FOUND.getMessage());
			}
			
			List<UserAnswer> userAnswerList = 
					userAnswerService.getUserAnswerList(session);
			if (userAnswerList == null) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			
			List<Question> filteredQuestionList = 
					questionService.getListOfQuestionFromUserAnswer(questionList, userAnswerList);
			model = modelService.setQuestionnaireModel(model, questionnaire);
			model = modelService.setQuestionListModel(model, filteredQuestionList);
			model = modelService.setUserModel(model, user);
			model = modelService.setUserAnswerListModel(model, userAnswerList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Key.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Key.FRONT;
	}
	
	@GetMapping(UrlConstant.Key.QUESTIONNAIRE_STATISTICS)
	public String getFrontQuestionnaireStatistics(
			Model model, 
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "ID", required = false) String questionnaireIdStr
			) throws Exception {
		try {
			model = modelService.setFragmentName(model, ModelConstant.Value.QUESTIONNAIRE_STATISTICS);
			model = modelService.setIsQuestionnaireList(model, false);
			
			Questionnaire questionnaire = 
					questionnaireService.getQuestionnaire(questionnaireIdStr);
			if (!StringUtil.Func.isValidUUID(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire != null 
					&& !questionnaire.getQuestionnaireId().toString().equals(questionnaireIdStr)) {
				throw new Exception(RtnInfo.FAILED.getMessage());
			}
			if (questionnaire == null) {
				throw new Exception(RtnInfo.NOT_FOUND.getMessage());
			}
			
			model = modelService.setQuestionnaireModel(model, questionnaire);
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ModelConstant.Key.ERROR_MESSAGE, ModelConstant.Value.FAILED);
			return UrlConstant.Control.REDIRECT 
					+ UrlConstant.Key.QUESTIONNAIRE_LIST;
		}
		return UrlConstant.Key.FRONT;
	}
	
}
