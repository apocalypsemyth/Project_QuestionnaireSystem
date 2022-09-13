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
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.DataTransactionalService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.CommonQuestionListResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.CommonQuestionResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostCommonQuestionReq;

@RestController
public class CommonQuestionController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommonQuestionService commonQuestionService;
	
	@Autowired
	private CommonQuestionSessionService commonQuestionSessionService;
	
	@Autowired
	private DataTransactionalService dataTransactionalService;
	
	@PostMapping(value = "/getCommonQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionResp getCommonQuestion(
			@RequestBody PostCommonQuestionReq req,
			HttpSession session
			) {
		CommonQuestionSession commonQuestionSessionResp = new CommonQuestionSession();
		String commonQuestionId = req.getCommonQuestionId();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode == null) {
				if (!StringUtils.hasText(commonQuestionId)) {
					session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, false);
					return new CommonQuestionResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							null
							);
				}
				session.setAttribute(SessionConstant.Name.IS_UPDATE_MODE, true);
				isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			}
			
			CommonQuestionSession commonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			if (isUpdateMode) {
				if (commonQuestionSession == null) {
					CommonQuestion commonQuestion = 
							commonQuestionService.getCommonQuestion(commonQuestionId);
					if (commonQuestion == null) {
						return new CommonQuestionResp(
								RtnInfo.NOT_FOUND.getCode(), 
								RtnInfo.NOT_FOUND.getMessage(),
								null
								);
					}
					commonQuestionSessionService
					.setCommonQuestionSession(session, new CommonQuestionSession(commonQuestion));
					commonQuestionSession =
							commonQuestionSessionService.getCommonQuestionSession(session);
					if (commonQuestionSession == null) {
						return new CommonQuestionResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					return new CommonQuestionResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(),
							commonQuestionSession
							);
				}
				return new CommonQuestionResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						commonQuestionSession
						);
			}
			
			commonQuestionSessionResp = commonQuestionSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CommonQuestionResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new CommonQuestionResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				commonQuestionSessionResp
				);
	}
	
	@GetMapping(value = "/getCommonQuestionList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionListResp getCommonQuestionList(
			@RequestParam(required = false) String keyword,
		    @RequestParam(defaultValue = "1") String index,
		    HttpServletRequest request
		    ) {
		List<CommonQuestion> commonQuestionListResp = new ArrayList<>();
		int totalData = 0;
		int totalRows = 0;
		int pageSize = DataConstant.Key.PAGE_SIZE;
		int pageIndex = 0;
		try {
			pageIndex = Integer.parseInt(index) - 1;
			boolean isValidQueryString = 
					dataTransactionalService.isValidQueryString(request, false);
			if (!isValidQueryString) 
				throw new Exception("Query string is invalid");
			
			totalData = commonQuestionService.getCommonQuestionTotal();
			DataListForPager commonQuestionListObj = 
					commonQuestionService
					.getCommonQuestionList(
							keyword, 
							pageSize, 
							pageIndex
							);
			totalRows = commonQuestionListObj.getTotalRows();
			List<CommonQuestion> commonQuestionList = commonQuestionListObj.getCommonQuestionList();
			if (commonQuestionList == null || commonQuestionList.isEmpty()) {
				return new CommonQuestionListResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						null,
						totalData,
						totalRows,
						pageSize,
						pageIndex
						);
			}
			
			commonQuestionListResp = commonQuestionList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CommonQuestionListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null,
					totalData,
					totalRows,
					pageSize,
					pageIndex
					);
		}
		return new CommonQuestionListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				commonQuestionListResp,
				totalData,
				totalRows,
				pageSize,
				pageIndex
				);
	}
	
	@PostMapping(value = "/createCommonQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionResp createCommonQuestion(
			@RequestBody PostCommonQuestionReq req,
			HttpSession session
			) {
		CommonQuestionSession commonQuestionSessionResp = new CommonQuestionSession();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (isUpdateMode) {
				return new CommonQuestionResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			CommonQuestionSession commonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			if (commonQuestionSession == null) {
				String commonQuestionIdStr = UUID.randomUUID().toString();
				req.setCommonQuestionId(commonQuestionIdStr);
				commonQuestionSessionService.createCommonQuestionSession(session, req);
				CommonQuestionSession newCommonQuestionSession = 
						commonQuestionSessionService.getCommonQuestionSession(session);
				if (newCommonQuestionSession == null) {
					return new CommonQuestionResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				return new CommonQuestionResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						newCommonQuestionSession
						);
			}
			
			boolean isToUpdateCommonQuestionSessionChange = 
					commonQuestionSessionService.isToUpdateCommonQuestionSessionChange(session, req);
			if (!isToUpdateCommonQuestionSessionChange) {
				return new CommonQuestionResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						commonQuestionSession
						);
			}
			
			commonQuestionSessionService.updateCommonQuestionSession(session, req);
			CommonQuestionSession updatedCommonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			if (updatedCommonQuestionSession == null) {
				return new CommonQuestionResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			commonQuestionSessionResp = updatedCommonQuestionSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CommonQuestionResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new CommonQuestionResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				commonQuestionSessionResp
				);
	}
	
	@PostMapping(value = "/updateCommonQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionResp updateCommonQuestion(
			@RequestBody PostCommonQuestionReq req,
			HttpSession session
			) {
		CommonQuestionSession commonQuestionSessionResp = new CommonQuestionSession();
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			if (!isUpdateMode) {
				return new CommonQuestionResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			CommonQuestionSession commonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			if (commonQuestionSession == null) {
				return new CommonQuestionResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			
			boolean isToUpdateCommonQuestionSessionChange = 
					commonQuestionSessionService.isToUpdateCommonQuestionSessionChange(session, req);
			if (!isToUpdateCommonQuestionSessionChange) {
				return new CommonQuestionResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(),
						commonQuestionSession
						);
			}
			
			commonQuestionSessionService.updateCommonQuestionSession(session, req);
			CommonQuestionSession updatedCommonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			if (updatedCommonQuestionSession == null) {
				return new CommonQuestionResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(),
						null
						);
			}
			commonQuestionSessionResp = updatedCommonQuestionSession;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CommonQuestionResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(),
					null
					);
		}
		return new CommonQuestionResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(),
				commonQuestionSessionResp
				);
	}
	
}
