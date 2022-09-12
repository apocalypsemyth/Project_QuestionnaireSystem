package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.IForHtmlMethod;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CategoryService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionListResp;

@RestController
public class QuestionController implements IForHtmlMethod {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionSessionService questionSessionService;
	
	@Autowired
	private QuestionnaireSessionService questionnaireSessionService;
	
	@Autowired
	private CommonQuestionSessionService commonQuestionSessionService;
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping(value = "/getQuestionList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp getQuestionList(
			@RequestBody PostQuestionReq req, 
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		String questionnaireIdStr = req.getQuestionnaireId();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode == null) {
				if (!StringUtils.hasText(questionnaireIdStr)) {
					session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, false);
					return new QuestionListResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							new ArrayList<>()
							);
				}
				session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, true);
				isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			}
			
			List<QuestionSession> questionSessionList = 
					questionSessionService
					.getQuestionSessionList(session);
			if (isUpdateMode) {
				if (questionSessionList == null) {
					List<Question> questionList = 
							questionService
							.getQuestionList(questionnaireIdStr, true);
					if (questionList == null) {
						return new QuestionListResp(
								RtnInfo.NOT_FOUND.getCode(), 
								RtnInfo.NOT_FOUND.getMessage(),
								null
								);
					}
					List<QuestionSession> builtQuestionSessionList = 
							questionSessionService
							.getQuestionSessionList(questionList);
					questionSessionService
					.setQuestionSessionList(session, builtQuestionSessionList);
					questionSessionList = 
							questionSessionService
							.getQuestionSessionList(session);
					return new QuestionListResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							questionSessionList
							);
				}
				return new QuestionListResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						questionSessionList
						);
			}
			
			questionSessionListResp = questionSessionList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
		}
	
	@PostMapping(value = "/showBtnDeleteQuestionByHasData", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public String showBtnDeleteQuestionByHasData(
			@RequestBody PostQuestionReq req, 
			HttpSession session
			) {
		String questionnaireId = req.getQuestionnaireId();
		String commonQuestionId = req.getCommonQuestionId();
		boolean hasQuestionnaireId = StringUtils.hasText(questionnaireId);
		boolean hasCommonQuestionId = StringUtils.hasText(commonQuestionId);
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (isUpdateMode) {
				if (!hasQuestionnaireId && !hasCommonQuestionId) 
					return RtnInfo.FAILED.getMessage();
				if (hasQuestionnaireId && hasCommonQuestionId) 
					return RtnInfo.FAILED.getMessage();
				if (questionSessionList == null) {
					return RtnInfo.FAILED.getMessage();
				}
				boolean areAllQuestionSessionListIsDeleted = 
						areAllQuestionSessionListIsDeleted(questionSessionList);
				if (areAllQuestionSessionListIsDeleted) {
					return RtnInfo.NOT_EMPTY_EMPTY.getMessage();
				}
				return null;
			}
			
			if (questionSessionList == null) {
				return RtnInfo.SUCCESSFUL.getMessage();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
		return null;
	}
	
	@GetMapping(value = "/hasQuestionSession")
	
	public String hasQuestionSession(HttpSession session) {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {			
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (questionSessionList == null) {
				return isUpdateMode 
						? RtnInfo.FAILED.getMessage() 
						: RtnInfo.ONE_QUESTION.getMessage();
			}
			
			if (isUpdateMode) {
				List<QuestionSession> filteredQuestionSessionList =
						questionSessionList
						.stream()
						.filter(item -> !item.getIsDeleted())
						.collect(Collectors.toList());
				if (filteredQuestionSessionList == null 
						|| filteredQuestionSessionList.isEmpty()) {
					return RtnInfo.ONE_QUESTION.getMessage();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
		return null;
	}
	
	@PostMapping(value = "/createQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp createQuestion(
			@RequestBody PostQuestionReq req, 
			@RequestParam(value = "is_questionnaire") String isQuestionnaireStr,
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			boolean isQuestionnaire = Boolean.valueOf(isQuestionnaireStr);
			if (isQuestionnaire) {
				QuestionnaireSession questionnaireSession = 
						questionnaireSessionService.getQuestionnaireSession(session);
				if (questionnaireSession == null) {
					return new QuestionListResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				String questionnaireIdStr = 
						questionnaireSession.getQuestionnaireId().toString();
				req.setQuestionnaireId(questionnaireIdStr);
			}
			else {
				CommonQuestionSession commonQuestionSession = 
						commonQuestionSessionService.getCommonQuestionSession(session);
				if (commonQuestionSession == null) {
					return new QuestionListResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				String commonQuestionIdStr = 
						commonQuestionSession.getCommonQuestionId().toString();
				req.setCommonQuestionId(commonQuestionIdStr);
				req.setIsTemplateOfCommonQuestion(true);
			}
			
			if (isSetQuestionListOfCommonQuestion != null 
					&& isSetQuestionListOfCommonQuestion) {
				boolean isCommonQuestionOfCategory = 
						req
						.getQuestionCategory()
						.equals(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				if (!isCommonQuestionOfCategory) {
					questionSessionService
					.convertQuestionListOfCommonQuestionToQuestionList(session);
					List<QuestionSession> updatedQuestionSessionList = 
							questionSessionService.getQuestionSessionList(session);
					if (updatedQuestionSessionList == null) {
						return new QuestionListResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					session.setAttribute(
							SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, 
							false
							);
				}
			}
			
			questionSessionService.createQuestionSession(session, req, isQuestionnaire);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (questionSessionList == null) {
				return new QuestionListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			questionSessionListResp = questionSessionList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
	}
	
	@PostMapping(value = "/showToUpdateQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp showToUpdateQuestion(
			@RequestBody PostQuestionReq req, 
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		String questionId = req.getQuestionId();
		try {
			if (!StringUtils.hasText(questionId)) {
				return new QuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null
						);
			}
			
			QuestionSession questionSession = 
					questionSessionService.getQuestionSession(session, questionId);
			if (questionSession == null) {
				return new QuestionListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			questionSessionListResp.add(questionSession);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
	}
	
	@PostMapping(value = "/updateQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp updateQuestion(
			@RequestBody PostQuestionReq req, 
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		String questionId = req.getQuestionId();
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			if (!StringUtils.hasText(questionId)) {
				return new QuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null
						);
			}
			
			if (isSetQuestionListOfCommonQuestion != null 
					&& isSetQuestionListOfCommonQuestion) {
				boolean isCommonQuestionOfCategory = 
						req
						.getQuestionCategory()
						.equals(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY);
				if (!isCommonQuestionOfCategory) {
					questionSessionService
					.convertQuestionListOfCommonQuestionToQuestionList(session);
					List<QuestionSession> updatedQuestionSessionList = 
							questionSessionService.getQuestionSessionList(session);
					if (updatedQuestionSessionList == null) {
						return new QuestionListResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					session.setAttribute(
							SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, 
							false
							);
				}
			}
			
			questionSessionService.updateQuestionSession(session, req);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (questionSessionList == null) {
				return new QuestionListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			questionSessionListResp = questionSessionList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
	}
	
	@PostMapping(value = "/deleteQuestionList", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp deleteQuestionList(
			@RequestBody PostQuestionReq req, 
			@RequestParam(value = "is_questionnaire") String isQuestionnaireStr,
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		List<String> questionIdList = req.getQuestionIdList();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			if (questionIdList == null || questionIdList.isEmpty()) {
				return new QuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null
						);
			}
			
			boolean isQuestionnaire = Boolean.valueOf(isQuestionnaireStr);
			if (isQuestionnaire) {
				//為何只檢查更新模式，原因如下
				//因為在新增模式，一直不按加入問題按鈕，持續選取自訂或常用問題時，會發生錯誤。
				if (isUpdateMode) {
					QuestionnaireSession questionnaireSession = 
							questionnaireSessionService.getQuestionnaireSession(session);
					if (questionnaireSession == null) {
						return new QuestionListResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					String questionnaireIdStr =
							questionnaireSession.getQuestionnaireId().toString();
					req.setQuestionnaireId(questionnaireIdStr);
				}
			}
			else {
				CommonQuestionSession commonQuestionSession = 
						commonQuestionSessionService.getCommonQuestionSession(session);
				if (commonQuestionSession == null) {
					return new QuestionListResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				if (isUpdateMode) {
					String commonQuestionIdStr = 
							commonQuestionSession.getCommonQuestionId().toString();
					req.setCommonQuestionId(commonQuestionIdStr);
				}
			}
			
			questionSessionService.deleteQuestionSessionList(session, req);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (isSetQuestionListOfCommonQuestion != null 
					&& isSetQuestionListOfCommonQuestion) {
				if (questionSessionList != null) {
					questionSessionService
					.convertQuestionListOfCommonQuestionToQuestionList(session);
					questionSessionList = 
							questionSessionService.getQuestionSessionList(session);
					if (questionSessionList == null) {
						return new QuestionListResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
				}
				session.setAttribute(
						SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, 
						false
						);
			}
			
			questionSessionListResp = questionSessionList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
	}
	
	@GetMapping(value = "/getIsSetQuestionListOfCommonQuestion")
	
	public String getIsSetQuestionListOfCommonQuestion(
			HttpSession session
			) {
		String isSet = "";
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			if (isSetQuestionListOfCommonQuestion == null) {
				return RtnInfo.FAILED.getMessage();
			}
			if (!isSetQuestionListOfCommonQuestion) {
				isSet = "false";
				return isSet;
			}
			isSet = "true";
			return isSet;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
	}
	
	@PostMapping(value = "/setIsSetQuestionListOfCommonQuestion")
	
	public String setIsSetQuestionListOfCommonQuestion(
			HttpSession session,
			@RequestParam(value = "is_set") String isSetStr
			) {
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			if (isSetQuestionListOfCommonQuestion == null) {
				return RtnInfo.FAILED.getMessage();
			}
			
			boolean isSet = Boolean.parseBoolean(isSetStr);
			if (!isSet) {
				session.setAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, isSet);
				return "false";
			}
			session.setAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, isSet);
			return "true";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
	}
	
	@PostMapping(value = "/setQuestionListOfCommonQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionListResp setQuestionListOfCommonQuestion(
			@RequestBody PostQuestionReq req, 
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		String categoryIdStr = req.getCategoryId();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		session.setAttribute(
				SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION, 
				true
				);
		try {
			if (!StringUtils.hasText(categoryIdStr)) {
				return new QuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null
						);
			}
			
			Category category = categoryService.getCategory(categoryIdStr);
			if (category == null) {
				return new QuestionListResp(
						RtnInfo.NOT_FOUND.getCode(), 
						RtnInfo.NOT_FOUND.getMessage(),
						null
						);
			}
			String commonQuestionIdStr = category.getCommonQuestionId().toString();
			List<Question> questionList = 
					questionService
					.getQuestionList(commonQuestionIdStr, false);
			if (questionList == null) {
				return new QuestionListResp(
						RtnInfo.NOT_FOUND.getCode(), 
						RtnInfo.NOT_FOUND.getMessage(),
						null
						);
			}
			List<QuestionSession> builtQuestionSessionList = 
					questionSessionService
					.getQuestionSessionList(questionList);
			builtQuestionSessionList = 
					questionSessionService
					.getSetPropertyOfQuestionListOfCommonQuestion(builtQuestionSessionList);
			if (isUpdateMode) {
				List<QuestionSession> questionSessionList = 
						questionSessionService
						.getQuestionSessionList(session);
				if (questionSessionList == null) {
					return new QuestionListResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				builtQuestionSessionList.addAll(questionSessionList);
			}
			
			questionSessionService
			.setQuestionSessionList(session, builtQuestionSessionList);
			questionSessionListResp = 
					questionSessionService
					.getQuestionSessionList(session);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionSessionListResp
				);
	}
	
	@GetMapping(value = "/deleteSetQuestionListOfCommonQuestion")
	
	public String deleteSetQuestionListOfCommonQuestion(
			HttpSession session
			) {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode) {
				List<QuestionSession> questionSessionList =
						questionSessionService.getQuestionSessionList(session);
				if (questionSessionList == null) {
					return RtnInfo.FAILED.getMessage();
				}
				questionSessionService
				.setIsDeletedOfQuestionListOrOfCommonQuestion(session, questionSessionList);
				questionSessionList =
						questionSessionService.getQuestionSessionList(session);
				if (questionSessionList == null) {
					return RtnInfo.FAILED.getMessage();
				}
				return null;
			}
			questionSessionService.deleteAllQuestionSessionList(session);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
		return null;
	}
	
}
