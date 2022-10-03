package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.IForHtmlMethod;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionListResp;

@RestController
public class QuestionController implements IForHtmlMethod {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuestionSessionService questionSessionService;
	
	@Autowired
	private QuestionnaireSessionService questionnaireSessionService;
	
	@Autowired
	private CommonQuestionSessionService commonQuestionSessionService;
	
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
				if (questionSessionList == null) 
					return RtnInfo.FAILED.getMessage();
				
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
	
	public String hasQuestionSession(
			HttpSession session
			) {
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
						questionSessionService
						.getQuestionSessionThatIsNotDeletedList(questionSessionList);
				if (filteredQuestionSessionList == null) {
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
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
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
			
			boolean isQuestionFromCommonQuestion = false;
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (isQuestionnaire) {
				isQuestionFromCommonQuestion = 
						questionSessionService.isQuestionFromCommonQuestion(req);
			}
			else {
				if (questionSessionList != null) {
					if (isUpdateMode) {
						List<QuestionSession> filteredQuestionSessionList = 
								questionSessionService
								.getQuestionSessionThatIsNotDeletedList(questionSessionList);
						if (filteredQuestionSessionList != null) {
							if (filteredQuestionSessionList.size() == 1) {
								return new QuestionListResp(
										RtnInfo.JUST_ONE_QUESTION.getCode(), 
										RtnInfo.JUST_ONE_QUESTION.getMessage(),
										questionSessionList
										);
							}
						}
					}
					else {
						if (questionSessionList.size() == 1) {
							return new QuestionListResp(
									RtnInfo.JUST_ONE_QUESTION.getCode(), 
									RtnInfo.JUST_ONE_QUESTION.getMessage(),
									questionSessionList
									);
						}
					}
				}
			}
			
			questionSessionService
			.createQuestionSession(session, req, isQuestionnaire, isQuestionFromCommonQuestion);
			questionSessionList = 
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
		try {
			if (!StringUtils.hasText(questionId)) {
				return new QuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null
						);
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
				if (isUpdateMode) {
					String commonQuestionIdStr = 
							commonQuestionSession.getCommonQuestionId().toString();
					req.setCommonQuestionId(commonQuestionIdStr);
				}
			}
			
			questionSessionService.deleteQuestionSessionList(session, req);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			
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
	
}
