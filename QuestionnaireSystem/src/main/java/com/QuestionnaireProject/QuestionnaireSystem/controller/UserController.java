package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.UserListResp;
import com.QuestionnaireProject.QuestionnaireSystem.vo.UserResp;

@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/createUser", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserResp createUser(
			@RequestBody PostUserReq req, 
			HttpSession session
			) {
		User userResp = new User();
		String questionnaireIdStr = req.getQuestionnaireId();
		try {
			if (!StringUtils.hasText(questionnaireIdStr)) {
				return new UserResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null
						);
			}
			
			User user = userService.getUser(session);
			if (user == null) {
				String userIdStr = UUID.randomUUID().toString();
				req.setUserId(userIdStr);
				userService.createUser(session, req);
				user = userService.getUser(session);
				if (user == null) {
					return new UserResp(
							RtnInfo.FAILED.getCode(), 
							RtnInfo.FAILED.getMessage(), 
							null
							);
				}
			}
			
			UUID userId = user.getUserId();
			if (userId == null) {
				return new UserResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null
						);
			}
			req.setUserId(userId.toString());
			userService.updateUser(session, req);
			user = userService.getUser(session);
			if (user == null) {
				return new UserResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null
						);
			}
			
			userResp = user;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new UserResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null
					);
		}
		return new UserResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				userResp
				);
	}
	
	@PostMapping(value = "/getUserList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserListResp getUserList(
			@RequestBody PostUserReq req, 
			HttpSession session
			) {
		List<User> userListResp = new ArrayList<>();
		DataListForPager userListObj = new DataListForPager();
		String questionnaireIdStr = req.getQuestionnaireId();
		int totalRows = 0;
		int pageSize = 4;
		Integer pageIndex = (Integer) session.getAttribute(SessionConstant.Name.PAGE_INDEX);
		try {
			if (pageIndex == null) {
				session.setAttribute(SessionConstant.Name.PAGE_INDEX, 0);
				pageIndex = (Integer) session.getAttribute(SessionConstant.Name.PAGE_INDEX); 
			}
			
			List<User> userListInSession = userService.getUserList(session);
			if (userListInSession == null) {
				List<User> userList = userService.getUserList(questionnaireIdStr);
				if (userList == null) {
					return new UserListResp(
							RtnInfo.SUCCESSFUL.getCode(), 
							RtnInfo.SUCCESSFUL.getMessage(), 
							null,
							totalRows,
							pageIndex
							);
				}
				userService.setUserList(session, userList);
				userList = userService.getUserList(session);
				userListObj = userService.getUserListForPager(pageSize, pageIndex, userList);
				totalRows = userListObj.getTotalRows();
				userListInSession = userListObj.getUserList();
				return new UserListResp(
						RtnInfo.SUCCESSFUL.getCode(), 
						RtnInfo.SUCCESSFUL.getMessage(), 
						userListInSession,
						totalRows,
						pageIndex
						);
			}
			
			userListObj = userService.getUserListForPager(pageSize, pageIndex, userListInSession);
			totalRows = userListObj.getTotalRows();
			userListResp = userListObj.getUserList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new UserListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null,
					totalRows,
					pageIndex
					);
		}
		return new UserListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				userListResp,
				totalRows,
				pageIndex
				);
	}
	
	@PostMapping(value = "/updateUserList", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserListResp updateUserList(
			@RequestBody PostUserReq req, 
			HttpSession session
			) {
		List<User> userListResp = new ArrayList<>();
		DataListForPager userListObj = new DataListForPager();
		String strIndex = req.getStrIndex();
		int totalRows = 0;
		int pageSize = 4;
		Integer pageIndex = (Integer) session.getAttribute(SessionConstant.Name.PAGE_INDEX);
		try {
			if (pageIndex == null) {
				pageIndex = 0;
				return new UserListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null,
						totalRows,
						pageIndex
						);
			}
			
			List<User> userListInSession = userService.getUserList(session);
			if (userListInSession == null) {
				return new UserListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null,
						totalRows,
						pageIndex
						);
			}
			
			if (strIndex.equals("First")) 
				pageIndex = 0;
			else if (strIndex.equals("Prev")) 
				pageIndex -= 1;
			else if (strIndex.equals("Next")) 
				pageIndex += 1;
			else if (strIndex.equals("Last")) 
				pageIndex = Integer.MAX_VALUE;
			else
				pageIndex = Integer.parseInt(strIndex) - 1;
			
			userListObj = userService.getUserListForPager(pageSize, pageIndex, userListInSession);
			pageIndex = userListObj.getPageIndex();
			session.setAttribute(SessionConstant.Name.PAGE_INDEX, pageIndex);
			totalRows = userListObj.getTotalRows();
			userListResp = userListObj.getUserList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new UserListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null,
					totalRows,
					pageIndex
					);
		}
		return new UserListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				userListResp,
				totalRows,
				pageIndex
				);
	}

}
