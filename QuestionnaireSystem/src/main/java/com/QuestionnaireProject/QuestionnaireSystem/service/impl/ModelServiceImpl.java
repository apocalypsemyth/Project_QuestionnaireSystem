package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.QuestionnaireProject.QuestionnaireSystem.constant.ModelConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.ModelService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;

@Service
public class ModelServiceImpl implements ModelService {

	@Override
	public Model setFragmentName(
			Model model, 
			String fragmentName
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.FRAGMENT_NAME, fragmentName);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setDataListModel(
			Model model, 
			Boolean isBackAdmin, 
			Boolean isQuestionnaire
			) throws Exception {
		try {
			Map<String, Object> dataListModel = new HashMap<>();
			if (isBackAdmin != null)
				dataListModel.put("isBackAdmin", isBackAdmin);
			if (isQuestionnaire != null)
				dataListModel.put("isQuestionnaire", isQuestionnaire);
			model.addAttribute(ModelConstant.Key.DATA_LIST_MODEL, dataListModel);
			
			return model;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setIsQuestionnaireList(
			Model model, 
			boolean isQuestionnaireList
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.IS_QUESTIONNAIRE_LIST, isQuestionnaireList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setUserModel(Model model, User user) throws Exception {
		try {
			Map<String, Object> userModel = new HashMap<>();
			boolean hasData = user != null;
			
			if (hasData) {
				userModel.put("userName", user.getUserName());
				userModel.put("phone", user.getPhone());
				userModel.put("email", user.getEmail());
				userModel.put("age", user.getAge());
				userModel.put(
						"answerDate", 
						DateTimeUtil.Convert.convertDateToString(user.getAnswerDate())
						);
			}
			else {
				userModel.put("userName", null);
				userModel.put("phone", null);
				userModel.put("email", null);
				userModel.put("age", null);
				userModel.put("answerDate", null);
			}
			model.addAttribute(ModelConstant.Key.USER_MODEL, userModel);
			
			return model;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setUserListModel(
			Model model,
			List<User> userList
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.USER_LIST_MODEL, userList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setUserAnswerListModel(
			Model model,
			List<UserAnswer> userAnswerList
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.USER_ANSWER_LIST_MODEL, userAnswerList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setQuestionnaireModel(
			Model model, 
			Questionnaire questionnaire
			) throws Exception {
		try {
			Map<String, Object> questionnaireModel = new HashMap<>();
			boolean hasData = questionnaire != null;
			
			if (hasData) {
				questionnaireModel.put("caption", questionnaire.getCaption());
				questionnaireModel.put("description", questionnaire.getDescription());
				questionnaireModel.put(
						"startDate", 
						DateTimeUtil
						.Convert
						.convertDateToString(questionnaire.getStartDate())
						);
				questionnaireModel.put(
						"endDate", 
						questionnaire.getEndDate() == null 
						? null 
						: DateTimeUtil
							.Convert
							.convertDateToString(questionnaire.getEndDate())
						);
				questionnaireModel.put("isEnable", questionnaire.getIsEnable());
			}
			else {
				questionnaireModel.put("caption", null);
				questionnaireModel.put("description", null);
				questionnaireModel.put("startDate", DateTimeUtil.Func.getStringDate());
				questionnaireModel.put("endDate", null);
				questionnaireModel.put("isEnable", "checked");
			}
			model.addAttribute(ModelConstant.Key.QUESTIONNAIRE_MODEL, questionnaireModel);
			
			return model;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setQuestionnaireModel(
			Model model, 
			QuestionnaireSession questionnaireSession
			) throws Exception {
		try {
			Map<String, Object> questionnaireModel = new HashMap<>();
			boolean hasData = questionnaireSession != null;
			
			if (hasData) {
				questionnaireModel.put("caption", questionnaireSession.getCaption());
				questionnaireModel.put("description", questionnaireSession.getDescription());
				questionnaireModel.put(
						"startDate", 
						DateTimeUtil
						.Convert
						.convertLocalDateTimeToString(questionnaireSession.getStartDate())
						);
				questionnaireModel.put(
						"endDate", 
						questionnaireSession.getEndDate() == null 
						? null 
								: DateTimeUtil
								.Convert
								.convertLocalDateTimeToString(questionnaireSession.getEndDate())
						);
				questionnaireModel.put("isEnable", questionnaireSession.getIsEnable());
			}
			else {
				questionnaireModel.put("caption", null);
				questionnaireModel.put("description", null);
				questionnaireModel.put("startDate", DateTimeUtil.Func.getStringDate());
				questionnaireModel.put("endDate", null);
				questionnaireModel.put("isEnable", "checked");
			}
			model.addAttribute(ModelConstant.Key.QUESTIONNAIRE_MODEL, questionnaireModel);
			
			return model;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public Model setCommonQuestionModel(
			Model model, 
			CommonQuestionSession commonQuestionSession
			)
			throws Exception {
		try {
			Map<String, Object> commonQuestionModel = new HashMap<>();
			boolean hasData = commonQuestionSession != null;
			
			if (hasData) {
				commonQuestionModel.put("commonQuestionName", commonQuestionSession.getCommonQuestionName());
			}
			else {
				commonQuestionModel.put("commonQuestionName", null);
			}
			model.addAttribute(ModelConstant.Key.COMMON_QUESTION_MODEL, commonQuestionModel);
			
			return model;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public Model setQuestionListModel(
			Model model,
			List<Question> questionList
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.QUESTION_LIST_MODEL, questionList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setQuestionListModelWithQuestionSession(
			Model model,
			List<QuestionSession> questionSessionList
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.QUESTION_LIST_MODEL, questionSessionList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setIsOverDateOrHasUser(
			Model model,
			boolean isOverDateOrHasUser
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.IS_OVER_DATE_OR_HAS_USER, isOverDateOrHasUser);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Model setErrorMessage(
			Model model,
			String errorMessage
			) throws Exception {
		try {
			return model.addAttribute(ModelConstant.Key.ERROR_MESSAGE, errorMessage);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
