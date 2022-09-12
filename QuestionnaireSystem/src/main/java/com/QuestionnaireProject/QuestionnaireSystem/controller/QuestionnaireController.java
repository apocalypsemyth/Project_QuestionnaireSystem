package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.DataTransactionalService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionnaireListResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionnaireResp;

@RestController
public class QuestionnaireController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private QuestionnaireSessionService questionnaireSessionService;
	
	@Autowired
	private DataTransactionalService dataTransactionalService;
	
	@PostMapping(value = "/getQuestionnaire", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireResp getQuestionnaire(
			@RequestBody PostQuestionnaireReq req, 
			HttpSession session
			) {
		QuestionnaireSession questionnaireSessionResp = new QuestionnaireSession();
		String questionnaireId = req.getQuestionnaireId();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode == null) {
				if (!StringUtils.hasText(questionnaireId)) {
					session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, false);
					return new QuestionnaireResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							null
							);
				}
				session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, true);
				isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			}
			
			QuestionnaireSession questionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			if (isUpdateMode) {
				if (questionnaireSession == null) {
					Questionnaire questionnaire = 
							questionnaireService.getQuestionnaire(questionnaireId);
					if (questionnaire == null) {
						return new QuestionnaireResp(
								RtnInfo.NOT_FOUND.getCode(), 
								RtnInfo.NOT_FOUND.getMessage(),
								null
								);
					}
					questionnaireSessionService
					.setQuestionnaireSession(session, new QuestionnaireSession(questionnaire));
					questionnaireSession =
							questionnaireSessionService.getQuestionnaireSession(session);
					if (questionnaireSession == null) {
						return new QuestionnaireResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					return new QuestionnaireResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							questionnaireSession
							);
				}
				return new QuestionnaireResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						questionnaireSession
						);
			}
			
			questionnaireSessionResp = questionnaireSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionnaireResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionnaireResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionnaireSessionResp
				);
	}
	
	@GetMapping(value = "/getQuestionnaireList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireListResp getQuestionnaireList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
		    @RequestParam(defaultValue = "1") String index,
			HttpServletRequest request
			) {
		List<Questionnaire> questionnaireListResp = new ArrayList<>();
		int totalData = 0;
		int totalRows = 0;
		int pageSize = DataConstant.Key.PAGE_SIZE;
		int pageIndex = 0;
		try {
			pageIndex = Integer.parseInt(index) - 1;
			boolean isValidQueryString = 
					dataTransactionalService.isValidQueryString(request, true);
			if (!isValidQueryString) 
				throw new Exception("query string is invalid");
			
			totalData = questionnaireService.getQuestionnaireTotal();
			DataListForPager questionnaireListObj = 
					questionnaireService
					.getQuestionnaireList(
							keyword, 
							startDate, 
							endDate, 
							pageSize, 
							pageIndex
							);
			totalRows = questionnaireListObj.getTotalRows();
			List<Questionnaire> questionnaireList = questionnaireListObj.getQuestionnaireList();
			if (questionnaireList == null || questionnaireList.isEmpty()) {
				return new QuestionnaireListResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						null,
						totalData,
						totalRows,
						pageSize,
						pageIndex
						);
			}
			
			questionnaireListResp = questionnaireList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionnaireListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null,
					totalData,
					totalRows,
					pageSize,
					pageIndex
					);
		}
		return new QuestionnaireListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionnaireListResp,
				totalData,
				totalRows,
				pageSize,
				pageIndex
				);
	}
	
	@PostMapping(value = "/createQuestionnaire",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireResp createQuestionnaire(
			@RequestBody PostQuestionnaireReq req,
			HttpSession session
			) {
		QuestionnaireSession questionnaireSessionResp = new QuestionnaireSession();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode) {
				return new QuestionnaireResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			QuestionnaireSession questionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			if (questionnaireSession == null) {
				String questionnaireIdStr = UUID.randomUUID().toString();
				req.setQuestionnaireId(questionnaireIdStr);
				questionnaireSessionService.createQuestionnaireSession(session, req);
				QuestionnaireSession newQuestionnaireSession = 
						questionnaireSessionService.getQuestionnaireSession(session);
				if (newQuestionnaireSession == null) {
					return new QuestionnaireResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				return new QuestionnaireResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						newQuestionnaireSession
						);
			}
			
			boolean isToUpdateQuestionnaireSessionChange = 
					questionnaireSessionService
					.isToUpdateQuestionnaireSessionChange(session, req);
			if (!isToUpdateQuestionnaireSessionChange) {
				return new QuestionnaireResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						questionnaireSession
						);
			}
			
			questionnaireSessionService.updateQuestionnaireSession(session, req);
			QuestionnaireSession updatedQuestionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			if (updatedQuestionnaireSession == null) {
				return new QuestionnaireResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			questionnaireSessionResp = updatedQuestionnaireSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionnaireResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionnaireResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionnaireSessionResp
				);
	}
	
	@PostMapping(value = "/updateQuestionnaire", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireResp updateQuestionnaire(
			@RequestBody PostQuestionnaireReq req,
			HttpSession session
			) {
		QuestionnaireSession questionnaireSessionResp = new QuestionnaireSession();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (!isUpdateMode) {
				return new QuestionnaireResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			QuestionnaireSession questionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			if (questionnaireSession == null) {
				return new QuestionnaireResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			boolean isToUpdateQuestionnaireSessionChange = 
					questionnaireSessionService
					.isToUpdateQuestionnaireSessionChange(session, req);
			if (!isToUpdateQuestionnaireSessionChange) {
				return new QuestionnaireResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						questionnaireSession
						);
			}
			
			questionnaireSessionService.updateQuestionnaireSession(session, req);
			QuestionnaireSession updatedQuestionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			if (updatedQuestionnaireSession == null) {
				return new QuestionnaireResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			questionnaireSessionResp = updatedQuestionnaireSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionnaireResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new QuestionnaireResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				questionnaireSessionResp
				);
	}
	
}
