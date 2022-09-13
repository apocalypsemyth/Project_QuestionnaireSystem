package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.model.CommonQuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.QuestionnaireSession;
import com.QuestionnaireProject.QuestionnaireSystem.model.UserAnswerDetailForCSV;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.DataTransactionalService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireSessionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.CommonQuestionListResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.CommonQuestionResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostCommonQuestionReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostQuestionnaireReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserAnswerReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionnaireListResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionnaireResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.QuestionnaireStatisticsResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.UserAnswerDetailResp;

@RestController
public class DataTransactionalController {
	
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
	private DataTransactionalService dataTransactionalService;
	
	@PostMapping(value = "/getQuestionnaireStatistics", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireStatisticsResp getQuestionnaireStatistics(
			@RequestBody PostQuestionnaireReq req
			) {
		List<Question> questionListResp = new ArrayList<>();
		List<UserAnswer> userAnswerListResp = new ArrayList<>();
		String questionnaireIdStr = req.getQuestionnaireId();
		try {
			List<Question> questionList = 
					questionService.getQuestionList(questionnaireIdStr, true);
			List<UserAnswer> userAnswerList = 
					userAnswerService.getUserAnswerList(questionnaireIdStr);
			if (questionList == null) {
				return new QuestionnaireStatisticsResp(
						RtnInfo.FAILED.getCode(),
						RtnInfo.FAILED.getMessage(),
						null,
						null
						);
			}
			if (userAnswerList == null) {
				return new QuestionnaireStatisticsResp(
						RtnInfo.NOT_EMPTY_EMPTY.getCode(),
						RtnInfo.NOT_EMPTY_EMPTY.getMessage(),
						questionList,
						null
						);
			}
			
			questionListResp = questionList;
			userAnswerListResp = userAnswerList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new QuestionnaireStatisticsResp(
					RtnInfo.FAILED.getCode(),
					RtnInfo.FAILED.getMessage(),
					null,
					null
					);
		}
		return new QuestionnaireStatisticsResp(
				RtnInfo.SUCCESSFUL.getCode(),
				RtnInfo.SUCCESSFUL.getMessage(),
				questionListResp,
				userAnswerListResp
				);
	}
	
	@PostMapping(value = "/getUserAnswerDetail", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserAnswerDetailResp getUserAnswerDetail(
			@RequestBody PostUserAnswerReq req,
			HttpSession session
			) {
		List<QuestionSession> questionSessionListResp = new ArrayList<>();
		User userResp = new User();
		List<UserAnswer> userAnswerListResp = new ArrayList<>();
		String questionnaireIdStr = req.getQuestionnaireId();
		String userIdStr = req.getUserId();
		try {
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (questionSessionList == null) {
				return new UserAnswerDetailResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null,
						null,
						null
						);
			}
			
			List<User> userListInSession = 
					userService.getUserList(session);
			if (userListInSession == null) {
				return new UserAnswerDetailResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null,
						null,
						null
						);
			}
			
			List<UserAnswer> userAnswerListInSession = 
					userAnswerService.getUserAnswerList(session);
			if (userAnswerListInSession == null) {
				List<UserAnswer> userAnswerList = 
						userAnswerService.getUserAnswerList(questionnaireIdStr);
				if (userAnswerList == null) {
					return new UserAnswerDetailResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(), 
							null,
							null,
							null
							);
				}
				userAnswerService.setUserAnswerList(session, userAnswerList);
				userAnswerListInSession = userAnswerService.getUserAnswerList(session);
			}
			
			questionSessionListResp = questionSessionList;
			userResp = userService.getUser(userIdStr, userListInSession);
			userAnswerListResp = userAnswerService.getUserAnswerList(userIdStr, userAnswerListInSession);
			if (userResp == null || userAnswerListResp == null) {
				return new UserAnswerDetailResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null,
						null,
						null
						);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new UserAnswerDetailResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null,
					null,
					null
					);
		}
		return new UserAnswerDetailResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				questionSessionListResp,
				userResp,
				userAnswerListResp
				);
	}
	
	@PostMapping(value = "/deleteQuestionnaireList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireListResp deleteQuestionnaireList(
			@RequestBody PostQuestionnaireReq req,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(defaultValue = "1") String index,
			HttpSession session
			) {
		List<Questionnaire> questionnaireListResp = new ArrayList<>();
		List<String> questionnaireIdList = req.getQuestionnaireIdList();
		int totalData = 0;
		int totalRows = 0;
		int pageSize = DataConstant.Key.PAGE_SIZE;
		int pageIndex = Integer.parseInt(index) - 1;
		try {
			totalData = questionnaireService.getQuestionnaireTotal();

			if (questionnaireIdList == null || questionnaireIdList.isEmpty()) {
				return new QuestionnaireListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null,
						totalData,
						totalRows,
						pageSize,
						pageIndex
						);
			}
			
			dataTransactionalService.deleteQuestionnaireList(questionnaireIdList);
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
			totalData = totalData - questionnaireIdList.size();
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
		}  catch (Exception e) {
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
	
	@PostMapping(value = "/deleteCommonQuestionList", 
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionListResp deleteCommonQuestionList(
			@RequestBody PostCommonQuestionReq req,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") String index
			) {
		List<CommonQuestion> commonQuestionListResp = new ArrayList<>();
		List<String> commonQuestionIdList = req.getCommonQuestionIdList();
		int totalData = 0;
		int totalRows = 0;
		int pageSize = DataConstant.Key.PAGE_SIZE;
		int pageIndex = Integer.parseInt(index) - 1;
		try {
			totalData = commonQuestionService.getCommonQuestionTotal();
			
			if (commonQuestionIdList == null || commonQuestionIdList.isEmpty()) {
				return new CommonQuestionListResp(
						RtnInfo.PARAMETER_REQUIRED.getCode(), 
						RtnInfo.PARAMETER_REQUIRED.getMessage(),
						null,
						totalData,
						totalRows,
						pageSize,
						pageIndex
						);
			}
			
			dataTransactionalService.deleteCommonQuestionList(commonQuestionIdList);
			DataListForPager commonQuestionListObj = 
					commonQuestionService
					.getCommonQuestionList(
							keyword, 
							pageSize, 
							pageIndex
							);
			totalRows = commonQuestionListObj.getTotalRows();
			totalData = totalData - commonQuestionIdList.size();
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
		}  catch (Exception e) {
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
	
	@GetMapping(value = "/deleteIsUpdateModeSession")
	
	public String deleteIsUpdateModeSession(HttpSession session) {
		String isDeleted = "true";
		try {
			Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			if (isUpdateMode == null) return isDeleted;
			
			session.removeAttribute(SessionConstant.Name.IS_UPDATE_MODE);
			return isDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage());
			isDeleted = "false";
			return isDeleted;
		}
	}
	
	@GetMapping(value = "/deleteQuestionnaireSession")
	
	public String deleteQuestionnaireSession(HttpSession session) {
		String isDeleted = "false";
		try {
			questionnaireSessionService.deleteQuestionnaireSession(session);
			questionSessionService.deleteAllQuestionSessionList(session);
			
			isDeleted = "true";
			return isDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage());
			isDeleted = "false";
			return isDeleted;
		}
	}
	
	@GetMapping(value = "/deleteCommonQuestionSession")
	
	public String deleteCommonQuestionSession(HttpSession session) {
		String isDeleted = "false";
		try {
			commonQuestionSessionService.deleteCommonQuestionSession(session);
			questionSessionService.deleteAllQuestionSessionList(session);
			
			isDeleted = "true";
			return isDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage());
			isDeleted = "false";
			return isDeleted;
		}
	}
	
	@GetMapping(value = "/deleteSessionOfUserAndUserAnswerList")
	
	public String deleteSessionOfUserAndUserAnswerList(HttpSession session) {
		String isDeleted = "false";
		try {
			userService.deleteUser(session);
			userAnswerService.deleteUserAnswerList(session);
			
			isDeleted = "true";
			return isDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage());
			isDeleted = "false";
			return isDeleted;
		}
	}
	
	@GetMapping(value = "/deleteSessionOfListOfUserAndUserAnswer")
	
	public String deleteSessionOfListOfUserAndUserAnswer(HttpSession session) {
		String isDeleted = "false";
		try {
			userService.deleteUserList(session);
			userAnswerService.deleteUserAnswerList(session);
			
			isDeleted = "true";
			return isDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage());
			isDeleted = "false";
			return isDeleted;
		}
	}
	
	@PostMapping(value = "/saveQuestionnaire", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public QuestionnaireResp saveQuestionnaire(
			@RequestBody PostQuestionnaireReq req, 
			HttpSession session
			) {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		Boolean isSetQuestionListOfCommonQuestion = (Boolean) session.getAttribute(SessionConstant.Name.IS_SET_QUESTION_LIST_OF_COMMON_QUESTION);
		try {
			QuestionnaireSession questionnaireSession = 
					questionnaireSessionService.getQuestionnaireSession(session);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (questionnaireSession == null || questionSessionList == null) {
				return new QuestionnaireResp(
						isUpdateMode ? RtnInfo.FAILED.getCode() : RtnInfo.ONE_QUESTION.getCode(), 
						isUpdateMode ? RtnInfo.FAILED.getMessage() : RtnInfo.ONE_QUESTION.getMessage(),
						null
						);
			}
			
			String questionnaireIdStr = questionnaireSession.getQuestionnaireId().toString();
			req.setQuestionnaireId(questionnaireIdStr);
			boolean isToUpdateQuestionnaireSessionChange = 
					questionnaireSessionService
					.isToUpdateQuestionnaireSessionChange(session, req);
			if (isToUpdateQuestionnaireSessionChange) {
				questionnaireSessionService.updateQuestionnaireSession(session, req);
				questionnaireSession = 
						questionnaireSessionService.getQuestionnaireSession(session);
			}
			
			boolean hasAtLeastOneRequiredQuestion = 
					questionSessionService.hasAtLeastOneRequiredQuestion(questionSessionList);
			if (!hasAtLeastOneRequiredQuestion) {
				return new QuestionnaireResp(
						RtnInfo.ONE_REQUIRED_QUESTION.getCode(), 
						RtnInfo.ONE_REQUIRED_QUESTION.getMessage(),
						null
						);
			}
			
			if (isSetQuestionListOfCommonQuestion) {
				List<QuestionSession> questionListOfCommonQuestionForChangedIds = 
						questionSessionService
						.getQuestionListOfCommonQuestionForChangedIds(questionnaireIdStr, questionSessionList, isUpdateMode);
				if (questionListOfCommonQuestionForChangedIds == null 
						|| questionListOfCommonQuestionForChangedIds.isEmpty()) {
					return new QuestionnaireResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(),
							null
							);
				}
				questionSessionList = questionListOfCommonQuestionForChangedIds;
			}
			else {
				boolean isNeedToConvertQuestionListOfCommonQuestionToQuestionList = 
						questionSessionService
						.isNeedToConvertQuestionListOfCommonQuestionToQuestionList(questionSessionList);
				if (isNeedToConvertQuestionListOfCommonQuestionToQuestionList) {
					List<QuestionSession> convertedQuestionList = 
							questionSessionService
							.convertQuestionListOfCommonQuestionToQuestionList(questionnaireIdStr, questionSessionList);
					if (convertedQuestionList == null 
							|| convertedQuestionList.isEmpty()) {
						return new QuestionnaireResp(
								RtnInfo.FAILED.getCode(), 
								RtnInfo.FAILED.getMessage(),
								null
								);
					}
					questionSessionList = convertedQuestionList;
				}
			}
			
			dataTransactionalService.saveQuestionnaire(questionnaireSession, questionSessionList);
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
				null
				);
	}
	
	@PostMapping(value = "/saveCommonQuestion", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CommonQuestionResp saveCommonQuestion(
			@RequestBody PostCommonQuestionReq req, 
			HttpSession session
			) {
		Boolean isUpdateMode = (Boolean) session.getAttribute(SessionConstant.Name.IS_UPDATE_MODE);
		try {
			CommonQuestionSession commonQuestionSession = 
					commonQuestionSessionService.getCommonQuestionSession(session);
			List<QuestionSession> questionSessionList = 
					questionSessionService.getQuestionSessionList(session);
			if (commonQuestionSession == null || questionSessionList == null) {
				return new CommonQuestionResp(
						isUpdateMode ? RtnInfo.FAILED.getCode() : RtnInfo.ONE_QUESTION.getCode(), 
						isUpdateMode ? RtnInfo.FAILED.getMessage() : RtnInfo.ONE_QUESTION.getMessage(),
						null
						);
			}
			
			String commonQuestionId = commonQuestionSession.getCommonQuestionId().toString();
			req.setCommonQuestionId(commonQuestionId);
			boolean isToUpdateCommonQuestionSessionChange = 
					commonQuestionSessionService
					.isToUpdateCommonQuestionSessionChange(session, req);
			if (isToUpdateCommonQuestionSessionChange) {
				commonQuestionSessionService.updateCommonQuestionSession(session, req);
				commonQuestionSession = 
						commonQuestionSessionService.getCommonQuestionSession(session);
			}
			
			boolean hasAtLeastOneRequiredQuestion = 
					questionSessionService.hasAtLeastOneRequiredQuestion(questionSessionList);
			if (!hasAtLeastOneRequiredQuestion) {
				return new CommonQuestionResp(
						RtnInfo.ONE_REQUIRED_QUESTION.getCode(), 
						RtnInfo.ONE_REQUIRED_QUESTION.getMessage(),
						null
						);
			}
			
			dataTransactionalService.saveCommonQuestion(commonQuestionSession, questionSessionList);
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
				null
				);
	}
	
	@GetMapping(value = "/saveUserAndUserAnswerList")
	
	public String saveUserAndUserAnswerList(
			HttpSession session
			) {
		try {
			User user = userService.getUser(session);
			List<UserAnswer> userAnswerList = 
					userAnswerService.getUserAnswerList(session);
			if (user == null || userAnswerList == null) {
				return RtnInfo.FAILED.getMessage();
			}
			
			dataTransactionalService.saveUserAndUserAnswerList(user, userAnswerList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return RtnInfo.FAILED.getMessage();
		}
		return null;
	}
	
	@GetMapping(value = "/downloadDataToCSV") 
	
	public ResponseEntity<Resource> downloadDataToCSV(
			HttpSession session
			) {
		try {
			List<QuestionSession> questionSessionList = questionSessionService.getQuestionSessionList(session);
			List<User> userList = userService.getUserList(session);
			List<UserAnswer> userAnswerList = userAnswerService.getUserAnswerList(session);
			if (questionSessionList == null || userList == null || userAnswerList == null) {
				return null;
			}
			List<UserAnswerDetailForCSV> userAnswerDetailForCSVList = 
					dataTransactionalService
					.getUserAnswerDetailForCSVList(questionSessionList, userList, userAnswerList);
			String filename = "userAnswerDetail_" + new Date() + ".csv";
		    InputStreamResource file = 
		    		new InputStreamResource(
		    				dataTransactionalService
		    				.getDataToCSV(userAnswerDetailForCSVList)
		    				);
			return ResponseEntity.ok()
	                .header("Content-Disposition", "attachment; filename=" + filename)
	                .contentType(MediaType.parseMediaType("application/csv"))
	                .body(file);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
}
